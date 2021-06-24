package pl.polsl.udf.fuzzy.around;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.membershipFunction.TrapezoidalMembershipFunction;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyAroundTrapezoidalFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}), // value
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // trapezoid A
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // trapezoid B
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true), // trapezoid C
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant = true)  // trapezoid D
})
public class FuzzyAroundTrapezoidalFunction extends UdfBase {

    public static final String NAME = "FUZZY_AROUND_TRAPEZOIDAL";

    private TrapezoidalMembershipFunction membershipFunction;

    public FuzzyAroundTrapezoidalFunction() {
        initialize();
    }

    public FuzzyAroundTrapezoidalFunction(final List<Expression> children) throws SQLException {
        super(children);
        initialize();
    }

    private void initialize() {
        Double trapezoidA = getConstantDoubleArgument(1);
        Double trapezoidB = getConstantDoubleArgument(2);
        Double trapezoidC = getConstantDoubleArgument(3);
        Double trapezoidD = getConstantDoubleArgument(4);
        membershipFunction = new TrapezoidalMembershipFunction(trapezoidA, trapezoidB, trapezoidC, trapezoidD);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Double value;

        try {
            value = getDoubleArgument(0, tuple, ptr);
            if (value == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        double result = membershipFunction.calculateMembership(value);

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

