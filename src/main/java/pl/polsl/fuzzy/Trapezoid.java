package pl.polsl.fuzzy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trapezoid {

    //TODO calculate membership inside this class?

    private double a;
    private double b;
    private double c;
    private double d;

    public Trapezoid(double a, double b, double c, double d) {
        boolean validTrapezoid = (a <= b && b <= c && c <= d);
        if (!validTrapezoid)
            throw new IllegalArgumentException("Trapezoid not defined correctly.");
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
