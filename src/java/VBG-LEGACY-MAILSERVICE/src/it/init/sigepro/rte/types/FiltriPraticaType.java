
/**
 * FiltriPraticaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  FiltriPraticaType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class FiltriPraticaType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FiltriPraticaType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Comune
                        */

                        
                                    protected it.init.sigepro.rte.types.ComuneType localComune ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localComuneTracker = false ;

                           public boolean isComuneSpecified(){
                               return localComuneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ComuneType
                           */
                           public  it.init.sigepro.rte.types.ComuneType getComune(){
                               return localComune;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Comune
                               */
                               public void setComune(it.init.sigepro.rte.types.ComuneType param){
                            localComuneTracker = param != null;
                                   
                                            this.localComune=param;
                                    

                               }
                            

                        /**
                        * field for IdPratica
                        */

                        
                                    protected java.lang.String localIdPratica ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdPraticaTracker = false ;

                           public boolean isIdPraticaSpecified(){
                               return localIdPraticaTracker;
                           }

                           

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
                            localIdPraticaTracker = param != null;
                                   
                                            this.localIdPratica=param;
                                    

                               }
                            

                        /**
                        * field for NumeroPratica
                        */

                        
                                    protected java.lang.String localNumeroPratica ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumeroPraticaTracker = false ;

                           public boolean isNumeroPraticaSpecified(){
                               return localNumeroPraticaTracker;
                           }

                           

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
                            localNumeroPraticaTracker = param != null;
                                   
                                            this.localNumeroPratica=param;
                                    

                               }
                            

                        /**
                        * field for DataPresentazionePraticaDa
                        */

                        
                                    protected java.util.Date localDataPresentazionePraticaDa ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataPresentazionePraticaDaTracker = false ;

                           public boolean isDataPresentazionePraticaDaSpecified(){
                               return localDataPresentazionePraticaDaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataPresentazionePraticaDa(){
                               return localDataPresentazionePraticaDa;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataPresentazionePraticaDa
                               */
                               public void setDataPresentazionePraticaDa(java.util.Date param){
                            localDataPresentazionePraticaDaTracker = param != null;
                                   
                                            this.localDataPresentazionePraticaDa=param;
                                    

                               }
                            

                        /**
                        * field for DataPresentazionePraticaA
                        */

                        
                                    protected java.util.Date localDataPresentazionePraticaA ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataPresentazionePraticaATracker = false ;

                           public boolean isDataPresentazionePraticaASpecified(){
                               return localDataPresentazionePraticaATracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataPresentazionePraticaA(){
                               return localDataPresentazionePraticaA;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataPresentazionePraticaA
                               */
                               public void setDataPresentazionePraticaA(java.util.Date param){
                            localDataPresentazionePraticaATracker = param != null;
                                   
                                            this.localDataPresentazionePraticaA=param;
                                    

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
                        * field for DataProtocolloGeneraleDa
                        */

                        
                                    protected java.util.Date localDataProtocolloGeneraleDa ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataProtocolloGeneraleDaTracker = false ;

                           public boolean isDataProtocolloGeneraleDaSpecified(){
                               return localDataProtocolloGeneraleDaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataProtocolloGeneraleDa(){
                               return localDataProtocolloGeneraleDa;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataProtocolloGeneraleDa
                               */
                               public void setDataProtocolloGeneraleDa(java.util.Date param){
                            localDataProtocolloGeneraleDaTracker = param != null;
                                   
                                            this.localDataProtocolloGeneraleDa=param;
                                    

                               }
                            

                        /**
                        * field for DataProtocolloGeneraleA
                        */

                        
                                    protected java.util.Date localDataProtocolloGeneraleA ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataProtocolloGeneraleATracker = false ;

                           public boolean isDataProtocolloGeneraleASpecified(){
                               return localDataProtocolloGeneraleATracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataProtocolloGeneraleA(){
                               return localDataProtocolloGeneraleA;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataProtocolloGeneraleA
                               */
                               public void setDataProtocolloGeneraleA(java.util.Date param){
                            localDataProtocolloGeneraleATracker = param != null;
                                   
                                            this.localDataProtocolloGeneraleA=param;
                                    

                               }
                            

                        /**
                        * field for NumeroAtto
                        */

                        
                                    protected java.lang.String localNumeroAtto ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumeroAttoTracker = false ;

                           public boolean isNumeroAttoSpecified(){
                               return localNumeroAttoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNumeroAtto(){
                               return localNumeroAtto;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumeroAtto
                               */
                               public void setNumeroAtto(java.lang.String param){
                            localNumeroAttoTracker = param != null;
                                   
                                            this.localNumeroAtto=param;
                                    

                               }
                            

                        /**
                        * field for CodiceIntervento
                        */

                        
                                    protected java.lang.String localCodiceIntervento ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCodiceInterventoTracker = false ;

                           public boolean isCodiceInterventoSpecified(){
                               return localCodiceInterventoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCodiceIntervento(){
                               return localCodiceIntervento;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodiceIntervento
                               */
                               public void setCodiceIntervento(java.lang.String param){
                            localCodiceInterventoTracker = param != null;
                                   
                                            this.localCodiceIntervento=param;
                                    

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
                        * field for LocalizzazioneCodiceViario
                        */

                        
                                    protected java.lang.String localLocalizzazioneCodiceViario ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLocalizzazioneCodiceViarioTracker = false ;

                           public boolean isLocalizzazioneCodiceViarioSpecified(){
                               return localLocalizzazioneCodiceViarioTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocalizzazioneCodiceViario(){
                               return localLocalizzazioneCodiceViario;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocalizzazioneCodiceViario
                               */
                               public void setLocalizzazioneCodiceViario(java.lang.String param){
                            localLocalizzazioneCodiceViarioTracker = param != null;
                                   
                                            this.localLocalizzazioneCodiceViario=param;
                                    

                               }
                            

                        /**
                        * field for LocalizzazioneIndirizzo
                        */

                        
                                    protected java.lang.String localLocalizzazioneIndirizzo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLocalizzazioneIndirizzoTracker = false ;

                           public boolean isLocalizzazioneIndirizzoSpecified(){
                               return localLocalizzazioneIndirizzoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocalizzazioneIndirizzo(){
                               return localLocalizzazioneIndirizzo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocalizzazioneIndirizzo
                               */
                               public void setLocalizzazioneIndirizzo(java.lang.String param){
                            localLocalizzazioneIndirizzoTracker = param != null;
                                   
                                            this.localLocalizzazioneIndirizzo=param;
                                    

                               }
                            

                        /**
                        * field for LocalizzazioneCivico
                        */

                        
                                    protected java.lang.String localLocalizzazioneCivico ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLocalizzazioneCivicoTracker = false ;

                           public boolean isLocalizzazioneCivicoSpecified(){
                               return localLocalizzazioneCivicoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocalizzazioneCivico(){
                               return localLocalizzazioneCivico;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocalizzazioneCivico
                               */
                               public void setLocalizzazioneCivico(java.lang.String param){
                            localLocalizzazioneCivicoTracker = param != null;
                                   
                                            this.localLocalizzazioneCivico=param;
                                    

                               }
                            

                        /**
                        * field for RifCatastaliTipoCatasto
                        */

                        
                                    protected it.init.sigepro.rte.types.RifCatastaliTipoCatasto_type1 localRifCatastaliTipoCatasto ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRifCatastaliTipoCatastoTracker = false ;

                           public boolean isRifCatastaliTipoCatastoSpecified(){
                               return localRifCatastaliTipoCatastoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.RifCatastaliTipoCatasto_type1
                           */
                           public  it.init.sigepro.rte.types.RifCatastaliTipoCatasto_type1 getRifCatastaliTipoCatasto(){
                               return localRifCatastaliTipoCatasto;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RifCatastaliTipoCatasto
                               */
                               public void setRifCatastaliTipoCatasto(it.init.sigepro.rte.types.RifCatastaliTipoCatasto_type1 param){
                            localRifCatastaliTipoCatastoTracker = param != null;
                                   
                                            this.localRifCatastaliTipoCatasto=param;
                                    

                               }
                            

                        /**
                        * field for RifCatastaliFoglio
                        */

                        
                                    protected java.lang.String localRifCatastaliFoglio ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRifCatastaliFoglioTracker = false ;

                           public boolean isRifCatastaliFoglioSpecified(){
                               return localRifCatastaliFoglioTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRifCatastaliFoglio(){
                               return localRifCatastaliFoglio;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RifCatastaliFoglio
                               */
                               public void setRifCatastaliFoglio(java.lang.String param){
                            localRifCatastaliFoglioTracker = param != null;
                                   
                                            this.localRifCatastaliFoglio=param;
                                    

                               }
                            

                        /**
                        * field for RifCatastaliParticella
                        */

                        
                                    protected java.lang.String localRifCatastaliParticella ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRifCatastaliParticellaTracker = false ;

                           public boolean isRifCatastaliParticellaSpecified(){
                               return localRifCatastaliParticellaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRifCatastaliParticella(){
                               return localRifCatastaliParticella;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RifCatastaliParticella
                               */
                               public void setRifCatastaliParticella(java.lang.String param){
                            localRifCatastaliParticellaTracker = param != null;
                                   
                                            this.localRifCatastaliParticella=param;
                                    

                               }
                            

                        /**
                        * field for RifCatastaliSub
                        */

                        
                                    protected java.lang.String localRifCatastaliSub ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRifCatastaliSubTracker = false ;

                           public boolean isRifCatastaliSubSpecified(){
                               return localRifCatastaliSubTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRifCatastaliSub(){
                               return localRifCatastaliSub;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RifCatastaliSub
                               */
                               public void setRifCatastaliSub(java.lang.String param){
                            localRifCatastaliSubTracker = param != null;
                                   
                                            this.localRifCatastaliSub=param;
                                    

                               }
                            

                        /**
                        * field for LimiteRecords
                        */

                        
                                    protected int localLimiteRecords ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLimiteRecordsTracker = false ;

                           public boolean isLimiteRecordsSpecified(){
                               return localLimiteRecordsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getLimiteRecords(){
                               return localLimiteRecords;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LimiteRecords
                               */
                               public void setLimiteRecords(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localLimiteRecordsTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localLimiteRecords=param;
                                    

                               }
                            

                        /**
                        * field for CodiceFiscaleRichiedente
                        */

                        
                                    protected java.lang.String localCodiceFiscaleRichiedente ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCodiceFiscaleRichiedenteTracker = false ;

                           public boolean isCodiceFiscaleRichiedenteSpecified(){
                               return localCodiceFiscaleRichiedenteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCodiceFiscaleRichiedente(){
                               return localCodiceFiscaleRichiedente;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodiceFiscaleRichiedente
                               */
                               public void setCodiceFiscaleRichiedente(java.lang.String param){
                            localCodiceFiscaleRichiedenteTracker = param != null;
                                   
                                            this.localCodiceFiscaleRichiedente=param;
                                    

                               }
                            

                        /**
                        * field for StatoPratica
                        */

                        
                                    protected it.init.sigepro.rte.types.StatoPraticaType localStatoPratica ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStatoPraticaTracker = false ;

                           public boolean isStatoPraticaSpecified(){
                               return localStatoPraticaTracker;
                           }

                           

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
                            localStatoPraticaTracker = param != null;
                                   
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
                           namespacePrefix+":FiltriPraticaType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FiltriPraticaType",
                           xmlWriter);
                   }

               
                   }
                if (localComuneTracker){
                                            if (localComune==null){
                                                 throw new org.apache.axis2.databinding.ADBException("comune cannot be null!!");
                                            }
                                           localComune.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","comune"),
                                               xmlWriter);
                                        } if (localIdPraticaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "idPratica", xmlWriter);
                             

                                          if (localIdPratica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("idPratica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdPratica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumeroPraticaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "numeroPratica", xmlWriter);
                             

                                          if (localNumeroPratica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("numeroPratica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNumeroPratica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDataPresentazionePraticaDaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataPresentazionePraticaDa", xmlWriter);
                             

                                          if (localDataPresentazionePraticaDa==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataPresentazionePraticaDa cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataPresentazionePraticaDa));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDataPresentazionePraticaATracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataPresentazionePraticaA", xmlWriter);
                             

                                          if (localDataPresentazionePraticaA==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataPresentazionePraticaA cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataPresentazionePraticaA));
                                            
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
                             } if (localDataProtocolloGeneraleDaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataProtocolloGeneraleDa", xmlWriter);
                             

                                          if (localDataProtocolloGeneraleDa==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataProtocolloGeneraleDa cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataProtocolloGeneraleDa));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDataProtocolloGeneraleATracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataProtocolloGeneraleA", xmlWriter);
                             

                                          if (localDataProtocolloGeneraleA==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataProtocolloGeneraleA cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataProtocolloGeneraleA));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumeroAttoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "numeroAtto", xmlWriter);
                             

                                          if (localNumeroAtto==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("numeroAtto cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNumeroAtto);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCodiceInterventoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "codiceIntervento", xmlWriter);
                             

                                          if (localCodiceIntervento==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codiceIntervento cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodiceIntervento);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
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
                             } if (localLocalizzazioneCodiceViarioTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "localizzazioneCodiceViario", xmlWriter);
                             

                                          if (localLocalizzazioneCodiceViario==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("localizzazioneCodiceViario cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocalizzazioneCodiceViario);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLocalizzazioneIndirizzoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "localizzazioneIndirizzo", xmlWriter);
                             

                                          if (localLocalizzazioneIndirizzo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("localizzazioneIndirizzo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocalizzazioneIndirizzo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLocalizzazioneCivicoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "localizzazioneCivico", xmlWriter);
                             

                                          if (localLocalizzazioneCivico==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("localizzazioneCivico cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocalizzazioneCivico);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRifCatastaliTipoCatastoTracker){
                                            if (localRifCatastaliTipoCatasto==null){
                                                 throw new org.apache.axis2.databinding.ADBException("rifCatastaliTipoCatasto cannot be null!!");
                                            }
                                           localRifCatastaliTipoCatasto.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","rifCatastaliTipoCatasto"),
                                               xmlWriter);
                                        } if (localRifCatastaliFoglioTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "rifCatastaliFoglio", xmlWriter);
                             

                                          if (localRifCatastaliFoglio==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("rifCatastaliFoglio cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRifCatastaliFoglio);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRifCatastaliParticellaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "rifCatastaliParticella", xmlWriter);
                             

                                          if (localRifCatastaliParticella==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("rifCatastaliParticella cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRifCatastaliParticella);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRifCatastaliSubTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "rifCatastaliSub", xmlWriter);
                             

                                          if (localRifCatastaliSub==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("rifCatastaliSub cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRifCatastaliSub);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLimiteRecordsTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "limiteRecords", xmlWriter);
                             
                                               if (localLimiteRecords==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("limiteRecords cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLimiteRecords));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCodiceFiscaleRichiedenteTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "codiceFiscaleRichiedente", xmlWriter);
                             

                                          if (localCodiceFiscaleRichiedente==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codiceFiscaleRichiedente cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodiceFiscaleRichiedente);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStatoPraticaTracker){
                                            if (localStatoPratica==null){
                                                 throw new org.apache.axis2.databinding.ADBException("statoPratica cannot be null!!");
                                            }
                                           localStatoPratica.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","statoPratica"),
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

                 if (localComuneTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "comune"));
                            
                            
                                    if (localComune==null){
                                         throw new org.apache.axis2.databinding.ADBException("comune cannot be null!!");
                                    }
                                    elementList.add(localComune);
                                } if (localIdPraticaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "idPratica"));
                                 
                                        if (localIdPratica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdPratica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("idPratica cannot be null!!");
                                        }
                                    } if (localNumeroPraticaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "numeroPratica"));
                                 
                                        if (localNumeroPratica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumeroPratica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("numeroPratica cannot be null!!");
                                        }
                                    } if (localDataPresentazionePraticaDaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataPresentazionePraticaDa"));
                                 
                                        if (localDataPresentazionePraticaDa != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataPresentazionePraticaDa));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataPresentazionePraticaDa cannot be null!!");
                                        }
                                    } if (localDataPresentazionePraticaATracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataPresentazionePraticaA"));
                                 
                                        if (localDataPresentazionePraticaA != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataPresentazionePraticaA));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataPresentazionePraticaA cannot be null!!");
                                        }
                                    } if (localNumeroProtocolloGeneraleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "numeroProtocolloGenerale"));
                                 
                                        if (localNumeroProtocolloGenerale != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumeroProtocolloGenerale));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("numeroProtocolloGenerale cannot be null!!");
                                        }
                                    } if (localDataProtocolloGeneraleDaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataProtocolloGeneraleDa"));
                                 
                                        if (localDataProtocolloGeneraleDa != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataProtocolloGeneraleDa));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataProtocolloGeneraleDa cannot be null!!");
                                        }
                                    } if (localDataProtocolloGeneraleATracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataProtocolloGeneraleA"));
                                 
                                        if (localDataProtocolloGeneraleA != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataProtocolloGeneraleA));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataProtocolloGeneraleA cannot be null!!");
                                        }
                                    } if (localNumeroAttoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "numeroAtto"));
                                 
                                        if (localNumeroAtto != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumeroAtto));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("numeroAtto cannot be null!!");
                                        }
                                    } if (localCodiceInterventoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceIntervento"));
                                 
                                        if (localCodiceIntervento != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodiceIntervento));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codiceIntervento cannot be null!!");
                                        }
                                    } if (localOggettoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "oggetto"));
                                 
                                        if (localOggetto != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOggetto));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("oggetto cannot be null!!");
                                        }
                                    } if (localLocalizzazioneCodiceViarioTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "localizzazioneCodiceViario"));
                                 
                                        if (localLocalizzazioneCodiceViario != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocalizzazioneCodiceViario));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("localizzazioneCodiceViario cannot be null!!");
                                        }
                                    } if (localLocalizzazioneIndirizzoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "localizzazioneIndirizzo"));
                                 
                                        if (localLocalizzazioneIndirizzo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocalizzazioneIndirizzo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("localizzazioneIndirizzo cannot be null!!");
                                        }
                                    } if (localLocalizzazioneCivicoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "localizzazioneCivico"));
                                 
                                        if (localLocalizzazioneCivico != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocalizzazioneCivico));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("localizzazioneCivico cannot be null!!");
                                        }
                                    } if (localRifCatastaliTipoCatastoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "rifCatastaliTipoCatasto"));
                            
                            
                                    if (localRifCatastaliTipoCatasto==null){
                                         throw new org.apache.axis2.databinding.ADBException("rifCatastaliTipoCatasto cannot be null!!");
                                    }
                                    elementList.add(localRifCatastaliTipoCatasto);
                                } if (localRifCatastaliFoglioTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "rifCatastaliFoglio"));
                                 
                                        if (localRifCatastaliFoglio != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRifCatastaliFoglio));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("rifCatastaliFoglio cannot be null!!");
                                        }
                                    } if (localRifCatastaliParticellaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "rifCatastaliParticella"));
                                 
                                        if (localRifCatastaliParticella != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRifCatastaliParticella));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("rifCatastaliParticella cannot be null!!");
                                        }
                                    } if (localRifCatastaliSubTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "rifCatastaliSub"));
                                 
                                        if (localRifCatastaliSub != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRifCatastaliSub));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("rifCatastaliSub cannot be null!!");
                                        }
                                    } if (localLimiteRecordsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "limiteRecords"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLimiteRecords));
                            } if (localCodiceFiscaleRichiedenteTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceFiscaleRichiedente"));
                                 
                                        if (localCodiceFiscaleRichiedente != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodiceFiscaleRichiedente));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codiceFiscaleRichiedente cannot be null!!");
                                        }
                                    } if (localStatoPraticaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "statoPratica"));
                            
                            
                                    if (localStatoPratica==null){
                                         throw new org.apache.axis2.databinding.ADBException("statoPratica cannot be null!!");
                                    }
                                    elementList.add(localStatoPratica);
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
        public static FiltriPraticaType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FiltriPraticaType object =
                new FiltriPraticaType();

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
                    
                            if (!"FiltriPraticaType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FiltriPraticaType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","comune").equals(reader.getName())){
                                
                                                object.setComune(it.init.sigepro.rte.types.ComuneType.Factory.parse(reader));
                                              
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
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","numeroPratica").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumeroPratica(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataPresentazionePraticaDa").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataPresentazionePraticaDa(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataPresentazionePraticaA").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataPresentazionePraticaA(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataProtocolloGeneraleDa").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataProtocolloGeneraleDa(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataProtocolloGeneraleA").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataProtocolloGeneraleA(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","numeroAtto").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumeroAtto(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceIntervento").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodiceIntervento(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","localizzazioneCodiceViario").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocalizzazioneCodiceViario(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","localizzazioneIndirizzo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocalizzazioneIndirizzo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","localizzazioneCivico").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocalizzazioneCivico(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","rifCatastaliTipoCatasto").equals(reader.getName())){
                                
                                                object.setRifCatastaliTipoCatasto(it.init.sigepro.rte.types.RifCatastaliTipoCatasto_type1.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","rifCatastaliFoglio").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRifCatastaliFoglio(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","rifCatastaliParticella").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRifCatastaliParticella(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","rifCatastaliSub").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRifCatastaliSub(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","limiteRecords").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLimiteRecords(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setLimiteRecords(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceFiscaleRichiedente").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodiceFiscaleRichiedente(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","statoPratica").equals(reader.getName())){
                                
                                                object.setStatoPratica(it.init.sigepro.rte.types.StatoPraticaType.Factory.parse(reader));
                                              
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
           
    