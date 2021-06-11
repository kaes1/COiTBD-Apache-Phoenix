package pl.polsl.fuzzyMath;

import pl.polsl.math.LinearFunction;
import pl.polsl.math.LinearIntersection;
import pl.polsl.math.Point;

public class FuzzyEquals {

    public static double fuzzifyAndCalculateEquals(double value1, double fuzzify1, double value2, double fuzzify2) {

        //TODO Test and handle fuzzify == 0

        Point value1Point = new Point(value1, 1);
        Point value2Point = new Point(value2, 1);
        Point value1FuzzifiedPoint;
        Point value2FuzzifiedPoint;

        if (value1 < value2) {
            value1FuzzifiedPoint = new Point(value1 + fuzzify1, 0);
            value2FuzzifiedPoint = new Point(value2 - fuzzify2, 0);
        } else {
            value1FuzzifiedPoint = new Point(value1 - fuzzify1, 0);
            value2FuzzifiedPoint = new Point(value2 + fuzzify2, 0);
        }

        LinearFunction linearFunction1 = new LinearFunction(value1Point, value1FuzzifiedPoint);
        LinearFunction linearFunction2 = new LinearFunction(value2Point, value2FuzzifiedPoint);

        Point intersectionPoint = LinearIntersection.calculateIntersectionPoint(linearFunction1, linearFunction2);

        return intersectionPoint.getY() >= 0
                ? intersectionPoint.getY()
                : 0.0;
    }
}
