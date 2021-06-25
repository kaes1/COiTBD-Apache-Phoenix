package pl.polsl.math

import spock.lang.Specification
import spock.lang.Unroll

class LinearIntersectionSpec extends Specification {

    @Unroll("should return intersection point #expectedIntersectionPoint between function \"#equation1\" and \"#equation2\"")
    def "should calculate intersection point between two linear functions"() {
        when:
        Point intersectionPoint = LinearIntersection.calculateIntersectionPoint(function1, function2)

        then:
        intersectionPoint == expectedIntersectionPoint

        where:
        function1                 | equation1   | function2                 | equation2    | expectedIntersectionPoint
        new LinearFunction(1, 0)  | "y = x"     | new LinearFunction(-1, 0) | "y = -x"     | new Point(0, 0)
        new LinearFunction(1, 0)  | "y = -x"    | new LinearFunction(-1, 0) | "y = x"      | new Point(0, 0)
        new LinearFunction(1, -2) | "y = x - 2" | new LinearFunction(-1, 1) | "y = -x + 1" | new Point(1.5, -0.5)
        new LinearFunction(-1, 2) | "y = -x + 2" | new LinearFunction(1, -1) | "y = x - 1" | new Point(1.5, 0.5)
    }

    def "should throw exception when calculating intersection of equal linear functions"() {
        given:
        LinearFunction linearFunction = new LinearFunction(2, 10)

        when:
        LinearIntersection.calculateIntersectionPoint(linearFunction, linearFunction)

        then:
        thrown MathException
    }

    def "should throw exception when calculating intersection of parallel lines"() {
        given:
        LinearFunction linearFunction1 = new LinearFunction(2, 10)
        LinearFunction linearFunction2 = new LinearFunction(2, 15)

        when:
        LinearIntersection.calculateIntersectionPoint(linearFunction1, linearFunction2)

        then:
        thrown MathException
    }
}
