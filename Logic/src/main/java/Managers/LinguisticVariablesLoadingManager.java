package Managers;

import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import SetsModel.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LinguisticVariablesLoadingManager {

    private static final String FILE_NAME = "linguistic_variables.json";

    public static void loadLinguisticQuantifiers (LinguisticVariableRepo repo) throws URISyntaxException, IOException {


        File file = new File(QuantifiersLoadingManager.class.getClassLoader().getResource(FILE_NAME).toURI());

        String readString = Files.readString(file.toPath());

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(readString);

        FuzzySetBuilder fuzzySetBuilder = FuzzySetBuilder.aFuzzySet();
        ClassicSetBuilder classicSetBuilder = ClassicSetBuilder.aClassicSet();

        Space.SpaceType spaceType;
        Range range;

        for (var v:jsonNode.get("variables")
        ) {

            String variableName = v.get("name").textValue();
            String space = v.get("space").textValue();

            if (space.equals("continuous"))
                spaceType = Space.SpaceType.dense;
            else spaceType = Space.SpaceType.discrete;

            JsonNode rangeNode = v.get("range");

            range = new Range(rangeNode.get(0).doubleValue(), rangeNode.get(1).doubleValue());

            LinguisticVariable linguisticVariable = new LinguisticVariable(getAttributeType(variableName));

            JsonNode labelNode = v.get("labels");
            for (var label:labelNode) {


                String labelName = label.get("labelName").textValue();
                String type = label.get("type").textValue();

                if (type.equals("gauss")) {

                    Double m = label.get("params").get("m").doubleValue();
                    Double s = label.get("params").get("s").doubleValue();
                    Double l = label.get("params").get("l").doubleValue();
                    Double r = label.get("params").get("r").doubleValue();

                    linguisticVariable.addLabel(labelName, fuzzySetBuilder.
                            withClassicSet(classicSetBuilder.withSpace(new Space(spaceType, range)).build()).
                            withFunction(new GaussianFunction(m, s, l, r)).withOperationType(FuzzyOperationsType.None).build());
                }
                if (type.equals("trapezoidal")) {

                    Double a = label.get("params").get("a").doubleValue();
                    Double b = label.get("params").get("b").doubleValue();
                    Double c = label.get("params").get("c").doubleValue();
                    Double d = label.get("params").get("d").doubleValue();

                    linguisticVariable.addLabel(labelName, fuzzySetBuilder.
                            withClassicSet(classicSetBuilder.withSpace(new Space(spaceType, range)).build()).
                            withFunction(new TrapezoidalFunction(a, b, c, d)).withOperationType(FuzzyOperationsType.None).build());
                }
                if (type.equals("triangular")) {

                    Double a = label.get("params").get("a").doubleValue();
                    Double b = label.get("params").get("b").doubleValue();
                    Double c = label.get("params").get("c").doubleValue();


                    linguisticVariable.addLabel(labelName, fuzzySetBuilder.
                            withClassicSet(classicSetBuilder.withSpace(new Space(spaceType, range)).build()).
                            withFunction(new TriangularFunction(a, b, c)).withOperationType(FuzzyOperationsType.None).build());
                }
            }

            repo.addVariable(linguisticVariable);

        }

    }

    private static AttributeType getAttributeType(String type) {

        if (type.equals("Price"))
            return AttributeType.price;
        if (type.equals("LivingArea"))
            return AttributeType.livingArea;
        if (type.equals("LotSize"))
            return AttributeType.lotSize;
        if (type.equals("KitchenArea"))
            return AttributeType.kitchenArea;
        if (type.equals("BedroomArea"))
            return AttributeType.bedroomArea;
        if (type.equals("LivingRoomArea"))
            return AttributeType.livingArea;
        if (type.equals("DiningRoomArea"))
            return AttributeType.diningRoomArea;
        if (type.equals("ElementarySchoolDistance"))
            return AttributeType.elementarySchoolDistance;
        if (type.equals("MiddleSchoolDistance"))
            return AttributeType.middleSchoolDistance;
        if (type.equals("HighSchoolDistance"))
            return AttributeType.highSchoolDistance;

        return null;
    }
}
