package pl.polsl.linguisticVariable;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LinguisticVariable {

    @Getter
    private final String variableName;
    private final Map<String, LinguisticValue> linguisticValues = new LinkedHashMap<>();

    public LinguisticVariable(String variableName, List<LinguisticValue> linguisticValues) {
        this.variableName = variableName;
        linguisticValues.forEach(linguisticValue ->
                this.linguisticValues.put(linguisticValue.getName().toUpperCase(), linguisticValue)
        );
    }

    public Optional<LinguisticValue> getLinguisticValue(String valueName) {
        return Optional.ofNullable(linguisticValues.get(valueName.toUpperCase()));
    }

    public Optional<LinguisticValue> getMatchingLinguisticValue(double value) {
        return linguisticValues.values().stream()
                .max((lv1, lv2) -> compareLinguisticValues(lv1, lv2, value));
    }

    private Integer compareLinguisticValues(LinguisticValue lv1, LinguisticValue lv2, double value) {
        Double membership1 = lv1.calculateMembership(value);
        Double membership2 = lv2.calculateMembership(value);
        return membership1.compareTo(membership2);
    }
}
