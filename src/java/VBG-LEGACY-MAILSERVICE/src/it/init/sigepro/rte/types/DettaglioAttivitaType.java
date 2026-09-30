
/**
 * DettaglioAttivitaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  DettaglioAttivitaType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class DettaglioAttivitaType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = DettaglioAttivitaType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for IdPratica
                        */

                        
                                    protected java.lang.String localIdPratica ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIdPratica(){
                               return localIdPratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdPratica
                               */
                               public void setIdPratica(java.lang.String param){
                            
                                            this.localIdPratica=param;
                                    

                               }
                            

                        /**
                        * field for IdAttivita
                        */

                        
                                    protected java.lang.String localIdAttivita ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIdAttivita(){
                               return localIdAttivita;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdAttivita
                               */
                               public void setIdAttivita(java.lang.String param){
                            
                                            this.localIdAttivita=param;
                                    

                               }
                            

                        /**
                        * field for DataAttivita
                        */

                        
                                    protected java.util.Date localDataAttivita ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataAttivitaTracker = false ;

                           public boolean isDataAttivitaSpecified(){
                               return localDataAttivitaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataAttivita(){
                               return localDataAttivita;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataAttivita
                               */
                               public void setDataAttivita(java.util.Date param){
                            localDataAttivitaTracker = param != null;
                                   
                                            this.localDataAttivita=param;
                                    

                               }
                            

                        /**
                        * field for OraDataAttivita
                        */

                        
                                    protected it.init.sigepro.rte.types.OraDataAttivita_type1 localOraDataAttivita ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOraDataAttivitaTracker = false ;

                           public boolean isOraDataAttivitaSpecified(){
                               return localOraDataAttivitaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.OraDataAttivita_type1
                           */
                           public  it.init.sigepro.rte.types.OraDataAttivita_type1 getOraDataAttivita(){
                               return localOraDataAttivita;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OraDataAttivita
                               */
                               public void setOraDataAttivita(it.init.sigepro.rte.types.OraDataAttivita_type1 param){
                            localOraDataAttivitaTracker = param != null;
                                   
                                            this.localOraDataAttivita=param;
                                    

                               }
                            

                        /**
                        * field for DataScadenza
                        */

                        
                                    protected java.util.Date localDataScadenza ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataScadenzaTracker = false ;

                           public boolean isDataScadenzaSpecified(){
                               return localDataScadenzaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataScadenza(){
                               return localDataScadenza;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataScadenza
                               */
                               public void setDataScadenza(java.util.Date param){
                            localDataScadenzaTracker = param != null;
                                   
                                            this.localDataScadenza=param;
                                    

                               }
                            

                        /**
                        * field for NumeroProtocolloGenerale
                        */

                        
                                    protected java.lang.String localNumeroProtocolloGenerale ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumeroProtocolloGeneraleTracker = false ;

                           public boolean isNumeroProtocolloGeneraleSpecified(){
                               return localNumeroProtocolloGeneraleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNumeroProtocolloGenerale(){
                               return localNumeroProtocolloGenerale;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumeroProtocolloGenerale
                               */
                               public void setNumeroProtocolloGenerale(java.lang.String param){
                            localNumeroProtocolloGeneraleTracker = param != null;
                                   
                                            this.localNumeroProtocolloGenerale=param;
                                    

                               }
                            

                        /**
                        * field for DataProtocolloGenerale
                        */

                        
                                    protected java.util.Date localDataProtocolloGenerale ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataProtocolloGeneraleTracker = false ;

                           public boolean isDataProtocolloGeneraleSpecified(){
                               return localDataProtocolloGeneraleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataProtocolloGenerale(){
                               return localDataProtocolloGenerale;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataProtocolloGenerale
                               */
                               public void setDataProtocolloGenerale(java.util.Date param){
                            localDataProtocolloGeneraleTracker = param != null;
                                   
                                            this.localDataProtocolloGenerale=param;
                                    

                               }
                            

                        /**
                        * field for TipoAttivita
                        */

                        
                                    protected it.init.sigepro.rte.types.TipoAttivitaType localTipoAttivita ;
                                

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.TipoAttivitaType
                           */
                           public  it.init.sigepro.rte.types.TipoAttivitaType getTipoAttivita(){
                               return localTipoAttivita;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TipoAttivita
                               */
                               public void setTipoAttivita(it.init.sigepro.rte.types.TipoAttivitaType param){
                            
                                            this.localTipoAttivita=param;
                                    

                               }
                            

                        /**
                        * field for Esito
                        */

                        
                                    protected boolean localEsito ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getEsito(){
                               return localEsito;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Esito
                               */
                               public void setEsito(boolean param){
                            
                                            this.localEsito=param;
                                    

                               }
                            

                        /**
                        * field for Parere
                        */

                        
                                    protected java.lang.String localParere ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localParereTracker = false ;

                           public boolean isParereSpecified(){
                               return localParereTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getParere(){
                               return localParere;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Parere
                               */
                               public void setParere(java.lang.String param){
                            localParereTracker = param != null;
                                   
                                            this.localParere=param;
                                    

                               }
                            

                        /**
                        * field for Note
                        */

                        
                                    protected java.lang.String localNote ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNoteTracker = false ;

                           public boolean isNoteSpecified(){
                               return localNoteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNote(){
                               return localNote;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Note
                               */
                               public void setNote(java.lang.String param){
                            localNoteTracker = param != null;
                                   
                                            this.localNote=param;
                                    

                               }
                            

                        /**
                        * field for AltriDati
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.ParametroType[] localAltriDati ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAltriDatiTracker = false ;

                           public boolean isAltriDatiSpecified(){
                               return localAltriDatiTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ParametroType[]
                           */
                           public  it.init.sigepro.rte.types.ParametroType[] getAltriDati(){
                               return localAltriDati;
                           }

                           
                        


                               
                              /**
                               * validate the array for AltriDati
                               */
                              protected void validateAltriDati(it.init.sigepro.rte.types.ParametroType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param AltriDati
                              */
                              public void setAltriDati(it.init.sigepro.rte.types.ParametroType[] param){
                              
                                   validateAltriDati(param);

                               localAltriDatiTracker = param != null;
                                      
                                      this.localAltriDati=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.ParametroType
                             */
                             public void addAltriDati(it.init.sigepro.rte.types.ParametroType param){
                                   if (localAltriDati == null){
                                   localAltriDati = new it.init.sigepro.rte.types.ParametroType[]{};
                                   }

                            
                                 //update the setting tracker
                                localAltriDatiTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localAltriDati);
                               list.add(param);
                               this.localAltriDati =
                             (it.init.sigepro.rte.types.ParametroType[])list.toArray(
                            new it.init.sigepro.rte.types.ParametroType[list.size()]);

                             }
                             

                        /**
                        * field for Documenti
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.DocumentiType[] localDocumenti ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDocumentiTracker = false ;

                           public boolean isDocumentiSpecified(){
                               return localDocumentiTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DocumentiType[]
                           */
                           public  it.init.sigepro.rte.types.DocumentiType[] getDocumenti(){
                               return localDocumenti;
                           }

                           
                        


                               
                              /**
                               * validate the array for Documenti
                               */
                              protected void validateDocumenti(it.init.sigepro.rte.types.DocumentiType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Documenti
                              */
                              public void setDocumenti(it.init.sigepro.rte.types.DocumentiType[] param){
                              
                                   validateDocumenti(param);

                               localDocumentiTracker = param != null;
                                      
                                      this.localDocumenti=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.DocumentiType
                             */
                             public void addDocumenti(it.init.sigepro.rte.types.DocumentiType param){
                                   if (localDocumenti == null){
                                   localDocumenti = new it.init.sigepro.rte.types.DocumentiType[]{};
                                   }

                            
                                 //update the setting tracker
                                localDocumentiTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localDocumenti);
                               list.add(param);
                               this.localDocumenti =
                             (it.init.sigepro.rte.types.DocumentiType[])list.toArray(
                            new it.init.sigepro.rte.types.DocumentiType[list.size()]);

                             }
                             

                        /**
                        * field for Procedimenti
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.ProcedimentoType[] localProcedimenti ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProcedimentiTracker = false ;

                           public boolean isProcedimentiSpecified(){
                               return localProcedimentiTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ProcedimentoType[]
                           */
                           public  it.init.sigepro.rte.types.ProcedimentoType[] getProcedimenti(){
                               return localProcedimenti;
                           }

                           
                        


                               
                              /**
                               * validate the array for Procedimenti
                               */
                              protected void validateProcedimenti(it.init.sigepro.rte.types.ProcedimentoType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Procedimenti
                              */
                              public void setProcedimenti(it.init.sigepro.rte.types.ProcedimentoType[] param){
                              
                                   validateProcedimenti(param);

                               localProcedimentiTracker = param != null;
                                      
                                      this.localProcedimenti=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.ProcedimentoType
                             */
                             public void addProcedimenti(it.init.sigepro.rte.types.ProcedimentoType param){
                                   if (localProcedimenti == null){
                                   localProcedimenti = new it.init.sigepro.rte.types.ProcedimentoType[]{};
                                   }

                            
                                 //update the setting tracker
                                localProcedimentiTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localProcedimenti);
                               list.add(param);
                               this.localProcedimenti =
                             (it.init.sigepro.rte.types.ProcedimentoType[])list.toArray(
                            new it.init.sigepro.rte.types.ProcedimentoType[list.size()]);

                             }
                             

                        /**
                        * field for DocumentiDaSostituire
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.SostituzioniDocumentaliType[] localDocumentiDaSostituire ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDocumentiDaSostituireTracker = false ;

                           public boolean isDocumentiDaSostituireSpecified(){
                               return localDocumentiDaSostituireTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.SostituzioniDocumentaliType[]
                           */
                           public  it.init.sigepro.rte.types.SostituzioniDocumentaliType[] getDocumentiDaSostituire(){
                               return localDocumentiDaSostituire;
                           }

                           
                        


                               
                              /**
                               * validate the array for DocumentiDaSostituire
                               */
                              protected void validateDocumentiDaSostituire(it.init.sigepro.rte.types.SostituzioniDocumentaliType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param DocumentiDaSostituire
                              */
                              public void setDocumentiDaSostituire(it.init.sigepro.rte.types.SostituzioniDocumentaliType[] param){
                              
                                   validateDocumentiDaSostituire(param);

                               localDocumentiDaSostituireTracker = param != null;
                                      
                                      this.localDocumentiDaSostituire=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.SostituzioniDocumentaliType
                             */
                             public void addDocumentiDaSostituire(it.init.sigepro.rte.types.SostituzioniDocumentaliType param){
                                   if (localDocumentiDaSostituire == null){
                                   localDocumentiDaSostituire = new it.init.sigepro.rte.types.SostituzioniDocumentaliType[]{};
                                   }

                            
                                 //update the setting tracker
                                localDocumentiDaSostituireTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localDocumentiDaSostituire);
                               list.add(param);
                               this.localDocumentiDaSostituire =
                             (it.init.sigepro.rte.types.SostituzioniDocumentaliType[])list.toArray(
                            new it.init.sigepro.rte.types.SostituzioniDocumentaliType[list.size()]);

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
                           namespacePrefix+":DettaglioAttivitaType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "DettaglioAttivitaType",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "idPratica", xmlWriter);
                             

                                          if (localIdPratica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("idPratica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdPratica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "idAttivita", xmlWriter);
                             

                                          if (localIdAttivita==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("idAttivita cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdAttivita);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localDataAttivitaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataAttivita", xmlWriter);
                             

                                          if (localDataAttivita==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataAttivita cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataAttivita));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOraDataAttivitaTracker){
                                            if (localOraDataAttivita==null){
                                                 throw new org.apache.axis2.databinding.ADBException("oraDataAttivita cannot be null!!");
                                            }
                                           localOraDataAttivita.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oraDataAttivita"),
                                               xmlWriter);
                                        } if (localDataScadenzaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataScadenza", xmlWriter);
                             

                                          if (localDataScadenza==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataScadenza cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataScadenza));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumeroProtocolloGeneraleTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "numeroProtocolloGenerale", xmlWriter);
                             

                                          if (localNumeroProtocolloGenerale==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("numeroProtocolloGenerale cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNumeroProtocolloGenerale);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDataProtocolloGeneraleTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataProtocolloGenerale", xmlWriter);
                             

                                          if (localDataProtocolloGenerale==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataProtocolloGenerale cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataProtocolloGenerale));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                            if (localTipoAttivita==null){
                                                 throw new org.apache.axis2.databinding.ADBException("tipoAttivita cannot be null!!");
                                            }
                                           localTipoAttivita.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tipoAttivita"),
                                               xmlWriter);
                                        
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "esito", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("esito cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsito));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localParereTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "parere", xmlWriter);
                             

                                          if (localParere==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("parere cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localParere);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNoteTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "note", xmlWriter);
                             

                                          if (localNote==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("note cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNote);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAltriDatiTracker){
                                       if (localAltriDati!=null){
                                            for (int i = 0;i < localAltriDati.length;i++){
                                                if (localAltriDati[i] != null){
                                                 localAltriDati[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriDati"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("altriDati cannot be null!!");
                                        
                                    }
                                 } if (localDocumentiTracker){
                                       if (localDocumenti!=null){
                                            for (int i = 0;i < localDocumenti.length;i++){
                                                if (localDocumenti[i] != null){
                                                 localDocumenti[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documenti"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("documenti cannot be null!!");
                                        
                                    }
                                 } if (localProcedimentiTracker){
                                       if (localProcedimenti!=null){
                                            for (int i = 0;i < localProcedimenti.length;i++){
                                                if (localProcedimenti[i] != null){
                                                 localProcedimenti[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procedimenti"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("procedimenti cannot be null!!");
                                        
                                    }
                                 } if (localDocumentiDaSostituireTracker){
                                       if (localDocumentiDaSostituire!=null){
                                            for (int i = 0;i < localDocumentiDaSostituire.length;i++){
                                                if (localDocumentiDaSostituire[i] != null){
                                                 localDocumentiDaSostituire[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documentiDaSostituire"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("documentiDaSostituire cannot be null!!");
                                        
                                    }
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
                                                                      "idPratica"));
                                 
                                        if (localIdPratica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdPratica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("idPratica cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "idAttivita"));
                                 
                                        if (localIdAttivita != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdAttivita));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("idAttivita cannot be null!!");
                                        }
                                     if (localDataAttivitaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataAttivita"));
                                 
                                        if (localDataAttivita != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataAttivita));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataAttivita cannot be null!!");
                                        }
                                    } if (localOraDataAttivitaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "oraDataAttivita"));
                            
                            
                                    if (localOraDataAttivita==null){
                                         throw new org.apache.axis2.databinding.ADBException("oraDataAttivita cannot be null!!");
                                    }
                                    elementList.add(localOraDataAttivita);
                                } if (localDataScadenzaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataScadenza"));
                                 
                                        if (localDataScadenza != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataScadenza));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataScadenza cannot be null!!");
                                        }
                                    } if (localNumeroProtocolloGeneraleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "numeroProtocolloGenerale"));
                                 
                                        if (localNumeroProtocolloGenerale != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumeroProtocolloGenerale));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("numeroProtocolloGenerale cannot be null!!");
                                        }
                                    } if (localDataProtocolloGeneraleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataProtocolloGenerale"));
                                 
                                        if (localDataProtocolloGenerale != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataProtocolloGenerale));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataProtocolloGenerale cannot be null!!");
                                        }
                                    }
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "tipoAttivita"));
                            
                            
                                    if (localTipoAttivita==null){
                                         throw new org.apache.axis2.databinding.ADBException("tipoAttivita cannot be null!!");
                                    }
                                    elementList.add(localTipoAttivita);
                                
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "esito"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsito));
                             if (localParereTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "parere"));
                                 
                                        if (localParere != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localParere));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("parere cannot be null!!");
                                        }
                                    } if (localNoteTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "note"));
                                 
                                        if (localNote != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNote));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("note cannot be null!!");
                                        }
                                    } if (localAltriDatiTracker){
                             if (localAltriDati!=null) {
                                 for (int i = 0;i < localAltriDati.length;i++){

                                    if (localAltriDati[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "altriDati"));
                                         elementList.add(localAltriDati[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("altriDati cannot be null!!");
                                    
                             }

                        } if (localDocumentiTracker){
                             if (localDocumenti!=null) {
                                 for (int i = 0;i < localDocumenti.length;i++){

                                    if (localDocumenti[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "documenti"));
                                         elementList.add(localDocumenti[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("documenti cannot be null!!");
                                    
                             }

                        } if (localProcedimentiTracker){
                             if (localProcedimenti!=null) {
                                 for (int i = 0;i < localProcedimenti.length;i++){

                                    if (localProcedimenti[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "procedimenti"));
                                         elementList.add(localProcedimenti[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("procedimenti cannot be null!!");
                                    
                             }

                        } if (localDocumentiDaSostituireTracker){
                             if (localDocumentiDaSostituire!=null) {
                                 for (int i = 0;i < localDocumentiDaSostituire.length;i++){

                                    if (localDocumentiDaSostituire[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "documentiDaSostituire"));
                                         elementList.add(localDocumentiDaSostituire[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("documentiDaSostituire cannot be null!!");
                                    
                             }

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
        public static DettaglioAttivitaType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            DettaglioAttivitaType object =
                new DettaglioAttivitaType();

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
                    
                            if (!"DettaglioAttivitaType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DettaglioAttivitaType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list12 = new java.util.ArrayList();
                    
                        java.util.ArrayList list13 = new java.util.ArrayList();
                    
                        java.util.ArrayList list14 = new java.util.ArrayList();
                    
                        java.util.ArrayList list15 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","idPratica").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdPratica(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","idAttivita").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdAttivita(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataAttivita").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataAttivita(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oraDataAttivita").equals(reader.getName())){
                                
                                                object.setOraDataAttivita(it.init.sigepro.rte.types.OraDataAttivita_type1.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataScadenza").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataScadenza(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","numeroProtocolloGenerale").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumeroProtocolloGenerale(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataProtocolloGenerale").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataProtocolloGenerale(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tipoAttivita").equals(reader.getName())){
                                
                                                object.setTipoAttivita(it.init.sigepro.rte.types.TipoAttivitaType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","esito").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEsito(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","parere").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setParere(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","note").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNote(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriDati").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list12.add(it.init.sigepro.rte.types.ParametroType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone12 = false;
                                                        while(!loopDone12){
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
                                                                loopDone12 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriDati").equals(reader.getName())){
                                                                    list12.add(it.init.sigepro.rte.types.ParametroType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone12 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setAltriDati((it.init.sigepro.rte.types.ParametroType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.ParametroType.class,
                                                                list12));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documenti").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list13.add(it.init.sigepro.rte.types.DocumentiType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone13 = false;
                                                        while(!loopDone13){
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
                                                                loopDone13 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documenti").equals(reader.getName())){
                                                                    list13.add(it.init.sigepro.rte.types.DocumentiType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone13 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setDocumenti((it.init.sigepro.rte.types.DocumentiType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.DocumentiType.class,
                                                                list13));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procedimenti").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list14.add(it.init.sigepro.rte.types.ProcedimentoType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone14 = false;
                                                        while(!loopDone14){
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
                                                                loopDone14 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procedimenti").equals(reader.getName())){
                                                                    list14.add(it.init.sigepro.rte.types.ProcedimentoType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone14 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setProcedimenti((it.init.sigepro.rte.types.ProcedimentoType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.ProcedimentoType.class,
                                                                list14));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documentiDaSostituire").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list15.add(it.init.sigepro.rte.types.SostituzioniDocumentaliType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone15 = false;
                                                        while(!loopDone15){
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
                                                                loopDone15 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documentiDaSostituire").equals(reader.getName())){
                                                                    list15.add(it.init.sigepro.rte.types.SostituzioniDocumentaliType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone15 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setDocumentiDaSostituire((it.init.sigepro.rte.types.SostituzioniDocumentaliType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.SostituzioniDocumentaliType.class,
                                                                list15));
                                                            
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
           
    