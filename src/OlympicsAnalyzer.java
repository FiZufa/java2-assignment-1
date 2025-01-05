import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class OlympicsAnalyzer implements OlympicsAnalyzerInterface {
    private Map<Integer, Person> athleteBioData;
    private List<OlympicResult> olympicResultList;
    private Map<String, Integer> countryMedalMap;
    private Map<Integer, OlympicGame> olympicGameData;
    private Map<String, String> countriesData;

    String[] CSVfiles = {
            "/Olympic_Athlete_Bio_filtered.csv",
            "/Olympic_Athlete_Event_Results.csv",
            "/Olympic_Games_Medal_Tally.csv",
            "/Olympic_Results.csv",
            "/Olympics_Country.csv",
            "/Olympics_Games.csv"
    };
    public OlympicsAnalyzer(String datasetPath) {
        loadAthleteBio(datasetPath + CSVfiles[0]);
        loadResultJoin(datasetPath + CSVfiles[1]);
        loadMedalTally(datasetPath + CSVfiles[2]);
        loadOlympicGame(datasetPath + CSVfiles[5]);
        loadCountriesData(datasetPath + CSVfiles[4]);

    }

    private void loadAthleteBio(String filePath) {
        athleteBioData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read and discard the header line
            String header = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                if (values.length < 8 || values[0].trim().isEmpty() || values[1].trim().isEmpty()) {
                    System.out.println("Skipping invalid or empty line: " + line);
                    continue;
                }

                try {
                    int athleteId = Integer.parseInt(values[0].trim());
                    String name = values[1].trim();
                    String sex = values[2].trim();
                    String born = values[3].trim();
                    float height = values.length > 4 && !values[4].trim().isEmpty() ? Float.parseFloat(values[4].trim()) : 0;
                    float weight = values.length > 5 && !values[5].trim().isEmpty() ? Float.parseFloat(values[5].trim()) : 0;
                    String country = values.length > 6 ? values[6].trim() : "";
                    String NOC = values[7].trim();

                    Person person = new Person(name, sex, born, height, weight, country, NOC);
                    athleteBioData.put(athleteId, person);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number in line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadResultJoin(String filePath) {
        olympicResultList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            String header = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);

                if (values.length < 11) {
                    System.out.println("Not enough data for line: " + line);
                    continue;
                }

                try {
                    String edition = values[0].trim();
                    String sport = values[3].trim();

                    // Parse athleteId safely
                    int athleteId = Integer.parseInt(values[7].trim());

                    // Parse resultId safely
                    int resultId = Integer.parseInt(values[5].trim());

                    // Parse editionId safely
                    int editionId = Integer.parseInt(values[1].trim());

                    String medal = values[9].trim();
                    boolean isTeam = values[10].trim().equalsIgnoreCase("True");

                    // Add the parsed result to the list
                    olympicResultList.add(new OlympicResult(athleteId, resultId, editionId, sport, medal, isTeam, edition));

                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number in line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMedalTally(String filePath) {
        countryMedalMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            String header = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);

                // Check if line contains enough values
                if (values.length < 9) {
                    System.out.println("Not enough data for line: " + line);
                    continue;
                }

                try {
                    // Split the season and check for validity
                    String[] seasonSplit = values[0].split(" ");
                    if (seasonSplit.length < 2) {
                        System.out.println("Invalid season format in line: " + line);
                        continue;
                    }
                    String season = seasonSplit[1];

                    // Parse year and totalMedal safely
                    int year = Integer.parseInt(values[2].trim());
                    String countryNOC = values[4].trim();
                    int totalMedal = Integer.parseInt(values[8].trim());

                    // Only consider Winter seasons after 2000
                    if ("Winter".equals(season) && year >= 2000) {
                        countryMedalMap.merge(countryNOC, totalMedal, Integer::sum);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number in line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadOlympicGame(String filePath) {
        olympicGameData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);

                // Check if line contains enough values
                if (values.length < 4) {
                    System.out.println("Not enough data for line: " + line);
                    continue;
                }

                try {
                    String edition = values[0].trim();
                    int editionId = Integer.parseInt(values[1].trim());
                    int year = Integer.parseInt(values[3].trim());

                    olympicGameData.put(editionId, new OlympicGame(editionId, edition, year));

                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number in line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCountriesData(String filePath) {
        countriesData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            String header = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);

                // Check if the line contains enough values
                if (values.length < 2) {
                    System.out.println("Not enough data for line: " + line);
                    continue;
                }

                String countryCode = values[0].trim();
                String country = values[1].trim();

                countriesData.put(countryCode, country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // Toggle in/out of quotes
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim()); // Add field
                sb.setLength(0); // Reset buffer
            } else {
                sb.append(c); // Append char to the current field
            }
        }

        // Add the last field after exiting the loop
        result.add(sb.toString().trim());

        // Handle edge case: trailing commas can result in empty fields
        return result.toArray(new String[0]);
    }

    private int cleanAndParseInt(String value) {
        try {
            // Remove non-numeric characters
            String cleanedValue = value.replaceAll("[^0-9]", "");

            // Handle case where no numeric value is found
            if (cleanedValue.isEmpty()) {
                System.out.println("No numeric value found in: " + value);
                return -1; // Return a default or error value
            }
            return Integer.parseInt(cleanedValue);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + value);
            return -1; // Return a default or error value
        }
    }

    @Override
    public Map<String, Integer> topPerformantFemale() {
        Map<String, Integer> goldMedalCounts = new HashMap<>();

        for(OlympicResult result: olympicResultList){

            if("Gold".equals(result.getMedal()) && !result.isTeamSport()){
                int athleteId = result.getAthleteId();
                Person athlete = athleteBioData.get(athleteId);

                if(athlete != null && "Female".equals(athlete.getSex())){
                    String name = athlete.getName();
                    goldMedalCounts.put(name, goldMedalCounts.getOrDefault(name, 0) + 1);
                }
            }
        }
        return goldMedalCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Map<String, Float> bmiBySports() {
        // Maps to store athletes' BMI by sport
        Map<String, List<Double>> sportBmiMap = new HashMap<>();
        Map<String, Set<String>> sportAthleteNamesMap = new HashMap<>();

        // Process each Olympic result
        for (OlympicResult result : olympicResultList) {
            Person athlete = athleteBioData.get(result.getAthleteId());
            if (athlete != null && athlete.getHeight() > 0 && athlete.getWeight() > 0) {
                String sport = result.getSport();
                double bmi = athlete.getBmi();
                if (bmi > 0) {
                    // Use a Set to track unique athlete names for each sport
                    Set<String> athleteNames = sportAthleteNamesMap.computeIfAbsent(sport, k -> new HashSet<>());

                    // Add athlete only if not a duplicate
                    if (athleteNames.add(athlete.getName())) {
                        // Get or create the list for the sport
                        List<Double> bmiList = sportBmiMap.computeIfAbsent(sport, k -> new ArrayList<>());
                        bmiList.add(bmi); // Store the BMI
                    }
                }
            }
        }

        // Calculate average BMI for each sport
        Map<String, Float> averageBmiMap = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : sportBmiMap.entrySet()) {
            String sport = entry.getKey();
            List<Double> bmis = entry.getValue();

            // Calculate average BMI
            double averageBmi = bmis.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            // Round the average BMI to one decimal place
            float roundedBmi = Math.round(averageBmi * 10) / 10.0f;

            // Store the rounded average BMI in the map
            averageBmiMap.put(sport, roundedBmi);
        }

        // Sort the average BMI map by value in descending order, then by key
        return averageBmiMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
    @Override
    public Map<String, Set<Integer>> leastAppearedSport() {
        Map<String, Set<Integer>> sportEditionMap = new HashMap<>();
        Map<String, Set<Integer>> sportYearMap = new HashMap<>();

        for (OlympicResult result : olympicResultList){
            String edition = result.getEdition().split(" ")[1];
            if (Objects.equals(edition, "Summer")){
                String sport = result.getSport();
                int editionId = result.getEditionId();

                sportEditionMap.computeIfAbsent(sport, k -> new HashSet<>()).add(editionId);
            }
        }

        for (Map.Entry<String, Set<Integer>> entry : sportEditionMap.entrySet()) {
            String sport = entry.getKey();
            Set<Integer> editionIds = entry.getValue();
            Set<Integer> years = new HashSet<>();

            for (int editionId : editionIds) {
                OlympicGame game = olympicGameData.get(editionId);
                if (game != null) {
                    years.add(game.getYear());
                }
            }
            sportYearMap.put(sport, years);
        }

        return sportYearMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Set<Integer>>comparingByValue(Comparator.comparingInt(Set::size))
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(10) // Limit to the top 10
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public Map<String, Integer> winterMedalsByCountry() {
        return countryMedalMap.entrySet().stream()
                // Sort by sumMedal in descending order, then by country code in alphabetical order
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                // Collect the sorted entries into a LinkedHashMap to maintain order
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function (not needed here)
                        LinkedHashMap::new // Ensure insertion order
                ));
    }

    @Override
    public Map<String, Integer> topCountryWithYoungAthletes() {
        Map<String, List<Integer>> countyAgesMap = new HashMap<>();
        Set<Integer> processedAthleteIds = new HashSet<>(); // Set to track processed athlete IDs

        for (OlympicResult result : olympicResultList) {
            if (result.getEditionId() == 61) { // Check for 2020 Olympics
                int athleteId = result.getAthleteId();

                // Skip if athlete was already processed
                if (processedAthleteIds.contains(athleteId)) {
                    continue;
                }

                Person athlete = athleteBioData.get(athleteId);
                if (athlete != null && athlete.getAge() > 0) {
                    // Get the country code
                    String countryCode = athlete.getCountryCode();

                    // Look up the country name using the country code
                    String countryName = countriesData.get(countryCode);

                    // If countryName is found, add the athlete's age to the map
                    if (countryName != null) {
                        countyAgesMap
                                .computeIfAbsent(countryName, k -> new ArrayList<>())
                                .add(athlete.getAge());

                        // Mark athlete as processed
                        processedAthleteIds.add(athleteId);
                    }
                }
            }
        }

        // Calculate average age for each country
        Map<String, Integer> avgAgeByCountry = countyAgesMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (int) Math.round(entry.getValue().stream()
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(0)) // Calculate the average and round
                ));

        // Sort by average age in ascending order, then alphabetically by country name
        return avgAgeByCountry.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .thenComparing(Map.Entry::getKey))
                .limit(10) // Return only the top 10 countries
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


}
