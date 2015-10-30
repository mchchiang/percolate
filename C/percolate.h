/*
 *  Main header file for percolation code.
 */

#define L 20

/*
 *  Prototypes for functions to be implemented by the student.
 */

void percfill(int percmap[L+2][L+2], float rho);
void percinit(int percmap[L+2][L+2]);
int percupdate(int percmap[L+2][L+2]);
int perctest(int percmap[L+2][L+2]);


/*
 *  Prototypes for supplied functions that should not be changed.
 */

struct cluster
{
  int id;
  int size;
};

void percwrite(char *percfile, int percmap[L+2][L+2], int maxcluster);
void percsort(struct cluster *list, int n);

void rinit(int ijkl);
float random_uniform(void);
