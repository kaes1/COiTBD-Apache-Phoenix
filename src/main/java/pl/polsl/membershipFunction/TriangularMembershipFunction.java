package pl.polsl.membershipFunction;

import lombok.Getter;

@Getter
public class TriangularMembershipFunction implements MembershipFunction {

    private final double a;
    private final double b;
    private final double c;

    public TriangularMembershipFunction(double a, double b, double c) {
        if (a > b || b > c) {
            throw new IllegalStateException(String.format("Invalid triangular membership function arguments: %f, %f, %f", a, b, c));
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateMembership(double x) {
        if (x == b) {
            return 1.0;
        }

        if (x <= a) {
            return 0.0;
        }

        if (x > a && x < b) {
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
