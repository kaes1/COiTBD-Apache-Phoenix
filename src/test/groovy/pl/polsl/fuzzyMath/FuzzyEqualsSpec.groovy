package pl.polsl.fuzzyMath

import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.closeTo

class FuzzyEqualsSpec extends Specification {

    @Unroll("should return #expectedResult for value #value1 fuzzified by #fuzzify1 and value #value2 fuzzified by #fuzzify2")
    def "should calculate fuzzy equals"() {
        when:
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2)

        then:
        result closeTo(expectedResult, 0)

        where:
        value1 | fuzzify1 | value2 | fuzzify2 | expectedResult
        10     | 0        | 10     | 0        | 1
        10     | 5        | 10     | 5        | 1
        10     | 0        | 20     | 0        | 0
        10     | 5        | 20     | 5        | 0
        10     | 10       | 20     | 5        | 1d / 3d
        20     | 10       | 10     | 5        | 1d / 3d
        10     | 10       | 20     | 5        | 1d / 3d
    }
}
