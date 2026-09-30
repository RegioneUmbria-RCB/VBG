
/**
 * NlaServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */

    package it.init.sigepro.rte.definitions;

    /**
     *  NlaServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class NlaServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public NlaServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public NlaServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for inserimentoPraticaNLA method
            * override this method for handling normal response from inserimentoPraticaNLA operation
            */
           public void receiveResultinserimentoPraticaNLA(
                    it.init.sigepro.rte.definitions.NlaServiceStub.InserimentoPraticaNLAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inserimentoPraticaNLA operation
           */
            public void receiveErrorinserimentoPraticaNLA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for richiestaPraticheListaNLA method
            * override this method for handling normal response from richiestaPraticheListaNLA operation
            */
           public void receiveResultrichiestaPraticheListaNLA(
                    it.init.sigepro.rte.definitions.NlaServiceStub.RichiestaPraticheListaNLAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from richiestaPraticheListaNLA operation
           */
            public void receiveErrorrichiestaPraticheListaNLA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for allegatoBinarioNLA method
            * override this method for handling normal response from allegatoBinarioNLA operation
            */
           public void receiveResultallegatoBinarioNLA(
                    it.init.sigepro.rte.definitions.NlaServiceStub.AllegatoBinarioNLAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from allegatoBinarioNLA operation
           */
            public void receiveErrorallegatoBinarioNLA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for aggiungiDocumentiNLA method
            * override this method for handling normal response from aggiungiDocumentiNLA operation
            */
           public void receiveResultaggiungiDocumentiNLA(
                    it.init.sigepro.rte.definitions.NlaServiceStub.AggiungiDocumentiNLAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from aggiungiDocumentiNLA operation
           */
            public void receiveErroraggiungiDocumentiNLA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for testNLA method
            * override this method for handling normal response from testNLA operation
            */
           public void receiveResulttestNLA(
                    it.init.sigepro.rte.definitions.NlaServiceStub.TestNLAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from testNLA operation
           */
            public void receiveErrortestNLA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for richiestaPraticaNLA method
            * override this method for handling normal response from richiestaPraticaNLA operation
            */
           public void receiveResultrichiestaPraticaNLA(
                    it.init.sigepro.rte.definitions.NlaServiceStub.RichiestaPraticaNLAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from richiestaPraticaNLA operation
           */
            public void receiveErrorrichiestaPraticaNLA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for inserimentoAttivitaNLA method
            * override this method for handling normal response from inserimentoAttivitaNLA operation
            */
           public void receiveResultinserimentoAttivitaNLA(
                    it.init.sigepro.rte.definitions.NlaServiceStub.InserimentoAttivitaNLAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inserimentoAttivitaNLA operation
           */
            public void receiveErrorinserimentoAttivitaNLA(java.lang.Exception e) {
            }
                


    }
    