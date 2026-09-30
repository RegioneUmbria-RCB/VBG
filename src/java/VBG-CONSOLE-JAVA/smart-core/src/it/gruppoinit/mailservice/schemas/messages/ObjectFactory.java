
package it.gruppoinit.mailservice.schemas.messages;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.mailservice.schemas.messages package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.mailservice.schemas.messages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MessageRequest }
     * 
     */
    public MessageRequest createMessageRequest() {
        return new MessageRequest();
    }

    /**
     * Create an instance of {@link MailMessageType }
     * 
     */
    public MailMessageType createMailMessageType() {
        return new MailMessageType();
    }

    /**
     * Create an instance of {@link MessageResponse }
     * 
     */
    public MessageResponse createMessageResponse() {
        return new MessageResponse();
    }

    /**
     * Create an instance of {@link AttachmentsType }
     * 
     */
    public AttachmentsType createAttachmentsType() {
        return new AttachmentsType();
    }

    /**
     * Create an instance of {@link AttachmentType }
     * 
     */
    public AttachmentType createAttachmentType() {
        return new AttachmentType();
    }

}
