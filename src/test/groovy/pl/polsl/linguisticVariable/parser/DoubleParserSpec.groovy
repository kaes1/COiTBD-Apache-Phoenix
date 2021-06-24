package pl.polsl.linguisticVariable.parser

import spock.lang.Specification
import spock.lang.Unroll

class DoubleParserSpec extends Specification {
    DoubleParser doubleParser = new DoubleParser()

    @Unroll("should return #expectedResult for valid input #input")
    def "should return valid double"() {
        when:
        def result = doubleParser.parse(input)

        then:
        result == expectedResult

        where:
        input    | expectedResult
        "10"     | 10d
        "-10"    | -10d
        "10.55"  | 10.55d
        "-10.55" | -10.55d
        "Inf"    | Double.MAX_VALUE
        "+Inf"   | Double.MAX_VALUE
        "-Inf"   | -Double.MAX_VALUE
    }

    def "should trim whitespaces"() {
        given:
        String input = "  2\n"

        when:
        def result = doubleParser.parse(input)

        then:
        result == 2
    }

    @Unroll("should throw exception when input #condition")
    def "should throw exception when input is invalid"() {
        when:
        doubleParser.parse(input)

        then:
        thrown LinguisticVariableParserException

        where:
        input     | condition
        null      | "is null"
        ""        | "is empty"
        " "       | "contains only whitespace"
        "2 10"    | "contains a space between numbers"
        "2a"      | "contains unrecognized letters"
        "2@"      | "contains special characters"
    }
}
