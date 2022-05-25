package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Summary {

    @Getter private String text;
    @Getter private List<Double> qualityMeasures;
}
