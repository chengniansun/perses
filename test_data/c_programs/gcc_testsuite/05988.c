




struct BUF1
{
  int b1;
  int b12;
};

void link_error();

int foo(struct BUF1 * p)
{
    int i = 0;

    for (i = 0; i < 1024*1024; i++)



      p->b1 = 1;

    if (p->b1 != 1)
      link_error ();
    return 0;
}

int main() { return 0; }
