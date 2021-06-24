package pl.polsl.linguisticVariable.parser;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public class DoubleParser {

    private final static List<String> POSITIVE_INFINITY = Arrays.asList("INFINITY", "INF", "I", "+INFINITY", "+INF", "+I");
    private final static List<String> NEGATIVE_INFINITY = Arrays.asList("-INFINITY", "-INF", "-I");

    public Double parseDouble(String input) throws LinguisticVariableParserException {
        if (StringUtils.isBlank(input)) {
            throw new LinguisticVariableParserException("String " + input + " is empty");
        }
        if (POSITIVE_INFINITY.contains(input.toUpperCase())) {
            return Double.MAX_VALUE;
        }
        if (NEGATIVE_INFINITY.contains(input.toUpperCase())) {
            return -Double.MAX_VALUE;
        }
        return Double.parseDouble(input);
    }
}
