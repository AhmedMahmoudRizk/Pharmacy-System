/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import DBManagementLayer.myconnector;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class SearchForDrugsController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    ChoiceBox medType;
    @FXML
    JFXTextField drugIDBox, nameBox, classificationBox, concentrationBox, amountBox, priceBox;
    @FXML
    TableView TableView;
    @FXML
    AnchorPane SearchPane;
    @FXML
    Label CurrEmailLabel;
    private myconnector con;
    private ObservableList<ObservableList> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            medType.setItems(FXCollections.observableArrayList("Any",
                    "Liquid", "Tablet", "Capsules", "Topical medicine", "Suppositories", "Drops", "Inhalers", "Injections", "patches", "Buccal & sublingual") //used "setItems" not "new ChoiceBox" because existing FXML items shouldn't be initialized
            );
            medType.getSelectionModel().select(0); //select Any by Default
            con = new myconnector();
            data = FXCollections.observableArrayList();
        } catch (Exception ex) {
            Logger.getLogger(SearchForDrugsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    @FXML 
private void onSearch(){
          if(!TableView.getItems().isEmpty())
            TableView.getItems().clear();
          ArrayList<String> drugAttr = new ArrayList<>();
          
          /* get attributes from text fields */
          String attrValue;
          attrValue = drugIDBox.getText();
              drugAttr.add(attrValue);
          attrValue = nameBox.getText();
              drugAttr.add(attrValue);
          attrValue = classificationBox.getText();
              drugAttr.add(attrValue);
          attrValue = concentrationBox.getText();
              drugAttr.add(attrValue);
          attrValue = priceBox.getText();
              drugAttr.add(attrValue);
          attrValue = amountBox.getText();
              drugAttr.add(attrValue);
          
          attrValue = medType.getSelectionModel().getSelectedItem().toString();
          drugAttr.add(attrValue);
          int lastValidIndex = -1; //determine if current attr is the last non empty value so to not put "AND" after it in select query
          try{
            for(int i=0 ; i<drugAttr.size();i++)
            {
               if(i == drugAttr.size()-1)
                {
                    if(!drugAttr.get(i).equals("Any"))
                    {
                        lastValidIndex = i;
                        continue;
                    }
                }
               if(!drugAttr.get(i).equals("") && !drugAttr.get(i).equals("Any"))
                {
                    lastValidIndex = i;
                }
            }
              System.out.println(drugAttr);
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT drugs.drugID,drugName,classification,concentration,price,amount,type,batchID,producedDate,expiredDate,quantity from Drugs,DrugBatch" 
                    + (lastValidIndex == -1?" Where ":" Where "
                    + (drugAttr.get(0).equals("")?"":"drugs.drugID = \'"+drugAttr.get(0)
//                    + ((lastValidIndex == 0 )?"":" AND "))
                    +"\' AND ")
                    
                    + (drugAttr.get(1).equals("")?"":"drugName = \'"+drugAttr.get(1)
//                    + ((lastValidIndex == 1)?"":" AND "))
                    +"\' AND ")
                    + (drugAttr.get(2).equals("")?"":"classification = \'"+drugAttr.get(2)
//                    + ((lastValidIndex == 2)?"":" AND "))
                    +"\' AND ")
                    + (drugAttr.get(3).equals("")?"":"concentration = "+drugAttr.get(3)
//                    + ((lastValidIndex == 3)?"":" AND "))
                    +" AND ")
                    + (drugAttr.get(4).equals("")?"":"price = "+drugAttr.get(4)
//                    + ((lastValidIndex == 4)?"":" AND "))
                    +" AND ")
                    + (drugAttr.get(5).equals("")?"":"amount = "+drugAttr.get(5)
//                    + ((lastValidIndex == 5)?"":" AND ")) 
                   +" AND ")
                    + (drugAttr.get(6).equals("Any")?"":"type = \'"+drugAttr.get(6)
//                    + ((lastValidIndex == 6)?"":" AND "))
                    +"\' AND ")
                    )+" Drugs.drugID = DrugBatch.drugID;";
            
            
              System.out.println(SQL);
              /**********************************
               * TABLE COLUMN ADDED DYNAMICALLY *
               **********************************/
              try ( //ResultSet
                      ResultSet rs = con.stmt.executeQuery(SQL)) {
                  /**********************************
                   * TABLE COLUMN ADDED DYNAMICALLY *
                   **********************************/
                  for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                      //We are using non property style for making dynamic table
                      final int j = i;
                      TableColumn col = (TableColumn) TableView.getColumns().get(i);
                      col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                          public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                              return new SimpleStringProperty(param.getValue().get(j).toString());
                          }
                      });
                      
                      //TableView.getColumns().addAll(col);
                    //  System.out.println("Column ["+ i +"] ");
                  }
                  
                  /********************************
                   * Data added to ObservableList *
                   ********************************/
                  while(rs.next()){
                      //Iterate Row
                      ObservableList<String> row = FXCollections.observableArrayList();
                      for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                          //Iterate Column
                          row.add(rs.getString(i));
                      }
                      //System.out.println("Row [1] added "+row );
                      data.add(row);
                  }
                  
                  //FINALLY ADDED TO TableView
                  
                  TableView.setItems(data);
                  TableView.setRowFactory(e -> {
                  TableRow<ObservableList<String>> row = new TableRow<>();
                  row.setOnMouseClicked(event -> {
                  if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ObservableList<String> rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.toString());
                      try {
                          show_DrugActivity(rowData);
                      } catch (IOException ex) {
                          Logger.getLogger(SearchForDrugsController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                      
                  }
                  });
                  return row ;
                  });
              }
            
          }catch(Exception e){
              //e.printStackTrace();
              System.err.println("Error on Building Data");
              data = FXCollections.observableArrayList();
              TableView.setItems(data);
          }
         
}

    private void show_DrugActivity(ObservableList<String> rowData) throws IOException {
        Pane root2 = FXMLLoader.load(getClass().getResource("/view/DrugActivities.fxml"));
        Label drugID = (Label) root2.lookup("#drugIDLabel");
        drugID.setText(rowData.get(0));
        Label drugName = (Label) root2.lookup("#drugNameLabel");
        drugName.setText(rowData.get(1));
        //root.getChildren().addAll(root1,root2,root3); 
        Scene scene = new Scene(root2);
        //Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        Stage stage = (Stage) SearchPane.getScene().getWindow();

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
    }
}
