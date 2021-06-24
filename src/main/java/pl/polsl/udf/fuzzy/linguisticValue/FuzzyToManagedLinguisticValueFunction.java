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
import pl.polsl.linguisticVariable.LinguisticVariable;
import pl.polsl.linguisticVariable.LinguisticVariableManager;
import pl.polsl.udf.ArgumentEvaluationFailedException;
import pl.polsl.udf.UdfBase;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@BuiltInFunction(name = FuzzyToManagedLinguisticValueFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}),   // value
        @Argument(allowedTypes = {PVarchar.class}, isConstant = true)  // linguistic variable name
})
public class FuzzyToManagedLinguisticValueFunction extends UdfBase {

    public static final String NAME = "FUZZY_TO_LINGUISTIC_VALUE";

    LinguisticVariableManager linguisticVariableManager;
    private String linguisticVariableName;
    private LinguisticVariable linguisticVariable;

    public FuzzyToManagedLinguisticValueFunction() {
        initialize();
    }

    public FuzzyToManagedLinguisticValueFunction(final List<Expression> children) throws SQLException {
        super(children);
        initialize();
    }

    private void initialize() {
        linguisticVariableName = getConstantStringArgument(1);
        linguisticVariableManager = LinguisticVariableManager.getInstance();
        linguisticVariable = linguisticVariableManager.getLinguisticVariable(linguisticVariableName)
                .orElseThrow(() -> new IllegalStateException("Cannot find linguistic variable"));
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

        Optional<LinguisticValue> linguisticValue = linguisticVariable.getMatchingLinguisticValue(arg1);

        if (!linguisticValue.isPresent()) {
            return false;
        }

        String result = linguisticValue.get().getName();

        ptr.set(PVarchar.INSTANCE.toBytes(result));
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

