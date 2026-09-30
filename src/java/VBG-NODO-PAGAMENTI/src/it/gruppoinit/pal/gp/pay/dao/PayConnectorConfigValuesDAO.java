package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigValues;
import it.gruppoinit.pal.gp.pay.features.interfaccia.PayConnectorConfigValuesHelper;

public interface PayConnectorConfigValuesDAO extends BaseDAO<PayConnectorConfigValues, PkId> {

    /**
     * restituisce il valore del parametro di configurazione passato in input per il connettore corrente. Se il
     * parametro non è valorizzato per il connettore corrente, il metodo verifica se è valorizzato per tutti i
     * connettori come valore di default ed eventualmente restituisce il valore di default se presente.
     * 
     * @param paramName
     * @return
     */
    public String getValoreParametroConfigurazione(ConfigParamNames paramName);

    /**
     * restituisce il valore del parametro di configurazione passato in input per il codice connettore passato come
     * argomento. Se il parametro non è valorizzato per il connettore corrente, il metodo verifica se è valorizzato per
     * tutti i connettori come valore di default ed eventualmente restituisce il valore di default se presente.
     * 
     * @param paramName
     * @param connectorCode
     * @return
     */
    public String getValoreParametroConfigurazione(ConfigParamNames paramName, String connectorCode);

    /**
     * Restiuisce la lista di tutti i valori dei parametri di connessione per un determinato connettore
     * 
     * @param connectorCode
     * @return
     */
    public List<PayConnectorConfigValuesHelper> getConnectorConfigValuesFromCodiceConnettore(String connectorCode);
}
