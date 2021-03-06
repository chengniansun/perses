



extern int ilogbf (float);
extern float logbf (float);
extern int ilogb (double);
extern double logb (double);
extern int ilogbl (long double);
extern long double logbl (long double);

extern void link_error(void);

void testf(float x)
{
  if ((int) logbf (x) != ilogbf (x))
    link_error ();
}

void test(double x)
{
  if ((int) logb (x) != ilogb (x))
    link_error ();
}

void testl(long double x)
{
  if ((int) logbl (x) != ilogbl (x))
    link_error ();
}

int main()
{
  testf (2.0f);
  test (2.0);
  testl (2.0l);

  return 0;
}
