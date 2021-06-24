package pl.polsl.udf.fuzzy.logic;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.fuzzyMath.FuzzyLogicOperators;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyNotFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}),   // value
})
public class FuzzyNotFunction extends UdfBase {

    public static final String NAME = "FUZZY_NOT";

    public FuzzyNotFunction() {
    }

    public FuzzyNotFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Double argument;

        try {
            argument = getDoubleArgument(0, tuple, ptr);
            if (argument == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        double result = FuzzyLogicOperators.fuzzyNot(argument);

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

