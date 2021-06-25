package pl.polsl.linguisticVariable


import pl.polsl.membershipFunction.TrapezoidalMembershipFunction
import spock.lang.Specification

class LinguisticVariableSpec extends Specification {

    def "should return linguistic value regardless of name case"() {
        given:
        LinguisticValue testValue = new LinguisticValue("TestValue", new TrapezoidalMembershipFunction(0, 0, 160, 170))
        LinguisticVariable testVariable = new LinguisticVariable("TestVariable", Arrays.asList(testValue))

        when:
        Optional<LinguisticValue> regularResult = testVariable.getLinguisticValue("TestValue")
        Optional<LinguisticValue> uppercaseResult = testVariable.getLinguisticValue("TESTVALUE")
        Optional<LinguisticValue> lowercaseResult = testVariable.getLinguisticValue("testvalue")

        then:
        regularResult.isPresent()
        regularResult.get() == testValue
        uppercaseResult.isPresent()
        uppercaseResult.get() == testValue
        lowercaseResult.isPresent()
        lowercaseResult.get() == testValue
    }

    def "should return matching linguistic value with highest membership"() {
        given:
        LinguisticValue value1 = Stub() {
            getName() >> "1"
            calculateMembership(10) >> 0.8
        }
        LinguisticValue value2 = Stub() {
            getName() >> "2"
            calculateMembership(10) >> 0.9
        }
        LinguisticValue value3 = Stub() {
            getName() >> "3"
            calculateMembership(10) >> 0.7
        }

        and:
        LinguisticVariable testVariable = new LinguisticVariable("TestVariable", Arrays.asList(value1, value2, value3))

        when:
        def result = testVariable.getMatchingLinguisticValue(10)

        then:
        result.isPresent()
        result.get() == value2
    }

    def "should return first matching linguistic value when multiple values have highest membership"() {
        given:
        LinguisticValue value1 = Stub() {
            getName() >> "1"
            calculateMembership(10) >> 0.8
        }
        LinguisticValue value2 = Stub() {
            getName() >> "2"
            calculateMembership(10) >> 0.9
        }
        LinguisticValue value3 = Stub() {
            getName() >> "3"
            calculateMembership(10) >> 0.9
        }

        and:
        LinguisticVariable testVariable = new LinguisticVariable("TestVariable", Arrays.asList(value1, value2, value3))

        when:
        def result = testVariable.getMatchingLinguisticValue(10)

        then:
        result.isPresent()
        result.get() == value2
    }
}
