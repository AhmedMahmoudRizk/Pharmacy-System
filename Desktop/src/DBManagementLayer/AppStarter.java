/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBManagementLayer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Yahia
 */
public class AppStarter extends Application {
    Pane root;
    Pane root1;
    Pane root2;
    Pane root3;

    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) {
        try {
            root = new Pane();
            root2 = FXMLLoader.load(getClass().getResource("/view/StartScreen.fxml"));
            root3 = FXMLLoader.load(getClass().getResource("/view/HeaderBar.fxml"));
            Pane Sign = (Pane) root2.lookup("#SignUpBox");
            Sign.setVisible(false);
            root.getChildren().addAll(root2,root3);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            
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
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });
            
            
            
            Scene scene = new Scene(root,1229,778);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(AppStarter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
