/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.antlr.v4.runtime.tree;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

public class ParseTreeWalker<P> {
    public static final ParseTreeWalker<?> DEFAULT = new ParseTreeWalker<>();

    public void walk(ParseTreeListener<P> listener, ParseTree<P> t) {
		if ( t instanceof ErrorNode) {
			listener.visitErrorNode((ErrorNode<P>)t);
			return;
		}
		else if ( t instanceof TerminalNode) {
			listener.visitTerminal((TerminalNode<P>)t);
			return;
		}
		RuleNode<P> r = (RuleNode<P>)t;
        enterRule(listener, r);
        int n = r.getChildCount();
        for (int i = 0; i<n; i++) {
            walk(listener, r.getChild(i));
        }
		exitRule(listener, r);
    }

	/**
	 * The discovery of a rule node, involves sending two events: the generic
	 * {@link ParseTreeListener#enterEveryRule} and a
	 * {@link RuleContext}-specific event. First we trigger the generic and then
	 * the rule specific. We to them in reverse order upon finishing the node.
	 */
	protected void enterRule(ParseTreeListener<P> listener, RuleNode<P> r) {
		ParserRuleContext<P> ctx = (ParserRuleContext<P>)r.getRuleContext();
		listener.enterEveryRule(ctx);
		ctx.enterRule(listener);
	}

	protected void exitRule(ParseTreeListener<P> listener, RuleNode<P> r) {
		ParserRuleContext<P> ctx = (ParserRuleContext<P>)r.getRuleContext();
		ctx.exitRule(listener);
		listener.exitEveryRule(ctx);
	}

	public void walk(ParseTreeListener<P> listener, ParseTree<P> t, P parameter) {
		if ( t instanceof ErrorNode) {
			listener.visitErrorNode((ErrorNode<P>)t, parameter);
			return;
		}
		else if ( t instanceof TerminalNode) {
			listener.visitTerminal((TerminalNode<P>)t, parameter);
			return;
		}
		RuleNode<P> r = (RuleNode<P>)t;
		enterRule(listener, r, parameter);
		int n = r.getChildCount();
		for (int i = 0; i<n; i++) {
			walk(listener, r.getChild(i), parameter);
		}
		exitRule(listener, r, parameter);
	}

	/**
	 * The discovery of a rule node, involves sending two events: the generic
	 * {@link ParseTreeListener#enterEveryRule} and a
	 * {@link RuleContext}-specific event. First we trigger the generic and then
	 * the rule specific. We to them in reverse order upon finishing the node.
	 */
    protected void enterRule(ParseTreeListener<P> listener, RuleNode<P> r, P parameter) {
		ParserRuleContext<P> ctx = (ParserRuleContext<P>)r.getRuleContext();
		listener.enterEveryRule(ctx, parameter);
		ctx.enterRule(listener, parameter);
    }

    protected void exitRule(ParseTreeListener<P> listener, RuleNode<P> r, P parameter) {
		ParserRuleContext<P> ctx = (ParserRuleContext<P>)r.getRuleContext();
		ctx.exitRule(listener, parameter);
		listener.exitEveryRule(ctx, parameter);
    }

}
