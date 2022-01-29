package MainPackage.orm;

import java.util.List;
import java.util.Optional;

public interface EntityManager {
    <T> Optional<T> get(Long id, Class<T> clazz);
    Long save(Object object) throws Exception;
    <T> List<T> getAll(Class<T> clazz);
    void delete(int id, Class<?> clazz);
}
