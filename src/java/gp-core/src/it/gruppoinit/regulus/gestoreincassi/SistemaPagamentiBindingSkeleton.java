/**
 * SistemaPagamentiBindingSkeleton.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package it.gruppoinit.regulus.gestoreincassi;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

/**
 * 
 * @author francescop
 * 
 */
@SuppressWarnings(value = { "serial", "unchecked", "unused" })
public class SistemaPagamentiBindingSkeleton extends ServletEndpointSupport implements it.gruppoinit.regulus.gestoreincassi.SistemaPagamenti,
	org.apache.axis.wsdl.Skeleton {

    private it.gruppoinit.regulus.gestoreincassi.SistemaPagamenti impl;

    protected void onInit() {

	this.impl = (SistemaPagamenti) getWebApplicationContext().getBean("sistemaPagamentiRegulusWS");
    }

    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
     * Returns List of OperationDesc objects with this name
     */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {

	return (java.util.List) _myOperations.get(methodName);
    }

    /**
     * Returns Collection of OperationDescs
     */
    public static java.util.Collection getOperationDescs() {

	return _myOperationsList;
    }

    static {
	org.apache.axis.description.OperationDesc _oper;
	org.apache.axis.description.FaultDesc _fault;
	org.apache.axis.description.ParameterDesc[] _params;
	_params = new org.apache.axis.description.ParameterDesc[] { new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("",
		"xmlInput"), org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), };
	_oper = new org.apache.axis.description.OperationDesc("richiesta", _params, new javax.xml.namespace.QName("", "result"));
	_oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	_oper.setElementQName(new javax.xml.namespace.QName("http://regulus.it/gestoreincassi", "richiesta"));
	_oper.setSoapAction("");
	_myOperationsList.add(_oper);
	if (_myOperations.get("richiesta") == null) {
	    _myOperations.put("richiesta", new java.util.ArrayList());
	}
	((java.util.List) _myOperations.get("richiesta")).add(_oper);
    }

    public SistemaPagamentiBindingSkeleton() {

	this.impl = new it.gruppoinit.regulus.gestoreincassi.SistemaPagamentiBindingImpl();
    }

    public SistemaPagamentiBindingSkeleton(it.gruppoinit.regulus.gestoreincassi.SistemaPagamenti impl) {

	this.impl = impl;
    }

    public java.lang.String richiesta(java.lang.String xmlInput) throws java.rmi.RemoteException {

	java.lang.String ret = impl.richiesta(xmlInput);
	return ret;
    }
}
