package id.ac.umn.ega;

public class ComicItem {
    private String ComicTitle;
    private String ComicImage;

    public ComicItem(String comicTitle, String comicImage) {
        ComicTitle = comicTitle;
        ComicImage = comicImage;
    }

    public String getComicTitle() {
        return ComicTitle;
    }

    public String getComicImage() {
        return ComicImage;
    }
}
