package com.crs.view;


import DataModel.House;
import Repos.HousesRepo;
import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import SetsModel.AttributeType;
import SetsModel.Connector;
import SetsModel.SummaryTypes;
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
    ComboBox comboBoxSummarizer1;

    @FXML
    ComboBox comboBoxSummarizer2;

    @FXML
    ComboBox comboBoxHouseType1;

    @FXML
    ComboBox comboBoxHouseType2;

    @FXML
    ComboBox comboBoxConnector;

    @FXML
    TextArea textArea;

    public void initialize(){

        instance = this;

        setSummaryTypeOptions();
        setColumnsTypesQualifier();
        setColumnsTypesSummarizer();
        setComboBoxDisabled(comboBoxQualifier, true);
        setComboBoxDisabled(comboBoxSummarizer1, true);
        setComboBoxDisabled(comboBoxSummarizer2, true);
        setComboBoxDisabled(comboBoxConnector, true);
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

        setComboBoxDisabled(comboBoxSummarizer1, false);

        List<String> labels = linguisticVariableRepo.getVariable(comboBoxColumnTypeSummarizer.getSelectionModel().getSelectedIndex()).getAllLabels();

        setComboBoxDisabled(comboBoxSummarizer1, false);
        setComboBoxElements(comboBoxSummarizer1, labels, "Wybierz etykietę sumaryzatora");

        setComboBoxDisabled(comboBoxSummarizer2, false);
        setComboBoxElements(comboBoxSummarizer2, labels, "Wybierz etykietę sumaryzatora");

        setComboBoxDisabled(comboBoxConnector, false);
        setComboBoxElements(comboBoxConnector, Arrays.asList("i", "lub"), "Wybierz spójnik");

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

    @FXML
    public void generate() {

        if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() > -1) {

            if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() > -1) {

                if (comboBoxColumnTypeQualifier.getSelectionModel().getSelectedIndex() > -1 || comboBoxSummaryType.getSelectionModel().getSelectedIndex() == 0) {

                    if (comboBoxColumnTypeSummarizer.getSelectionModel().getSelectedIndex() > -1) {

                        if (comboBoxQualifier.getSelectionModel().getSelectedIndex() > -1 || comboBoxSummaryType.getSelectionModel().getSelectedIndex() == 0) {

                            if (comboBoxSummarizer1.getSelectionModel().getSelectedIndex() > -1) {

                                if (comboBoxSummarizer2.getSelectionModel().getSelectedIndex() > -1){

                                    if (comboBoxConnector.getSelectionModel().getSelectedIndex() > -1) {

                                        if ((comboBoxHouseType1.getSelectionModel().getSelectedIndex() < -1 || comboBoxHouseType2.getSelectionModel().getSelectedIndex() < -1)
                                        && comboBoxSummaryType.getSelectionModel().getSelectedIndex() > 1) {

                                            showAlert(Alert.AlertType.ERROR, "Błąd", "Miasta nie poprawnie wybrane");
                                            return;
                                        }
                                        else {

                                            Connector connector = null;

                                            if (comboBoxConnector.getSelectionModel().getSelectedIndex() == 0) {

                                                connector = Connector.and;
                                            }
                                            else {
                                                connector = Connector.or;
                                            }

                                            if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() == 0) {
                                                //pods. jednop. I typu z wieloma sum

                                                Application.instance.generateSummary(SummaryTypes.single, 1, -1, null, comboBoxColumnTypeSummarizer.getSelectionModel().getSelectedIndex(),
                                                        Arrays.asList((String) comboBoxSummarizer1.getSelectionModel().getSelectedItem(),
                                                                (String) comboBoxSummarizer2.getSelectionModel().getSelectedItem()), connector);
                                            }
                                            if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() == 1) {

                                                //pods. jednop. II typu z wieloma sum
                                                Application.instance.generateSummary(SummaryTypes.single, 2, comboBoxColumnTypeQualifier.getSelectionModel().getSelectedIndex(),
                                                        (String) comboBoxQualifier.getSelectionModel().getSelectedItem(), comboBoxColumnTypeSummarizer.getSelectionModel().getSelectedIndex(),
                                                        Arrays.asList((String) comboBoxSummarizer1.getSelectionModel().getSelectedItem(),
                                                                (String) comboBoxSummarizer2.getSelectionModel().getSelectedItem()), connector);

                                            }
                                            if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() >= 2) {

                                                //TODO: pods. wielopodmio z wieloma sum
                                            }


                                        }
                                    }
                                    else {

                                        showAlert(Alert.AlertType.ERROR, "Błąd", "Nie wybrano spójnika");
                                        return;
                                    }
                                }
                                else {

                                    if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() == 0) {

                                        //pods. jednop. I typu z jednym sum

                                        Application.instance.generateSummary(SummaryTypes.single, 1, -1, null, comboBoxColumnTypeSummarizer.getSelectionModel().getSelectedIndex(),
                                                Arrays.asList((String) comboBoxSummarizer1.getSelectionModel().getSelectedItem()), null);
                                    }
                                    if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() == 1) {

                                        //pods. jednop. II typu z jednym sum

                                        Application.instance.generateSummary(SummaryTypes.single, 2, comboBoxColumnTypeQualifier.getSelectionModel().getSelectedIndex(),
                                                (String) comboBoxQualifier.getSelectionModel().getSelectedItem(), comboBoxColumnTypeSummarizer.getSelectionModel().getSelectedIndex(),
                                                Arrays.asList((String) comboBoxSummarizer1.getSelectionModel().getSelectedItem()), null);
                                    }
                                    else
                                    {
                                        if (comboBoxHouseType1.getSelectionModel().getSelectedIndex() < -1 || comboBoxHouseType2.getSelectionModel().getSelectedIndex() < -1) {

                                            showAlert(Alert.AlertType.ERROR, "Błąd", "Miasta nie poprawnie wybrane");
                                            return;
                                        }
                                        else {

                                            //TODO: podsum. wielopod. z jedn sum
                                        }
                                    }
                                }
                            }
                            else {
                                showAlert(Alert.AlertType.ERROR, "Błąd", "Nie wybrano etykiety - sumaryzator");
                                return;
                            }
                        }
                        else {
                            showAlert(Alert.AlertType.ERROR, "Błąd", "Nie wybrano atykiety - kwalifikator");
                            return;
                        }
                    }
                    else {
                        showAlert(Alert.AlertType.ERROR, "Błąd", "Nie wybrano atrybutu - sumaryzator");
                        return;
                    }
                }
                else
                {
                    showAlert(Alert.AlertType.ERROR, "Błąd", "Nie wybrano atrybutu - kwalifikator");
                    return;
                }
            }

        }
        else {
            showAlert(Alert.AlertType.ERROR, "Błąd", "Nie wybrano typu podsumowania");
            return;
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {

            }
        });
    }

    public void showSummary(String text) {

        textArea.setText(text);
    }

}