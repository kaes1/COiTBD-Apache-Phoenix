package pl.polsl.linguisticVariable.parser

import pl.polsl.membershipFunction.GaussianMembershipFunction
import pl.polsl.membershipFunction.TrapezoidalMembershipFunction
import pl.polsl.membershipFunction.TriangularMembershipFunction
import spock.lang.Specification
import spock.lang.Unroll

class LinguisticValueParserSpec extends Specification {

    NameParser nameParser = Stub()
    DoubleParser doubleParser = Stub()

    LinguisticValueParser linguisticValueParser = new LinguisticValueParser(nameParser, doubleParser)

    def setup() {
        nameParser.parse("High") >> "High"
        doubleParser.parse("180") >> 180
        doubleParser.parse("190") >> 190
        doubleParser.parse("1000") >> 1000
        doubleParser.parse("10") >> 10
    }


    @Unroll("should detect #expectedType membership function type for type input \"#inputType\"")
    def "should detect membership function type"() {

        given:
        String input = String.format("High,%s,180,190,1000,1000", inputType)

        when:
        def result = linguisticValueParser.parse(input)

        then:
        expectedClass.isInstance(result.membershipFunction)

        where:
        inputType     | expectedType  | expectedClass
        "T"           | "Trapezoidal" | TrapezoidalMembershipFunction.class
        "t"           | "Trapezoidal" | TrapezoidalMembershipFunction.class
        "Trap"        | "Trapezoidal" | TrapezoidalMembershipFunction.class
        "Trapezoidal" | "Trapezoidal" | TrapezoidalMembershipFunction.class
        "Tri"         | "Triangular"  | TriangularMembershipFunction.class
        "tri"         | "Triangular"  | TriangularMembershipFunction.class
        "Triangular"  | "Triangular"  | TriangularMembershipFunction.class
        "G"           | "Gaussian"    | GaussianMembershipFunction.class
        "g"           | "Gaussian"    | GaussianMembershipFunction.class
        "Gauss"       | "Gaussian"    | GaussianMembershipFunction.class
        "Gaussian"    | "Gaussian"    | GaussianMembershipFunction.class
    }

    def "should throw exception when membership function type is not recognized"() {
        given:
        String input = "High,X"

        when:
        linguisticValueParser.parse(input)

        then:
        thrown LinguisticVariableParserException
    }

    def "should create linguistic value with trapezoidal membership function"() {
        given:
        String input = "High,T,180,190,1000,1000"

        when:
        def result = linguisticValueParser.parse(input)

        then:
        result.name == "High"
        result.membershipFunction instanceof TrapezoidalMembershipFunction
        def membershipFunction = (TrapezoidalMembershipFunction) result.membershipFunction
        membershipFunction.a == 180
        membershipFunction.b == 190
        membershipFunction.c == 1000
        membershipFunction.d == 1000
    }

    def "should create linguistic value with triangular membership function"() {
        given:
        String input = "High,Tri,180,190,1000"

        when:
        def result = linguisticValueParser.parse(input)

        then:
        result.name == "High"
        result.membershipFunction instanceof TriangularMembershipFunction
        def membershipFunction = (TriangularMembershipFunction) result.membershipFunction
        membershipFunction.a == 180
        membershipFunction.b == 190
        membershipFunction.c == 1000
    }

    def "should create linguistic value with gaussian membership function"() {
        given:
        String input = "High,G,180,10"

        when:
        def result = linguisticValueParser.parse(input)

        then:
        result.name == "High"
        result.membershipFunction instanceof GaussianMembershipFunction
        def membershipFunction = (GaussianMembershipFunction) result.membershipFunction
        membershipFunction.mean == 180
        membershipFunction.standardDeviation == 10
    }


    @Unroll("should throw exception when input \"#input\" #condition")
    def "should throw exception when input contains too few arguments"() {
        when:
        linguisticValueParser.parse(input)

        then:
        thrown LinguisticVariableParserException

        where:
        input          | condition
        "High,T"       | "contains too few arguments"
        "High,T,1,2,3" | "contains too few arguments for trapezoidal membership function"
        "High,Tri,1,2" | "contains too few arguments for triangular membership function"
        "High,G,1"     | "contains too few arguments for gaussian membership function"
    }
}
