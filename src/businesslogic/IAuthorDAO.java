package businesslogic;

/**
 * 
 * @author daniCV
 */
public interface IAuthorDAO {
    public String consultNameOfTheAuthor(String articleTitle) throws BusinessException;
}
