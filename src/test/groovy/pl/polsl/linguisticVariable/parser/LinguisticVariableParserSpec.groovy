package pl.polsl.linguisticVariable.parser

import pl.polsl.linguisticVariable.LinguisticValue
import pl.polsl.linguisticVariable.LinguisticVariable
import pl.polsl.membershipFunction.TrapezoidalMembershipFunction
import spock.lang.Specification
import spock.lang.Unroll

class LinguisticVariableParserSpec extends Specification {

    static LinguisticValue lowHeightLinguisticValue = new LinguisticValue("Low", new TrapezoidalMembershipFunction(0, 0, 160, 170))
    static LinguisticValue mediumHeightLinguisticValue = new LinguisticValue("Medium", new TrapezoidalMembershipFunction(160, 170, 180, 190))
    static LinguisticValue highHeightLinguisticValue = new LinguisticValue("High", new TrapezoidalMembershipFunction(180, 190, 1000, 1000))

    LinguisticValueParser linguisticValueParser = Stub()
    NameParser nameParser = Stub()

    LinguisticVariableParser linguisticVariableParser = new LinguisticVariableParser(linguisticValueParser, nameParser)

    def setup() {
        nameParser.parse("Height") >> "Height"
        linguisticValueParser.parse("Low,T,0,0,160,170") >> lowHeightLinguisticValue
        linguisticValueParser.parse("Medium,T,160,170,180,190") >> mediumHeightLinguisticValue
        linguisticValueParser.parse("High,T,180,190,1000,1000") >> highHeightLinguisticValue
    }

    @Unroll("should create linguistic variable for valid input \"#input\"")
    def "should create linguistic variable for valid input"() {
        when:
        LinguisticVariable result = linguisticVariableParser.parse(input)

        then:
        result.variableName == "Height"
        for (linguisticValue in expectedLinguisticValues) {
            result.getLinguisticValue(linguisticValue.name).isPresent()
            result.getLinguisticValue(linguisticValue.name).get() == linguisticValue
        }

        where:
        input                                                                       | expectedLinguisticValues
        "Height:Low,T,0,0,160,170"                                                  | [lowHeightLinguisticValue]
        "Height:Low,T,0,0,160,170|Medium,T,160,170,180,190|High,T,180,190,1000,1000"| [lowHeightLinguisticValue, mediumHeightLinguisticValue, highHeightLinguisticValue]
    }

    @Unroll("should throw exception when input #condition")
    def "should throw exception for invalid input"() {
        when:
        linguisticVariableParser.parse(input)

        then:
        thrown LinguisticVariableParserException

        where:
        input    | condition
        null     | "is null"
        ""       | "is empty"
        " "      | "contains only whitespace"
        "Height" | "contains no linguistic values"
    }
}
