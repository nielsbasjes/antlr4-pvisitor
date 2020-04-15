/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.antlr.v4.runtime.tree;

import org.antlr.v4.runtime.ParserRuleContext;

/** This interface describes the minimal core of methods triggered
 *  by {@link ParseTreeWalker}. E.g.,
 *
 *  	ParseTreeWalker walker = new ParseTreeWalker();
 *		walker.walk(myParseTreeListener, myParseTree); <-- triggers events in your listener
 *
 *  If you want to trigger events in multiple listeners during a single
 *  tree walk, you can use the ParseTreeDispatcher object available at
 *
 * 		https://github.com/antlr/antlr4/issues/841
 */
public interface ParseTreeListener<P> {
	void visitTerminal(TerminalNode<P> node);
	void visitErrorNode(ErrorNode<P> node);
    void enterEveryRule(ParserRuleContext<P> ctx);
    void exitEveryRule(ParserRuleContext<P> ctx);

    void visitTerminal(TerminalNode<P> node, P parameter);
    void visitErrorNode(ErrorNode<P> node, P parameter);
    void enterEveryRule(ParserRuleContext<P> ctx, P parameter);
    void exitEveryRule(ParserRuleContext<P> ctx, P parameter);
}
