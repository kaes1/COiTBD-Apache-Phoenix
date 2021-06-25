package pl.polsl.linguisticVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LinguisticVariableManager {

    private static volatile LinguisticVariableManager INSTANCE;

    private final Map<String, LinguisticVariable> linguisticVariables = new HashMap<>();

    private LinguisticVariableManager() {
        InitialLinguisticVariables.get().forEach(this::saveLinguisticVariable);
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

    public Optional<LinguisticVariable> getLinguisticVariable(String variableName) {
        return Optional.ofNullable(linguisticVariables.get(variableName.toUpperCase()));
    }

    public void saveLinguisticVariable(LinguisticVariable linguisticVariable) {
        linguisticVariables.put(linguisticVariable.getVariableName().toUpperCase(), linguisticVariable);
    }
}
