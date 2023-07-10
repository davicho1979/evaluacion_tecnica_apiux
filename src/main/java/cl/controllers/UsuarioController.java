package cl.controllers;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.common.Common;
import cl.logger.ContextLogger;
import cl.model.entities.Usuario;
import cl.model.responses.GenericResponse;
import cl.model.responses.UsuarioResponse;
import cl.services.UsuarioService;

@RestController
public class UsuarioController 
{

	@Autowired
	private UsuarioService usuarioService;
	
    @Autowired
    private ContextLogger contextLogger;
    
    @Autowired
    private Common common;
    
	private static final Logger logger = LogManager.getLogger(UsuarioController.class);
	
	@GetMapping(value = "/listarUsuarios", produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<GenericResponse<Iterable<Usuario>>> listarUsuarios()
	{
		contextLogger.upgradeContextLogger("REQUEST", "GET /listarUsuarios");
		logger.info("{}", "all()");
		return usuarioService.listarUsuarios();
	}	

	@GetMapping(value = "/obtenerUsuarioById", produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<GenericResponse<Usuario>> obtenerUsuarioById(@RequestParam("id") String idUsuario) 
	{
		contextLogger.upgradeContextLogger("REQUEST", "GET /obtenerUsuarioById");
		logger.info("{}", "id -> "+idUsuario);
		return usuarioService.buscarUsuarioById(idUsuario);
	}

	@GetMapping(value = "/obtenerUsuarioByEmail", produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<GenericResponse<Usuario>> obtenerUsuarioByEmail(@RequestParam("email") String email) 
	{
		contextLogger.upgradeContextLogger("REQUEST", "GET /obtenerUsuarioByEmail");
		logger.info("{}", "email -> "+email);
		return usuarioService.buscarUsuarioPorEmail(email);
	}

	@Transactional	
	@RequestMapping(value = "/guardarUsuario", method = RequestMethod.POST,
	        consumes = {MediaType.APPLICATION_JSON_VALUE},
	        produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<GenericResponse<UsuarioResponse>> guardarUsuario(@RequestBody Usuario usuarioObj)
	{
		contextLogger.upgradeContextLogger("REQUEST", "POST /guardarUsuario");
		logger.info("{}", common.prettyPrinterOneLine(usuarioObj));
		return usuarioService.guardarUsuario(usuarioObj);
	}
	
}
