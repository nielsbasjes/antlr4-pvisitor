package nl.example.demo;

import nl.example.demo.ExpressionParser.ExpressionContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoListener extends ExpressionBaseListener<Sum> {

    private static final Logger LOG = LoggerFactory.getLogger(DemoListener.class);

    public static void main(String... args) {
        new DemoListener().run();
    }


    public void run() {
        String demoExpression = "(((1+2)+3)+((4+5)+((6+7)+8)))";

        CodePointCharStream input = CharStreams.fromString(demoExpression);
        ExpressionLexer     lexer = new ExpressionLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ExpressionParser<Sum> parser = new ExpressionParser<>(tokens);

        ExpressionContext<Sum>  expressionContext = parser.expression();
        ExpressionListener<Sum> listener          = new DemoListener();

        Sum sum = new Sum();

        ParseTreeWalker<Sum> walker = new ParseTreeWalker<>();
        walker.walk(listener, expressionContext, sum);
    }

    private void print(String name, ExpressionContext<Sum> ctx, Sum sum) {
        LOG.info(String.format("%-40s --> %d", ctx.toString(), sum.sum));
    }

    @Override
    public void enterSumNumbers(ExpressionParser.SumNumbersContext<Sum> ctx, Sum sum) {
        print("> SUM", ctx, sum);
    }

    @Override
    public void exitSumNumbers(ExpressionParser.SumNumbersContext<Sum> ctx, Sum sum) {
        print("< SUM", ctx, sum);
    }

    @Override
    public void enterNumber(ExpressionParser.NumberContext<Sum> ctx, Sum sum) {
        sum.sum += Long.parseLong(ctx.num.getText());
        print("> NUMBER", ctx, sum);
    }

    @Override
    public void exitNumber(ExpressionParser.NumberContext<Sum> ctx, Sum sum) {
        print("< NUMBER", ctx, sum);
    }

    @Override
    public void enterBracedExpression(ExpressionParser.BracedExpressionContext<Sum> ctx, Sum sum) {
        print("> BRACE OPEN", ctx, sum);
    }

    @Override
    public void exitBracedExpression(ExpressionParser.BracedExpressionContext<Sum> ctx, Sum sum) {
        print("< BRACE CLOSE", ctx, sum);
    }

}
