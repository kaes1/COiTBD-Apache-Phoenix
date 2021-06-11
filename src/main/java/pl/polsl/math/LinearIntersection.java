package pl.polsl.math;

public class LinearIntersection {
    public static Point calculateIntersectionPoint(LinearFunction f1, LinearFunction f2) {
        double W = f1.getA() * f2.getB() - f2.getA() * f1.getB();
        double Wx = (-f1.getC()) * f2.getB() - (-f2.getC()) * f1.getB();
        double Wy = f1.getA() * (-f2.getC()) - f2.getA() * (-f1.getC());
        double x = Wx / W;
        double y = Wy / W;
        return new Point(x, y);
    }
}
