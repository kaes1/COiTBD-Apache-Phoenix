package pl.polsl.udf;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.expression.function.ScalarFunction;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.*;
import pl.polsl.fuzzy.Trapezoid;
import pl.polsl.linguisticValue.LinguisticValueManager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyCreateLinguisticValueFunction.NAME, args = {
        @Argument(allowedTypes = {PVarchar.class}, isConstant=true), // linguistic value namespace
        @Argument(allowedTypes = {PVarchar.class}, isConstant=true),  // linguistic value name
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid A
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid B
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true), // trapezoid C
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}, isConstant=true)  // trapezoid D
})
public class FuzzyCreateLinguisticValueFunction extends ScalarFunction {

    public static final String NAME = "FUZZY_CREATE_LINGUISTIC_VALUE";

    public FuzzyCreateLinguisticValueFunction() {
    }

    public FuzzyCreateLinguisticValueFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Expression arg1Expr = children.get(0);
        if (!arg1Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        String arg1 = (String) PVarchar.INSTANCE.toObject(ptr, arg1Expr.getSortOrder());

        Expression arg2Expr = children.get(1);
        if (!arg2Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        String arg2 = (String) PVarchar.INSTANCE.toObject(ptr, arg2Expr.getSortOrder());

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

        Expression arg6Expr = children.get(5);
        if (!arg6Expr.evaluate(tuple, ptr)) return false;
        if (ptr.getLength() == 0) return true;
        double arg6 = getDoubleArgument(arg6Expr, ptr);

        LinguisticValueManager linguisticValueManager = LinguisticValueManager.getInstance();

        linguisticValueManager.createLinguisticValue(arg1, arg2, new Trapezoid(arg3,arg4,arg5,arg6));

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

    static double getDoubleArgument(Expression exp, ImmutableBytesWritable ptr) {
        if (exp.getDataType() == PDecimal.INSTANCE) {
            return ((BigDecimal) exp.getDataType().toObject(ptr, exp.getSortOrder())).doubleValue();
        } else {
            return exp.getDataType().getCodec().decodeDouble(ptr, exp.getSortOrder());
        }
    }
}

