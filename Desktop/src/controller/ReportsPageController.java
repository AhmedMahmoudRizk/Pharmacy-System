/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBManagementLayer.UserQuery;
import DBManagementLayer.UserQuery.TotalityObject;
import DBManagementLayer.myconnector;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Yahia
 */
public class ReportsPageController implements Initializable {

    @FXML
    PieChart soldPie, sellersPie;

    UserQuery userQuery;
    myconnector con;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            ArrayList<UserQuery.TotalityObject> soldList = new ArrayList();
            ArrayList<UserQuery.TotalityObject> sellerList = new ArrayList();

            con = new myconnector();
            userQuery = userQuery.getInstance(con);
            soldList = userQuery.viewTopDrugsSells();
            sellerList = userQuery.viewTopFiveUsersSells();
            if (soldList.isEmpty()) {
                soldList.add(new TotalityObject(0, "None"));
            }
            if (sellerList.isEmpty()) {
                sellerList.add(new TotalityObject(0, "None"));
            }
            createPieChart(soldList, sellerList);
        } catch (Exception ex) {
            Logger.getLogger(ReportsPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createPieChart(ArrayList<TotalityObject> soldList, ArrayList<TotalityObject> sellerList) {
        //PieChart pie = new PieChart();

        ObservableList<PieChart.Data> soldData
                = FXCollections.observableArrayList();
        for (TotalityObject totObj : soldList) {
            //System.out.println(totObj.getID() + " " + totObj.getTotal());
            soldData.add(new PieChart.Data(totObj.getID(), totObj.getTotal()));
        }
        soldPie.setData(soldData);

        ObservableList<PieChart.Data> sellerData
                = FXCollections.observableArrayList();
        sellerData.clear();
        for (TotalityObject totObj : sellerList) {
            sellerData.add(new PieChart.Data(totObj.getID(), totObj.getTotal()));
        }
        sellersPie.setData(sellerData);

//        final Label caption = new Label("");
//        caption.setTextFill(Color.BLACK);
//        caption.setStyle("-fx-font: 34 arial;");
//
//        for (final PieChart.Data data : soldPie.getData()) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    caption.setTranslateX(e.getSceneX());
//                    caption.setTranslateY(e.getSceneY());
//
//                    caption.setText(String.valueOf(data.getPieValue()));
//                    System.out.println(String.valueOf(data.getPieValue()));
//                }
//            });
//        }
//        for (final PieChart.Data data : sellersPie.getData()) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    caption.setTranslateX(e.getSceneX());
//                    caption.setTranslateY(e.getSceneY());
//
//                    caption.setText(String.valueOf(data.getPieValue()));
//                    System.out.println(String.valueOf(data.getPieValue()));
//
//                }
//            });
//
//        }
        soldPie.getData().forEach(data -> {
            String value = String.format("%.1f", (data.getPieValue()));
            Tooltip toolTip = new Tooltip(value);
            Tooltip.install(data.getNode(), toolTip);
        });
        sellersPie.getData().forEach(data -> {
            String value = String.format("%.1f", (data.getPieValue()));
            Tooltip toolTip = new Tooltip(value);
            Tooltip.install(data.getNode(), toolTip);
        });
    }

}
