package repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    Optional<T> get(Integer id);
    void save(T t);   // Abort if already exist
    void put(T t);    // Overwrite if already exist (Forced save)
    void update(T t); // Abort if doesn't exist
    void remove(T t);
    List<T> getAll();
}
