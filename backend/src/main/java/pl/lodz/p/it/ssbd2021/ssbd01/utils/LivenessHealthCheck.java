package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

    @Inject
    @ConfigProperty(name = "freememory.threshold", defaultValue = "10485760")
    private long threshold;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("MemoryHealthCheck Liveness check");
        long freeMemory = Runtime.getRuntime().freeMemory();
        if(freeMemory >= threshold) {
            responseBuilder.up();
        } else{
            responseBuilder.down().withData("error",
                    "Not enough free memory! Available " + freeMemory + "Please restart application");
        }
        return responseBuilder.build();
    }
}