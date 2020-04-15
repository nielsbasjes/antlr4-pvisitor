package nl.example.demo;

import nl.example.demo.ExpressionParser.ExpressionContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoVisitor extends ExpressionBaseVisitor<Long, Pair<Long, String>> {

    private static final Logger LOG = LoggerFactory.getLogger(DemoVisitor.class);

    public static void main(String... args) {
        new DemoVisitor().run();
    }

    public void run() {
        String demoExpression = "(((1+2)+3)+((4+5)+((6+7)+8)))";

        CodePointCharStream                         input             = CharStreams.fromString(demoExpression);
        ExpressionLexer                             lexer             = new ExpressionLexer(input);
        CommonTokenStream                           tokens            = new CommonTokenStream(lexer);
        ExpressionParser<Pair<Long, String>>        parser            = new ExpressionParser<>(tokens);
        ExpressionContext<Pair<Long, String>>       expressionContext = parser.expression();
        ExpressionVisitor<Long, Pair<Long, String>> visitor           = new DemoVisitor();

        LOG.info("end result: {}", visitor.visit(expressionContext, new Pair<>(0L, "")));
    }

    private void print(String name, Pair<Long, String> parameter, ExpressionContext<Pair<Long, String>> ctx) {
        LOG.info(String.format("%5s = %-50s %4d --> %s", name, parameter.b, parameter.a, ctx.toString()));
    }

    @Override
    protected Long defaultResult() {
        return 0L;
    }

    @Override
    public Long visitNumber(ExpressionParser.NumberContext<Pair<Long, String>> ctx, Pair<Long, String> parameter) {
        print("NUM", parameter, ctx);
        return Long.parseLong(ctx.num.getText());
    }

    @Override
    public Long visitSumNumbers(ExpressionParser.SumNumbersContext<Pair<Long, String>> ctx, Pair<Long, String> parameter) {
        Long   count = parameter.a;
        String path  = parameter.b;
        print("SUM", parameter, ctx);
        count++;
        path += " --> " + count;

        Pair<Long, String> nextParameter = new Pair<>(count, path);
        Long sum = 0L;
        for (ExpressionContext<Pair<Long, String>> expression : ctx.expression()) {
            sum += visit(expression, nextParameter);
        }
        return sum;
    }

    @Override
    public Long visitBracedExpression(ExpressionParser.BracedExpressionContext<Pair<Long, String>> ctx, Pair<Long, String> parameter) {
        Long   count = parameter.a;
        String path  = parameter.b;
        print("BRA", parameter, ctx);
        count++;
        path += " --> " + count;
        return visit(ctx.expr, new Pair<>(count, path));
    }
}
