public class OlympicGame {
    public int editionId;
    public String edition;
    public int year;

    public OlympicGame(int editionId, String edition, int year){
        this.editionId = editionId;
        this.edition = edition;
        this.year = year;
    }

    public int getEditionId() {
        return editionId;
    }

    public int getYear() {
        return year;
    }

    public String getEdition() {
        return edition;
    }

    @Override
    public String toString(){
        return "editionId: " + editionId +
                ", edition: " + edition +
                ", year: " + year;
    }
}
