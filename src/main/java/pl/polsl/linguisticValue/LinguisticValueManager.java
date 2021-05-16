package pl.polsl.linguisticValue;


import pl.polsl.fuzzy.Trapezoid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinguisticValueManager {

    private static volatile LinguisticValueManager INSTANCE;

    private List<LinguisticValue> linguisticValues;

    private LinguisticValueManager() {
        linguisticValues = new ArrayList<>();
        linguisticValues.add(new LinguisticValue("Low", new Trapezoid(0, 5,10,15)));
        linguisticValues.add(new LinguisticValue("Medium", new Trapezoid(10, 15,20,25)));
        linguisticValues.add(new LinguisticValue("High", new Trapezoid(20, 25,30,35)));
    }

    public static LinguisticValueManager getInstance() {
        if (INSTANCE != null)
            return INSTANCE;

        synchronized (LinguisticValueManager.class) {
            if(INSTANCE == null) {
                INSTANCE = new LinguisticValueManager();
            }
            return INSTANCE;
        }
    }

    public Optional<LinguisticValue> getLinguisticValue(String name) {
        return linguisticValues.stream()
                .filter(linguisticValue -> linguisticValue.getName().equals(name))
                .findFirst();
    }

    public void addLingusticValue(String name, Trapezoid membershipTrapezoid) {
        LinguisticValue linguisticValue = new LinguisticValue(name, membershipTrapezoid);
        linguisticValues.add(linguisticValue);
    }

    public void deleteLinguisticValue(String name) {
        linguisticValues.removeIf(linguisticValue ->
                linguisticValue.getName().equals(name)
        );
    }

    public void clearAllLingusticValues() {
        linguisticValues = new ArrayList<>();
    }
}
