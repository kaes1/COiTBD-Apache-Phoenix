package pl.polsl.fuzzyMath;

public class FuzzyLogicOperators {
    public static double fuzzyAnd(double membershipDegree1, double membershipDegree2) {
        return Math.min(membershipDegree1, membershipDegree2);
    }

    public static double fuzzyOr(double membershipDegree1, double membershipDegree2) {
        return Math.max(membershipDegree1, membershipDegree2);
    }

    public static double fuzzyNot(double value) {
        return 1.0 - value;
    }
}
