
/**
 * PersonaGiuridicaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  PersonaGiuridicaType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class PersonaGiuridicaType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = PersonaGiuridicaType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for PartitaIva
                        */

                        
                                    protected java.lang.String localPartitaIva ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPartitaIvaTracker = false ;

                           public boolean isPartitaIvaSpecified(){
                               return localPartitaIvaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPartitaIva(){
                               return localPartitaIva;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PartitaIva
                               */
                               public void setPartitaIva(java.lang.String param){
                            localPartitaIvaTracker = param != null;
                                   
                                            this.localPartitaIva=param;
                                    

                               }
                            

                        /**
                        * field for CodiceFiscale
                        */

                        
                                    protected java.lang.String localCodiceFiscale ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCodiceFiscaleTracker = false ;

                           public boolean isCodiceFiscaleSpecified(){
                               return localCodiceFiscaleTracker;
                           }

                           

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
                            localCodiceFiscaleTracker = param != null;
                                   
                                            this.localCodiceFiscale=param;
                                    

                               }
                            

                        /**
                        * field for RagioneSociale
                        */

                        
                                    protected java.lang.String localRagioneSociale ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRagioneSociale(){
                               return localRagioneSociale;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RagioneSociale
                               */
                               public void setRagioneSociale(java.lang.String param){
                            
                                            this.localRagioneSociale=param;
                                    

                               }
                            

                        /**
                        * field for NaturaGiuridica
                        */

                        
                                    protected java.lang.String localNaturaGiuridica ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNaturaGiuridicaTracker = false ;

                           public boolean isNaturaGiuridicaSpecified(){
                               return localNaturaGiuridicaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNaturaGiuridica(){
                               return localNaturaGiuridica;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NaturaGiuridica
                               */
                               public void setNaturaGiuridica(java.lang.String param){
                            localNaturaGiuridicaTracker = param != null;
                                   
                                            this.localNaturaGiuridica=param;
                                    

                               }
                            

                        /**
                        * field for SedeLegale
                        */

                        
                                    protected it.init.sigepro.rte.types.LocalizzazioneType localSedeLegale ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSedeLegaleTracker = false ;

                           public boolean isSedeLegaleSpecified(){
                               return localSedeLegaleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.LocalizzazioneType
                           */
                           public  it.init.sigepro.rte.types.LocalizzazioneType getSedeLegale(){
                               return localSedeLegale;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SedeLegale
                               */
                               public void setSedeLegale(it.init.sigepro.rte.types.LocalizzazioneType param){
                            localSedeLegaleTracker = param != null;
                                   
                                            this.localSedeLegale=param;
                                    

                               }
                            

                        /**
                        * field for IndirizzoCorrispondenza
                        */

                        
                                    protected it.init.sigepro.rte.types.LocalizzazioneType localIndirizzoCorrispondenza ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIndirizzoCorrispondenzaTracker = false ;

                           public boolean isIndirizzoCorrispondenzaSpecified(){
                               return localIndirizzoCorrispondenzaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.LocalizzazioneType
                           */
                           public  it.init.sigepro.rte.types.LocalizzazioneType getIndirizzoCorrispondenza(){
                               return localIndirizzoCorrispondenza;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IndirizzoCorrispondenza
                               */
                               public void setIndirizzoCorrispondenza(it.init.sigepro.rte.types.LocalizzazioneType param){
                            localIndirizzoCorrispondenzaTracker = param != null;
                                   
                                            this.localIndirizzoCorrispondenza=param;
                                    

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
                        * field for Fax
                        */

                        
                                    protected java.lang.String localFax ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFaxTracker = false ;

                           public boolean isFaxSpecified(){
                               return localFaxTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFax(){
                               return localFax;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fax
                               */
                               public void setFax(java.lang.String param){
                            localFaxTracker = param != null;
                                   
                                            this.localFax=param;
                                    

                               }
                            

                        /**
                        * field for IscrizioneCCIAA
                        */

                        
                                    protected it.init.sigepro.rte.types.IscrizioneRegistroType localIscrizioneCCIAA ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIscrizioneCCIAATracker = false ;

                           public boolean isIscrizioneCCIAASpecified(){
                               return localIscrizioneCCIAATracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.IscrizioneRegistroType
                           */
                           public  it.init.sigepro.rte.types.IscrizioneRegistroType getIscrizioneCCIAA(){
                               return localIscrizioneCCIAA;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IscrizioneCCIAA
                               */
                               public void setIscrizioneCCIAA(it.init.sigepro.rte.types.IscrizioneRegistroType param){
                            localIscrizioneCCIAATracker = param != null;
                                   
                                            this.localIscrizioneCCIAA=param;
                                    

                               }
                            

                        /**
                        * field for IscrizioneREA
                        */

                        
                                    protected it.init.sigepro.rte.types.RegistroREAType localIscrizioneREA ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIscrizioneREATracker = false ;

                           public boolean isIscrizioneREASpecified(){
                               return localIscrizioneREATracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.RegistroREAType
                           */
                           public  it.init.sigepro.rte.types.RegistroREAType getIscrizioneREA(){
                               return localIscrizioneREA;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IscrizioneREA
                               */
                               public void setIscrizioneREA(it.init.sigepro.rte.types.RegistroREAType param){
                            localIscrizioneREATracker = param != null;
                                   
                                            this.localIscrizioneREA=param;
                                    

                               }
                            

                        /**
                        * field for LegaleRappresentante
                        */

                        
                                    protected it.init.sigepro.rte.types.PersonaFisicaType localLegaleRappresentante ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLegaleRappresentanteTracker = false ;

                           public boolean isLegaleRappresentanteSpecified(){
                               return localLegaleRappresentanteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.PersonaFisicaType
                           */
                           public  it.init.sigepro.rte.types.PersonaFisicaType getLegaleRappresentante(){
                               return localLegaleRappresentante;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LegaleRappresentante
                               */
                               public void setLegaleRappresentante(it.init.sigepro.rte.types.PersonaFisicaType param){
                            localLegaleRappresentanteTracker = param != null;
                                   
                                            this.localLegaleRappresentante=param;
                                    

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
                        * field for DatiInps
                        */

                        
                                    protected it.init.sigepro.rte.types.DatiInpsType localDatiInps ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDatiInpsTracker = false ;

                           public boolean isDatiInpsSpecified(){
                               return localDatiInpsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DatiInpsType
                           */
                           public  it.init.sigepro.rte.types.DatiInpsType getDatiInps(){
                               return localDatiInps;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DatiInps
                               */
                               public void setDatiInps(it.init.sigepro.rte.types.DatiInpsType param){
                            localDatiInpsTracker = param != null;
                                   
                                            this.localDatiInps=param;
                                    

                               }
                            

                        /**
                        * field for DatiInail
                        */

                        
                                    protected it.init.sigepro.rte.types.DatiInailType localDatiInail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDatiInailTracker = false ;

                           public boolean isDatiInailSpecified(){
                               return localDatiInailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DatiInailType
                           */
                           public  it.init.sigepro.rte.types.DatiInailType getDatiInail(){
                               return localDatiInail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DatiInail
                               */
                               public void setDatiInail(it.init.sigepro.rte.types.DatiInailType param){
                            localDatiInailTracker = param != null;
                                   
                                            this.localDatiInail=param;
                                    

                               }
                            

                        /**
                        * field for DatiCassaEdile
                        */

                        
                                    protected it.init.sigepro.rte.types.DatiCassaEdileType localDatiCassaEdile ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDatiCassaEdileTracker = false ;

                           public boolean isDatiCassaEdileSpecified(){
                               return localDatiCassaEdileTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.DatiCassaEdileType
                           */
                           public  it.init.sigepro.rte.types.DatiCassaEdileType getDatiCassaEdile(){
                               return localDatiCassaEdile;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DatiCassaEdile
                               */
                               public void setDatiCassaEdile(it.init.sigepro.rte.types.DatiCassaEdileType param){
                            localDatiCassaEdileTracker = param != null;
                                   
                                            this.localDatiCassaEdile=param;
                                    

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
                        * field for DataInizioAttivita
                        */

                        
                                    protected java.util.Date localDataInizioAttivita ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataInizioAttivitaTracker = false ;

                           public boolean isDataInizioAttivitaSpecified(){
                               return localDataInizioAttivitaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getDataInizioAttivita(){
                               return localDataInizioAttivita;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataInizioAttivita
                               */
                               public void setDataInizioAttivita(java.util.Date param){
                            localDataInizioAttivitaTracker = param != null;
                                   
                                            this.localDataInizioAttivita=param;
                                    

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
                           namespacePrefix+":PersonaGiuridicaType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "PersonaGiuridicaType",
                           xmlWriter);
                   }

               
                   }
                if (localPartitaIvaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "partitaIva", xmlWriter);
                             

                                          if (localPartitaIva==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("partitaIva cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPartitaIva);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCodiceFiscaleTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "codiceFiscale", xmlWriter);
                             

                                          if (localCodiceFiscale==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codiceFiscale cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodiceFiscale);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "ragioneSociale", xmlWriter);
                             

                                          if (localRagioneSociale==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("ragioneSociale cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRagioneSociale);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localNaturaGiuridicaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "naturaGiuridica", xmlWriter);
                             

                                          if (localNaturaGiuridica==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("naturaGiuridica cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNaturaGiuridica);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSedeLegaleTracker){
                                            if (localSedeLegale==null){
                                                 throw new org.apache.axis2.databinding.ADBException("sedeLegale cannot be null!!");
                                            }
                                           localSedeLegale.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","sedeLegale"),
                                               xmlWriter);
                                        } if (localIndirizzoCorrispondenzaTracker){
                                            if (localIndirizzoCorrispondenza==null){
                                                 throw new org.apache.axis2.databinding.ADBException("indirizzoCorrispondenza cannot be null!!");
                                            }
                                           localIndirizzoCorrispondenza.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","indirizzoCorrispondenza"),
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
                             } if (localFaxTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "fax", xmlWriter);
                             

                                          if (localFax==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fax cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFax);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIscrizioneCCIAATracker){
                                            if (localIscrizioneCCIAA==null){
                                                 throw new org.apache.axis2.databinding.ADBException("iscrizioneCCIAA cannot be null!!");
                                            }
                                           localIscrizioneCCIAA.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","iscrizioneCCIAA"),
                                               xmlWriter);
                                        } if (localIscrizioneREATracker){
                                            if (localIscrizioneREA==null){
                                                 throw new org.apache.axis2.databinding.ADBException("iscrizioneREA cannot be null!!");
                                            }
                                           localIscrizioneREA.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","iscrizioneREA"),
                                               xmlWriter);
                                        } if (localLegaleRappresentanteTracker){
                                            if (localLegaleRappresentante==null){
                                                 throw new org.apache.axis2.databinding.ADBException("legaleRappresentante cannot be null!!");
                                            }
                                           localLegaleRappresentante.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","legaleRappresentante"),
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
                             } if (localDatiInpsTracker){
                                            if (localDatiInps==null){
                                                 throw new org.apache.axis2.databinding.ADBException("datiInps cannot be null!!");
                                            }
                                           localDatiInps.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiInps"),
                                               xmlWriter);
                                        } if (localDatiInailTracker){
                                            if (localDatiInail==null){
                                                 throw new org.apache.axis2.databinding.ADBException("datiInail cannot be null!!");
                                            }
                                           localDatiInail.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiInail"),
                                               xmlWriter);
                                        } if (localDatiCassaEdileTracker){
                                            if (localDatiCassaEdile==null){
                                                 throw new org.apache.axis2.databinding.ADBException("datiCassaEdile cannot be null!!");
                                            }
                                           localDatiCassaEdile.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiCassaEdile"),
                                               xmlWriter);
                                        } if (localDatiIscrizioneAlboTracker){
                                            if (localDatiIscrizioneAlbo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("datiIscrizioneAlbo cannot be null!!");
                                            }
                                           localDatiIscrizioneAlbo.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiIscrizioneAlbo"),
                                               xmlWriter);
                                        } if (localDataInizioAttivitaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "dataInizioAttivita", xmlWriter);
                             

                                          if (localDataInizioAttivita==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dataInizioAttivita cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataInizioAttivita));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
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

                 if (localPartitaIvaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "partitaIva"));
                                 
                                        if (localPartitaIva != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPartitaIva));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("partitaIva cannot be null!!");
                                        }
                                    } if (localCodiceFiscaleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceFiscale"));
                                 
                                        if (localCodiceFiscale != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodiceFiscale));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codiceFiscale cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "ragioneSociale"));
                                 
                                        if (localRagioneSociale != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRagioneSociale));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("ragioneSociale cannot be null!!");
                                        }
                                     if (localNaturaGiuridicaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "naturaGiuridica"));
                                 
                                        if (localNaturaGiuridica != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNaturaGiuridica));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("naturaGiuridica cannot be null!!");
                                        }
                                    } if (localSedeLegaleTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "sedeLegale"));
                            
                            
                                    if (localSedeLegale==null){
                                         throw new org.apache.axis2.databinding.ADBException("sedeLegale cannot be null!!");
                                    }
                                    elementList.add(localSedeLegale);
                                } if (localIndirizzoCorrispondenzaTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "indirizzoCorrispondenza"));
                            
                            
                                    if (localIndirizzoCorrispondenza==null){
                                         throw new org.apache.axis2.databinding.ADBException("indirizzoCorrispondenza cannot be null!!");
                                    }
                                    elementList.add(localIndirizzoCorrispondenza);
                                } if (localTelefonoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "telefono"));
                                 
                                        if (localTelefono != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelefono));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telefono cannot be null!!");
                                        }
                                    } if (localFaxTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "fax"));
                                 
                                        if (localFax != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFax));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fax cannot be null!!");
                                        }
                                    } if (localIscrizioneCCIAATracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "iscrizioneCCIAA"));
                            
                            
                                    if (localIscrizioneCCIAA==null){
                                         throw new org.apache.axis2.databinding.ADBException("iscrizioneCCIAA cannot be null!!");
                                    }
                                    elementList.add(localIscrizioneCCIAA);
                                } if (localIscrizioneREATracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "iscrizioneREA"));
                            
                            
                                    if (localIscrizioneREA==null){
                                         throw new org.apache.axis2.databinding.ADBException("iscrizioneREA cannot be null!!");
                                    }
                                    elementList.add(localIscrizioneREA);
                                } if (localLegaleRappresentanteTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "legaleRappresentante"));
                            
                            
                                    if (localLegaleRappresentante==null){
                                         throw new org.apache.axis2.databinding.ADBException("legaleRappresentante cannot be null!!");
                                    }
                                    elementList.add(localLegaleRappresentante);
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
                                    } if (localDatiInpsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "datiInps"));
                            
                            
                                    if (localDatiInps==null){
                                         throw new org.apache.axis2.databinding.ADBException("datiInps cannot be null!!");
                                    }
                                    elementList.add(localDatiInps);
                                } if (localDatiInailTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "datiInail"));
                            
                            
                                    if (localDatiInail==null){
                                         throw new org.apache.axis2.databinding.ADBException("datiInail cannot be null!!");
                                    }
                                    elementList.add(localDatiInail);
                                } if (localDatiCassaEdileTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "datiCassaEdile"));
                            
                            
                                    if (localDatiCassaEdile==null){
                                         throw new org.apache.axis2.databinding.ADBException("datiCassaEdile cannot be null!!");
                                    }
                                    elementList.add(localDatiCassaEdile);
                                } if (localDatiIscrizioneAlboTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "datiIscrizioneAlbo"));
                            
                            
                                    if (localDatiIscrizioneAlbo==null){
                                         throw new org.apache.axis2.databinding.ADBException("datiIscrizioneAlbo cannot be null!!");
                                    }
                                    elementList.add(localDatiIscrizioneAlbo);
                                } if (localDataInizioAttivitaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "dataInizioAttivita"));
                                 
                                        if (localDataInizioAttivita != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDataInizioAttivita));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dataInizioAttivita cannot be null!!");
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
        public static PersonaGiuridicaType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            PersonaGiuridicaType object =
                new PersonaGiuridicaType();

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
                    
                            if (!"PersonaGiuridicaType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (PersonaGiuridicaType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list12 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","partitaIva").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPartitaIva(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceFiscale").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodiceFiscale(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","ragioneSociale").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRagioneSociale(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","naturaGiuridica").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNaturaGiuridica(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","sedeLegale").equals(reader.getName())){
                                
                                                object.setSedeLegale(it.init.sigepro.rte.types.LocalizzazioneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","indirizzoCorrispondenza").equals(reader.getName())){
                                
                                                object.setIndirizzoCorrispondenza(it.init.sigepro.rte.types.LocalizzazioneType.Factory.parse(reader));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","fax").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFax(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","iscrizioneCCIAA").equals(reader.getName())){
                                
                                                object.setIscrizioneCCIAA(it.init.sigepro.rte.types.IscrizioneRegistroType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","iscrizioneREA").equals(reader.getName())){
                                
                                                object.setIscrizioneREA(it.init.sigepro.rte.types.RegistroREAType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","legaleRappresentante").equals(reader.getName())){
                                
                                                object.setLegaleRappresentante(it.init.sigepro.rte.types.PersonaFisicaType.Factory.parse(reader));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiInps").equals(reader.getName())){
                                
                                                object.setDatiInps(it.init.sigepro.rte.types.DatiInpsType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiInail").equals(reader.getName())){
                                
                                                object.setDatiInail(it.init.sigepro.rte.types.DatiInailType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","datiCassaEdile").equals(reader.getName())){
                                
                                                object.setDatiCassaEdile(it.init.sigepro.rte.types.DatiCassaEdileType.Factory.parse(reader));
                                              
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
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","dataInizioAttivita").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDataInizioAttivita(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
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
           
    