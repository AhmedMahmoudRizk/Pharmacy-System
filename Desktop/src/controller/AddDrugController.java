/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class AddDrugController implements Initializable {

    @FXML
    ChoiceBox medType;

    @FXML
    TextField IDField, DrugName, ClassificationField, ConcetrationField, PriceField, AmountField, BatchID, QuantityField;

    @FXML
    DatePicker PD, ED;

    @FXML
    AnchorPane AddMedicinePane;

    @FXML
    Label AddMedicineErrorLabel;
    private myconnector con;
    private UserQuery userQuery;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AddMedicineErrorLabel.setVisible(false);
            medType.setItems(FXCollections.observableArrayList("Any",
                    "Liquid", "Tablet", "Capsules", "Topical medicine", "Suppositories", "Drops", "Inhalers", "Injections", "patches", "Buccal & sublingual") //used "setItems" not "new ChoiceBox" because existing FXML items shouldn't be initialized
            );
            medType.getSelectionModel().select(0); //select Any by Default
            con = new myconnector();
            userQuery = userQuery.getInstance(con);
        } catch (Exception ex) {
            Logger.getLogger(AddDrugController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) AddMedicinePane.getScene().getWindow();
        stage.close();
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

    @FXML
    private void handleConfirm() {
        String execMsg = "";
        //if (!PD.getValue().toString().equals("") && !PD.getValue().toString().equals("") && java.sql.Date.valueOf(ED.getValue()).after(java.sql.Date.valueOf(PD.getValue()))) {

        if (checkDates(ED.getValue(), PD.getValue())) {
            execMsg = userQuery.addNewDrug(IDField.getText(), DrugName.getText(),
                    ClassificationField.getText(), ConcetrationField.getText(), PriceField.getText(),
                    AmountField.getText(), medType.getSelectionModel().getSelectedItem().toString(), BatchID.getText(),
                    ED.getValue().toString(), QuantityField.getText(), PD.getValue().toString());
            System.out.println(execMsg);
        } else {
            execMsg = "Parameter Error";
        }

        switch (execMsg) {
            case "Parameter Error":
                AddMedicineErrorLabel.setText("A Parameter Isn't Correct!");
                AddMedicineErrorLabel.setVisible(true);
                break;
            case "DRUG ID already exist":
                AddMedicineErrorLabel.setText("DRUG ID already exists");
                AddMedicineErrorLabel.setVisible(true);
                break;

            default:
                Stage stage = (Stage) AddMedicinePane.getScene().getWindow();
                stage.close();
                break;
        }
    }
}
