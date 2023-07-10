package cl.config;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracerBean {

    @Value("${application.groupid}")
    private String appName;

    private final String OPEN_TELEMTRY_IMPLEMENTATION_VERSION = "1.0.0";

    @Bean
    public Tracer initTracer()
    {

        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder().build();

        OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .buildAndRegisterGlobal();

        Tracer tracer = openTelemetry.getTracer(appName, OPEN_TELEMTRY_IMPLEMENTATION_VERSION);

        return tracer;

    }

}
