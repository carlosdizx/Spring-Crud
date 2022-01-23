package ejemplo.servicios.api;

import ejemplo.commons.GenericServiceApi;
import ejemplo.modelos.Persona;
import org.springframework.stereotype.Service;

@Service
public interface PersonaService extends GenericServiceApi<Persona, Integer>
{

}
