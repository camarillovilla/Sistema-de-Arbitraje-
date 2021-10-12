package businesslogic;

import dataaccess.SqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author daniCV
 */
public class AuthorDAO implements IAuthorDAO {
    
    private final SqlConnection CONNECTION = new SqlConnection();
    
    @Override
    public String consultNameOfTheAuthor(String articleTitle) throws BusinessException {

        String query = "SELECT GROUP_CONCAT(CONCAT(author.lastName, ', ',author.name, '; ')) AS autores FROM article\n" +
                "JOIN author ON author.articleTitle = article.title\n" +
                "WHERE article.title = ?\n" +
                "GROUP BY article.title;";
        String authorsInformation = null;
        try {
            PreparedStatement preparedStatement =
                    CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, articleTitle);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                authorsInformation = result.getString("autores");
            }

        } catch (SQLException sqlException) {
            throw new BusinessException("DatabaseConnectionError", sqlException);
        } finally {
            CONNECTION.disconnect();
        }
        return authorsInformation;
    }
}