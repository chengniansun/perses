package(default_visibility = [
    "//visibility:public",
])

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

java_library(
    name = "grammar_facade",
    srcs = [
        "AbstractDefaultParserFacade.java",
        "AbstractParserFacade.java",
        "AntlrFailureException.java",
        "FailOnErrorAntlrErrorListener.java",
    ],
    deps = [
        "//antlropt/org/perses/antlr/ast",
        "//src/org/perses/antlr:antlr_grammar_info",
        "//src/org/perses/antlr:antlr_grammar_parser",
        "//src/org/perses/antlr:antlr_grammar_util",
        "//src/org/perses/antlr:antlr_hierarchy",
        "//src/org/perses/antlr:parse_tree_with_parser",
        "//src/org/perses/program",
        "//src/org/perses/program:language_kind",
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_guava_guava",
        "@maven//:org_antlr_antlr4",
        "@maven//:org_antlr_antlr4_runtime",
    ],
)

kt_jvm_library(
    name = "facade_factory",
    srcs = ["ParserFacadeFactory.kt"],
    deps = [
        ":grammar_facade",
        "//src/org/perses/grammar/c:c_parser_facade",
        "//src/org/perses/grammar/c:pnfc_parser_facade",
        "//src/org/perses/grammar/go:pnfgo_parser_facade",
        "//src/org/perses/grammar/java:java_parser_facade",
        "//src/org/perses/grammar/javascript:javascript_parser_facade",
        "//src/org/perses/grammar/rust:pnfrust_parser_facade",
        "//src/org/perses/grammar/scala:pnfscala_parser_facade",
        "//src/org/perses/program:language_kind",
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_guava_guava",
    ],
)

java_binary(
    name = "antlr_tool",
    main_class = "org.antlr.v4.Tool",
    visibility = ["//visibility:public"],
    runtime_deps = ["@maven//:org_antlr_antlr4"],
)

sh_library(
    name = "antlr_merge_grammar_sed_scripts",
    data = [
        "antlr_merge_options.sed",
        "antlr_remove_grammar.sed",
        "antlr_remove_options.sed",
    ],
)

sh_binary(
    name = "antlr_merge_grammar",
    srcs = ["antlr_merge_grammar.sh"],
    data = [":antlr_merge_grammar_sed_scripts"],
    deps = ["@bazel_tools//tools/bash/runfiles"],
)
