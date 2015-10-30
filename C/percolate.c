#include <stdio.h>
#include <stdlib.h>

#include "percolate.h"

/*
 * Program to test for percolation of a cluster.
 */

int main(void)
{
  /*
   *  Define the main "map" array
   */

  int map[L+2][L+2];

  int loop, nchange;

  float rho;
  int seed;

  /*
   *  Set the most important variable, the density rho
   */

  rho  = 0.40;

  /*
   *  Set the randum number seed and initialise the generator
   */

  seed = 1564;
  rinit(seed);

  printf("percolate: parameters are rho=%f, L=%d, seed=%d\n", rho, L, seed);

  /*
   *  Initialise map with density rho
   */

  percfill(map, rho);

  /*
   *  Initialise map ready for updating
   */

  percinit(map);

  /*
   *  Keep updating until nothing changes
   */

  loop = 1;
  nchange = 1;

  while (nchange > 0)
    {
      nchange = percupdate(map);

      printf("percolate: number of changes on loop %d is %d\n",
	     loop, nchange);
      loop++;
    }

  /*
   *  Test to see if percolation occurred
   */

  if (perctest(map))
    {
      printf("percolate: cluster DOES percolate\n");
    }
  else
    {
      printf("percolate: cluster DOES NOT percolate\n");
    }

  /*
   *  Write map to the file "map.pgm", displaying L*L (ie all) the clusters
   */

  percwrite("map.pgm", map, L*L);
}

