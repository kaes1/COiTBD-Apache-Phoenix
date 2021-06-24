package pl.polsl.math

import spock.lang.Specification

class LinearFunctionSpec extends Specification {

    def "should create linear function from two points"() {
        given:
        Point A = new Point(0,0)
        Point B = new Point(1,1)

        when:
        LinearFunction linearFunction = new LinearFunction(A,B)

        then:
        linearFunction.getA() == -1
        linearFunction.getB() == 1
        linearFunction.getC() == 0
    }
}
