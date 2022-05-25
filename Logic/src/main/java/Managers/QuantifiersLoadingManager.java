package Managers;

import Repos.LinguisticQuantifierRepo;
import SetsModel.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuantifiersLoadingManager {

    private static final String FILE_NAME = "quantifiers.json";

    public static void loadLinguisticQuantifiers (LinguisticQuantifierRepo repo) throws URISyntaxException, IOException {

        File file = new File(QuantifiersLoadingManager.class.getClassLoader().getResource(FILE_NAME).toURI());

        String readString = Files.readString(file.toPath());

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(readString);

        FuzzySetBuilder fuzzySetBuilder = FuzzySetBuilder.aFuzzySet();
        ClassicSetBuilder classicSetBuilder = ClassicSetBuilder.aClassicSet();

        Space.SpaceType spaceType;
        Range range;

        JsonNode jsonNodeRelative = jsonNode.get("Względne");

        String space = jsonNodeRelative.get("space").textValue();

        JsonNode rangeNode = jsonNodeRelative.get("range");
        Double min = rangeNode.get(0).doubleValue();
        Double max = rangeNode.get(1).doubleValue();

        if (space == "continuous")
            spaceType = Space.SpaceType.dense;
        else spaceType = Space.SpaceType.discrete;

        range = new Range(min , max);

        for (var v:jsonNodeRelative.get("labels")
             ) {


            String labelName = v.get("name").textValue();
            String type = v.get("type").textValue();

            if (type.equals("gauss")) {

                Double m = v.get("params").get("m").doubleValue();
                Double s = v.get("params").get("s").doubleValue();
                Double l = v.get("params").get("l").doubleValue();
                Double r = v.get("params").get("r").doubleValue();

                repo.addQuantifier(new LinguisticQuantifier(LinguisticQuantifiersTypes.relative, labelName, fuzzySetBuilder.
                        withFunction(new GaussianFunction(m, s, l, r)).
                        withClassicSet(classicSetBuilder.withSpace(new Space(spaceType, range)).build()).build()));
            }
            if (type.equals("trapezoidal")) {

                Double a = v.get("params").get("a").doubleValue();
                Double b = v.get("params").get("b").doubleValue();
                Double c = v.get("params").get("c").doubleValue();
                Double d = v.get("params").get("d").doubleValue();

                repo.addQuantifier(new LinguisticQuantifier(LinguisticQuantifiersTypes.relative, labelName, fuzzySetBuilder.
                        withFunction(new TrapezoidalFunction(a, b, c, d)).
                        withClassicSet(classicSetBuilder.withSpace(new Space(spaceType, range)).build()).build()));
            }
            
        }
        
        JsonNode jsonNodeAbsolute = jsonNode.get("Absolutne");

        space = jsonNodeRelative.get("space").textValue();

        rangeNode = jsonNodeRelative.get("range");
        min = rangeNode.get(0).doubleValue();
        max = rangeNode.get(1).doubleValue();

        if (Objects.equals(space, "continuous"))
            spaceType = Space.SpaceType.dense;
        else spaceType = Space.SpaceType.discrete;

        range = new Range(min , max);

        for (var v:jsonNodeAbsolute.get("labels")
        ) {

            String labelName = v.get("name").textValue();
            String type = v.get("type").textValue();

            if (type.equals("gauss")) {

                Double m = v.get("params").get("m").doubleValue();
                Double s = v.get("params").get("s").doubleValue();
                Double l = v.get("params").get("l").doubleValue();
                Double r = v.get("params").get("r").doubleValue();

                repo.addQuantifier(new LinguisticQuantifier(LinguisticQuantifiersTypes.absolute, labelName, fuzzySetBuilder.
                        withFunction(new GaussianFunction(m, s, l, r)).
                        withClassicSet(classicSetBuilder.withSpace(new Space(spaceType, range)).build()).build()));
            }
            if (type.equals("trapezoidal")) {

                Double a = v.get("params").get("a").doubleValue();
                Double b = v.get("params").get("b").doubleValue();
                Double c = v.get("params").get("c").doubleValue();
                Double d = v.get("params").get("d").doubleValue();

                repo.addQuantifier(new LinguisticQuantifier(LinguisticQuantifiersTypes.absolute, labelName, fuzzySetBuilder.
                        withFunction(new TrapezoidalFunction(a, b, c, d)).
                        withClassicSet(classicSetBuilder.withSpace(new Space(spaceType, range)).build()).build()));
            }

        }
        
    }
}
