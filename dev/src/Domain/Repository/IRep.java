package dev.src.Domain.Repository;

public interface IRep<T, ID> {

    String add(T obj);
    T find(ID id);
    String update(T obj);
    String delete(ID id);

}
