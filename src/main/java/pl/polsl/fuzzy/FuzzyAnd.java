package pl.polsl.fuzzy;

public class FuzzyAnd {

    public static double fuzzyAnd(double membershipDegree1, double membershipDegree2) {
        return Math.min(membershipDegree1, membershipDegree2);
    }
}
