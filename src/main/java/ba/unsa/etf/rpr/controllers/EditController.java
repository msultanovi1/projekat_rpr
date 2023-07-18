package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.bussines.StatusManager;
import ba.unsa.etf.rpr.bussines.UserManager;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class EditController extends WindowController{


    private final UserManager userManager = UserManager.getInstance();
    private final StatusManager statusManager = StatusManager.getInstance();
    private final User user;
    private List<Status> statuses;

    public TextField idStatusEditField;
    public TextField bookNameEditField;
    public TextField UINEditField;
    public TextField authorEditField;
    public TextField genreEditField;
    public ComboBox<String> statusEditField;
    public ComboBox<Double> scoreEditField;

    public Button buttonConfirmEdit;
    public Button buttonCloseEdit;

    public EditController(User user) {
        this.user = user;
    }

    @FXML
    public void closeEditScreen() {
        closeWindow(buttonCloseEdit);
    }

}
