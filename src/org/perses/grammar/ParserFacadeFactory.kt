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
package org.perses.grammar

import com.google.common.collect.ImmutableMap
import com.google.common.io.Files
import org.perses.grammar.c.LanguageC
import org.perses.grammar.c.PnfCParserFacade
import org.perses.grammar.go.LanguageGo
import org.perses.grammar.go.PnfGoParserFacade
import org.perses.grammar.java.JavaParserFacade
import org.perses.grammar.java.LanguageJava
import org.perses.grammar.javascript.JavaScriptParserFacade
import org.perses.grammar.javascript.LanguageJavaScript
import org.perses.grammar.rust.LanguageRust
import org.perses.grammar.rust.PnfRustParserFacade
import org.perses.grammar.scala.LanguageScala
import org.perses.grammar.scala.PnfScalaParserFacade
import org.perses.program.LanguageKind
import java.io.File

/** Creates a parser facade, based on the type of language kind.  */
class ParserFacadeFactory private constructor(
  private val language2FacadeMap: ImmutableMap<LanguageKind, () -> AbstractParserFacade>
) {

  fun createParserFacade(languageKind: LanguageKind): AbstractParserFacade {
    require(language2FacadeMap.contains(languageKind)) {
      "Unrecognized language kind $languageKind"
    }
    return language2FacadeMap[languageKind]!!.invoke().also {
      check(it.language == languageKind) {
        "${it.language} != $languageKind"
      }
    }
  }

  private val fileExtToLanguageMap = language2FacadeMap.keys
    .asSequence()
    .flatMap { language -> language.extensions.asSequence().map { it to language } }
    .fold(
      ImmutableMap.builder<String, LanguageKind>(),
      { builder, pair -> builder.put(pair.first, pair.second) }
    )
    .build()

  fun computeLanguageKind(file: File): LanguageKind? {
    val ext = Files.getFileExtension(file.name)
    return fileExtToLanguageMap.get(ext)
  }

  companion object {

    @JvmStatic
    fun builderWithBuiltinLanguages(): Builder {
      val builder = Builder()
      builder.add(LanguageGo) { PnfGoParserFacade() }
      builder.add(LanguageRust) { PnfRustParserFacade() }
      builder.add(LanguageScala) { PnfScalaParserFacade() }
      builder.add(LanguageJava) { JavaParserFacade() }
      builder.add(LanguageC) { PnfCParserFacade() }
      builder.add(LanguageJavaScript) { JavaScriptParserFacade() }
      return builder
    }
  }

  class Builder {
    private val language2FacadeMap =
      ImmutableMap.builder<LanguageKind, () -> AbstractParserFacade>()

    fun add(language: LanguageKind, facadeCreator: () -> AbstractParserFacade): Builder {
      language2FacadeMap.put(language, facadeCreator)
      return this
    }

    fun build() = ParserFacadeFactory(language2FacadeMap.build())
  }
}
