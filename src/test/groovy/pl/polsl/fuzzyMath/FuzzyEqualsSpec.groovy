package pl.polsl.fuzzyMath

import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.closeTo

class FuzzyEqualsSpec extends Specification {

    @Unroll("should return #expectedResult for #val1 , #val2")
    def "should return expectedResult"() {
        given:
        double value1 = 10;
        double value2 = 10;
        double fuzzify1 = 2;
        double fuzzify2 = 4;

        when:
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2);

        then:
        result closeTo(1, 0)

        where:
        expectedResult | val1 | val2
        1              | 5    | 10
        2              | 5    | 10
        3              | 5    | 10
    }

    def "shouldReturnOneForSameValues"() {
        given:
        double value1 = 10
        double value2 = 10
        double fuzzify1 = 2
        double fuzzify2 = 4

        when:
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2)

        then:
        result == 1
    }

    def "shouldCalculateFuzzyEquals"() {
        given:
        double value1 = 10
        double value2 = 20
        double fuzzify1 = 10
        double fuzzify2 = 5

        when:
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2)

        then:
        result == 1.0f/3.0f
    }

    def "shouldCalculateFuzzyEqualsForValuesInDescendingOrder"() {
        given:
        double value1 = 20
        double value2 = 10
        double fuzzify1 = 10
        double fuzzify2 = 5

        when:
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2)

        then:
        result == 1.0f/3.0f
    }

    def "shouldReturnZeroWhenValuesAreTooFarAway"() {
        given:
        double value1 = 10
        double value2 = 20
        double fuzzify1 = 2
        double fuzzify2 = 4

        when:
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2)

        then:
        result == 0
    }

}
