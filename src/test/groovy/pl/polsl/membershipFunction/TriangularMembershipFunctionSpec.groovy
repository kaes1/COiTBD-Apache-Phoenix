package pl.polsl.membershipFunction

import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.closeTo

class TriangularMembershipFunctionSpec extends Specification {

    @Unroll("should return membership #expectedMembership for point #x in triangular membership function (#a, #b, #c)")
    def "should calculate membership"() {
        given:
        TriangularMembershipFunction triangularMembershipFunction = new TriangularMembershipFunction(a, b, c)

        when:
        double result = triangularMembershipFunction.calculateMembership(x)

        then:
        result closeTo(expectedMembership, 1E-15d)

        where:
        a | b  | c  | x     | expectedMembership
        5 | 10 | 15 | 0     | 0
        5 | 10 | 15 | 5     | 0
        5 | 10 | 15 | 10    | 1d
        5 | 10 | 15 | 15    | 0
        5 | 10 | 15 | 20    | 0
        5 | 10 | 15 | 7.5d  | 0.5d
        5 | 10 | 15 | 12.5d | 0.5d
        1 | 1  | 1  | 1     | 1d
        1 | 1  | 1  | 1.01d | 0d
        1 | 1  | 2  | 1.50d | 0.5d
        0 | 10 | 20 | 1     | 0.1d
        0 | 10 | 20 | 9     | 0.9d
        0 | 10 | 20 | 11    | 0.9d
        0 | 10 | 20 | 19    | 0.1d
    }

    @Unroll("should throw exception when creating triangular membership function with invalid arguments (#a, #b, #c)")
    def "should throw exception when creating triangular membership function with invalid arguments"() {
        when:
        new TriangularMembershipFunction(a, b, c)

        then:
        thrown IllegalStateException

        where:
        a  | b  | c
        15 | 10 | 5
        15 | 15 | 5
        15 | 10 | 10
        5  | 10 | 5
    }
}
