package com.paevolution.appiogateway.connector.ioitalia.web.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(path = "/ioitaliaconnector", produces = "application/json")
@CrossOrigin(origins = "*")
@Slf4j
public class IORestController {

    private WebClient webClient;
    private AppIOProperties properties;
    private IOClient ioClient;

    @Autowired
    public IORestController(@Qualifier("ioAPIBackendWebClient") WebClient webClient) {

	setWebClient(webClient);
	this.ioClient = new IOClient(this.webClient);
    }

    public void setWebClient(WebClient webClient) {

	this.webClient = webClient;
    }

    @Autowired
    public void setProperties(AppIOProperties properties) {

	this.properties = properties;
    }

    @PostMapping(path = "/messages", consumes = "application/json")
    public InlineResponse201 postNewMessage(@RequestBody NewMessage message) throws Throwable {

	return this.ioClient.postNewMessageWithFiscalCodeInRequestBody(message, defaultHeadersMap());
    }

    @PostMapping(path = "/messages/{fiscal_code}", consumes = "application/json")
    public InlineResponse201 postNewMessage(@PathVariable("fiscal_code") String fiscalCode, @RequestBody NewMessage message) throws Throwable {

	return this.ioClient.postNewMessagePassingFiscalCodeAsPathParameter(fiscalCode, message, defaultHeadersMap());
    }
    /*
     * "id": "01EW0WHG0Q07HWGNTB0V48D7EV" "fiscal_code": "AAAAAA00A00A000A"
     */

    @GetMapping(path = "/messages/{fiscal_code}/{id}")
    public MessageResponseWithContent messageByFiscalCodeAndTransactionId(@PathVariable("fiscal_code") String fiscalCode,
	    @PathVariable("id") String idTransazione) throws Throwable {

	return this.ioClient.getMessage(fiscalCode, idTransazione, defaultHeadersMap());
    }

    @PostMapping(path = "/profiles", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public LimitedProfile postProfile(@RequestBody GetLimitedProfileByPOSTPayload limitedProfileRequest) throws Throwable {

	return this.ioClient.getUserProfileByPOSTPayload(limitedProfileRequest, defaultHeadersMap());
    }

    @GetMapping(path = "/profiles/{fiscal_code}")
    public LimitedProfile getProfile(@PathVariable("fiscal_code") String fiscalCode) throws Throwable {

	return this.ioClient.getUserProfileByFiscalCodeAsPathParameter(fiscalCode, defaultHeadersMap());
    }

    @GetMapping(path = "/subscriptions-feed/{date}")
    public SubscriptionsFeed subscriptionsFeed(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateUTC) throws Throwable {

	log.debug("Formato data {}", dateUTC.toString());
	return this.ioClient.getSubscriptionsFeed(dateUTC, defaultHeadersMap());
    }

    /**
     * usare un OCP_APIM_SUBSCRIPTION_KEY_HEADER valido associato alla chiave primaria di un servizio di IO Italia di
     * test.
     * 
     * @return
     */
    private Map<String, String> defaultHeadersMap() {

	Map<String, String> resultMap = new HashMap<String, String>();
	resultMap.put(WebConstants.OCP_APIM_SUBSCRIPTION_KEY_HEADER, properties.getIOAPIPrimaryKey());
	return resultMap;
    }
}
