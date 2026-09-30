/**
 * SigeproSecurityServiceStub.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package it.gruppoinit.sigeprosecurity.ws;

/*
 * SigeproSecurityServiceStub java implementation
 */
public class SigeproSecurityServiceStub extends org.apache.axis2.client.Stub {

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
	_service = new org.apache.axis2.description.AxisService("SigeproSecurityService" + getUniqueSuffix());
	addAnonymousOperations();
	//creating the operations
	org.apache.axis2.description.AxisOperation __operation;
	_operations = new org.apache.axis2.description.AxisOperation[7];
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getDbConnectionInfo"));
	_service.addOperation(__operation);
	_operations[0] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "logout"));
	_service.addOperation(__operation);
	_operations[1] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "login"));
	_service.addOperation(__operation);
	_operations[2] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "loginSSO"));
	_service.addOperation(__operation);
	_operations[3] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getApplicationInfo"));
	_service.addOperation(__operation);
	_operations[4] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getSecurityList"));
	_service.addOperation(__operation);
	_operations[5] = __operation;
	__operation = new org.apache.axis2.description.OutInAxisOperation();
	__operation.setName(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "checkToken"));
	_service.addOperation(__operation);
	_operations[6] = __operation;
    }

    //populates the faults
    private void populateFaults() {

    }

    /**
     * Constructor that takes in a configContext
     */
    public SigeproSecurityServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint)
	    throws org.apache.axis2.AxisFault {

	this(configurationContext, targetEndpoint, false);
    }

    /**
     * Constructor that takes in a configContext and useseperate listner
     */
    public SigeproSecurityServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint,
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
    public SigeproSecurityServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

	this(configurationContext, "http://devel9:8080/sigeprosecurity/services");
    }

    /**
     * Default Constructor
     */
    public SigeproSecurityServiceStub() throws org.apache.axis2.AxisFault {

	this("http://devel9:8080/sigeprosecurity/services");
    }

    /**
     * Constructor taking the target endpoint
     */
    public SigeproSecurityServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {

	this(null, targetEndpoint);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#getDbConnectionInfo
     * @param getDbConnectionInfoRequest0
     */
    public it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse getDbConnectionInfo(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest getDbConnectionInfoRequest0)
	    throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
	    _operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/GetDbConnectionInfoRequest");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getDbConnectionInfoRequest0,
		    optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getDbConnectionInfo")),
		    new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getDbConnectionInfo"));
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
		    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetDbConnectionInfo"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetDbConnectionInfo"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "GetDbConnectionInfo"));
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#startgetDbConnectionInfo
     * @param getDbConnectionInfoRequest0
     */
    public void startgetDbConnectionInfo(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest getDbConnectionInfoRequest0,
	    final it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
	_operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/GetDbConnectionInfoRequest");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getDbConnectionInfoRequest0,
		optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getDbConnectionInfo")),
		new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getDbConnectionInfo"));
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
			    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse.class,
			    getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultgetDbConnectionInfo((it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorgetDbConnectionInfo(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetDbConnectionInfo"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetDbConnectionInfo"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "GetDbConnectionInfo"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorgetDbConnectionInfo(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetDbConnectionInfo(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetDbConnectionInfo(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetDbConnectionInfo(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetDbConnectionInfo(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetDbConnectionInfo(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetDbConnectionInfo(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetDbConnectionInfo(f);
			    }
			} else {
			    callback.receiveErrorgetDbConnectionInfo(f);
			}
		    } else {
			callback.receiveErrorgetDbConnectionInfo(f);
		    }
		} else {
		    callback.receiveErrorgetDbConnectionInfo(error);
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
		    callback.receiveErrorgetDbConnectionInfo(axisFault);
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#logout
     * @param logoutRequest2
     */
    public it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse logout(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest logoutRequest2) throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
	    _operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/LogoutRequest");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), logoutRequest2,
		    optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "logout")),
		    new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "logout"));
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
		    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Logout"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Logout"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "Logout"));
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#startlogout
     * @param logoutRequest2
     */
    public void startlogout(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest logoutRequest2,
	    final it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
	_operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/LogoutRequest");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), logoutRequest2,
		optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "logout")), new javax.xml.namespace.QName(
			"http://sigeprosecurity.gruppoinit.it/ws", "logout"));
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
			    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse.class, getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultlogout((it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorlogout(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Logout"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Logout"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "Logout"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorlogout(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogout(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogout(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogout(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogout(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogout(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogout(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogout(f);
			    }
			} else {
			    callback.receiveErrorlogout(f);
			}
		    } else {
			callback.receiveErrorlogout(f);
		    }
		} else {
		    callback.receiveErrorlogout(error);
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
		    callback.receiveErrorlogout(axisFault);
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
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#login
     * @param loginRequest4
     */
    public it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse login(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest loginRequest4) throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
	    _operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/LoginRequest");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), loginRequest4,
		    optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "login")),
		    new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "login"));
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
		    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Login"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Login"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "Login"));
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#startlogin
     * @param loginRequest4
     */
    public void startlogin(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest loginRequest4,
	    final it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
	_operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/LoginRequest");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), loginRequest4, optimizeContent(new javax.xml.namespace.QName(
		"http://sigeprosecurity.gruppoinit.it/ws", "login")), new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws",
		"login"));
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
			    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse.class, getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultlogin((it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorlogin(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Login"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Login"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "Login"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorlogin(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogin(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogin(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogin(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogin(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogin(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogin(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorlogin(f);
			    }
			} else {
			    callback.receiveErrorlogin(f);
			}
		    } else {
			callback.receiveErrorlogin(f);
		    }
		} else {
		    callback.receiveErrorlogin(error);
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
		    callback.receiveErrorlogin(axisFault);
		}
	    }
	});
	org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
	if (_operations[2].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
	    _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
	    _operations[2].setMessageReceiver(_callbackReceiver);
	}
	//execute the operation client
	_operationClient.execute(false);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#loginSSO
     * @param loginSSORequest6
     */
    public it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse loginSSO(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest loginSSORequest6) throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
	    _operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/LoginSSORequest");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), loginSSORequest6,
		    optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "loginSSO")),
		    new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "loginSSO"));
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
		    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "LoginSSO"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "LoginSSO"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "LoginSSO"));
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#startloginSSO
     * @param loginSSORequest6
     */
    public void startloginSSO(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest loginSSORequest6,
	    final it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
	_operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/LoginSSORequest");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), loginSSORequest6,
		optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "loginSSO")), new javax.xml.namespace.QName(
			"http://sigeprosecurity.gruppoinit.it/ws", "loginSSO"));
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
			    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse.class, getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultloginSSO((it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorloginSSO(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "LoginSSO"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "LoginSSO"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "LoginSSO"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorloginSSO(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorloginSSO(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorloginSSO(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorloginSSO(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorloginSSO(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorloginSSO(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorloginSSO(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorloginSSO(f);
			    }
			} else {
			    callback.receiveErrorloginSSO(f);
			}
		    } else {
			callback.receiveErrorloginSSO(f);
		    }
		} else {
		    callback.receiveErrorloginSSO(error);
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
		    callback.receiveErrorloginSSO(axisFault);
		}
	    }
	});
	org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
	if (_operations[3].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
	    _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
	    _operations[3].setMessageReceiver(_callbackReceiver);
	}
	//execute the operation client
	_operationClient.execute(false);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#getApplicationInfo
     * @param getApplicationInfoRequest8
     */
    public it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse getApplicationInfo(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest getApplicationInfoRequest8)
	    throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
	    _operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/GetApplicationInfoRequest");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getApplicationInfoRequest8,
		    optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getApplicationInfo")),
		    new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getApplicationInfo"));
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
		    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetApplicationInfo"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetApplicationInfo"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "GetApplicationInfo"));
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#startgetApplicationInfo
     * @param getApplicationInfoRequest8
     */
    public void startgetApplicationInfo(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest getApplicationInfoRequest8,
	    final it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
	_operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/GetApplicationInfoRequest");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getApplicationInfoRequest8,
		optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getApplicationInfo")),
		new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getApplicationInfo"));
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
			    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse.class,
			    getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultgetApplicationInfo((it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorgetApplicationInfo(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetApplicationInfo"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetApplicationInfo"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "GetApplicationInfo"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorgetApplicationInfo(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetApplicationInfo(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetApplicationInfo(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetApplicationInfo(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetApplicationInfo(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetApplicationInfo(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetApplicationInfo(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetApplicationInfo(f);
			    }
			} else {
			    callback.receiveErrorgetApplicationInfo(f);
			}
		    } else {
			callback.receiveErrorgetApplicationInfo(f);
		    }
		} else {
		    callback.receiveErrorgetApplicationInfo(error);
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
		    callback.receiveErrorgetApplicationInfo(axisFault);
		}
	    }
	});
	org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
	if (_operations[4].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
	    _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
	    _operations[4].setMessageReceiver(_callbackReceiver);
	}
	//execute the operation client
	_operationClient.execute(false);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#getSecurityList
     * @param getSecurityListRequest10
     */
    public it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse getSecurityList(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest getSecurityListRequest10)
	    throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
	    _operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/GetSecurityListRequest");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getSecurityListRequest10,
		    optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getSecurityList")),
		    new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getSecurityList"));
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
		    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetSecurityList"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetSecurityList"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "GetSecurityList"));
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#startgetSecurityList
     * @param getSecurityListRequest10
     */
    public void startgetSecurityList(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest getSecurityListRequest10,
	    final it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
	_operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/GetSecurityListRequest");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getSecurityListRequest10,
		optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getSecurityList")),
		new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "getSecurityList"));
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
			    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse.class,
			    getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultgetSecurityList((it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorgetSecurityList(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetSecurityList"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetSecurityList"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "GetSecurityList"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorgetSecurityList(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetSecurityList(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetSecurityList(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetSecurityList(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetSecurityList(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetSecurityList(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetSecurityList(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorgetSecurityList(f);
			    }
			} else {
			    callback.receiveErrorgetSecurityList(f);
			}
		    } else {
			callback.receiveErrorgetSecurityList(f);
		    }
		} else {
		    callback.receiveErrorgetSecurityList(error);
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
		    callback.receiveErrorgetSecurityList(axisFault);
		}
	    }
	});
	org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
	if (_operations[5].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
	    _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
	    _operations[5].setMessageReceiver(_callbackReceiver);
	}
	//execute the operation client
	_operationClient.execute(false);
    }

    /**
     * Auto generated method signature
     * 
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#checkToken
     * @param checkTokenRequest12
     */
    public it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse checkToken(
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest checkTokenRequest12) throws java.rmi.RemoteException {

	org.apache.axis2.context.MessageContext _messageContext = null;
	try {
	    org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
	    _operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/CheckTokenRequest");
	    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	    addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	    // create a message context
	    _messageContext = new org.apache.axis2.context.MessageContext();
	    // create SOAP envelope with that payload
	    org.apache.axiom.soap.SOAPEnvelope env = null;
	    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), checkTokenRequest12,
		    optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "checkToken")),
		    new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "checkToken"));
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
		    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse.class, getEnvelopeNamespaces(_returnEnv));
	    return (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse) object;
	} catch (org.apache.axis2.AxisFault f) {
	    org.apache.axiom.om.OMElement faultElt = f.getDetail();
	    if (faultElt != null) {
		if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "CheckToken"))) {
		    //make the fault by reflection
		    try {
			java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
				.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "CheckToken"));
			java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
			java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
			//message class
			java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt
				.getQName(), "CheckToken"));
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
     * @see it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService#startcheckToken
     * @param checkTokenRequest12
     */
    public void startcheckToken(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest checkTokenRequest12,
	    final it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceCallbackHandler callback) throws java.rmi.RemoteException {

	org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
	_operationClient.getOptions().setAction("http://sigeprosecurity.gruppoinit.it/ws/sigeproSecurity/CheckTokenRequest");
	_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
	addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
	// create SOAP envelope with that payload
	org.apache.axiom.soap.SOAPEnvelope env = null;
	final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
	//Style is Doc.
	env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), checkTokenRequest12,
		optimizeContent(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "checkToken")),
		new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/ws", "checkToken"));
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
			    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse.class, getEnvelopeNamespaces(resultEnv));
		    callback.receiveResultcheckToken((it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse) object);
		} catch (org.apache.axis2.AxisFault e) {
		    callback.receiveErrorcheckToken(e);
		}
	    }

	    public void onError(java.lang.Exception error) {

		if (error instanceof org.apache.axis2.AxisFault) {
		    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
		    org.apache.axiom.om.OMElement faultElt = f.getDetail();
		    if (faultElt != null) {
			if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "CheckToken"))) {
			    //make the fault by reflection
			    try {
				java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
					.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "CheckToken"));
				java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
				java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
				//message class
				java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
					faultElt.getQName(), "CheckToken"));
				java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
				java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
				java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
				m.invoke(ex, new java.lang.Object[] { messageObject });
				callback.receiveErrorcheckToken(new java.rmi.RemoteException(ex.getMessage(), ex));
			    } catch (java.lang.ClassCastException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorcheckToken(f);
			    } catch (java.lang.ClassNotFoundException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorcheckToken(f);
			    } catch (java.lang.NoSuchMethodException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorcheckToken(f);
			    } catch (java.lang.reflect.InvocationTargetException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorcheckToken(f);
			    } catch (java.lang.IllegalAccessException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorcheckToken(f);
			    } catch (java.lang.InstantiationException e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorcheckToken(f);
			    } catch (org.apache.axis2.AxisFault e) {
				// we cannot intantiate the class - throw the original Axis fault
				callback.receiveErrorcheckToken(f);
			    }
			} else {
			    callback.receiveErrorcheckToken(f);
			}
		    } else {
			callback.receiveErrorcheckToken(f);
		    }
		} else {
		    callback.receiveErrorcheckToken(error);
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
		    callback.receiveErrorcheckToken(axisFault);
		}
	    }
	});
	org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
	if (_operations[6].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
	    _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
	    _operations[6].setMessageReceiver(_callbackReceiver);
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

    //http://devel9:8080/sigeprosecurity/services
    public static class ContestoType implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"ContestoType", "ns1");
	/**
	 * field for ContestoType
	 */
	protected java.lang.String localContestoType;
	private static java.util.HashMap _table_ = new java.util.HashMap();

	// Constructor
	protected ContestoType(java.lang.String value, boolean isRegisterValue) {

	    localContestoType = value;
	    if (isRegisterValue) {
		_table_.put(localContestoType, this);
	    }
	}

	public static final java.lang.String _AMM = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("AMM");
	public static final java.lang.String _APP = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("APP");
	public static final java.lang.String _OPE = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("OPE");
	public static final java.lang.String _UTE = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("UTE");
	public static final ContestoType AMM = new ContestoType(_AMM, true);
	public static final ContestoType APP = new ContestoType(_APP, true);
	public static final ContestoType OPE = new ContestoType(_OPE, true);
	public static final ContestoType UTE = new ContestoType(_UTE, true);

	public java.lang.String getValue() {

	    return localContestoType;
	}

	public boolean equals(java.lang.Object obj) {

	    return (obj == this);
	}

	public int hashCode() {

	    return toString().hashCode();
	}

	public java.lang.String toString() {

	    return localContestoType.toString();
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":ContestoType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "ContestoType", xmlWriter);
		}
	    }
	    if (localContestoType == null) {
		throw new org.apache.axis2.databinding.ADBException("ContestoType cannot be null !!");
	    } else {
		xmlWriter.writeCharacters(localContestoType);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
		    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContestoType) }, null);
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    public static ContestoType fromValue(java.lang.String value) throws java.lang.IllegalArgumentException {

		ContestoType enumeration = (ContestoType) _table_.get(value);
		if ((enumeration == null) && !((value == null) || (value.equals("")))) {
		    throw new java.lang.IllegalArgumentException();
		}
		return enumeration;
	    }

	    public static ContestoType fromString(java.lang.String value, java.lang.String namespaceURI) throws java.lang.IllegalArgumentException {

		try {
		    return fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));
		} catch (java.lang.Exception e) {
		    throw new java.lang.IllegalArgumentException();
		}
	    }

	    public static ContestoType fromString(javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content) {

		if (content.indexOf(":") > -1) {
		    java.lang.String prefix = content.substring(0, content.indexOf(":"));
		    java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
		    return ContestoType.Factory.fromString(content, namespaceUri);
		} else {
		    return ContestoType.Factory.fromString(content, "");
		}
	    }

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static ContestoType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		ContestoType object = null;
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
				object = ContestoType.Factory.fromString(content, namespaceuri);
			    } else {
				// this seems to be not a qname send and empty namespace incase of it is
				// check is done in fromString method
				object = ContestoType.Factory.fromString(content, "");
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

    public static class GetSecurityListResponse implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"GetSecurityListResponse", "ns1");
	/**
	 * field for Security This was an Array!
	 */
	protected SecurityListType[] localSecurity;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localSecurityTracker = false;

	public boolean isSecuritySpecified() {

	    return localSecurityTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return SecurityListType[]
	 */
	public SecurityListType[] getSecurity() {

	    return localSecurity;
	}

	/**
	 * validate the array for Security
	 */
	protected void validateSecurity(SecurityListType[] param) {

	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Security
	 */
	public void setSecurity(SecurityListType[] param) {

	    validateSecurity(param);
	    localSecurityTracker = param != null;
	    this.localSecurity = param;
	}

	/**
	 * Auto generated add method for the array for convenience
	 * 
	 * @param param
	 *            SecurityListType
	 */
	public void addSecurity(SecurityListType param) {

	    if (localSecurity == null) {
		localSecurity = new SecurityListType[] {};
	    }
	    //update the setting tracker
	    localSecurityTracker = true;
	    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localSecurity);
	    list.add(param);
	    this.localSecurity = (SecurityListType[]) list.toArray(new SecurityListType[list.size()]);
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":GetSecurityListResponse",
			    xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetSecurityListResponse", xmlWriter);
		}
	    }
	    if (localSecurityTracker) {
		if (localSecurity != null) {
		    for (int i = 0; i < localSecurity.length; i++) {
			if (localSecurity[i] != null) {
			    localSecurity[i].serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "security"),
				    xmlWriter);
			} else {
			    // we don't have to do any thing since minOccures is zero
			}
		    }
		} else {
		    throw new org.apache.axis2.databinding.ADBException("security cannot be null!!");
		}
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    if (localSecurityTracker) {
		if (localSecurity != null) {
		    for (int i = 0; i < localSecurity.length; i++) {
			if (localSecurity[i] != null) {
			    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "security"));
			    elementList.add(localSecurity[i]);
			} else {
			    // nothing to do
			}
		    }
		} else {
		    throw new org.apache.axis2.databinding.ADBException("security cannot be null!!");
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
	    public static GetSecurityListResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		GetSecurityListResponse object = new GetSecurityListResponse();
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
			    if (!"GetSecurityListResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (GetSecurityListResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "security").equals(reader.getName())) {
			// Process the array and step past its final element's end.
			list1.add(SecurityListType.Factory.parse(reader));
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
				if (new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "security").equals(reader.getName())) {
				    list1.add(SecurityListType.Factory.parse(reader));
				} else {
				    loopDone1 = true;
				}
			    }
			}
			// call the converter utility  to convert and set the array
			object.setSecurity((SecurityListType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
				SecurityListType.class, list1));
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

    public static class AmbienteType implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"AmbienteType", "ns1");
	/**
	 * field for AmbienteType
	 */
	protected java.lang.String localAmbienteType;
	private static java.util.HashMap _table_ = new java.util.HashMap();

	// Constructor
	protected AmbienteType(java.lang.String value, boolean isRegisterValue) {

	    localAmbienteType = value;
	    if (isRegisterValue) {
		_table_.put(localAmbienteType, this);
	    }
	}

	public static final java.lang.String _ASP = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("ASP");
	public static final java.lang.String _DOTNET = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("DOTNET");
	public static final java.lang.String _JAVA = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("JAVA");
	public static final AmbienteType ASP = new AmbienteType(_ASP, true);
	public static final AmbienteType DOTNET = new AmbienteType(_DOTNET, true);
	public static final AmbienteType JAVA = new AmbienteType(_JAVA, true);

	public java.lang.String getValue() {

	    return localAmbienteType;
	}

	public boolean equals(java.lang.Object obj) {

	    return (obj == this);
	}

	public int hashCode() {

	    return toString().hashCode();
	}

	public java.lang.String toString() {

	    return localAmbienteType.toString();
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":AmbienteType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "AmbienteType", xmlWriter);
		}
	    }
	    if (localAmbienteType == null) {
		throw new org.apache.axis2.databinding.ADBException("AmbienteType cannot be null !!");
	    } else {
		xmlWriter.writeCharacters(localAmbienteType);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
		    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmbienteType) }, null);
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    public static AmbienteType fromValue(java.lang.String value) throws java.lang.IllegalArgumentException {

		AmbienteType enumeration = (AmbienteType) _table_.get(value);
		if ((enumeration == null) && !((value == null) || (value.equals("")))) {
		    throw new java.lang.IllegalArgumentException();
		}
		return enumeration;
	    }

	    public static AmbienteType fromString(java.lang.String value, java.lang.String namespaceURI) throws java.lang.IllegalArgumentException {

		try {
		    return fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));
		} catch (java.lang.Exception e) {
		    throw new java.lang.IllegalArgumentException();
		}
	    }

	    public static AmbienteType fromString(javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content) {

		if (content.indexOf(":") > -1) {
		    java.lang.String prefix = content.substring(0, content.indexOf(":"));
		    java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
		    return AmbienteType.Factory.fromString(content, namespaceUri);
		} else {
		    return AmbienteType.Factory.fromString(content, "");
		}
	    }

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static AmbienteType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		AmbienteType object = null;
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
				object = AmbienteType.Factory.fromString(content, namespaceuri);
			    } else {
				// this seems to be not a qname send and empty namespace incase of it is
				// check is done in fromString method
				object = AmbienteType.Factory.fromString(content, "");
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

    public static class SecurityListType implements org.apache.axis2.databinding.ADBBean {

	/* This type was generated from the piece of schema that had
	        name = SecurityListType
	        Namespace URI = http://sigeprosecurity.gruppoinit.it/schema
	        Namespace Prefix = ns1
	        */
	/**
	 * field for Alias
	 */
	protected java.lang.String localAlias;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlias() {

	    return localAlias;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Alias
	 */
	public void setAlias(java.lang.String param) {

	    this.localAlias = param;
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
	 * field for Attivo
	 */
	protected boolean localAttivo;

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getAttivo() {

	    return localAttivo;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Attivo
	 */
	public void setAttivo(boolean param) {

	    this.localAttivo = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":SecurityListType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "SecurityListType", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "alias", xmlWriter);
	    if (localAlias == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localAlias);
	    }
	    xmlWriter.writeEndElement();
	    if (localDescrizioneTracker) {
		namespace = "http://sigeprosecurity.gruppoinit.it/schema";
		writeStartElement(null, namespace, "descrizione", xmlWriter);
		if (localDescrizione == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("descrizione cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localDescrizione);
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "attivo", xmlWriter);
	    if (false) {
		throw new org.apache.axis2.databinding.ADBException("attivo cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAttivo));
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias"));
	    if (localAlias != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlias));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    }
	    if (localDescrizioneTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "descrizione"));
		if (localDescrizione != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescrizione));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("descrizione cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "attivo"));
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAttivo));
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
	    public static SecurityListType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		SecurityListType object = new SecurityListType();
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
			    if (!"SecurityListType".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (SecurityListType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAlias(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "descrizione").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setDescrizione(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "attivo").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAttivo(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
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

    public static class ComunisecurityAttiviType implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"ComunisecurityAttiviType", "ns1");
	/**
	 * field for ComunisecurityAttiviType
	 */
	protected java.lang.String localComunisecurityAttiviType;
	private static java.util.HashMap _table_ = new java.util.HashMap();

	// Constructor
	protected ComunisecurityAttiviType(java.lang.String value, boolean isRegisterValue) {

	    localComunisecurityAttiviType = value;
	    if (isRegisterValue) {
		_table_.put(localComunisecurityAttiviType, this);
	    }
	}

	public static final java.lang.String _TUTTI = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("TUTTI");
	public static final java.lang.String _ATTIVI = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("ATTIVI");
	public static final java.lang.String _DISATTIVATI = org.apache.axis2.databinding.utils.ConverterUtil.convertToString("DISATTIVATI");
	public static final ComunisecurityAttiviType TUTTI = new ComunisecurityAttiviType(_TUTTI, true);
	public static final ComunisecurityAttiviType ATTIVI = new ComunisecurityAttiviType(_ATTIVI, true);
	public static final ComunisecurityAttiviType DISATTIVATI = new ComunisecurityAttiviType(_DISATTIVATI, true);

	public java.lang.String getValue() {

	    return localComunisecurityAttiviType;
	}

	public boolean equals(java.lang.Object obj) {

	    return (obj == this);
	}

	public int hashCode() {

	    return toString().hashCode();
	}

	public java.lang.String toString() {

	    return localComunisecurityAttiviType.toString();
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":ComunisecurityAttiviType",
			    xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "ComunisecurityAttiviType", xmlWriter);
		}
	    }
	    if (localComunisecurityAttiviType == null) {
		throw new org.apache.axis2.databinding.ADBException("ComunisecurityAttiviType cannot be null !!");
	    } else {
		xmlWriter.writeCharacters(localComunisecurityAttiviType);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
		    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localComunisecurityAttiviType) }, null);
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

	    public static ComunisecurityAttiviType fromValue(java.lang.String value) throws java.lang.IllegalArgumentException {

		ComunisecurityAttiviType enumeration = (ComunisecurityAttiviType) _table_.get(value);
		if ((enumeration == null) && !((value == null) || (value.equals("")))) {
		    throw new java.lang.IllegalArgumentException();
		}
		return enumeration;
	    }

	    public static ComunisecurityAttiviType fromString(java.lang.String value, java.lang.String namespaceURI)
		    throws java.lang.IllegalArgumentException {

		try {
		    return fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));
		} catch (java.lang.Exception e) {
		    throw new java.lang.IllegalArgumentException();
		}
	    }

	    public static ComunisecurityAttiviType fromString(javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content) {

		if (content.indexOf(":") > -1) {
		    java.lang.String prefix = content.substring(0, content.indexOf(":"));
		    java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
		    return ComunisecurityAttiviType.Factory.fromString(content, namespaceUri);
		} else {
		    return ComunisecurityAttiviType.Factory.fromString(content, "");
		}
	    }

	    /**
	     * static method to create the object Precondition: If this object is an element, the current or next start
	     * element starts this object and any intervening reader events are ignorable If this object is not an
	     * element, it is a complex type and the reader is at the event just after the outer start element
	     * Postcondition: If this object is an element, the reader is positioned at its end element If this object
	     * is a complex type, the reader is positioned at the end element of its outer element
	     */
	    public static ComunisecurityAttiviType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		ComunisecurityAttiviType object = null;
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
				object = ComunisecurityAttiviType.Factory.fromString(content, namespaceuri);
			    } else {
				// this seems to be not a qname send and empty namespace incase of it is
				// check is done in fromString method
				object = ComunisecurityAttiviType.Factory.fromString(content, "");
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

    public static class GetDbConnectionInfoResponse implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"GetDbConnectionInfoResponse", "ns1");
	/**
	 * field for Alias
	 */
	protected java.lang.String localAlias;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlias() {

	    return localAlias;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Alias
	 */
	public void setAlias(java.lang.String param) {

	    this.localAlias = param;
	}

	/**
	 * field for IdComune
	 */
	protected java.lang.String localIdComune;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localIdComuneTracker = false;

	public boolean isIdComuneSpecified() {

	    return localIdComuneTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getIdComune() {

	    return localIdComune;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            IdComune
	 */
	public void setIdComune(java.lang.String param) {

	    localIdComuneTracker = param != null;
	    this.localIdComune = param;
	}

	/**
	 * field for ConnectionString
	 */
	protected java.lang.String localConnectionString;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getConnectionString() {

	    return localConnectionString;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ConnectionString
	 */
	public void setConnectionString(java.lang.String param) {

	    this.localConnectionString = param;
	}

	/**
	 * field for DbUser
	 */
	protected java.lang.String localDbUser;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDbUser() {

	    return localDbUser;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            DbUser
	 */
	public void setDbUser(java.lang.String param) {

	    this.localDbUser = param;
	}

	/**
	 * field for DbPassword
	 */
	protected java.lang.String localDbPassword;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDbPassword() {

	    return localDbPassword;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            DbPassword
	 */
	public void setDbPassword(java.lang.String param) {

	    this.localDbPassword = param;
	}

	/**
	 * field for Provider
	 */
	protected java.lang.String localProvider;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localProviderTracker = false;

	public boolean isProviderSpecified() {

	    return localProviderTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getProvider() {

	    return localProvider;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Provider
	 */
	public void setProvider(java.lang.String param) {

	    localProviderTracker = param != null;
	    this.localProvider = param;
	}

	/**
	 * field for DbOwner
	 */
	protected java.lang.String localDbOwner;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDbOwner() {

	    return localDbOwner;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            DbOwner
	 */
	public void setDbOwner(java.lang.String param) {

	    this.localDbOwner = param;
	}

	/**
	 * field for DbMsName
	 */
	protected java.lang.String localDbMsName;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localDbMsNameTracker = false;

	public boolean isDbMsNameSpecified() {

	    return localDbMsNameTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDbMsName() {

	    return localDbMsName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            DbMsName
	 */
	public void setDbMsName(java.lang.String param) {

	    localDbMsNameTracker = param != null;
	    this.localDbMsName = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":GetDbConnectionInfoResponse",
			    xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetDbConnectionInfoResponse", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "alias", xmlWriter);
	    if (localAlias == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localAlias);
	    }
	    xmlWriter.writeEndElement();
	    if (localIdComuneTracker) {
		namespace = "http://sigeprosecurity.gruppoinit.it/schema";
		writeStartElement(null, namespace, "idComune", xmlWriter);
		if (localIdComune == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("idComune cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localIdComune);
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "connectionString", xmlWriter);
	    if (localConnectionString == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("connectionString cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localConnectionString);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "dbUser", xmlWriter);
	    if (localDbUser == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("dbUser cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localDbUser);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "dbPassword", xmlWriter);
	    if (localDbPassword == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("dbPassword cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localDbPassword);
	    }
	    xmlWriter.writeEndElement();
	    if (localProviderTracker) {
		namespace = "http://sigeprosecurity.gruppoinit.it/schema";
		writeStartElement(null, namespace, "provider", xmlWriter);
		if (localProvider == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("provider cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localProvider);
		}
		xmlWriter.writeEndElement();
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "dbOwner", xmlWriter);
	    if (localDbOwner == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("dbOwner cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localDbOwner);
	    }
	    xmlWriter.writeEndElement();
	    if (localDbMsNameTracker) {
		namespace = "http://sigeprosecurity.gruppoinit.it/schema";
		writeStartElement(null, namespace, "dbMsName", xmlWriter);
		if (localDbMsName == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("dbMsName cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localDbMsName);
		}
		xmlWriter.writeEndElement();
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias"));
	    if (localAlias != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlias));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    }
	    if (localIdComuneTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "idComune"));
		if (localIdComune != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdComune));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("idComune cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "connectionString"));
	    if (localConnectionString != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localConnectionString));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("connectionString cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbUser"));
	    if (localDbUser != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDbUser));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("dbUser cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbPassword"));
	    if (localDbPassword != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDbPassword));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("dbPassword cannot be null!!");
	    }
	    if (localProviderTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "provider"));
		if (localProvider != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProvider));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("provider cannot be null!!");
		}
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbOwner"));
	    if (localDbOwner != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDbOwner));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("dbOwner cannot be null!!");
	    }
	    if (localDbMsNameTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbMsName"));
		if (localDbMsName != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDbMsName));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("dbMsName cannot be null!!");
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
	    public static GetDbConnectionInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		GetDbConnectionInfoResponse object = new GetDbConnectionInfoResponse();
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
			    if (!"GetDbConnectionInfoResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (GetDbConnectionInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAlias(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "idComune").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setIdComune(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "connectionString").equals(reader
				    .getName())) {
			java.lang.String content = reader.getElementText();
			object.setConnectionString(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbUser").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setDbUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbPassword").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setDbPassword(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "provider").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setProvider(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbOwner").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setDbOwner(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "dbMsName").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setDbMsName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class LoginSSORequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"LoginSSORequest", "ns1");
	/**
	 * field for Alias
	 */
	protected java.lang.String localAlias;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlias() {

	    return localAlias;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Alias
	 */
	public void setAlias(java.lang.String param) {

	    this.localAlias = param;
	}

	/**
	 * field for Contesto
	 */
	protected ContestoType localContesto;

	/**
	 * Auto generated getter method
	 * 
	 * @return ContestoType
	 */
	public ContestoType getContesto() {

	    return localContesto;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Contesto
	 */
	public void setContesto(ContestoType param) {

	    this.localContesto = param;
	}

	/**
	 * field for Username
	 */
	protected java.lang.String localUsername;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUsername() {

	    return localUsername;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Username
	 */
	public void setUsername(java.lang.String param) {

	    this.localUsername = param;
	}

	/**
	 * field for IpAddress
	 */
	protected java.lang.String localIpAddress;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getIpAddress() {

	    return localIpAddress;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            IpAddress
	 */
	public void setIpAddress(java.lang.String param) {

	    this.localIpAddress = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":LoginSSORequest", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "LoginSSORequest", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "alias", xmlWriter);
	    if (localAlias == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localAlias);
	    }
	    xmlWriter.writeEndElement();
	    if (localContesto == null) {
		throw new org.apache.axis2.databinding.ADBException("contesto cannot be null!!");
	    }
	    localContesto.serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto"), xmlWriter);
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "username", xmlWriter);
	    if (localUsername == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localUsername);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "ipAddress", xmlWriter);
	    if (localIpAddress == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("ipAddress cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localIpAddress);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias"));
	    if (localAlias != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlias));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto"));
	    if (localContesto == null) {
		throw new org.apache.axis2.databinding.ADBException("contesto cannot be null!!");
	    }
	    elementList.add(localContesto);
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "username"));
	    if (localUsername != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUsername));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "ipAddress"));
	    if (localIpAddress != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIpAddress));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("ipAddress cannot be null!!");
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
	    public static LoginSSORequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		LoginSSORequest object = new LoginSSORequest();
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
			    if (!"LoginSSORequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (LoginSSORequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAlias(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto").equals(reader.getName())) {
			object.setContesto(ContestoType.Factory.parse(reader));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "username").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setUsername(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "ipAddress").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setIpAddress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class CheckTokenResponse implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"CheckTokenResponse", "ns1");
	/**
	 * field for Valid
	 */
	protected boolean localValid;

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getValid() {

	    return localValid;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Valid
	 */
	public void setValid(boolean param) {

	    this.localValid = param;
	}

	/**
	 * field for TokenInfo
	 */
	protected TokenInfoType localTokenInfo;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localTokenInfoTracker = false;

	public boolean isTokenInfoSpecified() {

	    return localTokenInfoTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return TokenInfoType
	 */
	public TokenInfoType getTokenInfo() {

	    return localTokenInfo;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            TokenInfo
	 */
	public void setTokenInfo(TokenInfoType param) {

	    localTokenInfoTracker = param != null;
	    this.localTokenInfo = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":CheckTokenResponse", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "CheckTokenResponse", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "valid", xmlWriter);
	    if (false) {
		throw new org.apache.axis2.databinding.ADBException("valid cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValid));
	    }
	    xmlWriter.writeEndElement();
	    if (localTokenInfoTracker) {
		if (localTokenInfo == null) {
		    throw new org.apache.axis2.databinding.ADBException("tokenInfo cannot be null!!");
		}
		localTokenInfo.serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tokenInfo"), xmlWriter);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "valid"));
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValid));
	    if (localTokenInfoTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tokenInfo"));
		if (localTokenInfo == null) {
		    throw new org.apache.axis2.databinding.ADBException("tokenInfo cannot be null!!");
		}
		elementList.add(localTokenInfo);
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
	    public static CheckTokenResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		CheckTokenResponse object = new CheckTokenResponse();
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
			    if (!"CheckTokenResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (CheckTokenResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "valid").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setValid(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tokenInfo").equals(reader.getName())) {
			object.setTokenInfo(TokenInfoType.Factory.parse(reader));
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

    public static class ApplicationInfoType implements org.apache.axis2.databinding.ADBBean {

	/* This type was generated from the piece of schema that had
	        name = ApplicationInfoType
	        Namespace URI = http://sigeprosecurity.gruppoinit.it/schema
	        Namespace Prefix = ns1
	        */
	/**
	 * field for Param
	 */
	protected java.lang.String localParam;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParam() {

	    return localParam;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Param
	 */
	public void setParam(java.lang.String param) {

	    this.localParam = param;
	}

	/**
	 * field for Value
	 */
	protected java.lang.String localValue;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localValueTracker = false;

	public boolean isValueSpecified() {

	    return localValueTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getValue() {

	    return localValue;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Value
	 */
	public void setValue(java.lang.String param) {

	    localValueTracker = param != null;
	    this.localValue = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":ApplicationInfoType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "ApplicationInfoType", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "param", xmlWriter);
	    if (localParam == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("param cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localParam);
	    }
	    xmlWriter.writeEndElement();
	    if (localValueTracker) {
		namespace = "http://sigeprosecurity.gruppoinit.it/schema";
		writeStartElement(null, namespace, "value", xmlWriter);
		if (localValue == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("value cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localValue);
		}
		xmlWriter.writeEndElement();
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "param"));
	    if (localParam != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localParam));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("param cannot be null!!");
	    }
	    if (localValueTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "value"));
		if (localValue != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValue));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("value cannot be null!!");
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
	    public static ApplicationInfoType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		ApplicationInfoType object = new ApplicationInfoType();
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
			    if (!"ApplicationInfoType".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (ApplicationInfoType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "param").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setParam(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "value").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class GetSecurityListRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"GetSecurityListRequest", "ns1");
	/**
	 * field for Alias
	 */
	protected java.lang.String localAlias;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localAliasTracker = false;

	public boolean isAliasSpecified() {

	    return localAliasTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlias() {

	    return localAlias;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Alias
	 */
	public void setAlias(java.lang.String param) {

	    localAliasTracker = param != null;
	    this.localAlias = param;
	}

	/**
	 * field for Tipo
	 */
	protected ComunisecurityAttiviType localTipo;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localTipoTracker = false;

	public boolean isTipoSpecified() {

	    return localTipoTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return ComunisecurityAttiviType
	 */
	public ComunisecurityAttiviType getTipo() {

	    return localTipo;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Tipo
	 */
	public void setTipo(ComunisecurityAttiviType param) {

	    localTipoTracker = param != null;
	    this.localTipo = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":GetSecurityListRequest", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetSecurityListRequest", xmlWriter);
		}
	    }
	    if (localAliasTracker) {
		namespace = "http://sigeprosecurity.gruppoinit.it/schema";
		writeStartElement(null, namespace, "alias", xmlWriter);
		if (localAlias == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localAlias);
		}
		xmlWriter.writeEndElement();
	    }
	    if (localTipoTracker) {
		if (localTipo == null) {
		    throw new org.apache.axis2.databinding.ADBException("tipo cannot be null!!");
		}
		localTipo.serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tipo"), xmlWriter);
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    if (localAliasTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias"));
		if (localAlias != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlias));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
		}
	    }
	    if (localTipoTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tipo"));
		if (localTipo == null) {
		    throw new org.apache.axis2.databinding.ADBException("tipo cannot be null!!");
		}
		elementList.add(localTipo);
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
	    public static GetSecurityListRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		GetSecurityListRequest object = new GetSecurityListRequest();
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
			    if (!"GetSecurityListRequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (GetSecurityListRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAlias(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tipo").equals(reader.getName())) {
			object.setTipo(ComunisecurityAttiviType.Factory.parse(reader));
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

    public static class LogoutRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"LogoutRequest", "ns1");
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":LogoutRequest", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "LogoutRequest", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "token", xmlWriter);
	    if (localToken == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localToken);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
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
	    public static LogoutRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		LogoutRequest object = new LogoutRequest();
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
			    if (!"LogoutRequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (LogoutRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token").equals(reader.getName())) {
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

    public static class LoginResponse extends TokenResultType implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"LoginResponse", "ns1");

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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":LoginResponse", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "LoginResponse", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "token", xmlWriter);
	    if (localToken == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localToken);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    attribList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
	    attribList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "LoginResponse"));
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
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
	    public static LoginResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		LoginResponse object = new LoginResponse();
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
			    if (!"LoginResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (LoginResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
			    }
			}
		    }
		    // Note all attributes that were handled. Used to differ normal attributes
		    // from anyAttributes.
		    java.util.Vector handledAttributes = new java.util.Vector();
		    reader.next();
		    while (!reader.isEndElement()) {
			if (reader.isStartElement()) {
			    if (reader.isStartElement()
				    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token").equals(reader.getName())) {
				java.lang.String content = reader.getElementText();
				object.setToken(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
				reader.next();
			    } // End of if for expected property start element
			    else {
				// A start element we are not expecting indicates an invalid parameter was passed
				throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
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

    public static class LogoutResponse implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"LogoutResponse", "ns1");
	/**
	 * field for Success
	 */
	protected boolean localSuccess;

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getSuccess() {

	    return localSuccess;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Success
	 */
	public void setSuccess(boolean param) {

	    this.localSuccess = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":LogoutResponse", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "LogoutResponse", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "success", xmlWriter);
	    if (false) {
		throw new org.apache.axis2.databinding.ADBException("success cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSuccess));
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "success"));
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSuccess));
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
	    public static LogoutResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		LogoutResponse object = new LogoutResponse();
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
			    if (!"LogoutResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (LogoutResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "success").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setSuccess(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
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

    public static class ExtensionMapper {

	public static java.lang.Object getTypeObject(java.lang.String namespaceURI, java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
		throws java.lang.Exception {

	    if ("http://sigeprosecurity.gruppoinit.it/schema".equals(namespaceURI) && "ContestoType".equals(typeName)) {
		return ContestoType.Factory.parse(reader);
	    }
	    if ("http://sigeprosecurity.gruppoinit.it/schema".equals(namespaceURI) && "AmbienteType".equals(typeName)) {
		return AmbienteType.Factory.parse(reader);
	    }
	    if ("http://sigeprosecurity.gruppoinit.it/schema".equals(namespaceURI) && "SecurityListType".equals(typeName)) {
		return SecurityListType.Factory.parse(reader);
	    }
	    if ("http://sigeprosecurity.gruppoinit.it/schema".equals(namespaceURI) && "ComunisecurityAttiviType".equals(typeName)) {
		return ComunisecurityAttiviType.Factory.parse(reader);
	    }
	    if ("http://sigeprosecurity.gruppoinit.it/schema".equals(namespaceURI) && "ApplicationInfoType".equals(typeName)) {
		return ApplicationInfoType.Factory.parse(reader);
	    }
	    if ("http://sigeprosecurity.gruppoinit.it/schema".equals(namespaceURI) && "TokenInfoType".equals(typeName)) {
		return TokenInfoType.Factory.parse(reader);
	    }
	    if ("http://sigeprosecurity.gruppoinit.it/schema".equals(namespaceURI) && "TokenResultType".equals(typeName)) {
		return TokenResultType.Factory.parse(reader);
	    }
	    throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
	}
    }

    public static class GetApplicationInfoRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"GetApplicationInfoRequest", "ns1");
	/**
	 * field for Param
	 */
	protected java.lang.String localParam;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localParamTracker = false;

	public boolean isParamSpecified() {

	    return localParamTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParam() {

	    return localParam;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Param
	 */
	public void setParam(java.lang.String param) {

	    localParamTracker = param != null;
	    this.localParam = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":GetApplicationInfoRequest",
			    xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetApplicationInfoRequest", xmlWriter);
		}
	    }
	    if (localParamTracker) {
		namespace = "http://sigeprosecurity.gruppoinit.it/schema";
		writeStartElement(null, namespace, "param", xmlWriter);
		if (localParam == null) {
		    // write the nil attribute
		    throw new org.apache.axis2.databinding.ADBException("param cannot be null!!");
		} else {
		    xmlWriter.writeCharacters(localParam);
		}
		xmlWriter.writeEndElement();
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    if (localParamTracker) {
		elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "param"));
		if (localParam != null) {
		    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localParam));
		} else {
		    throw new org.apache.axis2.databinding.ADBException("param cannot be null!!");
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
	    public static GetApplicationInfoRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		GetApplicationInfoRequest object = new GetApplicationInfoRequest();
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
			    if (!"GetApplicationInfoRequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (GetApplicationInfoRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "param").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setParam(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class LoginRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"LoginRequest", "ns1");
	/**
	 * field for Alias
	 */
	protected java.lang.String localAlias;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlias() {

	    return localAlias;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Alias
	 */
	public void setAlias(java.lang.String param) {

	    this.localAlias = param;
	}

	/**
	 * field for Contesto
	 */
	protected ContestoType localContesto;

	/**
	 * Auto generated getter method
	 * 
	 * @return ContestoType
	 */
	public ContestoType getContesto() {

	    return localContesto;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Contesto
	 */
	public void setContesto(ContestoType param) {

	    this.localContesto = param;
	}

	/**
	 * field for Username
	 */
	protected java.lang.String localUsername;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUsername() {

	    return localUsername;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Username
	 */
	public void setUsername(java.lang.String param) {

	    this.localUsername = param;
	}

	/**
	 * field for Password
	 */
	protected java.lang.String localPassword;

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

	    this.localPassword = param;
	}

	/**
	 * field for IpAddress
	 */
	protected java.lang.String localIpAddress;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getIpAddress() {

	    return localIpAddress;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            IpAddress
	 */
	public void setIpAddress(java.lang.String param) {

	    this.localIpAddress = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":LoginRequest", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "LoginRequest", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "alias", xmlWriter);
	    if (localAlias == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localAlias);
	    }
	    xmlWriter.writeEndElement();
	    if (localContesto == null) {
		throw new org.apache.axis2.databinding.ADBException("contesto cannot be null!!");
	    }
	    localContesto.serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto"), xmlWriter);
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "username", xmlWriter);
	    if (localUsername == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localUsername);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "password", xmlWriter);
	    if (localPassword == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localPassword);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "ipAddress", xmlWriter);
	    if (localIpAddress == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("ipAddress cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localIpAddress);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias"));
	    if (localAlias != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlias));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto"));
	    if (localContesto == null) {
		throw new org.apache.axis2.databinding.ADBException("contesto cannot be null!!");
	    }
	    elementList.add(localContesto);
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "username"));
	    if (localUsername != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUsername));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "password"));
	    if (localPassword != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "ipAddress"));
	    if (localIpAddress != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIpAddress));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("ipAddress cannot be null!!");
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
	    public static LoginRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		LoginRequest object = new LoginRequest();
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
			    if (!"LoginRequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (LoginRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAlias(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto").equals(reader.getName())) {
			object.setContesto(ContestoType.Factory.parse(reader));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "username").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setUsername(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "password").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setPassword(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "ipAddress").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setIpAddress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class CheckTokenRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"CheckTokenRequest", "ns1");
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
	 * field for TokenInfo
	 */
	protected boolean localTokenInfo;

	/**
	 * Auto generated getter method
	 * 
	 * @return boolean
	 */
	public boolean getTokenInfo() {

	    return localTokenInfo;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            TokenInfo
	 */
	public void setTokenInfo(boolean param) {

	    this.localTokenInfo = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":CheckTokenRequest", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "CheckTokenRequest", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "token", xmlWriter);
	    if (localToken == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localToken);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "tokenInfo", xmlWriter);
	    if (false) {
		throw new org.apache.axis2.databinding.ADBException("tokenInfo cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTokenInfo));
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tokenInfo"));
	    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTokenInfo));
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
	    public static CheckTokenRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		CheckTokenRequest object = new CheckTokenRequest();
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
			    if (!"CheckTokenRequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (CheckTokenRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token").equals(reader.getName())) {
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "tokenInfo").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setTokenInfo(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
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

    public static class TokenInfoType implements org.apache.axis2.databinding.ADBBean {

	/* This type was generated from the piece of schema that had
	        name = TokenInfoType
	        Namespace URI = http://sigeprosecurity.gruppoinit.it/schema
	        Namespace Prefix = ns1
	        */
	/**
	 * field for Contesto
	 */
	protected ContestoType localContesto;

	/**
	 * Auto generated getter method
	 * 
	 * @return ContestoType
	 */
	public ContestoType getContesto() {

	    return localContesto;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Contesto
	 */
	public void setContesto(ContestoType param) {

	    this.localContesto = param;
	}

	/**
	 * field for ClientIp
	 */
	protected java.lang.String localClientIp;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getClientIp() {

	    return localClientIp;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ClientIp
	 */
	public void setClientIp(java.lang.String param) {

	    this.localClientIp = param;
	}

	/**
	 * field for Alias
	 */
	protected java.lang.String localAlias;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlias() {

	    return localAlias;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Alias
	 */
	public void setAlias(java.lang.String param) {

	    this.localAlias = param;
	}

	/**
	 * field for Idcomune
	 */
	protected java.lang.String localIdcomune;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getIdcomune() {

	    return localIdcomune;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Idcomune
	 */
	public void setIdcomune(java.lang.String param) {

	    this.localIdcomune = param;
	}

	/**
	 * field for Firstrequest
	 */
	protected java.lang.String localFirstrequest;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFirstrequest() {

	    return localFirstrequest;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Firstrequest
	 */
	public void setFirstrequest(java.lang.String param) {

	    this.localFirstrequest = param;
	}

	/**
	 * field for Lastrequest
	 */
	protected java.lang.String localLastrequest;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getLastrequest() {

	    return localLastrequest;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Lastrequest
	 */
	public void setLastrequest(java.lang.String param) {

	    this.localLastrequest = param;
	}

	/**
	 * field for Userid
	 */
	protected java.lang.String localUserid;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUserid() {

	    return localUserid;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Userid
	 */
	public void setUserid(java.lang.String param) {

	    this.localUserid = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":TokenInfoType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "TokenInfoType", xmlWriter);
		}
	    }
	    if (localContesto == null) {
		throw new org.apache.axis2.databinding.ADBException("contesto cannot be null!!");
	    }
	    localContesto.serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto"), xmlWriter);
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "clientIp", xmlWriter);
	    if (localClientIp == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("clientIp cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localClientIp);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "alias", xmlWriter);
	    if (localAlias == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localAlias);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "idcomune", xmlWriter);
	    if (localIdcomune == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("idcomune cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localIdcomune);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "firstrequest", xmlWriter);
	    if (localFirstrequest == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("firstrequest cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localFirstrequest);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "lastrequest", xmlWriter);
	    if (localLastrequest == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("lastrequest cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localLastrequest);
	    }
	    xmlWriter.writeEndElement();
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "userid", xmlWriter);
	    if (localUserid == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("userid cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localUserid);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto"));
	    if (localContesto == null) {
		throw new org.apache.axis2.databinding.ADBException("contesto cannot be null!!");
	    }
	    elementList.add(localContesto);
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "clientIp"));
	    if (localClientIp != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClientIp));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("clientIp cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias"));
	    if (localAlias != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlias));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "idcomune"));
	    if (localIdcomune != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdcomune));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("idcomune cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "firstrequest"));
	    if (localFirstrequest != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFirstrequest));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("firstrequest cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "lastrequest"));
	    if (localLastrequest != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastrequest));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("lastrequest cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "userid"));
	    if (localUserid != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUserid));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("userid cannot be null!!");
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
	    public static TokenInfoType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		TokenInfoType object = new TokenInfoType();
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
			    if (!"TokenInfoType".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (TokenInfoType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "contesto").equals(reader.getName())) {
			object.setContesto(ContestoType.Factory.parse(reader));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "clientIp").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setClientIp(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAlias(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "idcomune").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setIdcomune(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "firstrequest").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setFirstrequest(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "lastrequest").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setLastrequest(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "userid").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setUserid(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
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

    public static class LoginSSOResponse extends TokenResultType implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"LoginSSOResponse", "ns1");

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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":LoginSSOResponse", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "LoginSSOResponse", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "token", xmlWriter);
	    if (localToken == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localToken);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    attribList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
	    attribList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "LoginSSOResponse"));
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
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
	    public static LoginSSOResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		LoginSSOResponse object = new LoginSSOResponse();
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
			    if (!"LoginSSOResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (LoginSSOResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
			    }
			}
		    }
		    // Note all attributes that were handled. Used to differ normal attributes
		    // from anyAttributes.
		    java.util.Vector handledAttributes = new java.util.Vector();
		    reader.next();
		    while (!reader.isEndElement()) {
			if (reader.isStartElement()) {
			    if (reader.isStartElement()
				    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token").equals(reader.getName())) {
				java.lang.String content = reader.getElementText();
				object.setToken(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
				reader.next();
			    } // End of if for expected property start element
			    else {
				// A start element we are not expecting indicates an invalid parameter was passed
				throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
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

    public static class TokenResultType implements org.apache.axis2.databinding.ADBBean {

	/* This type was generated from the piece of schema that had
	        name = TokenResultType
	        Namespace URI = http://sigeprosecurity.gruppoinit.it/schema
	        Namespace Prefix = ns1
	        */
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":TokenResultType", xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "TokenResultType", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "token", xmlWriter);
	    if (localToken == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localToken);
	    }
	    xmlWriter.writeEndElement();
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token"));
	    if (localToken != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
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
	    public static TokenResultType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		TokenResultType object = new TokenResultType();
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
			    if (!"TokenResultType".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (TokenResultType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "token").equals(reader.getName())) {
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

    public static class GetDbConnectionInfoRequest implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"GetDbConnectionInfoRequest", "ns1");
	/**
	 * field for Alias
	 */
	protected java.lang.String localAlias;

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlias() {

	    return localAlias;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Alias
	 */
	public void setAlias(java.lang.String param) {

	    this.localAlias = param;
	}

	/**
	 * field for Ambiente
	 */
	protected AmbienteType localAmbiente;

	/**
	 * Auto generated getter method
	 * 
	 * @return AmbienteType
	 */
	public AmbienteType getAmbiente() {

	    return localAmbiente;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Ambiente
	 */
	public void setAmbiente(AmbienteType param) {

	    this.localAmbiente = param;
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":GetDbConnectionInfoRequest",
			    xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetDbConnectionInfoRequest", xmlWriter);
		}
	    }
	    namespace = "http://sigeprosecurity.gruppoinit.it/schema";
	    writeStartElement(null, namespace, "alias", xmlWriter);
	    if (localAlias == null) {
		// write the nil attribute
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    } else {
		xmlWriter.writeCharacters(localAlias);
	    }
	    xmlWriter.writeEndElement();
	    if (localAmbiente == null) {
		throw new org.apache.axis2.databinding.ADBException("ambiente cannot be null!!");
	    }
	    localAmbiente.serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "ambiente"), xmlWriter);
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias"));
	    if (localAlias != null) {
		elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlias));
	    } else {
		throw new org.apache.axis2.databinding.ADBException("alias cannot be null!!");
	    }
	    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "ambiente"));
	    if (localAmbiente == null) {
		throw new org.apache.axis2.databinding.ADBException("ambiente cannot be null!!");
	    }
	    elementList.add(localAmbiente);
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
	    public static GetDbConnectionInfoRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		GetDbConnectionInfoRequest object = new GetDbConnectionInfoRequest();
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
			    if (!"GetDbConnectionInfoRequest".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (GetDbConnectionInfoRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "alias").equals(reader.getName())) {
			java.lang.String content = reader.getElementText();
			object.setAlias(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
			reader.next();
		    } // End of if for expected property start element
		    else {
			// A start element we are not expecting indicates an invalid parameter was passed
			throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
		    }
		    while (!reader.isStartElement() && !reader.isEndElement())
			reader.next();
		    if (reader.isStartElement()
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "ambiente").equals(reader.getName())) {
			object.setAmbiente(AmbienteType.Factory.parse(reader));
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

    public static class GetApplicationInfoResponse implements org.apache.axis2.databinding.ADBBean {

	public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
		"GetApplicationInfoResponse", "ns1");
	/**
	 * field for ApplicationInfo This was an Array!
	 */
	protected ApplicationInfoType[] localApplicationInfo;
	/*  This tracker boolean wil be used to detect whether the user called the set method
	*   for this attribute. It will be used to determine whether to include this field
	*   in the serialized XML
	*/
	protected boolean localApplicationInfoTracker = false;

	public boolean isApplicationInfoSpecified() {

	    return localApplicationInfoTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return ApplicationInfoType[]
	 */
	public ApplicationInfoType[] getApplicationInfo() {

	    return localApplicationInfo;
	}

	/**
	 * validate the array for ApplicationInfo
	 */
	protected void validateApplicationInfo(ApplicationInfoType[] param) {

	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ApplicationInfo
	 */
	public void setApplicationInfo(ApplicationInfoType[] param) {

	    validateApplicationInfo(param);
	    localApplicationInfoTracker = param != null;
	    this.localApplicationInfo = param;
	}

	/**
	 * Auto generated add method for the array for convenience
	 * 
	 * @param param
	 *            ApplicationInfoType
	 */
	public void addApplicationInfo(ApplicationInfoType param) {

	    if (localApplicationInfo == null) {
		localApplicationInfo = new ApplicationInfoType[] {};
	    }
	    //update the setting tracker
	    localApplicationInfoTracker = true;
	    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localApplicationInfo);
	    list.add(param);
	    this.localApplicationInfo = (ApplicationInfoType[]) list.toArray(new ApplicationInfoType[list.size()]);
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
		java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://sigeprosecurity.gruppoinit.it/schema");
		if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":GetApplicationInfoResponse",
			    xmlWriter);
		} else {
		    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetApplicationInfoResponse", xmlWriter);
		}
	    }
	    if (localApplicationInfoTracker) {
		if (localApplicationInfo != null) {
		    for (int i = 0; i < localApplicationInfo.length; i++) {
			if (localApplicationInfo[i] != null) {
			    localApplicationInfo[i].serialize(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema",
				    "applicationInfo"), xmlWriter);
			} else {
			    // we don't have to do any thing since minOccures is zero
			}
		    }
		} else {
		    throw new org.apache.axis2.databinding.ADBException("applicationInfo cannot be null!!");
		}
	    }
	    xmlWriter.writeEndElement();
	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {

	    if (namespace.equals("http://sigeprosecurity.gruppoinit.it/schema")) {
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
	    if (localApplicationInfoTracker) {
		if (localApplicationInfo != null) {
		    for (int i = 0; i < localApplicationInfo.length; i++) {
			if (localApplicationInfo[i] != null) {
			    elementList.add(new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "applicationInfo"));
			    elementList.add(localApplicationInfo[i]);
			} else {
			    // nothing to do
			}
		    }
		} else {
		    throw new org.apache.axis2.databinding.ADBException("applicationInfo cannot be null!!");
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
	    public static GetApplicationInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

		GetApplicationInfoResponse object = new GetApplicationInfoResponse();
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
			    if (!"GetApplicationInfoResponse".equals(type)) {
				//find namespace for the prefix
				java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
				return (GetApplicationInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
			    && new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "applicationInfo").equals(reader
				    .getName())) {
			// Process the array and step past its final element's end.
			list1.add(ApplicationInfoType.Factory.parse(reader));
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
				if (new javax.xml.namespace.QName("http://sigeprosecurity.gruppoinit.it/schema", "applicationInfo").equals(reader
					.getName())) {
				    list1.add(ApplicationInfoType.Factory.parse(reader));
				} else {
				    loopDone1 = true;
				}
			    }
			}
			// call the converter utility  to convert and set the array
			object.setApplicationInfo((ApplicationInfoType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
				ApplicationInfoType.class, list1));
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

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest param, boolean optimizeContent)
	    throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.om.OMElement toOM(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse param,
	    boolean optimizeContent) throws org.apache.axis2.AxisFault {

	try {
	    return param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse.MY_QNAME,
		    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest.MY_QNAME, factory));
	    return emptyEnvelope;
	} catch (org.apache.axis2.databinding.ADBException e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
    }

    /* methods to provide back word compatibility */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
	    it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest param, boolean optimizeContent,
	    javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {

	try {
	    org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
	    emptyEnvelope.getBody().addChild(
		    param.getOMElement(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest.MY_QNAME, factory));
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
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoRequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutRequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSORequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListRequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenRequest.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	    if (it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse.class.equals(type)) {
		return it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse.Factory.parse(param
			.getXMLStreamReaderWithoutCaching());
	    }
	} catch (java.lang.Exception e) {
	    throw org.apache.axis2.AxisFault.makeFault(e);
	}
	return null;
    }
}
