package Lesson4;

import Lesson1.Action;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Lesson4/Graphics/scene.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Chat Window");
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Bye!");
            }
        });
        stage.show();
    }
}
