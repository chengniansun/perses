struct S {
 int one;
 int two;
};
struct S const foo(void);
struct S tmp;
void priv_sock_init() {
  tmp = (struct S)foo();
}
