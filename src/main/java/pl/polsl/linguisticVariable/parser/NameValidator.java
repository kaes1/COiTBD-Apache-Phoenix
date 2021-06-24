package pl.polsl.linguisticVariable.parser;

import org.apache.commons.lang.StringUtils;

public class NameValidator {

    public void validateName(String name) throws LinguisticVariableParserException {
        if (StringUtils.isBlank(name)) {
            throw new LinguisticVariableParserException("String " + name + " is empty");
        }
        if (!name.matches("\\p{Alnum}+")) {
            throw new LinguisticVariableParserException("String " + name + " contains illegal characters");
        }
    }
}
