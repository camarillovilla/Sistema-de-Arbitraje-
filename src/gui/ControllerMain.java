package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import businesslogic.ArticleDAO;
import businesslogic.AuthorDAO;
import businesslogic.BusinessException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import domain.Article;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerMain implements Initializable {
    @FXML
    private TableView<String> tableViewArticle;
    
    @FXML
    private TableColumn<String, String> tableColumnImage;
    
    @FXML
    private TableColumn<String, String> tableColumnInformation;
    
    @FXML
    private ToggleGroup isAccepted;
   
    @FXML
    private JFXButton buttonExit;
    
    @FXML
    private TextField textField1;
    
    @FXML
    private TextField textField2;
    
    @FXML
    private TextField textField3;
    
    @FXML
    private TextField textField4;
    
    @FXML
    private TextField textField5;
    
    @FXML
    private AnchorPane anchorPaneArticleEvaluation;
    
    @FXML
    private AnchorPane anchorPaneLiberation;
    
    @FXML 
    private JFXRadioButton radioButtonAceppted;
    
    @FXML 
    private JFXRadioButton radioButtonNotAceppted;
    
    @FXML
    private Label labelMoreArticleInformation;
    
    @FXML
    private Label labelArticleTitle;
    
    @FXML
    private Label labelTotal;
    
     @FXML
    private Label labelArticleTitleLiberation;
    
    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;
    
    private final ArticleDAO ARTICLE_DAO = new ArticleDAO();
    private final AuthorDAO AUTHOR_DAO = new AuthorDAO();
    private final FixTextToCell FIX_TEXT_TO_CELL = new FixTextToCell();
    ObservableList<String> articleObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        validateTextFieldsFormat();
        setMaxLenght(textField1, 2);
        setMaxLenght(textField2, 2);
        setMaxLenght(textField3, 2);
        setMaxLenght(textField4, 2);
        setMaxLenght(textField5, 2);        
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnInformation);
        tableColumnInformation.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        setReleasedArticlesInformation();
        addImageReleased();        
    }
    
    public void validateTextFieldsFormat() {
        textField1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField2.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textField3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField3.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        textField4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField4.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textField5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField5.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
    public static void setMaxLenght(TextField textField, int limit) {
        UnaryOperator<TextFormatter.Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textField.setTextFormatter(new TextFormatter(textLimitFilter));
    }
    
    public void clearAllFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        labelTotal.setText("");       
    }    
    
    public void showFieldsAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText("Llena todos los campos o \nVerifica el rango de cada lineamiento ");
        alert.showAndWait();
    }
        
    @FXML
    public void addValuesToEvaluate(MouseEvent mouseEvent) {             
        int totalValue = 0;  
        int contador = 0;
        int value = 0;
        
        if (!textField1.getText().isEmpty()) {
            value = Integer.parseInt(textField1.getText());
            if ((value >= 0) && (value <= 10)) {
                totalValue +=  value;
                contador++;
            }
        }
        
        if (!textField2.getText().isEmpty()) {
            value = Integer.parseInt(textField2.getText());
            if ((value >= 0) && (value <= 20)) {
                totalValue +=  value;
                contador++;
            }            
        }        
        
        if (!textField3.getText().isEmpty()) {
            value = Integer.parseInt(textField3.getText());
            if ((value >= 0) && (value <= 40)) {
                totalValue +=  value;
                contador++;
            }
        }
        
        if (!textField4.getText().isEmpty()) {
            value = Integer.parseInt(textField4.getText());
            if ((value >= 0) && (value <= 20)) {
                totalValue +=  value;
                contador++;
            }
        }
        
        if (!textField5.getText().isEmpty()) {
            value = Integer.parseInt(textField5.getText());
            if ((value >= 0) && (value <= 10)) {
                totalValue +=  value;
                contador++;
            }
        }
                
        if (contador == 5) {
            labelTotal.setText(totalValue + "");
        } else {
            showFieldsAlert();
        }         
    }
        
    @FXML
    public void confirmArticleEvaluation(MouseEvent mouseEvent) throws BusinessException {
        System.out.println(radioButtonAceppted.isSelected());
        System.out.println(radioButtonNotAceppted.isSelected());
        if (!labelTotal.getText().isEmpty() && (radioButtonAceppted.isSelected() || radioButtonNotAceppted.isSelected())) {
            String information = tableViewArticle.getSelectionModel().getSelectedItem();                
            if (information.contains(" ⚫ ")) {
                String[] parts = information.split("\\⚫");
                String articleTitle = parts[0].trim();
                int articleGrade = Integer.parseInt(labelTotal.getText());                
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
                DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm");
                String dateArticle = dateTimeFormatter.format(LocalDateTime.now());
                String timeArticle = dateTimeFormatterTime.format(LocalDateTime.now());        

                ARTICLE_DAO.changeStatusToEvaluated(articleTitle, dateArticle, timeArticle, articleGrade);
                clearAllFields();
                anchorPaneArticleEvaluation.setVisible(false);
                showEvaluatedArticles(mouseEvent);
                tableViewArticle.refresh();
            } 
        }  else {
            showFieldsAlert();
        }
    }
    
    public void selectToEvaluateArticle() {
        tableViewArticle.getSelectionModel().selectedItemProperty().addListener
        ((ObservableValue<? extends String> observable, String oldObjective, String newObjective) -> {
            if (tableViewArticle.getSelectionModel().getSelectedItem() != null && imageView2.isVisible()) {
                anchorPaneArticleEvaluation.setVisible(true);
                clearAllFields();                                            
                String information = tableViewArticle.getSelectionModel().getSelectedItem();                
                if (information.contains(" ⚫ ")) {
                    String moreInformation = "";
                    String[] parts = information.split("\\⚫");
                    labelArticleTitle.setText(parts[0].trim());  
                    for (int i = 1;  i < parts.length; i++) {
                        moreInformation += " ⚫ " + parts[i].trim();
                    }
                    labelMoreArticleInformation.setText(moreInformation);
                }              
            }
        });
    }
    
    @FXML
    public void acceptArticleLiberation(MouseEvent mouseEvent) {
        if (tableViewArticle.getSelectionModel().getSelectedItem() != null) {
            anchorPaneLiberation.setVisible(true);
            String information = tableViewArticle.getSelectionModel().getSelectedItem();
            String articleTitle = null;
            if (information.contains(" ⚫ ")) {
                String[] parts = information.split("\\⚫");
                articleTitle = parts[0].trim();
            }
            System.out.println(articleTitle);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
            DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm");
            String dateArticle = dateTimeFormatter.format(LocalDateTime.now());
            String timeArticle = dateTimeFormatterTime.format(LocalDateTime.now());
            try {
                ARTICLE_DAO.changeStateToReleased(articleTitle, dateArticle, timeArticle);
                anchorPaneLiberation.setVisible(false);
                showLiberationArticles(mouseEvent);
            } catch (BusinessException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @FXML
    public void canceltArticleLiberation(MouseEvent mouseEvent) {
        if (tableViewArticle.getSelectionModel().getSelectedItem() != null) {
            tableViewArticle.getSelectionModel().clearSelection();
            anchorPaneLiberation.setVisible(false);
        }
    }    
    
    public void selectToLiberationArticle() {
        tableViewArticle.getSelectionModel().selectedItemProperty().addListener
        ((ObservableValue<? extends String> observable, String oldObjective, String newObjective) -> {
            if (tableViewArticle.getSelectionModel().getSelectedItem() != null && imageView3.isVisible()) {
                anchorPaneLiberation.setVisible(true);
                
                String information = tableViewArticle.getSelectionModel().getSelectedItem();                
                if (information.contains(" ⚫ ")) {
                    String[] parts = information.split("\\⚫");
                    labelArticleTitleLiberation.setText(parts[0].trim());  
                }
            }
        });
    }

    public void setReleasedArticlesInformation() {
        try {    
            ArrayList<Article> articleList = ARTICLE_DAO.consultReleasedArticles();         
            for (Article article : articleList) {
                String articleInformation = null;
                String authors = AUTHOR_DAO.consultNameOfTheAuthor(article.getTitle());
                articleInformation = article.getTitle() + "\n" + authors + " ⚫ " + article.getState() + " ⚫ "
                    + article.getReceptionDate() + " ⚫ " + article.getReceptionTime();
                articleObservableList.add(articleInformation);
            }        
            tableViewArticle.setItems(articleObservableList);
        } catch (BusinessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setEvaluatedArticlesInformation() {
        try {
            ArrayList<Article> articleList = ARTICLE_DAO.consultArticlesNotEvaluated();
            for (Article article : articleList) {
                String articleInformation = null;
                String authors = AUTHOR_DAO.consultNameOfTheAuthor(article.getTitle());
                String qualification = null;      
                if (article.getGrade() ==  0) {
                    qualification = "Calificación = Sin calificar";
                } else if (article.getGrade() > 70) {
                    qualification = "Calificación = " + article.getGrade() + " Aprobado";
                } else {
                    qualification = "Calificación = " + article.getGrade() + " No aprobado";
                }                
                
                if (article.getReceptionTime() == null) {
                    articleInformation = article.getTitle() + "\n⚫ " + qualification + " ⚫ " + authors + " ⚫ " 
                            + article.getState() + " ⚫ " + article.getReceptionDate();
                } else {
                    articleInformation = article.getTitle() + "\n⚫ " + qualification + " ⚫ " + authors + " ⚫ " 
                            + article.getState() + " ⚫ " + article.getReceptionDate() + " ⚫ " + article.getReceptionTime();
                }
                  
                articleObservableList.add(articleInformation);
            }        
            tableViewArticle.setItems(articleObservableList);
        } catch (BusinessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setLiberationArticlesInformation() {
        try {
            ArrayList<Article> articleList = ARTICLE_DAO.consultLiberationArticles();
            for (Article article : articleList) {
                String articleInformation = null;
                String authors = AUTHOR_DAO.consultNameOfTheAuthor(article.getTitle());                
                articleInformation = article.getTitle() + "\n" + " ⚫ " + authors + " ⚫ " + article.getState() + " ⚫ "
                        + article.getReceptionDate() + " ⚫ " + article.getReceptionTime();               
                articleObservableList.add(articleInformation);
            }        
            tableViewArticle.setItems(articleObservableList);
        } catch (BusinessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setRejectedArticlesInformation() {
        try {
            ArrayList<Article> articleList = ARTICLE_DAO.consultRejectedArticles();
            for (Article article : articleList) {
                String articleInformation = null;
                String authors = AUTHOR_DAO.consultNameOfTheAuthor(article.getTitle());
                articleInformation = article.getTitle() + "\n" + authors + " ⚫ " + "Rechazado ⚫ "
                        + article.getReceptionDate() + " ⚫ " + article.getReceptionTime();
                articleObservableList.add(articleInformation);
            }        
            tableViewArticle.setItems(articleObservableList);
        } catch (BusinessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void clickedNext(MouseEvent mouseEvent) {
        int size = tableViewArticle.getItems().size();
        ObservableList<String> aux = tableViewArticle.getItems();
        ObservableList<String> articleList = FXCollections.observableArrayList();
        if (size > 3) {
            for (int i = 3; i < size; i++) {
                articleList.add(aux.get(i));
            }
            for (int i = 0; i < 3; i++) {
                articleList.add(aux.get(i));
            }
            tableViewArticle.getSelectionModel().clearSelection();
            tableViewArticle.setItems(articleList);
        }
    }

    @FXML
    public void clickedBack(MouseEvent mouseEvent) {
        int size = tableViewArticle.getItems().size();
        ObservableList<String> auxBack = tableViewArticle.getItems();
        ObservableList<String> articleListBack = FXCollections.observableArrayList();
        if (size > 3) {
            for (int i = 3; i > 0; i--) {
                articleListBack.add(auxBack.get((size) - i));
            }

            for (int i = 0; i < size - 3; i++) {
                articleListBack.add(auxBack.get(i));
            }

            tableViewArticle.getSelectionModel().clearSelection();
            tableViewArticle.setItems(articleListBack);
        }
    }

    public void addImageReleased() {
        Callback<TableColumn<String, String>, TableCell<String, String>> cellFactory = (TableColumn<String, String> param) -> {
            final TableCell<String, String> cell = new TableCell<String, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView imageViewReleased = new ImageView("/images/released.png");
                        imageViewReleased.setFitHeight(50.0);
                        imageViewReleased.setFitWidth(50.0);
                        setGraphic(imageViewReleased);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tableColumnImage.setCellFactory(cellFactory);
    }

    public void addImageEvaluated() {
        Callback<TableColumn<String, String>, TableCell<String, String>> cellFactory = (TableColumn<String, String> param) -> {
            final TableCell<String, String> cell = new TableCell<String, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView imageViewReleased = new ImageView("/images/evaluated.png");
                        imageViewReleased.setFitHeight(50.0);
                        imageViewReleased.setFitWidth(50.0);
                        setGraphic(imageViewReleased);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tableColumnImage.setCellFactory(cellFactory);
    }
    
    public void addImageReceived() {
        Callback<TableColumn<String, String>, TableCell<String, String>> cellFactory = (TableColumn<String, String> param) -> {
            final TableCell<String, String> cell = new TableCell<String, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView imageViewReleased = new ImageView("/images/received.png");
                        imageViewReleased.setFitHeight(50.0);
                        imageViewReleased.setFitWidth(50.0);
                        setGraphic(imageViewReleased);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tableColumnImage.setCellFactory(cellFactory);
    }
    
    public void addImageRejected() {
        Callback<TableColumn<String, String>, TableCell<String, String>> cellFactory = (TableColumn<String, String> param) -> {
            final TableCell<String, String> cell = new TableCell<String, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView imageViewReleased = new ImageView("/images/rejected.png");
                        imageViewReleased.setFitHeight(50.0);
                        imageViewReleased.setFitWidth(50.0);
                        setGraphic(imageViewReleased);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tableColumnImage.setCellFactory(cellFactory);
    }

    @FXML
    public void showReleaseArticles(MouseEvent mouseEvent) {
        imageView1.setVisible(true);
        imageView2.setVisible(false);
        imageView3.setVisible(false);
        imageView4.setVisible(false);
        anchorPaneLiberation.setVisible(false);
        anchorPaneArticleEvaluation.setVisible(false);
        clearAllFields();
        tableViewArticle.getItems().clear();
        setReleasedArticlesInformation();
        addImageReleased();
    }

    @FXML
    public void showEvaluatedArticles(MouseEvent mouseEvent) {
        imageView1.setVisible(false);
        imageView2.setVisible(true);
        imageView3.setVisible(false);
        imageView4.setVisible(false);
        anchorPaneLiberation.setVisible(false);
        tableViewArticle.getItems().clear();
        setEvaluatedArticlesInformation();
        //addImageEvaluated();
        addImageReceived();
        selectToEvaluateArticle();        
    }

    @FXML
    public void showLiberationArticles(MouseEvent mouseEvent) {
        imageView1.setVisible(false);
        imageView2.setVisible(false);
        imageView3.setVisible(true);
        imageView4.setVisible(false);
        anchorPaneArticleEvaluation.setVisible(false);
        clearAllFields();
        tableViewArticle.getItems().clear();
        setLiberationArticlesInformation();
        addImageEvaluated();
        selectToLiberationArticle();
    }

    @FXML
    public void showRejectedArticles(MouseEvent mouseEvent) {
        imageView1.setVisible(false);
        imageView2.setVisible(false);
        imageView3.setVisible(false);
        imageView4.setVisible(true);
        anchorPaneLiberation.setVisible(false);
        anchorPaneArticleEvaluation.setVisible(false);
        clearAllFields();
        tableViewArticle.getItems().clear();
        setRejectedArticlesInformation();
        addImageRejected();
    }

    @FXML
    public void exit(MouseEvent mouseEvent) {
        Stage stageReturn = (Stage) buttonExit.getScene().getWindow();
        stageReturn.close();
    }
}