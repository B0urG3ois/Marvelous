package id.ac.umn.ega;

public class SeriesItem {
    private String SeriesTitle;
    private String SeriesImage;

    public SeriesItem(String seriesTitle, String seriesImage) {
        SeriesTitle = seriesTitle;
        SeriesImage = seriesImage;
    }


    public String getSeriesTitle() {
        return SeriesTitle;
    }

    public String getSeriesImage() {
        return SeriesImage;
    }
}
