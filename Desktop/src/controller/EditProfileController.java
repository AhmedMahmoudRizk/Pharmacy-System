/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class EditProfileController implements Initializable {

    @FXML
    private JFXTextField SUEmailField, UserField, FnameField, LnameField, PhoneField;

    @FXML
    private JFXPasswordField SUPasswordField;
    @FXML
    AnchorPane EditProfilePane;

    @FXML
    Label EditProfileErrorLabel;

    private UserQuery userQuery;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            EditProfileErrorLabel.setVisible(false);
            myconnector con = new myconnector();
            userQuery = userQuery.getInstance(con);
        } catch (Exception ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    @FXML
//    private void fetchData() {
//        try {
//            // TODO
//            //System.out.println("AAAAAAAAAAAA"+SUEmailField.getText());
//
//            ResultSet rs = userQuery.getUserData(SUEmailField.getText());
//            rs.next();
//            UserField.setText(rs.getString("userName"));
//            FnameField.setText(rs.getString("firstName"));
//            LnameField.setText(rs.getString("lastName"));
//            PhoneField.setText(rs.getString("phoneNumber"));
//            SUPasswordField.setText(userQuery.getPassword(SUEmailField.getText()));
//
//        } catch (SQLException ex) {
//            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) EditProfilePane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleConfirm() {
        String execMsg = userQuery.editPersonalInformation(SUEmailField.getText(), UserField.getText(),
                SUPasswordField.getText(), FnameField.getText(), LnameField.getText(),
                PhoneField.getText());
        System.out.println(execMsg);
        switch (execMsg) {
            case "Parameter Error":
                EditProfileErrorLabel.setText("All Fields Are Required");
                EditProfileErrorLabel.setVisible(true);
                break;
            default:
                Stage stage = (Stage) EditProfilePane.getScene().getWindow();
                stage.close();
                break;

        }
    }
}
