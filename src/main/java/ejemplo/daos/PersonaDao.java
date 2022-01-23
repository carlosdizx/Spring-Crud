package ejemplo.daos;

import ejemplo.modelos.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaDao extends JpaRepository<Persona, Integer> {

}
