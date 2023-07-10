package cl.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.model.entities.Tarea;


@Repository
public interface TareaRepository extends CrudRepository<Tarea, Integer>
{
	Iterable<Tarea> findAll();
	
    <S extends Tarea> S save(S entity);
    
    Optional<Tarea> findById(Integer id);
    
    @Query(nativeQuery = true, value = "select * from tareas where DESCRIPCION_TAREA like %:descripcion%") 
    Iterable<Tarea> findByDescripcion(@Param("descripcion") String descripcion);    
    
}