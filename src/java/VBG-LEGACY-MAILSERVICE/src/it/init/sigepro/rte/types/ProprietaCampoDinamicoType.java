
/**
 * ProprietaCampoDinamicoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  ProprietaCampoDinamicoType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ProprietaCampoDinamicoType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ProprietaCampoDinamicoType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for TipoCampo
                        */

                        
                                    protected it.init.sigepro.rte.types.TipoCampoDinamicoType localTipoCampo ;
                                

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.TipoCampoDinamicoType
                           */
                           public  it.init.sigepro.rte.types.TipoCampoDinamicoType getTipoCampo(){
                               return localTipoCampo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TipoCampo
                               */
                               public void setTipoCampo(it.init.sigepro.rte.types.TipoCampoDinamicoType param){
                            
                                            this.localTipoCampo=param;
                                    

                               }
                            

                        /**
                        * field for Posizione
                        */

                        
                                    protected it.init.sigepro.rte.types.PosizioneCampoType localPosizione ;
                                

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.PosizioneCampoType
                           */
                           public  it.init.sigepro.rte.types.PosizioneCampoType getPosizione(){
                               return localPosizione;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Posizione
                               */
                               public void setPosizione(it.init.sigepro.rte.types.PosizioneCampoType param){
                            
                                            this.localPosizione=param;
                                    

                               }
                            

                        /**
                        * field for Obbligatorio
                        */

                        
                                    protected boolean localObbligatorio ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getObbligatorio(){
                               return localObbligatorio;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Obbligatorio
                               */
                               public void setObbligatorio(boolean param){
                            
                                            this.localObbligatorio=param;
                                    

                               }
                            

                        /**
                        * field for Multiplo
                        */

                        
                                    protected boolean localMultiplo ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getMultiplo(){
                               return localMultiplo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Multiplo
                               */
                               public void setMultiplo(boolean param){
                            
                                            this.localMultiplo=param;
                                    

                               }
                            

                        /**
                        * field for Lunghezza
                        */

                        
                                    protected it.init.sigepro.rte.types.Lunghezza_type1 localLunghezza ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLunghezzaTracker = false ;

                           public boolean isLunghezzaSpecified(){
                               return localLunghezzaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.Lunghezza_type1
                           */
                           public  it.init.sigepro.rte.types.Lunghezza_type1 getLunghezza(){
                               return localLunghezza;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Lunghezza
                               */
                               public void setLunghezza(it.init.sigepro.rte.types.Lunghezza_type1 param){
                            localLunghezzaTracker = param != null;
                                   
                                            this.localLunghezza=param;
                                    

                               }
                            

                        /**
                        * field for CifreDecimali
                        */

                        
                                    protected it.init.sigepro.rte.types.CifreDecimali_type1 localCifreDecimali ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCifreDecimaliTracker = false ;

                           public boolean isCifreDecimaliSpecified(){
                               return localCifreDecimaliTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.CifreDecimali_type1
                           */
                           public  it.init.sigepro.rte.types.CifreDecimali_type1 getCifreDecimali(){
                               return localCifreDecimali;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CifreDecimali
                               */
                               public void setCifreDecimali(it.init.sigepro.rte.types.CifreDecimali_type1 param){
                            localCifreDecimaliTracker = param != null;
                                   
                                            this.localCifreDecimali=param;
                                    

                               }
                            

                        /**
                        * field for ValoreLista
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.ValoreParametroType[] localValoreLista ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localValoreListaTracker = false ;

                           public boolean isValoreListaSpecified(){
                               return localValoreListaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ValoreParametroType[]
                           */
                           public  it.init.sigepro.rte.types.ValoreParametroType[] getValoreLista(){
                               return localValoreLista;
                           }

                           
                        


                               
                              /**
                               * validate the array for ValoreLista
                               */
                              protected void validateValoreLista(it.init.sigepro.rte.types.ValoreParametroType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param ValoreLista
                              */
                              public void setValoreLista(it.init.sigepro.rte.types.ValoreParametroType[] param){
                              
                                   validateValoreLista(param);

                               localValoreListaTracker = param != null;
                                      
                                      this.localValoreLista=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.ValoreParametroType
                             */
                             public void addValoreLista(it.init.sigepro.rte.types.ValoreParametroType param){
                                   if (localValoreLista == null){
                                   localValoreLista = new it.init.sigepro.rte.types.ValoreParametroType[]{};
                                   }

                            
                                 //update the setting tracker
                                localValoreListaTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localValoreLista);
                               list.add(param);
                               this.localValoreLista =
                             (it.init.sigepro.rte.types.ValoreParametroType[])list.toArray(
                            new it.init.sigepro.rte.types.ValoreParametroType[list.size()]);

                             }
                             

                        /**
                        * field for ValoriCheckBox
                        */

                        
                                    protected it.init.sigepro.rte.types.ValoriCampoCheckboxType localValoriCheckBox ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localValoriCheckBoxTracker = false ;

                           public boolean isValoriCheckBoxSpecified(){
                               return localValoriCheckBoxTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ValoriCampoCheckboxType
                           */
                           public  it.init.sigepro.rte.types.ValoriCampoCheckboxType getValoriCheckBox(){
                               return localValoriCheckBox;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ValoriCheckBox
                               */
                               public void setValoriCheckBox(it.init.sigepro.rte.types.ValoriCampoCheckboxType param){
                            localValoriCheckBoxTracker = param != null;
                                   
                                            this.localValoriCheckBox=param;
                                    

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
                           namespacePrefix+":ProprietaCampoDinamicoType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ProprietaCampoDinamicoType",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localTipoCampo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("tipoCampo cannot be null!!");
                                            }
                                           localTipoCampo.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tipoCampo"),
                                               xmlWriter);
                                        
                                            if (localPosizione==null){
                                                 throw new org.apache.axis2.databinding.ADBException("posizione cannot be null!!");
                                            }
                                           localPosizione.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","posizione"),
                                               xmlWriter);
                                        
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "obbligatorio", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("obbligatorio cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localObbligatorio));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "multiplo", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("multiplo cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMultiplo));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localLunghezzaTracker){
                                            if (localLunghezza==null){
                                                 throw new org.apache.axis2.databinding.ADBException("lunghezza cannot be null!!");
                                            }
                                           localLunghezza.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","lunghezza"),
                                               xmlWriter);
                                        } if (localCifreDecimaliTracker){
                                            if (localCifreDecimali==null){
                                                 throw new org.apache.axis2.databinding.ADBException("cifreDecimali cannot be null!!");
                                            }
                                           localCifreDecimali.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","cifreDecimali"),
                                               xmlWriter);
                                        } if (localValoreListaTracker){
                                       if (localValoreLista!=null){
                                            for (int i = 0;i < localValoreLista.length;i++){
                                                if (localValoreLista[i] != null){
                                                 localValoreLista[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","valoreLista"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("valoreLista cannot be null!!");
                                        
                                    }
                                 } if (localValoriCheckBoxTracker){
                                            if (localValoriCheckBox==null){
                                                 throw new org.apache.axis2.databinding.ADBException("valoriCheckBox cannot be null!!");
                                            }
                                           localValoriCheckBox.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","valoriCheckBox"),
                                               xmlWriter);
                                        }
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
                                                                      "tipoCampo"));
                            
                            
                                    if (localTipoCampo==null){
                                         throw new org.apache.axis2.databinding.ADBException("tipoCampo cannot be null!!");
                                    }
                                    elementList.add(localTipoCampo);
                                
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "posizione"));
                            
                            
                                    if (localPosizione==null){
                                         throw new org.apache.axis2.databinding.ADBException("posizione cannot be null!!");
                                    }
                                    elementList.add(localPosizione);
                                
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "obbligatorio"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localObbligatorio));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "multiplo"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMultiplo));
                             if (localLunghezzaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "lunghezza"));
                            
                            
                                    if (localLunghezza==null){
                                         throw new org.apache.axis2.databinding.ADBException("lunghezza cannot be null!!");
                                    }
                                    elementList.add(localLunghezza);
                                } if (localCifreDecimaliTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "cifreDecimali"));
                            
                            
                                    if (localCifreDecimali==null){
                                         throw new org.apache.axis2.databinding.ADBException("cifreDecimali cannot be null!!");
                                    }
                                    elementList.add(localCifreDecimali);
                                } if (localValoreListaTracker){
                             if (localValoreLista!=null) {
                                 for (int i = 0;i < localValoreLista.length;i++){

                                    if (localValoreLista[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "valoreLista"));
                                         elementList.add(localValoreLista[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("valoreLista cannot be null!!");
                                    
                             }

                        } if (localValoriCheckBoxTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "valoriCheckBox"));
                            
                            
                                    if (localValoriCheckBox==null){
                                         throw new org.apache.axis2.databinding.ADBException("valoriCheckBox cannot be null!!");
                                    }
                                    elementList.add(localValoriCheckBox);
                                }

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
        public static ProprietaCampoDinamicoType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            ProprietaCampoDinamicoType object =
                new ProprietaCampoDinamicoType();

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
                    
                            if (!"ProprietaCampoDinamicoType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ProprietaCampoDinamicoType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list7 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tipoCampo").equals(reader.getName())){
                                
                                                object.setTipoCampo(it.init.sigepro.rte.types.TipoCampoDinamicoType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","posizione").equals(reader.getName())){
                                
                                                object.setPosizione(it.init.sigepro.rte.types.PosizioneCampoType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","obbligatorio").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setObbligatorio(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","multiplo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMultiplo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","lunghezza").equals(reader.getName())){
                                
                                                object.setLunghezza(it.init.sigepro.rte.types.Lunghezza_type1.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","cifreDecimali").equals(reader.getName())){
                                
                                                object.setCifreDecimali(it.init.sigepro.rte.types.CifreDecimali_type1.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","valoreLista").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list7.add(it.init.sigepro.rte.types.ValoreParametroType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone7 = false;
                                                        while(!loopDone7){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone7 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","valoreLista").equals(reader.getName())){
                                                                    list7.add(it.init.sigepro.rte.types.ValoreParametroType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone7 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setValoreLista((it.init.sigepro.rte.types.ValoreParametroType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.ValoreParametroType.class,
                                                                list7));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","valoriCheckBox").equals(reader.getName())){
                                
                                                object.setValoriCheckBox(it.init.sigepro.rte.types.ValoriCampoCheckboxType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
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
           
    