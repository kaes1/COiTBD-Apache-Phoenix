package pl.polsl.udf.fuzzy.around;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.membershipFunction.TriangularMembershipFunction;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyAroundTriangularFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}), // value
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // triangle A
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // triangle B
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true)  // triangle C
})
public class FuzzyAroundTriangularFunction extends UdfBase {
    public static final String NAME = "FUZZY_AROUND_TRIANGULAR";

    private TriangularMembershipFunction membershipFunction;

    public FuzzyAroundTriangularFunction() {
        initialize();
    }

    public FuzzyAroundTriangularFunction(final List<Expression> children) throws SQLException {
        super(children);
        initialize();
    }

    private void initialize() {
        Double triangleA = getConstantDoubleArgument(1);
        Double triangleB = getConstantDoubleArgument(2);
        Double triangleC = getConstantDoubleArgument(3);
        membershipFunction = new TriangularMembershipFunction(triangleA, triangleB, triangleC);
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

