package pl.polsl.math

import spock.lang.Specification
import spock.lang.Unroll

class LinearFunctionSpec extends Specification {

    @Unroll("should create linear function \"#expectedFunctionEquation\" from standard form \"#standardEquation\"")
    def "should create linear function from standard form"() {

        when:
        LinearFunction linearFunction = new LinearFunction(standardA, standardB, standardC)

        then:
        linearFunction == expectedLinearFunction

        where:
        standardA | standardB | standardC | standardEquation   | expectedLinearFunction     | expectedFunctionEquation
        1         | -1        | 0         | "x - y + 0 = 0"    | new LinearFunction(1, 0)   | "y = x"
        1         | -1        | 5         | "x - y + 5 = 0"    | new LinearFunction(1, 5)   | "y = x + 5"
        2         | -1        | 5         | "2x - y + 5 = 0"   | new LinearFunction(2, 5)   | "y = 2x + 5"
        -2        | -2        | -2        | "-2x - 2y - 2 = 0" | new LinearFunction(-1, -1) | "y = -x - 1"
    }

    @Unroll("should create linear function \"#expectedFunctionEquation\" from slope intercept form \"#slopeInterceptEquation\"")
    def "should create linear function from slope intercept form"() {

        when:
        LinearFunction linearFunction = new LinearFunction(slopeInterceptFormA, slopeInterceptFormB)

        then:
        linearFunction == expectedLinearFunction

        where:
        slopeInterceptFormA | slopeInterceptFormB | slopeInterceptEquation | expectedLinearFunction           | expectedFunctionEquation
        1                   | 0                   | "y = x"                | new LinearFunction(1, -1, 0)     | "x - y + 0 = 0"
        1                   | 5                   | "y = x + 5"            | new LinearFunction(1, -1, 5)     | "x - y + 5 = 0"
        2                   | 5                   | "y = 2x + 5"           | new LinearFunction(1, -0.5, 2.5) | "x - y/2 + 2.5 = 0"
        -2                  | -5                  | "y = -2x - 5"          | new LinearFunction(1, +0.5, 2.5) | "x + y/2 + 2.5 = 0"
    }

    @Unroll("should create linear function \"#expectedFunctionEquation\" from points #pointA, #pointB")
    def "should create linear function from two points"() {
        when:
        LinearFunction linearFunction = new LinearFunction(pointA, pointB)

        then:
        linearFunction == expectedLinearFunction

        where:
        pointA      | pointB      | expectedLinearFunction    | expectedFunctionEquation
        point(0, 0) | point(1, 1) | new LinearFunction(1, 0)  | "y = x"
        point(1, 1) | point(0, 0) | new LinearFunction(1, 0)  | "y = x"
        point(0, 5) | point(1, 7) | new LinearFunction(2, 5)  | "y = 2x + 5"
        point(0, 5) | point(1, 7) | new LinearFunction(2, 5)  | "y = 2x + 5"
        point(0, 5) | point(1, 3) | new LinearFunction(-2, 5) | "y = -2x + 5"
    }

    def static point(double x, double y) {
        return new Point(x, y);
    }
}
