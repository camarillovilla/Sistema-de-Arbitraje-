package domain;

/**
 * 
 * @author daniCV
 */
public class Author {
    private String lastName;
    private String name; 
    private String articleTitle;

    public Author(String lastName, String name){
        this.lastName = lastName;
        this.name = name;
    }

    public Author(String lastName, String name, String articleTitle) {
        this.lastName = lastName;
        this.name = name;
        this.articleTitle = articleTitle;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    @Override
    public String toString() {
        return "Author{" + "lastName=" + lastName + ", name=" + name + ", articleTitle=" + articleTitle + '}';
    }
}
