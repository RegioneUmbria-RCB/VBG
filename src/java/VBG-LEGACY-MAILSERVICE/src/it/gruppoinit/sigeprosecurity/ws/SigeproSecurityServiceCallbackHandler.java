/**
 * SigeproSecurityServiceCallbackHandler.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package it.gruppoinit.sigeprosecurity.ws;

/**
 * SigeproSecurityServiceCallbackHandler Callback class, Users can extend this class and implement their own
 * receiveResult and receiveError methods.
 */
public abstract class SigeproSecurityServiceCallbackHandler {

    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking Web service call is finished and
     * appropriate method of this CallBack is called.
     * 
     * @param clientData
     *            Object mechanism by which the user can pass in user data that will be avilable at the time this
     *            callback is called.
     */
    public SigeproSecurityServiceCallbackHandler(Object clientData) {

	this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SigeproSecurityServiceCallbackHandler() {

	this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {

	return clientData;
    }

    /**
     * auto generated Axis2 call back method for getDbConnectionInfo method override this method for handling normal
     * response from getDbConnectionInfo operation
     */
    public void receiveResultgetDbConnectionInfo(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetDbConnectionInfoResponse result) {

    }

    /**
     * auto generated Axis2 Error handler override this method for handling error response from getDbConnectionInfo
     * operation
     */
    public void receiveErrorgetDbConnectionInfo(java.lang.Exception e) {

    }

    /**
     * auto generated Axis2 call back method for logout method override this method for handling normal response from
     * logout operation
     */
    public void receiveResultlogout(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LogoutResponse result) {

    }

    /**
     * auto generated Axis2 Error handler override this method for handling error response from logout operation
     */
    public void receiveErrorlogout(java.lang.Exception e) {

    }

    /**
     * auto generated Axis2 call back method for login method override this method for handling normal response from
     * login operation
     */
    public void receiveResultlogin(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse result) {

    }

    /**
     * auto generated Axis2 Error handler override this method for handling error response from login operation
     */
    public void receiveErrorlogin(java.lang.Exception e) {

    }

    /**
     * auto generated Axis2 call back method for loginSSO method override this method for handling normal response from
     * loginSSO operation
     */
    public void receiveResultloginSSO(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginSSOResponse result) {

    }

    /**
     * auto generated Axis2 Error handler override this method for handling error response from loginSSO operation
     */
    public void receiveErrorloginSSO(java.lang.Exception e) {

    }

    /**
     * auto generated Axis2 call back method for getApplicationInfo method override this method for handling normal
     * response from getApplicationInfo operation
     */
    public void receiveResultgetApplicationInfo(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse result) {

    }

    /**
     * auto generated Axis2 Error handler override this method for handling error response from getApplicationInfo
     * operation
     */
    public void receiveErrorgetApplicationInfo(java.lang.Exception e) {

    }

    /**
     * auto generated Axis2 call back method for getSecurityList method override this method for handling normal
     * response from getSecurityList operation
     */
    public void receiveResultgetSecurityList(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetSecurityListResponse result) {

    }

    /**
     * auto generated Axis2 Error handler override this method for handling error response from getSecurityList
     * operation
     */
    public void receiveErrorgetSecurityList(java.lang.Exception e) {

    }

    /**
     * auto generated Axis2 call back method for checkToken method override this method for handling normal response
     * from checkToken operation
     */
    public void receiveResultcheckToken(it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.CheckTokenResponse result) {

    }

    /**
     * auto generated Axis2 Error handler override this method for handling error response from checkToken operation
     */
    public void receiveErrorcheckToken(java.lang.Exception e) {

    }
}
