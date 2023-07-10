package cl.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.model.entities.Telefono;
import cl.model.repositories.TelefonoRepository;



@Component
public class TelefonoDAO 
{
    @Autowired
    private TelefonoRepository telefonoRepository;
     
    public <S extends Telefono> Telefono guardarTelefono(Telefono telefonoObj) 
    {
        return telefonoRepository.save(telefonoObj);
    }   
    
}
