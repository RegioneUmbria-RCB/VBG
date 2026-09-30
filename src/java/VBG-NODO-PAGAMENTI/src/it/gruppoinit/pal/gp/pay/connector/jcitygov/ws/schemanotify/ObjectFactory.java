//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.17 alle 10:12:21 AM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {
    
    public final static String NAME_SPACE_V_1_0 = "http://schemi.informatica.maggioli.it/operations/jcgpagopa/notify/1_0";

    private final static QName _RispostaNotificaPagamentoDebito_QNAME = new QName(NAME_SPACE_V_1_0, "RispostaNotificaPagamentoDebito");
    private final static QName _RispostaNotificaCaricamentoDebito_QNAME = new QName(NAME_SPACE_V_1_0, "RispostaNotificaCaricamentoDebito");
    private final static QName _RichiestaNotificaCaricamentoDebito_QNAME = new QName(NAME_SPACE_V_1_0, "RichiestaNotificaCaricamentoDebito");
    private final static QName _RichiestaNotificaPagamentoDebito_QNAME = new QName(NAME_SPACE_V_1_0, "RichiestaNotificaPagamentoDebito");
    private final static QName _RichiestaNotificaRendicontazionePagamento_QNAME = new QName(NAME_SPACE_V_1_0,
	    "RichiestaNotificaRendicontazionePagamento");
    private final static QName _RispostaNotificaRendicontazionePagamento_QNAME = new QName(NAME_SPACE_V_1_0,
	    "RispostaNotificaRendicontazionePagamento");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RichiestaNotificaCaricamentoDebito }
     * 
     */
    public RichiestaNotificaCaricamentoDebito createRichiestaNotificaCaricamentoDebito() {
        return new RichiestaNotificaCaricamentoDebito();
    }

    /**
     * Create an instance of {@link RichiestaNotificaPagamentoDebito }
     * 
     */
    public RichiestaNotificaPagamentoDebito createRichiestaNotificaPagamentoDebito() {
        return new RichiestaNotificaPagamentoDebito();
    }

    /**
     * Create an instance of {@link RichiestaNotificaRendicontazionePagamento }
     * 
     */
    public RichiestaNotificaRendicontazionePagamento createRichiestaNotificaRendicontazionePagamento() {
        return new RichiestaNotificaRendicontazionePagamento();
    }

    /**
     * Create an instance of {@link RispostaNotificaRendicontazionePagamento }
     * 
     */
    public RispostaNotificaRendicontazionePagamento createRispostaNotificaRendicontazionePagamento() {
        return new RispostaNotificaRendicontazionePagamento();
    }

    /**
     * Create an instance of {@link RispostaNotificaPagamentoDebito }
     * 
     */
    public RispostaNotificaPagamentoDebito createRispostaNotificaPagamentoDebito() {
        return new RispostaNotificaPagamentoDebito();
    }

    /**
     * Create an instance of {@link RispostaNotificaCaricamentoDebito }
     * 
     */
    public RispostaNotificaCaricamentoDebito createRispostaNotificaCaricamentoDebito() {
        return new RispostaNotificaCaricamentoDebito();
    }

    /**
     * Create an instance of {@link CtEsito }
     * 
     */
    public CtEsito createCtEsito() {
        return new CtEsito();
    }

    /**
     * Create an instance of {@link CtEsitoDebitoCaricato }
     * 
     */
    public CtEsitoDebitoCaricato createCtEsitoDebitoCaricato() {
        return new CtEsitoDebitoCaricato();
    }

    /**
     * Create an instance of {@link CtChiaviDebito }
     * 
     */
    public CtChiaviDebito createCtChiaviDebito() {
        return new CtChiaviDebito();
    }

    /**
     * Create an instance of {@link RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati }
     * 
     */
    public RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati createRichiestaNotificaCaricamentoDebitoEsitiDebitiCaricati() {
        return new RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaNotificaPagamentoDebito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = NAME_SPACE_V_1_0, name = "RispostaNotificaPagamentoDebito")
    public JAXBElement<RispostaNotificaPagamentoDebito> createRispostaNotificaPagamentoDebito(RispostaNotificaPagamentoDebito value) {
        return new JAXBElement<RispostaNotificaPagamentoDebito>(_RispostaNotificaPagamentoDebito_QNAME, RispostaNotificaPagamentoDebito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaNotificaCaricamentoDebito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = NAME_SPACE_V_1_0, name = "RispostaNotificaCaricamentoDebito")
    public JAXBElement<RispostaNotificaCaricamentoDebito> createRispostaNotificaCaricamentoDebito(RispostaNotificaCaricamentoDebito value) {
        return new JAXBElement<RispostaNotificaCaricamentoDebito>(_RispostaNotificaCaricamentoDebito_QNAME, RispostaNotificaCaricamentoDebito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaNotificaCaricamentoDebito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace =NAME_SPACE_V_1_0, name = "RichiestaNotificaCaricamentoDebito")
    public JAXBElement<RichiestaNotificaCaricamentoDebito> createRichiestaNotificaCaricamentoDebito(RichiestaNotificaCaricamentoDebito value) {
        return new JAXBElement<RichiestaNotificaCaricamentoDebito>(_RichiestaNotificaCaricamentoDebito_QNAME, RichiestaNotificaCaricamentoDebito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaNotificaPagamentoDebito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = NAME_SPACE_V_1_0, name = "RichiestaNotificaPagamentoDebito")
    public JAXBElement<RichiestaNotificaPagamentoDebito> createRichiestaNotificaPagamentoDebito(RichiestaNotificaPagamentoDebito value) {
        return new JAXBElement<RichiestaNotificaPagamentoDebito>(_RichiestaNotificaPagamentoDebito_QNAME, RichiestaNotificaPagamentoDebito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaNotificaRendicontazionePagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = NAME_SPACE_V_1_0, name = "RichiestaNotificaRendicontazionePagamento")
    public JAXBElement<RichiestaNotificaRendicontazionePagamento> createRichiestaNotificaRendicontazionePagamento(RichiestaNotificaRendicontazionePagamento value) {
        return new JAXBElement<RichiestaNotificaRendicontazionePagamento>(_RichiestaNotificaRendicontazionePagamento_QNAME, RichiestaNotificaRendicontazionePagamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaNotificaRendicontazionePagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = NAME_SPACE_V_1_0, name = "RispostaNotificaRendicontazionePagamento")
    public JAXBElement<RispostaNotificaRendicontazionePagamento> createRispostaNotificaRendicontazionePagamento(RispostaNotificaRendicontazionePagamento value) {
        return new JAXBElement<RispostaNotificaRendicontazionePagamento>(_RispostaNotificaRendicontazionePagamento_QNAME, RispostaNotificaRendicontazionePagamento.class, null, value);
    }

}
