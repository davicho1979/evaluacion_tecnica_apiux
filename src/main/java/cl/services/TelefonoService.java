package cl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.model.entities.Telefono;
import cl.model.repositories.TelefonoRepository;


@Service
public class TelefonoService 
{
    @Autowired
    private TelefonoRepository telefonoRepository;
     
    public <S extends Telefono> Telefono guardarTelefono(Telefono telefonoObj) 
    {
        return telefonoRepository.save(telefonoObj);
    }   
    
}
