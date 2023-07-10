package cl.test.evaluaciontecnica;


import static org.assertj.core.api.Assertions.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


class TestCasesTareas  extends BaseApplicationTest
{	
	

    
	private static final Logger logger = LogManager.getLogger(TestCasesTareas.class);
	
 	@Test
	void listarTareas() 
	{
		MvcResult result;
		try 
		{

			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get("http://127.0.0.1:8080/listarTareas")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
			
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse a = result.getResponse();

			assertThat(a.getStatus()).isEqualTo(200);

		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage());	
		}
	}	

 	@Test
	void listarTareaById() 
	{
		MvcResult result;
		try 
		{

			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get("http://127.0.0.1:8080/obtenerTareaById?id=1")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
			
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse a = result.getResponse();

			assertThat(a.getStatus()).isEqualTo(200);

		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage());	
		}
	}
 	
 	@Test
	void saveTareaOk() 
	{
		MvcResult result;
		try 
		{
		    String requestBody = "{\"descripcion\": \"Ejecución de Test\", \"vigencia\": true}";
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("http://127.0.0.1:8080/guardarTarea")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(requestBody);
			
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse a = result.getResponse();
			assertThat(a.getStatus()).isEqualTo(200);		
		
		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage());	
		}
	}
 	
 	@Test
	void saveTareaNOK() 
	{
		MvcResult result;
		try 
		{
		    String requestBody = "{\"vigencia\": true}";
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("http://127.0.0.1:8080/guardarTarea")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(requestBody);
			
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse a = result.getResponse();
			assertThat(a.getStatus()).isEqualTo(400);		
		
		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage());	
		}
	}	
}
