/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class StartScreenController implements Initializable {

    @FXML
    private JFXButton SignBtn;
    @FXML
    private Pane LoginPane;
    @FXML
    private Pane SignInBox;

    @FXML
    private Pane SignUpBox;

    @FXML
    private Label SignInErrorLabel, SignUpErrorLabel;

    @FXML
    private JFXTextField SIEmailField, SUEmailField, UserField, FnameField, LnameField, PhoneField;

    @FXML
    private JFXPasswordField SIPasswordField, SUPasswordField;
    private double xOffset = 0;
    private double yOffset = 0;
    private UserQuery userQuery;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            // TODO
            myconnector con = new myconnector();
            userQuery = userQuery.getInstance(con);

        } catch (Exception ex) {
            Logger.getLogger(StartScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SignInErrorLabel.setVisible(false);
        SignUpErrorLabel.setVisible(false);
    }

    @FXML
    private void ToggleBox() {
        if (SignBtn.getText().equals("Register Now")) {
            SignInBox.setVisible(false);
            SignUpBox.setVisible(true);
            SignBtn.setText("Log In");
        } else {
            SignInBox.setVisible(true);
            SignUpBox.setVisible(false);
            SignBtn.setText("Register Now");
        }
    }

    @FXML
    private void Sign_In() throws Exception {
        String Email = SIEmailField.getText();

        boolean success = userQuery.logIn(SIEmailField.getText(), SIPasswordField.getText());
        if(success)
        { 
        show_UserPage(Email, "Customer");
        }
        else
        { 
            SignInErrorLabel.setVisible(true);

        }
    }

    @FXML
    private void Sign_Up() throws Exception {
        String Email = SUEmailField.getText();
        String execMsg = userQuery.createUser(SUEmailField.getText(),UserField.getText(),
                SUPasswordField.getText(),FnameField.getText(),LnameField.getText()
                ,PhoneField.getText());
        System.out.println(execMsg);
       switch (execMsg) {
           case "Parameter Error":
               SignUpErrorLabel.setText("All Fields Are Required");
               SignUpErrorLabel.setVisible(true);
               break;
           case "Email Error":
               SignUpErrorLabel.setText("Email Already Exists");
               SignUpErrorLabel.setVisible(true);
               break;
           default:
        show_UserPage(Email, "Customer"); //editable
                break;
       }
    }

    private void show_UserPage(String Email, String customer) {
        try {
            Pane root = new Pane();
            Pane root1 = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Pane root3 = FXMLLoader.load(getClass().getResource("/view/HeaderBar.fxml"));

            root.getChildren().addAll(root1, root3);
            Scene scene = new Scene(root, 1229, 600);
            Stage stage = (Stage) SignInErrorLabel.getScene().getWindow();

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            stage.setScene(scene);
            stage.show();
            Label EmailLabel = (Label) root1.lookup("#CurrEmailLabel");
            EmailLabel.setText(EmailLabel.getText() + " " + Email);
        } catch (IOException ex) {
            Logger.getLogger(StartScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
