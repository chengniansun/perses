typedef __attribute__((vector_size(16))) signed char vec_schar;
typedef __attribute__((vector_size(16))) signed short vec_sshort;
typedef __attribute__((vector_size(16))) signed int vec_sint;
typedef __attribute__((vector_size(16))) signed long long vec_slong;
typedef __attribute__((vector_size(16))) unsigned char vec_uchar;
typedef __attribute__((vector_size(16))) unsigned short vec_ushort;
typedef __attribute__((vector_size(16))) unsigned int vec_uint;
typedef __attribute__((vector_size(16))) unsigned long long vec_ulong;
typedef __attribute__((vector_size(16))) double vec_double;
volatile vec_schar vsc;
volatile vec_sshort vss;
volatile vec_sint vsi;
volatile vec_slong vsl;
volatile vec_uchar vuc;
volatile vec_ushort vus;
volatile vec_uint vui;
volatile vec_ulong vul;
volatile vec_double vd;
volatile unsigned int len;
const void * volatile cptr;
void * volatile ptr;
int cc;
void test_core(void) {
  len = __builtin_s390_lcbb(cptr, 0);
  len = __builtin_s390_lcbb(cptr, 15);
  vsc = __builtin_s390_vlbb(cptr, 0);
  vsc = __builtin_s390_vlbb(cptr, 15);
  vsc = __builtin_s390_vll(len, cptr);
  vul = __builtin_s390_vpdi(vul, vul, 0);
  vul = __builtin_s390_vpdi(vul, vul, 15);
  vuc = __builtin_s390_vperm(vuc, vuc, vuc);
  vuc = __builtin_s390_vpklsh(vus, vus);
  vus = __builtin_s390_vpklsf(vui, vui);
  vui = __builtin_s390_vpklsg(vul, vul);
  vuc = __builtin_s390_vpklshs(vus, vus, &cc);
  vus = __builtin_s390_vpklsfs(vui, vui, &cc);
  vui = __builtin_s390_vpklsgs(vul, vul, &cc);
  vsc = __builtin_s390_vpksh(vss, vss);
  vss = __builtin_s390_vpksf(vsi, vsi);
  vsi = __builtin_s390_vpksg(vsl, vsl);
  vsc = __builtin_s390_vpkshs(vss, vss, &cc);
  vss = __builtin_s390_vpksfs(vsi, vsi, &cc);
  vsi = __builtin_s390_vpksgs(vsl, vsl, &cc);
  __builtin_s390_vstl(vsc, len, ptr);
  vss = __builtin_s390_vuphb(vsc);
  vsi = __builtin_s390_vuphh(vss);
  vsl = __builtin_s390_vuphf(vsi);
  vss = __builtin_s390_vuplb(vsc);
  vsi = __builtin_s390_vuplhw(vss);
  vsl = __builtin_s390_vuplf(vsi);
  vus = __builtin_s390_vuplhb(vuc);
  vui = __builtin_s390_vuplhh(vus);
  vul = __builtin_s390_vuplhf(vui);
  vus = __builtin_s390_vupllb(vuc);
  vui = __builtin_s390_vupllh(vus);
  vul = __builtin_s390_vupllf(vui);
}
void test_integer(void) {
  vuc = __builtin_s390_vaq(vuc, vuc);
  vuc = __builtin_s390_vacq(vuc, vuc, vuc);
  vuc = __builtin_s390_vaccq(vuc, vuc);
  vuc = __builtin_s390_vacccq(vuc, vuc, vuc);
  vuc = __builtin_s390_vaccb(vuc, vuc);
  vus = __builtin_s390_vacch(vus, vus);
  vui = __builtin_s390_vaccf(vui, vui);
  vul = __builtin_s390_vaccg(vul, vul);
  vsc = __builtin_s390_vavgb(vsc, vsc);
  vss = __builtin_s390_vavgh(vss, vss);
  vsi = __builtin_s390_vavgf(vsi, vsi);
  vsl = __builtin_s390_vavgg(vsl, vsl);
  vuc = __builtin_s390_vavglb(vuc, vuc);
  vus = __builtin_s390_vavglh(vus, vus);
  vui = __builtin_s390_vavglf(vui, vui);
  vul = __builtin_s390_vavglg(vul, vul);
  vui = __builtin_s390_vcksm(vui, vui);
  vuc = __builtin_s390_vclzb(vuc);
  vus = __builtin_s390_vclzh(vus);
  vui = __builtin_s390_vclzf(vui);
  vul = __builtin_s390_vclzg(vul);
  vuc = __builtin_s390_vctzb(vuc);
  vus = __builtin_s390_vctzh(vus);
  vui = __builtin_s390_vctzf(vui);
  vul = __builtin_s390_vctzg(vul);
  vuc = __builtin_s390_verimb(vuc, vuc, vuc, 0);
  vuc = __builtin_s390_verimb(vuc, vuc, vuc, 255);
  vus = __builtin_s390_verimh(vus, vus, vus, 0);
  vus = __builtin_s390_verimh(vus, vus, vus, 255);
  vui = __builtin_s390_verimf(vui, vui, vui, 0);
  vui = __builtin_s390_verimf(vui, vui, vui, 255);
  vul = __builtin_s390_verimg(vul, vul, vul, 0);
  vul = __builtin_s390_verimg(vul, vul, vul, 255);
  vuc = __builtin_s390_verllb(vuc, len);
  vus = __builtin_s390_verllh(vus, len);
  vui = __builtin_s390_verllf(vui, len);
  vul = __builtin_s390_verllg(vul, len);
  vuc = __builtin_s390_verllvb(vuc, vuc);
  vus = __builtin_s390_verllvh(vus, vus);
  vui = __builtin_s390_verllvf(vui, vui);
  vul = __builtin_s390_verllvg(vul, vul);
  vus = __builtin_s390_vgfmb(vuc, vuc);
  vui = __builtin_s390_vgfmh(vus, vus);
  vul = __builtin_s390_vgfmf(vui, vui);
  vuc = __builtin_s390_vgfmg(vul, vul);
  vus = __builtin_s390_vgfmab(vuc, vuc, vus);
  vui = __builtin_s390_vgfmah(vus, vus, vui);
  vul = __builtin_s390_vgfmaf(vui, vui, vul);
  vuc = __builtin_s390_vgfmag(vul, vul, vuc);
  vsc = __builtin_s390_vmahb(vsc, vsc, vsc);
  vss = __builtin_s390_vmahh(vss, vss, vss);
  vsi = __builtin_s390_vmahf(vsi, vsi, vsi);
  vuc = __builtin_s390_vmalhb(vuc, vuc, vuc);
  vus = __builtin_s390_vmalhh(vus, vus, vus);
  vui = __builtin_s390_vmalhf(vui, vui, vui);
  vss = __builtin_s390_vmaeb(vsc, vsc, vss);
  vsi = __builtin_s390_vmaeh(vss, vss, vsi);
  vsl = __builtin_s390_vmaef(vsi, vsi, vsl);
  vus = __builtin_s390_vmaleb(vuc, vuc, vus);
  vui = __builtin_s390_vmaleh(vus, vus, vui);
  vul = __builtin_s390_vmalef(vui, vui, vul);
  vss = __builtin_s390_vmaob(vsc, vsc, vss);
  vsi = __builtin_s390_vmaoh(vss, vss, vsi);
  vsl = __builtin_s390_vmaof(vsi, vsi, vsl);
  vus = __builtin_s390_vmalob(vuc, vuc, vus);
  vui = __builtin_s390_vmaloh(vus, vus, vui);
  vul = __builtin_s390_vmalof(vui, vui, vul);
  vsc = __builtin_s390_vmhb(vsc, vsc);
  vss = __builtin_s390_vmhh(vss, vss);
  vsi = __builtin_s390_vmhf(vsi, vsi);
  vuc = __builtin_s390_vmlhb(vuc, vuc);
  vus = __builtin_s390_vmlhh(vus, vus);
  vui = __builtin_s390_vmlhf(vui, vui);
  vss = __builtin_s390_vmeb(vsc, vsc);
  vsi = __builtin_s390_vmeh(vss, vss);
  vsl = __builtin_s390_vmef(vsi, vsi);
  vus = __builtin_s390_vmleb(vuc, vuc);
  vui = __builtin_s390_vmleh(vus, vus);
  vul = __builtin_s390_vmlef(vui, vui);
  vss = __builtin_s390_vmob(vsc, vsc);
  vsi = __builtin_s390_vmoh(vss, vss);
  vsl = __builtin_s390_vmof(vsi, vsi);
  vus = __builtin_s390_vmlob(vuc, vuc);
  vui = __builtin_s390_vmloh(vus, vus);
  vul = __builtin_s390_vmlof(vui, vui);
  vuc = __builtin_s390_vpopctb(vuc);
  vus = __builtin_s390_vpopcth(vus);
  vui = __builtin_s390_vpopctf(vui);
  vul = __builtin_s390_vpopctg(vul);
  vuc = __builtin_s390_vsq(vuc, vuc);
  vuc = __builtin_s390_vsbiq(vuc, vuc, vuc);
  vuc = __builtin_s390_vscbiq(vuc, vuc);
  vuc = __builtin_s390_vsbcbiq(vuc, vuc, vuc);
  vuc = __builtin_s390_vscbib(vuc, vuc);
  vus = __builtin_s390_vscbih(vus, vus);
  vui = __builtin_s390_vscbif(vui, vui);
  vul = __builtin_s390_vscbig(vul, vul);
  vuc = __builtin_s390_vsldb(vuc, vuc, 0);
  vuc = __builtin_s390_vsldb(vuc, vuc, 15);
  vuc = __builtin_s390_vsl(vuc, vuc);
  vuc = __builtin_s390_vslb(vuc, vuc);
  vuc = __builtin_s390_vsra(vuc, vuc);
  vuc = __builtin_s390_vsrab(vuc, vuc);
  vuc = __builtin_s390_vsrl(vuc, vuc);
  vuc = __builtin_s390_vsrlb(vuc, vuc);
  vui = __builtin_s390_vsumb(vuc, vuc);
  vui = __builtin_s390_vsumh(vus, vus);
  vul = __builtin_s390_vsumgh(vus, vus);
  vul = __builtin_s390_vsumgf(vui, vui);
  vuc = __builtin_s390_vsumqf(vui, vui);
  vuc = __builtin_s390_vsumqg(vul, vul);
  len = __builtin_s390_vtm(vuc, vuc);
  vsc = __builtin_s390_vceqbs(vsc, vsc, &cc);
  vss = __builtin_s390_vceqhs(vss, vss, &cc);
  vsi = __builtin_s390_vceqfs(vsi, vsi, &cc);
  vsl = __builtin_s390_vceqgs(vsl, vsl, &cc);
  vsc = __builtin_s390_vchbs(vsc, vsc, &cc);
  vss = __builtin_s390_vchhs(vss, vss, &cc);
  vsi = __builtin_s390_vchfs(vsi, vsi, &cc);
  vsl = __builtin_s390_vchgs(vsl, vsl, &cc);
  vsc = __builtin_s390_vchlbs(vuc, vuc, &cc);
  vss = __builtin_s390_vchlhs(vus, vus, &cc);
  vsi = __builtin_s390_vchlfs(vui, vui, &cc);
  vsl = __builtin_s390_vchlgs(vul, vul, &cc);
}
void test_string(void) {
  vuc = __builtin_s390_vfaeb(vuc, vuc, 0);
  vuc = __builtin_s390_vfaeb(vuc, vuc, 15);
  vus = __builtin_s390_vfaeh(vus, vus, 0);
  vus = __builtin_s390_vfaeh(vus, vus, 15);
  vui = __builtin_s390_vfaef(vui, vui, 0);
  vui = __builtin_s390_vfaef(vui, vui, 15);
  vuc = __builtin_s390_vfaezb(vuc, vuc, 0);
  vuc = __builtin_s390_vfaezb(vuc, vuc, 15);
  vus = __builtin_s390_vfaezh(vus, vus, 0);
  vus = __builtin_s390_vfaezh(vus, vus, 15);
  vui = __builtin_s390_vfaezf(vui, vui, 0);
  vui = __builtin_s390_vfaezf(vui, vui, 15);
  vuc = __builtin_s390_vfeeb(vuc, vuc);
  vus = __builtin_s390_vfeeh(vus, vus);
  vui = __builtin_s390_vfeef(vui, vui);
  vuc = __builtin_s390_vfeezb(vuc, vuc);
  vus = __builtin_s390_vfeezh(vus, vus);
  vui = __builtin_s390_vfeezf(vui, vui);
  vuc = __builtin_s390_vfeneb(vuc, vuc);
  vus = __builtin_s390_vfeneh(vus, vus);
  vui = __builtin_s390_vfenef(vui, vui);
  vuc = __builtin_s390_vfenezb(vuc, vuc);
  vus = __builtin_s390_vfenezh(vus, vus);
  vui = __builtin_s390_vfenezf(vui, vui);
  vuc = __builtin_s390_vistrb(vuc);
  vus = __builtin_s390_vistrh(vus);
  vui = __builtin_s390_vistrf(vui);
  vuc = __builtin_s390_vstrcb(vuc, vuc, vuc, 0);
  vuc = __builtin_s390_vstrcb(vuc, vuc, vuc, 15);
  vus = __builtin_s390_vstrch(vus, vus, vus, 0);
  vus = __builtin_s390_vstrch(vus, vus, vus, 15);
  vui = __builtin_s390_vstrcf(vui, vui, vui, 0);
  vui = __builtin_s390_vstrcf(vui, vui, vui, 15);
  vuc = __builtin_s390_vstrczb(vuc, vuc, vuc, 0);
  vuc = __builtin_s390_vstrczb(vuc, vuc, vuc, 15);
  vus = __builtin_s390_vstrczh(vus, vus, vus, 0);
  vus = __builtin_s390_vstrczh(vus, vus, vus, 15);
  vui = __builtin_s390_vstrczf(vui, vui, vui, 0);
  vui = __builtin_s390_vstrczf(vui, vui, vui, 15);
  vuc = __builtin_s390_vfaebs(vuc, vuc, 0, &cc);
  vuc = __builtin_s390_vfaebs(vuc, vuc, 15, &cc);
  vus = __builtin_s390_vfaehs(vus, vus, 0, &cc);
  vus = __builtin_s390_vfaehs(vus, vus, 15, &cc);
  vui = __builtin_s390_vfaefs(vui, vui, 0, &cc);
  vui = __builtin_s390_vfaefs(vui, vui, 15, &cc);
  vuc = __builtin_s390_vfaezbs(vuc, vuc, 0, &cc);
  vuc = __builtin_s390_vfaezbs(vuc, vuc, 15, &cc);
  vus = __builtin_s390_vfaezhs(vus, vus, 0, &cc);
  vus = __builtin_s390_vfaezhs(vus, vus, 15, &cc);
  vui = __builtin_s390_vfaezfs(vui, vui, 0, &cc);
  vui = __builtin_s390_vfaezfs(vui, vui, 15, &cc);
  vuc = __builtin_s390_vfeebs(vuc, vuc, &cc);
  vus = __builtin_s390_vfeehs(vus, vus, &cc);
  vui = __builtin_s390_vfeefs(vui, vui, &cc);
  vuc = __builtin_s390_vfeezbs(vuc, vuc, &cc);
  vus = __builtin_s390_vfeezhs(vus, vus, &cc);
  vui = __builtin_s390_vfeezfs(vui, vui, &cc);
  vuc = __builtin_s390_vfenebs(vuc, vuc, &cc);
  vus = __builtin_s390_vfenehs(vus, vus, &cc);
  vui = __builtin_s390_vfenefs(vui, vui, &cc);
  vuc = __builtin_s390_vfenezbs(vuc, vuc, &cc);
  vus = __builtin_s390_vfenezhs(vus, vus, &cc);
  vui = __builtin_s390_vfenezfs(vui, vui, &cc);
  vuc = __builtin_s390_vistrbs(vuc, &cc);
  vus = __builtin_s390_vistrhs(vus, &cc);
  vui = __builtin_s390_vistrfs(vui, &cc);
  vuc = __builtin_s390_vstrcbs(vuc, vuc, vuc, 0, &cc);
  vuc = __builtin_s390_vstrcbs(vuc, vuc, vuc, 15, &cc);
  vus = __builtin_s390_vstrchs(vus, vus, vus, 0, &cc);
  vus = __builtin_s390_vstrchs(vus, vus, vus, 15, &cc);
  vui = __builtin_s390_vstrcfs(vui, vui, vui, 0, &cc);
  vui = __builtin_s390_vstrcfs(vui, vui, vui, 15, &cc);
  vuc = __builtin_s390_vstrczbs(vuc, vuc, vuc, 0, &cc);
  vuc = __builtin_s390_vstrczbs(vuc, vuc, vuc, 15, &cc);
  vus = __builtin_s390_vstrczhs(vus, vus, vus, 0, &cc);
  vus = __builtin_s390_vstrczhs(vus, vus, vus, 15, &cc);
  vui = __builtin_s390_vstrczfs(vui, vui, vui, 0, &cc);
  vui = __builtin_s390_vstrczfs(vui, vui, vui, 15, &cc);
}
void test_float(void) {
  vsl = __builtin_s390_vfcedbs(vd, vd, &cc);
  vsl = __builtin_s390_vfchdbs(vd, vd, &cc);
  vsl = __builtin_s390_vfchedbs(vd, vd, &cc);
  vsl = __builtin_s390_vftcidb(vd, 0, &cc);
  vsl = __builtin_s390_vftcidb(vd, 4095, &cc);
  vd = __builtin_s390_vfsqdb(vd);
  vd = __builtin_s390_vfmadb(vd, vd, vd);
  vd = __builtin_s390_vfmsdb(vd, vd, vd);
  vd = __builtin_s390_vflpdb(vd);
  vd = __builtin_s390_vflndb(vd);
  vd = __builtin_s390_vfidb(vd, 0, 0);
  vd = __builtin_s390_vfidb(vd, 4, 0);
  vd = __builtin_s390_vfidb(vd, 4, 1);
  vd = __builtin_s390_vfidb(vd, 4, 5);
  vd = __builtin_s390_vfidb(vd, 4, 6);
  vd = __builtin_s390_vfidb(vd, 4, 7);
  vd = __builtin_s390_vfidb(vd, 4, 4);
}
