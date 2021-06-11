package pl.polsl.udf.fuzzy;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.membershipFunction.MembershipFunction;
import pl.polsl.membershipFunction.TrapezoidalMembershipFunction;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyAroundTrapezoidalFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}), // value
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid A
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid B
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid C
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true)  // trapezoid D
})
public class FuzzyAroundTrapezoidalFunction extends UdfBase {
    //TODO init like in CollationKeyFunction?
    //TODO trapezoids a,b,c,d are constant, can we assign them in initialize via getLiteralValue(1, Double.class) like in CollationKeyFunction?

    public static final String NAME = "FUZZY_AROUND_TRAPEZOIDAL";

    public FuzzyAroundTrapezoidalFunction() {
    }

    public FuzzyAroundTrapezoidalFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Double arg1, arg2, arg3, arg4, arg5;

        try {
            arg1 = getDoubleArgument(0, tuple, ptr);
            if (arg1 == null) return true;
            arg2 = getDoubleArgument(1, tuple, ptr);
            if (arg2 == null) return true;
            arg3 = getDoubleArgument(2, tuple, ptr);
            if (arg3 == null) return true;
            arg4 = getDoubleArgument(3, tuple, ptr);
            if (arg4 == null) return true;
            arg5 = getDoubleArgument(4, tuple, ptr);
            if (arg5 == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        MembershipFunction membershipFunction = new TrapezoidalMembershipFunction(arg2, arg3, arg4, arg5);

        double result = membershipFunction.calculateMembership(arg1);

        PDataType returnType = getDataType();
        ptr.set(new byte[returnType.getByteSize()]);
        returnType.getCodec().encodeDouble(result, ptr);
        return true;
    }

    @Override
    public PDataType getDataType() {
        return PDouble.INSTANCE;
    }

    @Override
    public String getName() {
        return NAME;
    }
}

