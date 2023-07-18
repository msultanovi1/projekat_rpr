package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.bussines.UserManager;
import ba.unsa.etf.rpr.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class ProfileController extends WindowController{

    private final UserManager userManager = UserManager.getInstance();

    private User user;
    @FXML
    public Label username;
    @FXML
    public Button buttonLogOut;
    @FXML
    public Button buttonBookList;
    @FXML
    public Button buttonFriendList;
    @FXML
    public Button buttonChange;
    @FXML
    public TextField aboutMe;
    

    public ProfileController(User user){
        this.user = user;
    }

    public void initialize(){
        username.setText(user.getName());
    }

    @FXML
    public void userLogOut(ActionEvent actionEvent) {
        openWindow("LogIn", "/fxml/loginScreen.fxml", new LogInController(), (Stage)buttonLogOut.getScene().getWindow(), false);
    }

    @FXML
    public void changeNamePassword(ActionEvent actionEvent) {
        String name = user.getName();
        openWindow("Change profile info", "/fxml/changeScreen.fxml", new ChangeController(user), (Stage)buttonChange.getScene().getWindow(), true);
        if (user.getName().equals(name)) {
            openAlert(Alert.AlertType.INFORMATION, "Update unnecessary|No changes in credentials detected.");
        }
        else {
            openAlert(Alert.AlertType.INFORMATION, "Update successful|Successfully changed credentials.");
        }
    }

    public void openBookList(ActionEvent actionEvent) {
        openWindow("LogIn", "/fxml/bookListScreen.fxml", new BookListController(user), (Stage)buttonBookList.getScene().getWindow(), false);
    }

    public void openFriendList(ActionEvent actionEvent) {
        openAlert(Alert.AlertType.INFORMATION, "We still don't have friend list :(");
    }
}
