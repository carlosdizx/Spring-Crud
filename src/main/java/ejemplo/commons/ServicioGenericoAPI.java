package ejemplo.commons;

import java.io.Serializable;
import java.util.List;

public interface ServicioGenericoAPI<Entidad,ID extends Serializable>
{
    Entidad guardar(Entidad entidad);

    void eliminar(ID id);

    Entidad buscar(ID id);

    List<Entidad> listarTodo();
}
