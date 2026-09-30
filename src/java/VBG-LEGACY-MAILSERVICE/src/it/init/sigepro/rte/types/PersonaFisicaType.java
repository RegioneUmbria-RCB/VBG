
/**
 * PersonaFisicaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  PersonaFisicaType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class PersonaFisicaType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = PersonaFisicaType
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
                        * field for Nome
                        */

                        
                                    protected java.lang.String localNome ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNome(){
                               return localNome;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Nome
                               */
                               public void setNome(java.lang.String param){
                            
                                            this.localNome=param;
                                    

                               }
                            

                        /**
                        * field for Cognome
                        */

                        
                                    protected java.lang.String localCognome ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCognome(){
                               return localCognome;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Cognome
                               */
                               public void setCognome(java.lang.String param){
                            
                                            this.localCognome=param;
                                    

                               }
                            

                        /**
                        * field for Titolo
                        */

                        
                                    protected java.lang.String localTitolo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTitoloTracker = false ;

                           public boolean isTitoloSpecified(){
                               return localTitoloTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTitolo(){
                               return localTitolo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Titolo
                               */
                               public void setTitolo(java.lang.String param){
                            localTitoloTracker = param != null;
                                   
                                            this.localTitolo=param;
                                    

                               }
                            

                        /**
                        * field for Sesso
                        */

                        
                                    protected java.lang.String localSesso ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSessoTracker = false ;

                           public boolean isSessoSpecified(){
                               return localSessoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSesso(){
                               return localSesso;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Sesso
                               */
                               public void setSesso(java.lang.String param){
                            localSessoTracker = param != null;
                                   
                                            this.localSesso=param;
                                    

                               }
                            

                        /**
                        * field for DataNascita
                        */

                        
                                    protected java.util.Date localDataNascita ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataNascitaTracker = false ;

                           public boolean isDataNascitaSpecified(){
                               return localDataNascitaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataNascita(){
                               return localDataNascita;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataNascita
                               */
                               public void setDataNascita(java.util.Date param){
                            localDataNascitaTracker = param != null;
                                   
                                            this.localDataNascita=param;
                                    

                               }
                            

                        /**
                        * field for ComuneNascita
                        */

                        
                                    protected it.init.sigepro.rte.types.ComuneType localComuneNascita ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localComuneNascitaTracker = false ;

                           public boolean isComuneNascitaSpecified(){
                               return localComuneNascitaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ComuneType
                           */
                           public  it.init.sigepro.rte.types.ComuneType getComuneNascita(){
                               return localComuneNascita;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ComuneNascita
                               */
                               public void setComuneNascita(it.init.sigepro.rte.types.ComuneType param){
                            localComuneNascitaTracker = param != null;
                                   
                                            this.localComuneNascita=param;
                                    

                               }
                            

                        /**
                        * field for Residenza
                        */

                        
                                    protected it.init.sigepro.rte.types.LocalizzazioneType localResidenza ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidenzaTracker = false ;

                           public boolean isResidenzaSpecified(){
                               return localResidenzaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.LocalizzazioneType
                           */
                           public  it.init.sigepro.rte.types.LocalizzazioneType getResidenza(){
                               return localResidenza;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Residenza
                               */
                               public void setResidenza(it.init.sigepro.rte.types.LocalizzazioneType param){
                            localResidenzaTracker = param != null;
                                   
                                            this.localResidenza=param;
                                    

                               }
                            

                        /**
                        * field for Corrispondenza
                        */

                        
                                    protected it.init.sigepro.rte.types.LocalizzazioneType localCorrispondenza ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCorrispondenzaTracker = false ;

                           public boolean isCorrispondenzaSpecified(){
                               return localCorrispondenzaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.LocalizzazioneType
                           */
                           public  it.init.sigepro.rte.types.LocalizzazioneType getCorrispondenza(){
                               return localCorrispondenza;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Corrispondenza
                               */
                               public void setCorrispondenza(it.init.sigepro.rte.types.LocalizzazioneType param){
                            localCorrispondenzaTracker = param != null;
                                   
                                            this.localCorrispondenza=param;
                                    

                               }
                            

                        /**
                        * field for Cittadinanza
                        */

                        
                                    protected it.init.sigepro.rte.types.CittadinanzaType localCittadinanza ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCittadinanzaTracker = false ;

                           public boolean isCittadinanzaSpecified(){
                               return localCittadinanzaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.CittadinanzaType
                           */
                           public  it.init.sigepro.rte.types.CittadinanzaType getCittadinanza(){
                               return localCittadinanza;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Cittadinanza
                               */
                               public void setCittadinanza(it.init.sigepro.rte.types.CittadinanzaType param){
                            localCittadinanzaTracker = param != null;
                                   
                                            this.localCittadinanza=param;
                                    

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
                        * field for Procura
                        */

                        
                                    protected it.init.sigepro.rte.types.DocumentiType localProcura ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProcuraTracker = false ;

                           public boolean isProcuraSpecified(){
                               return localProcuraTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DocumentiType
                           */
                           public  it.init.sigepro.rte.types.DocumentiType getProcura(){
                               return localProcura;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Procura
                               */
                               public void setProcura(it.init.sigepro.rte.types.DocumentiType param){
                            localProcuraTracker = param != null;
                                   
                                            this.localProcura=param;
                                    

                               }
                            

                        /**
                        * field for Telefono
                        */

                        
                                    protected java.lang.String localTelefono ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelefonoTracker = false ;

                           public boolean isTelefonoSpecified(){
                               return localTelefonoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelefono(){
                               return localTelefono;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Telefono
                               */
                               public void setTelefono(java.lang.String param){
                            localTelefonoTracker = param != null;
                                   
                                            this.localTelefono=param;
                                    

                               }
                            

                        /**
                        * field for Email
                        */

                        
                                    protected java.lang.String localEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEmailTracker = false ;

                           public boolean isEmailSpecified(){
                               return localEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEmail(){
                               return localEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Email
                               */
                               public void setEmail(java.lang.String param){
                            localEmailTracker = param != null;
                                   
                                            this.localEmail=param;
                                    

                               }
                            

                        /**
                        * field for Pec
                        */

                        
                                    protected java.lang.String localPec ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPecTracker = false ;

                           public boolean isPecSpecified(){
                               return localPecTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPec(){
                               return localPec;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Pec
                               */
                               public void setPec(java.lang.String param){
                            localPecTracker = param != null;
                                   
                                            this.localPec=param;
                                    

                               }
                            

                        /**
                        * field for TelefonoCellulare
                        */

                        
                                    protected java.lang.String localTelefonoCellulare ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelefonoCellulareTracker = false ;

                           public boolean isTelefonoCellulareSpecified(){
                               return localTelefonoCellulareTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelefonoCellulare(){
                               return localTelefonoCellulare;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelefonoCellulare
                               */
                               public void setTelefonoCellulare(java.lang.String param){
                            localTelefonoCellulareTracker = param != null;
                                   
                                            this.localTelefonoCellulare=param;
                                    

                               }
                            

                        /**
                        * field for DatiIscrizioneAlbo
                        */

                        
                                    protected it.init.sigepro.rte.types.DatiIscrizioneAlboType localDatiIscrizioneAlbo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDatiIscrizioneAlboTracker = false ;

                           public boolean isDatiIscrizioneAlboSpecified(){
                               return localDatiIscrizioneAlboTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DatiIscrizioneAlboType
                           */
                           public  it.init.sigepro.rte.types.DatiIscrizioneAlboType getDatiIscrizioneAlbo(){
                               return localDatiIscrizioneAlbo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DatiIscrizioneAlbo
                               */
                               public void setDatiIscrizioneAlbo(it.init.sigepro.rte.types.DatiIscrizioneAlboType param){
                            localDatiIscrizioneAlboTracker = param != null;
                                   
                                            this.localDatiIscrizioneAlbo=param;
                                    

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
                           namespacePrefix+":PersonaFisicaType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "PersonaFisicaType",
                           xmlWriter);
                   }

               
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
                             
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "nome", xmlWriter);
                             

                                          if (localNome==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nome cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNome);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "cognome", xmlWriter);
                             

                                          if (localCognome==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cognome cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCognome);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localTitoloTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "titolo", xmlWriter);
                             

                                          if (localTitolo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("titolo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTitolo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSessoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "sesso", xmlWriter);
                             

                                          if (localSesso==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sesso cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSesso);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDataNascitaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataNascita", xmlWriter);
                             

                                          if (localDataNascita==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataNascita cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataNascita));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localComuneNascitaTracker){
                                            if (localComuneNascita==null){
                                                 throw new org.apache.axis2.databinding.ADBException("comuneNascita cannot be null!!");
                                            }
                                           localComuneNascita.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","comuneNascita"),
                                               xmlWriter);
                                        } if (localResidenzaTracker){
                                            if (localResidenza==null){
                                                 throw new org.apache.axis2.databinding.ADBException("residenza cannot be null!!");
                                            }
                                           localResidenza.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","residenza"),
                                               xmlWriter);
                                        } if (localCorrispondenzaTracker){
                                            if (localCorrispondenza==null){
                                                 throw new org.apache.axis2.databinding.ADBException("corrispondenza cannot be null!!");
                                            }
                                           localCorrispondenza.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","corrispondenza"),
                                               xmlWriter);
                                        } if (localCittadinanzaTracker){
                                            if (localCittadinanza==null){
                                                 throw new org.apache.axis2.databinding.ADBException("cittadinanza cannot be null!!");
                                            }
                                           localCittadinanza.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","cittadinanza"),
                                               xmlWriter);
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
                                 } if (localProcuraTracker){
                                            if (localProcura==null){
                                                 throw new org.apache.axis2.databinding.ADBException("procura cannot be null!!");
                                            }
                                           localProcura.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procura"),
                                               xmlWriter);
                                        } if (localTelefonoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "telefono", xmlWriter);
                             

                                          if (localTelefono==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telefono cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelefono);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEmailTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "email", xmlWriter);
                             

                                          if (localEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPecTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "pec", xmlWriter);
                             

                                          if (localPec==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("pec cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPec);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelefonoCellulareTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "telefonoCellulare", xmlWriter);
                             

                                          if (localTelefonoCellulare==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telefonoCellulare cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelefonoCellulare);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDatiIscrizioneAlboTracker){
                                            if (localDatiIscrizioneAlbo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("datiIscrizioneAlbo cannot be null!!");
                                            }
                                           localDatiIscrizioneAlbo.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiIscrizioneAlbo"),
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
                                                                      "codiceFiscale"));
                                 
                                        if (localCodiceFiscale != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodiceFiscale));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codiceFiscale cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "nome"));
                                 
                                        if (localNome != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNome));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nome cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "cognome"));
                                 
                                        if (localCognome != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCognome));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cognome cannot be null!!");
                                        }
                                     if (localTitoloTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "titolo"));
                                 
                                        if (localTitolo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitolo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("titolo cannot be null!!");
                                        }
                                    } if (localSessoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "sesso"));
                                 
                                        if (localSesso != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSesso));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sesso cannot be null!!");
                                        }
                                    } if (localDataNascitaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataNascita"));
                                 
                                        if (localDataNascita != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataNascita));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataNascita cannot be null!!");
                                        }
                                    } if (localComuneNascitaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "comuneNascita"));
                            
                            
                                    if (localComuneNascita==null){
                                         throw new org.apache.axis2.databinding.ADBException("comuneNascita cannot be null!!");
                                    }
                                    elementList.add(localComuneNascita);
                                } if (localResidenzaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "residenza"));
                            
                            
                                    if (localResidenza==null){
                                         throw new org.apache.axis2.databinding.ADBException("residenza cannot be null!!");
                                    }
                                    elementList.add(localResidenza);
                                } if (localCorrispondenzaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "corrispondenza"));
                            
                            
                                    if (localCorrispondenza==null){
                                         throw new org.apache.axis2.databinding.ADBException("corrispondenza cannot be null!!");
                                    }
                                    elementList.add(localCorrispondenza);
                                } if (localCittadinanzaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "cittadinanza"));
                            
                            
                                    if (localCittadinanza==null){
                                         throw new org.apache.axis2.databinding.ADBException("cittadinanza cannot be null!!");
                                    }
                                    elementList.add(localCittadinanza);
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

                        } if (localProcuraTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "procura"));
                            
                            
                                    if (localProcura==null){
                                         throw new org.apache.axis2.databinding.ADBException("procura cannot be null!!");
                                    }
                                    elementList.add(localProcura);
                                } if (localTelefonoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "telefono"));
                                 
                                        if (localTelefono != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelefono));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telefono cannot be null!!");
                                        }
                                    } if (localEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "email"));
                                 
                                        if (localEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");
                                        }
                                    } if (localPecTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "pec"));
                                 
                                        if (localPec != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPec));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("pec cannot be null!!");
                                        }
                                    } if (localTelefonoCellulareTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "telefonoCellulare"));
                                 
                                        if (localTelefonoCellulare != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelefonoCellulare));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telefonoCellulare cannot be null!!");
                                        }
                                    } if (localDatiIscrizioneAlboTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "datiIscrizioneAlbo"));
                            
                            
                                    if (localDatiIscrizioneAlbo==null){
                                         throw new org.apache.axis2.databinding.ADBException("datiIscrizioneAlbo cannot be null!!");
                                    }
                                    elementList.add(localDatiIscrizioneAlbo);
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
        public static PersonaFisicaType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            PersonaFisicaType object =
                new PersonaFisicaType();

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
                    
                            if (!"PersonaFisicaType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (PersonaFisicaType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list11 = new java.util.ArrayList();
                    
                                    
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
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","nome").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNome(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","cognome").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCognome(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","titolo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTitolo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","sesso").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSesso(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataNascita").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataNascita(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","comuneNascita").equals(reader.getName())){
                                
                                                object.setComuneNascita(it.init.sigepro.rte.types.ComuneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","residenza").equals(reader.getName())){
                                
                                                object.setResidenza(it.init.sigepro.rte.types.LocalizzazioneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","corrispondenza").equals(reader.getName())){
                                
                                                object.setCorrispondenza(it.init.sigepro.rte.types.LocalizzazioneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","cittadinanza").equals(reader.getName())){
                                
                                                object.setCittadinanza(it.init.sigepro.rte.types.CittadinanzaType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriDati").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list11.add(it.init.sigepro.rte.types.ParametroType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone11 = false;
                                                        while(!loopDone11){
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
                                                                loopDone11 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriDati").equals(reader.getName())){
                                                                    list11.add(it.init.sigepro.rte.types.ParametroType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone11 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setAltriDati((it.init.sigepro.rte.types.ParametroType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.ParametroType.class,
                                                                list11));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procura").equals(reader.getName())){
                                
                                                object.setProcura(it.init.sigepro.rte.types.DocumentiType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","telefono").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelefono(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","email").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","pec").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPec(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","telefonoCellulare").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelefonoCellulare(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiIscrizioneAlbo").equals(reader.getName())){
                                
                                                object.setDatiIscrizioneAlbo(it.init.sigepro.rte.types.DatiIscrizioneAlboType.Factory.parse(reader));
                                              
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
           
    