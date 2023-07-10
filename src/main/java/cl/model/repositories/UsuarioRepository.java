package cl.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.model.entities.Usuario;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String>
{
	Iterable<Usuario> findAll();
	
    Optional<Usuario> findById(String id);
    
    @Query(nativeQuery = true, value = "select * from usuarios where email = :email") 
	Optional<Usuario> findByEmail(@Param("email") String email);
    
    <S extends Usuario> S save(S entity);
    

}
