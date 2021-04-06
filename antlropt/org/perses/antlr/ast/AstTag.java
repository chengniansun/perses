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

public enum AstTag {
  GRAMMAR(Precedence.NONE),
  OPTIONS(Precedence.NONE),
  ELEMENT_OPTIONS(Precedence.NONE),
  NAMED_ACTION(Precedence.NONE),

  RULE_DEFINITION_PARSER(Precedence.RULE),
  RULE_DEFINITION_LEXER(Precedence.RULE),
  LEXER_FRAGMENT_DEFINITION(Precedence.RULE),

  LEXER_SKIP_COMMAND(Precedence.SKIP),
  LEXER_CHANNEL_COMMAND(Precedence.CHANNEL),

  ALTERNATIVE_BLOCK(Precedence.ALTERNATIVE),

  SEQUENCE(Precedence.SEQUENCE),

  NOT(Precedence.NOT),

  STAR(Precedence.QUANTIFIER),
  PLUS(Precedence.QUANTIFIER),
  OPTIONAL(Precedence.QUANTIFIER),

  ACTION(Precedence.UNIT),
  TOKEN_SET(Precedence.UNIT),
  TERMINAL(Precedence.UNIT),
  RULE_REF(Precedence.UNIT),
  LEXER_CHAR_SET(Precedence.UNIT),
  LEXER_RANGE_OPERATOR(Precedence.UNIT),

  EPSILON(Precedence.NONE);

  private final Precedence precedence;

  AstTag(Precedence precedence) {
    this.precedence = precedence;
  }

  public Precedence getPrecedence() {
    return precedence;
  }

  public boolean isQuantifier() {
    return this == STAR || this == PLUS || this == OPTIONAL;
  }
}
