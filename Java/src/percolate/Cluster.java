package percolate;
class Cluster implements Comparable
{

    int id;
    int size;
    /*
    public Cluster(int id, int size)
    {
	this.id = id;
	this.size = size;
    }
    */

    void setId(int id)
    {
	this.id = id;
    }


    void setSize(int size)
    {
        this.size = size;
    }

    int getId()
    {
     return id;
    }


    int getSize()
    {
	return size;
    }


    public int compareTo(Object obj)
    {
	int size1, size2, id1, id2;

	Cluster clust = (Cluster)obj;

	size1 = size;
	size2 = clust.getSize();

	id1   = id;
	id2   = clust.getId();

	if (size1 != size2)
	    {
		return(size2 - size1);
	    }
	else
	    {
		return(id2 - id1);
	    }

    }

}
