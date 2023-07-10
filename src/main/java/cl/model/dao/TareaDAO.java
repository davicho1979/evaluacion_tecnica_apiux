package cl.model.dao;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.common.Common;
import cl.model.entities.Tarea;
import cl.model.repositories.TareaRepository;

@Component
public class TareaDAO 
{
    @Autowired
    private TareaRepository tareaRepository;
 
    @Autowired
    private Common common;
    
    public Iterable<Tarea> listarTareas() 
    {
        return tareaRepository.findAll();
    }
    
    public Optional<Tarea> findById(Integer id) 
    {
    	return tareaRepository.findById(id);
    }
    
    public Iterable<Tarea> findByDescripcion(String descripcion) 
    {
        return tareaRepository.findByDescripcion(descripcion);
    }
    
    public int deleteById(Integer id) 
    {
    		Session session = common.getCurrentSessionFromJPA();
    		NativeQuery<?> query = session.createSQLQuery("delete tareas where IDENTIFICADOR_TAREA = :ID");
    		query.setParameter("ID", id);
    		return query.executeUpdate();	
    }
    
    public <S extends Tarea> Tarea guardarTarea(Tarea tareaObj) 
    {
        return tareaRepository.save(tareaObj);
    } 
    
    public <S extends Tarea> Tarea editarTarea(Tarea tareaObj) 
    {
        return tareaRepository.save(tareaObj);
    } 
}
