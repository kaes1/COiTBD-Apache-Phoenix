package pl.polsl.udf.fuzzy.linguisticValue;

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

@BuiltInFunction(name = FuzzyIsManagedLinguisticValueFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}),   // value
        @Argument(allowedTypes = {PVarchar.class}, isConstant = true), // linguistic variable name
        @Argument(allowedTypes = {PVarchar.class}, isConstant = true)  // linguistic value name
})
public class FuzzyIsManagedLinguisticValueFunction extends UdfBase {

    public static final String NAME = "FUZZY_IS_LINGUISTIC_VALUE";

    LinguisticVariableManager linguisticVariableManager;
    private String linguisticVariableName;
    private String linguisticValueName;
    private LinguisticValue linguisticValue;

    public FuzzyIsManagedLinguisticValueFunction() {
        initialize();
    }

    public FuzzyIsManagedLinguisticValueFunction(final List<Expression> children) throws SQLException {
        super(children);
        initialize();
    }

    private void initialize() {
        linguisticVariableName = getConstantStringArgument(1);
        linguisticValueName = getConstantStringArgument(2);
        linguisticVariableManager = LinguisticVariableManager.getInstance();
        linguisticValue = linguisticVariableManager.getLinguisticValue(linguisticVariableName, linguisticValueName)
                .orElseThrow(() -> new IllegalStateException("Cannot find linguistic value"));
    }

    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable ptr) {
        Double arg1;

        try {
            arg1 = getDoubleArgument(0, tuple, ptr);
            if (arg1 == null) return true;
        } catch (ArgumentEvaluationFailedException exception) {
            return false;
        }

        double result = linguisticValue.getMembershipFunction().calculateMembership(arg1);

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

