package com.crs.view;


import DataModel.House;
import Repos.HousesRepo;
import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import SetsModel.AttributeType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Arrays;
import java.util.List;

public class Controller {

    private HousesRepo housesRepo;
    private LinguisticQuantifierRepo linguisticQuantifierRepo;
    private LinguisticVariableRepo linguisticVariableRepo;

    public static Controller instance;

    @FXML
    private TableView HousesTable;

    @FXML
    ComboBox comboBoxSummaryType;

    @FXML
    ComboBox comboBoxColumnTypeQualifier;

    @FXML
    ComboBox comboBoxColumnTypeSummarizer;

    @FXML
    ComboBox comboBoxQualifier;

    @FXML
    ComboBox comboBoxSummarizer;

    @FXML
    ComboBox comboBoxHouseType1;

    @FXML
    ComboBox comboBoxHouseType2;

    public void initialize(){

        instance = this;

        setSummaryTypeOptions();
        setColumnsTypesQualifier();
        setColumnsTypesSummarizer();
        setComboBoxDisabled(comboBoxQualifier, true);
        setComboBoxDisabled(comboBoxSummarizer, true);
        setComboBoxDisabled(comboBoxHouseType1, true);
        setComboBoxDisabled(comboBoxHouseType2, true);
        setHousesTypes();
    }

    public void setObjects(HousesRepo housesRepo, LinguisticVariableRepo variableRepo, LinguisticQuantifierRepo quantifierRepo) {

        this.housesRepo = housesRepo;
        linguisticVariableRepo = variableRepo;
        linguisticQuantifierRepo = quantifierRepo;

        showHouses(housesRepo.getHouses());
    }

    private void showHouses(List<House> houses) {

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

        TableColumn c12 = new TableColumn("Typ domu");

        c12.setCellValueFactory(new PropertyValueFactory<>("houseType"));


        HousesTable.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12);

        HousesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HousesTable.getItems().addAll(houses);
    }

    private void setSummaryTypeOptions() {

        comboBoxSummaryType.setPromptText("Wybierz typ podsumowania");

        comboBoxSummaryType.getItems().setAll("Podsumowanie jednopodmiotowe typu I",
                "Podsumowanie jednopodmiotowe typu II",
                "Podsumowanie wielopodmiotowe typu I",
                "Podsumowanie wielopodmiotowe typu II",
                "Podsumowanie wielopodmiotowe typu III",
                "Podsumowanie wielopodmiotowe typu IV");

    }

    private void setColumnsTypesQualifier () {

        comboBoxColumnTypeQualifier.setPromptText("Wybierz atrybut");

        for (var v: AttributeType.values()
             ) {

            comboBoxColumnTypeQualifier.getItems().add(v.toString());
        }

    }

    private void setColumnsTypesSummarizer () {

        comboBoxColumnTypeSummarizer.setPromptText("Wybierz atrybut");

        for (var v: AttributeType.values()
        ) {

            comboBoxColumnTypeSummarizer.getItems().add(v.toString());
        }

    }

    private void setHousesTypes() {

        setComboBoxElements(comboBoxHouseType1, Arrays.asList("CONDO", "SINGLE_FAMILY", "TOWNHOUSE"), "Wybierz typ domu");
        setComboBoxElements(comboBoxHouseType2, Arrays.asList("CONDO", "SINGLE_FAMILY", "TOWNHOUSE"), "Wybierz typ domu");
    }

    public void setComboBoxDisabled(ComboBox comboBox, boolean disabled) {

        comboBox.setDisable(disabled);
    }

    private void setComboBoxElements (ComboBox comboBox, List<String> elements, String defaultText) {

        comboBox.getItems().clear();
        comboBox.setPromptText(defaultText);
        comboBox.getItems().addAll(elements);
    }

    @FXML
    public void onColumnQualifierChanged () {

        setComboBoxDisabled(comboBoxQualifier, false);

        List<String> labels = linguisticVariableRepo.getVariable(comboBoxColumnTypeQualifier.getSelectionModel().getSelectedIndex()).getAllLabels();

        setComboBoxDisabled(comboBoxQualifier, false);
        setComboBoxElements(comboBoxQualifier, labels, "Wybierz etykietę kwalifikatora");


    }

    @FXML
    public void onColumnSummarizerChanged () {

        setComboBoxDisabled(comboBoxSummarizer, false);

        List<String> labels = linguisticVariableRepo.getVariable(comboBoxColumnTypeSummarizer.getSelectionModel().getSelectedIndex()).getAllLabels();

        setComboBoxDisabled(comboBoxSummarizer, false);
        setComboBoxElements(comboBoxSummarizer, labels, "Wybierz etykietę sumatyzatora");

    }

    public void onSummaryTypeChanged() {

        if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() > 1) {

            setComboBoxDisabled(comboBoxHouseType1, false);
            setComboBoxDisabled(comboBoxHouseType2, false);
        }
        else
        {
            setComboBoxDisabled(comboBoxHouseType1, true);
            setComboBoxDisabled(comboBoxHouseType2, true);
        }
    }

}