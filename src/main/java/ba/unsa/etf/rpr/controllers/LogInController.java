package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.bussines.UserManager;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

public class LogInController extends WindowController{


    @FXML
    public Button buttonLogIn;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Label message;

    public LogInController(){}


    public void userLogIn(){
        try {
            openWindow("Profile", "/fxml/profileScreen.fxml", new ProfileController(new UserManager().getByNameAndPassword(username.getText().trim(), password.getText().trim())), (Stage)buttonLogIn.getScene().getWindow(), false);
        }
        catch (MyBookListException exception) {
            openAlert(Alert.AlertType.ERROR, exception.getMessage());
        }
    }

}


