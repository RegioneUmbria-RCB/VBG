package com.paevolution.appiogateway.connector.ud.web.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.paevolution.appiogateway.configuration.AppIOProperties;
import com.paevolution.appiogateway.connector.ioitalia.client.IOClient;
import com.paevolution.appiogateway.connector.ioitalia.client.model.GetLimitedProfileByPOSTPayload;
import com.paevolution.appiogateway.connector.ioitalia.client.model.InlineResponse201;
import com.paevolution.appiogateway.connector.ioitalia.client.model.LimitedProfile;
import com.paevolution.appiogateway.connector.ioitalia.client.model.MessageResponseWithContent;
import com.paevolution.appiogateway.connector.ioitalia.client.model.NewMessage;
import com.paevolution.appiogateway.connector.ioitalia.client.model.SubscriptionsFeed;
import com.paevolution.appiogateway.utils.WebConstants;

import lombok.extern.slf4j.Slf4j;

/*
 * TODO: Configurare un client OAuth2 per Umbria Digitale
 */
@RestController
@RequestMapping(path = "/udconnector", produces = "application/json")
@CrossOrigin(origins = "*")
@Slf4j
public class UDConnectorRESTController {

    private WebClient webClient;
    private AppIOProperties properties;

    /// L'istanza di web client utilizzata e' quella di IOItalia.
    /// implementare e configurare un webclient come bean per Umbria Digitale
    /// impostando client-id e client-secret validi nel file di configurazione application.yml
    @Autowired
    public void setWebClient(@Qualifier("ioAPIBackendWebClient") WebClient webClient) {

	this.webClient = webClient;
    }

    @Autowired
    public void setProperties(AppIOProperties properties) {

	this.properties = properties;
    }

    @PostMapping(path = "/messages", consumes = "application/json")
    public InlineResponse201 postNewMessage(@RequestBody NewMessage message,
	    @RegisteredOAuth2AuthorizedClient("ioproxy") OAuth2AuthorizedClient authorizedClient) throws Throwable {

	return this.ioClient().postNewMessageWithFiscalCodeInRequestBody(message, defaultHeadersMap(tokenValueOf(authorizedClient)));
    }

    @PostMapping(path = "/messages/{fiscal_code}", consumes = "application/json")
    public InlineResponse201 postNewMessage(@PathVariable("fiscal_code") String fiscalCode, @RequestBody NewMessage message,
	    @RegisteredOAuth2AuthorizedClient("ioproxy") OAuth2AuthorizedClient authorizedClient) throws Throwable {

	return this.ioClient().postNewMessagePassingFiscalCodeAsPathParameter(fiscalCode, message, defaultHeadersMap(tokenValueOf(authorizedClient)));
    }
    /*
     * "id": "01EW0WHG0Q07HWGNTB0V48D7EV" "fiscal_code": "AAAAAA00A00A000A"
     */

    @GetMapping(path = "/messages/{fiscal_code}/{id}")
    public MessageResponseWithContent messageByFiscalCodeAndTransactionId(@PathVariable("fiscal_code") String fiscalCode,
	    @PathVariable("id") String idTransazione, @RegisteredOAuth2AuthorizedClient("ioproxy") OAuth2AuthorizedClient authorizedClient)
	    throws Throwable {

	return this.ioClient().getMessage(fiscalCode, idTransazione, defaultHeadersMap(tokenValueOf(authorizedClient)));
    }

    @PostMapping(path = "/profiles", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public LimitedProfile postProfile(@RequestBody GetLimitedProfileByPOSTPayload limitedProfileRequest,
	    @RegisteredOAuth2AuthorizedClient("ioproxy") OAuth2AuthorizedClient authorizedClient) throws Throwable {

	return this.ioClient().getUserProfileByPOSTPayload(limitedProfileRequest, defaultHeadersMap(tokenValueOf(authorizedClient)));
    }

    @GetMapping(path = "/profiles/{fiscal_code}")
    public LimitedProfile getProfile(@PathVariable("fiscal_code") String fiscalCode,
	    @RegisteredOAuth2AuthorizedClient("ioproxy") OAuth2AuthorizedClient authorizedClient) throws Throwable {

	return this.ioClient().getUserProfileByFiscalCodeAsPathParameter(fiscalCode, defaultHeadersMap(tokenValueOf(authorizedClient)));
    }

    @GetMapping(path = "/subscriptions-feed/{date}")
    public SubscriptionsFeed subscriptionsFeed(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateUTC,
	    @RegisteredOAuth2AuthorizedClient("ioproxy") OAuth2AuthorizedClient authorizedClient) throws Throwable {

	log.debug("Formato data {}", dateUTC.toString());
	return this.ioClient().getSubscriptionsFeed(dateUTC, defaultHeadersMap(tokenValueOf(authorizedClient)));
    }

    private IOClient ioClient() {

	return new IOClient(this.webClient);
    }

    /**
     * usare un OCP_APIM_SUBSCRIPTION_KEY_HEADER valido associato alla chiave primaria di un servizio di IO Italia di
     * test.
     * 
     * @return
     */
    private Map<String, String> defaultHeadersMap(String accessToken) {

	Map<String, String> resultMap = new HashMap<>();
	resultMap.put(WebConstants.OCP_APIM_SUBSCRIPTION_KEY_HEADER, properties.getIOAPIPrimaryKey());
	resultMap.put(HttpHeaders.AUTHORIZATION, WebConstants.BEARER_AUTHENTICATION_HEADER + accessToken);
	return resultMap;
    }

    private String tokenValueOf(OAuth2AuthorizedClient authorizedClient) {

	return authorizedClient.getAccessToken().getTokenValue();
    }
}
