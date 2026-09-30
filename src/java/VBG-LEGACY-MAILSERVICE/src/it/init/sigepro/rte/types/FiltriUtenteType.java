
/**
 * FiltriUtenteType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  FiltriUtenteType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class FiltriUtenteType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FiltriUtenteType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for CodiceFiscale
                        */

                        
                                    protected java.lang.String localCodiceFiscale ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCodiceFiscale(){
                               return localCodiceFiscale;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodiceFiscale
                               */
                               public void setCodiceFiscale(java.lang.String param){
                            
                                            this.localCodiceFiscale=param;
                                    

                               }
                            

                        /**
                        * field for CercaComeRichiedente
                        * This was an Attribute!
                        */

                        
                                    protected boolean localCercaComeRichiedente ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getCercaComeRichiedente(){
                               return localCercaComeRichiedente;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CercaComeRichiedente
                               */
                               public void setCercaComeRichiedente(boolean param){
                            
                                            this.localCercaComeRichiedente=param;
                                    

                               }
                            

                        /**
                        * field for CercaComeAziendaRichiedente
                        * This was an Attribute!
                        */

                        
                                    protected boolean localCercaComeAziendaRichiedente ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getCercaComeAziendaRichiedente(){
                               return localCercaComeAziendaRichiedente;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CercaComeAziendaRichiedente
                               */
                               public void setCercaComeAziendaRichiedente(boolean param){
                            
                                            this.localCercaComeAziendaRichiedente=param;
                                    

                               }
                            

                        /**
                        * field for CercaNeiSoggettiCollegati
                        * This was an Attribute!
                        */

                        
                                    protected boolean localCercaNeiSoggettiCollegati ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getCercaNeiSoggettiCollegati(){
                               return localCercaNeiSoggettiCollegati;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CercaNeiSoggettiCollegati
                               */
                               public void setCercaNeiSoggettiCollegati(boolean param){
                            
                                            this.localCercaNeiSoggettiCollegati=param;
                                    

                               }
                            

                        /**
                        * field for CercaComeIntermediario
                        * This was an Attribute!
                        */

                        
                                    protected boolean localCercaComeIntermediario ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getCercaComeIntermediario(){
                               return localCercaComeIntermediario;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CercaComeIntermediario
                               */
                               public void setCercaComeIntermediario(boolean param){
                            
                                            this.localCercaComeIntermediario=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://sigepro.init.it/rte/types");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FiltriUtenteType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FiltriUtenteType",
                           xmlWriter);
                   }

               
                   }
               
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "cercaComeRichiedente",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaComeRichiedente), xmlWriter);

                                            
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "cercaComeAziendaRichiedente",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaComeAziendaRichiedente), xmlWriter);

                                            
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "cercaNeiSoggettiCollegati",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaNeiSoggettiCollegati), xmlWriter);

                                            
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "cercaComeIntermediario",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaComeIntermediario), xmlWriter);

                                            
                                      }
                                    
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "codiceFiscale", xmlWriter);
                             

                                          if (localCodiceFiscale==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codiceFiscale cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodiceFiscale);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://sigepro.init.it/rte/types")){
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
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

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
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

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
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
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
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
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
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceFiscale"));
                                 
                                        if (localCodiceFiscale != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodiceFiscale));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codiceFiscale cannot be null!!");
                                        }
                                    
                            attribList.add(
                            new javax.xml.namespace.QName("","cercaComeRichiedente"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaComeRichiedente));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","cercaComeAziendaRichiedente"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaComeAziendaRichiedente));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","cercaNeiSoggettiCollegati"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaNeiSoggettiCollegati));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","cercaComeIntermediario"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCercaComeIntermediario));
                                

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FiltriUtenteType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FiltriUtenteType object =
                new FiltriUtenteType();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FiltriUtenteType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FiltriUtenteType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    // handle attribute "cercaComeRichiedente"
                    java.lang.String tempAttribCercaComeRichiedente =
                        
                                reader.getAttributeValue(null,"cercaComeRichiedente");
                            
                   if (tempAttribCercaComeRichiedente!=null){
                         java.lang.String content = tempAttribCercaComeRichiedente;
                        
                                                 object.setCercaComeRichiedente(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribCercaComeRichiedente));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("cercaComeRichiedente");
                    
                    // handle attribute "cercaComeAziendaRichiedente"
                    java.lang.String tempAttribCercaComeAziendaRichiedente =
                        
                                reader.getAttributeValue(null,"cercaComeAziendaRichiedente");
                            
                   if (tempAttribCercaComeAziendaRichiedente!=null){
                         java.lang.String content = tempAttribCercaComeAziendaRichiedente;
                        
                                                 object.setCercaComeAziendaRichiedente(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribCercaComeAziendaRichiedente));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("cercaComeAziendaRichiedente");
                    
                    // handle attribute "cercaNeiSoggettiCollegati"
                    java.lang.String tempAttribCercaNeiSoggettiCollegati =
                        
                                reader.getAttributeValue(null,"cercaNeiSoggettiCollegati");
                            
                   if (tempAttribCercaNeiSoggettiCollegati!=null){
                         java.lang.String content = tempAttribCercaNeiSoggettiCollegati;
                        
                                                 object.setCercaNeiSoggettiCollegati(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribCercaNeiSoggettiCollegati));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("cercaNeiSoggettiCollegati");
                    
                    // handle attribute "cercaComeIntermediario"
                    java.lang.String tempAttribCercaComeIntermediario =
                        
                                reader.getAttributeValue(null,"cercaComeIntermediario");
                            
                   if (tempAttribCercaComeIntermediario!=null){
                         java.lang.String content = tempAttribCercaComeIntermediario;
                        
                                                 object.setCercaComeIntermediario(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribCercaComeIntermediario));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("cercaComeIntermediario");
                    
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceFiscale").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodiceFiscale(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
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
           
    