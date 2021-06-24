package pl.polsl.linguisticVariable.parser;

import pl.polsl.linguisticVariable.LinguisticValue;
import pl.polsl.membershipFunction.GaussianMembershipFunction;
import pl.polsl.membershipFunction.TrapezoidalMembershipFunction;
import pl.polsl.membershipFunction.TriangularMembershipFunction;

import java.util.Arrays;
import java.util.List;


/**
 * Example:
 * "Height:Low,T,0,0,160,170|Medium,T,160,170,180,190|High,T,180,190,1000,1000"
 */
public class LinguisticValueParser {

    private final static List<String> TRAPEZOID_MEMBERSHIP_TYPE = Arrays.asList("1", "T", "TRAP", "TRAPEZOID");
    private final static List<String> TRIANGULAR_MEMBERSHIP_TYPE = Arrays.asList("2", "TRI", "TRIA", "TRIANGULAR");
    private final static List<String> GAUSSIAN_MEMBERSHIP_TYPE = Arrays.asList("3", "G", "GAUSS", "GAUSSIAN");

    private final NameValidator nameValidator;
    private final DoubleParser doubleParser;

    public LinguisticValueParser() {
        this.nameValidator = new NameValidator();
        this.doubleParser = new DoubleParser();
    }

    public LinguisticValueParser(NameValidator nameValidator, DoubleParser doubleParser) {
        this.nameValidator = nameValidator;
        this.doubleParser = doubleParser;
    }

    /**
     * Input example: "High,T,180,190,1000,1000"
     */
    public LinguisticValue parse(String linguisticValueString) throws LinguisticVariableParserException {
        String[] split = linguisticValueString.split(",");
        String valueName = split[0];
        nameValidator.validateName(valueName);
        String membershipType = split[1].toUpperCase();

        if (TRAPEZOID_MEMBERSHIP_TYPE.contains(membershipType)) {
            if (split.length < 6) {
                throw new LinguisticVariableParserException("Not enough arguments for Trapezoidal membership function");
            }
            double a = doubleParser.parseDouble(split[2]);
            double b = doubleParser.parseDouble(split[3]);
            double c = doubleParser.parseDouble(split[4]);
            double d = doubleParser.parseDouble(split[5]);
            return new LinguisticValue(valueName, new TrapezoidalMembershipFunction(a, b, c, d));
        } else if (TRIANGULAR_MEMBERSHIP_TYPE.contains(membershipType)) {
            if (split.length < 5) {
                throw new LinguisticVariableParserException("Not enough arguments for Triangular membership function");
            }
            double a = doubleParser.parseDouble(split[2]);
            double b = doubleParser.parseDouble(split[3]);
            double c = doubleParser.parseDouble(split[4]);
            return new LinguisticValue(valueName, new TriangularMembershipFunction(a, b, c));
        } else if (GAUSSIAN_MEMBERSHIP_TYPE.contains(membershipType)) {
            if (split.length < 4) {
                throw new LinguisticVariableParserException("Not enough arguments for Gaussian membership function");
            }
            double mean = doubleParser.parseDouble(split[2]);
            double standardDeviation = doubleParser.parseDouble(split[3]);
            return new LinguisticValue(valueName, new GaussianMembershipFunction(mean, standardDeviation));
        } else {
            throw new LinguisticVariableParserException("Unrecognized membership function type");
        }
    }
}
