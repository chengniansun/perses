package(default_visibility = ["//test/org/perses:__subpackages__"])

load("//src/org/perses:reduction.bzl", "reduce")
load("//benchmark:benchmark.bzl", "reduction_golden_test", "sanity_test")

sanity_test(
    name = "sanity_test",
    source_file = "small.js",
    test_script = "r.sh",
)

filegroup(
    name = "js_print",
    srcs = [
        "r.sh",
        "small.js",
    ],
)

reduce(
    name = "reduce_js_print",
    code_format = "COMPACT_ORIG_FORMAT",
    enable_edit_caching = True,
    enable_query_caching = True,
    progress_dump_file = "progress_dump.txt",
    reduction_algorithm = "perses_node_with_dfs_delta",
    result_file = "small_reduced.js",
    source_file = "small.js",
    statistics_file = "statistics.txt",
    test_script = "r.sh",
)

# reduction_golden_test(
#     name = "reduction_golden_test",
#     enable_edit_caching = True,
#     enable_query_caching = True,
#     golden_progress_file = "golden_reduction_progress.txt",
#     golden_reduced_file = "golden_reduced_small.js",
#     progress_dump_file = "reduction_progress.txt",
#     reduction_algorithm = "perses_node_priority_with_dfs_delta",
#     source_file = "small.js",
#     test_script = "r.sh",
# )

filegroup(
    name = "js_print_reduced",
    srcs = [
        "progress_dump.txt",
        "small_reduced.js",
        "statistics.txt",
    ],
)
