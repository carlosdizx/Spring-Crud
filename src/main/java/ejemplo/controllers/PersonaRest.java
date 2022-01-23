package ejemplo.controllers;

import ejemplo.servicios.api.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ejemplo.modelos.Persona;

import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/personas/")
public class PersonaRest {

    private final static int CANTIDAD_POR_PAGINA = 3;

    private final static String NOMBRE_ENTIDAD = "persona";

    private final static String NOMBRE_EN_PLURAL = "personas";

    private final static Map<String, Object> RESPONSE = new HashMap<>();

    @Autowired
    private PersonaService service;

    @GetMapping("all")
    public ResponseEntity<HashMap<String, Object>> findAll() {
        RESPONSE.clear();
        try {
            final List<Persona> listado = service.getAll();
            RESPONSE.put(NOMBRE_EN_PLURAL, listado);
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("page/{page}")
    public ResponseEntity<HashMap<String, Object>> findAllByPage(@PathVariable Integer page) {
        RESPONSE.clear();
        try {
            final Pageable pageable = PageRequest.of(page, CANTIDAD_POR_PAGINA);
            Page paginate = service.getAll(pageable);
            RESPONSE.put(NOMBRE_EN_PLURAL, paginate);
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("details/{id}")
    public ResponseEntity<HashMap<String, Object>> findAllById(@PathVariable Integer id) {
        RESPONSE.clear();
        try {
            Persona persona = service.findByID(id);
            if (persona != null) {
                RESPONSE.put(NOMBRE_ENTIDAD, persona);
                return new ResponseEntity(RESPONSE, HttpStatus.OK);
            } else {
                RESPONSE.put("mensaje", "Persona no encontrada");
                return new ResponseEntity(RESPONSE, HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<HashMap<String, Object>> create(@Valid @RequestBody Persona persona, BindingResult result) {
        RESPONSE.clear();
        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            RESPONSE.put("errors", errors);
            return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
        }

        try {
            Persona personaNueva = service.save(persona);
            RESPONSE.put("mensaje", "El cliente ha sido creado con éxito!");
            RESPONSE.put("cliente", personaNueva);
            return new ResponseEntity(RESPONSE, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "Error al realizar el insert en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<HashMap<String, Object>> edit(@Valid @RequestBody Persona persona, @PathVariable Integer id, BindingResult result) {
        RESPONSE.clear();
        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            RESPONSE.put("errors", errors);
            return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
        }
        try {
            Persona buscado = service.findByID(id);
            if (buscado != null) {
                buscado.setNombre(persona.getNombre());

                Persona actualizado = service.save(buscado);
                RESPONSE.put("mensaje", "La persona ha sido actualizado con éxito!");
                RESPONSE.put("cliente", actualizado);
                return new ResponseEntity(RESPONSE, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(RESPONSE, HttpStatus.NOT_MODIFIED);
            }
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "Error al realizar la actualización en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable Integer id)
    {
        RESPONSE.clear();
        try
        {
            Persona persona = service.findByID(id);
            if (persona == null){
                return new ResponseEntity(RESPONSE, HttpStatus.NO_CONTENT);
            }else{
                service.delete(id);
                RESPONSE.put("mensaje", "Se elimino!");
                return new ResponseEntity(RESPONSE, HttpStatus.ACCEPTED);
            }
        }
        catch (DataAccessException e)
        {
            RESPONSE.put("mensaje", "Error al realizar la eliminación en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
