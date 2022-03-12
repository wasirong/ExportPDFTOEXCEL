package com.dhl.MainApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JFXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("DHL出口报关单自动核对 Version.1.0.0.1");
        primaryStage.setScene(new Scene(root, 990, 440));
        primaryStage.show();
    }


    public static void main(String[] args) {
        System.out.println("Main+++++++++++++++++++++++");
        launch(args);
    }
}
