package pl.polsl.linguisticValue;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import pl.polsl.fuzzy.Trapezoid;

import java.util.*;

public class LinguisticValueManager {

    private static volatile LinguisticValueManager INSTANCE;

    private Map<String, List<LinguisticValue>> linguisticValueNamespaces;

    private LinguisticValueManager() {
        List<LinguisticValue> initialLinguisticValues = new ArrayList<>();
        initialLinguisticValues.add(new LinguisticValue("Low", new Trapezoid(0, 5,10,15)));
        initialLinguisticValues.add(new LinguisticValue("Medium", new Trapezoid(10, 15,20,25)));
        initialLinguisticValues.add(new LinguisticValue("High", new Trapezoid(20, 25,30,35)));
        linguisticValueNamespaces.put("Initial", initialLinguisticValues);
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
        List<LinguisticValue> linguisticValues = linguisticValueNamespaces.get(namespace);

        if (CollectionUtils.isEmpty(linguisticValues))
            return Optional.empty();

        return linguisticValues.stream()
                .filter(linguisticValue -> linguisticValue.getName().equals(name))
                .findFirst();
    }

    public void addLinguisticValue(String namespace, String name, Trapezoid membershipTrapezoid) {
        LinguisticValue linguisticValue = new LinguisticValue(name, membershipTrapezoid);
        if (linguisticValueNamespaces.containsKey(namespace)) {
            linguisticValueNamespaces.get(namespace).add(linguisticValue);
        } else {
            linguisticValueNamespaces.put(namespace, Collections.singletonList(linguisticValue));
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
