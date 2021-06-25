package pl.polsl.math;

import lombok.AllArgsConstructor;
import lombok.Value;


/**
 * Linear function with two available representations:
 * Standard Form:           Ax + By + C = 0
 * Slope-Intercept Form:    y = ax + b
 */
@Value
@AllArgsConstructor
public class LinearFunction {
    double A;
    double B;
    double C;

    double slopeInterceptFormA;
    double slopeInterceptFormB;

    public LinearFunction(double A, double B, double C) {
        this.A = 1;
        this.B = B / A + 0.0;
        this.C = C / A + 0.0;
        this.slopeInterceptFormA = -A / B + 0.0;
        this.slopeInterceptFormB = -C / B + 0.0;
    }

    public LinearFunction(double slopeInterceptFormA, double slopeInterceptFormB) {
        this.slopeInterceptFormA = slopeInterceptFormA + 0.0;
        this.slopeInterceptFormB = slopeInterceptFormB + 0.0;
        double A = slopeInterceptFormA + 0.0;
        double B = -1;
        double C = slopeInterceptFormB + 0.0;
        this.A = 1;
        this.B = B / A + 0.0;
        this.C = C / A + 0.0;
    }

    public LinearFunction(Point P1, Point P2) {
        // P1(x1, y1), P2(x2, y2)
        // (y-y1)(x2 - x1) - (x-x1)(y2 - y1) = 0
        // y(x2-x1) - y1*x2 + y1*x1    -   x(y2-y1) + x1*y2 - x1*y1 = 0
        // -(y2-y1)x + (x2-x1)y - y1x2 + y2x1 = 0
        double A = -(P2.getY() - P1.getY());
        double B = P2.getX() - P1.getX();
        double C = -P1.getY() * P2.getX() + P2.getY() * P1.getX();
        this.A = 1;
        this.B = B / A + 0.0;
        this.C = C / A + 0.0;
        this.slopeInterceptFormA = -A / B + 0.0;
        this.slopeInterceptFormB = -C / B + 0.0;
    }

    public double evaluate(double x) {
        return (-A * x - C) / B;
    }
}
