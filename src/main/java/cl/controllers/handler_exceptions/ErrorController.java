package cl.controllers.handler_exceptions;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import cl.common.Common;
import cl.common.Constants;
import cl.logger.ContextLogger;
import cl.model.responses.GenericResponse;
import cl.model.responses.Result;

@ControllerAdvice
public class ErrorController {

	@Autowired
	private ContextLogger contextLogger;

    @Autowired
    private Common common;
    
	private static final Logger logger = LogManager.getLogger(ErrorController.class);

	public static final String GENERIC_MESSAGE_HTTPMESSAGENOTREADABLE = "Cuerpo de mensaje incompleto.  Se requiere incorporar argumentos obligatorios";
	public static final String GENERIC_MESSAGE_METHODARGUMENTNOTVALID = "Elementos requeridos en el cuerpo del mensaje no han sido ingresados";
	public static final String GENERIC_MESSAGE_REQUIREDHEADERARG = "Cabeceras requeridas no han sido ingresadas ";
	public static final String GENERIC_MESSAGE_HTTPREQUESTMETHODNOTSUPPORTED = "Requerimiento no soportado";
	public static final String GENERIC_MESSAGE_EXCEPTION ="Excepción inesperada.  Informe a su administrador";
	public static final String GENERIC_MESSAGE_MISSINGREQUESTPARAMETER = "Parametros requeridos no han sido ingresados";
	public static final String GENERIC_MESSAGE_INVALID_REQUEST = "Requerimiento inválido";
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<GenericResponse<Object>> handleException(HttpMessageNotReadableException exception)
    {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	
		GenericResponse<Object> response = new GenericResponse<Object>();
		Result result = Result.builder()
						.success(Boolean.FALSE)
						.message(GENERIC_MESSAGE_HTTPMESSAGENOTREADABLE)
						.code(Constants.CODE_WARNING)
						.errorCode(null)
						.timestamp(common.getCurrentDateTime())
						.build();
		response.setData(null);
		response.setResult(result);
    	
		contextLogger.upgradeContextLogger("RESPONSE", request.getMethod()+request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logger.info("{}", common.prettyPrinterOneLine(response));
				
    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
    
    @ExceptionHandler
    public ResponseEntity<GenericResponse<Object>> handleException(MethodArgumentNotValidException exception) 
    {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		GenericResponse<Object> response = new GenericResponse<Object>();
		Result result = Result.builder()
						.success(Boolean.FALSE)
						.message(GENERIC_MESSAGE_METHODARGUMENTNOTVALID)
						.code(Constants.CODE_WARNING)
						.errorCode(null)
						.timestamp(common.getCurrentDateTime())
						.build();
		response.setData(null);
		response.setResult(result);
		
		contextLogger.upgradeContextLogger("RESPONSE", request.getMethod()+request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logger.info("{}", common.prettyPrinterOneLine(response));
				
    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }	
    
    @ExceptionHandler
    public ResponseEntity<GenericResponse<Object>> handleException(MissingServletRequestParameterException exception) 
    {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	
		GenericResponse<Object> response = new GenericResponse<Object>();
		Result result = Result.builder()
						.success(Boolean.FALSE)
						.message(GENERIC_MESSAGE_MISSINGREQUESTPARAMETER)
						.code(Constants.CODE_WARNING)
						.errorCode(null)
						.timestamp(common.getCurrentDateTime())
						.build();
		response.setData(null);
		response.setResult(result);
		
		contextLogger.upgradeContextLogger("RESPONSE", request.getMethod()+request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logger.info("{}", common.prettyPrinterOneLine(response));
				
    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    	  
    } 
  
    
    @ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<GenericResponse<Object>> handleException(MissingRequestHeaderException exception)
    {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		GenericResponse<Object> response = new GenericResponse<Object>();
		Result result = Result.builder()
						.success(Boolean.FALSE)
						.message(GENERIC_MESSAGE_REQUIREDHEADERARG)
						.code(Constants.CODE_WARNING)
						.errorCode(null)
						.timestamp(common.getCurrentDateTime())
						.build();
		response.setData(null);
		response.setResult(result);
		
		contextLogger.upgradeContextLogger("RESPONSE", request.getMethod()+request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logger.info("{}", common.prettyPrinterOneLine(response));
				
    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
    
    
    @ExceptionHandler
    public ResponseEntity<GenericResponse<Object>> handleException(HttpRequestMethodNotSupportedException exception) 
    {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	
		GenericResponse<Object> response = new GenericResponse<Object>();
		Result result = Result.builder()
						.success(Boolean.FALSE)
						.message(GENERIC_MESSAGE_HTTPREQUESTMETHODNOTSUPPORTED)
						.code(Constants.CODE_WARNING)
						.errorCode(null)
						.timestamp(common.getCurrentDateTime())
						.build();
		response.setData(null);
		response.setResult(result);
		
		contextLogger.upgradeContextLogger("RESPONSE", request.getMethod()+request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logger.info("{}", common.prettyPrinterOneLine(response));
				
    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    	
    }
    
    @ExceptionHandler
    public ResponseEntity<GenericResponse<Object>> handleException(NotReadablePropertyException exception) 
    {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	
		GenericResponse<Object> response = new GenericResponse<Object>();
		Result result = Result.builder()
						.success(Boolean.FALSE)
						.message(GENERIC_MESSAGE_METHODARGUMENTNOTVALID)
						.code(Constants.CODE_WARNING)
						.errorCode(null)
						.timestamp(common.getCurrentDateTime())
						.build();
		response.setData(null);
		response.setResult(result);
		
		contextLogger.upgradeContextLogger("RESPONSE", request.getMethod()+request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logger.info("{}", common.prettyPrinterOneLine(response));
				
    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    	
    }    
    
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericResponse<Object>> handleException(Exception exception)
	{
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	
		GenericResponse<Object> response = new GenericResponse<Object>();
		Result result = Result.builder()
						.success(Boolean.FALSE)
						.message(GENERIC_MESSAGE_EXCEPTION)
						.code(Constants.CODE_WARNING)
						.errorCode(null)
						.timestamp(common.getCurrentDateTime())
						.build();
		response.setData(null);
		response.setResult(result);
		
		contextLogger.upgradeContextLogger("RESPONSE", request.getMethod()+request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logger.info("{}", common.prettyPrinterOneLine(response));
				
    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    	
	}
}
