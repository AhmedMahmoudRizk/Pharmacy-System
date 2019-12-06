/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import DBManagementLayer.UserQuery;
import DBManagementLayer.myconnector;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class DrugActivitiesController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    AnchorPane DrugActivityPane;

    @FXML
    Label drugIDLabel;
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
            Logger.getLogger(DrugActivitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleClose() {
        Stage stage = (Stage) DrugActivityPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleEdit() throws IOException {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/view/EditDrug.fxml"));

            Scene scene = new Scene(root);

            ResultSet rs = userQuery.getDrugData(drugIDLabel.getText());
            rs.next();
            TextField drugID = (TextField) root.lookup("#IDField");
            TextField drugName = (TextField) root.lookup("#DrugName");
            TextField classification = (TextField) root.lookup("#ClassificationField");
            TextField concentration = (TextField) root.lookup("#ConcetrationField");
            TextField price = (TextField) root.lookup("#PriceField");
            TextField amount = (TextField) root.lookup("#AmountField");
            ChoiceBox type = (ChoiceBox) root.lookup("#medType");

            drugID.setText(rs.getString("drugID"));
            drugName.setText(rs.getString("drugName"));
            classification.setText(rs.getString("classification"));
            concentration.setText(rs.getString("concentration"));
            price.setText(rs.getString("price"));
            amount.setText(rs.getString("amount"));
            type.setValue(rs.getString("type"));

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
            Logger.getLogger(DrugActivitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void handleDelete() {

        try {
            userQuery.deleteDrug(drugIDLabel.getText());
            Stage stage = (Stage) DrugActivityPane.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugActivitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleConfirmOrder() {
        try {
            boolean orderConfirmed = false;

            orderConfirmed = userQuery.confirmOdrer(drugIDLabel.getText());

            if (orderConfirmed) {
                showAlertWithoutHeaderText(AlertType.INFORMATION);
            } else {
                showAlertWithoutHeaderText(AlertType.ERROR);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrugActivitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void handleSell() {

        try {

            Pane root2 = FXMLLoader.load(getClass().getResource("/view/SellDrugDialog.fxml"));
            Label drugID = (Label) root2.lookup("#drugIDLabel");
            drugID.setText(drugIDLabel.getText());

            Scene scene = new Scene(root2);
            Stage stage = (Stage) DrugActivityPane.getScene().getWindow();

            root2.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root2.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DrugActivitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void handleAddBatch() {

        try {

            Pane root2 = FXMLLoader.load(getClass().getResource("/view/AddBatch.fxml"));
            Label drugID = (Label) root2.lookup("#drugIDLabel");
            drugID.setText(drugIDLabel.getText());

            Scene scene = new Scene(root2);
            Stage stage = (Stage) DrugActivityPane.getScene().getWindow();

            root2.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root2.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DrugActivitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Show a Information Alert without Header Text
    private void showAlertWithoutHeaderText(AlertType type) {
        Alert alert = new Alert(type);
        //alert.setTitle("Test Connection");
        alert.initStyle(StageStyle.UTILITY);
        // Header Text: null
        alert.setHeaderText(null);
        if (type.compareTo(AlertType.ERROR) == 0) {
            alert.setContentText("No Order To Confirm!");
        } else {
            alert.setGraphic(new ImageView(new Image(this.getClass().getResource("/images/confirmed.png").toString())));
            alert.setContentText("Order Confirmed");
        }
        alert.showAndWait();

    }
}
