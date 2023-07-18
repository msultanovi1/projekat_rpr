package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.bussines.StatusManager;
import ba.unsa.etf.rpr.bussines.UserManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    public BookListController(User user) {
        this.user = user;
        try {
            statuses = statusManager.searchByUser(user);
        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Unexpected error occured|" + exception.getMessage());
        }
    }

    @FXML
    public void initialize() {
        idStatusColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookUINColumn.setCellValueFactory(new PropertyValueFactory<>("uin"));
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
        bookStatusColumn.setCellValueFactory((tableData) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(tableData.getValue().getStatus());
            return property;
        });
        bookScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        refreshAll();
    }
    @FXML
    public void returnToProfile(ActionEvent actionEvent) {
        openWindow("Profile", "/fxml/profileScreen.fxml", new ProfileController(user), (Stage)buttonReturnToProfile.getScene().getWindow(), false);
    }

    @FXML
    public void searchBook(ActionEvent actionEvent) {
    }
    @FXML
    public void addEditBookInfo(ActionEvent actionEvent) {
        openWindow("Edit MyBookList", "/fxml/editBookListScreen.fxml", new EditController(user), (Stage)buttonAddEditBook.getScene().getWindow(), true);
        refreshAll();
    }
    @FXML
    public void removeBook(ActionEvent actionEvent) {
        try {
            String bookStatusIdFieldTxt = bookStatusIdField.getText().trim();
            if (bookStatusIdFieldTxt.isEmpty()) {
                throw new MyBookListException("Provide an ID of a case to delete the corresponding case.");
            }
            int id = Integer.parseInt(bookStatusIdFieldTxt);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove the book with id of " + id, ButtonType.YES, ButtonType.NO).showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                statusManager.delete(Integer.parseInt(bookStatusIdFieldTxt));
                openAlert(Alert.AlertType.INFORMATION, "Deletion success|choosen book successfully removed from your book list.");
                refreshAll();
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

    private void refreshAll() {
        try {
            if (user != null) {
                statusTableView.setItems(FXCollections.observableList(statusManager.searchByUser(user)));
            }
            statusTableView.refresh();
        }
        catch (MyBookListException exception) {
            if (!exception.getMessage().equals("Database is empty - no books found.")) {
                openAlert(Alert.AlertType.ERROR, "Unexpected error occured|" + exception.getMessage());
                System.exit(-1);
            }
        }
    }
}
