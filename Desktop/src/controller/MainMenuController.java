/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXPasswordField;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class MainMenuController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Pane LogOut;

    @FXML
    private Label CurrEmailLabel;

    @FXML
    private Pane SearchDrug;
    private myconnector con;
    private UserQuery userQuery;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            con = new myconnector();
            userQuery = userQuery.getInstance(con);
        } catch (Exception ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void LogOutHandler() throws IOException {
        Pane root = new Pane();
        Pane root2 = FXMLLoader.load(getClass().getResource("/view/StartScreen.fxml"));
        Pane root3 = FXMLLoader.load(getClass().getResource("/view/HeaderBar.fxml"));
        Pane Sign = (Pane) root2.lookup("#SignUpBox");
        Sign.setVisible(false);
        //root.getChildren().addAll(root1,root2,root3); 
        root.getChildren().addAll(root2, root3);
        Scene scene = new Scene(root, 1229, 778);
        //Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        Stage stage = (Stage) LogOut.getScene().getWindow();

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

    }

    @FXML
    private void SearchDrugHandler() throws IOException {
        Pane root = new Pane();
        Pane root2 = FXMLLoader.load(getClass().getResource("/view/SearchForDrugs.fxml"));
        Pane root3 = FXMLLoader.load(getClass().getResource("/view/HeaderBar.fxml"));

        root.getChildren().addAll(root2, root3);

        Scene scene = new Scene(root);
        //Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        //Stage stage = (Stage)SearchBook.getScene().getWindow();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Label EmailLabel = (Label) root.lookup("#CurrEmailLabel");
        String Emailstr = CurrEmailLabel.getText().substring(8);
        System.out.println(Emailstr);
        EmailLabel.setText(EmailLabel.getText() + " " + Emailstr);
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
    }

    @FXML
    private void EditProfileHandler() throws IOException {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/view/EditProfile.fxml"));

            Scene scene = new Scene(root);

            TextField emailField = (TextField) root.lookup("#SUEmailField");
            TextField UserField = (TextField) root.lookup("#UserField");
            TextField FnameField = (TextField) root.lookup("#FnameField");
            TextField LnameField = (TextField) root.lookup("#LnameField");
            TextField PhoneField = (TextField) root.lookup("#PhoneField");
            JFXPasswordField SUPasswordField = (JFXPasswordField) root.lookup("#SUPasswordField");

            emailField.setText(CurrEmailLabel.getText().substring(8));

            ResultSet rs = userQuery.getUserData(emailField.getText());
            rs.next();
            UserField.setText(rs.getString("userName"));
            FnameField.setText(rs.getString("firstName"));
            LnameField.setText(rs.getString("lastName"));
            PhoneField.setText(rs.getString("phoneNumber"));
            SUPasswordField.setText(userQuery.getPassword(emailField.getText()));
            //Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

            //Stage stage = (Stage)SearchBook.getScene().getWindow();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

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
        } catch (SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AddMedicineHandler() throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("/view/AddDrug.fxml"));

        Scene scene = new Scene(root);
        //Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        //Stage stage = (Stage)SearchBook.getScene().getWindow();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

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
    }

    @FXML
    private void showReportsHandler() throws IOException {
        Pane root = new Pane();
        Pane root1 = FXMLLoader.load(getClass().getResource("/view/ReportsPage.fxml"));
        Pane root3 = FXMLLoader.load(getClass().getResource("/view/HeaderBar.fxml"));

        root.getChildren().addAll(root1, root3);
        Scene scene = new Scene(root, root1.getPrefWidth(), root1.getPrefHeight());

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

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
    }
}
