package pl.polsl.math;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LinearFunction {
    // Linear function represented as: Ax + By + C = 0
    double A;
    double B;
    double C;

    public LinearFunction(Point P1, Point P2) {
        // P1(x1, y1), P2(x2, y2)
        // (y-y1)(x2 - x1) - (x-x1)(y2 - y1) = 0
        // y(x2-x1) - y1*x2 + y1*x1    -   x(y2-y1) + x1*y2 - x1*y1 = 0
        // -(y2-y1)x + (x2-x1)y - y1x2 + y2x1 = 0
        A = -(P2.getY() - P1.getY());
        B = P2.getX() - P1.getX();
        C = -P1.getY() * P2.getX() + P2.getY() * P1.getX();
    }

    public double evaluate(double x) {
        return (-A * x - C) / B;
    }
}
