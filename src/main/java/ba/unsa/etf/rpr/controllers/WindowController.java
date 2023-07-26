package ba.unsa.etf.rpr.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class WindowController {

    public static void openWindow(String title, String file, Object controller, Stage previousStage, boolean modality) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource(file));
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            if (modality) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null));
                stage.showAndWait();
            }
            else {
                if (previousStage != null) {
                    previousStage.close();
                }
                stage.show();
            }
        }
        catch (IOException exception) {
            //openAlert(Alert.AlertType.ERROR, "Unable to open requested window|" + exception.getMessage());
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    public static void closeWindow(Node node) {
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    public static void openAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        switch (alertType) {
            case ERROR -> alert.setTitle("Error");
            case INFORMATION -> alert.setTitle("Helpful message");
        }
        alert.setHeaderText(message.substring(0, message.indexOf("|")));
        alert.setContentText(message.substring(message.indexOf("|") + 1));
        alert.initModality(Modality.WINDOW_MODAL);
        long numberOfWindows = Stage.getWindows().stream().filter(Window::isShowing).count();
        alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing).skip(numberOfWindows - 1).findFirst().orElse(null));
        //alert.showAndWait();
        System.out.println(message);
    }
}
