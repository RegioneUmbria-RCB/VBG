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
	    log.info("Request: <method='{}', url='{}'>", new Object[] { clientRequest.method(), clientRequest.url() });
	    log.info("--- HTTP Headers of Request ---");
	    clientRequest.headers().forEach(this::logHeader);
	    log.info("-------------------------------");
	    return next.exchange(clientRequest);
	};
    }

    private ExchangeFilterFunction logResponse() {

	return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
	    log.info("Response: <status_code={}>", clientResponse.statusCode());
	    log.info("--- HTTP Headers of Response ---");
	    clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
	    log.info("--------------------------------");
	    return Mono.just(clientResponse);
	});
    }

    private void logHeader(String name, List<String> values) {

	values.forEach(value -> log.info("{}={}", name, value));
    }
}
