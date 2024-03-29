package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.StatusManager;
import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;

/**
 * The JavaFX controller for the overview of users' BookList
 */
public class BookListController extends WindowController{

    private final UserManager userManager = UserManager.getInstance();
    private final StatusManager statusManager = StatusManager.getInstance();
    private final User user;
    private List<Status> statuses;
    public Button buttonReturnToProfile;
    public Button buttonSearchBook;
    public Button buttonAddEditBook;
    public Button buttonRemoveBook;
    public TextField bookNameField;
    public TextField bookStatusIdField;

    public TableView<Status> statusTableView;
    public TableColumn<Status, Integer> idStatusColumn;
    public TableColumn<Status, String> bookNameColumn;
    public TableColumn<Status, Long> bookUINColumn;
    public TableColumn<Status, String> bookAuthorColumn;
    public TableColumn<Status, String> bookGenreColumn;
    public TableColumn<Status, String> bookStatusColumn;
    public TableColumn<Status, Double> bookScoreColumn;

    /**
     * A constructor that initializes the user that has invoked the opening of this window
     * and also statuses that are linked to this user.
     * @param user the user that requested the opening of this window
     */
    public BookListController(User user) {
        this.user = user;
        try {
            statuses = statusManager.searchByUser(user);
        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Unexpected error occurred|" + exception.getMessage());
        }
    }

    /**
     * The method that gets called right before the opening of this window
     * Its purpose is to initialize all and restrict some JavaFX components shown in the created window
     */
    @FXML
    public void initialize() throws MyBookListException{

        statuses = statusManager.searchByUser(user);

        idStatusColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameColumn.setCellValueFactory((tableData) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(tableData.getValue().getBook().getName());
            return property;
        });
        bookUINColumn.setCellValueFactory((tableData) -> {
            SimpleObjectProperty<Long> property = new SimpleObjectProperty<>();
            property.setValue(tableData.getValue().getBook().getUIN());
            return property;
        });
        bookAuthorColumn.setCellValueFactory((tableData) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(tableData.getValue().getBook().getAuthor().getName());
            return property;
        });
        bookGenreColumn.setCellValueFactory((tableData) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(tableData.getValue().getBook().getGenre().getName());
            return property;
        });
        bookStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        bookScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        bookStatusIdField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                bookStatusIdField.setText(newValue.replaceAll("\\D", ""));
            }
        });
        refreshAll();
    }

    /**
     * Clicking on the corresponding button closes this window and opens profile window
     */
    @FXML
    public void returnToProfile(ActionEvent actionEvent) {
        openWindow("Profile", "/fxml/profileScreen.fxml", new ProfileController(user), (Stage)buttonReturnToProfile.getScene().getWindow(), false);
    }

    /**
     * Clicking on the corresponding button opens up new window where the user can edit some existing statuses
     * but also add some new ones along with adding authors, genres and books
     */
    @FXML
    public void addEditBookInfo(ActionEvent actionEvent) {
        openWindow("Edit MyBookList", "/fxml/editBookListScreenNew.fxml", new EditController(user), (Stage)buttonAddEditBook.getScene().getWindow(), true);
        refreshAll();
    }

    /**
     * A method that removes a book from the users book list with the entered ID shown in the book list table
     * In case of any errors occurring a pop-up window will be shown giving more details about the error itself
     */
    @FXML
    public void removeBook(ActionEvent actionEvent) {
        try {
            String bookStatusIdFieldTxt = bookStatusIdField.getText().trim();
            if (bookStatusIdFieldTxt.isEmpty()) {
                throw new MyBookListException("Provide an ID of a book you wish to remove from your book list.");
            }
            int id = Integer.parseInt(bookStatusIdFieldTxt);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove the book with id of " + id, ButtonType.YES, ButtonType.NO).showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                statusManager.delete(Integer.parseInt(bookStatusIdFieldTxt));
                openAlert(Alert.AlertType.INFORMATION, "Deletion success|chosen book successfully removed from your book list.");
                refreshAll();
                bookStatusIdField.setText("");
            }
        }
        catch (MyBookListException exception) {
            openAlert(Alert.AlertType.ERROR, "Deletion failure|" + exception.getMessage());
            if (exception.getMessage().contains("Access denied")) {
                bookStatusIdField.setEditable(false);
                buttonRemoveBook.setDisable(true);
            }
        }
    }

    /**
     * Method that reacquires all users and all required statuses to be shown
     * It usually gets called after modifications have been made
     */
    private void refreshAll() {
        try {
            if (user != null) {
                statusTableView.setItems(FXCollections.observableList(statusManager.searchByUser(user)));
            }
            statusTableView.refresh();
        }
        catch (MyBookListException exception) {
            if (!exception.getMessage().equals("Database is empty - no books found.")) {
                openAlert(Alert.AlertType.ERROR, "Unexpected error occurred|" + exception.getMessage());
                System.exit(-1);
            }
        }
    }
}
