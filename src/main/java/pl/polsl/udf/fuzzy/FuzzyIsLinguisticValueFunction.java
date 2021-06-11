package pl.polsl.udf.fuzzy;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import org.apache.phoenix.schema.types.PVarchar;
import pl.polsl.linguisticVariable.LinguisticValue;
import pl.polsl.linguisticVariable.LinguisticVariableManager;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@BuiltInFunction(name = FuzzyIsLinguisticValueFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}),   // value
        @Argument(allowedTypes = {PVarchar.class}, isConstant=true), // linguistic variable name
        @Argument(allowedTypes = {PVarchar.class}, isConstant=true)  // linguistic value name
})
public class FuzzyIsLinguisticValueFunction extends UdfBase {

    public static final String NAME = "FUZZY_IS_LINGUISTIC_VALUE";

    public FuzzyIsLinguisticValueFunction() {
    }

    public FuzzyIsLinguisticValueFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Double arg1;
        String arg2, arg3;

        try {
            arg1 = getDoubleArgument(0, tuple, ptr);
            if (arg1 == null) return true;
            arg2 = getStringArgument(1, tuple, ptr);
            if (arg2 == null) return true;
            arg3 = getStringArgument(2, tuple, ptr);
            if (arg3 == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        LinguisticVariableManager linguisticVariableManager = LinguisticVariableManager.getInstance();

        Optional<LinguisticValue> linguisticValue = linguisticVariableManager.getLinguisticValue(arg2, arg3);

        if (!linguisticValue.isPresent()) {
            return false;
        }

        double result = linguisticValue.get().getMembershipFunction().calculateMembership(arg1);

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

