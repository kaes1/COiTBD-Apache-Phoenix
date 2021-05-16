package pl.polsl.fuzzy;

public class FuzzyOr {

    public static double fuzzyOr(double membershipDegree1, double membershipDegree2) {
        return Math.max(membershipDegree1, membershipDegree2);
    }
}
