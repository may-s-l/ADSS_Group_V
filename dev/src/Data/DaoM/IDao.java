package dev.src.Data.DaoM;

public interface IDao<T,ID> {


    void insert(T obj);
    T select(ID id);
    void update(T obj);
    void delete(ID id);

}
