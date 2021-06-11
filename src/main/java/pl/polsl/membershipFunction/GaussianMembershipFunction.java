package pl.polsl.membershipFunction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GaussianMembershipFunction implements MembershipFunction {

    private double mean;
    private double standardDeviation;

    @Override
    public double calculateMembership(double value) {
        return Math.exp(  -power(value - mean)  /  (2*power(standardDeviation)) );
    }

    private double power(double x) {
        return Math.pow(x, 2);
    }
}
