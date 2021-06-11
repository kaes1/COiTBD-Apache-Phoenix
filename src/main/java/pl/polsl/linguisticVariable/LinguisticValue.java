package pl.polsl.linguisticVariable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.membershipFunction.MembershipFunction;

@Getter
@Setter
@AllArgsConstructor
public class LinguisticValue {
    private String name;
    private MembershipFunction membershipFunction;
}
