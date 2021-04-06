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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.tool.ast.ActionAST;
import org.antlr.v4.tool.ast.AltAST;
import org.antlr.v4.tool.ast.BlockAST;
import org.antlr.v4.tool.ast.GrammarAST;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.antlr.v4.tool.ast.NotAST;
import org.antlr.v4.tool.ast.OptionalBlockAST;
import org.antlr.v4.tool.ast.PlusBlockAST;
import org.antlr.v4.tool.ast.RangeAST;
import org.antlr.v4.tool.ast.RuleAST;
import org.antlr.v4.tool.ast.RuleRefAST;
import org.antlr.v4.tool.ast.SetAST;
import org.antlr.v4.tool.ast.StarBlockAST;
import org.antlr.v4.tool.ast.TerminalAST;
import org.perses.antlr.AntlrGrammarParser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public final class PersesAstBuilder {

  private final SymbolTable symbolTable = new SymbolTable();

  public static PersesGrammar loadGrammarFromString(String grammarContent) {
    return new PersesAstBuilder()
        .buildFromAntlrRootAst(AntlrGrammarParser.parseRawGrammarASTFromString(grammarContent));
  }

  public PersesGrammar buildFromAntlrRootAst(GrammarRootAST root) {
    final int childCount = root.getChildCount();
    checkArgument(childCount >= 2, "Invalid child count: %s", childCount);

    final GrammarAST firstChild = (GrammarAST) root.getChild(0);
    final String grammarName = firstChild.getToken().getText();

    ImmutableList<AbstractPersesRuleDefAst> rules = null;
    PersesGrammarOptionsAst options = PersesGrammarOptionsAst.EMPTY;
    ImmutableList.Builder<PersesNamedAction> namedActions = ImmutableList.builder();

    for (int i = 1; i < childCount; ++i) {
      final GrammarAST child = (GrammarAST) root.getChild(i);
      if (isOptionsNode(child)) {
        checkState(options == PersesGrammarOptionsAst.EMPTY, "duplicate options node: %s", options);
        options = convertOptions(child);
      } else if (isRulesNode(child)) {
        checkState(rules == null, "duplicate rules node: %s", rules);
        rules = convertRuleDefinitions(child, symbolTable);
      } else if (isNamedActionNode(child)) {
        checkState(child.getChildCount() >= 2);
        final String name = extractNameFromNamedActionNode(child);
        final GrammarAST lastChild = (GrammarAST) child.getChild(child.getChildCount() - 1);
        checkState(lastChild instanceof ActionAST);
        final String body = lastChild.getText();
        namedActions.add(new PersesNamedAction(name, body));
      } else {
        // TODO (vinced): JavaScriptLexer.g4 contains: channels { ERROR }
        // Unhandled child: channels {
        // error(177): <empty>:510:19: ERROR is not a recognized channel name.
        // throw new RuntimeException("Unhandled child: " + child);
      }
    }
    checkNotNull(rules, "No rules found in %s", root);
    final PersesGrammar.GrammarType grammarType = identifyGrammarType(root);
    return new PersesGrammar(
        grammarType, grammarName, options, namedActions.build(), rules, symbolTable);
  }

  private static String extractNameFromNamedActionNode(GrammarAST node) {
    checkArgument(isNamedActionNode(node));
    final int childCount = node.getChildCount();
    final StringBuilder builder = new StringBuilder();
    checkState(childCount > 1);
    final GrammarAST firstChild = (GrammarAST) node.getChild(0);
    builder.append(firstChild.getText());

    for (int i = 1; i < childCount - 1; ++i) {
      builder.append("::").append(node.getChild(i).getText());
    }
    return builder.toString();
  }

  private static PersesGrammar.GrammarType identifyGrammarType(GrammarRootAST root) {
    switch (root.getText()) {
      case "COMBINED_GRAMMAR":
        return PersesGrammar.GrammarType.COMBINED;
      case "PARSER_GRAMMAR":
        return PersesGrammar.GrammarType.PARSER;
      case "LEXER_GRAMMAR":
        return PersesGrammar.GrammarType.LEXER;
      default:
        throw new RuntimeException("Unsupported grammar type: " + root.getText());
    }
  }

  private PersesGrammarOptionsAst convertOptions(GrammarAST secondChild) {
    final ImmutableList.Builder<PersesGrammarOptionsAst.Option> builder = ImmutableList.builder();
    final int childCount = secondChild.getChildCount();
    for (int i = 0; i < childCount; ++i) {
      final GrammarAST child = (GrammarAST) secondChild.getChild(i);
      checkState(child.getText().equals("="));
      checkState(child.getChildCount() == 2);
      builder.add(
          new PersesGrammarOptionsAst.Option(
              child.getChild(0).getText(), child.getChild(1).getText()));
    }
    return new PersesGrammarOptionsAst(builder.build());
  }

  private static boolean isOptionsNode(GrammarAST ast) {
    return ast.getText().equals("OPTIONS");
  }

  private static boolean isRulesNode(GrammarAST ast) {
    return ast.getText().equals("RULES");
  }

  private static boolean isNamedActionNode(GrammarAST ast) {
    return ast.getText().equals("@");
  }

  private static ImmutableList<AbstractPersesRuleDefAst> convertRuleDefinitions(
      GrammarAST rulesAst, SymbolTable symbolTable) {
    checkArgument("RULES".equals(rulesAst.getToken().getText()));
    final ImmutableList.Builder<AbstractPersesRuleDefAst> builder = ImmutableList.builder();
    final int childCount = rulesAst.getChildCount();
    for (int i = 0; i < childCount; ++i) {
      final GrammarAST childTree = (GrammarAST) rulesAst.getChild(i);
      checkState(childTree instanceof RuleAST, "The actual type is %s", childTree.getClass());
      final RuleAST ast = (RuleAST) childTree;
      checkState("RULE".equals(ast.getToken().getText()));
      builder.add(convertRuleDefinition(ast, symbolTable));
    }
    return builder.build();
  }

  private static AbstractPersesRuleDefAst convertRuleDefinition(
      RuleAST ruleAST, SymbolTable symbolTable) {
    final int childCount = ruleAST.getChildCount();
    checkArgument(childCount == 2 || childCount == 3);
    checkArgument(ruleAST.getChild(0) instanceof GrammarAST);
    checkArgument(ruleAST.getChild(childCount - 1) instanceof BlockAST);

    final GrammarAST ruleNameAst = (GrammarAST) ruleAST.getChild(0);
    final RuleNameRegistry.RuleNameHandle ruleNameHandle =
        symbolTable.getRuleNameRegistry().getOrCreate(ruleNameAst.getToken().getText());

    BlockAST ruleBody = (BlockAST) ruleAST.getChild(childCount - 1);

    if (childCount == 3) {
      GrammarAST second = (GrammarAST) ruleAST.getChild(1);
      checkArgument(second.getText().equals("RULEMODIFIERS"));
      checkArgument(second.getChildCount() == 1);
      checkArgument(second.getChild(0).getText().equals("fragment"));

      AbstractPersesRuleElement body = convertAlternativeBlock(ruleBody, symbolTable);
      return new PersesFragmentLexerRuleAst(ruleNameHandle, body);
    }
    checkState(childCount == 2, "Invalid child count: %s", childCount);

    if (!ruleAST.isLexerRule()) {
      AbstractPersesRuleElement body = convertAlternativeBlock(ruleBody, symbolTable);
      return createRuleDef(ruleNameHandle, body);
    }
    if (ruleBody.getChildCount() == 1
        && ((GrammarAST) ruleBody.getChild(0)).getToken().getType()
            == ANTLRParser.LEXER_ALT_ACTION) {
      AltAST lexerAltAction = (AltAST) ruleBody.getChild(0);
      checkState(lexerAltAction.getChildCount() == 2);
      GrammarAST firstChild = (GrammarAST) lexerAltAction.getChild(0);
      checkState(firstChild instanceof AltAST);
      AbstractPersesRuleElement body = convertSingleAlternative((AltAST) firstChild, symbolTable);
      GrammarAST secondChild = (GrammarAST) lexerAltAction.getChild(1);
      switch (secondChild.getText()) {
        case "skip":
          return createRuleDef(ruleNameHandle, new PersesLexerSkipCommandAst(body));
        case "LEXER_ACTION_CALL":
          checkState(secondChild.getChildCount() == 2);
          checkState(secondChild.getChild(0).getText().equals("channel"));
          final String channelName = secondChild.getChild(1).getText();
          return createRuleDef(ruleNameHandle, new PersesLexerChannelCommandAst(channelName, body));
        default:
          throw new RuntimeException("Unhandled: " + secondChild.getText());
      }
    } else {
      AbstractPersesRuleElement body = convertAlternativeBlock(ruleBody, symbolTable);
      return createRuleDef(ruleNameHandle, body);
    }
  }

  public static AbstractPersesRuleDefAst createRuleDef(
      RuleNameRegistry.RuleNameHandle ruleNameHandle, AbstractPersesRuleElement body) {
    switch (ruleNameHandle.getType()) {
      case KLEENE_PLUS:
      case KLEENE_STAR:
      case OPTIONAL:
      case OTHER_RULE:
      case ALT_BLOCKS:
        return new PersesParserRuleAst(ruleNameHandle, body);
      case TOKEN:
        return new PersesLexerRuleAst(ruleNameHandle, body);
      default:
        throw new RuntimeException("Unhandled rule type: " + ruleNameHandle);
    }
  }

  private static AbstractPersesRuleElement convertAlternativeBlock(
      BlockAST block, SymbolTable symbolTable) {
    final LinkedHashSet<Class<?>> childNodeClasses = collectChildNodeClasses(block);
    checkArgument(childNodeClasses.size() == 1, childNodeClasses);
    checkArgument(childNodeClasses.iterator().next().equals(AltAST.class));

    ImmutableList.Builder<AbstractPersesRuleElement> builder = ImmutableList.builder();
    final int childCount = block.getChildCount();
    for (int i = 0; i < childCount; ++i) {
      final AltAST child = (AltAST) block.getChild(i);
      AbstractPersesRuleElement convertedChild = convertSingleAlternative(child, symbolTable);
      if (convertedChild instanceof PersesTokenSetAst
          || convertedChild instanceof PersesAlternativeBlockAst) {
        // Inline the alternative block.
        for (int j = 0, size = convertedChild.getChildCount(); j < size; ++j) {
          builder.add(convertedChild.getChild(j));
        }
      } else {
        builder.add(convertedChild);
      }
    }
    ImmutableList<AbstractPersesRuleElement> alternatives = builder.build();
    checkState(alternatives.size() > 0, alternatives);
    if (alternatives.size() == 1) {
      return alternatives.get(0);
    } else {
      return new PersesAlternativeBlockAst(alternatives);
    }
  }

  private static AbstractPersesRuleElement convertSingleAlternative(
      AltAST ast, SymbolTable symbolTable) {
    final int astTokenType = ast.getToken().getType();
    checkArgument(astTokenType == ANTLRParser.LEXER_ALT_ACTION || astTokenType == ANTLRParser.ALT);
    if (astTokenType == ANTLRParser.LEXER_ALT_ACTION) {
      checkArgument(ast.getChildCount() == 2);
      GrammarAST child = (GrammarAST) ast.getChild(0);
      checkArgument(
          child instanceof AltAST && ((AltAST) child).getToken().getType() == ANTLRParser.ALT);
      ast = (AltAST) child;
    }

    ArrayList<AbstractPersesRuleElement> children = new ArrayList<>();
    final int childCount = ast.getChildCount();
    for (int i = 0; i < childCount; ++i) {
      AbstractPersesRuleElement convertedChild =
          dispatchConversion((GrammarAST) ast.getChild(i), symbolTable);
      children.add(convertedChild);
    }
    return combineIntoSequence(children);
  }

  public static AbstractPersesRuleElement combineIntoSequence(
      List<AbstractPersesRuleElement> children) {
    if (children.size() == 1) {
      return children.get(0);
    }
    ImmutableList.Builder<AbstractPersesRuleElement> builder = ImmutableList.builder();
    for (AbstractPersesRuleElement child : children) {
      final AstTag tag = child.getTag();
      if (tag == AstTag.EPSILON) {
        continue;
      }
      if (tag == AstTag.SEQUENCE) {
        child.foreachChildRuleElement(builder::add);
      } else {
        builder.add(child);
      }
    }
    ImmutableList<AbstractPersesRuleElement> processedChildren = builder.build();
    checkState(processedChildren.size() > 0);
    if (processedChildren.size() == 1) {
      return processedChildren.get(0);
    }
    return new PersesSequenceAst(processedChildren);
  }

  @VisibleForTesting
  static PersesRuleReferenceAst convertRuleRefAst(RuleRefAST ruleRefAst, SymbolTable symbolTable) {
    final RuleNameRegistry.RuleNameHandle ruleNameHandle =
        symbolTable.getRuleNameRegistry().getOrCreate(ruleRefAst.getToken().getText());
    return new PersesRuleReferenceAst(ruleNameHandle);
  }

  static PersesOptionalAst convertOptionalBlockAst(OptionalBlockAST ast, SymbolTable symbolTable) {
    final AbstractPersesRuleElement body = checkAndConvertBodyOfQuantifiedBlock(ast, symbolTable);
    return ast.isGreedy()
        ? PersesOptionalAst.createGreedy(body)
        : PersesOptionalAst.createNonGreedy(body);
  }

  static PersesStarAst convertStarBlockAst(StarBlockAST ast, SymbolTable symbolTable) {
    final AbstractPersesRuleElement body = checkAndConvertBodyOfQuantifiedBlock(ast, symbolTable);
    return ast.isGreedy() ? PersesStarAst.createGreedy(body) : PersesStarAst.createNonGreedy(body);
  }

  static PersesPlusAst convertPlusBlockAst(PlusBlockAST ast, SymbolTable symbolTable) {
    final AbstractPersesRuleElement body = checkAndConvertBodyOfQuantifiedBlock(ast, symbolTable);
    return ast.isGreedy() ? PersesPlusAst.createGreedy(body) : PersesPlusAst.createNonGreedy(body);
  }

  private static AbstractPersesRuleElement checkAndConvertBodyOfQuantifiedBlock(
      GrammarAST ast, SymbolTable symbolTable) {
    checkArgument(ast.getChildCount() == 1);
    checkArgument(ast.getChild(0) instanceof BlockAST);
    BlockAST block = (BlockAST) ast.getChild(0);
    return dispatchConversion(block, symbolTable);
  }

  private static AbstractPersesRuleElement convertTerminalSetAst(GrammarAST ast) {
    checkArgument(isTerminalSetAst(ast), "Unhandled ast: %s, %s", ast, ast.getClass());
    checkArgument(ast.getToken().getText().equals("SET"));

    ImmutableList.Builder<AbstractPersesTerminalAst> builder = ImmutableList.builder();
    final int childCount = ast.getChildCount();
    for (int i = 0; i < childCount; ++i) {
      final GrammarAST child = (GrammarAST) ast.getChild(i);
      if (child instanceof TerminalAST) {
        TerminalAST terminal = (TerminalAST) child;
        builder.add(convertTerminalAst(terminal));
      } else if (PersesLexerCharSet.isLexerCharSet(child)) {
        builder.add(new PersesLexerCharSet(child.getText()));
      } else {
        throw new RuntimeException("Unreachable. " + child);
      }
    }
    ImmutableList<AbstractPersesTerminalAst> terminals = builder.build();
    checkState(terminals.size() > 0);
    if (terminals.size() == 1) {
      return terminals.get(0);
    } else {
      return new PersesTokenSetAst(terminals);
    }
  }

  private static PersesNotAst convertNotAst(NotAST notAST, SymbolTable symbolTable) {
    checkArgument(notAST.getChildCount() == 1);
    GrammarAST child = (GrammarAST) notAST.getChild(0);
    checkArgument(child instanceof GrammarAST);
    GrammarAST childAst = (GrammarAST) child;
    checkArgument(isTerminalSetAst(childAst));
    AbstractPersesRuleElement tokens = dispatchConversion(childAst, symbolTable);
    return new PersesNotAst(tokens);
  }

  @VisibleForTesting
  static AbstractPersesRuleElement dispatchConversion(GrammarAST ast, SymbolTable symbolTable) {
    if (ast instanceof TerminalAST) {
      TerminalAST terminal = (TerminalAST) ast;
      return convertTerminalAst(terminal);
    }
    if (ast instanceof RuleRefAST) {
      return convertRuleRefAst((RuleRefAST) ast, symbolTable);
    }
    if (ast instanceof OptionalBlockAST) {
      return convertOptionalBlockAst((OptionalBlockAST) ast, symbolTable);
    }
    if (ast instanceof PlusBlockAST) {
      return convertPlusBlockAst((PlusBlockAST) ast, symbolTable);
    }
    if (ast instanceof StarBlockAST) {
      return convertStarBlockAst((StarBlockAST) ast, symbolTable);
    }
    if (ast instanceof BlockAST) {
      return convertAlternativeBlock((BlockAST) ast, symbolTable);
    }
    if (ast instanceof RangeAST) {
      return convertRangeAST((RangeAST) ast);
    }
    if (ast instanceof ActionAST) {
      final ActionAST action = (ActionAST) ast;
      return new PersesActionAst(action.getText());
    }
    if (isTerminalSetAst(ast)) {
      return convertTerminalSetAst(ast);
    }
    if (ast instanceof NotAST) {
      return convertNotAst((NotAST) ast, symbolTable);
    }
    if (PersesEpsilonAst.isEpsilonAst(ast)) {
      return PersesEpsilonAst.INSTANCE;
    }
    if (PersesLexerCharSet.isLexerCharSet(ast)) {
      return new PersesLexerCharSet(ast.getText());
    }
    if (PersesElementOptionsAst.isElementOptionsAst(ast)) {
      return PersesElementOptionsAst.INSTANCE;
    }

    throw new RuntimeException("Unhandled ast: " + ast + ", " + ast.getClass());
  }

  private static PersesTerminalAst convertTerminalAst(TerminalAST terminal) {
    return new PersesTerminalAst(terminal.getText(), terminal.getToken().getType());
  }

  private static AbstractPersesRuleElement convertRangeAST(RangeAST ast) {
    Preconditions.checkArgument(ast.getChildCount() == 2);
    final GrammarAST first = (GrammarAST) ast.getChild(0);
    final GrammarAST second = (GrammarAST) ast.getChild(1);
    Preconditions.checkState(first instanceof TerminalAST);
    Preconditions.checkState(second instanceof TerminalAST);
    return new PersesRangeAst(
        convertTerminalAst((TerminalAST) first), convertTerminalAst((TerminalAST) second));
  }

  private static boolean isTerminalSetAst(GrammarAST ast) {
    if (ast instanceof SetAST) {
      return true;
    }
    if (ast.getToken().getType() == ANTLRParser.SET) {
      return true;
    }
    return false;
  }

  private static LinkedHashSet<Class<?>> collectChildNodeClasses(BlockAST ast) {
    LinkedHashSet<Class<?>> childNodeClasses = new LinkedHashSet<>();
    final int childCount = ast.getChildCount();
    for (int i = 0; i < childCount; ++i) {
      childNodeClasses.add(ast.getChild(i).getClass());
    }
    return childNodeClasses;
  }
}
