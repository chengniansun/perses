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
package org.perses.grammar.javascript;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.perses.antlr.ParseTreeWithParser;
import org.perses.grammar.AbstractDefaultParserFacade;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class JavaScriptParserFacade extends AbstractDefaultParserFacade<JavaScriptLexer, JavaScriptParser> {

  public JavaScriptParserFacade() {
    super(
        LanguageJavaScript.INSTANCE,
        createSeparateAntlrGrammar("JavaScriptParser.g4", "JavaScriptLexer.g4", JavaScriptParserFacade.class));
  }

  @Override
  protected JavaScriptLexer createLexer(ANTLRInputStream inputStream) {
    return new JavaScriptLexer(inputStream);
  }

  @Override
  protected JavaScriptParser createParser(CommonTokenStream tokens) {
    return new JavaScriptParser(tokens);
  }

  @Override
  protected ParseTree startParsing(JavaScriptParser parser) {
    return parser.program();
  }

  public ParseTreeWithParser parseWithOrigJavaScriptParser(File file) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
      return parseWithOrigJavaScriptParser(reader, file.getPath());
    }
  }

  public ParseTreeWithParser parseWithOrigJavaScriptParser(String jsProgram) throws IOException {
    try (BufferedReader reader = new BufferedReader(new StringReader(jsProgram))) {
      return parseWithOrigJavaScriptParser(reader, "<in-memory>");
    }
  }

  public ParseTreeWithParser parseWithOrigJavaScriptParser(String jsProgram, String fileName)
      throws IOException {
    try (BufferedReader reader = new BufferedReader(new StringReader(jsProgram))) {
      return parseWithOrigJavaScriptParser(reader, fileName);
    }
  }

  private static ParseTreeWithParser parseWithOrigJavaScriptParser(BufferedReader reader, String fileName)
      throws IOException {
    return parseReader(fileName, reader, JavaScriptLexer::new, JavaScriptParser::new, JavaScriptParser::program);
  }
}
