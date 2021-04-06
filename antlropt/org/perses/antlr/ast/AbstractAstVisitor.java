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

import java.util.Collection;
import java.util.Map;

public abstract class AbstractAstVisitor {
  public final void postorder(AbstractPersesRuleElement ast) {
    ast.foreachChildRuleElement(this::postorder);
    dispatch(ast);
  }

  public final void preorder(AbstractPersesAst ast) {
    dispatch(ast);
    ast.foreachChild(this::preorder);
  }

  public final void preorderGrammar(
      Collection<Map.Entry<RuleNameRegistry.RuleNameHandle, AbstractPersesRuleElement>> rules) {
    for (Map.Entry<RuleNameRegistry.RuleNameHandle, AbstractPersesRuleElement> entry : rules) {
      preorder(entry.getValue());
    }
  }

  private void dispatch(AbstractPersesAst ast) {
    switch (ast.getTag()) {
      case GRAMMAR:
        visit((PersesGrammar) ast);
        break;
      case RULE_REF:
        visit((PersesRuleReferenceAst) ast);
        break;
      case RULE_DEFINITION_LEXER:
        visit((PersesLexerRuleAst) ast);
        break;
      case RULE_DEFINITION_PARSER:
        visit((PersesParserRuleAst) ast);
        break;
      case LEXER_FRAGMENT_DEFINITION:
        visit((PersesFragmentLexerRuleAst) ast);
        break;
      case ACTION:
        visit((PersesActionAst) ast);
        break;
      case ALTERNATIVE_BLOCK:
        visit((PersesAlternativeBlockAst) ast);
        break;
      case EPSILON:
        visit((PersesEpsilonAst) ast);
        break;
      case LEXER_CHAR_SET:
        visit((PersesLexerCharSet) ast);
        break;
      case LEXER_SKIP_COMMAND:
        visit((PersesLexerSkipCommandAst) ast);
        break;
      case LEXER_CHANNEL_COMMAND:
        visit((PersesLexerChannelCommandAst) ast);
        break;
      case NOT:
        visit((PersesNotAst) ast);
        break;
      case OPTIONAL:
        visit((PersesOptionalAst) ast);
        break;
      case STAR:
        visit((PersesStarAst) ast);
        break;
      case PLUS:
        visit((PersesPlusAst) ast);
        break;
      case SEQUENCE:
        visit((PersesSequenceAst) ast);
        break;
      case TERMINAL:
        visit((PersesTerminalAst) ast);
        break;
      case TOKEN_SET:
        visit((PersesTokenSetAst) ast);
        break;
      case LEXER_RANGE_OPERATOR:
        visit((PersesRangeAst) ast);
        break;
      case ELEMENT_OPTIONS:
        visit((PersesElementOptionsAst) ast);
        break;
      default:
        throw new RuntimeException("Unhandled type: " + ast);
    }
  }

  protected abstract void visit(PersesTokenSetAst ast);

  protected abstract void visit(PersesTerminalAst ast);

  protected abstract void visit(PersesSequenceAst ast);

  protected abstract void visit(PersesPlusAst ast);

  protected abstract void visit(PersesStarAst ast);

  protected abstract void visit(PersesOptionalAst ast);

  protected abstract void visit(PersesNotAst ast);

  protected abstract void visit(PersesLexerSkipCommandAst ast);

  protected abstract void visit(PersesLexerChannelCommandAst ast);

  protected abstract void visit(PersesLexerCharSet ast);

  protected abstract void visit(PersesEpsilonAst ast);

  protected abstract void visit(PersesElementOptionsAst ast);

  protected abstract void visit(PersesAlternativeBlockAst ast);

  protected abstract void visit(PersesActionAst ast);

  protected abstract void visit(PersesFragmentLexerRuleAst ast);

  protected abstract void visit(PersesParserRuleAst ast);

  protected abstract void visit(PersesLexerRuleAst ast);

  protected abstract void visit(PersesRuleReferenceAst ast);

  protected abstract void visit(PersesGrammar ast);

  protected abstract void visit(PersesRangeAst ast);
}
