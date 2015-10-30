#include <stdio.h>
#include <stdlib.h>

#include "percolate.h"

/*
 *  DO NOT CHANGE THIS FILE
 *
 *  Routine to write percolation data to file
 *
 *  Clusters are coloured by size with the largest "maxcluster" being
 *  displayed.
 */


void percwrite(char *percfile, int percmap[L+2][L+2], int maxcluster)
{
  int i, j;
  int ncluster, maxsize;

  struct cluster clustlist[L*L];

  int colour, rank[L*L];

  FILE *fp;

  for (i=0; i < L*L; i++)
    {
      rank[i] = -1;
      clustlist[i].size = 0;
      clustlist[i].id   = i+1;
    }

  /*
   * Find the sizes of all the distinct clusters
   */

  for (i=1;i<=L; i++)
    {
      for (j=1; j<=L; j++)
        {
	  if (percmap[i][j] != 0)
	    {
	      ++(clustlist[percmap[i][j]-1].size);
	    }
	}
    }

  /*
   * Now sort them with the largest first
   */

  percsort(clustlist, L*L);

  maxsize = clustlist[0].size;

  for (ncluster=0; ncluster < L*L && clustlist[ncluster].size > 0; ncluster++);

  if (maxcluster > ncluster)
    {
      maxcluster = ncluster;
    }

  for (i=0; i < ncluster; i++)
    {
      rank[clustlist[i].id - 1] = i;
    }

  printf("percwrite: opening file <%s>\n", percfile);

  fp = fopen(percfile, "w");

  printf("percwrite: map has %d clusters, maximum cluster size is %d\n",
	 ncluster, maxsize);

  if (maxcluster == 1)
    {
      printf("percwrite: displaying the largest cluster\n");
    }
  else if (maxcluster == ncluster)
    {
      printf("percwrite: displaying all clusters\n");
    }
  else
    {
      printf("percwrite: displaying the largest %d clusters\n", maxcluster);
    }

  printf("percwrite: writing data ...\n");

  fprintf(fp, "P2\n");

  if (maxcluster > 0)
    {
      fprintf(fp, "%d %d\n%d\n", L, L, maxcluster);
    }
  else
    {
      fprintf(fp, "%d %d\n%d\n", L, L, 1);
    }

  for (j=L; j>=1; j--)
    {
      for (i=1;i<=L; i++)
	{
	  colour = percmap[i][j];

	  /*
	   *  If it is part of a cluster, look for the colour
	   */

	  if (percmap[i][j] > 0)
	    {
	      colour = rank[percmap[i][j]-1];

	      if (colour >= maxcluster)
		{
		  colour = maxcluster;
		}
	    }
	  else
	    {
	      colour = maxcluster;
	    }

	  fprintf(fp, " %2d", colour);
	}
      fprintf(fp,"\n");
    }

  printf("percwrite: ... done\n");

  fclose(fp);
  printf("percwrite: file closed\n");
}
