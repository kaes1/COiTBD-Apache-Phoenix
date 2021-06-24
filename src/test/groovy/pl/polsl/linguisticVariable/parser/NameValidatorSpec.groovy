package pl.polsl.linguisticVariable.parser

import spock.lang.Specification

class NameValidatorSpec extends Specification {
    //TODO

    def "test"() {
        given:


        when:
        boolean onlyLettersAndNumbers = input.matches("\\p{Alnum}+")

        then:
        onlyLettersAndNumbers == expectedResult

        where:
        input     | expectedResult
        "abc"     | true
        "abc123"  | true
        "abc 132" | false
        " abc3 "  | false
        "a@bc"    | false
        "#abc"    | false
    }

}
