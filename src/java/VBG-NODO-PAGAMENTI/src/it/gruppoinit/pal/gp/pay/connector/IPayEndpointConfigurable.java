package it.gruppoinit.pal.gp.pay.connector;

import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;

public interface IPayEndpointConfigurable {

    /**
     * metodo che restituisce l'url dell'endpoint del WS del PSP invocato dalo connettore
     * 
     * @return
     */
    @Deprecated
    public String getWsEndpointUrl();

    /**
     * metodo per la configurazione automatica da DB dell'url dell'endpoint del WS del PSP invocato dalo connettore
     * 
     * @return
     */
    @Deprecated
    public void setWsEndpointUrl(String wsEndpointUrl);

    /**
     * metodo che restituisce l'utente del WS del PSP invocato dal connettore
     * 
     * @return
     */
    @Deprecated
    public String getWsUser();

    /**
     * metodo per la configurazione automatica da DB dell'utente del WS del PSP invocato dalo connettore
     * 
     * @return
     */
    @Deprecated
    public void setWsUser(String wsUser);

    /**
     * metodo che restituisce la password del WS del PSP invocato dal connettore
     * 
     * @return
     */
    @Deprecated
    public String getWsPassword();

    /**
     * metodo per la configurazione automatica da DB della password del WS del PSP invocato dalo connettore
     * 
     * @return
     */
    @Deprecated
    public void setWsPassword(String wsPassword);

    /**
     * metodo che restituisce il timeout del WS del PSP invocato dal connettore
     * 
     * @return
     */
    @Deprecated
    public Integer getWsTimeout();

    /**
     * metodo per la configurazione automatica da DB della password del WS del PSP invocato dalo connettore
     * 
     * @return
     */
    @Deprecated
    public void setWsTimeout(Integer wsTimeout);

    /**
     * metodo che restituisce l'identificativo univoco dell'installazione (valorizzato da deploy.properties)
     * 
     * @return
     */
    public String getIdInstallazione();

    /**
     * metodo per la configurazione dell'identificativo univoco dell'installazione (settato in deploy.properties)
     * 
     * @return
     */
    /*
     * metodi per la configurazione degli endpoint dei vari WS utilizzati dal connettore
     */
    public void setIdInstallazione(String idInstallazione);

    public PayConnectorWsEndpoint getWsCaricamentoConfig();

    public void setWsCaricamentoConfig(PayConnectorWsEndpoint config);

    public void setWsAnnullamentoConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsAnnullamentoConfig();

    public void setWsVerificaConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsVerificaConfig();

    public void setWsAttivaSessioneConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsAttivaSessioneConfig();

    public void setWsAvvisoConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsAvvisoConfig();

    public void setWsSecurityConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsSecurityConfig();

    public void setWsNotificaConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsNotificaConfig();

    public void setWsRicevutaConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsRicevutaConfig();

    public void setWsFatturaConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsFatturaConfig();

    public void setWsIuvConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsIuvConfig();

    public void setWsCaricamentoMassivoConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsCaricamentoMassivoConfig();
    
    public void setWsSincronizzaDebitiPerSoggettoConfig(PayConnectorWsEndpoint config);

    public PayConnectorWsEndpoint getWsSincronizzaDebitiPerSoggettoConfig();
}
