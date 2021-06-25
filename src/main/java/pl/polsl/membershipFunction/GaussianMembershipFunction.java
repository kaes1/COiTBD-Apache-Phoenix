package pl.polsl.membershipFunction;

import lombok.Getter;

@Getter
public class GaussianMembershipFunction implements MembershipFunction {

    private final double mean;
    private final double standardDeviation;

    public GaussianMembershipFunction(double mean, double standardDeviation) {
        if (standardDeviation < 0) {
            throw new IllegalStateException(String.format("Invalid gaussian membership function standard deviation: %f. Standard deviation cannot be negative.", standardDeviation));
        }
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public double calculateMembership(double value) {
        return Math.exp(-power(value - mean) / (2 * power(standardDeviation)));
    }

    private double power(double x) {
        return Math.pow(x, 2);
    }
}
