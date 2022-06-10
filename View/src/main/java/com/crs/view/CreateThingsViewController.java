package com.crs.view;


import DataModel.House;
import Repos.HousesRepo;
import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import SetsModel.*;
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
import java.util.stream.Collectors;

public class CreateThingsViewController {

    public static CreateThingsViewController instance;

    private LinguisticQuantifierRepo linguisticQuantifierRepo;
    private LinguisticVariableRepo linguisticVariableRepo;

    @FXML
    public ComboBox comboBoxType;
    public ComboBox comboBoxFunctionType;

    @FXML
    public TextField textFieldName;

    @FXML
    public TextField textFieldCoefficients;

    @FXML
    public ComboBox comboBoxVariableOrQuantifierType;

    @FXML
    public TitledPane titledPaneVQ;

    public void initialize(){

        instance = this;
        setObjects();
        setType();
    }

    public void setObjects() {

        linguisticVariableRepo = Application.instance.getLinguisticVariableRepo();
        linguisticQuantifierRepo = Application.instance.getLinguisticQuantifierRepo();

    }

    private void setType() {

        comboBoxType.getItems().clear();

        comboBoxType.setPromptText("Wybierz...");

        comboBoxType.getItems().setAll("Etykieta sumaryzatora lub kwalifikatora", "Kwantyfikator");

        comboBoxType.getSelectionModel().clearSelection();

        comboBoxFunctionType.getItems().clear();

        comboBoxFunctionType.setPromptText("Wybierz funkcję");

        comboBoxFunctionType.getItems().setAll("Trójkątna", "Trapezoidalna", "Gaussowska");

        comboBoxFunctionType.getSelectionModel().clearSelection();

        comboBoxVariableOrQuantifierType.setDisable(true);
    }

    @FXML
    public void onTypeChanged() {

        if (comboBoxType.getSelectionModel().getSelectedIndex() == 0) {

            final ObservableList<String> strings = FXCollections.observableArrayList();

            for (var o:AttributeType.values()) {

                strings.add(o.toString());
            }

            comboBoxVariableOrQuantifierType.getItems().clear();
            comboBoxVariableOrQuantifierType.getItems().addAll(strings);

            titledPaneVQ.setText("Wybierz atrybut");

            comboBoxVariableOrQuantifierType.setDisable(false);
        }
        else {

            final ObservableList<String> strings = FXCollections.observableArrayList();
            strings.addAll("Względny", "Absolutny");

            titledPaneVQ.setText("Wybierz typ kwantyfikatora");

            comboBoxVariableOrQuantifierType.getItems().clear();
            comboBoxVariableOrQuantifierType.getItems().addAll(strings);

            comboBoxVariableOrQuantifierType.setDisable(false);
        }
    }

    @FXML
    public void add() {

        if (comboBoxType.getSelectionModel().getSelectedIndex() == -1)
            return;

        List<String> stringCoeffs = Arrays.stream(textFieldCoefficients.getText().toString().split(";")).toList();

        int selectedFunctionIndex = comboBoxFunctionType.getSelectionModel().getSelectedIndex();

        if (selectedFunctionIndex == -1) {
            showAlert(Alert.AlertType.ERROR, "Błąd", "Wybierz funkcję!");
            return;
        }

        List<Double> coeffs = new ArrayList<>();

        int n = (selectedFunctionIndex == 0) ? 3 : 4;

        for (int i = 0; i < n; i++) {

            try {

                coeffs.add(Double.valueOf(stringCoeffs.get(i)));
            }
            catch (NumberFormatException e) {

                showAlert(Alert.AlertType.ERROR, "Błąd", e.getMessage());
                return;
            }
        }

        MembershipFunction function = null;

        if (selectedFunctionIndex == 0) {
            function = new TriangularFunction(coeffs.get(0), coeffs.get(1), coeffs.get(2));
        }
        if (selectedFunctionIndex == 1) {
            function = new TrapezoidalFunction(coeffs.get(0), coeffs.get(1),coeffs.get(2), coeffs.get(3));
        }
        if (selectedFunctionIndex == 2) {
            function = new GaussianFunction(coeffs.get(0), coeffs.get(1), coeffs.get(2), coeffs.get(3));
        }

        FuzzySet fuzzySet = new FuzzySet(function);

        if (comboBoxType.getSelectionModel().getSelectedIndex() == 0) {

            int selectedVariableIndex = comboBoxVariableOrQuantifierType.getSelectionModel().getSelectedIndex();

            if (selectedVariableIndex == -1) {
                showAlert(Alert.AlertType.ERROR, "Błąd", "Wybierz atrybut!");
                return;
            }

            String name = textFieldName.getText();

            if (name.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Błąd", "Wpisz nazwę!");
                return;
            }

            var labels = linguisticVariableRepo.getVariable(selectedVariableIndex).getAllLabels();

            if (labels.contains(name)) {
                showAlert(Alert.AlertType.ERROR, "Błąd", "Nazwa już istnieje!");
                return;
            }

            linguisticVariableRepo.getVariable(selectedVariableIndex).addLabel(name, fuzzySet);


        }
        else {
            int selectedTypeIndex = comboBoxVariableOrQuantifierType.getSelectionModel().getSelectedIndex();

            if (selectedTypeIndex == -1) {
                showAlert(Alert.AlertType.ERROR, "Błąd", "Wybierz rodzaj kwantyfikatora!");
                return;
            }

            String name = textFieldName.getText();

            if (name.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Błąd", "Wpisz nazwę!");
                return;
            }

            var labels = linguisticQuantifierRepo.getAll().stream().map(x -> x.getName()).collect(Collectors.toList());

            if (labels.contains(name)) {
                showAlert(Alert.AlertType.ERROR, "Błąd", "Nazwa już istnieje!");
                return;
            }

            LinguisticQuantifiersTypes type = (selectedTypeIndex == 0) ? LinguisticQuantifiersTypes.relative : LinguisticQuantifiersTypes.absolute;

            linguisticQuantifierRepo.addQuantifier(new LinguisticQuantifier(type, name, fuzzySet));

        }

        showAlert(Alert.AlertType.CONFIRMATION, "Sukces", "Dodano!");
        Application.instance.update();
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

}
