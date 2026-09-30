/**
 * MailMessageType.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:23:23 CEST)
 */
package it.gruppoinit.mailservice.schemas.messages;

/**
 * MailMessageType bean class
 */
@SuppressWarnings({ "unchecked", "unused" })
public class MailMessageType implements org.apache.axis2.databinding.ADBBean {

    /* This type was generated from the piece of schema that had
            name = MailMessageType
            Namespace URI = http://gruppoinit.it/mailService/schemas/messages
            Namespace Prefix = ns3
            */
    /**
     * field for InviaComeHtml
     */
    protected boolean localInviaComeHtml;
    /*  This tracker boolean wil be used to detect whether the user called the set method
    *   for this attribute. It will be used to determine whether to include this field
    *   in the serialized XML
    */
    protected boolean localInviaComeHtmlTracker = false;

    public boolean isInviaComeHtmlSpecified() {

	return localInviaComeHtmlTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return boolean
     */
    public boolean getInviaComeHtml() {

	return localInviaComeHtml;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            InviaComeHtml
     */
    public void setInviaComeHtml(boolean param) {

	// setting primitive attribute tracker to true
	localInviaComeHtmlTracker = true;
	this.localInviaComeHtml = param;
    }

    /**
     * field for Mittente
     */
    protected java.lang.String localMittente;
    /*  This tracker boolean wil be used to detect whether the user called the set method
    *   for this attribute. It will be used to determine whether to include this field
    *   in the serialized XML
    */
    protected boolean localMittenteTracker = false;

    public boolean isMittenteSpecified() {

	return localMittenteTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getMittente() {

	return localMittente;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            Mittente
     */
    public void setMittente(java.lang.String param) {

	localMittenteTracker = param != null;
	this.localMittente = param;
    }

    /**
     * field for CorpoMail
     */
    protected java.lang.String localCorpoMail;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getCorpoMail() {

	return localCorpoMail;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            CorpoMail
     */
    public void setCorpoMail(java.lang.String param) {

	this.localCorpoMail = param;
    }

    /**
     * field for Oggetto
     */
    protected java.lang.String localOggetto;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getOggetto() {

	return localOggetto;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            Oggetto
     */
    public void setOggetto(java.lang.String param) {

	this.localOggetto = param;
    }

    /**
     * field for Destinatari
     */
    protected java.lang.String localDestinatari;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getDestinatari() {

	return localDestinatari;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            Destinatari
     */
    public void setDestinatari(java.lang.String param) {

	this.localDestinatari = param;
    }

    /**
     * field for DestinatariInCopia
     */
    protected java.lang.String localDestinatariInCopia;
    /*  This tracker boolean wil be used to detect whether the user called the set method
    *   for this attribute. It will be used to determine whether to include this field
    *   in the serialized XML
    */
    protected boolean localDestinatariInCopiaTracker = false;

    public boolean isDestinatariInCopiaSpecified() {

	return localDestinatariInCopiaTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getDestinatariInCopia() {

	return localDestinatariInCopia;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            DestinatariInCopia
     */
    public void setDestinatariInCopia(java.lang.String param) {

	localDestinatariInCopiaTracker = param != null;
	this.localDestinatariInCopia = param;
    }

    /**
     * field for DestinatariInCopiaNascosta
     */
    protected java.lang.String localDestinatariInCopiaNascosta;
    /*  This tracker boolean wil be used to detect whether the user called the set method
    *   for this attribute. It will be used to determine whether to include this field
    *   in the serialized XML
    */
    protected boolean localDestinatariInCopiaNascostaTracker = false;

    public boolean isDestinatariInCopiaNascostaSpecified() {

	return localDestinatariInCopiaNascostaTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getDestinatariInCopiaNascosta() {

	return localDestinatariInCopiaNascosta;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            DestinatariInCopiaNascosta
     */
    public void setDestinatariInCopiaNascosta(java.lang.String param) {

	localDestinatariInCopiaNascostaTracker = param != null;
	this.localDestinatariInCopiaNascosta = param;
    }

    /**
     * field for MessageID
     */
    protected java.lang.String localMessageID;
    /*  This tracker boolean wil be used to detect whether the user called the set method
    *   for this attribute. It will be used to determine whether to include this field
    *   in the serialized XML
    */
    protected boolean localMessageIDTracker = false;

    public boolean isMessageIDSpecified() {

	return localMessageIDTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getMessageID() {

	return localMessageID;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            MessageID
     */
    public void setMessageID(java.lang.String param) {

	localMessageIDTracker = param != null;
	this.localMessageID = param;
    }

    /**
     * field for Attachments
     */
    protected it.gruppoinit.mailservice.schemas.messages.AttachmentsType localAttachments;
    /*  This tracker boolean wil be used to detect whether the user called the set method
    *   for this attribute. It will be used to determine whether to include this field
    *   in the serialized XML
    */
    protected boolean localAttachmentsTracker = false;

    public boolean isAttachmentsSpecified() {

	return localAttachmentsTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return it.gruppoinit.mailservice.schemas.messages.AttachmentsType
     */
    public it.gruppoinit.mailservice.schemas.messages.AttachmentsType getAttachments() {

	return localAttachments;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *            Attachments
     */
    public void setAttachments(it.gruppoinit.mailservice.schemas.messages.AttachmentsType param) {

	localAttachmentsTracker = param != null;
	this.localAttachments = param;
    }

    /**
     * 
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory)
	    throws org.apache.axis2.databinding.ADBException {

	org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this, parentQName);
	return factory.createOMElement(dataSource, parentQName);
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
		writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MailMessageType", xmlWriter);
	    } else {
		writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MailMessageType", xmlWriter);
	    }
	}
	if (localInviaComeHtmlTracker) {
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "inviaComeHtml", xmlWriter);
	    if (false) {
		throw new org.apache.axis2.databinding.ADBException("inviaComeHtml cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInviaComeHtml));
	    }
	    xmlWriter.writeEndElement();
	}
	if (localMittenteTracker) {
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "mittente", xmlWriter);
	    if (localMittente == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("mittente cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localMittente);
	    }
	    xmlWriter.writeEndElement();
	}
	namespace = "http://gruppoinit.it/mailService/schemas/messages";
	writeStartElement(null, namespace, "corpoMail", xmlWriter);
	if (localCorpoMail == null) {
	    // write the nil attribute
	    throw new org.apache.axis2.databinding.ADBException("corpoMail cannot be null!!");
	} else {
	    xmlWriter.writeCharacters(localCorpoMail);
	}
	xmlWriter.writeEndElement();
	namespace = "http://gruppoinit.it/mailService/schemas/messages";
	writeStartElement(null, namespace, "oggetto", xmlWriter);
	if (localOggetto == null) {
	    // write the nil attribute
	    throw new org.apache.axis2.databinding.ADBException("oggetto cannot be null!!");
	} else {
	    xmlWriter.writeCharacters(localOggetto);
	}
	xmlWriter.writeEndElement();
	namespace = "http://gruppoinit.it/mailService/schemas/messages";
	writeStartElement(null, namespace, "destinatari", xmlWriter);
	if (localDestinatari == null) {
	    // write the nil attribute
	    throw new org.apache.axis2.databinding.ADBException("destinatari cannot be null!!");
	} else {
	    xmlWriter.writeCharacters(localDestinatari);
	}
	xmlWriter.writeEndElement();
	if (localDestinatariInCopiaTracker) {
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "destinatariInCopia", xmlWriter);
	    if (localDestinatariInCopia == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("destinatariInCopia cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localDestinatariInCopia);
	    }
	    xmlWriter.writeEndElement();
	}
	if (localDestinatariInCopiaNascostaTracker) {
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "destinatariInCopiaNascosta", xmlWriter);
	    if (localDestinatariInCopiaNascosta == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("destinatariInCopiaNascosta cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localDestinatariInCopiaNascosta);
	    }
	    xmlWriter.writeEndElement();
	}
	if (localMessageIDTracker) {
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "messageID", xmlWriter);
	    if (localMessageID == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("messageID cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localMessageID);
	    }
	    xmlWriter.writeEndElement();
	}
	if (localAttachmentsTracker) {
	    if (localAttachments == null) {
		throw new org.apache.axis2.databinding.ADBException("attachments cannot be null!!");
	    }
	    localAttachments.serialize(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachments"), xmlWriter);
	}
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
	if (localInviaComeHtmlTracker) {
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "inviaComeHtml"));
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInviaComeHtml));
	}
	if (localMittenteTracker) {
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mittente"));
	    if (localMittente != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMittente));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("mittente cannot be null!!");
	    }
	}
	elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "corpoMail"));
	if (localCorpoMail != null) {
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCorpoMail));
	} else {
	    throw new org.apache.axis2.databinding.ADBException("corpoMail cannot be null!!");
	}
	elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "oggetto"));
	if (localOggetto != null) {
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOggetto));
	} else {
	    throw new org.apache.axis2.databinding.ADBException("oggetto cannot be null!!");
	}
	elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "destinatari"));
	if (localDestinatari != null) {
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDestinatari));
	} else {
	    throw new org.apache.axis2.databinding.ADBException("destinatari cannot be null!!");
	}
	if (localDestinatariInCopiaTracker) {
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "destinatariInCopia"));
	    if (localDestinatariInCopia != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDestinatariInCopia));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("destinatariInCopia cannot be null!!");
	    }
	}
	if (localDestinatariInCopiaNascostaTracker) {
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "destinatariInCopiaNascosta"));
	    if (localDestinatariInCopiaNascosta != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDestinatariInCopiaNascosta));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("destinatariInCopiaNascosta cannot be null!!");
	    }
	}
	if (localMessageIDTracker) {
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "messageID"));
	    if (localMessageID != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMessageID));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("messageID cannot be null!!");
	    }
	}
	if (localAttachmentsTracker) {
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachments"));
	    if (localAttachments == null) {
		throw new org.apache.axis2.databinding.ADBException("attachments cannot be null!!");
	    }
	    elementList.add(localAttachments);
	}
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
	public static MailMessageType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

	    MailMessageType object = new MailMessageType();
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
			if (!"MailMessageType".equals(type)) {
			    //find namespace for the prefix
			    java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
			    return (MailMessageType) it.gruppoinit.mailservice.schemas.messages.ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "inviaComeHtml").equals(reader
				.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setInviaComeHtml(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mittente").equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setMittente(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "corpoMail").equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setCorpoMail(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		    // A start element we are not expecting indicates an invalid parameter was passed
		    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "oggetto").equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setOggetto(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		    // A start element we are not expecting indicates an invalid parameter was passed
		    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "destinatari").equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setDestinatari(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		    // A start element we are not expecting indicates an invalid parameter was passed
		    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "destinatariInCopia").equals(reader
				.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setDestinatariInCopia(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "destinatariInCopiaNascosta")
				.equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setDestinatariInCopiaNascosta(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "messageID").equals(reader.getName())) {
		    java.lang.String content = reader.getElementText();
		    object.setMessageID(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
		    reader.next();
		} // End of if for expected property start element
		else {
		}
		while (!reader.isStartElement() && !reader.isEndElement())
		    reader.next();
		if (reader.isStartElement()
			&& new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachments").equals(reader.getName())) {
		    object.setAttachments(it.gruppoinit.mailservice.schemas.messages.AttachmentsType.Factory.parse(reader));
		    reader.next();
		} // End of if for expected property start element
		else {
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
