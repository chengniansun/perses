package(default_visibility = ["//test/org/perses:__subpackages__"])

load("//src/org/perses:reduction.bzl", "reduce")
load("//test/org/perses:test.bzl", "golden_test")
load("//benchmark:benchmark.bzl", "reduction_golden_test", "sanity_test")

sanity_test(
    name = "sanity_test",
    source_file = "hello.rs",
    test_script = "r.sh",
)

filegroup(
    name = "rs_print",
    srcs = [
        "hello.rs",
        "r.sh",
    ],
)

reduce(
    name = "reduce_rs_print",
    enable_edit_caching = True,
    enable_query_caching = True,
    progress_dump_file = "progress_dump.txt",
    reduction_algorithm = "perses_node_with_dfs_delta",
    result_file = "hello_reduced.rs",
    source_file = "hello.rs",
    statistics_file = "statistics.txt",
    test_script = "r.sh",
)

genrule(
    name = "format_rs_from_reduced_rs_print",
    srcs = ["hello_reduced.rs"],
    outs = ["golden_file_format_rs_from_reduced_rs_print.rs"],
    cmd = "$(location rustfmt_wrapper.sh) $(location hello_reduced.rs) $@",
    tools = ["rustfmt_wrapper.sh"],
)

reduce(
    name = "reduce_rs_print_with_formatter",
    call_formatter = True,
    enable_edit_caching = True,
    enable_query_caching = True,
    reduction_algorithm = "perses_node_with_dfs_delta",
    result_file = "hello_reduced_formatted.rs",
    source_file = "hello.rs",
    test_script = "r.sh",
)

golden_test(
    name = "golden_test_reduce_rs_print_with_formatter",
    golden_file = "golden_file_format_rs_from_reduced_rs_print.rs",
    test_file = "hello_reduced_formatted.rs",
)

reduction_golden_test(
    name = "reduction_golden_test",
    enable_edit_caching = True,
    enable_query_caching = True,
    golden_progress_file = "golden_reduction_progress.txt",
    golden_reduced_file = "golden_reduced_hello.rs",
    progress_dump_file = "reduction_progress.txt",
    reduction_algorithm = "perses_node_priority_with_dfs_delta",
    source_file = "hello.rs",
    test_script = "r.sh",
)

filegroup(
    name = "rs_print_reduced",
    srcs = [
        "hello_reduced.rs",
        "progress_dump.txt",
        "statistics.txt",
    ],
)
