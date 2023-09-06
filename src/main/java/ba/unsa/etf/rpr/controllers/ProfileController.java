package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The JavaFX controller for the overview of the users profile with some options and functionalities
 */
public class ProfileController extends WindowController{

    private final UserManager userManager = UserManager.getInstance();

    private User user;

    public Label username;

    public Button buttonLogOut;

    public Button buttonBookList;

    public Button buttonFriendList;

    public Button buttonChange;

    public TextArea aboutMe;

    /**
     * A constructor that initializes the user that has invoked the opening of this window
     * @param user the user that requested the opening of this window
     */
    public ProfileController(User user){
        this.user = user;
    }

    /**
     * The method that gets called right before the opening of this window
     * Its purpose is to initialize all and restrict some JavaFX components shown in the created window
     */
    @FXML
    public void initialize(){
        username.setText(user.getName());
        aboutMe.setText(user.getAboutMe());
    }

    /**
     * The method that is responsible for logging the user out of the system
     * Its only purpose is to call their parent's method openWindow which simultaneously closes the current window and opens up the login window again
     */
    @FXML
    public void userLogOut(ActionEvent actionEvent) {
        openWindow("LogIn", "/fxml/loginScreen.fxml", new LogInController(), (Stage)buttonLogOut.getScene().getWindow(), false);
    }

    /**
     * Clicking on the corresponding button opens up a new window where the user can change their name and/or password
     * After closing the newly opened window an alert will pop off, indicating a successful or unnecessary change in their credentials
     */
    @FXML
    public void changeNamePassword(ActionEvent actionEvent) {
        String name = user.getName();
        openWindow("Change profile info", "/fxml/changeScreen.fxml", new ChangeController(user), (Stage)buttonChange.getScene().getWindow(), true);
        if (user.getName().equals(name)) {
            openAlert(Alert.AlertType.INFORMATION, "Update unnecessary. No changes in credentials detected.");
        }
        else {
            openAlert(Alert.AlertType.INFORMATION, "Update successful. Successfully changed credentials.");
        }
    }

    /**
     * Clicking on the corresponding button closes profile window and opens up a new window for users book list
     * @param actionEvent
     */
    public void openBookList(ActionEvent actionEvent) {
        openWindow("Book List", "/fxml/bookListScreen.fxml", new BookListController(user), (Stage)buttonBookList.getScene().getWindow(), false);
    }

    /**
     * Clicking on the corresponding button closes profile window and opens up a new window for users friend list
     * @param actionEvent
     */
    public void openFriendList(ActionEvent actionEvent) {
        openAlert(Alert.AlertType.INFORMATION, "We still don't have friend list :(");
    }
}
