package cl.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.common.Common;
import cl.model.entities.Tarea;
import cl.model.entities.Telefono;
import cl.model.entities.Usuario;
import cl.model.repositories.TareaRepository;
import cl.model.repositories.TelefonoRepository;
import cl.model.repositories.UsuarioRepository;

@Configuration
public class BulkInitialDataBean 
{

    @Autowired
    private Common common;
    
	@Bean
	public CommandLineRunner bulkInitialData
	(
			UsuarioRepository usuarioRepository, 
			TelefonoRepository telefonoRepository,
			TareaRepository tareaRepository
	) 
	{
		return (args) -> 
		{
			// USUARIO 1			
			Usuario u1 = new Usuario();
			u1.setIdUsuario("88b78024-681a-427d-beeb-3124bcd38610");
			u1.setName("David Ortiz");
			u1.setEmail("david.ortiz.albornoz@gmail.com");
			u1.setActivo(1);
			u1.setFechaCreacion(new Date());
			u1.setPassword("abc1810_!");
			u1.setFechaModificacion(null);
			u1.setFechaSesion(null);
			u1.setToken(null);

			
			List<Telefono> telefonoListU1 = new ArrayList<Telefono>();
			Telefono t1 = new Telefono();
			t1.setIdTelefono(500);
			t1.setUsuario(u1.getIdUsuario());
			t1.setContrycode("56");
			t1.setCitycode("09");
			t1.setNumber("7458-65200");
			telefonoListU1.add(t1);
			
			Telefono t2 = new Telefono();
			t2.setIdTelefono(501);
			t2.setUsuario(u1.getIdUsuario());
			t2.setContrycode("56");
			t2.setCitycode("02");
			t2.setNumber("2541-5458");			
			telefonoListU1.add(t2);
			
			u1.setPhones(telefonoListU1);
			usuarioRepository.save(u1);
			telefonoRepository.save(t1);
			telefonoRepository.save(t2);	
			
			// USUARIO 2
			Usuario u2 = new Usuario();
			u2.setIdUsuario("6efc7a3f-8ea8-419c-9b57-07e44c7866bf");
			u2.setName("Julian Galeano");
			u2.setEmail("julian.773@gmail.com");
			u2.setActivo(1);
			u2.setFechaCreacion(new Date());
			u2.setPassword("jyly_Ahyd*");
			u2.setFechaModificacion(null);
			u2.setFechaSesion(null);
			u2.setToken(null);	

			List<Telefono> telefonoListU2 = new ArrayList<Telefono>();
			Telefono t3 = new Telefono();
			t3.setIdTelefono(301);
			t3.setUsuario(u2.getIdUsuario());
			t3.setContrycode("56");
			t3.setCitycode("09");
			t3.setNumber("7458-65200");
			telefonoListU2.add(t3);
		
			
			u2.setPhones(telefonoListU2);
			usuarioRepository.save(u2);
			telefonoRepository.save(t3);
	
			// TASK 1
			Tarea task1 = new Tarea();
			task1.setIdentificadorTarea(1001);
			task1.setDescripcionTarea("Selección de personal");
			task1.setFechaCreacionTarea(common.getCurrentDate());
			task1.setVigenciaTarea(Boolean.TRUE);
			tareaRepository.save(task1);
			
			// TASK 2
			Tarea task2 = new Tarea();
			task1.setIdentificadorTarea(1002);
			task2.setDescripcionTarea("Compra insumos deportivos");
			task2.setFechaCreacionTarea(common.getCurrentDate());
			task2.setVigenciaTarea(Boolean.FALSE);
			tareaRepository.save(task2);
			
			// TASK 3
			Tarea task3 = new Tarea();
			task3.setIdentificadorTarea(1002);
			task3.setDescripcionTarea("Remate insumos de aseo");
			task3.setFechaCreacionTarea(common.getCurrentDate());
			task3.setVigenciaTarea(Boolean.FALSE);
			tareaRepository.save(task3);
		};
	}

}
