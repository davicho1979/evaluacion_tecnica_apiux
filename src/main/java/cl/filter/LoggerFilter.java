package cl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import cl.filter.wrapper.HeaderMapRequestWrapper;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.IdGenerator;


@Component
public class LoggerFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(LoggerFilter.class);

	@Autowired
	private Tracer tracer;

    @Value("${application.groupid}")
    private String appName;
    
	public static final String X_B3_TRACEID = "X-B3-TraceId";
	public static final String X_B3_SPANID = "X-B3-SpanId";
	public static final String X_B3_PARENTSPANTID = "X-B3-ParentSpanId";
	
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		logger.info("Initializing filter :{}", this);
	}
	
	private void setTraces(HttpServletRequest req) 
	{
		String tid = IdGenerator.random().generateTraceId();
        String sid = IdGenerator.random().generateSpanId();

        SpanContext parentSpanContext = SpanContext.createFromRemoteParent(tid,sid, TraceFlags.getSampled(), TraceState.getDefault());
        Span parentSpan = Span.wrap(parentSpanContext);

        Span span = tracer.spanBuilder(appName)
                .setParent(Context.current().with(parentSpan))
                .startSpan();

		span.end();
		parentSpan.end();
		
		String parentSpanId = (req.getHeader(X_B3_TRACEID)==null) ? parentSpan.getSpanContext().getTraceId() : req.getHeader(X_B3_TRACEID);
		String spanId = (req.getHeader(X_B3_SPANID)==null) ? span.getSpanContext().getSpanId() : req.getHeader(X_B3_SPANID);
		String traceId = (req.getHeader(X_B3_PARENTSPANTID)==null) ? span.getSpanContext().getTraceId() : req.getHeader(X_B3_PARENTSPANTID);
		
		ThreadContext.put(X_B3_TRACEID, (traceId != null) ? traceId : "N/A");
		ThreadContext.put(X_B3_SPANID, (spanId != null) ? spanId : "N/A");
		ThreadContext.put(X_B3_PARENTSPANTID, (parentSpanId != null) ? parentSpanId : "N/A");
		
		RequestContextHolder.getRequestAttributes().setAttribute(X_B3_TRACEID, (traceId != null) ? traceId : "N/A", RequestAttributes.SCOPE_REQUEST);
		RequestContextHolder.getRequestAttributes().setAttribute(X_B3_SPANID, (spanId != null) ? spanId : "N/A", RequestAttributes.SCOPE_REQUEST);
		RequestContextHolder.getRequestAttributes().setAttribute(X_B3_PARENTSPANTID, (parentSpanId != null) ? parentSpanId : "N/A", RequestAttributes.SCOPE_REQUEST);
		
		//RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		//String[] atts =  attributes.getAttributeNames(RequestAttributes.SCOPE_REQUEST);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HeaderMapRequestWrapper requestWrapper;
		
		try 
		{

			requestWrapper = new HeaderMapRequestWrapper(req);
			this.setTraces(req);
			
			/*
			InputStream inputStream = requestWrapper.getInputStream();
	        byte[] body = StreamUtils.copyToByteArray(inputStream);
			String logBody = new String(body);
	        */
			;
			chain.doFilter(requestWrapper, response);

		} 
		catch (IllegalArgumentException | IOException | ServletException e) 
		{
			logger.error("Error PutRequestHeadersFilter ...");
		} 

	}

	@Override
	public void destroy() {
		logger.warn("Destructing filter :{}", this);
	}
}