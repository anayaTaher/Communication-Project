package sample;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    private VBox vBox;
    private Parent fxml;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myMethod();
    }

    public void open_receiver() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vBox);
        t.setToX(0);
        t.play();
        t.setOnFinished((e) -> {

            try {
                fxml = FXMLLoader.load(getClass().getResource("Receiver.fxml"));
                vBox.getChildren().removeAll();
                vBox.getChildren().setAll(fxml);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }

    public void open_sender() {
        myMethod();
    }

    private void myMethod() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vBox);
        t.setToX(vBox.getLayoutX() * 20);
        t.play();
        t.setOnFinished((e) -> {

            try {
                fxml = FXMLLoader.load(getClass().getResource("Sender.fxml"));
                vBox.getChildren().removeAll();
                vBox.getChildren().setAll(fxml);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }
}
