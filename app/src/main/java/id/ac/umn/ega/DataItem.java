package id.ac.umn.ega;

public class DataItem {
    private String CharactersID;
    private String CharactersName;
    private String CharactersImage;
    private String CharactersDescription;

    public DataItem(String IDCharacters, String NameCharacters, String ImageCharacters, String DescriptionCharacters) {
        CharactersID = IDCharacters;
        CharactersName = NameCharacters;
        CharactersImage = ImageCharacters;
        CharactersDescription = DescriptionCharacters;
    }

    public String getCharactersID() {
        return CharactersID;
    }

    public String getCharactersName() {
        return CharactersName;
    }

    public String getCharactersImage() {
        return CharactersImage;
    }

    public String getCharactersDescription() {
        return CharactersDescription;
    }

}
