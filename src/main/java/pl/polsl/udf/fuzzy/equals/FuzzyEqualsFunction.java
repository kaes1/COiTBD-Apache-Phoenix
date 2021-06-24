package pl.polsl.udf.fuzzy.equals;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.fuzzyMath.FuzzyEquals;
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

    private Double fuzzify1;
    private Double fuzzify2;

    public FuzzyEqualsFunction() {
        initialize();
    }

    public FuzzyEqualsFunction(final List<Expression> children) throws SQLException {
        super(children);
        initialize();
    }

    private void initialize() {
        fuzzify1 = getConstantDoubleArgument(1);
        fuzzify2 = getConstantDoubleArgument(3);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Double value1, value2;

        try {
            value1 = getDoubleArgument(0, tuple, ptr);
            if (value1 == null) return true;
            value2 = getDoubleArgument(2, tuple, ptr);
            if (value2 == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        double result = FuzzyEquals.fuzzifyAndCalculateEquals(value1, fuzzify1, value2, fuzzify2);

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

