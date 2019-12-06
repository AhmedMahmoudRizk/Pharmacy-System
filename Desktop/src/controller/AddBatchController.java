/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class AddBatchController implements Initializable {
    
    @FXML
    TextField BatchID, QuantityField;
    
    @FXML
    Label drugIDLabel;
    
    @FXML
    DatePicker PD, ED;
    
    @FXML
    AnchorPane AddBatchPane;
    
    @FXML
    Label AddBatchErrorLabel;
    private myconnector con;
    private UserQuery userQuery;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            AddBatchErrorLabel.setVisible(false);
            con = new myconnector();
            userQuery = userQuery.getInstance(con);
        } catch (Exception ex) {
            Logger.getLogger(AddBatchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleClose() {
        Stage stage = (Stage) AddBatchPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleConfirm() {
        String execMsg = "";
        if (checkDates(ED.getValue(), PD.getValue())) {
            try {
                execMsg = userQuery.addBatch(drugIDLabel.getText(), BatchID.getText(), QuantityField.getText(), ED.getValue().toString(), PD.getValue().toString());
            } catch (SQLException ex) {
                Logger.getLogger(AddBatchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            
            execMsg = "Parameter Error";
        }
        
        if (execMsg.equals("")) {
            Stage stage = (Stage) AddBatchPane.getScene().getWindow();
            stage.close();
        } else {
            AddBatchErrorLabel.setVisible(true);
            AddBatchErrorLabel.setText(execMsg);
        }
    }
    
    private boolean checkDates(LocalDate expDate, LocalDate prodDate) {
        
        boolean validDates = false;
        if (expDate != null && prodDate != null) {
            if (!expDate.toString().equals("") && !prodDate.toString().equals("")) {
                if (java.sql.Date.valueOf(expDate).after(java.sql.Date.valueOf(prodDate))) {
                    java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    if (!java.sql.Date.valueOf(expDate).equals(currentDate) && !java.sql.Date.valueOf(expDate).before(currentDate)) {
                        if (!java.sql.Date.valueOf(prodDate).after(currentDate)) {
                            validDates = true;
                        }
                    }
                }
            }
        }
        return validDates;
        
    }
}
