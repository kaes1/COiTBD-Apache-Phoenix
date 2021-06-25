package pl.polsl.membershipFunction

import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.closeTo

class GaussianMembershipFunctionSpec extends Specification {

    @Unroll("should return membership #expectedMembership for point #x in gaussian membership function (mean = #mean, std = #standardDeviation)")
    def "should calculate membership"() {
        given:
        GaussianMembershipFunction gaussianMembershipFunction = new GaussianMembershipFunction(mean, standardDeviation)

        when:
        double result = gaussianMembershipFunction.calculateMembership(x)

        then:
        result closeTo(expectedMembership, 1E-15d)

        where:
        mean | standardDeviation | x | expectedMembership
        5    | 1                 | 5 | 1d
        5    | 1                 | 4 | Math.exp(-power((4 - 5)) / (2 * power(1)))
        5    | 1                 | 6 | Math.exp(-power((6 - 5)) / (2 * power(1)))
    }

    def "should throw exception when creating gaussian membership function with negative standard deviation"() {
        given:
        def standardDeviation = -15d

        when:
        new GaussianMembershipFunction(5, standardDeviation)

        then:
        thrown IllegalStateException
    }

    static double power(double x) {
        return Math.pow(x, 2)
    }
}
