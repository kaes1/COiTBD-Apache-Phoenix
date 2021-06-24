package pl.polsl.udf.fuzzy.logic;

import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.expression.function.JavaMathTwoArgumentFunction;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.fuzzyMath.FuzzyLogicOperators;

import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyAndFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}),
        @Argument(allowedTypes = {PDouble.class, PDecimal.class})
})
public class FuzzyAndFunction extends JavaMathTwoArgumentFunction {

    public static final String NAME = "FUZZY_AND";

    public FuzzyAndFunction() {
    }

    public FuzzyAndFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    protected double compute(double firstArg, double secondArg) {
        return FuzzyLogicOperators.fuzzyAnd(firstArg, secondArg);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

