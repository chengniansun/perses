


typedef struct {
 int x;
 int y;
} S;

int *a_p;
S a[1000];

void
f(int k)
{
 int i;

 for (i=k; i<1000; i+=k) {
  a_p = &a[i].y;
  *a_p = 100;
        }
}
