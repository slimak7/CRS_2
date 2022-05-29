package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Summary {

    @Getter private String text;
    @Getter private List<Double> qualityMeasures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Summary summary = (Summary) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder().append(text, summary.text).append(qualityMeasures, summary.qualityMeasures).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(text).append(qualityMeasures).toHashCode();
    }
}
