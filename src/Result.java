public class Result {
    private String eventTitle;
    private String sport;
    private String year;
    private int editionId;
    private String season;

    public Result(String eventTitle, String sport, String year, int editionId, String season) {
        this.eventTitle = eventTitle;
        this.sport = sport;
        this.year = year;
        this.editionId = editionId;
        this.season = season;
    }
    
    public String getSeason() {
        return season;
    }
    
    public String getYear() {
        return year;
    }
    
    public String getSport() {
        return sport;
    }

    public int getEditionId() {
        return editionId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "eventTitle: " + this.eventTitle +
                "Sport: " + this.sport +
                "edition Id: " + this.editionId +
                "year: " + this.year +
                "season: " + this.season +
                "}";
    }
}
