package cl.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.common.Common;
import cl.logger.ContextLogger;
import cl.model.entities.Tarea;
import cl.model.request.TareaEditRequest;
import cl.model.request.TareaSaveRequest;
import cl.model.responses.GenericResponse;
import cl.services.TareaService;

@RestController
public class TareaController 
{

	@Autowired
	private TareaService tareaService;
	
    @Autowired
    private ContextLogger contextLogger;
    
    @Autowired
    private Common common;
    
	private static final Logger logger = LogManager.getLogger(TareaController.class);
	
	@GetMapping(value = "/listarTareas", produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<GenericResponse<Iterable<Tarea>>> listarTareas()
	{
		contextLogger.upgradeContextLogger("REQUEST", "GET /listarTareas");
		logger.info("{}", "all()");
		return tareaService.listarTareas();
	}	

	@GetMapping(value = "/obtenerTareaById", produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<GenericResponse<Tarea>> obtenerTareaById(@RequestParam("id") Integer idTarea) 
	{
		contextLogger.upgradeContextLogger("REQUEST", "GET /obtenerTareaById");
		logger.info("{}", "id -> "+idTarea);
		return tareaService.buscarTareaById(idTarea);
	}

	@GetMapping(value = "/obtenerTareasByDescripcion", produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<GenericResponse<Iterable<Tarea>>> obtenerTareasByDescripcion(@RequestParam("descripcion") String descripcion)
	{
		contextLogger.upgradeContextLogger("REQUEST", "GET /obtenerTareasByDescripcion");
		logger.info("{}", "all()");
		return tareaService.obtenerTareasByDescripcion(descripcion);
	}
	
	@Transactional
	@DeleteMapping(value = "/eliminarTarea", produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<GenericResponse<Tarea>> eliminarTarea(@RequestParam("id") Integer idTarea) 
	{
		contextLogger.upgradeContextLogger("REQUEST", "DELETE /eliminarTarea");
		logger.info("{}", "id -> "+idTarea);
		return tareaService.eliminarTarea(idTarea);
	}
	
	@Transactional	
	@RequestMapping(value = "/guardarTarea", method = RequestMethod.POST,
	        consumes = {MediaType.APPLICATION_JSON_VALUE},
	        produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<GenericResponse<Tarea>> guardarTarea(@Valid @RequestBody TareaSaveRequest tareaObj)
	{
		contextLogger.upgradeContextLogger("REQUEST", "POST /guardarTarea");
		logger.info("{}", common.prettyPrinterOneLine(tareaObj));
		return tareaService.guardarTarea(tareaObj);
	}
	
	@Transactional	
	@RequestMapping(value = "/editarTarea", method = RequestMethod.PUT,
	        consumes = {MediaType.APPLICATION_JSON_VALUE},
	        produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<GenericResponse<Tarea>> editarTarea(@Valid @RequestBody TareaEditRequest tareaObj)
	{
		contextLogger.upgradeContextLogger("REQUEST", "PUT /editarTarea");
		logger.info("{}", common.prettyPrinterOneLine(tareaObj));
		return tareaService.editarTarea(tareaObj);
	}
}
