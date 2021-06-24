package pl.polsl.membershipFunction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrapezoidalMembershipFunction implements MembershipFunction {

    private final double a;
    private final double b;
    private final double c;
    private final double d;

    @Override
    public double calculateMembership(double x) {
        if (x <= a) {
            return 0.0;
        }

        if (x > a && x <= b) {
            double membership = (x - a) / (b - a);
            return membership;
        }

        if (x > b && x <= c ) {
            return 1.0;
        }

        if (x > c && x <= d) {
            double membership = 1 - (c - x) / (c - d);
            return membership;
        }

        return 0.0;
    }
}
