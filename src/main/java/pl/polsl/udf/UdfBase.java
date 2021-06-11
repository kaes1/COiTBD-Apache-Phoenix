package pl.polsl.udf;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.expression.function.ScalarFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PVarchar;

import java.math.BigDecimal;
import java.util.List;

public abstract class UdfBase extends ScalarFunction {

    public UdfBase() {
    }

    public UdfBase(List<Expression> children) {
        super(children);
    }

    protected String getStringArgument(int argumentNumber, Tuple tuple, ImmutableBytesWritable ptr) throws ArgumentEvaluationFailedException {
        Expression argumentExpression = children.get(argumentNumber);
        if (!argumentExpression.evaluate(tuple, ptr)) throw new ArgumentEvaluationFailedException();
        if (ptr.getLength() == 0) return null;
        return (String) PVarchar.INSTANCE.toObject(ptr, argumentExpression.getSortOrder());
    }

    protected Double getDoubleArgument(int argumentNumber, Tuple tuple, ImmutableBytesWritable ptr) throws ArgumentEvaluationFailedException {
        Expression argumentExpression = children.get(argumentNumber);
        if (!argumentExpression.evaluate(tuple, ptr)) throw new ArgumentEvaluationFailedException();
        if (ptr.getLength() == 0) return null;
        return getDoubleArgument(argumentExpression, ptr);
    }

    protected static double getDoubleArgument(Expression argumentExpression, ImmutableBytesWritable ptr) {
        if (argumentExpression.getDataType() == PDecimal.INSTANCE) {
            return ((BigDecimal) argumentExpression.getDataType().toObject(ptr, argumentExpression.getSortOrder())).doubleValue();
        } else {
            return argumentExpression.getDataType().getCodec().decodeDouble(ptr, argumentExpression.getSortOrder());
        }
    }
}
