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

// TODO: I should deprecate this class. This default visitor might makes subclass error-prone to
//      miss some cases.
public class DefaultAstVisitor extends AbstractAstVisitor {

  @Override
  protected void visit(PersesTokenSetAst ast) {}

  @Override
  protected void visit(PersesRangeAst ast) {}

  @Override
  protected void visit(PersesTerminalAst ast) {}

  @Override
  protected void visit(PersesSequenceAst ast) {}

  @Override
  protected void visit(PersesPlusAst ast) {}

  @Override
  protected void visit(PersesStarAst ast) {}

  @Override
  protected void visit(PersesOptionalAst ast) {}

  @Override
  protected void visit(PersesNotAst ast) {}

  @Override
  protected void visit(PersesLexerSkipCommandAst ast) {}

  @Override
  protected void visit(PersesLexerChannelCommandAst ast) {}

  @Override
  protected void visit(PersesLexerCharSet ast) {}

  @Override
  protected void visit(PersesEpsilonAst ast) {}

  @Override
  protected void visit(PersesAlternativeBlockAst ast) {}

  @Override
  protected void visit(PersesActionAst ast) {}

  @Override
  protected void visit(PersesFragmentLexerRuleAst ast) {}

  @Override
  protected void visit(PersesParserRuleAst ast) {}

  @Override
  protected void visit(PersesLexerRuleAst ast) {}

  @Override
  protected void visit(PersesRuleReferenceAst ast) {}

  @Override
  protected void visit(PersesGrammar ast) {}

  @Override
  protected void visit(PersesElementOptionsAst ast) {}
}
