package Lesson4;

import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class FXMLDocumentController {
    @FXML
    private Label label;

    @FXML
    private Button button;

    @FXML
    public void handleButtonAction(ActionEvent event){
        System.out.println("You clicked me!");
        label.setText("Hello World");
    }

    @FXML
    public void mouseEnterAction(MouseEvent mouseEvent){
        System.out.println("Mouse was entered");
    }
}
