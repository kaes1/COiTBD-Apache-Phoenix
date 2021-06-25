package pl.polsl.membershipFunction

import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.closeTo

class TrapezoidalMembershipFunctionSpec extends Specification {

    @Unroll("should return membership #expectedMembership for point #x in trapezoidal membership function (#a, #b, #c, #d)")
    def "should calculate membership"() {
        given:
        TrapezoidalMembershipFunction trapezoidalMembershipFunction = new TrapezoidalMembershipFunction(a, b, c, d)

        when:
        double result = trapezoidalMembershipFunction.calculateMembership(x)

        then:
        result closeTo(expectedMembership, 1E-15d)

        where:
        a | b  | c  | d | x     | expectedMembership
        5 | 10 | 15 | 20 | 0     | 0
        5 | 10 | 15 | 20 | 5     | 0
        5 | 10 | 15 | 20 | 10    | 1d
        5 | 10 | 15 | 20 | 15    | 1d
        5 | 10 | 15 | 20 | 20    | 0
        5 | 10 | 15 | 20 | 25    | 0
        5 | 10 | 15 | 20 | 7.5d  | 0.5d
        5 | 10 | 15 | 20 | 17.5d | 0.5d
        1 | 1  | 1  | 1 | 1     | 1d
        1 | 1  | 1  | 1 | 1.01d | 0d
        1 | 1  | 1  | 2 | 1.50d | 0.5d
        0 | 10 | 20 | 30 | 1     | 0.1d
        0 | 10 | 20 | 30 | 9     | 0.9d
        0 | 10 | 20 | 30 | 21    | 0.9d
        0 | 10 | 20 | 30 | 29    | 0.1d
    }

    @Unroll("should throw exception when creating trapezoidal membership function with invalid arguments (#a, #b, #c, #d)")
    def "should throw exception when creating trapezoidal membership function with invalid arguments"() {
        when:
        new TrapezoidalMembershipFunction(a, b, c, d)

        then:
        thrown IllegalStateException

        where:
        a  | b  | c  | d
        20 | 15 | 10 | 5
        20 | 20 | 20 | 5
        20 | 20 | 5  | 5
        20 | 5  | 5  | 5
        5 | 10  | 5  | 5
        5 | 5  | 10  | 5
    }
}
