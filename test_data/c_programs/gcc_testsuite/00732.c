struct z1
{
  double x[4];
};

struct z1 a = { 5.0, 6.0, 7.0, 8.0 };

struct z2
{
  float x[3];
};

struct z2 b = { 13.f, 14.f, 15.f };
struct z2 c = { 16.f, 17.f, 18.f };


enum aapcs64_test_kind
{
  TK_PARAM = 0,
  TK_VA_ARG,
  TK_RETURN
};

int which_kind_of_test;

extern int printf (const char*, ...);
extern void abort (void);
extern void dumpregs () __asm("myfunc");
enum structure_type
{
  flat = 0,
  i32in128,
  f32in64,
  i8in64,
  i16in64,
  i32in64,
};



typedef long unsigned int size_t;
int memcmp (void *, void *, size_t);



const int element_size[] = { 1, 4, 4, 1, 2, 4 };
const int block_reverse_size[] = { 1, 16, 8, 8, 8, 8 };

int
validate_memory (void *mem1, char *mem2, size_t size, enum structure_type type)
{
  unsigned int i;
  char *cmem1 = (char *) mem1;
  switch (type)
    {
    case f32in64:
    case i32in128:
    default:
      break;
    }
  return memcmp (mem1, mem2, size);
}
void testfunc(char* stack)
{


  { struct z1 __x = a; ; if (validate_memory (&__x, stack + 0, sizeof (struct z1), flat) != 0) abort(); }
  { double __x = 9.0; ; if (validate_memory (&__x, stack + 32, sizeof (double), flat) != 0) abort(); }
  { double __x = 10.0; ; if (validate_memory (&__x, stack + 40, sizeof (double), flat) != 0) abort(); }
  { struct z2 __x = b; ; if (validate_memory (&__x, stack + 336, sizeof (struct z2), flat) != 0) abort(); }

  { float __x = 15.5f; ; if (validate_memory (&__x, stack + 336 +16, sizeof (float), flat) != 0) abort(); }



  { struct z2 __x = c; ; if (validate_memory (&__x, stack + 336 +24, sizeof (struct z2), flat) != 0) abort(); }

  return;
}






void myfunc(


  struct z1,
  double,
  double,
  struct z2,

  float,



  struct z2

) ;
int main()
{
  which_kind_of_test = TK_PARAM;
  myfunc(






  a,
  9.0,
  10.0,
  b,

  15.5f,



  c

);
  return 0;
}

