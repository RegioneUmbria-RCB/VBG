/**
 * CnsHandlerSoap11Skeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.gruppoinit.cnshandler.ws;

public class CnsHandlerSoap11Skeleton implements it.gruppoinit.cnshandler.ws.CnsHandler, org.apache.axis.wsdl.Skeleton {
    private it.gruppoinit.cnshandler.ws.CnsHandler impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
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
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://cnshandler.gruppoinit.it/schema", "GetUserDataRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://cnshandler.gruppoinit.it/schema", ">GetUserDataRequest"), it.gruppoinit.cnshandler.schema.GetUserDataRequest.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getUserData", _params, new javax.xml.namespace.QName("http://cnshandler.gruppoinit.it/schema", "GetUserDataResponse"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://cnshandler.gruppoinit.it/schema", ">GetUserDataResponse"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "GetUserData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getUserData") == null) {
            _myOperations.put("getUserData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getUserData")).add(_oper);
    }

    public CnsHandlerSoap11Skeleton() {
        this.impl = new it.gruppoinit.cnshandler.ws.CnsHandlerSoap11Impl();
    }

    public CnsHandlerSoap11Skeleton(it.gruppoinit.cnshandler.ws.CnsHandler impl) {
        this.impl = impl;
    }
    public it.gruppoinit.cnshandler.schema.GetUserDataResponse getUserData(it.gruppoinit.cnshandler.schema.GetUserDataRequest getUserDataRequest) throws java.rmi.RemoteException
    {
        it.gruppoinit.cnshandler.schema.GetUserDataResponse ret = impl.getUserData(getUserDataRequest);
        return ret;
    }

}
