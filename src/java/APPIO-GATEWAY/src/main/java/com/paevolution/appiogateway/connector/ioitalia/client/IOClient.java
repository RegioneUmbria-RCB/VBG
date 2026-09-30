package com.paevolution.appiogateway.connector.ioitalia.client;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.paevolution.appiogateway.connector.ioitalia.client.model.GetLimitedProfileByPOSTPayload;
import com.paevolution.appiogateway.connector.ioitalia.client.model.InlineResponse201;
import com.paevolution.appiogateway.connector.ioitalia.client.model.LimitedProfile;
import com.paevolution.appiogateway.connector.ioitalia.client.model.MessageResponseWithContent;
import com.paevolution.appiogateway.connector.ioitalia.client.model.NewMessage;
import com.paevolution.appiogateway.connector.ioitalia.client.model.SubscriptionsFeed;
import com.paevolution.appiogateway.utils.WebConstants;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

public class IOClient {

    private WebClient webClient;
    private static final Long timeout = 30000L;

    @Autowired
    public IOClient(WebClient webClient) {

	this.webClient = webClient;
    }

    public void setWebClient(WebClient webClient) {

	this.webClient = webClient;
    }

    public LimitedProfile getUserProfileByPOSTPayload(GetLimitedProfileByPOSTPayload fiscalCodePayload, Map<String, String> headers)
	    throws Throwable {

	try {
	    return this.webClient.post().uri(WebConstants.PROFILES_URI)
		    .headers(httpAdditionalHeader -> setRequestHeaders(httpAdditionalHeader, headers))
		    .body(Mono.just(fiscalCodePayload), GetLimitedProfileByPOSTPayload.class).retrieve().bodyToMono(LimitedProfile.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }

    public LimitedProfile getUserProfileByFiscalCodeAsPathParameter(String fiscalCode, Map<String, String> headers) throws Throwable {

	try {
	    return this.webClient.get().uri(WebConstants.PROFILES_URI_WITH_FISCAL_CODE_PARAM, fiscalCode)
		    .headers(httpAdditionalHeader -> setRequestHeaders(httpAdditionalHeader, headers)).accept(MediaType.APPLICATION_JSON).retrieve()
		    .bodyToMono(LimitedProfile.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }

    public InlineResponse201 postNewMessageWithFiscalCodeInRequestBody(NewMessage message, Map<String, String> headers) throws Throwable {

	try {
	    return this.webClient.post().uri(WebConstants.MESSAGES_URI)
		    .headers(httpAdditionalHeader -> setRequestHeaders(httpAdditionalHeader, headers)).body(Mono.just(message), NewMessage.class)
		    .retrieve().bodyToMono(InlineResponse201.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }

    public InlineResponse201 postNewMessagePassingFiscalCodeAsPathParameter(String fiscalCode, NewMessage message, Map<String, String> headers)
	    throws Throwable {

	try {
	    return this.webClient.post().uri(WebConstants.MESSAGES_URI_WITH_FISCAL_CODE_PARAM, fiscalCode)
		    .headers(httpAdditionalHeader -> setRequestHeaders(httpAdditionalHeader, headers)).body(Mono.just(message), NewMessage.class)
		    .retrieve().bodyToMono(InlineResponse201.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }

    public MessageResponseWithContent getMessage(String fiscalCode, String idTransazione, Map<String, String> headers) throws Throwable {

	try {
	    return this.webClient.get().uri(WebConstants.MESSAGES_URI_WITH_FISCAL_CODE_AND_ID_PARAMS, fiscalCode, idTransazione)
		    .headers(httpAdditionalHeader -> setRequestHeaders(httpAdditionalHeader, headers)).accept(MediaType.APPLICATION_JSON).retrieve()
		    .bodyToMono(MessageResponseWithContent.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }

    public SubscriptionsFeed getSubscriptionsFeed(@DateTimeFormat(pattern = "yyyy-MM-dd") Date subscribeOrUnsubscribeDate,
	    Map<String, String> headers) throws Throwable {

	try {
	    return this.webClient.get().uri(WebConstants.SUBSCRIPTIONS_FEED_URI_WITH_DATE_PARAM, subscribeOrUnsubscribeDate)
		    .headers(httpAdditionalHeader -> setRequestHeaders(httpAdditionalHeader, headers)).accept(MediaType.APPLICATION_JSON).retrieve()
		    .bodyToMono(SubscriptionsFeed.class).block();
	} catch (WebClientResponseException ex) {
	    throw Exceptions.unwrap(ex);
	}
    }

    private void setRequestHeaders(HttpHeaders httpHeaders, Map<String, String> headerMap) {

	headerMap.forEach((k, v) -> httpHeaders.set(k, v));
    }

    private Predicate<HttpStatus> httpStatusEqualsTo(HttpStatus status) {

	return httpStatus -> httpStatus.equals(status);
    }

    private Function<ClientResponse, Mono<? extends Throwable>> throwsException(Exception ex) {

	return error -> Mono.error(ex);
    }
}
