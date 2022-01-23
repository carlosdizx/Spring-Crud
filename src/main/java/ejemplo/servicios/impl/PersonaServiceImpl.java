package ejemplo.servicios.impl;

import ejemplo.commons.GenericServiceImpl;
import ejemplo.daos.PersonaDao;
import ejemplo.servicios.api.PersonaService;
import ejemplo.modelos.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class PersonaServiceImpl extends GenericServiceImpl<Persona, Integer> implements PersonaService
{
    @Autowired
    private PersonaDao dao;

    @Override
    public JpaRepository<Persona, Integer> getDao()
    {
        return dao;
    }
}
