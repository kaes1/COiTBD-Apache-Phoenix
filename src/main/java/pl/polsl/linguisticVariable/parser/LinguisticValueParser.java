package pl.polsl.linguisticVariable.parser;

import pl.polsl.linguisticVariable.LinguisticValue;
import pl.polsl.membershipFunction.GaussianMembershipFunction;
import pl.polsl.membershipFunction.TrapezoidalMembershipFunction;
import pl.polsl.membershipFunction.TriangularMembershipFunction;

import java.util.Arrays;
import java.util.List;

public class LinguisticValueParser {

    private final static List<String> TRAPEZOID_MEMBERSHIP_TYPE = Arrays.asList("1", "T", "TRAP", "TRAPEZOID", "TRAPEZOIDAL");
    private final static List<String> TRIANGULAR_MEMBERSHIP_TYPE = Arrays.asList("2", "TRI", "TRIA", "TRIANGLE", "TRIANGULAR");
    private final static List<String> GAUSSIAN_MEMBERSHIP_TYPE = Arrays.asList("3", "G", "GAUSS", "GAUSSIAN");

    private final NameParser nameParser;
    private final DoubleParser doubleParser;

    public LinguisticValueParser() {
        this.nameParser = new NameParser();
        this.doubleParser = new DoubleParser();
    }

    public LinguisticValueParser(NameParser nameParser, DoubleParser doubleParser) {
        this.nameParser = nameParser;
        this.doubleParser = doubleParser;
    }

    /**
     * Input example: "High,T,180,190,1000,1000"
     */
    public LinguisticValue parse(String linguisticValueString) throws LinguisticVariableParserException {
        String[] split = linguisticValueString.split(",");

        if (split.length < 2) {
            throw new LinguisticVariableParserException("Linguistic value string " + linguisticValueString + " contains too few arguments.");
        }

        String valueName = nameParser.parse(split[0]);
        String membershipType = split[1].toUpperCase();

        if (TRAPEZOID_MEMBERSHIP_TYPE.contains(membershipType)) {
            if (split.length < 6) {
                throw new LinguisticVariableParserException("Linguistic value string " + linguisticValueString + "contains too few for Trapezoidal membership function.");
            }
            double a = doubleParser.parse(split[2]);
            double b = doubleParser.parse(split[3]);
            double c = doubleParser.parse(split[4]);
            double d = doubleParser.parse(split[5]);
            return new LinguisticValue(valueName, new TrapezoidalMembershipFunction(a, b, c, d));
        } else if (TRIANGULAR_MEMBERSHIP_TYPE.contains(membershipType)) {
            if (split.length < 5) {
                throw new LinguisticVariableParserException("Linguistic value string " + linguisticValueString + "contains too few for Triangular membership function.");
            }
            double a = doubleParser.parse(split[2]);
            double b = doubleParser.parse(split[3]);
            double c = doubleParser.parse(split[4]);
            return new LinguisticValue(valueName, new TriangularMembershipFunction(a, b, c));
        } else if (GAUSSIAN_MEMBERSHIP_TYPE.contains(membershipType)) {
            if (split.length < 4) {
                throw new LinguisticVariableParserException("Linguistic value string " + linguisticValueString + "contains too few for Gaussian membership function.");
            }
            double mean = doubleParser.parse(split[2]);
            double standardDeviation = doubleParser.parse(split[3]);
            return new LinguisticValue(valueName, new GaussianMembershipFunction(mean, standardDeviation));
        } else {
            throw new LinguisticVariableParserException("Unrecognized membership function type.");
        }
    }
}
