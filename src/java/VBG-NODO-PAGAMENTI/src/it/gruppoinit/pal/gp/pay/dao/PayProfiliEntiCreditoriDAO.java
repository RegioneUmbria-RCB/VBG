/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.features.interfaccia.ConnectorConfigHelper;

/**
 * @author francol
 *
 */
public interface PayProfiliEntiCreditoriDAO extends BaseDAO<PayProfiliEntiCreditori, PkId> {

    public List<PayProfiliEntiCreditori> findByCfCodiceProfilo(String codProfiloEnte);

    public List<PayProfiliEntiCreditori> findByCodiceProfiloPSP(String codProfiloEnte);

    public PayProfiliEntiCreditori findByIdAppPspAndIdPosizionePsp(String idAppPsp, String idPosizionePsp);

    public List<ConnectorConfigHelper> findConfigHelperByJavaclass(String javaClass);
}
