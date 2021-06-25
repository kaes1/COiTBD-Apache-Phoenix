package pl.polsl.linguisticVariable

import pl.polsl.membershipFunction.TrapezoidalMembershipFunction
import spock.lang.Specification

class LinguisticVariableManagerSpec extends Specification {

    def "should load initial linguistic variables"() {
        given:
        LinguisticVariableManager manager = LinguisticVariableManager.getInstance()

        expect:
        for (linguisticVariable in InitialLinguisticVariables.get()) {
            Optional<LinguisticVariable> optional = manager.getLinguisticVariable(linguisticVariable.getVariableName())
            optional.isPresent()
            optional.get() == linguisticVariable
        }
    }

    def "should save linguistic variable"() {
        given:
        LinguisticVariableManager manager = LinguisticVariableManager.getInstance()

        and:
        LinguisticValue testValue = new LinguisticValue("Test", new TrapezoidalMembershipFunction(0, 0, 160, 170))
        LinguisticVariable testVariable = new LinguisticVariable("TestVariable", Arrays.asList(testValue))

        when:
        manager.saveLinguisticVariable(testVariable)

        then:
        noExceptionThrown()
        manager.getLinguisticVariable(testVariable.getVariableName()).isPresent()
        manager.getLinguisticVariable(testVariable.getVariableName()).get() == testVariable
    }

    def "should return linguistic variable regardless of name ecase"() {
        given:
        LinguisticVariableManager manager = LinguisticVariableManager.getInstance()

        and:
        LinguisticValue testValue = new LinguisticValue("Test", new TrapezoidalMembershipFunction(0, 0, 160, 170))
        LinguisticVariable testVariable = new LinguisticVariable("TestVariable", Arrays.asList(testValue))

        and:
        manager.saveLinguisticVariable(testVariable)

        when:
        Optional<LinguisticVariable> regularResult = manager.getLinguisticVariable("TestVariable")
        Optional<LinguisticVariable> uppercaseResult = manager.getLinguisticVariable("TESTVARIABLE")
        Optional<LinguisticVariable> lowercaseResult = manager.getLinguisticVariable("testvariable")

        then:
        regularResult.isPresent()
        regularResult.get() == testVariable
        uppercaseResult.isPresent()
        uppercaseResult.get() == testVariable
        lowercaseResult.isPresent()
        lowercaseResult.get() == testVariable
    }
}
