/**
 * MailConfigServiceStub.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package it.gruppoinit.sigepro.definitions.mailconfig;

/*
 * MailConfigServiceStub java implementation
 */
public class MailConfigServiceStub extends org.apache.axis2.client.Stub {

    protected org.apache.axis2.description.AxisOperation[] _operations;
    //hashmaps to keep the fault mapping
    private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
    private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
    private java.util.HashMap faultMessageMap = new java.util.HashMap();
    private static int counter = 0;

    private static synchronized java.lang.String getUniqueSuffix() {

	// reset the counter if it is greater than 99999
	if (counter > 99999) {
	    counter = 0;
	}
	counter = counter + 1;
	return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
    }

    private void populateAxisService() throws org.apache.axis2.AxisFault {

	//creating the Service with a unique name
	_service = new org.apache.axis2.description.AxisService("MailConfigService" + getUniqueSuffix());
	addAnonymousOperations();
	//creating the operations
	org.apache.axis2.description.AxisOperation __operation;
	_operations = new org.apache.axis2.description.AxisOperation[2];
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/definitions/mailconfig", "mailConfig2"));
	_service.addOperation(__operation);
	_operations[0] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/definitions/mailconfig", "mailConfig"));
	_service.addOperation(__operation);
	_operations[1] = __operation;
    }

    //populates the faults
    private void populateFaults() {

    }

    /**
     * Constructor that takes in a configContext
     */
    public MailConfigServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint)
	    throws org.apache.axis2.AxisFault {

	this(configurationContext, targetEndpoint, false);
    }

    /**
     * Constructor that takes in a configContext and useseperate listner
     */
    public MailConfigServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint,
	    boolean useSeparateListener) throws org.apache.axis2.AxisFault {

	//To populate AxisService
	populateAxisService();
	populateFaults();
	_serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext, _service);
	_serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(targetEndpoint));
	_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
    }

    /**
     * Default Constructor
     */
    public MailConfigServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

	this(configurationContext, "http://localhost:8080/backend/services/mailconfig");
    }

    /**
     * Default Constructor
     */
    public MailConfigServiceStub() throws org.apache.axis2.AxisFault {

	this("http://localhost:8080/backend/services/mailconfig");
    }

    /**
     * Constructor taking the target endpoint
     */
    public MailConfigServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {

	this(null, targetEndpoint);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigepro.definitions.mailconfig.MailConfigService#mailConfig2
     * @param mailConfigRequest2
     */
    public it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2 mailConfig2(
	    it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2 mailConfigRequest2) throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
	    _operationClient.getOptions().setAction("mailConfig2");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), mailConfigRequest2,
		    optimizeContent(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/definitions/mailconfig", "mailConfig2")),
		    new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/definitions/mailconfig", "mailConfig2"));
	    //adding SOAP soap_headers
	    _serviceClient.addHeadersToEnvelope(env);
	    // set the message context with that soap envelope
	    _messageContext.setEnvelope(env);
	    // add the message contxt to the operation client
	    _operationClient.addMessageContext(_messageContext);
	    //execute the operation client
	    _operationClient.execute(true);
	    org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
		    .getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
	    org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
	    java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
		    it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "MailConfig2"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "MailConfig2"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "MailConfig2"));
			java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
			java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
			java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
			m.invoke(ex, new java.lang.Object[] { messageObject });
			throw new java.rmi.RemoteException(ex.getMessage(), ex);
		    } catch (java.lang.ClassCastException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.ClassNotFoundException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.NoSuchMethodException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.reflect.InvocationTargetException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.IllegalAccessException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.InstantiationException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    }
		} else {
		    throw f;
		}
	    } else {
		throw f;
	    }
	} finally {
	    if (_messageContext.getTransportOut() != null) {
		_messageContext.getTransportOut().getSender().cleanup(_messageContext);
	    }
	}
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigepro.definitions.mailconfig.MailConfigService#mailConfig
     * @param mailConfigRequest
     */
    public it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse mailConfig(
	    it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest mailConfigRequest) throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
	    _operationClient.getOptions().setAction("mailConfig");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), mailConfigRequest,
		    optimizeContent(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/definitions/mailconfig", "mailConfig")),
		    new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/definitions/mailconfig", "mailConfig"));
	    //adding SOAP soap_headers
	    _serviceClient.addHeadersToEnvelope(env);
	    // set the message context with that soap envelope
	    _messageContext.setEnvelope(env);
	    // add the message contxt to the operation client
	    _operationClient.addMessageContext(_messageContext);
	    //execute the operation client
	    _operationClient.execute(true);
	    org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
		    .getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
	    org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
	    java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
		    it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "MailConfig"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "MailConfig"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "MailConfig"));
			java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
			java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
			java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
			m.invoke(ex, new java.lang.Object[] { messageObject });
			throw new java.rmi.RemoteException(ex.getMessage(), ex);
		    } catch (java.lang.ClassCastException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.ClassNotFoundException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.NoSuchMethodException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.reflect.InvocationTargetException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.IllegalAccessException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    } catch (java.lang.InstantiationException e) {
			// we cannot intantiate the class - throw the original Axis fault
			throw f;
		    }
		} else {
		    throw f;
		}
	    } else {
		throw f;
	    }
	} finally {
	    if (_messageContext.getTransportOut() != null) {
		_messageContext.getTransportOut().getSender().cleanup(_messageContext);
	    }
	}
    }

    /**
     * A utility method that copies the namepaces from the SOAPEnvelope
     */
    private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env) {

	java.util.Map returnMap = new java.util.HashMap();
	java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
	while (namespaceIterator.hasNext()) {
	    org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
	    returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
	}
	return returnMap;
    }

    private javax.xml.namespace.QName[] opNameArray = null;

    private boolean optimizeContent(javax.xml.namespace.QName opName) {

	if (opNameArray == null) {
	    return false;
	}
	for (int i = 0; i < opNameArray.length; i++) {
	    if (opName.equals(opNameArray[i])) {
		return true;
	    }
	}
	return false;
    }

    //http://localhost:8080/backend/services/mailconfig
    public static class ActionType implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
		"http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "actionType", "ns1");
	/**
	 * field for ActionType
	 */
	protected java.lang.String localActionType;
	private static java.util.HashMap _table_ = new java.util.HashMap();

	// Constructor
	protected ActionType(java.lang.String value, boolean isRegisterValue) {

	    localActionType = value;
	    if (isRegisterValue) {
		_table_.put(localActionType, this);
	    }
	}

	public static final java.lang.String _READ = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("READ");
	public static final java.lang.String _SEND = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SEND");
	public static final ActionType READ = new ActionType(_READ, true);
	public static final ActionType SEND = new ActionType(_SEND, true);

	public java.lang.String getValue() {

	    return localActionType;
	}

	public boolean equals(java.lang.Object obj) {

	    return (obj == this);
	}

	public int hashCode() {

	    return toString().hashCode();
	}

	public java.lang.String toString() {

	    return localActionType.toString();
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

	    //We can safely assume an element has only one type associated with it
	    java.lang.String namespace = parentQName.getNamespaceURI();
	    java.lang.String _localName = parentQName.getLocalPart();
	    writeStartElement(null, namespace, _localName, xmlWriter);
	    // add the type details if this is used in a simple type
	    if (serializeType) {
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://gruppoinit.it/sigepro/schemas/messages/mailconfig");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":actionType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "actionType", xmlWriter);
		}
	    }
	    if (localActionType == null) {
		throw new org.apache.axis2.databinding.ADBException("actionType cannot be null !!");
	    } else {
		xmlWriter.writeCharacters(localActionType);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/sigepro/schemas/messages/mailconfig")) {
		return "ns1";
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
	private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
		throws javax.xml.stream.XMLStreamException {

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
			    stringToWrite.append(prefix).append(":")
				    .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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

	    //We can safely assume an element has only one type associated with it
	    return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(MY_QNAME, new java.lang.Object[] {
		    org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
		    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localActionType) }, null);
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    public static ActionType fromValue(java.lang.String value) throws java.lang.IllegalArgumentException {

		ActionType enumeration = (ActionType) _table_.get(value);
		if ((enumeration == null) && !((value == null) || (value.equals("")))) {
		    throw new java.lang.IllegalArgumentException();
		}
		return enumeration;
	    }

	    public static ActionType fromString(java.lang.String value, java.lang.String namespaceURI) throws java.lang.IllegalArgumentException {

		try {
		    return fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));
		} catch (java.lang.Exception e) {
		    throw new java.lang.IllegalArgumentException();
		}
	    }

	    public static ActionType fromString(javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content) {

		if (content.indexOf(":") > -1) {
		    java.lang.String prefix = content.substring(0, content.indexOf(":"));
		    java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
		    return ActionType.Factory.fromString(content, namespaceUri);
		} else {
		    return ActionType.Factory.fromString(content, "");
		}
	    }

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static ActionType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		ActionType object = null;
		// initialize a hash map to keep values
		java.util.Map attributeMap = new java.util.HashMap();
		java.util.List extraAttributeList = new java.util.ArrayList<org.apache.axiom.om.OMAttribute>();
		int event;
		java.lang.String nillableValue = null;
		java.lang.String prefix = "";
		java.lang.String namespaceuri = "";
		try {
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    // Note all attributes that were handled. Used to differ normal attributes
		    // from anyAttributes.
		    java.util.Vector handledAttributes = new java.util.Vector();
		    while (!reader.isEndElement()) {
			if (reader.isStartElement() || reader.hasText()) {
			    java.lang.String content = reader.getElementText();
			    if (content.indexOf(":") > 0) {
				// this seems to be a Qname so find the namespace and send
				prefix = content.substring(0, content.indexOf(":"));
				namespaceuri = reader.getNamespaceURI(prefix);
				object = ActionType.Factory.fromString(content, namespaceuri);
			    } else {
				// this seems to be not a qname send and empty namespace incase of it is
				// check is done in fromString method
				object = ActionType.Factory.fromString(content, "");
			    }
			} else {
			    reader.next();
			}
		    } // end of while loop
		} catch (javax.xml.stream.XMLStreamException e) {
		    throw new java.lang.Exception(e);
		}
		return object;
	    }
	}//end of factory class
    }

    public static class MailConfigRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
		"http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "MailConfigRequest", "ns1");
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
	 * field for Action
	 */
	protected ActionType localAction;

	/**
	 * Auto generated getter method
	 * 
	 * @return ActionType
	 */
	public ActionType getAction() {

	    return localAction;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Action
	 */
	public void setAction(ActionType param) {

	    this.localAction = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://gruppoinit.it/sigepro/schemas/messages/mailconfig");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MailConfigRequest", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MailConfigRequest", xmlWriter);
		}
	    }
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "token", xmlWriter);
	    if (localToken == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localToken);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "software", xmlWriter);
	    if (localSoftware == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localSoftware);
	    }
	    xmlWriter.writeEndElement();
	    if (localAction == null) {
		throw new org.apache.axis2.databinding.ADBException("action cannot be null!!");
	    }
	    localAction.serialize(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "action"), xmlWriter);
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/sigepro/schemas/messages/mailconfig")) {
		return "ns1";
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
	private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
		throws javax.xml.stream.XMLStreamException {

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
			    stringToWrite.append(prefix).append(":")
				    .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "software"));
	    if (localSoftware != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSoftware));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "action"));
	    if (localAction == null) {
		throw new org.apache.axis2.databinding.ADBException("action cannot be null!!");
	    }
	    elementList.add(localAction);
	    return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static MailConfigRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		MailConfigRequest object = new MailConfigRequest();
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
			    if (!"MailConfigRequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (MailConfigRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "token").equals(reader
				    .getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "software").equals(reader
				    .getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "action").equals(reader
				    .getName())) {
			object.setAction(ActionType.Factory.parse(reader));
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

    public static class ProtocolType implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
		"http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "protocolType", "ns1");
	/**
	 * field for ProtocolType
	 */
	protected java.lang.String localProtocolType;
	private static java.util.HashMap _table_ = new java.util.HashMap();

	// Constructor
	protected ProtocolType(java.lang.String value, boolean isRegisterValue) {

	    localProtocolType = value;
	    if (isRegisterValue) {
		_table_.put(localProtocolType, this);
	    }
	}

	public static final java.lang.String _SMTP = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SMTP");
	public static final java.lang.String _SMTP_SSL = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SMTP_SSL");
	public static final java.lang.String _SSMTP_SMTPS = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SSMTP_SMTPS");
	public static final java.lang.String _POP3 = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("POP3");
	public static final java.lang.String _IMAP = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("IMAP");
	public static final java.lang.String _SSL_POP3 = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SSL_POP3");
	public static final java.lang.String _SSL_IMAP = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SSL_IMAP");
	public static final ProtocolType SMTP = new ProtocolType(_SMTP, true);
	public static final ProtocolType SMTP_SSL = new ProtocolType(_SMTP_SSL, true);
	public static final ProtocolType SSMTP_SMTPS = new ProtocolType(_SSMTP_SMTPS, true);
	public static final ProtocolType POP3 = new ProtocolType(_POP3, true);
	public static final ProtocolType IMAP = new ProtocolType(_IMAP, true);
	public static final ProtocolType SSL_POP3 = new ProtocolType(_SSL_POP3, true);
	public static final ProtocolType SSL_IMAP = new ProtocolType(_SSL_IMAP, true);

	public java.lang.String getValue() {

	    return localProtocolType;
	}

	public boolean equals(java.lang.Object obj) {

	    return (obj == this);
	}

	public int hashCode() {

	    return toString().hashCode();
	}

	public java.lang.String toString() {

	    return localProtocolType.toString();
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

	    //We can safely assume an element has only one type associated with it
	    java.lang.String namespace = parentQName.getNamespaceURI();
	    java.lang.String _localName = parentQName.getLocalPart();
	    writeStartElement(null, namespace, _localName, xmlWriter);
	    // add the type details if this is used in a simple type
	    if (serializeType) {
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://gruppoinit.it/sigepro/schemas/messages/mailconfig");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":protocolType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "protocolType", xmlWriter);
		}
	    }
	    if (localProtocolType == null) {
		throw new org.apache.axis2.databinding.ADBException("protocolType cannot be null !!");
	    } else {
		xmlWriter.writeCharacters(localProtocolType);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/sigepro/schemas/messages/mailconfig")) {
		return "ns1";
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
	private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
		throws javax.xml.stream.XMLStreamException {

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
			    stringToWrite.append(prefix).append(":")
				    .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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

	    //We can safely assume an element has only one type associated with it
	    return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(MY_QNAME, new java.lang.Object[] {
		    org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
		    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProtocolType) }, null);
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    public static ProtocolType fromValue(java.lang.String value) throws java.lang.IllegalArgumentException {

		ProtocolType enumeration = (ProtocolType) _table_.get(value);
		if ((enumeration == null) && !((value == null) || (value.equals("")))) {
		    throw new java.lang.IllegalArgumentException();
		}
		return enumeration;
	    }

	    public static ProtocolType fromString(java.lang.String value, java.lang.String namespaceURI) throws java.lang.IllegalArgumentException {

		try {
		    return fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));
		} catch (java.lang.Exception e) {
		    throw new java.lang.IllegalArgumentException();
		}
	    }

	    public static ProtocolType fromString(javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content) {

		if (content.indexOf(":") > -1) {
		    java.lang.String prefix = content.substring(0, content.indexOf(":"));
		    java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
		    return ProtocolType.Factory.fromString(content, namespaceUri);
		} else {
		    return ProtocolType.Factory.fromString(content, "");
		}
	    }

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static ProtocolType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		ProtocolType object = null;
		// initialize a hash map to keep values
		java.util.Map attributeMap = new java.util.HashMap();
		java.util.List extraAttributeList = new java.util.ArrayList<org.apache.axiom.om.OMAttribute>();
		int event;
		java.lang.String nillableValue = null;
		java.lang.String prefix = "";
		java.lang.String namespaceuri = "";
		try {
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    // Note all attributes that were handled. Used to differ normal attributes
		    // from anyAttributes.
		    java.util.Vector handledAttributes = new java.util.Vector();
		    while (!reader.isEndElement()) {
			if (reader.isStartElement() || reader.hasText()) {
			    java.lang.String content = reader.getElementText();
			    if (content.indexOf(":") > 0) {
				// this seems to be a Qname so find the namespace and send
				prefix = content.substring(0, content.indexOf(":"));
				namespaceuri = reader.getNamespaceURI(prefix);
				object = ProtocolType.Factory.fromString(content, namespaceuri);
			    } else {
				// this seems to be not a qname send and empty namespace incase of it is
				// check is done in fromString method
				object = ProtocolType.Factory.fromString(content, "");
			    }
			} else {
			    reader.next();
			}
		    } // end of while loop
		} catch (javax.xml.stream.XMLStreamException e) {
		    throw new java.lang.Exception(e);
		}
		return object;
	    }
	}//end of factory class
    }

    public static class MailConfigResponse implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
		"http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "MailConfigResponse", "ns1");
	/**
	 * field for User
	 */
	protected java.lang.String localUser;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localUserTracker = false;

	public boolean isUserSpecified() {

	    return localUserTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUser() {

	    return localUser;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            User
	 */
	public void setUser(java.lang.String param) {

	    localUserTracker = param != null;
	    this.localUser = param;
	}

	/**
	 * field for Password
	 */
	protected java.lang.String localPassword;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localPasswordTracker = false;

	public boolean isPasswordSpecified() {

	    return localPasswordTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPassword() {

	    return localPassword;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Password
	 */
	public void setPassword(java.lang.String param) {

	    localPasswordTracker = param != null;
	    this.localPassword = param;
	}

	/**
	 * field for UseAuthentication
	 */
	protected boolean localUseAuthentication;

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getUseAuthentication() {

	    return localUseAuthentication;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            UseAuthentication
	 */
	public void setUseAuthentication(boolean param) {

	    this.localUseAuthentication = param;
	}

	/**
	 * field for Url
	 */
	protected java.lang.String localUrl;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUrl() {

	    return localUrl;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Url
	 */
	public void setUrl(java.lang.String param) {

	    this.localUrl = param;
	}

	/**
	 * field for Port
	 */
	protected java.math.BigInteger localPort;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.math.BigInteger
	 */
	public java.math.BigInteger getPort() {

	    return localPort;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Port
	 */
	public void setPort(java.math.BigInteger param) {

	    this.localPort = param;
	}

	/**
	 * field for Protocol
	 */
	protected ProtocolType localProtocol;

	/**
	 * Auto generated getter method
	 * 
	 * @return ProtocolType
	 */
	public ProtocolType getProtocol() {

	    return localProtocol;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Protocol
	 */
	public void setProtocol(ProtocolType param) {

	    this.localProtocol = param;
	}

	/**
	 * field for SenderEmailAddress
	 */
	protected java.lang.String localSenderEmailAddress;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localSenderEmailAddressTracker = false;

	public boolean isSenderEmailAddressSpecified() {

	    return localSenderEmailAddressTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getSenderEmailAddress() {

	    return localSenderEmailAddress;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            SenderEmailAddress
	 */
	public void setSenderEmailAddress(java.lang.String param) {

	    localSenderEmailAddressTracker = param != null;
	    this.localSenderEmailAddress = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://gruppoinit.it/sigepro/schemas/messages/mailconfig");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MailConfigResponse", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MailConfigResponse", xmlWriter);
		}
	    }
	    if (localUserTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "user", xmlWriter);
		if (localUser == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localUser);
		}
		xmlWriter.writeEndElement();
	    }
	    if (localPasswordTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "password", xmlWriter);
		if (localPassword == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localPassword);
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "useAuthentication", xmlWriter);
	    if (false) {
		throw new org.apache.axis2.databinding.ADBException("useAuthentication cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseAuthentication));
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "url", xmlWriter);
	    if (localUrl == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("url cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localUrl);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "port", xmlWriter);
	    if (localPort == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("port cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPort));
	    }
	    xmlWriter.writeEndElement();
	    if (localProtocol == null) {
		throw new org.apache.axis2.databinding.ADBException("protocol cannot be null!!");
	    }
	    localProtocol.serialize(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "protocol"), xmlWriter);
	    if (localSenderEmailAddressTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "senderEmailAddress", xmlWriter);
		if (localSenderEmailAddress == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("senderEmailAddress cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localSenderEmailAddress);
		}
		xmlWriter.writeEndElement();
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/sigepro/schemas/messages/mailconfig")) {
		return "ns1";
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
	private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
		throws javax.xml.stream.XMLStreamException {

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
			    stringToWrite.append(prefix).append(":")
				    .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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
	    if (localUserTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "user"));
		if (localUser != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUser));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");
		}
	    }
	    if (localPasswordTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "password"));
		if (localPassword != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "useAuthentication"));
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseAuthentication));
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "url"));
	    if (localUrl != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUrl));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("url cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "port"));
	    if (localPort != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPort));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("port cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "protocol"));
	    if (localProtocol == null) {
		throw new org.apache.axis2.databinding.ADBException("protocol cannot be null!!");
	    }
	    elementList.add(localProtocol);
	    if (localSenderEmailAddressTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "senderEmailAddress"));
		if (localSenderEmailAddress != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSenderEmailAddress));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("senderEmailAddress cannot be null!!");
		}
	    }
	    return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static MailConfigResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		MailConfigResponse object = new MailConfigResponse();
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
			    if (!"MailConfigResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (MailConfigResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "user").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "password").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setPassword(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "useAuthentication")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setUseAuthentication(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "url").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setUrl(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "port").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setPort(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "protocol").equals(reader
				    .getName())) {
			object.setProtocol(ProtocolType.Factory.parse(reader));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "senderEmailAddress")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setSenderEmailAddress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class MailConfigResponse2 implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
		"http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "MailConfigResponse2", "ns1");
	/**
	 * field for User
	 */
	protected java.lang.String localUser;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localUserTracker = false;

	public boolean isUserSpecified() {

	    return localUserTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUser() {

	    return localUser;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            User
	 */
	public void setUser(java.lang.String param) {

	    localUserTracker = param != null;
	    this.localUser = param;
	}

	/**
	 * field for Password
	 */
	protected java.lang.String localPassword;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localPasswordTracker = false;

	public boolean isPasswordSpecified() {

	    return localPasswordTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPassword() {

	    return localPassword;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Password
	 */
	public void setPassword(java.lang.String param) {

	    localPasswordTracker = param != null;
	    this.localPassword = param;
	}

	/**
	 * field for UseAuthentication
	 */
	protected boolean localUseAuthentication;

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getUseAuthentication() {

	    return localUseAuthentication;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            UseAuthentication
	 */
	public void setUseAuthentication(boolean param) {

	    this.localUseAuthentication = param;
	}

	/**
	 * field for Url
	 */
	protected java.lang.String localUrl;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUrl() {

	    return localUrl;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Url
	 */
	public void setUrl(java.lang.String param) {

	    this.localUrl = param;
	}

	/**
	 * field for Port
	 */
	protected java.math.BigInteger localPort;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.math.BigInteger
	 */
	public java.math.BigInteger getPort() {

	    return localPort;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Port
	 */
	public void setPort(java.math.BigInteger param) {

	    this.localPort = param;
	}

	/**
	 * field for Protocol
	 */
	protected ProtocolType localProtocol;

	/**
	 * Auto generated getter method
	 * 
	 * @return ProtocolType
	 */
	public ProtocolType getProtocol() {

	    return localProtocol;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Protocol
	 */
	public void setProtocol(ProtocolType param) {

	    this.localProtocol = param;
	}

	/**
	 * field for SenderEmailAddress
	 */
	protected java.lang.String localSenderEmailAddress;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localSenderEmailAddressTracker = false;

	public boolean isSenderEmailAddressSpecified() {

	    return localSenderEmailAddressTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getSenderEmailAddress() {

	    return localSenderEmailAddress;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            SenderEmailAddress
	 */
	public void setSenderEmailAddress(java.lang.String param) {

	    localSenderEmailAddressTracker = param != null;
	    this.localSenderEmailAddress = param;
	}

	/**
	 * field for Id_account
	 */
	protected java.math.BigInteger localId_account;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.math.BigInteger
	 */
	public java.math.BigInteger getId_account() {

	    return localId_account;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Id_account
	 */
	public void setId_account(java.math.BigInteger param) {

	    this.localId_account = param;
	}

	/**
	 * field for Descrizione
	 */
	protected java.lang.String localDescrizione;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDescrizione() {

	    return localDescrizione;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Descrizione
	 */
	public void setDescrizione(java.lang.String param) {

	    this.localDescrizione = param;
	}

	/**
	 * field for Codice_software
	 */
	protected java.lang.String localCodice_software;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getCodice_software() {

	    return localCodice_software;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Codice_software
	 */
	public void setCodice_software(java.lang.String param) {

	    this.localCodice_software = param;
	}

	/**
	 * field for Codice_comune
	 */
	protected java.lang.String localCodice_comune;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localCodice_comuneTracker = false;

	public boolean isCodice_comuneSpecified() {

	    return localCodice_comuneTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getCodice_comune() {

	    return localCodice_comune;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Codice_comune
	 */
	public void setCodice_comune(java.lang.String param) {

	    localCodice_comuneTracker = param != null;
	    this.localCodice_comune = param;
	}

	/**
	 * field for Comune
	 */
	protected java.lang.String localComune;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localComuneTracker = false;

	public boolean isComuneSpecified() {

	    return localComuneTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getComune() {

	    return localComune;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Comune
	 */
	public void setComune(java.lang.String param) {

	    localComuneTracker = param != null;
	    this.localComune = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://gruppoinit.it/sigepro/schemas/messages/mailconfig");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MailConfigResponse2", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MailConfigResponse2", xmlWriter);
		}
	    }
	    if (localUserTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "user", xmlWriter);
		if (localUser == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localUser);
		}
		xmlWriter.writeEndElement();
	    }
	    if (localPasswordTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "password", xmlWriter);
		if (localPassword == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localPassword);
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "useAuthentication", xmlWriter);
	    if (false) {
		throw new org.apache.axis2.databinding.ADBException("useAuthentication cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseAuthentication));
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "url", xmlWriter);
	    if (localUrl == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("url cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localUrl);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "port", xmlWriter);
	    if (localPort == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("port cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPort));
	    }
	    xmlWriter.writeEndElement();
	    if (localProtocol == null) {
		throw new org.apache.axis2.databinding.ADBException("protocol cannot be null!!");
	    }
	    localProtocol.serialize(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "protocol"), xmlWriter);
	    if (localSenderEmailAddressTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "senderEmailAddress", xmlWriter);
		if (localSenderEmailAddress == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("senderEmailAddress cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localSenderEmailAddress);
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "id_account", xmlWriter);
	    if (localId_account == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("id_account cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId_account));
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "descrizione", xmlWriter);
	    if (localDescrizione == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("descrizione cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localDescrizione);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "codice_software", xmlWriter);
	    if (localCodice_software == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("codice_software cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localCodice_software);
	    }
	    xmlWriter.writeEndElement();
	    if (localCodice_comuneTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "codice_comune", xmlWriter);
		if (localCodice_comune == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("codice_comune cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localCodice_comune);
		}
		xmlWriter.writeEndElement();
	    }
	    if (localComuneTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "comune", xmlWriter);
		if (localComune == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("comune cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localComune);
		}
		xmlWriter.writeEndElement();
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/sigepro/schemas/messages/mailconfig")) {
		return "ns1";
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
	private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
		throws javax.xml.stream.XMLStreamException {

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
			    stringToWrite.append(prefix).append(":")
				    .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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
	    if (localUserTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "user"));
		if (localUser != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUser));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");
		}
	    }
	    if (localPasswordTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "password"));
		if (localPassword != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "useAuthentication"));
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseAuthentication));
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "url"));
	    if (localUrl != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUrl));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("url cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "port"));
	    if (localPort != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPort));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("port cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "protocol"));
	    if (localProtocol == null) {
		throw new org.apache.axis2.databinding.ADBException("protocol cannot be null!!");
	    }
	    elementList.add(localProtocol);
	    if (localSenderEmailAddressTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "senderEmailAddress"));
		if (localSenderEmailAddress != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSenderEmailAddress));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("senderEmailAddress cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "id_account"));
	    if (localId_account != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId_account));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("id_account cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "descrizione"));
	    if (localDescrizione != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescrizione));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("descrizione cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "codice_software"));
	    if (localCodice_software != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodice_software));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("codice_software cannot be null!!");
	    }
	    if (localCodice_comuneTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "codice_comune"));
		if (localCodice_comune != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodice_comune));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("codice_comune cannot be null!!");
		}
	    }
	    if (localComuneTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "comune"));
		if (localComune != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localComune));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("comune cannot be null!!");
		}
	    }
	    return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static MailConfigResponse2 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		MailConfigResponse2 object = new MailConfigResponse2();
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
			    if (!"MailConfigResponse2".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (MailConfigResponse2) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "user").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "password").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setPassword(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "useAuthentication")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setUseAuthentication(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "url").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setUrl(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "port").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setPort(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "protocol").equals(reader
				    .getName())) {
			object.setProtocol(ProtocolType.Factory.parse(reader));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "senderEmailAddress")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setSenderEmailAddress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "id_account").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setId_account(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "descrizione").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setDescrizione(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "codice_software")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setCodice_software(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "codice_comune")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setCodice_comune(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "comune").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setComune(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class ExtensionMapper {

	public static java.lang.Object getTypeObject(java.lang.String namespaceURI, java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
		throws java.lang.Exception {

	    if ("http://gruppoinit.it/sigepro/schemas/messages/mailconfig".equals(namespaceURI) && "protocolType".equals(typeName)) {
		return ProtocolType.Factory.parse(reader);
	    }
	    if ("http://gruppoinit.it/sigepro/schemas/messages/mailconfig".equals(namespaceURI) && "actionType".equals(typeName)) {
		return ActionType.Factory.parse(reader);
	    }
	    throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
	}
    }

    public static class MailConfigRequest2 implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
		"http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "MailConfigRequest2", "ns1");
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
	 * field for Codicecomune
	 */
	protected java.lang.String localCodicecomune;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localCodicecomuneTracker = false;

	public boolean isCodicecomuneSpecified() {

	    return localCodicecomuneTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getCodicecomune() {

	    return localCodicecomune;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Codicecomune
	 */
	public void setCodicecomune(java.lang.String param) {

	    localCodicecomuneTracker = param != null;
	    this.localCodicecomune = param;
	}

	/**
	 * field for Id_account
	 */
	protected java.math.BigInteger localId_account;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localId_accountTracker = false;

	public boolean isId_accountSpecified() {

	    return localId_accountTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.math.BigInteger
	 */
	public java.math.BigInteger getId_account() {

	    return localId_account;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Id_account
	 */
	public void setId_account(java.math.BigInteger param) {

	    localId_accountTracker = param != null;
	    this.localId_account = param;
	}

	/**
	 * field for Action
	 */
	protected ActionType localAction;

	/**
	 * Auto generated getter method
	 * 
	 * @return ActionType
	 */
	public ActionType getAction() {

	    return localAction;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Action
	 */
	public void setAction(ActionType param) {

	    this.localAction = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://gruppoinit.it/sigepro/schemas/messages/mailconfig");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MailConfigRequest2", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MailConfigRequest2", xmlWriter);
		}
	    }
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "token", xmlWriter);
	    if (localToken == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localToken);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
	    writeStartElement(null, namespace, "software", xmlWriter);
	    if (localSoftware == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localSoftware);
	    }
	    xmlWriter.writeEndElement();
	    if (localCodicecomuneTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "codicecomune", xmlWriter);
		if (localCodicecomune == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("codicecomune cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localCodicecomune);
		}
		xmlWriter.writeEndElement();
	    }
	    if (localId_accountTracker) {
		namespace = "http://gruppoinit.it/sigepro/schemas/messages/mailconfig";
		writeStartElement(null, namespace, "id_account", xmlWriter);
		if (localId_account == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("id_account cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId_account));
		}
		xmlWriter.writeEndElement();
	    }
	    if (localAction == null) {
		throw new org.apache.axis2.databinding.ADBException("action cannot be null!!");
	    }
	    localAction.serialize(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "action"), xmlWriter);
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/sigepro/schemas/messages/mailconfig")) {
		return "ns1";
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
	private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
		throws javax.xml.stream.XMLStreamException {

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
			    stringToWrite.append(prefix).append(":")
				    .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "software"));
	    if (localSoftware != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSoftware));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	    }
	    if (localCodicecomuneTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "codicecomune"));
		if (localCodicecomune != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodicecomune));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("codicecomune cannot be null!!");
		}
	    }
	    if (localId_accountTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "id_account"));
		if (localId_account != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId_account));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("id_account cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "action"));
	    if (localAction == null) {
		throw new org.apache.axis2.databinding.ADBException("action cannot be null!!");
	    }
	    elementList.add(localAction);
	    return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static MailConfigRequest2 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		MailConfigRequest2 object = new MailConfigRequest2();
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
			    if (!"MailConfigRequest2".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (MailConfigRequest2) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "token").equals(reader
				    .getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "software").equals(reader
				    .getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "codicecomune")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setCodicecomune(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "id_account").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setId_account(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/sigepro/schemas/messages/mailconfig", "action").equals(reader
				    .getName())) {
			object.setAction(ActionType.Factory.parse(reader));
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

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2 param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2 param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2 param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    /**
     * get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory) {

	return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param, java.lang.Class type, java.util.Map extraNamespaces)
	    throws org.apache.axis2.AxisFault {

	try {
	    if (it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2.class.equals(type)) {
		return it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest2.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2.class.equals(type)) {
		return it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse2.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest.class.equals(type)) {
		return it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigRequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse.class.equals(type)) {
		return it.gruppoinit.sigepro.definitions.mailconfig.MailConfigServiceStub.MailConfigResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	} catch (java.lang.Exception e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
	return null;
    }
}
