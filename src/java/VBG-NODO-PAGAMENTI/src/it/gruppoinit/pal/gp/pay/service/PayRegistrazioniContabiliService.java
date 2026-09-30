/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;

/**
 * @author francol
 *
 */
public interface PayRegistrazioniContabiliService extends BaseService<PayRegistrazioniContabili, PkId> {

    public PayRegistrazioniContabili creaRegistrazioneContabile(RegistrazioneContabileType regCont, PayConfigurationHelper payCfg, boolean otf)
	    throws PayException;

    public int countPosizioniByIdRegistrazione(Integer idRegistrazioneContabile);

    public List<Integer> findIdPosizioniByIdRegistrazione(Integer idRegistrazioneContabile);

	public PayRegistrazioniContabili creaRegistrazioneContabileSingolaPd(RegistrazioneContabileType regCont,
			PayConfigurationHelper payCfg, boolean otf, String iuv, String codiceavviso, String idpsp,
			StatiPagamento stato, Date dataevento) throws PayException;
}
