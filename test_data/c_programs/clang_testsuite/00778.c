typedef struct {
  int op;
} event_t;
event_t test(int X) {
  event_t foo = { 1 }, bar = { 2 };
  return X ? foo : bar;
}
