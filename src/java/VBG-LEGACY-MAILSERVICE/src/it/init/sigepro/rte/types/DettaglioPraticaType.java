
/**
 * DettaglioPraticaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  DettaglioPraticaType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class DettaglioPraticaType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = DettaglioPraticaType
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
                        * field for CodicePraticaTelematica
                        */

                        
                                    protected java.lang.String localCodicePraticaTelematica ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCodicePraticaTelematicaTracker = false ;

                           public boolean isCodicePraticaTelematicaSpecified(){
                               return localCodicePraticaTelematicaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCodicePraticaTelematica(){
                               return localCodicePraticaTelematica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodicePraticaTelematica
                               */
                               public void setCodicePraticaTelematica(java.lang.String param){
                            localCodicePraticaTelematicaTracker = param != null;
                                   
                                            this.localCodicePraticaTelematica=param;
                                    

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

                        
                                    protected it.init.sigepro.rte.types.OraDataPratica_type5 localOraDataPratica ;
                                
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
                           * @return it.init.sigepro.rte.types.OraDataPratica_type5
                           */
                           public  it.init.sigepro.rte.types.OraDataPratica_type5 getOraDataPratica(){
                               return localOraDataPratica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OraDataPratica
                               */
                               public void setOraDataPratica(it.init.sigepro.rte.types.OraDataPratica_type5 param){
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
                        * field for CodiceAccreditamentoSUAP
                        */

                        
                                    protected java.lang.String localCodiceAccreditamentoSUAP ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCodiceAccreditamentoSUAPTracker = false ;

                           public boolean isCodiceAccreditamentoSUAPSpecified(){
                               return localCodiceAccreditamentoSUAPTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCodiceAccreditamentoSUAP(){
                               return localCodiceAccreditamentoSUAP;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodiceAccreditamentoSUAP
                               */
                               public void setCodiceAccreditamentoSUAP(java.lang.String param){
                            localCodiceAccreditamentoSUAPTracker = param != null;
                                   
                                            this.localCodiceAccreditamentoSUAP=param;
                                    

                               }
                            

                        /**
                        * field for DomicilioElettronico
                        */

                        
                                    protected java.lang.String localDomicilioElettronico ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomicilioElettronicoTracker = false ;

                           public boolean isDomicilioElettronicoSpecified(){
                               return localDomicilioElettronicoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDomicilioElettronico(){
                               return localDomicilioElettronico;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DomicilioElettronico
                               */
                               public void setDomicilioElettronico(java.lang.String param){
                            localDomicilioElettronicoTracker = param != null;
                                   
                                            this.localDomicilioElettronico=param;
                                    

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
                        * field for Procure
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.ProcuraType[] localProcure ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProcureTracker = false ;

                           public boolean isProcureSpecified(){
                               return localProcureTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.ProcuraType[]
                           */
                           public  it.init.sigepro.rte.types.ProcuraType[] getProcure(){
                               return localProcure;
                           }

                           
                        


                               
                              /**
                               * validate the array for Procure
                               */
                              protected void validateProcure(it.init.sigepro.rte.types.ProcuraType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Procure
                              */
                              public void setProcure(it.init.sigepro.rte.types.ProcuraType[] param){
                              
                                   validateProcure(param);

                               localProcureTracker = param != null;
                                      
                                      this.localProcure=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.ProcuraType
                             */
                             public void addProcure(it.init.sigepro.rte.types.ProcuraType param){
                                   if (localProcure == null){
                                   localProcure = new it.init.sigepro.rte.types.ProcuraType[]{};
                                   }

                            
                                 //update the setting tracker
                                localProcureTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localProcure);
                               list.add(param);
                               this.localProcure =
                             (it.init.sigepro.rte.types.ProcuraType[])list.toArray(
                            new it.init.sigepro.rte.types.ProcuraType[list.size()]);

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
                        * field for AltriSoggetti
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.AltriSoggettiType[] localAltriSoggetti ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAltriSoggettiTracker = false ;

                           public boolean isAltriSoggettiSpecified(){
                               return localAltriSoggettiTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.AltriSoggettiType[]
                           */
                           public  it.init.sigepro.rte.types.AltriSoggettiType[] getAltriSoggetti(){
                               return localAltriSoggetti;
                           }

                           
                        


                               
                              /**
                               * validate the array for AltriSoggetti
                               */
                              protected void validateAltriSoggetti(it.init.sigepro.rte.types.AltriSoggettiType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param AltriSoggetti
                              */
                              public void setAltriSoggetti(it.init.sigepro.rte.types.AltriSoggettiType[] param){
                              
                                   validateAltriSoggetti(param);

                               localAltriSoggettiTracker = param != null;
                                      
                                      this.localAltriSoggetti=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.AltriSoggettiType
                             */
                             public void addAltriSoggetti(it.init.sigepro.rte.types.AltriSoggettiType param){
                                   if (localAltriSoggetti == null){
                                   localAltriSoggetti = new it.init.sigepro.rte.types.AltriSoggettiType[]{};
                                   }

                            
                                 //update the setting tracker
                                localAltriSoggettiTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localAltriSoggetti);
                               list.add(param);
                               this.localAltriSoggetti =
                             (it.init.sigepro.rte.types.AltriSoggettiType[])list.toArray(
                            new it.init.sigepro.rte.types.AltriSoggettiType[list.size()]);

                             }
                             

                        /**
                        * field for Oggetto
                        */

                        
                                    protected java.lang.String localOggetto ;
                                

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
                            
                                            this.localOggetto=param;
                                    

                               }
                            

                        /**
                        * field for Insegna
                        */

                        
                                    protected java.lang.String localInsegna ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsegnaTracker = false ;

                           public boolean isInsegnaSpecified(){
                               return localInsegnaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsegna(){
                               return localInsegna;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Insegna
                               */
                               public void setInsegna(java.lang.String param){
                            localInsegnaTracker = param != null;
                                   
                                            this.localInsegna=param;
                                    

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
                        * field for Schede
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.SchedaType[] localSchede ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSchedeTracker = false ;

                           public boolean isSchedeSpecified(){
                               return localSchedeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.SchedaType[]
                           */
                           public  it.init.sigepro.rte.types.SchedaType[] getSchede(){
                               return localSchede;
                           }

                           
                        


                               
                              /**
                               * validate the array for Schede
                               */
                              protected void validateSchede(it.init.sigepro.rte.types.SchedaType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Schede
                              */
                              public void setSchede(it.init.sigepro.rte.types.SchedaType[] param){
                              
                                   validateSchede(param);

                               localSchedeTracker = param != null;
                                      
                                      this.localSchede=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.SchedaType
                             */
                             public void addSchede(it.init.sigepro.rte.types.SchedaType param){
                                   if (localSchede == null){
                                   localSchede = new it.init.sigepro.rte.types.SchedaType[]{};
                                   }

                            
                                 //update the setting tracker
                                localSchedeTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localSchede);
                               list.add(param);
                               this.localSchede =
                             (it.init.sigepro.rte.types.SchedaType[])list.toArray(
                            new it.init.sigepro.rte.types.SchedaType[list.size()]);

                             }
                             

                        /**
                        * field for Oneri
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.OneriType[] localOneri ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOneriTracker = false ;

                           public boolean isOneriSpecified(){
                               return localOneriTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.OneriType[]
                           */
                           public  it.init.sigepro.rte.types.OneriType[] getOneri(){
                               return localOneri;
                           }

                           
                        


                               
                              /**
                               * validate the array for Oneri
                               */
                              protected void validateOneri(it.init.sigepro.rte.types.OneriType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Oneri
                              */
                              public void setOneri(it.init.sigepro.rte.types.OneriType[] param){
                              
                                   validateOneri(param);

                               localOneriTracker = param != null;
                                      
                                      this.localOneri=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.OneriType
                             */
                             public void addOneri(it.init.sigepro.rte.types.OneriType param){
                                   if (localOneri == null){
                                   localOneri = new it.init.sigepro.rte.types.OneriType[]{};
                                   }

                            
                                 //update the setting tracker
                                localOneriTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localOneri);
                               list.add(param);
                               this.localOneri =
                             (it.init.sigepro.rte.types.OneriType[])list.toArray(
                            new it.init.sigepro.rte.types.OneriType[list.size()]);

                             }
                             

                        /**
                        * field for Annotazioni
                        */

                        
                                    protected java.lang.String localAnnotazioni ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAnnotazioniTracker = false ;

                           public boolean isAnnotazioniSpecified(){
                               return localAnnotazioniTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAnnotazioni(){
                               return localAnnotazioni;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Annotazioni
                               */
                               public void setAnnotazioni(java.lang.String param){
                            localAnnotazioniTracker = param != null;
                                   
                                            this.localAnnotazioni=param;
                                    

                               }
                            

                        /**
                        * field for NaturaFo
                        */

                        
                                    protected it.init.sigepro.rte.types.NaturaFoType localNaturaFo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNaturaFoTracker = false ;

                           public boolean isNaturaFoSpecified(){
                               return localNaturaFoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.NaturaFoType
                           */
                           public  it.init.sigepro.rte.types.NaturaFoType getNaturaFo(){
                               return localNaturaFo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NaturaFo
                               */
                               public void setNaturaFo(it.init.sigepro.rte.types.NaturaFoType param){
                            localNaturaFoTracker = param != null;
                                   
                                            this.localNaturaFo=param;
                                    

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
                           namespacePrefix+":DettaglioPraticaType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "DettaglioPraticaType",
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
                                    writeStartElement(null, namespace, "numeroPratica", xmlWriter);
                             

                                          if (localNumeroPratica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("numeroPratica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNumeroPratica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localCodicePraticaTelematicaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "codicePraticaTelematica", xmlWriter);
                             

                                          if (localCodicePraticaTelematica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codicePraticaTelematica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodicePraticaTelematica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
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
                             } if (localCodiceComuneTracker){
                                            if (localCodiceComune==null){
                                                 throw new org.apache.axis2.databinding.ADBException("codiceComune cannot be null!!");
                                            }
                                           localCodiceComune.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceComune"),
                                               xmlWriter);
                                        } if (localCodiceAccreditamentoSUAPTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "codiceAccreditamentoSUAP", xmlWriter);
                             

                                          if (localCodiceAccreditamentoSUAP==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codiceAccreditamentoSUAP cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodiceAccreditamentoSUAP);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDomicilioElettronicoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "domicilioElettronico", xmlWriter);
                             

                                          if (localDomicilioElettronico==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("domicilioElettronico cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDomicilioElettronico);
                                            
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
                                        } if (localProcureTracker){
                                       if (localProcure!=null){
                                            for (int i = 0;i < localProcure.length;i++){
                                                if (localProcure[i] != null){
                                                 localProcure[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procure"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("procure cannot be null!!");
                                        
                                    }
                                 } if (localIntermediarioTracker){
                                            if (localIntermediario==null){
                                                 throw new org.apache.axis2.databinding.ADBException("intermediario cannot be null!!");
                                            }
                                           localIntermediario.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","intermediario"),
                                               xmlWriter);
                                        } if (localAltriSoggettiTracker){
                                       if (localAltriSoggetti!=null){
                                            for (int i = 0;i < localAltriSoggetti.length;i++){
                                                if (localAltriSoggetti[i] != null){
                                                 localAltriSoggetti[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriSoggetti"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("altriSoggetti cannot be null!!");
                                        
                                    }
                                 }
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "oggetto", xmlWriter);
                             

                                          if (localOggetto==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("oggetto cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOggetto);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localInsegnaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "insegna", xmlWriter);
                             

                                          if (localInsegna==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("insegna cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsegna);
                                            
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
                                 } if (localSchedeTracker){
                                       if (localSchede!=null){
                                            for (int i = 0;i < localSchede.length;i++){
                                                if (localSchede[i] != null){
                                                 localSchede[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","schede"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("schede cannot be null!!");
                                        
                                    }
                                 } if (localOneriTracker){
                                       if (localOneri!=null){
                                            for (int i = 0;i < localOneri.length;i++){
                                                if (localOneri[i] != null){
                                                 localOneri[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oneri"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("oneri cannot be null!!");
                                        
                                    }
                                 } if (localAnnotazioniTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "annotazioni", xmlWriter);
                             

                                          if (localAnnotazioni==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("annotazioni cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAnnotazioni);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNaturaFoTracker){
                                            if (localNaturaFo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("naturaFo cannot be null!!");
                                            }
                                           localNaturaFo.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","naturaFo"),
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
                                     if (localCodicePraticaTelematicaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codicePraticaTelematica"));
                                 
                                        if (localCodicePraticaTelematica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodicePraticaTelematica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codicePraticaTelematica cannot be null!!");
                                        }
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
                                    } if (localCodiceComuneTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceComune"));
                            
                            
                                    if (localCodiceComune==null){
                                         throw new org.apache.axis2.databinding.ADBException("codiceComune cannot be null!!");
                                    }
                                    elementList.add(localCodiceComune);
                                } if (localCodiceAccreditamentoSUAPTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceAccreditamentoSUAP"));
                                 
                                        if (localCodiceAccreditamentoSUAP != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodiceAccreditamentoSUAP));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codiceAccreditamentoSUAP cannot be null!!");
                                        }
                                    } if (localDomicilioElettronicoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "domicilioElettronico"));
                                 
                                        if (localDomicilioElettronico != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDomicilioElettronico));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("domicilioElettronico cannot be null!!");
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
                                } if (localProcureTracker){
                             if (localProcure!=null) {
                                 for (int i = 0;i < localProcure.length;i++){

                                    if (localProcure[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "procure"));
                                         elementList.add(localProcure[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("procure cannot be null!!");
                                    
                             }

                        } if (localIntermediarioTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "intermediario"));
                            
                            
                                    if (localIntermediario==null){
                                         throw new org.apache.axis2.databinding.ADBException("intermediario cannot be null!!");
                                    }
                                    elementList.add(localIntermediario);
                                } if (localAltriSoggettiTracker){
                             if (localAltriSoggetti!=null) {
                                 for (int i = 0;i < localAltriSoggetti.length;i++){

                                    if (localAltriSoggetti[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "altriSoggetti"));
                                         elementList.add(localAltriSoggetti[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("altriSoggetti cannot be null!!");
                                    
                             }

                        }
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "oggetto"));
                                 
                                        if (localOggetto != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOggetto));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("oggetto cannot be null!!");
                                        }
                                     if (localInsegnaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "insegna"));
                                 
                                        if (localInsegna != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsegna));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("insegna cannot be null!!");
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

                        } if (localSchedeTracker){
                             if (localSchede!=null) {
                                 for (int i = 0;i < localSchede.length;i++){

                                    if (localSchede[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "schede"));
                                         elementList.add(localSchede[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("schede cannot be null!!");
                                    
                             }

                        } if (localOneriTracker){
                             if (localOneri!=null) {
                                 for (int i = 0;i < localOneri.length;i++){

                                    if (localOneri[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "oneri"));
                                         elementList.add(localOneri[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("oneri cannot be null!!");
                                    
                             }

                        } if (localAnnotazioniTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "annotazioni"));
                                 
                                        if (localAnnotazioni != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAnnotazioni));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("annotazioni cannot be null!!");
                                        }
                                    } if (localNaturaFoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "naturaFo"));
                            
                            
                                    if (localNaturaFo==null){
                                         throw new org.apache.axis2.databinding.ADBException("naturaFo cannot be null!!");
                                    }
                                    elementList.add(localNaturaFo);
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
        public static DettaglioPraticaType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            DettaglioPraticaType object =
                new DettaglioPraticaType();

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
                    
                            if (!"DettaglioPraticaType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DettaglioPraticaType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list13 = new java.util.ArrayList();
                    
                        java.util.ArrayList list15 = new java.util.ArrayList();
                    
                        java.util.ArrayList list19 = new java.util.ArrayList();
                    
                        java.util.ArrayList list20 = new java.util.ArrayList();
                    
                        java.util.ArrayList list21 = new java.util.ArrayList();
                    
                        java.util.ArrayList list22 = new java.util.ArrayList();
                    
                        java.util.ArrayList list23 = new java.util.ArrayList();
                    
                        java.util.ArrayList list24 = new java.util.ArrayList();
                    
                                    
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codicePraticaTelematica").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodicePraticaTelematica(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
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
                                
                                                object.setOraDataPratica(it.init.sigepro.rte.types.OraDataPratica_type5.Factory.parse(reader));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceComune").equals(reader.getName())){
                                
                                                object.setCodiceComune(it.init.sigepro.rte.types.ComuneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceAccreditamentoSUAP").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodiceAccreditamentoSUAP(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","domicilioElettronico").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDomicilioElettronico(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procure").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list13.add(it.init.sigepro.rte.types.ProcuraType.Factory.parse(reader));
                                                                
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
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procure").equals(reader.getName())){
                                                                    list13.add(it.init.sigepro.rte.types.ProcuraType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone13 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setProcure((it.init.sigepro.rte.types.ProcuraType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.ProcuraType.class,
                                                                list13));
                                                            
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriSoggetti").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list15.add(it.init.sigepro.rte.types.AltriSoggettiType.Factory.parse(reader));
                                                                
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
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriSoggetti").equals(reader.getName())){
                                                                    list15.add(it.init.sigepro.rte.types.AltriSoggettiType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone15 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setAltriSoggetti((it.init.sigepro.rte.types.AltriSoggettiType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.AltriSoggettiType.class,
                                                                list15));
                                                            
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
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","insegna").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsegna(
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
                                    list19.add(it.init.sigepro.rte.types.LocalizzazioneNelComuneType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone19 = false;
                                                        while(!loopDone19){
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
                                                                loopDone19 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","localizzazione").equals(reader.getName())){
                                                                    list19.add(it.init.sigepro.rte.types.LocalizzazioneNelComuneType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone19 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setLocalizzazione((it.init.sigepro.rte.types.LocalizzazioneNelComuneType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.LocalizzazioneNelComuneType.class,
                                                                list19));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documenti").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list20.add(it.init.sigepro.rte.types.DocumentiType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone20 = false;
                                                        while(!loopDone20){
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
                                                                loopDone20 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","documenti").equals(reader.getName())){
                                                                    list20.add(it.init.sigepro.rte.types.DocumentiType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone20 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setDocumenti((it.init.sigepro.rte.types.DocumentiType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.DocumentiType.class,
                                                                list20));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriDati").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list21.add(it.init.sigepro.rte.types.ParametroType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone21 = false;
                                                        while(!loopDone21){
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
                                                                loopDone21 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","altriDati").equals(reader.getName())){
                                                                    list21.add(it.init.sigepro.rte.types.ParametroType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone21 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setAltriDati((it.init.sigepro.rte.types.ParametroType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.ParametroType.class,
                                                                list21));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procedimenti").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list22.add(it.init.sigepro.rte.types.ProcedimentoType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone22 = false;
                                                        while(!loopDone22){
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
                                                                loopDone22 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","procedimenti").equals(reader.getName())){
                                                                    list22.add(it.init.sigepro.rte.types.ProcedimentoType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone22 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setProcedimenti((it.init.sigepro.rte.types.ProcedimentoType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.ProcedimentoType.class,
                                                                list22));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","schede").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list23.add(it.init.sigepro.rte.types.SchedaType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone23 = false;
                                                        while(!loopDone23){
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
                                                                loopDone23 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","schede").equals(reader.getName())){
                                                                    list23.add(it.init.sigepro.rte.types.SchedaType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone23 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setSchede((it.init.sigepro.rte.types.SchedaType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.SchedaType.class,
                                                                list23));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oneri").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list24.add(it.init.sigepro.rte.types.OneriType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone24 = false;
                                                        while(!loopDone24){
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
                                                                loopDone24 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","oneri").equals(reader.getName())){
                                                                    list24.add(it.init.sigepro.rte.types.OneriType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone24 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setOneri((it.init.sigepro.rte.types.OneriType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.OneriType.class,
                                                                list24));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","annotazioni").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAnnotazioni(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","naturaFo").equals(reader.getName())){
                                
                                                object.setNaturaFo(it.init.sigepro.rte.types.NaturaFoType.Factory.parse(reader));
                                              
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
           
    