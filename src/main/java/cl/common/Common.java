package cl.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.text.StringEscapeUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Common 
{
	
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;
    
	public Session getCurrentSessionFromJPA() 
	{
		  // JPA and Hibernate SessionFactory example
		  //EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
		  //EntityManager entityManager = emFactory.createEntityManager();
		  // Get the Hibernate Session from the EntityManager in JPA
		  org.hibernate.Session session = (Session) entityManager.getDelegate();
		  //Session session = entityManager.unwrap(org.hibernate.Session.class);
		  //SessionFactory factory = session.getSessionFactory();
		  //return factory.getCurrentSession();
		  return session;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public int size(Iterable data) {
		 
	    if (data instanceof Collection) {
	        return ((Collection<?>) data).size();
	    }
	    int counter = 0;
	    for (Object i : data) {
	        counter++;
	    }
	    return counter;
	}
	
	public String generateUUID() 
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public boolean verificarSintaxisEmail(String email)
	{
	    Pattern pat = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$");
	    Matcher mat = pat.matcher(email);
	    if (mat.find()) {
	        return true;
	    } else {
	        return false;
	    }		
	}
	
	public boolean verificarPatronContrasena(String contrasena)
	{
	    Pattern pat = Pattern.compile("^(?=(?:\\D*\\d){2}\\D*$)(?:[^A-Z]*[A-Z]){1}[^A-Z]*$");
	    Matcher mat = pat.matcher(contrasena);
	    if (mat.find()) {
	        return true;
	    } else {
	        return false;
	    }		
	}	

	public String getCurrentDateTime()
	{
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf2.format(timestamp);
	}
	
	public Date getCurrentDate() throws ParseException
	{	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC-04"));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String dateInString = formatter.format(timestamp); 
		return formatter.parse(dateInString);
	}
	
	public Optional<HttpServletRequest> getCurrentHttpRequest() {
	    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
	        .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
	        .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
	        .map(ServletRequestAttributes::getRequest);
	}
	
	public String prettyPrinterOneLine(Object result) 
    {
		ObjectMapper mapper = new ObjectMapper();
        String jsonResult = null;
		try 
		{
			jsonResult = mapper.writer().writeValueAsString(result);
		} 
		catch (JsonProcessingException e) 
		{
			
		}			
		return StringEscapeUtils.escapeJava(jsonResult);    	
    }
	
	public <T> T convertJsonToObject(String object,  Class<T> toValueType) 
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		try 
		{
			return mapper.readValue(object, toValueType);
		} 
		catch (Exception e) 
		{
			throw new RuntimeException();
		}

	}
}
