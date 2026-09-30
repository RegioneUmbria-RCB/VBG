package it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo;

import it.init.sigepro.rte.types.DettaglioPraticaType;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo
     * 
     */
    public ObjectFactory() {

    }

    /**
     * Create an instance of {@link MailtipoFrontendRequest }
     * 
     */
    public MailtipoFrontendRequest createMailtipoFrontendRequest() {

	return new MailtipoFrontendRequest();
    }

    /**
     * Create an instance of {@link DettaglioPraticaType }
     * 
     */
    public DettaglioPraticaType createDettaglioPraticaType() {

	return new DettaglioPraticaType();
    }

    /**
     * Create an instance of {@link MailtipoResponse }
     * 
     */
    public MailtipoResponse createMailtipoResponse() {

	return new MailtipoResponse();
    }

    /**
     * Create an instance of {@link MailtipoRequest }
     * 
     */
    public MailtipoRequest createMailtipoRequest() {

	return new MailtipoRequest();
    }
}
