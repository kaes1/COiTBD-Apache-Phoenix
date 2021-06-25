package pl.polsl.fuzzyMath

import spock.lang.Specification

class FuzzyLogicOperatorsSpec extends Specification {

    def "should calculate Fuzzy And as minimum of two arguments"() {
        given:
        double value1 = 0.5
        double value2 = 0.75

        when:
        def result = FuzzyLogicOperators.fuzzyAnd(value1, value2)

        then:
        result == value1
    }

    def "should calculate Fuzzy Or as maximum of two arguments"() {
        given:
        double value1 = 0.5
        double value2 = 0.75

        when:
        def result = FuzzyLogicOperators.fuzzyOr(value1, value2)

        then:
        result == value2
    }

    def "should calculate Fuzzy Not as one minus argument"() {
        given:
        double value = 0.6

        when:
        def result = FuzzyLogicOperators.fuzzyNot(value)

        then:
        result == 0.4d
    }
}
