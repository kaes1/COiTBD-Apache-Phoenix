package pl.polsl.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinearFunctionTest {

    @Test
    public void testLinearFunctionConstructorFromTwoPoints() {
        //given
        Point A = new Point(0,0);
        Point B = new Point(1,1);

        //when
        LinearFunction linearFunction = new LinearFunction(A,B);

        //then
        assertEquals(-1, linearFunction.getA(), 0);
        assertEquals(1, linearFunction.getB(), 0);
        assertEquals(0, linearFunction.getC(), 0);
    }

}
