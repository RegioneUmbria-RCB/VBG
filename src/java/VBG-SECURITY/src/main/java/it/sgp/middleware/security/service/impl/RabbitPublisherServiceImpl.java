package it.sgp.middleware.security.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.micrometer.core.instrument.util.StringUtils;
import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.domain.ContestoEnum;
import it.sgp.middleware.security.rabbitmq.bean.RabbitParamsBean;
import it.sgp.middleware.security.rabbitmq.config.RabbitMQConfig;
import it.sgp.middleware.security.rabbitmq.config.RabbitMQConstants;
import it.sgp.middleware.security.rabbitmq.model.Header;
import it.sgp.middleware.security.rabbitmq.model.MessaggiRabbitMQBroker;
import it.sgp.middleware.security.rabbitmq.model.Utente;
import it.sgp.middleware.security.rabbitmq.modellazione.TopicEnum;
import it.sgp.middleware.security.rabbitmq.publisher.RabbitMQProducer;
import it.sgp.middleware.security.service.ComunisecurityParamService;
import it.sgp.middleware.security.service.RabbitPublisherService;

@Service
public class RabbitPublisherServiceImpl implements RabbitPublisherService {

    private static Logger log = LoggerFactory.getLogger(RabbitPublisherServiceImpl.class);
    private static final String CACHE_RABBIT_PARAMS = "CACHE_RABBIT_PARAMS";
    private static final String VERSIONE = "1.0"; //per ora li metterei costanti in quanto non utilizzati per lo scopo
    private static final String SOFTWARE = "TT"; //per ora li metterei costanti in quanto non utilizzati per lo scopo
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Cache<String, RabbitParamsBean> cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();
    private ComunisecurityParamService comunisecurityParamService;

    @Autowired
    public void setComunisecurityParamService(ComunisecurityParamService comunisecurityParamService) {

	this.comunisecurityParamService = comunisecurityParamService;
    }

    public static void main(String[] args) {

	ComunisecuritySession session = new ComunisecuritySession();
	session.setContesto(ContestoEnum.APP.name());
	if (!(session.getContesto().equalsIgnoreCase(ContestoEnum.OPE.name()) //
		|| session.getContesto().equalsIgnoreCase(ContestoEnum.UTEG.name()) //
		|| session.getContesto().equalsIgnoreCase(ContestoEnum.UTE.name()) //
	)) {
	    System.out.println(session.getContesto());
	}
    }

    @Override
    public void sendMessageIfRabbitEnabled(ComunisecuritySession session) {

	if (!(session.getContesto().equalsIgnoreCase(ContestoEnum.OPE.name()) //
		|| session.getContesto().equalsIgnoreCase(ContestoEnum.UTEG.name()) //
		|| session.getContesto().equalsIgnoreCase(ContestoEnum.UTE.name()) //
	)) {
	    return;
	}
	String rabbitHost = null;
	Integer rabbitPort = null;
	String exchangeName = null;
	try {
	    RabbitParamsBean rabbitParamsBean = cache.getIfPresent(CACHE_RABBIT_PARAMS);
	    if (rabbitParamsBean == null) {
		rabbitParamsBean = new RabbitParamsBean();
		rabbitParamsBean.setHostName(comunisecurityParamService.findById(RabbitMQConstants.RABBIT_HOSTNAME).getValue());
		rabbitParamsBean.setUserName(comunisecurityParamService.findById(RabbitMQConstants.RABBIT_USERNAME).getValue());
		rabbitParamsBean.setPassword(comunisecurityParamService.findById(RabbitMQConstants.RABBIT_PASSWORD).getValue());
		rabbitParamsBean.setPortNumber(Integer.parseInt(comunisecurityParamService.findById(RabbitMQConstants.RABBIT_PORT).getValue()));
		rabbitParamsBean.setExchangeName(comunisecurityParamService.findById(RabbitMQConstants.RABBIT_EXCHANGE_NAME).getValue());
		cache.put(CACHE_RABBIT_PARAMS, rabbitParamsBean);
	    }
	    if (rabbitParamsBean.getHostName() == null || rabbitParamsBean.getHostName().isBlank()) {
		return;
	    }
	    rabbitHost = rabbitParamsBean.getHostName();
	    rabbitPort = rabbitParamsBean.getPortNumber();
	    exchangeName = rabbitParamsBean.getExchangeName();
	    Header header = new Header();
	    header.setVersione(VERSIONE);
	    header.setSoftware(SOFTWARE);
	    header.setAlias(session.getAlias());
	    Utente utente = new Utente();
	    utente.setContesto(session.getContesto());
	    utente.setUserid(session.getUserid());
	    MessaggiRabbitMQBroker<Utente> messaggio = new MessaggiRabbitMQBroker<>();
	    messaggio.setHeader(header);
	    messaggio.setBody(utente);
	    RabbitMQProducer producer = new RabbitMQProducer(
		    new RabbitMQConfig(rabbitHost, rabbitPort, rabbitParamsBean.getUserName(), rabbitParamsBean.getPassword()), exchangeName,
		    TopicEnum.AUTENTICAZIONE_ACCESSO_LOGIN);
	    producer.sendMessage(UUID.randomUUID().toString(), objectMapper.writeValueAsString(messaggio));
	} catch (Exception e) {
	    String riferimentiRabbit = "HOST: " + rabbitHost + ":" + rabbitPort + ", exchangename=" + exchangeName;
	    log.error("Errore durante l'invio messaggio a Rabbit: " + riferimentiRabbit, e);
	}
    }
}
