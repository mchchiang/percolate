#include <stdio.h>
#include <stdlib.h>

#include "percolate.h"

/*
 *  Function to fill the percolation map with density rho.
 *
 *  All boundary points should be set to zero.
 *
 *  The interior L*L entries should each be set to zero with
 *  probability rho, otherwise should be set to one.
 */

void percfill(int percmap[L+2][L+2], float rho)
{
  int i, j;

  for (i=0; i<L+2; i++)
    {
      for (j=0; j<L+2; j++)
	{
	  percmap[i][j] = (i+j)%2;
	}
    }
}
  
