package pl.polsl.udf;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.expression.function.ScalarFunction;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.fuzzy.FuzzyAround;
import pl.polsl.fuzzy.Trapezoid;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyAroundFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}), // value
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid A
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid B
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid C
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true)  // trapezoid D
})
public class FuzzyAroundFunction extends ScalarFunction {

    public static final String NAME = "FUZZY_AROUND";

    public FuzzyAroundFunction() {
    }

    public FuzzyAroundFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Expression arg1Expr = children.get(0);
        if (!arg1Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        double arg1 = getDoubleArgument(arg1Expr, ptr);

        Expression arg2Expr = children.get(1);
        if (!arg2Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        double arg2 = getDoubleArgument(arg2Expr, ptr);

        Expression arg3Expr = children.get(2);
        if (!arg3Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        double arg3 = getDoubleArgument(arg3Expr, ptr);

        Expression arg4Expr = children.get(3);
        if (!arg4Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        double arg4 = getDoubleArgument(arg4Expr, ptr);

        Expression arg5Expr = children.get(4);
        if (!arg5Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        double arg5 = getDoubleArgument(arg5Expr, ptr);

        double result = FuzzyAround.fuzzyAround(arg1, new Trapezoid(arg2, arg3, arg4, arg5));

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

    static double getDoubleArgument(Expression exp, ImmutableBytesWritable ptr) {
        if (exp.getDataType() == PDecimal.INSTANCE) {
            return ((BigDecimal) exp.getDataType().toObject(ptr, exp.getSortOrder())).doubleValue();
        } else {
            return exp.getDataType().getCodec().decodeDouble(ptr, exp.getSortOrder());
        }
    }
}

