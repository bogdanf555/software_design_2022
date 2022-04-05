package com.foodpanda.utils;

import java.util.List;

public class Iterator<T> {

    private List<T> list;
    private int index;
    private int listSize;

    public Iterator(List<T> list) {
        this.list = list;
        this.index = 0;

        if (list != null)
            this.listSize = list.size();
        else
            this.listSize = -1;
    }

    public int getListSize() {
        return this.list.size();
    }

    public boolean hasNext() {
        return index < listSize - 1;
    }

    public T get() {

        if (index < listSize)
            return list.get(index);
        return null;
    }

    public T next() {

        if (!hasNext())
            return null;

        index++;
        return get();
    }
}
