package pl.polsl.fuzzyMath;

import org.junit.Test;

import static org.junit.Assert.*;

public class FuzzyEqualsTest {

    @Test
    public void testFuzzyEqualsForFarSamePoints() {
        //given
        double value1 = 10;
        double value2 = 10;
        double fuzzify1 = 2;
        double fuzzify2 = 4;

        //when
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2);

        //then
        assertEquals(1, result, 0);
    }

    @Test
    public void testFuzzyEqualsWhenPointsAreEqualToSomeDegree() {
        //given
        double value1 = 10;
        double value2 = 20;
        double fuzzify1 = 10;
        double fuzzify2 = 5;

        //when
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2);

        //then
        assertEquals(1.0/3.0, result, 0);
    }

    @Test
    public void testFuzzyEqualsWhenPointsAreEqualToSomeDegreeButDifferentOrder() {
        //given
        double value1 = 20;
        double value2 = 10;
        double fuzzify1 = 10;
        double fuzzify2 = 5;

        //when
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2);

        //then
        assertEquals(1.0/3.0, result, 0);
    }


    @Test
    public void testFuzzyEqualsWhenPointsAreNotEqual() {
        //given
        double value1 = 10;
        double value2 = 20;
        double fuzzify1 = 2;
        double fuzzify2 = 4;

        //when
        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2);

        //then
        assertEquals(0, result, 0);
    }

}
