package main.structure;

/**
 *
 * @author Sabri
 *
 * @param <T>
 */
public class GenericData<T> implements Comparable<GenericData<T>>{
    /**
     * name
     */
    public String cle;
    /**
     * value
     */
    public T donnee;

    public GenericData(T data){
        cle = data.toString();
        donnee = data;
    }

    @Override
    public String toString()
    {
        return this.cle;
    }

    @Override
    public int compareTo(GenericData<T> data)
    {
        return this.cle.compareTo(data.cle);
    }

    @Override
    public boolean equals(Object o){
        boolean equal = false;
        if(o==null)equal = false;
        else{
			GenericData<T> oData = (GenericData<T>)o;
            if(this.cle.equals(oData.cle))
                equal = true;
            else
                equal = false;
        }
        return equal;
    }
}
