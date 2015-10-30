import java.io.*;

/*
 * Program to test for percolation of a cluster.
 */

class Percolate
{

    static int L = 20;

    static PrintWriter screen = new PrintWriter(System.out,true);
    
    public static void main(String argv[]) throws IOException
    {

	int loop, nchange;
	
	float rho;
	int seed;

	/*
	 *  Set the most important variable, the density rho
	 */
	
	rho  = 0.40f;

	/*
	 *  Set the random number seed
	 */
	
	seed = 1564;
	
	screen.println("percolate: parameters are rho="+rho+", L="+L+", seed="+seed);

	/*
	 * Create the percolation map, size [L+2][L+2]
	 */

	Percmap perc = new Percmap(L, seed);


	/*
	 *  Fill map with density rho
	 */
	
	perc.fill(rho);


	/*
	 *  Initialise map ready for updating
	 */
	
       	perc.init();


	/*
	 *  Keep updating until nothing changes
	 */
	
	loop = 1;
	nchange = 1;
	
	while (nchange > 0)
	    {
		nchange = perc.update();
		
		screen.println("percolate: number of changes on loop "+loop+" is "+nchange);
		loop++;
	    }

	/*
	 *  Test to see if percolation occurred
	 */
	
	if (perc.test())
	    {
		screen.println("percolate: cluster DOES percolate");
	    }
	else
	    {
		screen.println("percolate: cluster DOES NOT percolate");
	    }

	/*
	 *  Write map to file "map.pgm", displaying L*L (ie all) the clusters
	 */
	
	perc.write("map.pgm", L*L);
    }
}
