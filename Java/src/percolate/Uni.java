package percolate;
/*
 *	Java version of Marsaglia's UNI random number generator
 *	More or less transliterated from the Fortran -- with 1 bug fix
 *	Hence horrible style
 *
 */


class Uni
{

/*
 *	Global variables for rstart & uni
 */

float []uni_u = new float[98];	/* Was U(97) in Fortran version -- too lazy to fix */
float uni_c, uni_cd, uni_cm;
int uni_ui, uni_uj;

    public Uni(int ijkl)
    {
	rinit(ijkl);
    }

    float uniform()
    {
	float luni;			/* local variable for uni */
	
	luni = uni_u[uni_ui] - uni_u[uni_uj];
	if (luni < 0.0)
	    luni += 1.0;
	uni_u[uni_ui] = luni;
	if (--uni_ui == 0)
	    uni_ui = 97;
	if (--uni_uj == 0)
	    uni_uj = 97;
	if ((uni_c -= uni_cd) < 0.0)
	    uni_c += uni_cm;
	if ((luni -= uni_c) < 0.0)
	    luni += 1.0;
	return (float) luni;
    }
    
    void rstart(int i, int j, int k, int l)
    {
	int ii, jj, m;
	float s, t;
	
	for (ii = 1; ii <= 97; ii++) {
	    s = 0.0f;
	    t = 0.5f;
	    for (jj = 1; jj <= 24; jj++) {
		m = ((i*j % 179) * k) % 179;
		i = j;
		j = k;
		k = m;
		l = (53*l+1) % 169;
		if (l*m % 64 >= 32)
		    s += t;
		t *= 0.5;
	    }
	    uni_u[ii] = s;
	}
	uni_c  = 362436.0f   / 16777216.0f;
	uni_cd = 7654321.0f  / 16777216.0f;
	uni_cm = 16777213.0f / 16777216.0f;
	uni_ui = 97;	/*  There is a bug in the original Fortran version */
	uni_uj = 33;	/*  of UNI -- i and j should be SAVEd in UNI()     */
    }
    
    
    /* ~rinit: this takes a single integer in the range
       0 <= ijkl <= 900 000 000
       and produces the four smaller integers needed for rstart. It is
       *	based on the ideas contained in the RMARIN subroutine in
       *		F. James, "A Review of Pseudorandom Number Generators",
       *			Comp. Phys. Commun. Oct 1990, p.340
       *	To reduce the modifications to the existing code, rinit now
       *	takes the role of a preprocessor for rstart.
       *
       *	This is useful for the parallel version of the code as James
       *	states that any integer ijkl will produce a statistically
       *	independent sequence of random numbers.
       *
       *     Very funny. If that statement was worth anything he would have provided
       *     a proof to go with it. spb 12/12/90 
       */
    
    void rinit(int ijkl)
    {
	int i, j, k, l, ij, kl;
	
	/* check ijkl is within range */
	if( (ijkl < 0) || (ijkl > 900000000) )
	    {
		System.out.println("rinit: ijkl = "+ijkl+" -- out of range\n\n");
		System.exit(3);
	    }
	
	/*        printf("rinit: seed_ijkl = %d\n", ijkl); */
	
	/* decompose the long integer into the the equivalent four
	 * integers for rstart. This should be a 1-1 mapping
 	 *	ijkl <--> (i, j, k, l)
	 * though not quite all of the possible sets of (i, j, k, l)
	 * can be produced.
	 */
	
	ij = ijkl/30082;
	kl = ijkl - (30082 * ij);
	
	i = ((ij/177) % 177) + 2;
	j = (ij % 177) + 2;
	k = ((kl/169) % 178) + 1;
	l = kl % 169;
	
	if( (i <= 0) || (i > 178) )
	    {
		System.out.println("rinit: i = "+i+"%d -- out of range\n\n");
		System.exit(3);
	    }
	
	if( (j <= 0) || (j > 178) )
	    {
		System.out.println("rinit: j = "+j+" -- out of range\n\n");
		System.exit(3);
	    }
	
	if( (k <= 0) || (k > 178) )
	    {
		System.out.println("rinit: k = "+k+" -- out of range\n\n");
		System.exit(3);
	    }
	
	if( (l < 0) || (l > 168) )
	    {
		System.out.println("rinit: l = "+l+" -- out of range\n\n");
		System.exit(3);
	    }
	
	if (i == 1 && j == 1 && k == 1)
	    {
                System.out.println("rinit: 1 1 1 not allowed for 1st 3 seeds\n\n");
		System.exit(4);
	    }
	
	/*        System.out.println("rinit: initialising RNG via rstart("+i+", "+j+", "+k+", "+l);  */
	
        rstart(i, j, k, l);
	
    }
    
}