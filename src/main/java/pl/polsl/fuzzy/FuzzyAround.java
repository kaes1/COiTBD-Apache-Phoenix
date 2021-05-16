package pl.polsl.fuzzy;

public class FuzzyAround {

    public static double fuzzyAround(double value, Trapezoid trapezoid) {

        if (value <= trapezoid.getA()) {
            return 0.0;
        }

        if (value > trapezoid.getA() && value <= trapezoid.getB()) {
            double membership = (value - trapezoid.getA()) / (trapezoid.getB() - trapezoid.getA()) * 1.0;
            return membership;
        }

        if (value > trapezoid.getB() && value <= trapezoid.getC() ) {
            return 1.0;
        }

        if (value > trapezoid.getC() && value <= trapezoid.getD()) {
            double membership = 1 - (value - trapezoid.getC()) / (trapezoid.getD() - trapezoid.getC());
            return membership; //TODO wyliczyc pozycję na osi Y
        }

        return 0.0;
    }
}
