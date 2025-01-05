import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        OlympicsAnalyzer olympicsAnalyzer = new OlympicsAnalyzer(Paths.get("src", "local_test_data").toString());

        Map<String, Integer> result = olympicsAnalyzer.topPerformantFemale();

//        for (Map.Entry<String, Integer> entry : result.entrySet()) {
//            String athleteName = entry.getKey();
//            int goldMedalCount = entry.getValue();
//            System.out.println("Athlete: " + athleteName + ", Gold Medals: " + goldMedalCount);
//        }

        Map<String, Float> resultbmi = olympicsAnalyzer.bmiBySports();

//        for (Map.Entry<String, Float> entry : resultbmi.entrySet()) {
//            String sport = entry.getKey();
//            float bmi = entry.getValue();
//            System.out.println("sport: " + sport + ", BMI: " + bmi);
//        }

        Map<String, Set<Integer>> resultthird = olympicsAnalyzer.leastAppearedSport();

//        for (Map.Entry<String, Set<Integer>> entry : resultthird.entrySet()) {
//            String sport = entry.getKey();
////            float bmi = entry.getValue();
//            System.out.println("sport: " + sport);
//        }

        Map<String, Integer> medalTally = olympicsAnalyzer.winterMedalsByCountry();

//        for (Map.Entry<String, Integer> entry : medalTally.entrySet()) {
//            String sport = entry.getKey();
//            int cnt = entry.getValue();
//            System.out.println("sport: " + sport + ", medal: " + cnt);
//        }

        Map<String, Integer> ageByCountry = olympicsAnalyzer.topCountryWithYoungAthletes();

//        for (Map.Entry<String, Integer> entry : ageByCountry.entrySet()){
//            String country = entry.getKey();
//            int age = entry.getValue();
//            System.out.println("country: " + country + " age: " + age);
//        }

        Map<String, Set<Integer>> leastAppeared = olympicsAnalyzer.leastAppearedSport();

//        for (Map.Entry<String, Set<Integer>> entry : leastAppeared.entrySet()) {
//            String sport = entry.getKey();
//            Set<Integer> years = entry.getValue();
//            System.out.println("Sport: " + sport + ", Years: " + years);
//        }

    }
}