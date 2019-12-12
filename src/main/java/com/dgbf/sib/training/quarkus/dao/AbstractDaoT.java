package com.dgbf.sib.training.quarkus.dao;

import java.util.List;

public  class  AbstractDaoT<T> {

    protected T value;
    
    protected List<T> lst ;
    
    
    
    

    public AbstractDaoT() {
        this.value = null;
    }

    public AbstractDaoT(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<T> getLst() {
        return lst;
    }

    public void setLst(List<T> lst) {
        this.lst = lst;
    }

    public int getNextId(){
        int nextId = lst.size() + 1;
        if (lst.isEmpty()) {
            return 1;
        } else {

            Boolean isExist = false;
            do {
                isExist = false;
                for (T t : lst) {
                    if (t.getId() == nextId) {
                        isExist = true;
                        nextId++;
                        break;
                    }
                }

            } while (isExist == true);
        }
        return nextId;
    }
}
