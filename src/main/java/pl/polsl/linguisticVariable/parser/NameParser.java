package pl.polsl.linguisticVariable.parser;

import org.apache.commons.lang.StringUtils;

public class NameParser {

    public String parse(String name) throws LinguisticVariableParserException {
        if (StringUtils.isBlank(name)) {
            throw new LinguisticVariableParserException("String " + name + " is empty.");
        }

        name = name.trim();

        if (!name.matches("\\p{Alnum}+")) {
            throw new LinguisticVariableParserException("String " + name + " contains illegal characters.");
        }

        return name;
    }
}
