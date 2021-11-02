package repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findAll();
    Optional<T> findById();
    T save(T t);
    void deleteById(int id);
}
