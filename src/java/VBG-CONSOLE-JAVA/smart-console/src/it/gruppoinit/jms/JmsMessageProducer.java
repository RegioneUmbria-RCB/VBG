package it.gruppoinit.jms;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Classe delegata all'invio dei messaggi di audit
 * 
 * @author Riccardo Bocci
 * 
 */
public class JmsMessageProducer {

    private static final String JMS_DEFAULT_DESTINATION_QUEUE = "it.gruppoinit.sigepro.queue";
    private static final Logger log = LoggerFactory.getLogger(JmsMessageProducer.class);
    private JmsTemplate jmsProducerTemplate;
    private boolean initialized = false;

    public JmsMessageProducer() {

    }

    /**
     * 
     */
    public void initialize() {

	this.initialized = false;
	String auditServiceUrl = WebConstants.getSecurityParamValue(SecurityParams.AUDIT_SERVICE_URL);
	try {
	    ConnectionFactory factory = createConnectionFactory(auditServiceUrl);
	    createJmsTemplate(factory);
	    initialized = true;
	} catch (Exception e) {
	    log.error("Errore nell'inizializzazione del servizio di auditing [{}]: {}", auditServiceUrl, e.getMessage());
	}
    }

    private ConnectionFactory createConnectionFactory(String auditServiceUrl) {

	if (log.isDebugEnabled()) {
	    log.debug("createConnectionFactory: sto creando la connessione al servizio di auditing all'indirizzo: {}", auditServiceUrl);
	}
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	connectionFactory.getPrefetchPolicy().setAll(1);
	connectionFactory.setBrokerURL(auditServiceUrl);
	connectionFactory.setDispatchAsync(true);
	connectionFactory.setUseAsyncSend(true);
	connectionFactory.setSendTimeout(1000);
	return connectionFactory;
    }

    private void createJmsTemplate(ConnectionFactory connectionFactory) {

	if (log.isDebugEnabled()) {
	    log.debug("createJmsTemplate: Sto creando il template JMS");
	}
	this.jmsProducerTemplate = new JmsTemplate();
	jmsProducerTemplate.setConnectionFactory(connectionFactory);
	jmsProducerTemplate.setDefaultDestinationName(JMS_DEFAULT_DESTINATION_QUEUE);
	jmsProducerTemplate.setDeliveryPersistent(false);
	jmsProducerTemplate.setReceiveTimeout(1000);
    }

    public void setJmsProducerTemplate(JmsTemplate jmsProducerTemplate) {

	this.jmsProducerTemplate = jmsProducerTemplate;
    }

    public void sendMessage(final AuditMessage auditMessage) throws JMSException {

	if (this.initialized == false) {
	    initialize();
	}
	if (this.initialized) {
	    if (log.isDebugEnabled()) {
		log.debug("Sending message: {}" + auditMessage.toString());
	    }
	    jmsProducerTemplate.send(new MessageCreator() {

		public Message createMessage(Session session) throws JMSException {

		    Message message = session.createObjectMessage(auditMessage);
		    return (Message) message;
		}
	    });
	}
    }

    public boolean isInitialized() {

	return initialized;
    }
}
