package pl.polsl.linguisticVariable.parser;

import org.apache.commons.lang.StringUtils;
import pl.polsl.linguisticVariable.LinguisticValue;
import pl.polsl.linguisticVariable.LinguisticVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Input example: "Height:Low,T,0,0,160,170|Medium,T,160,170,180,190|High,T,180,190,1000,1000"
 */
public class LinguisticVariableParser {

    private final LinguisticValueParser linguisticValueParser;
    private final NameParser nameParser;

    private final static String GENERAL_PARSING_ERROR = "Failed to parse Linguistic Variable";

    public LinguisticVariableParser() {
        this.linguisticValueParser = new LinguisticValueParser();
        this.nameParser = new NameParser();
    }

    public LinguisticVariableParser(LinguisticValueParser linguisticValueParser, NameParser nameParser) {
        this.linguisticValueParser = linguisticValueParser;
        this.nameParser = nameParser;
    }

    public LinguisticVariable parse(String input) throws LinguisticVariableParserException {

        if (StringUtils.isBlank(input)) {
            throw new LinguisticVariableParserException("Linguistic Variable input string is empty");
        }

        String[] inputSplit = input.split(":");

        if (inputSplit.length < 2) {
            throw new LinguisticVariableParserException(GENERAL_PARSING_ERROR);
        }

        String variableName = nameParser.parse(inputSplit[0]);

        String[] valuesSplit = inputSplit[1].split("\\|");

        if (valuesSplit.length < 1) {
            throw new LinguisticVariableParserException("Not enough Linguistic Values in Variable");
        }

        List<LinguisticValue> linguisticValues = Arrays.stream(valuesSplit)
                .map(this::parseValue)
                .collect(Collectors.toList());

        return new LinguisticVariable(variableName, linguisticValues);
    }

    /**
     * Input example: "High,T,180,190,1000,1000"
     */
    private LinguisticValue parseValue(String valueString) throws LinguisticVariableParserException {
        return linguisticValueParser.parse(valueString);
    }
}
