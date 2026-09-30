
/**
 * MailServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
        package it.gruppoinit.mailservice;

        /**
        *  MailServiceMessageReceiverInOut message receiver
        */

        public class MailServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        MailServiceSkeletonInterface skel = (MailServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("sendMail".equals(methodName)){
                
                it.gruppoinit.mailservice.schemas.messages.MessageResponse messageResponse5 = null;
	                        it.gruppoinit.mailservice.schemas.messages.MessageRequest wrappedParam =
                                                             (it.gruppoinit.mailservice.schemas.messages.MessageRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gruppoinit.mailservice.schemas.messages.MessageRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               messageResponse5 =
                                                   
                                                   
                                                         skel.sendMail(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), messageResponse5, false, new javax.xml.namespace.QName("http://gruppoinit.it/MailService/",
                                                    "sendMail"));
                                    } else 

            if("sendMail2".equals(methodName)){
                
                it.gruppoinit.mailservice.schemas.messages.MessageResponse2 messageResponse27 = null;
	                        it.gruppoinit.mailservice.schemas.messages.MessageRequest2 wrappedParam =
                                                             (it.gruppoinit.mailservice.schemas.messages.MessageRequest2)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    it.gruppoinit.mailservice.schemas.messages.MessageRequest2.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               messageResponse27 =
                                                   
                                                   
                                                         skel.sendMail2(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), messageResponse27, false, new javax.xml.namespace.QName("http://gruppoinit.it/MailService/",
                                                    "sendMail2"));
                                    
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
            private  org.apache.axiom.om.OMElement  toOM(it.gruppoinit.mailservice.schemas.messages.MessageRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gruppoinit.mailservice.schemas.messages.MessageRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gruppoinit.mailservice.schemas.messages.MessageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gruppoinit.mailservice.schemas.messages.MessageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gruppoinit.mailservice.schemas.messages.MessageRequest2 param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gruppoinit.mailservice.schemas.messages.MessageRequest2.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(it.gruppoinit.mailservice.schemas.messages.MessageResponse2 param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(it.gruppoinit.mailservice.schemas.messages.MessageResponse2.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gruppoinit.mailservice.schemas.messages.MessageResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gruppoinit.mailservice.schemas.messages.MessageResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gruppoinit.mailservice.schemas.messages.MessageResponse wrapsendMail(){
                                it.gruppoinit.mailservice.schemas.messages.MessageResponse wrappedElement = new it.gruppoinit.mailservice.schemas.messages.MessageResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, it.gruppoinit.mailservice.schemas.messages.MessageResponse2 param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(it.gruppoinit.mailservice.schemas.messages.MessageResponse2.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private it.gruppoinit.mailservice.schemas.messages.MessageResponse2 wrapsendMail2(){
                                it.gruppoinit.mailservice.schemas.messages.MessageResponse2 wrappedElement = new it.gruppoinit.mailservice.schemas.messages.MessageResponse2();
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
        
                if (it.gruppoinit.mailservice.schemas.messages.MessageRequest.class.equals(type)){
                
                           return it.gruppoinit.mailservice.schemas.messages.MessageRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gruppoinit.mailservice.schemas.messages.MessageResponse.class.equals(type)){
                
                           return it.gruppoinit.mailservice.schemas.messages.MessageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gruppoinit.mailservice.schemas.messages.MessageRequest2.class.equals(type)){
                
                           return it.gruppoinit.mailservice.schemas.messages.MessageRequest2.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (it.gruppoinit.mailservice.schemas.messages.MessageResponse2.class.equals(type)){
                
                           return it.gruppoinit.mailservice.schemas.messages.MessageResponse2.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    