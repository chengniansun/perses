


extern void bar (void);

void foo1 (unsigned int i)
{
  if (i & 4)
    bar ();
}

void foo2 (unsigned int i)
{
  if (i & 0x80000000)
    bar ();
}

void foo3 (unsigned char c)
{
  if (c & 4)
    bar ();
}

void foo4 (unsigned char c)
{
  if (c & 0x80)
    bar ();
}
