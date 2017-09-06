/**
 * Created by Juan José on 06/09/2017.
 */
class List
{
    private int index;
    private Object value;
    private List nextindex;

    public List(int index)
    {
        this.index = index;
        nextindex = null;
        value = null;
    }

    public List()
    {
        index = -1;
        value = null;
        nextindex = null;
    }

    public void store(int index, Object value)
    {
        List current = this;
        List previous = null;

        List node = new List(index);
        node.value = value;

        while (current != null && current.index < index)
        {
            previous = current;
            current = current.nextindex;
        }

        if (current == null)
        {
            previous.nextindex = node;
        } else
        {
            if (current.index == index)
            {
                System.out.println("DUPLICATE INDEX");
                return;
            }
            previous.nextindex = node;
            node.nextindex = current;
        }
        return;
    }

    public Object fetch(int index)
    {
        List current = this;
        Object value = null;
        while (current != null && current.index != index)
        {
            current = current.nextindex;
        }
        if (current != null)
        {
            value = current.value;
        } else
        {
            value = null;
        }
        return value;
    }

    public int elementCount()
    {
        int elementCount = 0;
        for (List current = this.nextindex; (current != null); current = current.nextindex)
        {
            elementCount++;
        }
        return elementCount;
    }
}


