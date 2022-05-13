package com.crs.view;


import DataModel.House;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Controller {

    public static Controller instance;

    @FXML
    private TableView HousesTable;

    @FXML
    public  void initialize(){

        instance = this;
    }

    public void ShowHouses(List<House> houses) {

        TableColumn c1 = new TableColumn("Nr");

        c1.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn c2 = new TableColumn("Cena");

        c2.setCellValueFactory(new PropertyValueFactory<>("price"));

        HousesTable.getColumns().addAll(c1, c2);

        HousesTable.getItems().addAll(houses);
    }

}