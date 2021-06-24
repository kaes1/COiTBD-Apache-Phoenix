package pl.polsl.linguisticVariable.parser

import spock.lang.Specification
import spock.lang.Unroll

class NameParserSpec extends Specification {

    NameParser nameParser = new NameParser()

    @Unroll("should return name #expectedResult for valid input #input")
    def "should return valid name"() {
        when:
        def result = nameParser.parse(input)

        then:
        result == expectedResult

        where:
        input          | expectedResult
        "Height"       | "Height"
        "HEIGHT"       | "HEIGHT"
        "height"       | "height"
        "PersonHeight" | "PersonHeight"
    }

    def "should trim whitespaces"() {
        given:
        String input = "    SomeName\n"

        when:
        def result = nameParser.parse(input)

        then:
        result == "SomeName"
    }

    @Unroll("should throw exception when name #condition")
    def "should throw exception when name is invalid"() {
        when:
        nameParser.parse(input)

        then:
        thrown LinguisticVariableParserException

        where:
        input     | condition
        null      | "is null"
        ""        | "is empty"
        " "       | "contains only whitespace"
        "abc@"    | "contains a special character"
        "abc abc" | "contains a space between letters"
    }

}
