/**
 * MessageRequest.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:23:23 CEST)
 */
package it.gruppoinit.mailservice.schemas.messages;

/**
 * MessageRequest bean class
 */
@SuppressWarnings({ "unchecked", "unused" })
public class MessageRequest implements org.apache.axis2.databinding.ADBBean {

    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages",
	    "MessageRequest", "ns3");
    /**
     * field for Token
     */
    protected java.lang.String localToken;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getToken() {

	return localToken;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            Token
     */
    public void setToken(java.lang.String param) {

	this.localToken = param;
    }

    /**
     * field for Software
     */
    protected java.lang.String localSoftware;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getSoftware() {

	return localSoftware;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            Software
     */
    public void setSoftware(java.lang.String param) {

	this.localSoftware = param;
    }

    /**
     * field for Codicemovimento
     */
    protected java.lang.String localCodicemovimento;
    /*  This tracker boolean wil be used to detect whether the user called the set method
    *   for this attribute. It will be used to determine whether to include this field
    *   in the serialized XML
    */
    protected boolean localCodicemovimentoTracker = false;

    public boolean isCodicemovimentoSpecified() {

	return localCodicemovimentoTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getCodicemovimento() {

	return localCodicemovimento;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            Codicemovimento
     */
    public void setCodicemovimento(java.lang.String param) {

	localCodicemovimentoTracker = param != null;
	this.localCodicemovimento = param;
    }

    /**
     * field for MailMessage
     */
    protected it.gruppoinit.mailservice.schemas.messages.MailMessageType localMailMessage;

    /**
     * Auto generated getter method
     * 
     * @return it.gruppoinit.mailservice.schemas.messages.MailMessageType
     */
    public it.gruppoinit.mailservice.schemas.messages.MailMessageType getMailMessage() {

	return localMailMessage;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            MailMessage
     */
    public void setMailMessage(it.gruppoinit.mailservice.schemas.messages.MailMessageType param) {

	this.localMailMessage = param;
    }

    /**
     * 
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory)
	    throws org.apache.axis2.databinding.ADBException {

	org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME);
	return factory.createOMElement(dataSource, MY_QNAME);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter)
	    throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

	serialize(parentQName, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
	    throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

	java.lang.String prefix = null;
	java.lang.String namespace = null;
	prefix = parentQName.getPrefix();
	namespace = parentQName.getNamespaceURI();
	writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
	if (serializeType) {
	    java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://gruppoinit.it/mailService/schemas/messages");
	    if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MessageRequest", xmlWriter);
	    } else {
		writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MessageRequest", xmlWriter);
	    }
	}
	namespace = "http://gruppoinit.it/mailService/schemas/messages";
	writeStartElement(null, namespace, "token", xmlWriter);
	if (localToken == null) {
	    // write the nil attribute
	    throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	} else {
	    xmlWriter.writeCharacters(localToken);
	}
	xmlWriter.writeEndElement();
	namespace = "http://gruppoinit.it/mailService/schemas/messages";
	writeStartElement(null, namespace, "software", xmlWriter);
	if (localSoftware == null) {
	    // write the nil attribute
	    throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	} else {
	    xmlWriter.writeCharacters(localSoftware);
	}
	xmlWriter.writeEndElement();
	if (localCodicemovimentoTracker) {
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "codicemovimento", xmlWriter);
	    if (localCodicemovimento == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("codicemovimento cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localCodicemovimento);
	    }
	    xmlWriter.writeEndElement();
	}
	if (localMailMessage == null) {
	    throw new org.apache.axis2.databinding.ADBException("mailMessage cannot be null!!");
	}
	localMailMessage.serialize(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mailMessage"), xmlWriter);
	xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {

	if (namespace.equals("http://gruppoinit.it/mailService/schemas/messages")) {
	    return "ns3";
	}
	return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Utility method to write an element start tag.
     */
    private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
	    javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

	java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
	if (writerPrefix != null) {
	    xmlWriter.writeStartElement(namespace, localPart);
	} else {
	    if (namespace.length() == 0) {
		prefix = "";
	    } else if (prefix == null) {
		prefix = generatePrefix(namespace);
	    }
	    xmlWriter.writeStartElement(prefix, localPart, namespace);
	    xmlWriter.writeNamespace(prefix, namespace);
	    xmlWriter.setPrefix(prefix, namespace);
	}
    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix, java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
	    javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

	if (xmlWriter.getPrefix(namespace) == null) {
	    xmlWriter.writeNamespace(prefix, namespace);
	    xmlWriter.setPrefix(prefix, namespace);
	}
	xmlWriter.writeAttribute(namespace, attName, attValue);
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
	    javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

	if (namespace.equals("")) {
	    xmlWriter.writeAttribute(attName, attValue);
	} else {
	    registerPrefix(xmlWriter, namespace);
	    xmlWriter.writeAttribute(namespace, attName, attValue);
	}
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName, javax.xml.namespace.QName qname,
	    javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

	java.lang.String attributeNamespace = qname.getNamespaceURI();
	java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
	if (attributePrefix == null) {
	    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
	}
	java.lang.String attributeValue;
	if (attributePrefix.trim().length() > 0) {
	    attributeValue = attributePrefix + ":" + qname.getLocalPart();
	} else {
	    attributeValue = qname.getLocalPart();
	}
	if (namespace.equals("")) {
	    xmlWriter.writeAttribute(attName, attributeValue);
	} else {
	    registerPrefix(xmlWriter, namespace);
	    xmlWriter.writeAttribute(namespace, attName, attributeValue);
	}
    }

    /**
     * method to handle Qnames
     */
    private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

	java.lang.String namespaceURI = qname.getNamespaceURI();
	if (namespaceURI != null) {
	    java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
	    if (prefix == null) {
		prefix = generatePrefix(namespaceURI);
		xmlWriter.writeNamespace(prefix, namespaceURI);
		xmlWriter.setPrefix(prefix, namespaceURI);
	    }
	    if (prefix.trim().length() > 0) {
		xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
	    } else {
		// i.e this is the default namespace
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
	    }
	} else {
	    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
	}
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter)
	    throws javax.xml.stream.XMLStreamException {

	if (qnames != null) {
	    // we have to store this data until last moment since it is not possible to write any
	    // namespace data after writing the charactor data
	    java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
	    java.lang.String namespaceURI = null;
	    java.lang.String prefix = null;
	    for (int i = 0; i < qnames.length; i++) {
		if (i > 0) {
		    stringToWrite.append(" ");
		}
		namespaceURI = qnames[i].getNamespaceURI();
		if (namespaceURI != null) {
		    prefix = xmlWriter.getPrefix(namespaceURI);
		    if ((prefix == null) || (prefix.length() == 0)) {
			prefix = generatePrefix(namespaceURI);
			xmlWriter.writeNamespace(prefix, namespaceURI);
			xmlWriter.setPrefix(prefix, namespaceURI);
		    }
		    if (prefix.trim().length() > 0) {
			stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
		    } else {
			stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
		    }
		} else {
		    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
		}
	    }
	    xmlWriter.writeCharacters(stringToWrite.toString());
	}
    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
	    throws javax.xml.stream.XMLStreamException {

	java.lang.String prefix = xmlWriter.getPrefix(namespace);
	if (prefix == null) {
	    prefix = generatePrefix(namespace);
	    javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
	    while (true) {
		java.lang.String uri = nsContext.getNamespaceURI(prefix);
		if (uri == null || uri.length() == 0) {
		    break;
		}
		prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
	    }
	    xmlWriter.writeNamespace(prefix, namespace);
	    xmlWriter.setPrefix(prefix, namespace);
	}
	return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     * 
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException {

	java.util.ArrayList elementList = new java.util.ArrayList();
	java.util.ArrayList attribList = new java.util.ArrayList();
	elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "token"));
	if (localToken != null) {
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	} else {
	    throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	}
	elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "software"));
	if (localSoftware != null) {
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSoftware));
	} else {
	    throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	}
	if (localCodicemovimentoTracker) {
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "codicemovimento"));
	    if (localCodicemovimento != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodicemovimento));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("codicemovimento cannot be null!!");
	    }
	}
	elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mailMessage"));
	if (localMailMessage == null) {
	    throw new org.apache.axis2.databinding.ADBException("mailMessage cannot be null!!");
	}
	elementList.add(localMailMessage);
	return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {

	/**
	 * static method to create the object Precondition: If this object is an element, the current or next start
	 * element starts this object and any intervening reader events are ignorable If this object is not an element,
	 * it is a complex type and the reader is at the event just after the outer start element Postcondition: If this
	 * object is an element, the reader is positioned at its end element If this object is a complex type, the
	 * reader is positioned at the end element of its outer element
	 */
	public static MessageRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

	    MessageRequest object = new MessageRequest();
	    int event;
	    java.lang.String nillableValue = null;
	    java.lang.String prefix = "";
	    java.lang.String namespaceuri = "";
	    try {
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
		    java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
		    if (fullTypeName != null) {
			java.lang.String nsPrefix = null;
			if (fullTypeName.indexOf(":") > -1) {
			    nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
			}
			nsPrefix = nsPrefix == null ? "" : nsPrefix;
			java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
			if (!"MessageRequest".equals(type)) {
			    //find namespace for the prefix
			    java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
			    return (MessageRequest) it.gruppoinit.mailservice.schemas.messages.ExtensionMapper.getTypeObject(nsUri, type, reader);
			}
		    }
		}
		// Note all attributes that were handled. Used to differ normal attributes
		// from anyAttributes.
		java.util.Vector handledAttributes = new java.util.Vector();
		reader.next();
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "token").equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setToken(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		    // A start element we are not expecting indicates an invalid parameter was passed
		    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "software").equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setSoftware(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		    // A start element we are not expecting indicates an invalid parameter was passed
		    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "codicemovimento").equals(reader
				.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setCodicemovimento(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mailMessage").equals(reader.getName())) {
		    object.setMailMessage(it.gruppoinit.mailservice.schemas.messages.MailMessageType.Factory.parse(reader));
		    reader.next();
		} // End of if for expected property start element
		else {
		    // A start element we are not expecting indicates an invalid parameter was passed
		    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement())
		    // A start element we are not expecting indicates a trailing invalid property
		    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
	    } catch (javax.xml.stream.XMLStreamException e) {
		throw new java.lang.Exception(e);
	    }
	    return object;
	}
    }//end of factory class
}
