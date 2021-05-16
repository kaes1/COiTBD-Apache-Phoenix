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
import org.apache.phoenix.schema.types.PVarchar;
import pl.polsl.fuzzy.FuzzyAround;
import pl.polsl.linguisticValue.LinguisticValue;
import pl.polsl.linguisticValue.LinguisticValueManager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@BuiltInFunction(name = FuzzyIsLinguisticValueFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}), // value
        @Argument(allowedTypes = {PVarchar.class}, isConstant=true) // linguistic value name
})
public class FuzzyIsLinguisticValueFunction extends ScalarFunction {

    public static final String NAME = "FUZZY_IS_LINGUISTIC_VALUE";

    public FuzzyIsLinguisticValueFunction() {
    }

    public FuzzyIsLinguisticValueFunction(final List<Expression> children) throws SQLException {
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
        String arg2 = (String) PVarchar.INSTANCE.toObject(ptr, arg2Expr.getSortOrder());

        LinguisticValueManager linguisticValueManager = LinguisticValueManager.getInstance();

        Optional<LinguisticValue> linguisticValue = linguisticValueManager.getLinguisticValue(arg2);

        if (!linguisticValue.isPresent()) {
            return false;
        }

        double result = FuzzyAround.fuzzyAround(arg1, linguisticValue.get().getMembershipTrapezoid());

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

