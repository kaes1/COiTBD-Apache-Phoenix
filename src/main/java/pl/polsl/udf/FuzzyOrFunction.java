package pl.polsl.udf;

import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.expression.function.JavaMathTwoArgumentFunction;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.types.PDecimal;
import org.apache.phoenix.schema.types.PDouble;
import pl.polsl.fuzzy.FuzzyOr;

import java.sql.SQLException;
import java.util.List;

@BuiltInFunction(name = FuzzyOrFunction.NAME, args = {
        @Argument(allowedTypes = {PDouble.class, PDecimal.class}),
        @Argument(allowedTypes = {PDouble.class, PDecimal.class})
})
public class FuzzyOrFunction extends JavaMathTwoArgumentFunction {

    public static final String NAME = "FUZZY_OR";

    public FuzzyOrFunction() {
    }

    public FuzzyOrFunction(final List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    protected double compute(double firstArg, double secondArg) {
        return FuzzyOr.fuzzyOr(firstArg, secondArg);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

