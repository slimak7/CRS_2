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

        TableColumn c3 = new TableColumn("Pow. mieszkalna");

        c3.setCellValueFactory(new PropertyValueFactory<>("livingArea"));

        TableColumn c4 = new TableColumn("Pow. działki");

        c4.setCellValueFactory(new PropertyValueFactory<>("lotSize"));

        TableColumn c5 = new TableColumn("Pow. kuchni");

        c5.setCellValueFactory(new PropertyValueFactory<>("kitchenArea"));

        TableColumn c6 = new TableColumn("Pow. sypialni");

        c6.setCellValueFactory(new PropertyValueFactory<>("bedroomArea"));

        TableColumn c7 = new TableColumn("Pow. salonu");

        c7.setCellValueFactory(new PropertyValueFactory<>("livingRoomArea"));

        TableColumn c8 = new TableColumn("Pow. jadalni");

        c8.setCellValueFactory(new PropertyValueFactory<>("diningRoomArea"));

        TableColumn c9 = new TableColumn("Dyst. do szkoły podst.");

        c9.setCellValueFactory(new PropertyValueFactory<>("elementarySchoolDistance"));

        TableColumn c10 = new TableColumn("Dyst. do gimnazjum");

        c10.setCellValueFactory(new PropertyValueFactory<>("middleSchoolDistance"));

        TableColumn c11 = new TableColumn("Dyst. do liceum");

        c11.setCellValueFactory(new PropertyValueFactory<>("highSchoolDistance"));


        HousesTable.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11);

        HousesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HousesTable.getItems().addAll(houses);
    }

}