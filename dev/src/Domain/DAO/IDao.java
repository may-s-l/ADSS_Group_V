package dev.src.Domain.DAO;

public interface IDao<T, ID> {
    void create(T obj);
    T read(ID id);
    void update(T obj);
    void delete(ID id);
}

