package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.bussines.UserManager;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ChangeController extends WindowController{

    private final UserManager userManager = UserManager.getInstance();
    private final User user;

    private String previousUsername;
    private String newUsername;
    private String previousPassword;
    private String newPassword;
    private String confirmedPassword;
    public TextField previousUsernameField;
    public TextField newUsernameField;
    public PasswordField previousPasswordField;
    public PasswordField newPasswordField;
    public PasswordField confirmedPasswordField;
    public Button buttonConfirm;
    public Button buttonCancel;

    public ChangeController(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        previousUsernameField.setText(user.getName());
    }

    private void loadFields() {
        previousUsername = previousUsernameField.getText().trim();
        previousPassword = previousPasswordField.getText().trim();
        newUsername = newUsernameField.getText().trim();
        newPassword = newPasswordField.getText().trim();
        confirmedPassword = confirmedPasswordField.getText().trim();
    }

    private void validateFields() throws MyBookListException {
        loadFields();
        if (previousUsername.isEmpty()) {
            throw new MyBookListException("Old name field left empty.");
        }
        if (previousPassword.isEmpty()) {
            throw new MyBookListException("Old password field left empty.");
        }
        if (!user.getName().equals(previousUsername) || !user.getPassword().equals(previousPassword)) {
            throw new MyBookListException("Incorrect input of old username or password!");
        }
        if (newUsername.isEmpty()) {
            if (newPassword.isEmpty()) {
                if (confirmedPassword.isEmpty()) {
                    throw new MyBookListException("All new fields are left empty.");
                }
                else {
                    throw new MyBookListException("New password field left empty - unentered password cannot be confirmed.");
                }
            }
            newUsername = previousUsername;
        }
        else if (newPassword.isEmpty() && !confirmedPassword.isEmpty() || !newPassword.isEmpty() && confirmedPassword.isEmpty()) {
            throw new MyBookListException("New and confirmation password fields must be left empty if you don't wish to change password.");
        }
        if (newPassword.isEmpty()) {
            newPassword = previousPassword;
        }
        else if (!newPassword.equals(confirmedPassword)) {
            throw new MyBookListException("New password not confirmed correctly.");
        }
    }

    public void closeChangeScreen(ActionEvent actionEvent) {
        closeWindow(buttonCancel);
    }

    public void changeNamePassword(ActionEvent actionEvent) {
        try {
            validateFields();
            try {
                user.setName(newUsername);
                user.setPassword(newPassword);
                userManager.updateNameAndPassword(user);
                closeWindow(buttonConfirm);
            }
            catch (MyBookListException exception) {
                user.setName(previousUsername);
                user.setPassword(previousPassword);
                throw exception;
            }
        }
        catch (MyBookListException exception) {
            openAlert(Alert.AlertType.ERROR, "Field problems|" + exception.getMessage());
        }
    }


}
