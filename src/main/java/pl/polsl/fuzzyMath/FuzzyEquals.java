package pl.polsl.fuzzyMath;

import pl.polsl.math.LinearFunction;
import pl.polsl.math.LinearIntersection;
import pl.polsl.math.Point;

public class FuzzyEquals {

    public static double fuzzifyAndCalculateEquals(double value1, double fuzzify1, double value2, double fuzzify2) {

        if (value1 == value2) {
            return 1;
        }

        if (fuzzify1 == 0 && fuzzify2 == 0) {
            return value1 == value2 ? 1 : 0;
        }

        if (fuzzify1 == 0) {
            return calculateEqualsWithSingleFuzzifiedValue(value1, value2, fuzzify2);
        }

        if (fuzzify2 == 0) {
            return calculateEqualsWithSingleFuzzifiedValue(value2, value1, fuzzify1);
        }

        Point value1Point = new Point(value1, 1);
        Point value2Point = new Point(value2, 1);
        Point value1FuzzifiedPoint = new Point(fuzzify(value1, fuzzify1, value2), 0);
        Point value2FuzzifiedPoint = new Point(fuzzify(value2, fuzzify2, value1), 0);

        LinearFunction linearFunction1 = new LinearFunction(value1Point, value1FuzzifiedPoint);
        LinearFunction linearFunction2 = new LinearFunction(value2Point, value2FuzzifiedPoint);

        Point intersectionPoint = LinearIntersection.calculateIntersectionPoint(linearFunction1, linearFunction2);

        return cutOffResult(intersectionPoint.getY());
    }

    private static double calculateEqualsWithSingleFuzzifiedValue(double staticValue, double valueToFuzzify, double fuzzifyAmount) {
        Point originalValuePoint = new Point(staticValue, 1);
        Point fuzzifiedPoint = new Point(fuzzify(valueToFuzzify, fuzzifyAmount, staticValue), 0);
        LinearFunction linearFunction = new LinearFunction(originalValuePoint, fuzzifiedPoint);
        return cutOffResult(linearFunction.evaluate(staticValue));
    }

    private static double fuzzify(double value, double fuzzifyAmount, double valueToFuzzifyTowards) {
        return value < valueToFuzzifyTowards
                ? value + fuzzifyAmount
                : value - fuzzifyAmount;
    }

    private static double cutOffResult(double result) {
        return result >= 0
                ? result
                : 0;
    }
}
