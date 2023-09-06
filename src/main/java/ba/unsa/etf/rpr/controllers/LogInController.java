package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


/**
 * The JavaFX controller for logging in users to the application
 */
public class LogInController extends WindowController{


    @FXML
    public Button buttonLogIn;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    public LogInController(){}

    /**
     * The method that is responsible for logging the user into the system
     */
    public void userLogIn(){
        try {
            openWindow("Profile", "/fxml/profileScreen.fxml", new ProfileController(new UserManager().getByNameAndPassword(username.getText().trim(), password.getText().trim())), (Stage)buttonLogIn.getScene().getWindow(), false);
        }
        catch (MyBookListException exception) {
            openAlert(Alert.AlertType.ERROR, exception.getMessage());
        }
    }

}


