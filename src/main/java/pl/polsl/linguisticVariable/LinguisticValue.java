package pl.polsl.linguisticVariable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.membershipFunction.MembershipFunction;

@Getter
@AllArgsConstructor
public class LinguisticValue {
    private final String name;
    private final MembershipFunction membershipFunction;

    public double calculateMembership(double value) {
        return membershipFunction.calculateMembership(value);
    }
}
