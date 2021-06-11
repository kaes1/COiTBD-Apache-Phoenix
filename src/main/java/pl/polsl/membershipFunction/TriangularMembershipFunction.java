package pl.polsl.membershipFunction;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.polsl.math.LinearFunction;

@Data
@AllArgsConstructor
public class TriangularMembershipFunction implements MembershipFunction {

    private double a;
    private double b;
    private double c;

    @Override
    public double calculateMembership(double x) {
        if (x <= a) {
            return 0.0;
        }

        if (x > a && x <= b) {
            double membership = (x - a) / (b - a);
            return membership;
        }

        if (x > b && x <= c) {
            double membership = 1 - (b - x) / (b - c);

            return membership;
        }

        return 0.0;
    }
}