package(default_visibility = [
    "//visibility:public",
])

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "command_options",
    srcs = ["CommandOptions.kt"],
    deps = [
        "//src/org/perses/program:enum_format_control",
        "//src/org/perses/reduction:enum_query_caching_control",
        "//src/org/perses/util:command_line_flags",
        "//src/org/perses/util:percentage",
        "//src/org/perses/util:shell",
        "@maven//:com_beust_jcommander",
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_guava_guava",
    ],
)

java_library(
    name = "perses_lib",
    srcs = ["Main.java"],
    deps = [
        ":command_options",
        "//src/org/perses/grammar:facade_factory",
        "//src/org/perses/grammar/c:c_parser_facade",
        "//src/org/perses/grammar/c:language_kind",
        "//src/org/perses/grammar/c:pnfc_parser_facade",
        "//src/org/perses/grammar/go:languge_kind",
        "//src/org/perses/grammar/go:pnfgo_parser_facade",
        "//src/org/perses/grammar/java:java_parser_facade",
        "//src/org/perses/grammar/java:language_kind",
        "//src/org/perses/grammar/javascript:javascript_parser_facade",
        "//src/org/perses/grammar/javascript:languge_kind",
        "//src/org/perses/grammar/rust:language_kind",
        "//src/org/perses/grammar/rust:pnfrust_parser_facade",
        "//src/org/perses/grammar/scala:language_kind",
        "//src/org/perses/grammar/scala:pnfscala_parser_facade",
        "//src/org/perses/program:language_kind",
        "//src/org/perses/reduction:reducer_factory",
        "//src/org/perses/reduction:reduction_driver",
        "//src/org/perses/util:default_logging_configurations",
        "//version/org/perses/version",
        "@maven//:com_beust_jcommander",
        "@maven//:com_google_guava_guava",
        "@maven//:org_antlr_antlr4",
    ],
)

java_binary(
    name = "perses",
    jvm_flags = ["-ea"],
    main_class = "org.perses.Main",
    runtime_deps = [
        ":command_options",
        ":perses_lib",
        "//src/org/perses/grammar:facade_factory",
        "//src/org/perses/grammar/c:c_parser_facade",
        "//src/org/perses/grammar/c:language_kind",
        "//src/org/perses/grammar/c:pnfc_parser_facade",
        "//src/org/perses/grammar/go:languge_kind",
        "//src/org/perses/grammar/go:pnfgo_parser_facade",
        "//src/org/perses/grammar/java:java_parser_facade",
        "//src/org/perses/grammar/java:language_kind",
        "//src/org/perses/grammar/javascript:javascript_parser_facade",
        "//src/org/perses/grammar/javascript:languge_kind",
        "//src/org/perses/grammar/rust:language_kind",
        "//src/org/perses/grammar/rust:pnfrust_parser_facade",
        "//src/org/perses/grammar/scala:language_kind",
        "//src/org/perses/grammar/scala:pnfscala_parser_facade",
        "//src/org/perses/program:language_kind",
        "//src/org/perses/reduction:reducer_factory",
        "//src/org/perses/reduction:reduction_driver",
        "//src/org/perses/util:default_logging_configurations",
        "//version/org/perses/version",
        "@maven//:com_beust_jcommander",
        "@maven//:com_google_flogger_flogger_system_backend",
        "@maven//:com_google_guava_guava",
        "@maven//:org_antlr_antlr4",
    ],
)

java_binary(
    name = "token_counter",
    srcs = ["TokenCounterMain.java"],
    jvm_flags = ["-ea"],
    main_class = "org.perses.TokenCounterMain",
    runtime_deps = [
        "@maven//:com_google_flogger_flogger_system_backend",
    ],
    deps = [
        "//src/org/perses/antlr:parse_tree_with_parser",
        "//src/org/perses/grammar:facade_factory",
        "//src/org/perses/grammar:grammar_facade",
        "//src/org/perses/program",
        "//src/org/perses/program:language_kind",
        "//src/org/perses/reduction:reducer_factory",
        "@maven//:com_google_guava_guava",
        "@maven//:org_antlr_antlr4",
        "@maven//:org_antlr_antlr4_runtime",
    ],
)
