/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.features.interfaccia.InfoCausaliParam;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleBean;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaliPerConnettore;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.CausaleRegistrazioneType;

/**
 * @author francol
 *
 */
public interface PayRegistrazioniCausaliService extends BaseService<PayRegistrazioniCausali, PkId> {

    public PayRegistrazioniCausali findCausaleRegistrazione(CausaleRegistrazioneType regCaus, PayConfigurationHelper payCfg) throws PayException;

    public InfoCausaleBean findInfoCausale(String codiceMappatura);

    public List<InfoCausaliPerConnettore> findCausaliPerConnettore(List<String> cfCodiciProfilo);

    public PayRegistrazioniCausali findByMappaturaClient(String codiceMappatura);

    public List<InfoCausaliParam> findInfoCausaliRidottePerConnettore(String cfcodprofilo);

	public String findMappaturaClientByPosizioneDebitoria(PayPosizioniDebitorie payPos);
}
