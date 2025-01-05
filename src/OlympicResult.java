public class OlympicResult {
    private int athleteId;
    private int resultId;
    private int editionId;
    private String sport;
    private String medal;
    boolean isTeam;
    String edition;

    public OlympicResult(int athleteId, int resultId, int editionId, String sport, String medal, boolean isTeam, String edition){
        this.athleteId = athleteId;
        this.resultId = resultId;
        this.editionId = editionId;
        this.sport = sport;
        this.medal = medal;
        this.isTeam = isTeam;
        this.edition = edition;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public String getSport(){
        return sport;
    }

    public String getMedal() {
        return medal;
    }
    public boolean isTeamSport() {
        return isTeam;
    }
    public String getEdition() {
        return edition;
    }

    public int getEditionId() {
        return editionId;
    }
    @Override
    public String toString(){
        return "OlympicResult{" +
                "athleteId: " + this.athleteId +
                ", resultId: " + this.resultId +
                ", editionId: " + this.editionId +
                ", medal: " + this.medal +
                ", isTeam: " + this.isTeam +
                "}";
    }

}
