package com.paevolution.appioproducer.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.paevolution.appioproducer.ws.client.model.CreatedMessageResponse;
import com.paevolution.appioproducer.ws.client.model.MessaggiRequest;
import com.paevolution.appioproducer.ws.client.model.StatusMessageResponse;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

@Component
public class AppIOGatewayClient {

    private WebClient webClient;

    @Autowired
    public AppIOGatewayClient(@Qualifier("appiogatewayWSClient") WebClient webClient) {

	setWebClient(webClient);
    }

    public void setWebClient(WebClient webClient) {

	this.webClient = webClient;
    }

    public CreatedMessageResponse postMessage(MessaggiRequest message) throws Throwable {

	try {
	    return this.webClient.post().uri("/messages").body(Mono.just(message), MessaggiRequest.class).retrieve()
		    .bodyToMono(CreatedMessageResponse.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }

    public StatusMessageResponse getMessageStatus(String messageId) throws Throwable {

	try {
	    return this.webClient.get().uri("/messages/{message_id}", messageId).retrieve().bodyToMono(StatusMessageResponse.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }
}