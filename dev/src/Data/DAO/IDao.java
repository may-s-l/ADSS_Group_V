package dev.src.Data.DAO;

import java.sql.ResultSet;

public interface IDao<T,ID> {


    void insert(T obj);
    T select(ID id);
    void update(T obj);
    void delete(ID id);

}
