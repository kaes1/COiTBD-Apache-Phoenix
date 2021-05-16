package pl.polsl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.fuzzy.FuzzyAround;
import pl.polsl.fuzzy.Trapezoid;

import static org.junit.Assert.*;

public class FuzzyAroundTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isAround() {
        //given

        //when
        double membershipDegree1 = FuzzyAround.fuzzyAround(48, new Trapezoid(40,50,50,60));
        double membershipDegree2 = FuzzyAround.fuzzyAround(52, new Trapezoid(40,50,50,60));

        //then
        assertEquals(0.8, membershipDegree1, 0);
        assertEquals(0.8, membershipDegree2, 0);

    }
}
