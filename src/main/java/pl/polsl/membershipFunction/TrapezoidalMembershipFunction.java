package pl.polsl.membershipFunction;

import lombok.Getter;

@Getter
public class TrapezoidalMembershipFunction implements MembershipFunction {

    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public TrapezoidalMembershipFunction(double a, double b, double c, double d) {
        if (a > b || b > c || c > d) {
            throw new IllegalStateException(String.format("Invalid trapezoidal membership function arguments: %f, %f, %f, %f", a, b, c, d));
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double calculateMembership(double x) {
        if (x >= b && x <= c ) {
            return 1.0;
        }

        if (x <= a) {
            return 0.0;
        }

        if (x > a && x < b) {
            double membership = (x - a) / (b - a);
            return membership;
        }

        if (x > c && x < d) {
            double membership = 1 - (c - x) / (c - d);
            return membership;
        }

        return 0.0;
    }
}
