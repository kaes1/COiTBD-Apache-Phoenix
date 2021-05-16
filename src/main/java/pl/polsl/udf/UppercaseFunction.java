package pl.polsl.udf;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.expression.function.ScalarFunction;
import org.apache.phoenix.parse.FunctionParseNode;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PVarchar;

import java.sql.SQLException;
import java.util.List;

@FunctionParseNode.BuiltInFunction(name = UppercaseFunction.NAME, args = {@FunctionParseNode.Argument(allowedTypes = {PVarchar.class})})
public class UppercaseFunction extends ScalarFunction {
    public static final String NAME = "CUSTOM_UPPERCASE";

    public UppercaseFunction() {
    }

    public UppercaseFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Expression arg1Expr = children.get(0);

        if (!arg1Expr.evaluate(tuple, ptr))
            return false;
        if (ptr.getLength() == 0)
            return true;

        String sourceStr = (String) PVarchar.INSTANCE.toObject(ptr, arg1Expr.getSortOrder());

        if (sourceStr == null) {
            return true;
        }

        String uppercased = sourceStr.toUpperCase();

        ptr.set(PVarchar.INSTANCE.toBytes(uppercased));
        return true;
    }

    @Override
    public PDataType getDataType() {
        return PVarchar.INSTANCE;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
