/**
 * Created by Juan José on 06/09/2017.
 */

public class SparseArray
{
    private List start;
    private int index;

    SparseArray(int index)
    {
        start = new List();
        this.index = index;
    }

    public void store(int index, Object value)
    {
        if (index >= 0 && index < this.index)
        {
            if (value != null)
                start.store(index, value);
        } else
        {
            System.out.println("INDEX OUT OF BOUNDS");
        }
    }

    public Object fetch(int index)
    {
        if (index >= 0 && index < this.index)
            return start.fetch(index);
        else
        {
            System.out.println("INDEX OUT OF BOUNDS");
            return null;
        }
    }

    public int elementCount()
    {
        return start.elementCount();
    }

    public static void main(String... arg)
    {
        Integer[] iarray = new Integer[5];
        iarray[0] = 1;
        iarray[1] = null;
        iarray[2] = 2;
        iarray[3] = null;
        iarray[4] = null;

        SparseArray sparseArray = new SparseArray(5);
        for (int i = 0; i < iarray.length; i++)
        {
            sparseArray.store(i, iarray[i]);
        }

        System.out.println("NORMAL ARRAY");
        for (int i = 0 ; i < iarray.length; i++)
        {
            System.out.print(iarray[i] + "\t");
        }

        System.out.println("\nSPARSE ARRAY");
        for (int i = 0; i < iarray.length; i++)
        {
            if (sparseArray.fetch(i) != null)
                System.out.print(sparseArray.fetch(i) + "\t");
        }
        System.out.println("The Size of Sparse Array is " + sparseArray.elementCount());
    }
}
