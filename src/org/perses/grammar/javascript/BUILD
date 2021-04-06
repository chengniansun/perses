package(default_visibility = [
    "//visibility:public",
])

load("//antlropt/org/perses/antlr/pnf:pnf.bzl", "pnf_convert")
load(
    "//src/org/perses/grammar:antlr_util.bzl",
    "antlr_codegen",
)
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

pnf_convert(
    name = "normalize_javascript_to_pnf",
    src = "JavaScriptParser.g4",
    out = "PnfJavaScriptParser.g4",
    start_rule_name = "program",
)

antlr_codegen(
    name = "javascript_lexer",
    additional_source_files = [
        "JavaScriptLexerBase.java",
    ],
    java_pkg_name = "org.perses.grammar.javascript",
    names_of_java_files_to_keep = ["JavaScriptLexer.java"],
    parser_grammar_file = "JavaScriptLexer.g4",
)

antlr_codegen(
    name = "javascript_parser",
    additional_source_files = ["JavaScriptParserBase.java"],
    java_pkg_name = "org.perses.grammar.javascript",
    lexer_grammar_file = "JavaScriptLexer.g4",
    names_of_java_files_to_keep = ["JavaScriptParser.java"],
    parser_grammar_file = "JavaScriptParser.g4",
    deps = [":javascript_lexer"],
)

# antlr_codegen(
#     name = "pnf_javascript_parser",
#     parser_grammar_file = "PnfJavaScriptParser.g4",
#     java_pkg_name = "org.perses.grammar.javascript",
#     lexer_grammar_file = "JavaScriptLexer.g4",
#     names_of_java_files_to_keep = [ "PnfJavaScriptParser.java" ],
#     additional_source_files = [ "JavaScriptParserBase.java" ],
#     deps = [
#         ":javascript_lexer",
#     ],
# )

java_library(
    name = "javascript_parser_facade",
    srcs = ["JavaScriptParserFacade.java"],
    deps = [
        ":languge_kind",
        ":javascript_parser",
        # ":pnf_javascript_parser",
        "//src/org/perses/antlr:parse_tree_with_parser",
        "//src/org/perses/grammar:grammar_facade",
        "//src/org/perses/program:language_kind",
        "@maven//:com_google_flogger_flogger",
        "@maven//:org_antlr_antlr4_runtime",
    ],
)

kt_jvm_library(
    name = "languge_kind",
    srcs = ["LanguageJavaScript.kt"],
    deps = [
        "//src/org/perses/program:language_kind",
    ],
)
