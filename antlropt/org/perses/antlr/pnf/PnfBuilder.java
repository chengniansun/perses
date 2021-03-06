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
package org.perses.antlr.pnf;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import org.perses.antlr.ast.AbstractPersesRuleElement;
import org.perses.antlr.ast.AstTag;
import org.perses.antlr.ast.PersesGrammar;
import org.perses.antlr.ast.PersesSequenceAst;
import org.perses.antlr.ast.RuleNameRegistry.RuleNameHandle;

import java.util.List;
import java.util.stream.Collectors;

public class PnfBuilder {

  private final PersesGrammar grammar;

  public PnfBuilder(PersesGrammar grammar) {
    this.grammar = grammar;
  }

  public static PnfBuilder create(PersesGrammar grammar) {
    return new PnfBuilder(grammar);
  }

  public PersesGrammar build() {
    final ImmutableRuleDefMap ruleDefMap = ImmutableRuleDefMap.create(this.grammar);
    normalizeLeft(ruleDefMap);
    return null;
  }

  @VisibleForTesting
  static void normalizeLeft(ImmutableRuleDefMap ruleDefMap) {
    RuleTransitionGraph ruleTransitionGraph = RuleTransitionGraph.createForLeftmostTransition(ruleDefMap);
    List<ImmutableRuleDefMap> sccs =
        ruleTransitionGraph.computeSccSet().stream()
            .map(scc -> ruleDefMap.project(scc.vertexSet()))
            .collect(Collectors.toList());
//    for (RuleDefMap scc : sccs) {
//      RuleDefMap mapAfterTransformation = transformLeftForScc(scc);
//      introduceQuantifiersLeft(scc);
//    }
  }

  @VisibleForTesting
  static ImmutableRuleDefMap introduceQuantifiersLeft(ImmutableRuleDefMap ruleDefMap) {
    return null;
  }

  @VisibleForTesting
  static void introducePlusLeft(
      LinkedHashMultimap<RuleNameHandle, AbstractPersesRuleElement> ruleDefMap, RuleNameHandle ruleName) {
    for (AbstractPersesRuleElement def : ruleDefMap.get(ruleName)) {
      final AstTag tag = def.getTag();
      Preconditions.checkState(tag != AstTag.ALTERNATIVE_BLOCK);
      if (tag != AstTag.SEQUENCE) {
        continue;
      }
      final PersesSequenceAst seq = (PersesSequenceAst) def;
      for (int i = 0, size = seq.getChildCount(); i < size; ++i) {
        final AbstractPersesRuleElement child = seq.getChild(i);
        final AstTag childTag = child.getTag();
        Preconditions.checkState(childTag != AstTag.SEQUENCE);
        if (childTag == AstTag.STAR) {

        }
      }
    }
  }


}
