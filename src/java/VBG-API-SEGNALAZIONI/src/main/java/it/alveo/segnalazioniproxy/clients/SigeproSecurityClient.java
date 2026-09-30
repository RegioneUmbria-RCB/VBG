package it.alveo.segnalazioniproxy.clients;

import it.alveo.segnalazioniproxy.dao.SgConfigurazioniEntiRepository;
import it.alveo.segnalazioniproxy.dao.SgConfigurazioniRepository;
import it.alveo.segnalazioniproxy.dao.SgPraticheRepository;
import it.alveo.segnalazioniproxy.entities.SgConfigurazioni;
import it.alveo.segnalazioniproxy.entities.SgConfigurazioniEnti;
import it.alveo.segnalazioniproxy.entities.SgPratiche;
import it.alveo.segnalazioniproxy.utils.SecurityHeaderCallback;
import it.alveo.segnalazioniproxy.utils.Utils;
import it.alveo.sigeproSecurity.ContestoType;
import it.alveo.sigeproSecurity.LoginRequest;
import it.alveo.sigeproSecurity.LoginResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


@Component
public class SigeproSecurityClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityClient.class);

    @Autowired
    private SgConfigurazioniRepository configurazioniRepository;
    @Autowired
    private SgConfigurazioniEntiRepository configurazioniEntiRepository;
    @Autowired
    private SgPraticheRepository praticheRepository;
    @Autowired
    private ApplicationContext applicationContext;

    public LoginResponse login(String alias, String software, String uuidSegnalazione) {
        SgConfigurazioni configurazione = configurazioniRepository.findByAliasAndSoftware(alias, software)
                .orElseThrow(() -> new IllegalArgumentException("Configurazione non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        //recupero della segnalazione a DB
        SgPratiche praticaConfig = praticheRepository.findByUuid(uuidSegnalazione).
                orElseThrow(() -> new IllegalArgumentException("Pratica non trova con uuid: " + uuidSegnalazione));

        //recupero della configurazione dell'ente a DB
        SgConfigurazioniEnti configurazioniEnti = configurazioniEntiRepository.findByCodiceComune(praticaConfig.getCodiceComune())
                .orElseThrow(() -> new IllegalArgumentException("Configurazione ente non trovata con alias: `" + alias + "` e software: `" + software + "`"));


        String securityUsername = configurazione.getSecurityWsUsername();
        String securityPassword = configurazione.getSecurityWsPassword();
        String securityServiceUrl = configurazione.getSecurityWsUrl();

        LoginRequest request = new LoginRequest();
        ContestoType contesto = ContestoType.APP;
        request.setAlias(configurazioniEnti.getDestIdEnte());
        request.setContesto(contesto);
        request.setUsername(securityUsername);
        request.setPassword(securityPassword);
        
        try {
	    request.setIpAddress(InetAddress.getLocalHost().getHostAddress());
	} catch (UnknownHostException e) {
	    request.setIpAddress("127.0.0.1");
	}
        
        log.info("LoginRequest inviata: {}", request);
        SecurityHeaderCallback headerCallback = new SecurityHeaderCallback(securityUsername, securityPassword);

        return (LoginResponse)
                Utils.getSecurityWebServiceTemplate(applicationContext, securityServiceUrl).marshalSendAndReceive(securityServiceUrl, request,
                        message -> {
                            new SoapActionCallback("Login").doWithMessage(message);
                            headerCallback.doWithMessage(message);
                        });
    }
}
