
/**
 * DettaglioPraticaVisuraType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  DettaglioPraticaVisuraType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class DettaglioPraticaVisuraType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = DettaglioPraticaVisuraType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for DettaglioPratica
                        */

                        
                                    protected it.init.sigepro.rte.types.DettaglioPraticaType localDettaglioPratica ;
                                

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DettaglioPraticaType
                           */
                           public  it.init.sigepro.rte.types.DettaglioPraticaType getDettaglioPratica(){
                               return localDettaglioPratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DettaglioPratica
                               */
                               public void setDettaglioPratica(it.init.sigepro.rte.types.DettaglioPraticaType param){
                            
                                            this.localDettaglioPratica=param;
                                    

                               }
                            

                        /**
                        * field for ListaAttivita
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.DettaglioAttivitaType[] localListaAttivita ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListaAttivitaTracker = false ;

                           public boolean isListaAttivitaSpecified(){
                               return localListaAttivitaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DettaglioAttivitaType[]
                           */
                           public  it.init.sigepro.rte.types.DettaglioAttivitaType[] getListaAttivita(){
                               return localListaAttivita;
                           }

                           
                        


                               
                              /**
                               * validate the array for ListaAttivita
                               */
                              protected void validateListaAttivita(it.init.sigepro.rte.types.DettaglioAttivitaType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param ListaAttivita
                              */
                              public void setListaAttivita(it.init.sigepro.rte.types.DettaglioAttivitaType[] param){
                              
                                   validateListaAttivita(param);

                               localListaAttivitaTracker = param != null;
                                      
                                      this.localListaAttivita=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.DettaglioAttivitaType
                             */
                             public void addListaAttivita(it.init.sigepro.rte.types.DettaglioAttivitaType param){
                                   if (localListaAttivita == null){
                                   localListaAttivita = new it.init.sigepro.rte.types.DettaglioAttivitaType[]{};
                                   }

                            
                                 //update the setting tracker
                                localListaAttivitaTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localListaAttivita);
                               list.add(param);
                               this.localListaAttivita =
                             (it.init.sigepro.rte.types.DettaglioAttivitaType[])list.toArray(
                            new it.init.sigepro.rte.types.DettaglioAttivitaType[list.size()]);

                             }
                             

                        /**
                        * field for StatoPratica
                        */

                        
                                    protected it.init.sigepro.rte.types.StatoPraticaType localStatoPratica ;
                                

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.StatoPraticaType
                           */
                           public  it.init.sigepro.rte.types.StatoPraticaType getStatoPratica(){
                               return localStatoPratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StatoPratica
                               */
                               public void setStatoPratica(it.init.sigepro.rte.types.StatoPraticaType param){
                            
                                            this.localStatoPratica=param;
                                    

                               }
                            

                        /**
                        * field for StatoIter
                        */

                        
                                    protected it.init.sigepro.rte.types.StatoIterType localStatoIter ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStatoIterTracker = false ;

                           public boolean isStatoIterSpecified(){
                               return localStatoIterTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.StatoIterType
                           */
                           public  it.init.sigepro.rte.types.StatoIterType getStatoIter(){
                               return localStatoIter;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StatoIter
                               */
                               public void setStatoIter(it.init.sigepro.rte.types.StatoIterType param){
                            localStatoIterTracker = param != null;
                                   
                                            this.localStatoIter=param;
                                    

                               }
                            

                        /**
                        * field for PasswordMD5
                        */

                        
                                    protected java.lang.String localPasswordMD5 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPasswordMD5Tracker = false ;

                           public boolean isPasswordMD5Specified(){
                               return localPasswordMD5Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPasswordMD5(){
                               return localPasswordMD5;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PasswordMD5
                               */
                               public void setPasswordMD5(java.lang.String param){
                            localPasswordMD5Tracker = param != null;
                                   
                                            this.localPasswordMD5=param;
                                    

                               }
                            

                        /**
                        * field for ResponsabileProcedimento
                        */

                        
                                    protected java.lang.String localResponsabileProcedimento ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResponsabileProcedimentoTracker = false ;

                           public boolean isResponsabileProcedimentoSpecified(){
                               return localResponsabileProcedimentoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResponsabileProcedimento(){
                               return localResponsabileProcedimento;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponsabileProcedimento
                               */
                               public void setResponsabileProcedimento(java.lang.String param){
                            localResponsabileProcedimentoTracker = param != null;
                                   
                                            this.localResponsabileProcedimento=param;
                                    

                               }
                            

                        /**
                        * field for IstruttorePratica
                        */

                        
                                    protected java.lang.String localIstruttorePratica ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIstruttorePraticaTracker = false ;

                           public boolean isIstruttorePraticaSpecified(){
                               return localIstruttorePraticaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIstruttorePratica(){
                               return localIstruttorePratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IstruttorePratica
                               */
                               public void setIstruttorePratica(java.lang.String param){
                            localIstruttorePraticaTracker = param != null;
                                   
                                            this.localIstruttorePratica=param;
                                    

                               }
                            

                        /**
                        * field for ListaAtti
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.EstremiAttoEstesoType[] localListaAtti ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListaAttiTracker = false ;

                           public boolean isListaAttiSpecified(){
                               return localListaAttiTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.EstremiAttoEstesoType[]
                           */
                           public  it.init.sigepro.rte.types.EstremiAttoEstesoType[] getListaAtti(){
                               return localListaAtti;
                           }

                           
                        


                               
                              /**
                               * validate the array for ListaAtti
                               */
                              protected void validateListaAtti(it.init.sigepro.rte.types.EstremiAttoEstesoType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param ListaAtti
                              */
                              public void setListaAtti(it.init.sigepro.rte.types.EstremiAttoEstesoType[] param){
                              
                                   validateListaAtti(param);

                               localListaAttiTracker = param != null;
                                      
                                      this.localListaAtti=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.EstremiAttoEstesoType
                             */
                             public void addListaAtti(it.init.sigepro.rte.types.EstremiAttoEstesoType param){
                                   if (localListaAtti == null){
                                   localListaAtti = new it.init.sigepro.rte.types.EstremiAttoEstesoType[]{};
                                   }

                            
                                 //update the setting tracker
                                localListaAttiTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localListaAtti);
                               list.add(param);
                               this.localListaAtti =
                             (it.init.sigepro.rte.types.EstremiAttoEstesoType[])list.toArray(
                            new it.init.sigepro.rte.types.EstremiAttoEstesoType[list.size()]);

                             }
                             

                        /**
                        * field for TempisticaProcedimento
                        */

                        
                                    protected it.init.sigepro.rte.types.TempisticaProcedimentoType localTempisticaProcedimento ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTempisticaProcedimentoTracker = false ;

                           public boolean isTempisticaProcedimentoSpecified(){
                               return localTempisticaProcedimentoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.TempisticaProcedimentoType
                           */
                           public  it.init.sigepro.rte.types.TempisticaProcedimentoType getTempisticaProcedimento(){
                               return localTempisticaProcedimento;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TempisticaProcedimento
                               */
                               public void setTempisticaProcedimento(it.init.sigepro.rte.types.TempisticaProcedimentoType param){
                            localTempisticaProcedimentoTracker = param != null;
                                   
                                            this.localTempisticaProcedimento=param;
                                    

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
                           namespacePrefix+":DettaglioPraticaVisuraType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "DettaglioPraticaVisuraType",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localDettaglioPratica==null){
                                                 throw new org.apache.axis2.databinding.ADBException("dettaglioPratica cannot be null!!");
                                            }
                                           localDettaglioPratica.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dettaglioPratica"),
                                               xmlWriter);
                                         if (localListaAttivitaTracker){
                                       if (localListaAttivita!=null){
                                            for (int i = 0;i < localListaAttivita.length;i++){
                                                if (localListaAttivita[i] != null){
                                                 localListaAttivita[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","listaAttivita"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("listaAttivita cannot be null!!");
                                        
                                    }
                                 }
                                            if (localStatoPratica==null){
                                                 throw new org.apache.axis2.databinding.ADBException("statoPratica cannot be null!!");
                                            }
                                           localStatoPratica.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","statoPratica"),
                                               xmlWriter);
                                         if (localStatoIterTracker){
                                            if (localStatoIter==null){
                                                 throw new org.apache.axis2.databinding.ADBException("statoIter cannot be null!!");
                                            }
                                           localStatoIter.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","statoIter"),
                                               xmlWriter);
                                        } if (localPasswordMD5Tracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "passwordMD5", xmlWriter);
                             

                                          if (localPasswordMD5==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passwordMD5 cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPasswordMD5);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResponsabileProcedimentoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "responsabileProcedimento", xmlWriter);
                             

                                          if (localResponsabileProcedimento==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("responsabileProcedimento cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResponsabileProcedimento);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIstruttorePraticaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "istruttorePratica", xmlWriter);
                             

                                          if (localIstruttorePratica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("istruttorePratica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIstruttorePratica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localListaAttiTracker){
                                       if (localListaAtti!=null){
                                            for (int i = 0;i < localListaAtti.length;i++){
                                                if (localListaAtti[i] != null){
                                                 localListaAtti[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","listaAtti"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("listaAtti cannot be null!!");
                                        
                                    }
                                 } if (localTempisticaProcedimentoTracker){
                                            if (localTempisticaProcedimento==null){
                                                 throw new org.apache.axis2.databinding.ADBException("tempisticaProcedimento cannot be null!!");
                                            }
                                           localTempisticaProcedimento.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tempisticaProcedimento"),
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
                                                                      "dettaglioPratica"));
                            
                            
                                    if (localDettaglioPratica==null){
                                         throw new org.apache.axis2.databinding.ADBException("dettaglioPratica cannot be null!!");
                                    }
                                    elementList.add(localDettaglioPratica);
                                 if (localListaAttivitaTracker){
                             if (localListaAttivita!=null) {
                                 for (int i = 0;i < localListaAttivita.length;i++){

                                    if (localListaAttivita[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "listaAttivita"));
                                         elementList.add(localListaAttivita[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("listaAttivita cannot be null!!");
                                    
                             }

                        }
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "statoPratica"));
                            
                            
                                    if (localStatoPratica==null){
                                         throw new org.apache.axis2.databinding.ADBException("statoPratica cannot be null!!");
                                    }
                                    elementList.add(localStatoPratica);
                                 if (localStatoIterTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "statoIter"));
                            
                            
                                    if (localStatoIter==null){
                                         throw new org.apache.axis2.databinding.ADBException("statoIter cannot be null!!");
                                    }
                                    elementList.add(localStatoIter);
                                } if (localPasswordMD5Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "passwordMD5"));
                                 
                                        if (localPasswordMD5 != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPasswordMD5));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passwordMD5 cannot be null!!");
                                        }
                                    } if (localResponsabileProcedimentoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "responsabileProcedimento"));
                                 
                                        if (localResponsabileProcedimento != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResponsabileProcedimento));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("responsabileProcedimento cannot be null!!");
                                        }
                                    } if (localIstruttorePraticaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "istruttorePratica"));
                                 
                                        if (localIstruttorePratica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIstruttorePratica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("istruttorePratica cannot be null!!");
                                        }
                                    } if (localListaAttiTracker){
                             if (localListaAtti!=null) {
                                 for (int i = 0;i < localListaAtti.length;i++){

                                    if (localListaAtti[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "listaAtti"));
                                         elementList.add(localListaAtti[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("listaAtti cannot be null!!");
                                    
                             }

                        } if (localTempisticaProcedimentoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "tempisticaProcedimento"));
                            
                            
                                    if (localTempisticaProcedimento==null){
                                         throw new org.apache.axis2.databinding.ADBException("tempisticaProcedimento cannot be null!!");
                                    }
                                    elementList.add(localTempisticaProcedimento);
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
        public static DettaglioPraticaVisuraType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            DettaglioPraticaVisuraType object =
                new DettaglioPraticaVisuraType();

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
                    
                            if (!"DettaglioPraticaVisuraType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DettaglioPraticaVisuraType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list2 = new java.util.ArrayList();
                    
                        java.util.ArrayList list8 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dettaglioPratica").equals(reader.getName())){
                                
                                                object.setDettaglioPratica(it.init.sigepro.rte.types.DettaglioPraticaType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","listaAttivita").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list2.add(it.init.sigepro.rte.types.DettaglioAttivitaType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone2 = false;
                                                        while(!loopDone2){
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
                                                                loopDone2 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","listaAttivita").equals(reader.getName())){
                                                                    list2.add(it.init.sigepro.rte.types.DettaglioAttivitaType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone2 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setListaAttivita((it.init.sigepro.rte.types.DettaglioAttivitaType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.DettaglioAttivitaType.class,
                                                                list2));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","statoPratica").equals(reader.getName())){
                                
                                                object.setStatoPratica(it.init.sigepro.rte.types.StatoPraticaType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","statoIter").equals(reader.getName())){
                                
                                                object.setStatoIter(it.init.sigepro.rte.types.StatoIterType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","passwordMD5").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPasswordMD5(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","responsabileProcedimento").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResponsabileProcedimento(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","istruttorePratica").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIstruttorePratica(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","listaAtti").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list8.add(it.init.sigepro.rte.types.EstremiAttoEstesoType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone8 = false;
                                                        while(!loopDone8){
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
                                                                loopDone8 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","listaAtti").equals(reader.getName())){
                                                                    list8.add(it.init.sigepro.rte.types.EstremiAttoEstesoType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone8 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setListaAtti((it.init.sigepro.rte.types.EstremiAttoEstesoType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.EstremiAttoEstesoType.class,
                                                                list8));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tempisticaProcedimento").equals(reader.getName())){
                                
                                                object.setTempisticaProcedimento(it.init.sigepro.rte.types.TempisticaProcedimentoType.Factory.parse(reader));
                                              
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
           
    