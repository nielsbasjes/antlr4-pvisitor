package nl.example.demo;

import nl.example.demo.ExpressionParser.ExpressionContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo extends ExpressionBaseVisitor<Void, Pair<Long, String>> {

    private static final Logger LOG = LoggerFactory.getLogger(Demo.class);

    public static void main(String... args) {
        new Demo().run();
    }

    public void run() {
        String demoExpression = "(((1+2)+3)+((4+5)+((6+7)+8)))";

        CodePointCharStream input = CharStreams.fromString(demoExpression);
        ExpressionLexer     lexer = new ExpressionLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ExpressionParser<Pair<Long, String>> parser = new ExpressionParser<>(tokens);

        ExpressionContext<Pair<Long, String>>         expressionContext = parser.expression();
        ExpressionVisitor<Void, Pair<Long, String>> visitor           = new Demo();
        visitor.visit(expressionContext, new Pair<>(1L, "1"));
    }

    @Override
    public Void visitNumber(ExpressionParser.NumberContext<Pair<Long, String>> ctx, Pair<Long, String> parameter) {
        Long count = parameter.a;
        String path = parameter.b;
        LOG.info("NUM: {} \t\t\t\t\t: {}", path, ctx.getText());
        count++;
        path += " --> " + count;
        return visitChildren(ctx, new Pair<>(count, path));
    }

    @Override
    public Void visitSumNumbers(ExpressionParser.SumNumbersContext<Pair<Long, String>> ctx, Pair<Long, String> parameter) {
        Long count = parameter.a;
        String path = parameter.b;
        LOG.info("SUM: {} \t\t\t\t\t: {}", path, ctx.getText());
        count++;
        path += " --> " + count;
        return visitChildren(ctx, new Pair<>(count, path));
    }

    @Override
    public Void visitBracedExpression(ExpressionParser.BracedExpressionContext<Pair<Long, String>> ctx, Pair<Long, String> parameter) {
        Long count = parameter.a;
        String path = parameter.b;
        LOG.info("BRA: {} \t\t\t\t\t: {}", path, ctx.getText());
        count++;
        path += " --> " + count;
        return visitChildren(ctx, new Pair<>(count, path));
    }

//    @Override
//    public Void visitTerminal(TerminalNode<Pair<Long, String>> node, Pair<Long, String> parameter) {
//        Long count = parameter.a;
//        String path = parameter.b;
//        LOG.info("---: {} \t\t\t\t\t: {}", path, node.getText());
//        return null;
//    }
}
