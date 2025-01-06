# Analysis of Olympic Historical Dataset
This repo contains assignment 1 of Computer System Design and Application (CS209) course SUSTech Fall 2024. The task is to analyse Olympic historical dataset using Java programming language and utilizing its features: Collections, Lambda, and Streams.

## Datasets Description
The dataset is sourced from [Kaggle](https://www.kaggle.com/datasets/josephcheng123456/olympic-historical-dataset-from-olympediaorg).
![ER table dataset](ERtable.png)
This dataset contains:

- 154,902 unique athletes and their biological information i.e. height, weight, date of birth
- All Winter / Summer Olympic games from 1896 to 2022
- 7326 unique results (result for a specific event played at an Olympic game)
- 314,726 rows of athlete to result data which includes both team sports and individual sports.
- 235 distinct countries (some existing from the past)

## Data Analysis
The analysis will be implemented in the methods in the
`OlympicsAnalyzer` class. Method details are described below.

### 1. Top 10 Performant Female Athletes in Individual Sport
    `
    public Map<String, Integer> topPerformantFemale()
    

### 2. BMI by Sports
    `
    public Map<String, Float> bmiBySports()
### 3. Top 10 Least Appeared Summer Sports
    `
    public Map<String, Set<Integer>> leastAppearedSport()

### 4. Top 10 Countries by Medals in Winter Olympics since the year 2000
    `public Map<String, Integer> winterMedalsByCountry()



