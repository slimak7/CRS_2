package com.crs.view;

import Managers.HouseLoadingManager;
import Managers.LinguisticVariablesLoadingManager;
import Managers.QuantifiersLoadingManager;
import Repos.HousesRepo;
import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import SetsModel.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {

    public static Application instance;

    private HousesRepo housesRepo;
    private LinguisticQuantifierRepo linguisticQuantifierRepo;
    private LinguisticVariableRepo linguisticVariableRepo;

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("KSR");
        stage.setScene(scene);


        stage.show();

        initialize();
    }

    private void initialize() throws URISyntaxException, SQLException, IOException {

        instance = this;

        housesRepo = new HousesRepo(
                HouseLoadingManager.LoadHouses()
        );

        linguisticQuantifierRepo = new LinguisticQuantifierRepo();
        linguisticVariableRepo = new LinguisticVariableRepo();

        QuantifiersLoadingManager.loadLinguisticQuantifiers(linguisticQuantifierRepo);
        LinguisticVariablesLoadingManager.loadLinguisticQuantifiers(linguisticVariableRepo);

        Controller.instance.setObjects(housesRepo, linguisticVariableRepo, linguisticQuantifierRepo);
    }

    public void generateSummary(SummaryTypes summaryType, Integer multiType, Integer qualifierIndex, String qualifierLabel,
                                Integer summarizerIndex, List<String> summarizersLabels, Connector connector) {

        SummaryBuilder summaryBuilder = SummaryBuilder.aSummary();

        ClassicSet P1, P2, W = null;

        if (summaryType.equals(SummaryTypes.single)) {

            LinguisticVariable qualifier = null;

            if (qualifierIndex == -1) {
                P1 = null;
            }
            else {

                qualifier = linguisticVariableRepo.getVariable(qualifierIndex);
                qualifier.setCurrentLabel(qualifierLabel);

                P1 = new ClassicSet(housesRepo.getValuesOfAttribute(qualifier.getAttributeType()),
                        qualifier.getFuzzySet(0).getClassicSet().getSpace(), false);

            }

            LinguisticVariable summarizer = linguisticVariableRepo.getVariable(summarizerIndex);

            List<LinguisticVariable> summarizers = new ArrayList<>();

            P2 = new ClassicSet(housesRepo.getValuesOfAttribute(summarizer.getAttributeType()),
                    summarizer.getFuzzySet(0).getClassicSet().getSpace(), false);

            for (var label:summarizersLabels
                 ) {

                LinguisticVariable linguisticVariable = new LinguisticVariable(summarizer.getAttributeType(),
                        summarizer.getLabels(), label);

                summarizers.add(linguisticVariable);
            }



            Summary summary = summaryBuilder.withSummaryType(summaryType).withMultiForm(multiType).
                    withClassicSetP1(P1).withClassicSetP2(P2).withClassicSetW(W).withQualifier(qualifier).
                    withSummarizers(summarizers).withQuantifier(linguisticQuantifierRepo.getAll()).
                    withConnector(connector).build();

            List<String> multiLine = summary.getStringSummariesWithAverageT();

            String text = "";

            for (var line:multiLine
                 ) {

                text += line + "\n";
            }

            Controller.instance.showSummary(text);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}