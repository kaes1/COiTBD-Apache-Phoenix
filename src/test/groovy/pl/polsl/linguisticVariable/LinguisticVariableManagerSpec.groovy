package pl.polsl.linguisticVariable

import spock.lang.Specification



class LinguisticVariableManagerSpec extends Specification {

    def "test"() {
        given:
        LinguisticVariableManager manager = LinguisticVariableManager.getInstance()

        when:
        Optional<LinguisticValue> linguisticValue = manager.getLinguisticValue("HEIGHT", "Low")
        Optional<LinguisticValue> linguisticValue2 = manager.getLinguisticValue("HeightT", "Low")
        Optional<LinguisticValue> linguisticValue3 = manager.getLinguisticValue("HEIGHT", "Loww")

        then:
        linguisticValue.isPresent()
        !linguisticValue2.isPresent()
        !linguisticValue3.isPresent()
    }
}
