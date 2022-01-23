package ejemplo.Controllers;

import ejemplo.servicios.api.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ejemplo.modelos.Persona;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/personas/")
public class PersonaRest {

    private final static int CANTIDAD_POR_PAGINA = 10;

    private final static String NOMBRE_ENTIDAD = "Persona";

    private final static String NOMBRE_EN_PLURAL = "Personas";

    private final static Map<String, Object> RESPONSE = new HashMap<>();

    @Autowired
    private PersonaService service;

    @GetMapping("all")
    public ResponseEntity<HashMap<String, Object>> findAll() {
        RESPONSE.clear();
        try {
            List<Persona> listado = service.getAll();
            RESPONSE.put(NOMBRE_EN_PLURAL, listado);
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        }
        catch (DataAccessException e) {
            RESPONSE.put("Mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("page/{page}")
    public ResponseEntity<HashMap<String, Object>> findAllByPage(@PathVariable Integer page, Model model)
    {
        RESPONSE.clear();
        try {
            Pageable pageable = PageRequest.of(page, CANTIDAD_POR_PAGINA);
            RESPONSE.put(NOMBRE_EN_PLURAL, service.getAll(pageable));
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        }
        catch (DataAccessException e) {
            RESPONSE.put("Mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
