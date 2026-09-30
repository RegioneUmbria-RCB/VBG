package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

public class NumerazioneDaProtocolloServiceImpl implements NumerazioneService {

    private Autorizzazioni aut;
    private Movimenti movimento;
    public static NumerazioneEnum TipoNumerazione = NumerazioneEnum.DA_PROTOCOLLO;
    private Integer codiceRegistro;
    private TipologiaregistriService registriService;
    private UserSecurityService userSecurityService;
    private ProtocollazioneService protocollazioneService;

    public NumerazioneDaProtocolloServiceImpl(TipologiaregistriService registriService, Autorizzazioni aut, UserSecurityService userSecurityService,
	    ProtocollazioneService protocollazioneService) {

	// Il numero dell'autorizzazione e la data saranno assegnati automaticamente in fase di salvataggio. 
	// Se l'autorizzazione è stata creata dal movimento, e questo è protocollato, saranno usati il numero e la data di protocollo del movimento, altrimenti verrà richiesta una nuova protocollazione.
	this.aut = aut;
	this.movimento = aut.getMovimenti();
	this.codiceRegistro = aut.getTipologiaregistro().getId().getCodice();
	this.registriService = registriService;
	this.userSecurityService = userSecurityService;
	this.protocollazioneService = protocollazioneService;
    }

    private boolean checkMovimentoProtocollato() {

	return (movimento != null && StringUtils.isNotBlank(movimento.getNumeroprotocollo()) && movimento.getDataprotocollo() != null);
    }

    @Override
    public EstremiAutorizzazione get() {

	if (checkMovimentoProtocollato()) {
	    return new EstremiAutorizzazione(movimento.getFkidprotocollo(), movimento.getNumeroprotocollo(), movimento.getDataprotocollo());
	}
	return new EstremiAutorizzazione(null, null, null);
    }

    @Override
    public EstremiAutorizzazione assegnaNumero() {

	if (checkMovimentoProtocollato()) {
	    return new EstremiAutorizzazione(movimento.getFkidprotocollo(), movimento.getNumeroprotocollo(), movimento.getDataprotocollo());
	}
	if (registriService.checkSeNumeratoreEsterno(this.codiceRegistro)) {
	    Responsabili operatore = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    try {
		protocollazioneService.protocollaAutorizzazione(codiceRegistro, operatore.getId().getCodice().intValue(), ORMHelper.getToken(),
			Calendar.getInstance(), aut, aut.getAutorizcomune().getCodicecomune());
		return new EstremiAutorizzazione(aut.getFkidprotocollo(), aut.getAutoriznumero(), aut.getAutorizdata());
	    } catch (Exception e) {
		throw new InvalidConfigurationException(e.getMessage());
	    }
	}
	throw new NotImplementedException();
    }
}
