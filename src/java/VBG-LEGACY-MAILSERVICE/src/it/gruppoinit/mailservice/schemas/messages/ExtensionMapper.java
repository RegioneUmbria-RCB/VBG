/**
 * ExtensionMapper.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:23:23 CEST)
 */
package it.gruppoinit.mailservice.schemas.messages;

/**
 * ExtensionMapper class
 */
@SuppressWarnings({ "unchecked", "unused" })
public class ExtensionMapper {

    public static java.lang.Object getTypeObject(java.lang.String namespaceURI, java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
	    throws java.lang.Exception {

	if ("http://gruppoinit.it/mailService/schemas/messages".equals(namespaceURI) && "AttachmentType".equals(typeName)) {
	    return it.gruppoinit.mailservice.schemas.messages.AttachmentType.Factory.parse(reader);
	}
	if ("http://gruppoinit.it/mailService/schemas/messages".equals(namespaceURI) && "AttachmentsType".equals(typeName)) {
	    return it.gruppoinit.mailservice.schemas.messages.AttachmentsType.Factory.parse(reader);
	}
	if ("http://gruppoinit.it/mailService/schemas/messages".equals(namespaceURI) && "MailMessageType".equals(typeName)) {
	    return it.gruppoinit.mailservice.schemas.messages.MailMessageType.Factory.parse(reader);
	}
	throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
    }
}
