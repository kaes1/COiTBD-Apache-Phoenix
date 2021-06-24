package pl.polsl.math

import spock.lang.Specification

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.closeTo

class LinearIntersectionSpec extends Specification {

    def "shouldCalculateLinearIntersection"() {
        given:
        LinearFunction linearFunction1 = new LinearFunction(-1, 1, 0) // y = x
        LinearFunction linearFunction2 = new LinearFunction(1, 1, 0) // y = -x

        when:
        Point intersectionPoint = LinearIntersection.calculateIntersectionPoint(linearFunction1, linearFunction2)

        then:
        assertThat(intersectionPoint.getX(), closeTo(0, 0))
        assertThat(intersectionPoint.getY(), closeTo(0, 0))
    }

    def "shouldCalculateLinearIntersectionForComplexFunction"() {
        given:
        LinearFunction linearFunction1 = new LinearFunction(-1, 1, 2) // y = x - 2
        LinearFunction linearFunction2 = new LinearFunction(1, 1, -1) // y = -x + 1

        when:
        Point intersectionPoint = LinearIntersection.calculateIntersectionPoint(linearFunction1, linearFunction2)

        then:
        assertThat(intersectionPoint.getX(), closeTo(1.5f, 0))
        assertThat(intersectionPoint.getY(), closeTo(-0.5f, 0))
    }
}
