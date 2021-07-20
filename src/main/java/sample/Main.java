package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Player;
import model.robot.SmartRobot;
import services.GameManager;

import java.io.File;

public class Main extends Application {
    public static GameManager gameManager=new GameManager();
    @Override
    public void start(Stage primaryStage) throws Exception {
        File file;
        gameManager.init();
        gameManager.setStage(primaryStage);
        gameManager.setRoot("log_in");
        gameManager.getStage().show();
        gameManager.setOpponent(new SmartRobot());
    }


    public static void main(String[] args) {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        }catch (ClassNotFoundException c){
//            System.out.println(c);
//        }
        launch(args);
    }
}
