package pl.polsl.linguisticValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.fuzzy.Trapezoid;

@Getter
@Setter
@AllArgsConstructor
public class LinguisticValue {
    private String name;
    private Trapezoid membershipTrapezoid;
}
