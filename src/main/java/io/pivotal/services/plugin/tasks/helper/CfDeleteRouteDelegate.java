package io.pivotal.services.plugin.tasks.helper;

import io.pivotal.services.plugin.CfProperties;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.routes.DeleteRouteRequest;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import reactor.core.publisher.Mono;

/**
 * Helper for deleting a route
 *
 * @author Biju Kunjummen
 */
public class CfDeleteRouteDelegate {

    private static final Logger LOGGER = Logging.getLogger(CfDeleteRouteDelegate.class);

    public Mono<Void> deleteRoute(CloudFoundryOperations cfOperations,
                                  CfProperties cfProperties) {

        return cfOperations.routes().delete(
            DeleteRouteRequest
                .builder()
                .domain(cfProperties.domain())
                .host(cfProperties.host())
                .path(cfProperties.path())
                .build()).doOnSubscribe((s) -> {
            LOGGER.lifecycle("Deleting hostname '{}' in domain '{}' with path '{}' of app '{}'", cfProperties.host(),
                cfProperties.domain(), cfProperties.path(), cfProperties.name());
        });

    }

}
