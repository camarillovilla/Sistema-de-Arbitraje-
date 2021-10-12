package domain;

/**
 * 
 * @author daniCV
 */
public class Article {

    private int id;
    private String title;
    private String receptionDate; 
    private String state;
    private String receptionTime; 
    private int grade;


    public Article(String title, String receptionDate, String state, String receptionTime, int grade) {
        this.title = title;
        this.receptionDate = receptionDate;
        this.state = state;
        this.receptionTime = receptionTime;
        this.grade = grade;
    }

    public Article(int id, String title, String receptionDate, String state, String receptionTime, int grade) {
        this.id = id;
        this.title = title;
        this.receptionDate = receptionDate;
        this.state = state;
        this.receptionTime = receptionTime;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getReceptionDate() {
        return receptionDate;
    }

    public String getState() {
        return state;
    }

    public String getReceptionTime() {
        return receptionTime;
    }

    public int getGrade() {
        return grade;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReceptionDate(String receptionDate) {
        this.receptionDate = receptionDate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setReceptionTime(String receptionTime) {
        this.receptionTime = receptionTime;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Article{" + "title=" + title + ", receptionDate=" + receptionDate + ", state=" + state + ", receptionTime=" + receptionTime + ", grade=" + grade + '}';
    }
}