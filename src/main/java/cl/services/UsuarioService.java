package cl.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import cl.common.JWTGenerator;
import cl.logger.ContextLogger;
import cl.model.dao.TelefonoDAO;
import cl.model.dao.UsuarioDAO;
import cl.model.entities.Telefono;
import cl.model.entities.Usuario;
import cl.model.responses.GenericResponse;
import cl.model.responses.Result;
import cl.model.responses.UsuarioResponse;

@Service
public class UsuarioService 
{
    @Autowired
    private ContextLogger contextLogger;
    
    @Autowired
    private UsuarioDAO usuarioDAO;
 
    @Autowired
    private TelefonoDAO telefonoDAO;
    
    @Autowired
    private Common common;
    
	private static final Logger logger = LogManager.getLogger(UsuarioService.class);
	
    public ResponseEntity<GenericResponse<Iterable<Usuario>>> listarUsuarios() 
    {
		GenericResponse<Iterable<Usuario>> response = new GenericResponse<Iterable<Usuario>>();
		Result result = Result.builder().build();
		Iterable<Usuario> usuarioList = new ArrayList<Usuario>();
		
		try 
		{
			usuarioList = usuarioDAO.listarUsuarios();
			int countList = common.size(usuarioList);
			if (countList>0)
			{
				result.setSuccess(true);			
				result.setCode(Constants.CODE_OK);
				result.setMessage("Success");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(usuarioList);	
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /listarUsuarios");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);				
			}
			else
			{
				result.setSuccess(true);				
				result.setCode(Constants.CODE_WARNING);				
				result.setMessage("Usuarios no encontrados");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(null);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /listarUsuarios");
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
			
			contextLogger.upgradeContextLogger("RESPONSE", "GET /listarUsuarios");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}		

    }
    

    public ResponseEntity<GenericResponse<Usuario>> buscarUsuarioById(String idUsuario) 
    {
		GenericResponse<Usuario> response = new GenericResponse<Usuario>();
		Result result = Result.builder().build();
		try 
		{
			Optional<Usuario> usuarioAux = usuarioDAO.buscarUsuarioById(idUsuario);

			if (usuarioAux.isPresent())
			{
				result.setSuccess(true);			
				result.setCode(Constants.CODE_OK);
				result.setMessage("Success");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(usuarioAux.get());
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerUsuarioById");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);					
			}
			else
			{
				result.setSuccess(true);				
				result.setCode(Constants.CODE_WARNING);				
				result.setMessage("Usuario no encontrado");	
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(null);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerUsuarioById");
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
			
			contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerUsuarioById");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}	
    }    
    
    public  ResponseEntity<GenericResponse<Usuario>> buscarUsuarioPorEmail(String email) 
    {
		GenericResponse<Usuario> response = new GenericResponse<Usuario>();
		Result result = Result.builder().build();
		try 
		{
			Optional<Usuario> usuarioAux = usuarioDAO.buscarUsuarioPorEmail(email);

			if (usuarioAux.isPresent())
			{
				result.setSuccess(true);			
				result.setCode(Constants.CODE_OK);
				result.setMessage("Success");
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(usuarioAux.get());
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerUsuarioByEmail");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);			
			}
			else
			{
				result.setSuccess(true);				
				result.setCode(Constants.CODE_WARNING);				
				result.setMessage("Usuario no encontrado");	
				result.setTimestamp(common.getCurrentDateTime());
				response.setData(null);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerUsuarioByEmail");
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
			
			contextLogger.upgradeContextLogger("RESPONSE", "GET /obtenerUsuarioByEmail");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}	
    }  
    
    @Transactional
    public ResponseEntity<GenericResponse<UsuarioResponse>> guardarUsuario(Usuario usuarioObj) 
    {
		GenericResponse<UsuarioResponse> response = new GenericResponse<UsuarioResponse>();
		Result result = Result.builder().build();
		try 
		{

			GenericResponse<UsuarioResponse> rCondiciones = this.verificarCondiciones(usuarioObj);
			
			if (rCondiciones.getResult().getCode().equals(Constants.CODE_WARNING))
			{
				contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarUsuario");
				logger.info("{}", common.prettyPrinterOneLine(rCondiciones));
		
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rCondiciones);
			}

				
			String uuId = common.generateUUID();
			String jwt = JWTGenerator.createJWT(uuId, null, null, 300000);
			usuarioObj.setIdUsuario(uuId);
			usuarioObj.setActivo(1);
			usuarioObj.setFechaCreacion(common.getCurrentDate());
			usuarioObj.setToken(jwt);
			
			Usuario usuarioAux = usuarioDAO.guardarUsuario(usuarioObj);
			
			if (usuarioAux!=null)
			{
				this.guardarTelefonos(usuarioObj, uuId);
				
				Usuario user = usuarioDAO.buscarUsuarioById(uuId).get();
				UsuarioResponse userResponse = new UsuarioResponse();
				userResponse.setIdUsuario(user.getIdUsuario());				
				userResponse.setFechaCreacion(user.getFechaCreacion());
				userResponse.setFechaModificacion(user.getFechaModificacion());
				userResponse.setFechaSesion(user.getFechaSesion());
				userResponse.setActivo(user.getActivo());
				userResponse.setToken("*****");
				
				result.setSuccess(true);			
				result.setCode(Constants.CODE_OK);
				result.setMessage("Usuario guardado con exito");
				result.setTimestamp(common.getCurrentDateTime());
				
				response.setData(userResponse);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarUsuario");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.ok().body(response);
			}
			else
			{

				result.setSuccess(true);				
				result.setCode(Constants.CODE_WARNING);				
				result.setMessage("Usuario no guardado");	
				result.setTimestamp(common.getCurrentDateTime());
				
				response.setData(null);
				response.setResult(result);
				
				contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarUsuario");
				logger.info("{}", common.prettyPrinterOneLine(response));
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);					
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
			
			contextLogger.upgradeContextLogger("RESPONSE", "POST /guardarUsuario");
			logger.error("{}", common.prettyPrinterOneLine(response));
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}	
    }   
    
	@Transactional	
	private GenericResponse<UsuarioResponse> verificarCondiciones(Usuario usuarioObj)
	{
		
		GenericResponse<UsuarioResponse> response = new GenericResponse<UsuarioResponse>();
		Result result = Result.builder().build();
		result.setCode(Constants.CODE_OK);
		
		if (this.existeEmail(usuarioObj))
		{
			result.setSuccess(true);				
			result.setCode(Constants.CODE_WARNING);				
			result.setMessage("Usuario no guardado, email ya se encuentra previamente registrado ");
			result.setTimestamp(common.getCurrentDateTime());
			
			response.setData(null);
			response.setResult(result);
		}
		if (!common.verificarSintaxisEmail(usuarioObj.getEmail()))
		{
			result.setSuccess(true);				
			result.setCode(Constants.CODE_WARNING);				
			result.setMessage("Usuario no guardado, email inválido (no cumple sintaxis de email) ");
			result.setTimestamp(common.getCurrentDateTime());
			
			response.setData(null);
			response.setResult(result);
		}
		if (!common.verificarPatronContrasena(usuarioObj.getPassword()))
		{
			result.setSuccess(true);				
			result.setCode(Constants.CODE_WARNING);				
			result.setMessage("Usuario no guardado, password inválida (no cumple políticas)");
			result.setTimestamp(common.getCurrentDateTime());
			
			response.setData(null);
			response.setResult(result);
		}
		
		response.setResult(result);
		return response;
		
	}
    
	
	@Transactional	
	@SuppressWarnings({ "rawtypes" })
	private void guardarTelefonos(Usuario usuarioObj, String uuId)
	{
		List<Telefono> telefonoList = usuarioObj.getPhones();
		for (Iterator iterator = telefonoList.iterator(); iterator.hasNext();) 
		{
			Telefono telefono = (Telefono) iterator.next();
			telefono.setUsuario(uuId);
			telefonoDAO.guardarTelefono(telefono);
		}	
	}

	@Transactional	
	private boolean existeEmail(Usuario usuarioObj)
	{
		Optional<Usuario> usuarioAux = usuarioDAO.buscarUsuarioPorEmail(usuarioObj.getEmail());
		if (usuarioAux.isPresent())
			return true;
		else
			return false;	
	}	
}
