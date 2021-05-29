package pl.polsl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.linguisticValue.LinguisticValue;
import pl.polsl.linguisticValue.LinguisticValueManager;

import java.util.Optional;

import static org.junit.Assert.*;

public class LinguisticValueManagerTest {

    @Test
    public void testInitialLinguisticValue() throws Exception {

        LinguisticValueManager manager = LinguisticValueManager.getInstance();

        Optional<LinguisticValue> linguisticValue = manager.getLinguisticValue("INITIAL", "Low");
        Optional<LinguisticValue> linguisticValue2 = manager.getLinguisticValue("INITIALL", "Low");
        Optional<LinguisticValue> linguisticValue3 = manager.getLinguisticValue("INITIAL", "Loww");

        assertTrue(linguisticValue.isPresent());
        assertFalse(linguisticValue2.isPresent());
        assertFalse(linguisticValue3.isPresent());
    }

}
