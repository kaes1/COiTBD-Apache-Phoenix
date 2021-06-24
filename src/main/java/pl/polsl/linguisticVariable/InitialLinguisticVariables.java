package pl.polsl.linguisticVariable;

import pl.polsl.membershipFunction.TrapezoidalMembershipFunction;

import java.util.Arrays;
import java.util.List;

public class InitialLinguisticVariables {

    public static List<LinguisticVariable> get() {
        LinguisticValue low = new LinguisticValue("Low", new TrapezoidalMembershipFunction(0, 0, 160, 170));
        LinguisticValue medium = new LinguisticValue("Medium", new TrapezoidalMembershipFunction(160, 170, 180, 190));
        LinguisticValue high = new LinguisticValue("High", new TrapezoidalMembershipFunction(180, 190, Double.MAX_VALUE, Double.MAX_VALUE));
        LinguisticVariable heightVariable = new LinguisticVariable("Height", Arrays.asList(low, medium, high));
        return Arrays.asList(heightVariable);
    }
}
