/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.features.interfaccia.ConnectorConfigHelper;

/**
 * @author francol
 *
 */
public interface PayProfiliEntiCreditoriService extends BaseService<PayProfiliEntiCreditori, PkId> {

    public PayProfiliEntiCreditori findByCfCodiceProfilo(String codProfiloEnte) throws PayConfigurationException;

    public PayProfiliEntiCreditori findByCodiceProfiloPSP(String codProfiloEnte) throws PayConfigurationException;

    public PayProfiliEntiCreditori findByIdAppPspAndIdPosizionePsp(String idAppPsp, String IdPosizionePsp) throws PayConfigurationException;

    public PayProfiliEntiCreditori findByConnectorCode(String connectorCode) throws PayConfigurationException;

    public List<ConnectorConfigHelper> findConfigHelperByJavaclass(String javaClass) throws PayConfigurationException;
}
