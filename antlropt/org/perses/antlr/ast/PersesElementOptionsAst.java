/*
 * Copyright (C) 2018-2020 University of Waterloo.
 *
 * This file is part of Perses.
 *
 * Perses is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3, or (at your option) any later version.
 *
 * Perses is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Perses; see the file LICENSE.  If not see <http://www.gnu.org/licenses/>.
 */
package org.perses.antlr.ast;

import com.google.common.base.MoreObjects;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.tool.ast.GrammarAST;

import java.io.PrintStream;
import java.util.List;

public class PersesElementOptionsAst extends AbstractPersesRuleElement {

  public static PersesElementOptionsAst INSTANCE = new PersesElementOptionsAst();

  private PersesElementOptionsAst() {}

  public static boolean isElementOptionsAst(GrammarAST ast) {
    return ast.getToken().getType() == ANTLRParser.ELEMENT_OPTIONS;
  }

  @Override
  public void toSourceCode(PrintStream stream, int indent, boolean multiLineMode) {
    stream.print("/* Element options. */");
  }

  @Deprecated
  @Override
  public AbstractPersesRuleElement getChild(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getChildCount() {
    return 0;
  }

  @Override
  public AstTag getTag() {
    return AstTag.ELEMENT_OPTIONS;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("code", getSourceCode()).toString();
  }

  @Override
  public AbstractPersesRuleElement createWithNewChildren(
      List<AbstractPersesRuleElement> newChildren) {
    return INSTANCE;
  }
}
