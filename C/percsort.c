#include <stdio.h>
#include <stdlib.h>

#include "percolate.h"

/*
 *  DO NOT CHANGE THIS FILE
 *
 *  Function to sort clusters into decreasing size.
 */

static int clustcompare(const void *p1, const void *p2)
{
  int size1, size2, id1, id2;

  size1 = ((struct cluster *) p1)->size;
  size2 = ((struct cluster *) p2)->size;

  id1   = ((struct cluster *) p1)->id;
  id2   = ((struct cluster *) p2)->id;

  if (size1 != size2)
    {
      return(size2 - size1);
    }
  else
    {
      return(id2 - id1);
    }
}

void percsort(struct cluster *list, int n)
{
  qsort(list, (size_t) n, sizeof(struct cluster), clustcompare);
}

