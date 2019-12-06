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

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class EditDrugController implements Initializable {

    @FXML
    ChoiceBox medType;

    @FXML
    TextField IDField, DrugName, ClassificationField, ConcetrationField, PriceField, AmountField;

    @FXML
    AnchorPane EditMedicinePane;

    @FXML
    Label EditMedicineErrorLabel;
    private myconnector con;
    private UserQuery userQuery;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            EditMedicineErrorLabel.setVisible(false);
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
    public void handleConfirm() {
        String execMsg = userQuery.editDrug(IDField.getText(), DrugName.getText(),
                ClassificationField.getText(), ConcetrationField.getText(), PriceField.getText(),
                AmountField.getText(), medType.getSelectionModel().getSelectedItem().toString());

        switch (execMsg) {
            case "Parameter Error":
                EditMedicineErrorLabel.setText("one of the parameter is not correct");
                EditMedicineErrorLabel.setVisible(true);
                break;

            case "Error Occured During Transaction":
                EditMedicineErrorLabel.setText("Error Occured During Transaction");
                EditMedicineErrorLabel.setVisible(true);
                break;

            default:
                Stage stage = (Stage) EditMedicinePane.getScene().getWindow();
                stage.close();
                break;
        }
    }



@FXML
public void handleClose(){
        Stage stage = (Stage) EditMedicinePane.getScene().getWindow();
        stage.close();
    }
}
