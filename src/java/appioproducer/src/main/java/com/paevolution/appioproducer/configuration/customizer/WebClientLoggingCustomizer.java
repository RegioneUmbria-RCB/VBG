package com.paevolution.appioproducer.configuration.customizer;

import java.util.List;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class WebClientLoggingCustomizer implements WebClientCustomizer {

    @Override
    public void customize(Builder webClientBuilder) {

	webClientBuilder.filter(logRequest());
	webClientBuilder.filter(logResponse());
    }

    private ExchangeFilterFunction logRequest() {

	return (clientRequest, next) -> {
	    log.debug("Request: <method='{}', url='{}'>", new Object[] { clientRequest.method(), clientRequest.url() });
	    log.debug("--- HTTP Headers of Request ---");
	    clientRequest.headers().forEach(this::logHeader);
	    log.debug("-------------------------------");
	    return next.exchange(clientRequest);
	};
    }

    private ExchangeFilterFunction logResponse() {

	return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
	    log.debug("Response: <status_code={}>", clientResponse.statusCode());
	    log.debug("--- HTTP Headers of Response ---");
	    clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));
	    log.debug("--------------------------------");
	    return Mono.just(clientResponse);
	});
    }

    private void logHeader(String name, List<String> values) {

	values.forEach(value -> log.debug("{}={}", name, value));
    }
}
