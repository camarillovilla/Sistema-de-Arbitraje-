package businesslogic;

import domain.Article;
import dataaccess.SqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author daniCV
 */
public class ArticleDAO implements IArticleDAO {

    private final SqlConnection CONNECTION = new SqlConnection();
    
    @Override
    public int changeStatusToEvaluated(String updateArticleTitle, 
            String updateReceptionDate, String updateReceptionTime, int updateGrade) 
            throws BusinessException {
        String query = "UPDATE article SET state = ?, receptionDate = ?, receptionTime = ?,"
            + "grade = ? WHERE title = ?";
        int result = 0; 
        try {
            PreparedStatement preparedStatement = 
                    CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, "Evaluado");
            preparedStatement.setString(2, updateReceptionDate);
            preparedStatement.setString(3, updateReceptionTime);
            preparedStatement.setInt(4, updateGrade);
            preparedStatement.setString(5, updateArticleTitle);            
            result = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new BusinessException("DatabaseConnectionError", sqlException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public int changeStateToReleased(String updateArticleTitle, 
        String updateReceptionDate, String updateReceptionTime) throws BusinessException {
        String query = "UPDATE article SET state = 'Liberado', receptionDate = ?, receptionTime = ?"
            + "WHERE title = ?";
        int result = 0; 
        try {
            PreparedStatement preparedStatement = 
                CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, updateReceptionDate);
            preparedStatement.setString(2, updateReceptionTime);
            preparedStatement.setString(3, updateArticleTitle);            
            result = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new BusinessException("DatabaseConnectionError", sqlException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public ArrayList<Article> consultRejectedArticles() 
            throws BusinessException {
        String query = "SELECT title, receptionDate, state, receptionTime, grade "
            + "FROM article WHERE grade < 70";
        ArrayList<Article> arrayListArticle = new ArrayList<>();
        try {
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListArticle.add(new Article(resultSet.getString("title"), 
                    resultSet.getString("receptionDate"), resultSet.getString("state"), 
                    resultSet.getString("receptionTime"), resultSet.getInt("grade")));
            }
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListArticle;
    }

    @Override
    public ArrayList<Article> consultArticlesNotEvaluated() throws BusinessException {
        String query = "SELECT title, receptionDate, state, receptionTime, grade "
            + "FROM article WHERE grade IS NULL OR state = 'Evaluado'";
        ArrayList<Article> arrayListArticle = new ArrayList<>();
        try {
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListArticle.add(new Article(resultSet.getString("title"), 
                    resultSet.getString("receptionDate"), resultSet.getString("state"), 
                    resultSet.getString("receptionTime"), resultSet.getInt("grade")));
            }
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListArticle;
    }

    @Override
    public ArrayList<Article> consultReleasedArticles() throws BusinessException {
        String query = "SELECT title, receptionDate, state, receptionTime, grade "
            + "FROM article WHERE state = 'Liberado'";
        ArrayList<Article> arrayListArticle = new ArrayList<>();
        try {
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListArticle.add(new Article(resultSet.getString("title"), 
                    resultSet.getString("receptionDate"), resultSet.getString("state"), 
                    resultSet.getString("receptionTime"), resultSet.getInt("grade")));
            }
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListArticle;
    }

    @Override
    public ArrayList<Article> consultAllArticles() throws BusinessException {
        String query = "SELECT title, receptionDate, state, receptionTime, grade "
            + "FROM article";
        ArrayList<Article> arrayListArticle = new ArrayList<>();
        try {
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListArticle.add(new Article(resultSet.getString("title"), 
                    resultSet.getString("receptionDate"), resultSet.getString("state"), 
                    resultSet.getString("receptionTime"), resultSet.getInt("grade")));
            }
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListArticle;
    }

    @Override
    public ArrayList<Article> consultLiberationArticles() throws BusinessException {
        String query = "SELECT title, receptionDate, state, receptionTime, grade "
                + "FROM article WHERE grade IS NOT NULL AND state = 'Evaluado'";
        ArrayList<Article> arrayListArticle = new ArrayList<>();
        try {
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListArticle.add(new Article(resultSet.getString("title"),
                        resultSet.getString("receptionDate"), resultSet.getString("state"),
                        resultSet.getString("receptionTime"), resultSet.getInt("grade")));
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListArticle;
    }
}
