package experiment.diary;

public class Diary {
    private int month;
    private int day;
    private String title;
    private String content;
    private byte[] picture;

    Diary(int month, int day, String title, String content, byte[] picture) {
        this.month   = month;
        this.day     = day;
        this.title   = title;
        this.content = content;
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }
    public int getDay() {
        return day;
    }
    public int getMonth() {
        return month;
    }
    public String getContent() {
        return content;
    }
    public String getTitle() {
        return title;
    }

    // TODO: the method of picture changing
}
