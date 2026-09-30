package it.alveo.ricalcoloaree.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import it.alveo.ricalcoloaree.configurations.RicalcoloAreeAppPropConfig;
import it.alveo.ricalcoloaree.sigeprosecurity.AmbienteType;
import it.alveo.ricalcoloaree.sigeprosecurity.CheckTokenRequest;
import it.alveo.ricalcoloaree.sigeprosecurity.CheckTokenResponse;
import it.alveo.ricalcoloaree.sigeprosecurity.GetDbConnectionInfoRequest;
import it.alveo.ricalcoloaree.sigeprosecurity.GetDbConnectionInfoResponse;
import it.alveo.ricalcoloaree.utils.SecurityHeaderCallback;
import it.alveo.ricalcoloaree.utils.Utils;

@Component
public class SigeproSecurityClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityClient.class);
    @Autowired
    private ApplicationContext applicationContext;

    public CheckTokenResponse checkToken(String token, RicalcoloAreeAppPropConfig confg) {

	log.debug("SigeproSecurityClient: Entro nel metodo checkToken config {}", confg);
	CheckTokenRequest req = new CheckTokenRequest();
	req.setToken(token);
	req.setTokenInfo(true);
	String securityUsername = confg.getTokenuser();
	String securityPassword = confg.getTokenpwd();
	SecurityHeaderCallback headerCallback = new SecurityHeaderCallback(securityUsername, securityPassword);
	String securityServiceUrl = confg.getTokenurl();
	log.debug("SigeproSecurityClient: prima di chiamare checkToken config {}", confg);
	return (CheckTokenResponse) Utils.getSecurityWebServiceTemplate(applicationContext, securityServiceUrl)
		.marshalSendAndReceive(securityServiceUrl, req, message -> {
		    new SoapActionCallback("CheckToken").doWithMessage(message);
		    headerCallback.doWithMessage(message);
		});
    }

    public GetDbConnectionInfoResponse getDbByAlias(String alias, RicalcoloAreeAppPropConfig confg) {

	log.debug("SigeproSecurityClient: Entro nel metodo GetDbConnectionInfo config {}", confg);
	GetDbConnectionInfoRequest req = new GetDbConnectionInfoRequest();
	req.setAlias(alias);
	req.setAmbiente(AmbienteType.JAVA);
	String securityUsername = confg.getTokenuser();
	String securityPassword = confg.getTokenpwd();
	SecurityHeaderCallback headerCallback = new SecurityHeaderCallback(securityUsername, securityPassword);
	String securityServiceUrl = confg.getTokenurl();
	log.debug("SigeproSecurityClient: prima di chiamare GetDbConnectionInfo {}", confg);
	return (GetDbConnectionInfoResponse) Utils.getSecurityWebServiceTemplate(applicationContext, securityServiceUrl)
		.marshalSendAndReceive(securityServiceUrl, req, message -> {
		    new SoapActionCallback("GetDbConnectionInfo").doWithMessage(message);
		    headerCallback.doWithMessage(message);
		});
    }
}
