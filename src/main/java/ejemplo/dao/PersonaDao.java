package ejemplo.dao;

import ejemplo.modelos.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDao extends CrudRepository<Persona, Integer>
{

}
