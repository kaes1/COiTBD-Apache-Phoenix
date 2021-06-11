package pl.polsl;

import org.junit.Test;
import pl.polsl.linguisticVariable.LinguisticValue;
import pl.polsl.linguisticVariable.LinguisticVariableManager;

import java.util.Optional;

import static org.junit.Assert.*;

public class LinguisticValueManagerTest {

    @Test
    public void testInitialLinguisticValue() {

        LinguisticVariableManager manager = LinguisticVariableManager.getInstance();

        Optional<LinguisticValue> linguisticValue = manager.getLinguisticValue("HEIGHT", "Low");
        Optional<LinguisticValue> linguisticValue2 = manager.getLinguisticValue("HeightT", "Low");
        Optional<LinguisticValue> linguisticValue3 = manager.getLinguisticValue("HEIGHT", "Loww");

        assertTrue(linguisticValue.isPresent());
        assertFalse(linguisticValue2.isPresent());
        assertFalse(linguisticValue3.isPresent());
    }

}
