package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.clientnotify;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * it.gruppoinit.pal.clientnotify package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NotificaPagamentoDebitoResponse_QNAME = new QName("http://schemi.informatica.maggioli.it/ws/pagopa/Notify",
	    "NotificaPagamentoDebitoResponse");
    private final static QName _NotificaPagamentoDebitoRequest_QNAME = new QName("http://schemi.informatica.maggioli.it/ws/pagopa/Notify",
	    "NotificaPagamentoDebitoRequest");
    private final static QName _NotificaStatoDebitoRequest_QNAME = new QName("http://schemi.informatica.maggioli.it/ws/pagopa/Notify",
	    "NotificaStatoDebitoRequest");
    private final static QName _NotificaStatoDebitoResponse_QNAME = new QName("http://schemi.informatica.maggioli.it/ws/pagopa/Notify",
	    "NotificaStatoDebitoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * it.gruppoinit.pal.clientnotify
     * 
     */
    public ObjectFactory() {

    }

    /**
     * Create an instance of {@link RispostaStandard }
     * 
     */
    public RispostaStandard createRispostaStandard() {

	return new RispostaStandard();
    }

    /**
     * Create an instance of {@link RispostaStandard.Messaggi }
     * 
     */
    public RispostaStandard.Messaggi createRispostaStandardMessaggi() {

	return new RispostaStandard.Messaggi();
    }

    /**
     * Create an instance of {@link RichiestaStandard }
     * 
     */
    public RichiestaStandard createRichiestaStandard() {

	return new RichiestaStandard();
    }

    /**
     * Create an instance of {@link RispostaStandard.Messaggi.Messaggio }
     * 
     */
    public RispostaStandard.Messaggi.Messaggio createRispostaStandardMessaggiMessaggio() {

	return new RispostaStandard.Messaggi.Messaggio();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaStandard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemi.informatica.maggioli.it/ws/pagopa/Notify", name = "NotificaPagamentoDebitoResponse")
    public JAXBElement<RispostaStandard> createNotificaPagamentoDebitoResponse(RispostaStandard value) {

	return new JAXBElement<RispostaStandard>(_NotificaPagamentoDebitoResponse_QNAME, RispostaStandard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaStandard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemi.informatica.maggioli.it/ws/pagopa/Notify", name = "NotificaPagamentoDebitoRequest")
    public JAXBElement<RichiestaStandard> createNotificaPagamentoDebitoRequest(RichiestaStandard value) {

	return new JAXBElement<RichiestaStandard>(_NotificaPagamentoDebitoRequest_QNAME, RichiestaStandard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaStandard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemi.informatica.maggioli.it/ws/pagopa/Notify", name = "NotificaStatoDebitoRequest")
    public JAXBElement<RichiestaStandard> createNotificaStatoDebitoRequest(RichiestaStandard value) {

	return new JAXBElement<RichiestaStandard>(_NotificaStatoDebitoRequest_QNAME, RichiestaStandard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaStandard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemi.informatica.maggioli.it/ws/pagopa/Notify", name = "NotificaStatoDebitoResponse")
    public JAXBElement<RispostaStandard> createNotificaStatoDebitoResponse(RispostaStandard value) {

	return new JAXBElement<RispostaStandard>(_NotificaStatoDebitoResponse_QNAME, RispostaStandard.class, null, value);
    }
}
