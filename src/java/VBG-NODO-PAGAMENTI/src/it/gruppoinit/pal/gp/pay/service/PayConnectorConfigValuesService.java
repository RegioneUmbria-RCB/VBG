package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigValues;
import it.gruppoinit.pal.gp.pay.features.interfaccia.PayConnectorConfigValuesHelper;

public interface PayConnectorConfigValuesService extends BaseService<PayConnectorConfigValues, PkId> {

    public boolean isDocumentiSuFilesystem();

    public String getDocumentiSuFilesystemPath();

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

    public List<PayConnectorConfigValuesHelper> getConnectorConfigValuesFromCodiceConnettore(String connectorCode);
}
