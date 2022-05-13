package com.crs.view;

import Managers.LoadingManager;
import Repos.HousesRepo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("KSR");
        stage.setScene(scene);


        stage.show();

        initialize();
    }

    private void initialize() throws URISyntaxException {
        HousesRepo repo = new HousesRepo(
                LoadingManager.LoadHouses()
        );

        Controller.instance.ShowHouses(repo.getHouses());
    }

    public static void main(String[] args) {
        launch();
    }
}