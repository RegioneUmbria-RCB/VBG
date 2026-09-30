package it.paevolution.fileconverter2.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.health.autoconfigure.contributor.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import it.paevolution.fileconverter2.health.model.GotenbergHealthStatus;
import it.paevolution.fileconverter2.util.Configurazione;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnEnabledHealthIndicator("customGontenbergHeath")
/*
To disable a particular indicator, we can set the “management.health.<indicator_identifier>.enabled” configuration property to false. For instance, if we add the following to our application.properties:
management.health.customGontenbergHeath.enabled=false
Copy
Then Spring Boot will disable the RandomHealthIndicator. To activate this configuration property, we should also add the @ConditionalOnEnabledHealthIndicator annotation on the indicator:
*/
public class CustomGotenbergHealthIndicator implements HealthIndicator {

    @Autowired
    private Configurazione configurazione;
    private WebClient webClient;

    @Override
    public Health health() {

	if (!configurazione.isGotenbergAttivo()) {
	    return Health.up().withDetail("goten", "Non attivo da configurazione, verifica application.yaml").build();
	}
	GotenbergHealthStatus checkGoten = null;
	try {
	    checkGoten = checkGoten();
	    if (!checkGoten.getStatus().equalsIgnoreCase("up")) {
		return Health.down().withDetail("goten", checkGoten).build();
	    }
	} catch (Exception e) {
	    //   log.error("Errore nel recupero delle informazioni sulla versione rabbit", e);
	    return Health.down().withDetail("Error Code", e.getMessage()).build();
	}
	return Health.up().withDetail("goten", checkGoten).build();
    }

    private GotenbergHealthStatus checkGoten() {

	this.webClient = WebClient.builder().baseUrl(configurazione.getGotenbergBaseUrl()).build();
	Mono<GotenbergHealthStatus> ret = this.webClient.get().uri("/health").retrieve().bodyToMono(GotenbergHealthStatus.class);
	return ret.block();
    }
}
