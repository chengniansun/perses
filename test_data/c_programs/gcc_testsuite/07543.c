




short a[16];
short d[16];

int foo ()
{
  int i;
  short b[16] = {0,3,6,9,12,15,18,21,24,27,30,33,36,39,42,45};
  short c[16] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};


  for (i = 0; i < 16/2; i++)
    {
      a[i] = b[2*i+1] * c[2*i+1] - b[2*i] * c[2*i];
      d[i] = b[2*i] * c[2*i+1] + b[2*i+1] * c[2*i];
    }

  return 0;
}
