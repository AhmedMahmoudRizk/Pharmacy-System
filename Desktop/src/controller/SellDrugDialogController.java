/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class SellDrugDialogController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    AnchorPane DrugSellPane;

    @FXML
    Label drugIDLabel;

    @FXML
    JFXTextField quantityField;

    UserQuery userQuery;
    myconnector con;

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
            Logger.getLogger(SellDrugDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleClose() {
        Stage stage = (Stage) DrugSellPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleConfirm() {

        try {
            String response = userQuery.reduceQuantity(userQuery.getEmail(), drugIDLabel.getText(), quantityField.getText());
            if (!response.equals("")) {
                showAlertWithoutHeaderText(response);
            } else {
                Stage stage = (Stage) DrugSellPane.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrugActivitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Show a Information Alert without Header Text
    private void showAlertWithoutHeaderText(String response) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.setTitle("Test Connection");
        alert.initStyle(StageStyle.UTILITY);
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(response);

        alert.showAndWait();

    }

}
