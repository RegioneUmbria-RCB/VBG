/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.features.interfaccia.InfoCausaliParam;
import it.gruppoinit.pal.gp.pay.service.helper.CausaliConnettoreBean;

/**
 * @author francol
 *
 */
public interface PayRegistrazioniCausaliDAO extends BaseDAO<PayRegistrazioniCausali, PkId> {

    List<CausaliConnettoreBean> findInfoCausaliPerConnettore(List<String> cfCodiciProfilo);

    List<InfoCausaliParam> findInfoCausaliRidottePerConnettore(String cfcodprofilo);

	String findMappaturaClientByPosizioneDebitoria(PayPosizioniDebitorie payPos);
}
