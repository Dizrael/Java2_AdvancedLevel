package Lesson4;

import javafx.fxml.FXML;

import javax.swing.text.html.ListView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class FXMLDocumentController {

    @FXML
    private Button buttonSendMessage;

    @FXML
    private TextArea myTextArea;

    @FXML
    private TextField myTextType;

    @FXML
    private ListView myUserList;

    @FXML
    private Label label;

    @FXML
    public void handleButtonAction(ActionEvent event){
        System.out.println("You clicked me!");
    }

    @FXML
    public void mouseEnterAction(MouseEvent mouseEvent){
        System.out.println("Mouse was entered");
    }
}
