
/**
 * StcServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package it.init.sigepro.rte.definitions;

    /**
     *  StcServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class StcServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public StcServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public StcServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for notificaAttivita method
            * override this method for handling normal response from notificaAttivita operation
            */
           public void receiveResultnotificaAttivita(
                    it.init.sigepro.rte.definitions.StcServiceStub.NotificaAttivitaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from notificaAttivita operation
           */
            public void receiveErrornotificaAttivita(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for aggiungiDocumenti method
            * override this method for handling normal response from aggiungiDocumenti operation
            */
           public void receiveResultaggiungiDocumenti(
                    it.init.sigepro.rte.definitions.StcServiceStub.AggiungiDocumentiResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from aggiungiDocumenti operation
           */
            public void receiveErroraggiungiDocumenti(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for richiestaPraticheLista method
            * override this method for handling normal response from richiestaPraticheLista operation
            */
           public void receiveResultrichiestaPraticheLista(
                    it.init.sigepro.rte.definitions.StcServiceStub.RichiestaPraticheListaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from richiestaPraticheLista operation
           */
            public void receiveErrorrichiestaPraticheLista(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for richiestaPraticaCollegataDaAttivitaMittente method
            * override this method for handling normal response from richiestaPraticaCollegataDaAttivitaMittente operation
            */
           public void receiveResultrichiestaPraticaCollegataDaAttivitaMittente(
                    it.init.sigepro.rte.definitions.StcServiceStub.RichiestaPraticaCollegataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from richiestaPraticaCollegataDaAttivitaMittente operation
           */
            public void receiveErrorrichiestaPraticaCollegataDaAttivitaMittente(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for allegatoBinario method
            * override this method for handling normal response from allegatoBinario operation
            */
           public void receiveResultallegatoBinario(
                    it.init.sigepro.rte.definitions.StcServiceStub.AllegatoBinarioResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from allegatoBinario operation
           */
            public void receiveErrorallegatoBinario(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancellaAttivita method
            * override this method for handling normal response from cancellaAttivita operation
            */
           public void receiveResultcancellaAttivita(
                    ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancellaAttivita operation
           */
            public void receiveErrorcancellaAttivita(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for inserimentoPratica method
            * override this method for handling normal response from inserimentoPratica operation
            */
           public void receiveResultinserimentoPratica(
                    it.init.sigepro.rte.definitions.StcServiceStub.InserimentoPraticaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inserimentoPratica operation
           */
            public void receiveErrorinserimentoPratica(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for direzioneSportello method
            * override this method for handling normal response from direzioneSportello operation
            */
           public void receiveResultdirezioneSportello(
                    it.init.sigepro.rte.definitions.StcServiceStub.DirezioneSportelloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from direzioneSportello operation
           */
            public void receiveErrordirezioneSportello(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkToken method
            * override this method for handling normal response from checkToken operation
            */
           public void receiveResultcheckToken(
                    it.init.sigepro.rte.definitions.StcServiceStub.CheckTokenResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkToken operation
           */
            public void receiveErrorcheckToken(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for richiestaPraticaCollegata method
            * override this method for handling normal response from richiestaPraticaCollegata operation
            */
           public void receiveResultrichiestaPraticaCollegata(
                    it.init.sigepro.rte.definitions.StcServiceStub.RichiestaPraticaCollegataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from richiestaPraticaCollegata operation
           */
            public void receiveErrorrichiestaPraticaCollegata(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for richiestaPratica method
            * override this method for handling normal response from richiestaPratica operation
            */
           public void receiveResultrichiestaPratica(
                    it.init.sigepro.rte.definitions.StcServiceStub.RichiestaPraticaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from richiestaPratica operation
           */
            public void receiveErrorrichiestaPratica(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for richiestaPraticaCollegataDaAttivitaDestinataria method
            * override this method for handling normal response from richiestaPraticaCollegataDaAttivitaDestinataria operation
            */
           public void receiveResultrichiestaPraticaCollegataDaAttivitaDestinataria(
                    it.init.sigepro.rte.definitions.StcServiceStub.RichiestaPraticaCollegataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from richiestaPraticaCollegataDaAttivitaDestinataria operation
           */
            public void receiveErrorrichiestaPraticaCollegataDaAttivitaDestinataria(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    it.init.sigepro.rte.definitions.StcServiceStub.LoginResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                


    }
    