package cl.services;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.common.Common;
import cl.common.Constants;
import cl.logger.ContextLogger;
import cl.model.dao.TareaDAO;
import cl.model.entities.Tarea;
import cl.model.request.TareaEditRequest;
import cl.model.request.TareaSaveRequest;
import cl.model.responses.GenericResponse;
import cl.model.responses.Result;

@Service
public class TareaService 
{
    @Autowired
    private ContextLogger contextLogger;
 
    @Autowired
    private TareaDAO tareaDAO;
    
    @Autowired
    private Common common;
    
	private static final Logger logger = LogManager.getLogger(TareaService.class);
	
    public ResponseEntity<GenericResponse<Iterable<Tarea>>> listarTareas() 
    {
    	GenericResponse<Iterable<Tarea>> response = new GenericResponse<Iterable<Tarea>>();
		Result result = Result.builder().build();
		Iterable<Tarea> tareaList = new ArrayList<Tarea>();

		try 
		{
			tareaList = tareaDAO.listarTareas();
			int countList = common.size(tareaList);
			if (countList>0)
			{
				result.setSuccess(true);			
				result.setCode(Constants.CODE_OK);
				result.setMessage("Success");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(tareaList);	
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /listarTareas");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);				
			}
			else
			{
				result.setSuccess(true);				
				result.setCode(Constants.CODE_WARNING);				
				result.setMessage("Tareas no encontradss");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(null);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /listarTareas");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);					
			}
		}
		catch(Exception e) 
		{
			result.setSuccess(false);
			result.setCode(Constants.CODE_EXCEPTION);
			result.setMessage(e.getMessage());
			result.setTimestamp(common.getCurrentDateTime());
			response.setData(null);
			response.setResult(result);
			
			contextLogger.upgradeContextLogger("RESPONSE", "GET /listarTareas");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}		

    }
    
    public ResponseEntity<GenericResponse<Tarea>> buscarTareaById(Integer idTarea) 
    {
		GenericResponse<Tarea> response = new GenericResponse<Tarea>();
		Result result = Result.builder().build();
		try 
		{
			Optional<Tarea> tareaAux = tareaDAO.findById(idTarea);

			if (tareaAux.isPresent())
			{
				result.setSuccess(true);			
				result.setCode(Constants.CODE_OK);
				result.setMessage("Success");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(tareaAux.get());
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerTareaById");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);					
			}
			else
			{
				result.setSuccess(true);				
				result.setCode(Constants.CODE_WARNING);				
				result.setMessage("Tarea no encontrada");	
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(null);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerTareaById");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);					
			}
		}
		catch(Exception e) 
		{
			result.setSuccess(false);
			result.setCode(Constants.CODE_EXCEPTION);
			result.setMessage(e.getMessage());
			result.setTimestamp(common.getCurrentDateTime());
			response.setData(null);
			response.setResult(result);
			
			contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerTareaById");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}	
    }
    
    public ResponseEntity<GenericResponse<Iterable<Tarea>>> obtenerTareasByDescripcion(String descripcion) 
    {
		GenericResponse<Iterable<Tarea>> response = new GenericResponse<Iterable<Tarea>>();
		Result result = Result.builder().build();
		Iterable<Tarea> tareaList = new ArrayList<Tarea>();
		
		try 
		{
			tareaList = tareaDAO.findByDescripcion(descripcion);
			int countList = common.size(tareaList);
			if (countList>0)
			{
				result.setSuccess(true);			
				result.setCode(Constants.CODE_OK);
				result.setMessage("Success");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(tareaList);	
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerTareasByDescripcion");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);				
			}
			else
			{
				result.setSuccess(true);				
				result.setCode(Constants.CODE_WARNING);				
				result.setMessage("Tareas no encontradss");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(null);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerTareasByDescripcion");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);					
			}
		}
		catch(Exception e) 
		{
			result.setSuccess(false);
			result.setCode(Constants.CODE_EXCEPTION);
			result.setMessage(e.getMessage());
			result.setTimestamp(common.getCurrentDateTime());
			response.setData(null);
			response.setResult(result);
			
			contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerTareasByDescripcion");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}		
    }
    
    @Transactional
    public ResponseEntity<GenericResponse<Tarea>> eliminarTarea(Integer idTarea) 
    {
		GenericResponse<Tarea> response = new GenericResponse<Tarea>();
		Result result = Result.builder().build();
		try 
		{
			    int r = tareaDAO.deleteById(idTarea);
			    if (r > 0)
			    {
					result.setSuccess(true);			
					result.setCode(Constants.CODE_OK);
					result.setMessage("Tarea eliminada con exito");
					result.setTimestamp(common.getCurrentDateTime());
					
					response.setData(null);
					response.setResult(result);
					
					contextLogger.upgradeContextLogger("RESPONSE", "DELETE /eliminarTarea");
					logger.info("{}", common.prettyPrinterOneLine(response));
					
					return ResponseEntity.ok().body(response);
			    }
			    else
			    {
					result.setSuccess(true);			
					result.setCode(Constants.CODE_WARNING);
					result.setMessage("Tarea no eliminada");
					result.setTimestamp(common.getCurrentDateTime());
					
					response.setData(null);
					response.setResult(result);
					
					contextLogger.upgradeContextLogger("RESPONSE", "DELETE /eliminarTarea");
					logger.info("{}", common.prettyPrinterOneLine(response));
					
					return ResponseEntity.badRequest().body(response);
			    }	
		}
		catch(Exception e) 
		{
			result.setSuccess(false);
			result.setCode(Constants.CODE_EXCEPTION);
			result.setMessage(e.getMessage());
			result.setTimestamp(common.getCurrentDateTime());
			response.setData(null);
			response.setResult(result);
			
			contextLogger.upgradeContextLogger("RESPONSE", "DELETE /eliminarTarea");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}	
    } 
    
    
	@Transactional
    public ResponseEntity<GenericResponse<Tarea>> guardarTarea(TareaSaveRequest tareaObj) 
    {
		GenericResponse<Tarea> response = new GenericResponse<Tarea>();
		Result result = Result.builder().build();
		try 
		{
				Tarea t = new Tarea();
				t.setDescripcionTarea(tareaObj.getDescripcion());
				t.setVigenciaTarea(tareaObj.getVigencia());
				t.setFechaCreacionTarea(common.getCurrentDate());
				
			    Tarea ttResult = tareaDAO.guardarTarea(t);
			    
			    if (ttResult!=null)
			    {
					result.setSuccess(true);			
					result.setCode(Constants.CODE_OK);
					result.setMessage("Tarea guardada con exito");
					result.setTimestamp(common.getCurrentDateTime());
					
					response.setData(ttResult);
					response.setResult(result);
					
					contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarTarea");
					logger.info("{}", common.prettyPrinterOneLine(response));
					
					return ResponseEntity.ok().body(response);
			    }
			    else
			    {
					result.setSuccess(true);			
					result.setCode(Constants.CODE_WARNING);
					result.setMessage("Tarea no guardada");
					result.setTimestamp(common.getCurrentDateTime());
					
					response.setData(null);
					response.setResult(result);
					
					contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarTarea");
					logger.info("{}", common.prettyPrinterOneLine(response));
					
					return ResponseEntity.badRequest().body(response);
			    }	
		}
		catch(Exception e) 
		{
			result.setSuccess(false);
			result.setCode(Constants.CODE_EXCEPTION);
			result.setMessage(e.getMessage());
			result.setTimestamp(common.getCurrentDateTime());
			response.setData(null);
			response.setResult(result);
			
			contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarTarea");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}	
    }  
	
	@Transactional
    public ResponseEntity<GenericResponse<Tarea>> editarTarea(TareaEditRequest tareaObj) 
    {
		GenericResponse<Tarea> response = new GenericResponse<Tarea>();
		Result result = Result.builder().build();
		try 
		{
			    Optional<Tarea> ttFind = tareaDAO.findById(tareaObj.getId());
			
			    if (ttFind.isPresent())
			    {
					Tarea t = new Tarea();
					t.setIdentificadorTarea(tareaObj.getId());
					t.setDescripcionTarea(tareaObj.getDescripcion());
					t.setVigenciaTarea(tareaObj.getVigencia());
					t.setFechaCreacionTarea(common.getCurrentDate());
					
				    Tarea ttResult = tareaDAO.guardarTarea(t);
				    
				    if (ttResult!=null)
				    {
						result.setSuccess(true);			
						result.setCode(Constants.CODE_OK);
						result.setMessage("Tarea editada con exito");
						result.setTimestamp(common.getCurrentDateTime());
						
						response.setData(ttResult);
						response.setResult(result);
						
						contextLogger.upgradeContextLogger("RESPONSE", "POST /editarTarea");
						logger.info("{}", common.prettyPrinterOneLine(response));
						
						return ResponseEntity.ok().body(response);
				    }
				    else
				    {
						result.setSuccess(true);			
						result.setCode(Constants.CODE_WARNING);
						result.setMessage("Tarea no editada");
						result.setTimestamp(common.getCurrentDateTime());
						
						response.setData(null);
						response.setResult(result);
						
						contextLogger.upgradeContextLogger("RESPONSE", "POST /editarTarea");
						logger.info("{}", common.prettyPrinterOneLine(response));
						
						return ResponseEntity.badRequest().body(response);
				    }
			    }
			    else
			    {
					result.setSuccess(true);			
					result.setCode(Constants.CODE_WARNING);
					result.setMessage("Tarea no editada. Id de tarea inexistente");
					result.setTimestamp(common.getCurrentDateTime());
					
					response.setData(null);
					response.setResult(result);
					
					contextLogger.upgradeContextLogger("RESPONSE", "POST /editarTarea");
					logger.info("{}", common.prettyPrinterOneLine(response));
					
					return ResponseEntity.badRequest().body(response);
			    }
	
		}
		catch(Exception e) 
		{
			result.setSuccess(false);
			result.setCode(Constants.CODE_EXCEPTION);
			result.setMessage(e.getMessage());
			result.setTimestamp(common.getCurrentDateTime());
			response.setData(null);
			response.setResult(result);
			
			contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarTarea");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}	
    } 	
}
