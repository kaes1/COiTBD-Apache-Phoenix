package pl.polsl.linguisticVariable;

import pl.polsl.membershipFunction.TrapezoidalMembershipFunction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LinguisticVariableManager {

    private static volatile LinguisticVariableManager INSTANCE;

    private final Map<String, LinguisticVariable> linguisticVariables = new HashMap<>();

    //TODO Jak zrobić zmienne lingwistyczne operujące od -inf do +inf?

    private LinguisticVariableManager() {
        LinguisticValue low = new LinguisticValue("Low", new TrapezoidalMembershipFunction(0, 0, 160, 170));
        LinguisticValue medium = new LinguisticValue("Medium", new TrapezoidalMembershipFunction(160, 170, 180, 190));
        LinguisticValue high = new LinguisticValue("High", new TrapezoidalMembershipFunction(180, 190, Double.MAX_VALUE, Double.MAX_VALUE));
        LinguisticVariable heightVariable = new LinguisticVariable("Height", Arrays.asList(low, medium, high));
        saveLinguisticVariable(heightVariable);
    }

    public static LinguisticVariableManager getInstance() {
        if (INSTANCE != null)
            return INSTANCE;

        synchronized (LinguisticVariableManager.class) {
            if (INSTANCE == null) {
                INSTANCE = new LinguisticVariableManager();
            }
            return INSTANCE;
        }
    }

    public Optional<LinguisticValue> getLinguisticValue(String variableName, String valueName) {
        Optional<LinguisticVariable> linguisticVariable = getLinguisticVariable(variableName);
        return linguisticVariable.flatMap(variable -> variable.getLinguisticValue(valueName));
    }

    public Optional<LinguisticVariable> getLinguisticVariable(String variableName) {
        return Optional.ofNullable(linguisticVariables.get(variableName.toUpperCase()));
    }

    public void saveLinguisticVariable(LinguisticVariable linguisticVariable) {
        linguisticVariables.put(linguisticVariable.getVariableName().toUpperCase(), linguisticVariable);
    }

    public void clearAllLinguisticVariables() {
        linguisticVariables.clear();
    }
}
