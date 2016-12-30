package main.structure;

public abstract class Hachage<T extends GenericData<E>,E> {

    /**
     *
     */
    public int m;

    /**
     *
     */
    public abstract void add(T data);

    /**
     *
     * @param data
     * @return
     */
    public abstract boolean recherche(T data);

    /**
     *
     */
    public abstract int h(T data);
}
