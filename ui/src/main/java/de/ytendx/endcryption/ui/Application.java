package de.ytendx.endcryption.ui;

import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.client.EndCryptionClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Application extends javafx.application.Application {

    private static Stage stage;
    public static EndCryptionClient endCryptionClient;

    public static IndexController indexController;
    public static LoginController loginController;
    public static SocketAdapter master;

    @Override
    public void start(Stage stage) throws IOException {
        Application.stage = stage;
        switchToLogin();
        endCryptionClient = new EndCryptionClient("EndCryption" + new Random().nextInt(100));
        //new EndCryptionClient("Schwurblibub", new SocketAdapter("185.248.140.51", 4000));
    }

    public static void switchToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        loginController = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("EndCryption - Client");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToIndex() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("index.fxml"));
        indexController = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("EndCryption - Client");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}