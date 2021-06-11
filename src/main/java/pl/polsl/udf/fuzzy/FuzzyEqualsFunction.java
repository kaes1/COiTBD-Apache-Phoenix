package pl.polsl.udf.fuzzy;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.fuzzyMath.FuzzyEquals;
import pl.polsl.membershipFunction.MembershipFunction;
import pl.polsl.membershipFunction.TriangularMembershipFunction;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyEqualsFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}), // value 1
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // fuzzify 1
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}), // value 2
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // fuzzify 2
})
public class FuzzyEqualsFunction extends UdfBase {
    public static final String NAME = "FUZZY_EQUALS";

    public FuzzyEqualsFunction() {
    }

    public FuzzyEqualsFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Double arg1, arg2, arg3, arg4;

        try {
            arg1 = getDoubleArgument(0, tuple, ptr);
            if (arg1 == null) return true;
            arg2 = getDoubleArgument(1, tuple, ptr);
            if (arg2 == null) return true;
            arg3 = getDoubleArgument(2, tuple, ptr);
            if (arg3 == null) return true;
            arg4 = getDoubleArgument(3, tuple, ptr);
            if (arg4 == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        double result = FuzzyEquals.fuzzifyAndCalculateEquals(arg1, arg2, arg3, arg4);

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

