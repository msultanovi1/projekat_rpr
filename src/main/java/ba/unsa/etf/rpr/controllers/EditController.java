package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.bussines.StatusManager;
import ba.unsa.etf.rpr.bussines.UserManager;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EditController extends WindowController{


    private final UserManager userManager = UserManager.getInstance();
    private final StatusManager statusManager = StatusManager.getInstance();
    private final User user;
    private List<Status> statuses;

    private String[] readingStatus = {"read", "reading", "to be read", "on hold"};

    public ComboBox<Integer> idEditBoxField;
    public TextField bookNameEditBoxField;
    public TextField UINEditField;
    public TextField authorEditField;
    public TextField genreEditField;
    public ComboBox<String> statusEditBoxField;
    public ComboBox<Double> scoreEditBoxField;

    public Button buttonConfirmEdit;
    public Button buttonCloseEdit;

    public TextField idAddField;
    public ComboBox<String> bookNameAddBoxField;
    public TextField UINAddField;
    public TextField authorAddField;
    public TextField genreAddField;
    public ComboBox<String> statusAddBoxField;
    public ComboBox<Double> scoreAddBoxField;

    public Button buttonAddNewBook;
    public Button buttonConfirmAdd;
    public Button buttonCloseAdd;

    public EditController(User user) {
        this.user = user;
        try {
            statuses = statusManager.searchByUser(user);
        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Unexpected error occured|" + exception.getMessage());
        }
    }

    @FXML
    public void initialize(){
        idAddField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idAddField.setText(newValue.replaceAll("\\D", ""));
            }
        });
        idEditBoxField.setItems(FXCollections.observableList(statuses.stream().map(Status::getId).collect(toList())));
        statusAddBoxField.setItems(FXCollections.observableList(statuses.stream().map(Status::getStatus).collect(toList())));
    }

    public void confirmAdd(ActionEvent actionEvent) {
    }

    public void closeAdd(ActionEvent actionEvent) {
        closeWindow(buttonCloseAdd);
    }

    public void addNewBook(ActionEvent actionEvent) {
        openWindow("Add new book", "/fxml/addNewBookScreen.fxml", new ChangeController(user), (Stage)buttonAddNewBook.getScene().getWindow(), true);
    }

    public void confirmEdit(ActionEvent actionEvent) {
    }

    public void closeEdit(ActionEvent actionEvent) {
        closeWindow(buttonCloseEdit);
    }
}
