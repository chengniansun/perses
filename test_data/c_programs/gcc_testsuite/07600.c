





extern void foo(void) __attribute__ ((visibility ("hidden")));
typedef void (*foo_t)(void);
foo_t test = foo;
