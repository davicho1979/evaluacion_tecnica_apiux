package cl.logger;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import cl.common.Common;
import cl.filter.wrapper.HeaderMapRequestWrapper;


@Component
public class ContextLogger 
{

	final Logger logger = (Logger) LogManager.getLogger();

    @Autowired
    private Common common;
    
	public  void initNewLoggerContext(Map<String, String> newMap)
	{
		for (Map.Entry<String, String> entry : newMap.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			ThreadContext.put(key, value);
			RequestContextHolder.getRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
		}
	}

	public void upgradeContextLogger(String layer, String operation) 
	{
		Optional<HttpServletRequest> rr = common.getCurrentHttpRequest();
		if (rr.isPresent()) 
		{
			try 
			{
				HeaderMapRequestWrapper rWrapper = new HeaderMapRequestWrapper(rr.get());
				rWrapper.addHeader("LAYER", layer);
				rWrapper.addHeader("OPERATION", operation);

				Map<String, String> allMap = rWrapper.getHeaderMap();
				this.initNewLoggerContext(allMap);

			} catch (IOException e) 
			{
				logger.error(e.getMessage());
			} 

		}
	}

}
