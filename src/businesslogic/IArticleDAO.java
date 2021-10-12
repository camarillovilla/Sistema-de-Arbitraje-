package businesslogic;

import domain.Article;
import java.util.ArrayList;

/**
 * 
 * @author daniCV
 */
public interface IArticleDAO {    
    public int changeStatusToEvaluated(String updateArticleTitle, 
    String updateReceptionDate, String updateReceptionTime, int updateGrade) throws BusinessException;
    public int changeStateToReleased(String updateArticleTitle, 
        String updateReceptionDate, String updateReceptionTime) throws BusinessException;
    public ArrayList<Article> consultRejectedArticles() throws BusinessException;
    public ArrayList<Article> consultArticlesNotEvaluated() throws BusinessException;
    public ArrayList<Article> consultReleasedArticles() throws BusinessException;
    public ArrayList<Article> consultAllArticles() throws BusinessException;
    public ArrayList<Article> consultLiberationArticles() throws BusinessException;
}
