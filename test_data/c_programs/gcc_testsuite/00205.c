

void __attribute__((mips16)) f1 (void);
void (*ptr1) (void) = f1;

void __attribute__((nomips16)) f2 (void);
void (*ptr2) (void) = f2;
