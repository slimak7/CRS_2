package com.crs.view;


import DataModel.House;
import Repos.HousesRepo;
import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import SetsModel.AttributeType;
import SetsModel.SummaryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import org.controlsfx.control.CheckComboBox;


import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    ComboBox comboBoxHouseType1;

    @FXML
    ComboBox comboBoxHouseType2;

    @FXML
    ComboBox comboBoxSortBy;

    @FXML
    TextArea textArea;

    @FXML
    TextField textFieldTruthValue;

    @FXML
    TextField textFieldWeights;

    @FXML
    AnchorPane titledPaneQ;

    @FXML
    AnchorPane titledPaneS;

    List<Integer> selectedSummarizers;
    List<Integer> selectedQualifiers;

    CheckComboBox<String> checkComboBox1;
    CheckComboBox<String> checkComboBox2;

    public void initialize(){

        instance = this;

        selectedQualifiers = new ArrayList<>();
        selectedSummarizers = new ArrayList<>();

        setSummaryTypeOptions();
        setColumnsTypesQualifier();
        setColumnsTypesSummarizer();
        setSortBy();
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

        comboBoxSummaryType.getItems().clear();

        comboBoxSummaryType.setPromptText("Wybierz typ podsumowania");

        comboBoxSummaryType.getItems().setAll("Podsumowanie jednopodmiotowe typu I",
                "Podsumowanie jednopodmiotowe typu II",
                "Podsumowanie wielopodmiotowe typu I",
                "Podsumowanie wielopodmiotowe typu II",
                "Podsumowanie wielopodmiotowe typu III",
                "Podsumowanie wielopodmiotowe typu IV");

        comboBoxSummaryType.getSelectionModel().clearSelection();

    }

    private void setSortBy() {

        comboBoxSortBy.getItems().clear();

        List<String> options = Arrays.asList("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "Średnia ważona - domyślnie");

        for (var v: options
        ) {

            comboBoxSortBy.getItems().add(v);

        }

        comboBoxSortBy.getSelectionModel().select(11);
    }

    private void setColumnsTypesQualifier () {

        if (!titledPaneQ.getChildren().isEmpty()) {

            selectedQualifiers.clear();
            checkComboBox1.getCheckModel().clearChecks();

            return;
        }

        final ObservableList<String> strings = FXCollections.observableArrayList();

        for (var o:AttributeType.values()) {

            strings.add(o.toString());
        }

        checkComboBox1 = new CheckComboBox<String>(strings);

        // and listen to the relevant events (e.g. when the selected indices or
        // selected items change).
        checkComboBox1.getCheckModel().getCheckedIndices().addListener(new ListChangeListener<Integer>() {
            public void onChanged(ListChangeListener.Change<? extends Integer> c) {

                selectedQualifiers.clear();

                selectedQualifiers.addAll(checkComboBox1.getCheckModel().getCheckedIndices());


            }
        });

        titledPaneQ.getChildren().add(checkComboBox1);
        checkComboBox1.show();
        checkComboBox1.setDisable(true);
    }


    private void setColumnsTypesSummarizer () {

        if (!titledPaneS.getChildren().isEmpty()) {

            selectedQualifiers.clear();
            checkComboBox2.getCheckModel().clearChecks();

            return;
        }

        final ObservableList<String> strings = FXCollections.observableArrayList();

        for (var o:AttributeType.values()) {

            strings.add(o.toString());
        }

        checkComboBox2 = new CheckComboBox<String>(strings);

        // and listen to the relevant events (e.g. when the selected indices or
        // selected items change).
        checkComboBox2.getCheckModel().getCheckedIndices().addListener(new ListChangeListener<Integer>() {
            public void onChanged(ListChangeListener.Change<? extends Integer> c) {

                selectedSummarizers.clear();

                selectedSummarizers.addAll(checkComboBox2.getCheckModel().getCheckedIndices());
            }
        });

        titledPaneS.getChildren().add(checkComboBox2);
        checkComboBox2.show();
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

        textArea.clear();
        textArea.setText(text);
    }

    @FXML
    public void comboBoxSummaryTypeChanged() {

        if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() >= 2){
            setComboBoxDisabled(comboBoxHouseType1, false);
            setComboBoxDisabled(comboBoxHouseType2, false);

        }
        else {
            setComboBoxDisabled(comboBoxHouseType1, true);
            setComboBoxDisabled(comboBoxHouseType2, true);

        }

        if (comboBoxSummaryType.getSelectionModel().getSelectedIndex() >= 1)
            checkComboBox1.setDisable(false);
        else
            checkComboBox1.setDisable(true);
    }

    @FXML
    public void generateAllSummaries() {

        Integer summaryTypeIndex = comboBoxSummaryType.getSelectionModel().getSelectedIndex();

        SummaryTypes summaryType = (summaryTypeIndex > 2) ? SummaryTypes.multi : SummaryTypes.single;

        Integer multiForm = (summaryType.equals(SummaryTypes.single)) ? summaryTypeIndex + 1 : summaryTypeIndex - 1;

        Double truthBorder;
        try {

            truthBorder = Double.valueOf(textFieldTruthValue.getText());
        }
        catch (NumberFormatException exception) {

            showAlert(Alert.AlertType.ERROR, "Błąd", "Nieprawidłowa wartość stopnia prawdziwości");
            return;
        }


        Application.instance.generateAllSummaries(summaryType, multiForm, selectedQualifiers, selectedSummarizers, truthBorder);
    }

    public List<Double> getWeights() {

        String text = textFieldWeights.getText();

        List<String> weightsString = Arrays.stream(text.split(";")).toList();

        List<Double> weights = new ArrayList<>();

        for(var d:weightsString) {

            weights.add(Double.valueOf(d));
        }

        return weights;
    }

    public Integer getMeasureIndexToSort() {

        return comboBoxSortBy.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void reset() {

        setSummaryTypeOptions();
        setColumnsTypesQualifier();
        setColumnsTypesSummarizer();
        setSortBy();
        setComboBoxDisabled(comboBoxHouseType1, true);
        setComboBoxDisabled(comboBoxHouseType2, true);
        setHousesTypes();
    }

    @FXML
    public void saveSummaries() throws FileNotFoundException, UnsupportedEncodingException {

        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz podsumowania");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {


            File fileToSave = fileChooser.getSelectedFile();

            Application.instance.saveSummaries(fileToSave.getAbsolutePath());


        }
    }

}