package pl.polsl.linguisticValue;


import org.apache.commons.collections.CollectionUtils;
import pl.polsl.fuzzy.Trapezoid;

import java.util.*;

public class LinguisticValueManager {

    private static volatile LinguisticValueManager INSTANCE;

    private Map<String, List<LinguisticValue>> linguisticValueNamespaces;

    private LinguisticValueManager() {
        List<LinguisticValue> initialLinguisticValues = new ArrayList<>();
        linguisticValueNamespaces = new HashMap<>();
        createLinguisticValue("Initial", "LOW", new Trapezoid(0, 5,10,15));
        createLinguisticValue("Initial", "MEDIUM", new Trapezoid(10, 15,20,25));
        createLinguisticValue("Initial", "HIGH", new Trapezoid(20, 25,30,35));
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

    public Optional<LinguisticValue> getLinguisticValue(String namespace, String name) {
        List<LinguisticValue> linguisticValues = linguisticValueNamespaces.get(namespace.toUpperCase());

        if (CollectionUtils.isEmpty(linguisticValues))
            return Optional.empty();

        return linguisticValues.stream()
                .filter(linguisticValue -> linguisticValue.getName().equals(name.toUpperCase()))
                .findFirst();
    }

    public void createLinguisticValue(String namespace, String name, Trapezoid membershipTrapezoid) {
        LinguisticValue linguisticValue = new LinguisticValue(name.toUpperCase(), membershipTrapezoid);
        if (linguisticValueNamespaces.containsKey(namespace.toUpperCase())) {
            linguisticValueNamespaces.get(namespace.toUpperCase()).add(linguisticValue);
        } else {
            ArrayList<LinguisticValue> list = new ArrayList<>();
            list.add(linguisticValue);
            linguisticValueNamespaces.put(namespace.toUpperCase(), list);
        }
    }

    public void deleteLinguisticValue(String namespace, String name) {
        List<LinguisticValue> linguisticValues = linguisticValueNamespaces.get(namespace);
        if (CollectionUtils.isNotEmpty(linguisticValues)) {
            linguisticValues.removeIf(linguisticValue ->
                    linguisticValue.getName().equals(name)
            );
        }
    }

    public void clearAllLinguisticValues() {
        linguisticValueNamespaces = new HashMap<>();
    }
}
