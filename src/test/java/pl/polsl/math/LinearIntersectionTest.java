package pl.polsl.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinearIntersectionTest {

    @Test
    public void testLinearIntersection() {
        //given
        LinearFunction linearFunction1 = new LinearFunction(-1, 1, 0); // y = x
        LinearFunction linearFunction2 = new LinearFunction(1, 1, 0); // y = -x

        //when
        Point intersectionPoint = LinearIntersection.calculateIntersectionPoint(linearFunction1, linearFunction2);

        //then
        assertEquals(0, intersectionPoint.getX(), 0);
        assertEquals(0, intersectionPoint.getY(), 0);
    }

    @Test
    public void testLinearIntersectionForComplexFunctions() {
        //given
        LinearFunction linearFunction1 = new LinearFunction(-1, 1, 2); // y = x - 2
        LinearFunction linearFunction2 = new LinearFunction(1, 1, -1); // y = -x + 1

        //when
        Point intersectionPoint = LinearIntersection.calculateIntersectionPoint(linearFunction1, linearFunction2);

        //then
        assertEquals(1.5, intersectionPoint.getX(), 0);
        assertEquals(-0.5, intersectionPoint.getY(), 0);
    }

}
