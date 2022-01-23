package ejemplo.commons;

import java.io.Serializable;
import java.util.List;

public interface GenericServiceApi<T, ID extends Serializable> {
    T save(T entity);

    void delete(ID id);

    T findByID(ID id);

    List<T> getAll();
}
