package dev.src.Data.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDao<T, ID> {
    void insert(T obj);
    T select(ID id);
    void update(T obj);
    void delete(ID id);
    T load(ResultSet rs) throws SQLException;
}

