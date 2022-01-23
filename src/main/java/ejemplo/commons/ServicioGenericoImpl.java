package ejemplo.commons;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class ServicioGenericoImpl <Entidad, ID extends Serializable> implements ServicioGenericoAPI<Entidad, ID>
{
    @Override
    public Entidad guardar(Entidad entidad)
    {
        return null;
    }

    @Override
    public void eliminar(ID id)
    {

    }

    @Override
    public Entidad buscar(ID id)
    {
        return null;
    }

    @Override
    public List<Entidad> listarTodo()
    {
        return null;
    }

    public abstract JpaRepository getDao();
}
