package nl.example.demo;

import nl.example.demo.ExpressionParser.ExpressionContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo extends ExpressionBaseVisitor<String, String> {

    private static final Logger LOG = LoggerFactory.getLogger(Demo.class);

    public static void main(String... args) {
        new Demo().run();
    }

    public void run() {
        String demoExpression = "(((1+2)+3)+((4+5)+((6+7)+8)))";

        CodePointCharStream input = CharStreams.fromString(demoExpression);
        ExpressionLexer     lexer = new ExpressionLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ExpressionParser<String> parser = new ExpressionParser<>(tokens);

        ExpressionContext<String>         expressionContext = parser.expression();
        ExpressionVisitor<String, String> visitor           = new Demo();
        visitor.visit(expressionContext, demoExpression);
    }

    @Override
    public String visitNumber(ExpressionParser.NumberContext<String> ctx, String parameter) {
        LOG.info("NUM: {} --> {}", parameter, ctx.getText());
        return visitChildren(ctx, parameter + " --> " + ctx.getText());
    }

    @Override
    public String visitSumNumbers(ExpressionParser.SumNumbersContext<String> ctx, String parameter) {
        LOG.info("SUM: {} --> {}", parameter, ctx.getText());
        return visitChildren(ctx, parameter + " --> " + ctx.getText());
    }

    @Override
    public String visitBracedExpression(ExpressionParser.BracedExpressionContext<String> ctx, String parameter) {
        LOG.info("BRA: {} --> {}", parameter, ctx.getText());
        return visitChildren(ctx, parameter + " --> " + ctx.getText());
    }
}
