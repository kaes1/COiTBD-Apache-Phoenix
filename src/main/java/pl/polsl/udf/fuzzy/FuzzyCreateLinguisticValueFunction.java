package pl.polsl.udf.fuzzy;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.*;
import pl.polsl.linguisticVariable.LinguisticValue;
import pl.polsl.linguisticVariable.LinguisticVariable;
import pl.polsl.linguisticVariable.LinguisticVariableManager;
import pl.polsl.membershipFunction.MembershipFunction;
import pl.polsl.membershipFunction.TrapezoidalMembershipFunction;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@BuiltInFunction(name = FuzzyCreateLinguisticValueFunction.NAME, args = {
        @Argument(allowedTypes = {PVarchar.class}, isConstant = true),  // linguistic variable name
        @Argument(allowedTypes = {PVarchar.class}, isConstant = true),  // linguistic value name
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // trapezoid A
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // trapezoid B
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // trapezoid C
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true)  // trapezoid D
})
public class FuzzyCreateLinguisticValueFunction extends UdfBase {

    public static final String NAME = "FUZZY_CREATE_LINGUISTIC_VALUE";

    public FuzzyCreateLinguisticValueFunction() {
    }

    public FuzzyCreateLinguisticValueFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {

        String arg1, arg2;
        Double arg3, arg4, arg5, arg6;

        try {
            arg1 = getStringArgument(0, tuple, ptr);
            if (arg1 == null) return true;
            arg2 = getStringArgument(1, tuple, ptr);
            if (arg2 == null) return true;
            arg3 = getDoubleArgument(2, tuple, ptr);
            if (arg3 == null) return true;
            arg4 = getDoubleArgument(3, tuple, ptr);
            if (arg4 == null) return true;
            arg5 = getDoubleArgument(4, tuple, ptr);
            if (arg5 == null) return true;
            arg6 = getDoubleArgument(5, tuple, ptr);
            if (arg6 == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        MembershipFunction membershipFunction = new TrapezoidalMembershipFunction(arg3, arg4, arg5, arg6);

        LinguisticVariableManager linguisticVariableManager = LinguisticVariableManager.getInstance();

        LinguisticValue linguisticValue = new LinguisticValue(arg2, membershipFunction);
        LinguisticVariable linguisticVariable = new LinguisticVariable(arg1, Collections.singletonList(linguisticValue));

        linguisticVariableManager.saveLinguisticVariable(linguisticVariable);

        ptr.set(PDataType.TRUE_BYTES);
        return true;
    }

    @Override
    public PDataType getDataType() {
        return PBoolean.INSTANCE;
    }

    @Override
    public String getName() {
        return NAME;
    }
}

