
/**
 * LocalizzazioneNelComuneType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package it.init.sigepro.rte.types;
            

            /**
            *  LocalizzazioneNelComuneType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class LocalizzazioneNelComuneType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = LocalizzazioneNelComuneType
                Namespace URI = http://sigepro.init.it/rte/types
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Id
                        */

                        
                                    protected java.lang.String localId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(java.lang.String param){
                            
                                            this.localId=param;
                                    

                               }
                            

                        /**
                        * field for CodiceViario
                        */

                        
                                    protected java.lang.String localCodiceViario ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCodiceViario(){
                               return localCodiceViario;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodiceViario
                               */
                               public void setCodiceViario(java.lang.String param){
                            
                                            this.localCodiceViario=param;
                                    

                               }
                            

                        /**
                        * field for Denominazione
                        */

                        
                                    protected java.lang.String localDenominazione ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDenominazione(){
                               return localDenominazione;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Denominazione
                               */
                               public void setDenominazione(java.lang.String param){
                            
                                            this.localDenominazione=param;
                                    

                               }
                            

                        /**
                        * field for Civico
                        */

                        
                                    protected java.lang.String localCivico ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCivicoTracker = false ;

                           public boolean isCivicoSpecified(){
                               return localCivicoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCivico(){
                               return localCivico;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Civico
                               */
                               public void setCivico(java.lang.String param){
                            localCivicoTracker = param != null;
                                   
                                            this.localCivico=param;
                                    

                               }
                            

                        /**
                        * field for Esponente
                        */

                        
                                    protected java.lang.String localEsponente ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEsponenteTracker = false ;

                           public boolean isEsponenteSpecified(){
                               return localEsponenteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEsponente(){
                               return localEsponente;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Esponente
                               */
                               public void setEsponente(java.lang.String param){
                            localEsponenteTracker = param != null;
                                   
                                            this.localEsponente=param;
                                    

                               }
                            

                        /**
                        * field for Colore
                        */

                        
                                    protected java.lang.String localColore ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localColoreTracker = false ;

                           public boolean isColoreSpecified(){
                               return localColoreTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getColore(){
                               return localColore;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Colore
                               */
                               public void setColore(java.lang.String param){
                            localColoreTracker = param != null;
                                   
                                            this.localColore=param;
                                    

                               }
                            

                        /**
                        * field for Scala
                        */

                        
                                    protected java.lang.String localScala ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localScalaTracker = false ;

                           public boolean isScalaSpecified(){
                               return localScalaTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getScala(){
                               return localScala;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Scala
                               */
                               public void setScala(java.lang.String param){
                            localScalaTracker = param != null;
                                   
                                            this.localScala=param;
                                    

                               }
                            

                        /**
                        * field for Interno
                        */

                        
                                    protected java.lang.String localInterno ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInternoTracker = false ;

                           public boolean isInternoSpecified(){
                               return localInternoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInterno(){
                               return localInterno;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Interno
                               */
                               public void setInterno(java.lang.String param){
                            localInternoTracker = param != null;
                                   
                                            this.localInterno=param;
                                    

                               }
                            

                        /**
                        * field for EsponenteInterno
                        */

                        
                                    protected java.lang.String localEsponenteInterno ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEsponenteInternoTracker = false ;

                           public boolean isEsponenteInternoSpecified(){
                               return localEsponenteInternoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEsponenteInterno(){
                               return localEsponenteInterno;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EsponenteInterno
                               */
                               public void setEsponenteInterno(java.lang.String param){
                            localEsponenteInternoTracker = param != null;
                                   
                                            this.localEsponenteInterno=param;
                                    

                               }
                            

                        /**
                        * field for Piano
                        */

                        
                                    protected java.lang.String localPiano ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPianoTracker = false ;

                           public boolean isPianoSpecified(){
                               return localPianoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPiano(){
                               return localPiano;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Piano
                               */
                               public void setPiano(java.lang.String param){
                            localPianoTracker = param != null;
                                   
                                            this.localPiano=param;
                                    

                               }
                            

                        /**
                        * field for Fabbricato
                        */

                        
                                    protected java.lang.String localFabbricato ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFabbricatoTracker = false ;

                           public boolean isFabbricatoSpecified(){
                               return localFabbricatoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFabbricato(){
                               return localFabbricato;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fabbricato
                               */
                               public void setFabbricato(java.lang.String param){
                            localFabbricatoTracker = param != null;
                                   
                                            this.localFabbricato=param;
                                    

                               }
                            

                        /**
                        * field for Km
                        */

                        
                                    protected java.lang.String localKm ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localKmTracker = false ;

                           public boolean isKmSpecified(){
                               return localKmTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getKm(){
                               return localKm;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Km
                               */
                               public void setKm(java.lang.String param){
                            localKmTracker = param != null;
                                   
                                            this.localKm=param;
                                    

                               }
                            

                        /**
                        * field for Frazione
                        */

                        
                                    protected it.init.sigepro.rte.types.FrazioneType localFrazione ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFrazioneTracker = false ;

                           public boolean isFrazioneSpecified(){
                               return localFrazioneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.FrazioneType
                           */
                           public  it.init.sigepro.rte.types.FrazioneType getFrazione(){
                               return localFrazione;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Frazione
                               */
                               public void setFrazione(it.init.sigepro.rte.types.FrazioneType param){
                            localFrazioneTracker = param != null;
                                   
                                            this.localFrazione=param;
                                    

                               }
                            

                        /**
                        * field for Circoscrizione
                        */

                        
                                    protected it.init.sigepro.rte.types.CircoscrizioneType localCircoscrizione ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCircoscrizioneTracker = false ;

                           public boolean isCircoscrizioneSpecified(){
                               return localCircoscrizioneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.CircoscrizioneType
                           */
                           public  it.init.sigepro.rte.types.CircoscrizioneType getCircoscrizione(){
                               return localCircoscrizione;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Circoscrizione
                               */
                               public void setCircoscrizione(it.init.sigepro.rte.types.CircoscrizioneType param){
                            localCircoscrizioneTracker = param != null;
                                   
                                            this.localCircoscrizione=param;
                                    

                               }
                            

                        /**
                        * field for Quartiere
                        */

                        
                                    protected it.init.sigepro.rte.types.QuartiereType localQuartiere ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localQuartiereTracker = false ;

                           public boolean isQuartiereSpecified(){
                               return localQuartiereTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.QuartiereType
                           */
                           public  it.init.sigepro.rte.types.QuartiereType getQuartiere(){
                               return localQuartiere;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Quartiere
                               */
                               public void setQuartiere(it.init.sigepro.rte.types.QuartiereType param){
                            localQuartiereTracker = param != null;
                                   
                                            this.localQuartiere=param;
                                    

                               }
                            

                        /**
                        * field for RiferimentoCatastale
                        * This was an Array!
                        */

                        
                                    protected it.init.sigepro.rte.types.RiferimentoCatastaleType[] localRiferimentoCatastale ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRiferimentoCatastaleTracker = false ;

                           public boolean isRiferimentoCatastaleSpecified(){
                               return localRiferimentoCatastaleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.RiferimentoCatastaleType[]
                           */
                           public  it.init.sigepro.rte.types.RiferimentoCatastaleType[] getRiferimentoCatastale(){
                               return localRiferimentoCatastale;
                           }

                           
                        


                               
                              /**
                               * validate the array for RiferimentoCatastale
                               */
                              protected void validateRiferimentoCatastale(it.init.sigepro.rte.types.RiferimentoCatastaleType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param RiferimentoCatastale
                              */
                              public void setRiferimentoCatastale(it.init.sigepro.rte.types.RiferimentoCatastaleType[] param){
                              
                                   validateRiferimentoCatastale(param);

                               localRiferimentoCatastaleTracker = param != null;
                                      
                                      this.localRiferimentoCatastale=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param it.init.sigepro.rte.types.RiferimentoCatastaleType
                             */
                             public void addRiferimentoCatastale(it.init.sigepro.rte.types.RiferimentoCatastaleType param){
                                   if (localRiferimentoCatastale == null){
                                   localRiferimentoCatastale = new it.init.sigepro.rte.types.RiferimentoCatastaleType[]{};
                                   }

                            
                                 //update the setting tracker
                                localRiferimentoCatastaleTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localRiferimentoCatastale);
                               list.add(param);
                               this.localRiferimentoCatastale =
                             (it.init.sigepro.rte.types.RiferimentoCatastaleType[])list.toArray(
                            new it.init.sigepro.rte.types.RiferimentoCatastaleType[list.size()]);

                             }
                             

                        /**
                        * field for Coordinate
                        */

                        
                                    protected it.init.sigepro.rte.types.CoordinateType localCoordinate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCoordinateTracker = false ;

                           public boolean isCoordinateSpecified(){
                               return localCoordinateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.CoordinateType
                           */
                           public  it.init.sigepro.rte.types.CoordinateType getCoordinate(){
                               return localCoordinate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Coordinate
                               */
                               public void setCoordinate(it.init.sigepro.rte.types.CoordinateType param){
                            localCoordinateTracker = param != null;
                                   
                                            this.localCoordinate=param;
                                    

                               }
                            

                        /**
                        * field for Tipo
                        */

                        
                                    protected it.init.sigepro.rte.types.TipoLocalizzazioneType localTipo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTipoTracker = false ;

                           public boolean isTipoSpecified(){
                               return localTipoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return it.init.sigepro.rte.types.TipoLocalizzazioneType
                           */
                           public  it.init.sigepro.rte.types.TipoLocalizzazioneType getTipo(){
                               return localTipo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Tipo
                               */
                               public void setTipo(it.init.sigepro.rte.types.TipoLocalizzazioneType param){
                            localTipoTracker = param != null;
                                   
                                            this.localTipo=param;
                                    

                               }
                            

                        /**
                        * field for Uuid
                        */

                        
                                    protected java.lang.String localUuid ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUuidTracker = false ;

                           public boolean isUuidSpecified(){
                               return localUuidTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUuid(){
                               return localUuid;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Uuid
                               */
                               public void setUuid(java.lang.String param){
                            localUuidTracker = param != null;
                                   
                                            this.localUuid=param;
                                    

                               }
                            

                        /**
                        * field for Cap
                        */

                        
                                    protected java.lang.String localCap ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCapTracker = false ;

                           public boolean isCapSpecified(){
                               return localCapTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCap(){
                               return localCap;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Cap
                               */
                               public void setCap(java.lang.String param){
                            localCapTracker = param != null;
                                   
                                            this.localCap=param;
                                    

                               }
                            

                        /**
                        * field for AccessoTipo
                        */

                        
                                    protected java.lang.String localAccessoTipo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccessoTipoTracker = false ;

                           public boolean isAccessoTipoSpecified(){
                               return localAccessoTipoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccessoTipo(){
                               return localAccessoTipo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccessoTipo
                               */
                               public void setAccessoTipo(java.lang.String param){
                            localAccessoTipoTracker = param != null;
                                   
                                            this.localAccessoTipo=param;
                                    

                               }
                            

                        /**
                        * field for AccessoNumero
                        */

                        
                                    protected java.lang.String localAccessoNumero ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccessoNumeroTracker = false ;

                           public boolean isAccessoNumeroSpecified(){
                               return localAccessoNumeroTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccessoNumero(){
                               return localAccessoNumero;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccessoNumero
                               */
                               public void setAccessoNumero(java.lang.String param){
                            localAccessoNumeroTracker = param != null;
                                   
                                            this.localAccessoNumero=param;
                                    

                               }
                            

                        /**
                        * field for AccessoDescrizione
                        */

                        
                                    protected java.lang.String localAccessoDescrizione ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccessoDescrizioneTracker = false ;

                           public boolean isAccessoDescrizioneSpecified(){
                               return localAccessoDescrizioneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccessoDescrizione(){
                               return localAccessoDescrizione;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccessoDescrizione
                               */
                               public void setAccessoDescrizione(java.lang.String param){
                            localAccessoDescrizioneTracker = param != null;
                                   
                                            this.localAccessoDescrizione=param;
                                    

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
                           namespacePrefix+":LocalizzazioneNelComuneType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "LocalizzazioneNelComuneType",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "id", xmlWriter);
                             

                                          if (localId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "codiceViario", xmlWriter);
                             

                                          if (localCodiceViario==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codiceViario cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodiceViario);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "denominazione", xmlWriter);
                             

                                          if (localDenominazione==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("denominazione cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDenominazione);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localCivicoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "civico", xmlWriter);
                             

                                          if (localCivico==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("civico cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCivico);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEsponenteTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "esponente", xmlWriter);
                             

                                          if (localEsponente==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("esponente cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEsponente);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localColoreTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "colore", xmlWriter);
                             

                                          if (localColore==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("colore cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localColore);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localScalaTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "scala", xmlWriter);
                             

                                          if (localScala==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("scala cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localScala);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInternoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "interno", xmlWriter);
                             

                                          if (localInterno==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("interno cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInterno);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEsponenteInternoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "esponenteInterno", xmlWriter);
                             

                                          if (localEsponenteInterno==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("esponenteInterno cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEsponenteInterno);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPianoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "piano", xmlWriter);
                             

                                          if (localPiano==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("piano cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPiano);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFabbricatoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "fabbricato", xmlWriter);
                             

                                          if (localFabbricato==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fabbricato cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFabbricato);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localKmTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "km", xmlWriter);
                             

                                          if (localKm==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("km cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localKm);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFrazioneTracker){
                                            if (localFrazione==null){
                                                 throw new org.apache.axis2.databinding.ADBException("frazione cannot be null!!");
                                            }
                                           localFrazione.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","frazione"),
                                               xmlWriter);
                                        } if (localCircoscrizioneTracker){
                                            if (localCircoscrizione==null){
                                                 throw new org.apache.axis2.databinding.ADBException("circoscrizione cannot be null!!");
                                            }
                                           localCircoscrizione.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","circoscrizione"),
                                               xmlWriter);
                                        } if (localQuartiereTracker){
                                            if (localQuartiere==null){
                                                 throw new org.apache.axis2.databinding.ADBException("quartiere cannot be null!!");
                                            }
                                           localQuartiere.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","quartiere"),
                                               xmlWriter);
                                        } if (localRiferimentoCatastaleTracker){
                                       if (localRiferimentoCatastale!=null){
                                            for (int i = 0;i < localRiferimentoCatastale.length;i++){
                                                if (localRiferimentoCatastale[i] != null){
                                                 localRiferimentoCatastale[i].serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","riferimentoCatastale"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("riferimentoCatastale cannot be null!!");
                                        
                                    }
                                 } if (localCoordinateTracker){
                                            if (localCoordinate==null){
                                                 throw new org.apache.axis2.databinding.ADBException("coordinate cannot be null!!");
                                            }
                                           localCoordinate.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","coordinate"),
                                               xmlWriter);
                                        } if (localTipoTracker){
                                            if (localTipo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("tipo cannot be null!!");
                                            }
                                           localTipo.serialize(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tipo"),
                                               xmlWriter);
                                        } if (localUuidTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "uuid", xmlWriter);
                             

                                          if (localUuid==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("uuid cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUuid);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCapTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "cap", xmlWriter);
                             

                                          if (localCap==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cap cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCap);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccessoTipoTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "accessoTipo", xmlWriter);
                             

                                          if (localAccessoTipo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accessoTipo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccessoTipo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccessoNumeroTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "accessoNumero", xmlWriter);
                             

                                          if (localAccessoNumero==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accessoNumero cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccessoNumero);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccessoDescrizioneTracker){
                                    namespace = "http://sigepro.init.it/rte/types";
                                    writeStartElement(null, namespace, "accessoDescrizione", xmlWriter);
                             

                                          if (localAccessoDescrizione==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accessoDescrizione cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccessoDescrizione);
                                            
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "id"));
                                 
                                        if (localId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "codiceViario"));
                                 
                                        if (localCodiceViario != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodiceViario));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codiceViario cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "denominazione"));
                                 
                                        if (localDenominazione != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDenominazione));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("denominazione cannot be null!!");
                                        }
                                     if (localCivicoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "civico"));
                                 
                                        if (localCivico != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCivico));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("civico cannot be null!!");
                                        }
                                    } if (localEsponenteTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "esponente"));
                                 
                                        if (localEsponente != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsponente));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("esponente cannot be null!!");
                                        }
                                    } if (localColoreTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "colore"));
                                 
                                        if (localColore != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localColore));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("colore cannot be null!!");
                                        }
                                    } if (localScalaTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "scala"));
                                 
                                        if (localScala != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localScala));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("scala cannot be null!!");
                                        }
                                    } if (localInternoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "interno"));
                                 
                                        if (localInterno != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterno));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("interno cannot be null!!");
                                        }
                                    } if (localEsponenteInternoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "esponenteInterno"));
                                 
                                        if (localEsponenteInterno != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsponenteInterno));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("esponenteInterno cannot be null!!");
                                        }
                                    } if (localPianoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "piano"));
                                 
                                        if (localPiano != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPiano));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("piano cannot be null!!");
                                        }
                                    } if (localFabbricatoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "fabbricato"));
                                 
                                        if (localFabbricato != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFabbricato));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fabbricato cannot be null!!");
                                        }
                                    } if (localKmTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "km"));
                                 
                                        if (localKm != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localKm));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("km cannot be null!!");
                                        }
                                    } if (localFrazioneTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "frazione"));
                            
                            
                                    if (localFrazione==null){
                                         throw new org.apache.axis2.databinding.ADBException("frazione cannot be null!!");
                                    }
                                    elementList.add(localFrazione);
                                } if (localCircoscrizioneTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "circoscrizione"));
                            
                            
                                    if (localCircoscrizione==null){
                                         throw new org.apache.axis2.databinding.ADBException("circoscrizione cannot be null!!");
                                    }
                                    elementList.add(localCircoscrizione);
                                } if (localQuartiereTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "quartiere"));
                            
                            
                                    if (localQuartiere==null){
                                         throw new org.apache.axis2.databinding.ADBException("quartiere cannot be null!!");
                                    }
                                    elementList.add(localQuartiere);
                                } if (localRiferimentoCatastaleTracker){
                             if (localRiferimentoCatastale!=null) {
                                 for (int i = 0;i < localRiferimentoCatastale.length;i++){

                                    if (localRiferimentoCatastale[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                          "riferimentoCatastale"));
                                         elementList.add(localRiferimentoCatastale[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("riferimentoCatastale cannot be null!!");
                                    
                             }

                        } if (localCoordinateTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "coordinate"));
                            
                            
                                    if (localCoordinate==null){
                                         throw new org.apache.axis2.databinding.ADBException("coordinate cannot be null!!");
                                    }
                                    elementList.add(localCoordinate);
                                } if (localTipoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "tipo"));
                            
                            
                                    if (localTipo==null){
                                         throw new org.apache.axis2.databinding.ADBException("tipo cannot be null!!");
                                    }
                                    elementList.add(localTipo);
                                } if (localUuidTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "uuid"));
                                 
                                        if (localUuid != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUuid));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("uuid cannot be null!!");
                                        }
                                    } if (localCapTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "cap"));
                                 
                                        if (localCap != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCap));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cap cannot be null!!");
                                        }
                                    } if (localAccessoTipoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "accessoTipo"));
                                 
                                        if (localAccessoTipo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessoTipo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accessoTipo cannot be null!!");
                                        }
                                    } if (localAccessoNumeroTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "accessoNumero"));
                                 
                                        if (localAccessoNumero != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessoNumero));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accessoNumero cannot be null!!");
                                        }
                                    } if (localAccessoDescrizioneTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://sigepro.init.it/rte/types",
                                                                      "accessoDescrizione"));
                                 
                                        if (localAccessoDescrizione != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessoDescrizione));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accessoDescrizione cannot be null!!");
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
        public static LocalizzazioneNelComuneType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            LocalizzazioneNelComuneType object =
                new LocalizzazioneNelComuneType();

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
                    
                            if (!"LocalizzazioneNelComuneType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (LocalizzazioneNelComuneType)it.init.sigepro.rte.elements.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list16 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","id").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","codiceViario").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodiceViario(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","denominazione").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDenominazione(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","civico").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCivico(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","esponente").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEsponente(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","colore").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setColore(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","scala").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setScala(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","interno").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInterno(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","esponenteInterno").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEsponenteInterno(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","piano").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPiano(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","fabbricato").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFabbricato(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","km").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setKm(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","frazione").equals(reader.getName())){
                                
                                                object.setFrazione(it.init.sigepro.rte.types.FrazioneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","circoscrizione").equals(reader.getName())){
                                
                                                object.setCircoscrizione(it.init.sigepro.rte.types.CircoscrizioneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","quartiere").equals(reader.getName())){
                                
                                                object.setQuartiere(it.init.sigepro.rte.types.QuartiereType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","riferimentoCatastale").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list16.add(it.init.sigepro.rte.types.RiferimentoCatastaleType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone16 = false;
                                                        while(!loopDone16){
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
                                                                loopDone16 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","riferimentoCatastale").equals(reader.getName())){
                                                                    list16.add(it.init.sigepro.rte.types.RiferimentoCatastaleType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone16 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setRiferimentoCatastale((it.init.sigepro.rte.types.RiferimentoCatastaleType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                it.init.sigepro.rte.types.RiferimentoCatastaleType.class,
                                                                list16));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","coordinate").equals(reader.getName())){
                                
                                                object.setCoordinate(it.init.sigepro.rte.types.CoordinateType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","tipo").equals(reader.getName())){
                                
                                                object.setTipo(it.init.sigepro.rte.types.TipoLocalizzazioneType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","uuid").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUuid(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","cap").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCap(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","accessoTipo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccessoTipo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","accessoNumero").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccessoNumero(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://sigepro.init.it/rte/types","accessoDescrizione").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccessoDescrizione(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
           
    