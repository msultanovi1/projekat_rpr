package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controllers.LogInController;
import ba.unsa.etf.rpr.controllers.WindowController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A full representation of the created MyBookList application through the use of the Graphical User Interface (GUI)
 * @author Minela Sultanović
 */

public class AppFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        WindowController.openWindow("MyBookList LogIn", "/fxml/loginScreen.fxml", new LogInController(), null, false);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
