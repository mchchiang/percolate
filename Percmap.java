import java.io.*;
import java.util.*;
import java.text.*;

/*
 * Class to manipulate the percolation map
 */

class Percmap
{

    int [][] map;
    int N;
    Uni random;
       
    PrintWriter screen = new PrintWriter(System.out,true);

    NumberFormat Nf = NumberFormat.getInstance();


    public Percmap(int N, int seed)
    {
	/*
	 * Create the map array
	 */

	this.N = N;
	map = new int[N+2][N+2];

	/*
	 *  Initialise the random number generator
	 */

	random = new Uni(seed);	
    }

    /*
     *  Method to fill the percolation map with density rho.
     *
     *  All boundary points should be set to zero.
     *
     *  The interior L*L entries should each be set to zero with
     *  probability rho, otherwise should be set to one.
     */


    void fill(float rho)
    {
        int i, j;
        int nfill;

        float r;

        for (i=0; i<N+2; i++)
            {
                for (j=0; j<N+2; j++)
                    {
                        map[i][j] = (i+j)%2;
                    }
            }
    }


    /*
     *  Method to initialise the map prior to looking for clusters.
     *
     *  All interior L*L points that are non-zero should be replaced with a
     *  unique positive integer.
     */

    void init()
    {
    }


    /*
     *  Method to update all entries of the map once.
     *
     *  Should return the actual number of changes, zero if nothing changes.
     */

    int update()
    {
	return(0);
    }


    /*
     * Method to test for percolation.
     *
     *  Should return true if a cluster percolates, false otherwise.
     */

    boolean test()
    {
	return(false);
    }
    

    /*
     *  DO NOT CHANGE THIS METHOD
     *
     *  Method to write percolation data to file
     *
     *  Clusters are coloured by size with the largest "maxcluster" being
     *  displayed.
     */

    void write(String percfile, int maxcluster) throws IOException
     {
	int i, j, size;
	int ncluster, maxsize;
	
	//struct cluster clustlist[N*N];
	Cluster [] clustlist = new Cluster[N*N];

	int colour;
	int [] rank = new int[N*N];

	for (i=0; i < N*N; i++)
	    {
		rank[i] = -1;
		clustlist[i] = new Cluster();
		clustlist[i].setSize(0);
		clustlist[i].setId(i+1);
	    }

	/*
	 * Find the sizes of all the distinct clusters
	 */

	for (i=1;i<=N; i++)
	    {
		for (j=1; j<=N; j++)
		    {
			if (map[i][j] != 0)
			    {
				size = clustlist[map[i][j]-1].getSize();
				size++;
				clustlist[map[i][j]-1].setSize(size);
			    }
		    }
	    }

	/*
	 * Now sort them with the largest first
	 */

	percsort(clustlist);

	maxsize = clustlist[0].getSize();

	for (ncluster=0; ncluster < N*N && clustlist[ncluster].getSize()
		 > 0; ncluster++);

	if (maxcluster > ncluster)
	    {
		maxcluster = ncluster;
	    }

	for (i=0; i < ncluster; i++)
	    {
		rank[clustlist[i].getId() - 1] = i;
	    }
	screen.println("percwrite: opening file "+percfile);


	/*
	 * open output file for writing
	 */

	FileWriter fp = new FileWriter(new File(percfile));

	/*
	 * and set format for output to file
	 */

	Nf.setMaximumIntegerDigits(3);
	Nf.setMinimumIntegerDigits(3);



	screen.println("percwrite: map has "+ncluster+" clusters, maximum cluster size is "+maxsize);

	if (maxcluster == 1)
	    {
		screen.println("percwrite: displaying the largest cluster");
	    }
	else if (maxcluster == ncluster)
	    {
		screen.println("percwrite: displaying all clusters");
	    }
	else
	    {
		screen.println("percwrite: displaying the largest "+maxcluster+" clusters");
	    }

	screen.println("percwrite: writing data ...");

	fp.write("P2\n");

	if (maxcluster > 0)
	    {
		fp.write(N+" "+N+"\n"+maxcluster+"\n");
	    }
	else
	    {
                fp.write(N+" "+N+"\n"+1+"\n");
	    }

	for (j=N; j>=1; j--)
	    {
		for (i=1;i<=N; i++)
		    {
			colour = map[i][j];

			/*
			 *  If it is part of a cluster, look for the colour
			 */

			if (map[i][j] > 0)
			    {
				colour = rank[map[i][j]-1];

				if (colour >= maxcluster)
				    {
					colour = maxcluster;
				    }
			    }
			else
			    {
				colour = maxcluster;
			    }

			fp.write(Nf.format(colour)+" ");
		    }
		fp.write("\n");
	    }

	screen.println("percwrite: ... done");

	fp.close();
	screen.println("percwrite: file closed");


    }


    /*
     *  DO NOT CHANGE THIS METHOD
     *
     *  Method to sort clusters into decreasing size.
     */

    void percsort(Cluster [] clustlist)
    {
	Arrays.sort(clustlist);
    }

}
