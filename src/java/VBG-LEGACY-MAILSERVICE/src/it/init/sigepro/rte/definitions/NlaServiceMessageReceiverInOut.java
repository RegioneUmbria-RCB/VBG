
/**
 * NlaServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
        package it.init.sigepro.rte.definitions;

        /**
        *  NlaServiceMessageReceiverInOut message receiver
        */

        public class NlaServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        NlaServiceSkeletonInterface skel = (NlaServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("inserimentoPraticaNLA".equals(methodName)){
                
                it.init.sigepro.rte.InserimentoPraticaNLAResponse inserimentoPraticaNLAResponse15 = null;
	                        it.init.sigepro.rte.InserimentoPraticaNLARequest wrappedParam =
                                                             (it.init.sigepro.rte.InserimentoPraticaNLARequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.init.sigepro.rte.InserimentoPraticaNLARequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               inserimentoPraticaNLAResponse15 =
                                                   
                                                   
                                                         skel.inserimentoPraticaNLA(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), inserimentoPraticaNLAResponse15, false, new javax.xml.namespace.QName("http://sigepro.init.it/rte/definitions",
                                                    "inserimentoPraticaNLA"));
                                    } else 

            if("richiestaPraticheListaNLA".equals(methodName)){
                
                it.init.sigepro.rte.RichiestaPraticheListaNLAResponse richiestaPraticheListaNLAResponse17 = null;
	                        it.init.sigepro.rte.RichiestaPraticheListaNLARequest wrappedParam =
                                                             (it.init.sigepro.rte.RichiestaPraticheListaNLARequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.init.sigepro.rte.RichiestaPraticheListaNLARequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               richiestaPraticheListaNLAResponse17 =
                                                   
                                                   
                                                         skel.richiestaPraticheListaNLA(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), richiestaPraticheListaNLAResponse17, false, new javax.xml.namespace.QName("http://sigepro.init.it/rte/definitions",
                                                    "richiestaPraticheListaNLA"));
                                    } else 

            if("allegatoBinarioNLA".equals(methodName)){
                
                it.init.sigepro.rte.AllegatoBinarioNLAResponse allegatoBinarioNLAResponse19 = null;
	                        it.init.sigepro.rte.AllegatoBinarioNLARequest wrappedParam =
                                                             (it.init.sigepro.rte.AllegatoBinarioNLARequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.init.sigepro.rte.AllegatoBinarioNLARequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               allegatoBinarioNLAResponse19 =
                                                   
                                                   
                                                         skel.allegatoBinarioNLA(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), allegatoBinarioNLAResponse19, false, new javax.xml.namespace.QName("http://sigepro.init.it/rte/definitions",
                                                    "allegatoBinarioNLA"));
                                    } else 

            if("aggiungiDocumentiNLA".equals(methodName)){
                
                it.init.sigepro.rte.AggiungiDocumentiNLAResponse aggiungiDocumentiNLAResponse21 = null;
	                        it.init.sigepro.rte.AggiungiDocumentiNLARequest wrappedParam =
                                                             (it.init.sigepro.rte.AggiungiDocumentiNLARequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.init.sigepro.rte.AggiungiDocumentiNLARequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               aggiungiDocumentiNLAResponse21 =
                                                   
                                                   
                                                         skel.aggiungiDocumentiNLA(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), aggiungiDocumentiNLAResponse21, false, new javax.xml.namespace.QName("http://sigepro.init.it/rte/definitions",
                                                    "aggiungiDocumentiNLA"));
                                    } else 

            if("testNLA".equals(methodName)){
                
                it.init.sigepro.rte.TestNLAResponse testNLAResponse23 = null;
	                        it.init.sigepro.rte.TestNLARequest wrappedParam =
                                                             (it.init.sigepro.rte.TestNLARequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.init.sigepro.rte.TestNLARequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               testNLAResponse23 =
                                                   
                                                   
                                                         skel.testNLA(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), testNLAResponse23, false, new javax.xml.namespace.QName("http://sigepro.init.it/rte/definitions",
                                                    "testNLA"));
                                    } else 

            if("richiestaPraticaNLA".equals(methodName)){
                
                it.init.sigepro.rte.RichiestaPraticaNLAResponse richiestaPraticaNLAResponse25 = null;
	                        it.init.sigepro.rte.RichiestaPraticaNLARequest wrappedParam =
                                                             (it.init.sigepro.rte.RichiestaPraticaNLARequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.init.sigepro.rte.RichiestaPraticaNLARequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               richiestaPraticaNLAResponse25 =
                                                   
                                                   
                                                         skel.richiestaPraticaNLA(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), richiestaPraticaNLAResponse25, false, new javax.xml.namespace.QName("http://sigepro.init.it/rte/definitions",
                                                    "richiestaPraticaNLA"));
                                    } else 

            if("inserimentoAttivitaNLA".equals(methodName)){
                
                it.init.sigepro.rte.InserimentoAttivitaNLAResponse inserimentoAttivitaNLAResponse27 = null;
	                        it.init.sigepro.rte.InserimentoAttivitaNLARequest wrappedParam =
                                                             (it.init.sigepro.rte.InserimentoAttivitaNLARequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.init.sigepro.rte.InserimentoAttivitaNLARequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               inserimentoAttivitaNLAResponse27 =
                                                   
                                                   
                                                         skel.inserimentoAttivitaNLA(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), inserimentoAttivitaNLAResponse27, false, new javax.xml.namespace.QName("http://sigepro.init.it/rte/definitions",
                                                    "inserimentoAttivitaNLA"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.InserimentoPraticaNLARequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.InserimentoPraticaNLARequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.InserimentoPraticaNLAResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.InserimentoPraticaNLAResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.RichiestaPraticheListaNLARequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.RichiestaPraticheListaNLARequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.RichiestaPraticheListaNLAResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.RichiestaPraticheListaNLAResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.AllegatoBinarioNLARequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.AllegatoBinarioNLARequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.AllegatoBinarioNLAResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.AllegatoBinarioNLAResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.AggiungiDocumentiNLARequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.AggiungiDocumentiNLARequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.AggiungiDocumentiNLAResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.AggiungiDocumentiNLAResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.TestNLARequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.TestNLARequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.TestNLAResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.TestNLAResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.RichiestaPraticaNLARequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.RichiestaPraticaNLARequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.RichiestaPraticaNLAResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.RichiestaPraticaNLAResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.InserimentoAttivitaNLARequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.InserimentoAttivitaNLARequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.init.sigepro.rte.InserimentoAttivitaNLAResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.init.sigepro.rte.InserimentoAttivitaNLAResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.init.sigepro.rte.InserimentoPraticaNLAResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.init.sigepro.rte.InserimentoPraticaNLAResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.init.sigepro.rte.InserimentoPraticaNLAResponse wrapInserimentoPraticaNLA(){
                                it.init.sigepro.rte.InserimentoPraticaNLAResponse wrappedElement = new it.init.sigepro.rte.InserimentoPraticaNLAResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.init.sigepro.rte.RichiestaPraticheListaNLAResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.init.sigepro.rte.RichiestaPraticheListaNLAResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.init.sigepro.rte.RichiestaPraticheListaNLAResponse wrapRichiestaPraticheListaNLA(){
                                it.init.sigepro.rte.RichiestaPraticheListaNLAResponse wrappedElement = new it.init.sigepro.rte.RichiestaPraticheListaNLAResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.init.sigepro.rte.AllegatoBinarioNLAResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.init.sigepro.rte.AllegatoBinarioNLAResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.init.sigepro.rte.AllegatoBinarioNLAResponse wrapAllegatoBinarioNLA(){
                                it.init.sigepro.rte.AllegatoBinarioNLAResponse wrappedElement = new it.init.sigepro.rte.AllegatoBinarioNLAResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.init.sigepro.rte.AggiungiDocumentiNLAResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.init.sigepro.rte.AggiungiDocumentiNLAResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.init.sigepro.rte.AggiungiDocumentiNLAResponse wrapAggiungiDocumentiNLA(){
                                it.init.sigepro.rte.AggiungiDocumentiNLAResponse wrappedElement = new it.init.sigepro.rte.AggiungiDocumentiNLAResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.init.sigepro.rte.TestNLAResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.init.sigepro.rte.TestNLAResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.init.sigepro.rte.TestNLAResponse wrapTestNLA(){
                                it.init.sigepro.rte.TestNLAResponse wrappedElement = new it.init.sigepro.rte.TestNLAResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.init.sigepro.rte.RichiestaPraticaNLAResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.init.sigepro.rte.RichiestaPraticaNLAResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.init.sigepro.rte.RichiestaPraticaNLAResponse wrapRichiestaPraticaNLA(){
                                it.init.sigepro.rte.RichiestaPraticaNLAResponse wrappedElement = new it.init.sigepro.rte.RichiestaPraticaNLAResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.init.sigepro.rte.InserimentoAttivitaNLAResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.init.sigepro.rte.InserimentoAttivitaNLAResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.init.sigepro.rte.InserimentoAttivitaNLAResponse wrapInserimentoAttivitaNLA(){
                                it.init.sigepro.rte.InserimentoAttivitaNLAResponse wrappedElement = new it.init.sigepro.rte.InserimentoAttivitaNLAResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (it.init.sigepro.rte.InserimentoPraticaNLARequest.class.equals(type)){
                
                           return it.init.sigepro.rte.InserimentoPraticaNLARequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.InserimentoPraticaNLAResponse.class.equals(type)){
                
                           return it.init.sigepro.rte.InserimentoPraticaNLAResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.RichiestaPraticheListaNLARequest.class.equals(type)){
                
                           return it.init.sigepro.rte.RichiestaPraticheListaNLARequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.RichiestaPraticheListaNLAResponse.class.equals(type)){
                
                           return it.init.sigepro.rte.RichiestaPraticheListaNLAResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.AllegatoBinarioNLARequest.class.equals(type)){
                
                           return it.init.sigepro.rte.AllegatoBinarioNLARequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.AllegatoBinarioNLAResponse.class.equals(type)){
                
                           return it.init.sigepro.rte.AllegatoBinarioNLAResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.AggiungiDocumentiNLARequest.class.equals(type)){
                
                           return it.init.sigepro.rte.AggiungiDocumentiNLARequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.AggiungiDocumentiNLAResponse.class.equals(type)){
                
                           return it.init.sigepro.rte.AggiungiDocumentiNLAResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.TestNLARequest.class.equals(type)){
                
                           return it.init.sigepro.rte.TestNLARequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.TestNLAResponse.class.equals(type)){
                
                           return it.init.sigepro.rte.TestNLAResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.RichiestaPraticaNLARequest.class.equals(type)){
                
                           return it.init.sigepro.rte.RichiestaPraticaNLARequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.RichiestaPraticaNLAResponse.class.equals(type)){
                
                           return it.init.sigepro.rte.RichiestaPraticaNLAResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.InserimentoAttivitaNLARequest.class.equals(type)){
                
                           return it.init.sigepro.rte.InserimentoAttivitaNLARequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.init.sigepro.rte.InserimentoAttivitaNLAResponse.class.equals(type)){
                
                           return it.init.sigepro.rte.InserimentoAttivitaNLAResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    