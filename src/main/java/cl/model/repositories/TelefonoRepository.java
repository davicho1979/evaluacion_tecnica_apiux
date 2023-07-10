package cl.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.model.entities.Telefono;


@Repository
public interface TelefonoRepository extends CrudRepository<Telefono, String>
{
    <S extends Telefono> S save(S entity);
}