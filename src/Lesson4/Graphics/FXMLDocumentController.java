package Lesson4.Graphics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class FXMLDocumentController implements Initializable {

    @FXML
    private ListView myUserList;
    @FXML
    private javafx.scene.control.Button buttonSendMessage;
    @FXML
    private TextArea myTextField;
    @FXML
    private TextField myTextType;

    ObservableList<String> users = FXCollections.observableArrayList("User1", "User2", "User3");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myUserList.setItems(users);
    }

    @FXML
    public void handleButtonAction(ActionEvent e) {
        String text = myTextType.getText();
        String userName = myUserList.getSelectionModel().getSelectedItems().toString();
        if (!text.isEmpty()){
            myTextField.appendText(userName +  ": " + text + "\n");
            myTextType.clear();
        }
        e.consume();
    }

    @FXML
    public void enterTyped(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)){
            String text = myTextType.getText();
            String userName = myUserList.getSelectionModel().getSelectedItems().toString();
            if (!text.isEmpty()){
                myTextField.appendText(userName +  ": " + text + "\n");
                myTextType.clear();
            }
            e.consume();
        }
    }


}
