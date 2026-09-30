package it.gruppoinit.sigepro.schemas.messages.mailconfig;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * it.gruppoinit.sigepro.schemas.messages.mailconfig package.
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
     * it.gruppoinit.sigepro.schemas.messages.mailconfig
     * 
     */
    public ObjectFactory() {

    }

    /**
     * Create an instance of {@link MailConfigRequest }
     * 
     */
    public MailConfigRequest createMailConfigRequest() {

	return new MailConfigRequest();
    }

    /**
     * Create an instance of {@link MailConfigResponse }
     * 
     */
    public MailConfigResponse createMailConfigResponse() {

	return new MailConfigResponse();
    }

    /**
     * Create an instance of {@link MailConfigResponse2 }
     * 
     */
    public MailConfigResponse2 createMailConfigResponse2() {

	return new MailConfigResponse2();
    }

    /**
     * Create an instance of {@link MailConfigRequest2 }
     * 
     */
    public MailConfigRequest2 createMailConfigRequest2() {

	return new MailConfigRequest2();
    }
}
