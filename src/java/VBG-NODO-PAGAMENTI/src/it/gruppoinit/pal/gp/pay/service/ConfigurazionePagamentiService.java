/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.PayRequestType;

/**
 * @author francol
 *
 */
public interface ConfigurazionePagamentiService {

    public PayProfiliEntiCreditori configuraRequestPerEnteCreditore(PayRequestType request) throws PayConfigurationException;

    public PayProfiliEntiCreditori configuraRequestPerEnteCreditore(String codiceProfilo) throws PayConfigurationException;

    public void configuraRequestPerEnteCreditore(PayProfiliEntiCreditori profiloEnte);

    public PayProfiliEntiCreditori configuraRequestIdAppPspAndIdPosizionePsp(String idAppPsp, String idPosizionePsp)
	    throws PayConfigurationException;

    public PayConfigurationHelper getConfigurazioneEnteCorrente();
}
