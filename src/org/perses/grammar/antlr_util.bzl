def antlr_codegen(
        name,
        parser_grammar_file,
        java_pkg_name,
        names_of_java_files_to_keep,
        additional_source_files = [],
        lexer_grammar_file = None,
        deps = None):
    genrule_name = "%s_gen" % name

    current_pkg_name = native.package_name()
    java_pkg_folder = java_pkg_name.replace(".", "/")
    if not current_pkg_name.endswith(java_pkg_folder):
        fail("The current pkg name '%s' should end with %s" % (current_pkg_name, java_pkg_folder))

    commands = [
        "GRAMMAR_FILE=$(location %s)" % parser_grammar_file,
        "GRAMMAR_FILE_NAME=$$(basename $${GRAMMAR_FILE})",
        "TMP=$$(mktemp -d)",
        "cp $${GRAMMAR_FILE} $${TMP}",
    ]
    common_antlr_args = [
        "\"$(location //src/org/perses/grammar:antlr_tool)\"",
        "-no-listener",
        "-no-visitor",
        "-lib \"$${TMP}\"",  # For antlr to locate the tokens files generated from the lexer grammar.
        "-package %s" % java_pkg_name,
    ]
    if lexer_grammar_file:
        commands.append("LEXER_GRAMMAR_FILE=$(location %s)" % lexer_grammar_file)
        commands.append("LEXER_GRAMMAR_FILE_NAME=$$(basename $${LEXER_GRAMMAR_FILE})")
        commands.append("cp $${LEXER_GRAMMAR_FILE} $${TMP}")

        antlr_args_for_lexer = common_antlr_args[:] + ["\"$${TMP}/$${LEXER_GRAMMAR_FILE_NAME}\""]
        commands.append(" ".join(antlr_args_for_lexer))

    antlr_args_for_parser = common_antlr_args[:] + ["\"$${TMP}/$${GRAMMAR_FILE_NAME}\""]
    commands.append(" ".join(antlr_args_for_parser))

    for file_to_copy in names_of_java_files_to_keep:
        commands.append("cp \"$${TMP}/%s\" $(location %s)" % (file_to_copy, file_to_copy))

    grammar_files = [parser_grammar_file] + ([lexer_grammar_file] if lexer_grammar_file else [])
    native.genrule(
        name = genrule_name,
        outs = names_of_java_files_to_keep,
        srcs = grammar_files,
        cmd = " ; \\\n".join(commands),
        tools = ["//src/org/perses/grammar:antlr_tool"],
    )
    deps = deps or []
    native.java_library(
        name = name,
        srcs = names_of_java_files_to_keep + additional_source_files,
        exports = deps,
        resources = grammar_files,
        deps = ["@maven//:org_antlr_antlr4_runtime"] + deps,
    )

def antlr_codegen_lexer(
        name,
        lexer_grammar_file,
        java_pkg_name,
        lexer_java_file_name):
    antlr_codegen(
        name = name,
        parser_grammar_file = lexer_grammar_file,
        java_pkg_name = java_pkg_name,
        names_of_java_files_to_keep = [lexer_java_file_name],
    )

def antlr_codegen_parser(
        name,
        parser_grammar_file,
        java_pkg_name,
        parser_java_file_name,
        lexer_grammar_file = None,
        deps = None):
    deps = deps or []
    antlr_codegen(
        name = name,
        parser_grammar_file = parser_grammar_file,
        java_pkg_name = java_pkg_name,
        lexer_grammar_file = lexer_grammar_file,
        names_of_java_files_to_keep = [parser_java_file_name],
        deps = deps,
    )

def antlr_codegen_combined(
        name,
        grammar_file,
        java_pkg_name,
        lexer_java_file_name,
        parser_java_file_name,
        deps = None):
    deps = deps or []

    lexer_rule = "%s_lexer_" % name
    antlr_codegen_lexer(
        name = lexer_rule,
        lexer_grammar_file = grammar_file,
        java_pkg_name = java_pkg_name,
        lexer_java_file_name = lexer_java_file_name,
    )

    parser_rule = "%s_parser_" % name
    antlr_codegen_parser(
        name = parser_rule,
        parser_grammar_file = grammar_file,
        java_pkg_name = java_pkg_name,
        parser_java_file_name = parser_java_file_name,
    )
    native.java_library(
        name = name,
        exports = [
            lexer_rule,
            parser_rule,
        ],
    )
