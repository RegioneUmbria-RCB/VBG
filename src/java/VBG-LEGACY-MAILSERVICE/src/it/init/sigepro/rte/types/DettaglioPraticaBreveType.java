
/**
 * DettaglioPraticaBreveType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  DettaglioPraticaBreveType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class DettaglioPraticaBreveType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = DettaglioPraticaBreveType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for CodiceComune
                        */

                        
                                    protected it.init.sigepro.rte.types.ComuneType localCodiceComune ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCodiceComuneTracker = false ;

                           public boolean isCodiceComuneSpecified(){
                               return localCodiceComuneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ComuneType
                           */
                           public  it.init.sigepro.rte.types.ComuneType getCodiceComune(){
                               return localCodiceComune;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodiceComune
                               */
                               public void setCodiceComune(it.init.sigepro.rte.types.ComuneType param){
                            localCodiceComuneTracker = param != null;
                                   
                                            this.localCodiceComune=param;
                                    

                               }
                            

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
                        * field for NumeroPratica
                        */

                        
                                    protected java.lang.String localNumeroPratica ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNumeroPratica(){
                               return localNumeroPratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumeroPratica
                               */
                               public void setNumeroPratica(java.lang.String param){
                            
                                            this.localNumeroPratica=param;
                                    

                               }
                            

                        /**
                        * field for DataPratica
                        */

                        
                                    protected java.util.Date localDataPratica ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataPratica(){
                               return localDataPratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataPratica
                               */
                               public void setDataPratica(java.util.Date param){
                            
                                            this.localDataPratica=param;
                                    

                               }
                            

                        /**
                        * field for OraDataPratica
                        */

                        
                                    protected it.init.sigepro.rte.types.OraDataPratica_type3 localOraDataPratica ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOraDataPraticaTracker = false ;

                           public boolean isOraDataPraticaSpecified(){
                               return localOraDataPraticaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.OraDataPratica_type3
                           */
                           public  it.init.sigepro.rte.types.OraDataPratica_type3 getOraDataPratica(){
                               return localOraDataPratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OraDataPratica
                               */
                               public void setOraDataPratica(it.init.sigepro.rte.types.OraDataPratica_type3 param){
                            localOraDataPraticaTracker = param != null;
                                   
                                            this.localOraDataPratica=param;
                                    

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
                        * field for Richiedente
                        */

                        
                                    protected it.init.sigepro.rte.types.RichiedenteType localRichiedente ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRichiedenteTracker = false ;

                           public boolean isRichiedenteSpecified(){
                               return localRichiedenteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.RichiedenteType
                           */
                           public  it.init.sigepro.rte.types.RichiedenteType getRichiedente(){
                               return localRichiedente;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Richiedente
                               */
                               public void setRichiedente(it.init.sigepro.rte.types.RichiedenteType param){
                            localRichiedenteTracker = param != null;
                                   
                                            this.localRichiedente=param;
                                    

                               }
                            

                        /**
                        * field for AziendaRichiedente
                        */

                        
                                    protected it.init.sigepro.rte.types.PersonaGiuridicaType localAziendaRichiedente ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAziendaRichiedenteTracker = false ;

                           public boolean isAziendaRichiedenteSpecified(){
                               return localAziendaRichiedenteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.PersonaGiuridicaType
                           */
                           public  it.init.sigepro.rte.types.PersonaGiuridicaType getAziendaRichiedente(){
                               return localAziendaRichiedente;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AziendaRichiedente
                               */
                               public void setAziendaRichiedente(it.init.sigepro.rte.types.PersonaGiuridicaType param){
                            localAziendaRichiedenteTracker = param != null;
                                   
                                            this.localAziendaRichiedente=param;
                                    

                               }
                            

                        /**
                        * field for Intermediario
                        */

                        
                                    protected it.init.sigepro.rte.types.AnagrafeType localIntermediario ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIntermediarioTracker = false ;

                           public boolean isIntermediarioSpecified(){
                               return localIntermediarioTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.AnagrafeType
                           */
                           public  it.init.sigepro.rte.types.AnagrafeType getIntermediario(){
                               return localIntermediario;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Intermediario
                               */
                               public void setIntermediario(it.init.sigepro.rte.types.AnagrafeType param){
                            localIntermediarioTracker = param != null;
                                   
                                            this.localIntermediario=param;
                                    

                               }
                            

                        /**
                        * field for Oggetto
                        */

                        
                                    protected java.lang.String localOggetto ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOggettoTracker = false ;

                           public boolean isOggettoSpecified(){
                               return localOggettoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOggetto(){
                               return localOggetto;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Oggetto
                               */
                               public void setOggetto(java.lang.String param){
                            localOggettoTracker = param != null;
                                   
                                            this.localOggetto=param;
                                    

                               }
                            

                        /**
                        * field for Intervento
                        */

                        
                                    protected it.init.sigepro.rte.types.InterventoType localIntervento ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInterventoTracker = false ;

                           public boolean isInterventoSpecified(){
                               return localInterventoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.InterventoType
                           */
                           public  it.init.sigepro.rte.types.InterventoType getIntervento(){
                               return localIntervento;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Intervento
                               */
                               public void setIntervento(it.init.sigepro.rte.types.InterventoType param){
                            localInterventoTracker = param != null;
                                   
                                            this.localIntervento=param;
                                    

                               }
                            

                        /**
                        * field for Localizzazione
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.LocalizzazioneNelComuneType[] localLocalizzazione ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLocalizzazioneTracker = false ;

                           public boolean isLocalizzazioneSpecified(){
                               return localLocalizzazioneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.LocalizzazioneNelComuneType[]
                           */
                           public  it.init.sigepro.rte.types.LocalizzazioneNelComuneType[] getLocalizzazione(){
                               return localLocalizzazione;
                           }

                           
                        


                               
                              /**
                               * validate the array for Localizzazione
                               */
                              protected void validateLocalizzazione(it.init.sigepro.rte.types.LocalizzazioneNelComuneType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Localizzazione
                              */
                              public void setLocalizzazione(it.init.sigepro.rte.types.LocalizzazioneNelComuneType[] param){
                              
                                   validateLocalizzazione(param);

                               localLocalizzazioneTracker = param != null;
                                      
                                      this.localLocalizzazione=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.LocalizzazioneNelComuneType
                             */
                             public void addLocalizzazione(it.init.sigepro.rte.types.LocalizzazioneNelComuneType param){
                                   if (localLocalizzazione == null){
                                   localLocalizzazione = new it.init.sigepro.rte.types.LocalizzazioneNelComuneType[]{};
                                   }

                            
                                 //update the setting tracker
                                localLocalizzazioneTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localLocalizzazione);
                               list.add(param);
                               this.localLocalizzazione =
                             (it.init.sigepro.rte.types.LocalizzazioneNelComuneType[])list.toArray(
                            new it.init.sigepro.rte.types.LocalizzazioneNelComuneType[list.size()]);

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
                           namespacePrefix+":DettaglioPraticaBreveType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "DettaglioPraticaBreveType",
                           xmlWriter);
                   }

               
                   }
                if (localCodiceComuneTracker){
                                            if (localCodiceComune==null){
                                                 throw new org.apache.axis2.databinding.ADBException("codiceComune cannot be null!!");
                                            }
                                           localCodiceComune.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceComune"),
                                               xmlWriter);
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
                                    writeStartElement(null, namespace, "numeroPratica", xmlWriter);
                             

                                          if (localNumeroPratica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("numeroPratica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNumeroPratica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataPratica", xmlWriter);
                             

                                          if (localDataPratica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataPratica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataPratica));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localOraDataPraticaTracker){
                                            if (localOraDataPratica==null){
                                                 throw new org.apache.axis2.databinding.ADBException("oraDataPratica cannot be null!!");
                                            }
                                           localOraDataPratica.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oraDataPratica"),
                                               xmlWriter);
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
                             } if (localRichiedenteTracker){
                                            if (localRichiedente==null){
                                                 throw new org.apache.axis2.databinding.ADBException("richiedente cannot be null!!");
                                            }
                                           localRichiedente.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","richiedente"),
                                               xmlWriter);
                                        } if (localAziendaRichiedenteTracker){
                                            if (localAziendaRichiedente==null){
                                                 throw new org.apache.axis2.databinding.ADBException("aziendaRichiedente cannot be null!!");
                                            }
                                           localAziendaRichiedente.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","aziendaRichiedente"),
                                               xmlWriter);
                                        } if (localIntermediarioTracker){
                                            if (localIntermediario==null){
                                                 throw new org.apache.axis2.databinding.ADBException("intermediario cannot be null!!");
                                            }
                                           localIntermediario.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","intermediario"),
                                               xmlWriter);
                                        } if (localOggettoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "oggetto", xmlWriter);
                             

                                          if (localOggetto==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("oggetto cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOggetto);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInterventoTracker){
                                            if (localIntervento==null){
                                                 throw new org.apache.axis2.databinding.ADBException("intervento cannot be null!!");
                                            }
                                           localIntervento.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","intervento"),
                                               xmlWriter);
                                        } if (localLocalizzazioneTracker){
                                       if (localLocalizzazione!=null){
                                            for (int i = 0;i < localLocalizzazione.length;i++){
                                                if (localLocalizzazione[i] != null){
                                                 localLocalizzazione[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","localizzazione"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("localizzazione cannot be null!!");
                                        
                                    }
                                 }
                                            if (localStatoPratica==null){
                                                 throw new org.apache.axis2.databinding.ADBException("statoPratica cannot be null!!");
                                            }
                                           localStatoPratica.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","statoPratica"),
                                               xmlWriter);
                                        
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

                 if (localCodiceComuneTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceComune"));
                            
                            
                                    if (localCodiceComune==null){
                                         throw new org.apache.axis2.databinding.ADBException("codiceComune cannot be null!!");
                                    }
                                    elementList.add(localCodiceComune);
                                }
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "idPratica"));
                                 
                                        if (localIdPratica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdPratica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("idPratica cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "numeroPratica"));
                                 
                                        if (localNumeroPratica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumeroPratica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("numeroPratica cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataPratica"));
                                 
                                        if (localDataPratica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataPratica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataPratica cannot be null!!");
                                        }
                                     if (localOraDataPraticaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "oraDataPratica"));
                            
                            
                                    if (localOraDataPratica==null){
                                         throw new org.apache.axis2.databinding.ADBException("oraDataPratica cannot be null!!");
                                    }
                                    elementList.add(localOraDataPratica);
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
                                    } if (localRichiedenteTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "richiedente"));
                            
                            
                                    if (localRichiedente==null){
                                         throw new org.apache.axis2.databinding.ADBException("richiedente cannot be null!!");
                                    }
                                    elementList.add(localRichiedente);
                                } if (localAziendaRichiedenteTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "aziendaRichiedente"));
                            
                            
                                    if (localAziendaRichiedente==null){
                                         throw new org.apache.axis2.databinding.ADBException("aziendaRichiedente cannot be null!!");
                                    }
                                    elementList.add(localAziendaRichiedente);
                                } if (localIntermediarioTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "intermediario"));
                            
                            
                                    if (localIntermediario==null){
                                         throw new org.apache.axis2.databinding.ADBException("intermediario cannot be null!!");
                                    }
                                    elementList.add(localIntermediario);
                                } if (localOggettoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "oggetto"));
                                 
                                        if (localOggetto != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOggetto));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("oggetto cannot be null!!");
                                        }
                                    } if (localInterventoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "intervento"));
                            
                            
                                    if (localIntervento==null){
                                         throw new org.apache.axis2.databinding.ADBException("intervento cannot be null!!");
                                    }
                                    elementList.add(localIntervento);
                                } if (localLocalizzazioneTracker){
                             if (localLocalizzazione!=null) {
                                 for (int i = 0;i < localLocalizzazione.length;i++){

                                    if (localLocalizzazione[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "localizzazione"));
                                         elementList.add(localLocalizzazione[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("localizzazione cannot be null!!");
                                    
                             }

                        }
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "statoPratica"));
                            
                            
                                    if (localStatoPratica==null){
                                         throw new org.apache.axis2.databinding.ADBException("statoPratica cannot be null!!");
                                    }
                                    elementList.add(localStatoPratica);
                                

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
        public static DettaglioPraticaBreveType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            DettaglioPraticaBreveType object =
                new DettaglioPraticaBreveType();

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
                    
                            if (!"DettaglioPraticaBreveType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DettaglioPraticaBreveType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list13 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceComune").equals(reader.getName())){
                                
                                                object.setCodiceComune(it.init.sigepro.rte.types.ComuneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","numeroPratica").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumeroPratica(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataPratica").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataPratica(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oraDataPratica").equals(reader.getName())){
                                
                                                object.setOraDataPratica(it.init.sigepro.rte.types.OraDataPratica_type3.Factory.parse(reader));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","richiedente").equals(reader.getName())){
                                
                                                object.setRichiedente(it.init.sigepro.rte.types.RichiedenteType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","aziendaRichiedente").equals(reader.getName())){
                                
                                                object.setAziendaRichiedente(it.init.sigepro.rte.types.PersonaGiuridicaType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","intermediario").equals(reader.getName())){
                                
                                                object.setIntermediario(it.init.sigepro.rte.types.AnagrafeType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oggetto").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOggetto(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","intervento").equals(reader.getName())){
                                
                                                object.setIntervento(it.init.sigepro.rte.types.InterventoType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","localizzazione").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list13.add(it.init.sigepro.rte.types.LocalizzazioneNelComuneType.Factory.parse(reader));
                                                                
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
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","localizzazione").equals(reader.getName())){
                                                                    list13.add(it.init.sigepro.rte.types.LocalizzazioneNelComuneType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone13 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setLocalizzazione((it.init.sigepro.rte.types.LocalizzazioneNelComuneType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.LocalizzazioneNelComuneType.class,
                                                                list13));
                                                            
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
           
    