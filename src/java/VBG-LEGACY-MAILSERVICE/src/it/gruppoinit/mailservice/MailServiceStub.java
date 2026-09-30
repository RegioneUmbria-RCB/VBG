/**
 * MailServiceStub.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package it.gruppoinit.mailservice;

/*
 * MailServiceStub java implementation
 */
public class MailServiceStub extends org.apache.axis2.client.Stub {

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
	_service = new org.apache.axis2.description.AxisService("MailService" + getUniqueSuffix());
	addAnonymousOperations();
	//creating the operations
	org.apache.axis2.description.AxisOperation __operation;
	_operations = new org.apache.axis2.description.AxisOperation[2];
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://gruppoinit.it/MailService/", "sendMail"));
	_service.addOperation(__operation);
	_operations[0] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://gruppoinit.it/MailService/", "sendMail2"));
	_service.addOperation(__operation);
	_operations[1] = __operation;
    }

    //populates the faults
    private void populateFaults() {

    }

    /**
     * Constructor that takes in a configContext
     */
    public MailServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint)
	    throws org.apache.axis2.AxisFault {

	this(configurationContext, targetEndpoint, false);
    }

    /**
     * Constructor that takes in a configContext and useseperate listner
     */
    public MailServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint,
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
    public MailServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

	this(configurationContext, "http://www.example.org/");
    }

    /**
     * Default Constructor
     */
    public MailServiceStub() throws org.apache.axis2.AxisFault {

	this("http://www.example.org/");
    }

    /**
     * Constructor taking the target endpoint
     */
    public MailServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {

	this(null, targetEndpoint);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.mailservice.MailService#sendMail
     * @param messageRequest0
     */
    public it.gruppoinit.mailservice.MailServiceStub.MessageResponse sendMail(it.gruppoinit.mailservice.MailServiceStub.MessageRequest messageRequest0)
	    throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
	    _operationClient.getOptions().setAction("http://gruppoinit.it/MailService/sendMail");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), messageRequest0,
		    optimizeContent(new javax.xml.namespace.QName("http://gruppoinit.it/MailService/", "sendMail")), new javax.xml.namespace.QName(
			    "http://gruppoinit.it/MailService/", "sendMail"));
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
	    java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(), it.gruppoinit.mailservice.MailServiceStub.MessageResponse.class,
		    getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.mailservice.MailServiceStub.MessageResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "sendMail"));
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
     * Auto generated method signature for Asynchronous Invocations
     * 
     * @see it.gruppoinit.mailservice.MailService#startsendMail
     * @param messageRequest0
     */
    public void startsendMail(it.gruppoinit.mailservice.MailServiceStub.MessageRequest messageRequest0,
	    final it.gruppoinit.mailservice.MailServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
	_operationClient.getOptions().setAction("http://gruppoinit.it/MailService/sendMail");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), messageRequest0,
		optimizeContent(new javax.xml.namespace.QName("http://gruppoinit.it/MailService/", "sendMail")), new javax.xml.namespace.QName(
			"http://gruppoinit.it/MailService/", "sendMail"));
	// adding SOAP soap_headers
	_serviceClient.addHeadersToEnvelope(env);
	// create message context with that soap envelope
	_messageContext.setEnvelope(env);
	// add the message context to the operation client
	_operationClient.addMessageContext(_messageContext);
	_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {

	    public void onMessage(org.apache.axis2.context.MessageContext resultContext) {

		try {
		    org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
		    java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
			    it.gruppoinit.mailservice.MailServiceStub.MessageResponse.class, getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultsendMail((it.gruppoinit.mailservice.MailServiceStub.MessageResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorsendMail(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "sendMail"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorsendMail(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail(f);
			    }
			} else {
			    callback.receiveErrorsendMail(f);
			}
		    } else {
			callback.receiveErrorsendMail(f);
		    }
		} else {
		    callback.receiveErrorsendMail(error);
		}
	    }

	    public void onFault(org.apache.axis2.context.MessageContext faultContext) {

		org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
		onError(fault);
	    }

	    public void onComplete() {

		try {
		    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
		} catch (org.apache.axis2.AxisFault axisFault) {
		    callback.receiveErrorsendMail(axisFault);
		}
	    }
	});
	org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
	if (_operations[0].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
	    _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
	    _operations[0].setMessageReceiver(_callbackReceiver);
	}
	//execute the operation client
	_operationClient.execute(false);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.mailservice.MailService#sendMail2
     * @param messageRequest22
     */
    public it.gruppoinit.mailservice.MailServiceStub.MessageResponse2 sendMail2(
	    it.gruppoinit.mailservice.MailServiceStub.MessageRequest2 messageRequest22) throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
	    _operationClient.getOptions().setAction("http://gruppoinit.it/MailService/sendMail2");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), messageRequest22,
		    optimizeContent(new javax.xml.namespace.QName("http://gruppoinit.it/MailService/", "sendMail2")), new javax.xml.namespace.QName(
			    "http://gruppoinit.it/MailService/", "sendMail2"));
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
		    it.gruppoinit.mailservice.MailServiceStub.MessageResponse2.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.mailservice.MailServiceStub.MessageResponse2) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail2"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail2"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "sendMail2"));
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
     * Auto generated method signature for Asynchronous Invocations
     * 
     * @see it.gruppoinit.mailservice.MailService#startsendMail2
     * @param messageRequest22
     */
    public void startsendMail2(it.gruppoinit.mailservice.MailServiceStub.MessageRequest2 messageRequest22,
	    final it.gruppoinit.mailservice.MailServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
	_operationClient.getOptions().setAction("http://gruppoinit.it/MailService/sendMail2");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), messageRequest22,
		optimizeContent(new javax.xml.namespace.QName("http://gruppoinit.it/MailService/", "sendMail2")), new javax.xml.namespace.QName(
			"http://gruppoinit.it/MailService/", "sendMail2"));
	// adding SOAP soap_headers
	_serviceClient.addHeadersToEnvelope(env);
	// create message context with that soap envelope
	_messageContext.setEnvelope(env);
	// add the message context to the operation client
	_operationClient.addMessageContext(_messageContext);
	_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {

	    public void onMessage(org.apache.axis2.context.MessageContext resultContext) {

		try {
		    org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
		    java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
			    it.gruppoinit.mailservice.MailServiceStub.MessageResponse2.class, getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultsendMail2((it.gruppoinit.mailservice.MailServiceStub.MessageResponse2) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorsendMail2(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail2"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "sendMail2"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "sendMail2"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorsendMail2(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail2(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail2(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail2(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail2(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail2(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail2(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorsendMail2(f);
			    }
			} else {
			    callback.receiveErrorsendMail2(f);
			}
		    } else {
			callback.receiveErrorsendMail2(f);
		    }
		} else {
		    callback.receiveErrorsendMail2(error);
		}
	    }

	    public void onFault(org.apache.axis2.context.MessageContext faultContext) {

		org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
		onError(fault);
	    }

	    public void onComplete() {

		try {
		    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
		} catch (org.apache.axis2.AxisFault axisFault) {
		    callback.receiveErrorsendMail2(axisFault);
		}
	    }
	});
	org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
	if (_operations[1].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
	    _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
	    _operations[1].setMessageReceiver(_callbackReceiver);
	}
	//execute the operation client
	_operationClient.execute(false);
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

    //http://www.example.org/
    public static class AttachmentType implements org.apache.axis2.databinding.ADBBean {

	/* This type was generated from the piece of schema that had
	        name = AttachmentType
	        Namespace URI = http://gruppoinit.it/mailService/schemas/messages
	        Namespace Prefix = ns1
	        */
	/**
	 * field for Id
	 */
	protected java.lang.String localId;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localIdTracker = false;

	public boolean isIdSpecified() {

	    return localIdTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getId() {

	    return localId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Id
	 */
	public void setId(java.lang.String param) {

	    localIdTracker = param != null;
	    this.localId = param;
	}

	/**
	 * field for Descrizione
	 */
	protected java.lang.String localDescrizione;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localDescrizioneTracker = false;

	public boolean isDescrizioneSpecified() {

	    return localDescrizioneTracker;
	}

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

	    localDescrizioneTracker = param != null;
	    this.localDescrizione = param;
	}

	/**
	 * field for MimeType
	 */
	protected java.lang.String localMimeType;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMimeType() {

	    return localMimeType;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            MimeType
	 */
	public void setMimeType(java.lang.String param) {

	    this.localMimeType = param;
	}

	/**
	 * field for FileName
	 */
	protected java.lang.String localFileName;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFileName() {

	    return localFileName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            FileName
	 */
	public void setFileName(java.lang.String param) {

	    this.localFileName = param;
	}

	/**
	 * field for BinaryData
	 */
	protected javax.activation.DataHandler localBinaryData;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localBinaryDataTracker = false;

	public boolean isBinaryDataSpecified() {

	    return localBinaryDataTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return javax.activation.DataHandler
	 */
	public javax.activation.DataHandler getBinaryData() {

	    return localBinaryData;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            BinaryData
	 */
	public void setBinaryData(javax.activation.DataHandler param) {

	    localBinaryDataTracker = param != null;
	    this.localBinaryData = param;
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
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":AttachmentType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "AttachmentType", xmlWriter);
		}
	    }
	    if (localIdTracker) {
		namespace = "http://gruppoinit.it/mailService/schemas/messages";
		writeStartElement(null, namespace, "id", xmlWriter);
		if (localId == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localId);
		}
		xmlWriter.writeEndElement();
	    }
	    if (localDescrizioneTracker) {
		namespace = "http://gruppoinit.it/mailService/schemas/messages";
		writeStartElement(null, namespace, "descrizione", xmlWriter);
		if (localDescrizione == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("descrizione cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localDescrizione);
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "mimeType", xmlWriter);
	    if (localMimeType == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("mimeType cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localMimeType);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "fileName", xmlWriter);
	    if (localFileName == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("fileName cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localFileName);
	    }
	    xmlWriter.writeEndElement();
	    if (localBinaryDataTracker) {
		namespace = "http://gruppoinit.it/mailService/schemas/messages";
		writeStartElement(null, namespace, "binaryData", xmlWriter);
		if (localBinaryData != null) {
		    try {
			org.apache.axiom.util.stax.XMLStreamWriterUtils.writeDataHandler(xmlWriter, localBinaryData, null, true);
		    } catch (java.io.IOException ex) {
			throw new javax.xml.stream.XMLStreamException("Unable to read data handler for binaryData", ex);
		    }
		} else {
		}
		xmlWriter.writeEndElement();
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/mailService/schemas/messages")) {
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
	    if (localIdTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "id"));
		if (localId != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
		}
	    }
	    if (localDescrizioneTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "descrizione"));
		if (localDescrizione != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescrizione));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("descrizione cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mimeType"));
	    if (localMimeType != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMimeType));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("mimeType cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "fileName"));
	    if (localFileName != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFileName));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("fileName cannot be null!!");
	    }
	    if (localBinaryDataTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "binaryData"));
		elementList.add(localBinaryData);
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
	    public static AttachmentType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		AttachmentType object = new AttachmentType();
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
			    if (!"AttachmentType".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (AttachmentType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "id").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "descrizione").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setDescrizione(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mimeType")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setMimeType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "fileName")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setFileName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "binaryData").equals(reader
				    .getName())) {
			object.setBinaryData(org.apache.axiom.util.stax.XMLStreamReaderUtils.getDataHandlerFromElement(reader));
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

    public static class AttachmentsType implements org.apache.axis2.databinding.ADBBean {

	/* This type was generated from the piece of schema that had
	        name = AttachmentsType
	        Namespace URI = http://gruppoinit.it/mailService/schemas/messages
	        Namespace Prefix = ns1
	        */
	/**
	 * field for Attachment This was an Array!
	 */
	protected AttachmentType[] localAttachment;

	/**
	 * Auto generated getter method
	 * 
	 * @return AttachmentType[]
	 */
	public AttachmentType[] getAttachment() {

	    return localAttachment;
	}

	/**
	 * validate the array for Attachment
	 */
	protected void validateAttachment(AttachmentType[] param) {

	    if ((param != null) && (param.length < 1)) {
		throw new java.lang.RuntimeException();
	    }
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Attachment
	 */
	public void setAttachment(AttachmentType[] param) {

	    validateAttachment(param);
	    this.localAttachment = param;
	}

	/**
	 * Auto generated add method for the array for convenience
	 * 
	 * @param param
	 *            AttachmentType
	 */
	public void addAttachment(AttachmentType param) {

	    if (localAttachment == null) {
		localAttachment = new AttachmentType[] {};
	    }
	    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localAttachment);
	    list.add(param);
	    this.localAttachment = (AttachmentType[]) list.toArray(new AttachmentType[list.size()]);
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
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":AttachmentsType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "AttachmentsType", xmlWriter);
		}
	    }
	    if (localAttachment != null) {
		for (int i = 0; i < localAttachment.length; i++) {
		    if (localAttachment[i] != null) {
			localAttachment[i].serialize(
				new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachment"), xmlWriter);
		    } else {
			throw new org.apache.axis2.databinding.ADBException("attachment cannot be null!!");
		    }
		}
	    } else {
		throw new org.apache.axis2.databinding.ADBException("attachment cannot be null!!");
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/mailService/schemas/messages")) {
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
	    if (localAttachment != null) {
		for (int i = 0; i < localAttachment.length; i++) {
		    if (localAttachment[i] != null) {
			elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachment"));
			elementList.add(localAttachment[i]);
		    } else {
			throw new org.apache.axis2.databinding.ADBException("attachment cannot be null !!");
		    }
		}
	    } else {
		throw new org.apache.axis2.databinding.ADBException("attachment cannot be null!!");
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
	    public static AttachmentsType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		AttachmentsType object = new AttachmentsType();
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
			    if (!"AttachmentsType".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (AttachmentsType) ExtensionMapper.getTypeObject(nsUri, type, reader);
			    }
			}
		    }
		    // Note all attributes that were handled. Used to differ normal attributes
		    // from anyAttributes.
		    java.util.Vector handledAttributes = new java.util.Vector();
		    reader.next();
		    java.util.ArrayList list1 = new java.util.ArrayList();
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachment").equals(reader
				    .getName())) {
			// Process the array and step past its final element's end.
			list1.add(AttachmentType.Factory.parse(reader));
			//loop until we find a start element that is not part of this array
			boolean loopDone1 = false;
			while (!loopDone1) {
			    // We should be at the end element, but make sure
			    while (!reader.isEndElement())
				reader.next();
			    // Step out of this element
			    reader.next();
			    // Step to next element event.
			    while (!reader.isStartElement() && !reader.isEndElement())
				reader.next();
			    if (reader.isEndElement()) {
				//two continuous end elements means we are exiting the xml structure
				loopDone1 = true;
			    } else {
				if (new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachment").equals(reader
					.getName())) {
				    list1.add(AttachmentType.Factory.parse(reader));
				} else {
				    loopDone1 = true;
				}
			    }
			}
			// call the converter utility  to convert and set the array
			object.setAttachment((AttachmentType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(AttachmentType.class,
				list1));
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

    public static class ExtensionMapper {

	public static java.lang.Object getTypeObject(java.lang.String namespaceURI, java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
		throws java.lang.Exception {

	    if ("http://gruppoinit.it/mailService/schemas/messages".equals(namespaceURI) && "AttachmentType".equals(typeName)) {
		return AttachmentType.Factory.parse(reader);
	    }
	    if ("http://gruppoinit.it/mailService/schemas/messages".equals(namespaceURI) && "AttachmentsType".equals(typeName)) {
		return AttachmentsType.Factory.parse(reader);
	    }
	    if ("http://gruppoinit.it/mailService/schemas/messages".equals(namespaceURI) && "MailMessageType".equals(typeName)) {
		return MailMessageType.Factory.parse(reader);
	    }
	    throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
	}
    }

    public static class MessageRequest2 implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages",
		"MessageRequest2", "ns1");
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
	 * field for Accountid
	 */
	protected java.math.BigInteger localAccountid;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localAccountidTracker = false;

	public boolean isAccountidSpecified() {

	    return localAccountidTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.math.BigInteger
	 */
	public java.math.BigInteger getAccountid() {

	    return localAccountid;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Accountid
	 */
	public void setAccountid(java.math.BigInteger param) {

	    localAccountidTracker = param != null;
	    this.localAccountid = param;
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
	protected MailMessageType localMailMessage;

	/**
	 * Auto generated getter method
	 * 
	 * @return MailMessageType
	 */
	public MailMessageType getMailMessage() {

	    return localMailMessage;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            MailMessage
	 */
	public void setMailMessage(MailMessageType param) {

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
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MessageRequest2", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MessageRequest2", xmlWriter);
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
	    if (localAccountidTracker) {
		namespace = "http://gruppoinit.it/mailService/schemas/messages";
		writeStartElement(null, namespace, "accountid", xmlWriter);
		if (localAccountid == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("accountid cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountid));
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "software", xmlWriter);
	    if (localSoftware == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localSoftware);
	    }
	    xmlWriter.writeEndElement();
	    if (localCodicecomuneTracker) {
		namespace = "http://gruppoinit.it/mailService/schemas/messages";
		writeStartElement(null, namespace, "codicecomune", xmlWriter);
		if (localCodicecomune == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("codicecomune cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localCodicecomune);
		}
		xmlWriter.writeEndElement();
	    }
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
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    }
	    if (localAccountidTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "accountid"));
		if (localAccountid != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountid));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("accountid cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "software"));
	    if (localSoftware != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSoftware));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("software cannot be null!!");
	    }
	    if (localCodicecomuneTracker) {
		elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "codicecomune"));
		if (localCodicecomune != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodicecomune));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("codicecomune cannot be null!!");
		}
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
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static MessageRequest2 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		MessageRequest2 object = new MessageRequest2();
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
			    if (!"MessageRequest2".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (MessageRequest2) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "accountid").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setAccountid(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "software")
				    .equals(reader.getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "codicecomune").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setCodicecomune(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mailMessage").equals(reader
				    .getName())) {
			object.setMailMessage(MailMessageType.Factory.parse(reader));
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

    public static class MessageResponse implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages",
		"MessageResponse", "ns1");
	/**
	 * field for Esito
	 */
	protected java.lang.String localEsito;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getEsito() {

	    return localEsito;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Esito
	 */
	public void setEsito(java.lang.String param) {

	    this.localEsito = param;
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
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MessageResponse", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MessageResponse", xmlWriter);
		}
	    }
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "esito", xmlWriter);
	    if (localEsito == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("esito cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localEsito);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/mailService/schemas/messages")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "esito"));
	    if (localEsito != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsito));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("esito cannot be null!!");
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
	    public static MessageResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		MessageResponse object = new MessageResponse();
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
			    if (!"MessageResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (MessageResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "esito").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setEsito(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class MessageResponse2 implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages",
		"MessageResponse2", "ns1");
	/**
	 * field for Esito
	 */
	protected java.lang.String localEsito;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getEsito() {

	    return localEsito;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Esito
	 */
	public void setEsito(java.lang.String param) {

	    this.localEsito = param;
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
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":MessageResponse2", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "MessageResponse2", xmlWriter);
		}
	    }
	    namespace = "http://gruppoinit.it/mailService/schemas/messages";
	    writeStartElement(null, namespace, "esito", xmlWriter);
	    if (localEsito == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("esito cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localEsito);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/mailService/schemas/messages")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "esito"));
	    if (localEsito != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsito));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("esito cannot be null!!");
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
	    public static MessageResponse2 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		MessageResponse2 object = new MessageResponse2();
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
			    if (!"MessageResponse2".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (MessageResponse2) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "esito").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setEsito(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class MailMessageType implements org.apache.axis2.databinding.ADBBean {

	/* This type was generated from the piece of schema that had
	        name = MailMessageType
	        Namespace URI = http://gruppoinit.it/mailService/schemas/messages
	        Namespace Prefix = ns1
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
	protected AttachmentsType localAttachments;
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
	 * @return AttachmentsType
	 */
	public AttachmentsType getAttachments() {

	    return localAttachments;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Attachments
	 */
	public void setAttachments(AttachmentsType param) {

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
		localAttachments.serialize(new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachments"),
			xmlWriter);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://gruppoinit.it/mailService/schemas/messages")) {
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
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
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
				return (MailMessageType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mittente")
				    .equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setMittente(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "corpoMail").equals(reader
				    .getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "destinatari").equals(reader
				    .getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "messageID").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setMessageID(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "attachments").equals(reader
				    .getName())) {
			object.setAttachments(AttachmentsType.Factory.parse(reader));
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

    public static class MessageRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages",
		"MessageRequest", "ns1");
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
	protected MailMessageType localMailMessage;

	/**
	 * Auto generated getter method
	 * 
	 * @return MailMessageType
	 */
	public MailMessageType getMailMessage() {

	    return localMailMessage;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            MailMessage
	 */
	public void setMailMessage(MailMessageType param) {

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
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
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
				return (MessageRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "software")
				    .equals(reader.getName())) {
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
			    && new javax.xml.namespace.QName("http://gruppoinit.it/mailService/schemas/messages", "mailMessage").equals(reader
				    .getName())) {
			object.setMailMessage(MailMessageType.Factory.parse(reader));
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

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.mailservice.MailServiceStub.MessageRequest param, boolean optimizeContent)
	    throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.mailservice.MailServiceStub.MessageRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.mailservice.MailServiceStub.MessageResponse param, boolean optimizeContent)
	    throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.mailservice.MailServiceStub.MessageResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.mailservice.MailServiceStub.MessageRequest2 param, boolean optimizeContent)
	    throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.mailservice.MailServiceStub.MessageRequest2.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.mailservice.MailServiceStub.MessageResponse2 param, boolean optimizeContent)
	    throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.mailservice.MailServiceStub.MessageResponse2.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.mailservice.MailServiceStub.MessageRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
	    throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(param.getOMElement(it.gruppoinit.mailservice.MailServiceStub.MessageRequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.mailservice.MailServiceStub.MessageRequest2 param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
	    throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(param.getOMElement(it.gruppoinit.mailservice.MailServiceStub.MessageRequest2.MY_QNAME, factory));
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
	    if (it.gruppoinit.mailservice.MailServiceStub.MessageRequest.class.equals(type)) {
		return it.gruppoinit.mailservice.MailServiceStub.MessageRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.mailservice.MailServiceStub.MessageResponse.class.equals(type)) {
		return it.gruppoinit.mailservice.MailServiceStub.MessageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.mailservice.MailServiceStub.MessageRequest2.class.equals(type)) {
		return it.gruppoinit.mailservice.MailServiceStub.MessageRequest2.Factory.parse(param.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.mailservice.MailServiceStub.MessageResponse2.class.equals(type)) {
		return it.gruppoinit.mailservice.MailServiceStub.MessageResponse2.Factory.parse(param.getXMLStreamReaderWithoutCaching());
	    }
	} catch (java.lang.Exception e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
	return null;
    }
}
