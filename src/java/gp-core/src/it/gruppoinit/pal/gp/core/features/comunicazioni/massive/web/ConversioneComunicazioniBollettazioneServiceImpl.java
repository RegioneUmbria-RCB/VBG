package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive.metadati.BollMetadatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.BollCfgTipoMetadatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.MetadatoBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ParametriConstants;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ComunicazioneBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaTestata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniToBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneFlyweight;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneMail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@Service
public class ConversioneComunicazioniBollettazioneServiceImpl implements IConversioneComunicazioniBollettazioneService {

    @Autowired
    private IComunicazioniToBollettazioneService comunicazioniToBollettazioneService;
    @Autowired
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private BollCfgTipoMetadatiService bollCfgTipoMetadatiService;

    @Autowired
    public void setBollCfgTipoMetadatiService(BollCfgTipoMetadatiService bollCfgTipoMetadatiService) {

	this.bollCfgTipoMetadatiService = bollCfgTipoMetadatiService;
    }

    @Override
    public ComunicazioneBollettazioneDetail popolaCommandDettaglioByIdTestata(Integer idTestataMassive) {

	FiltriRicercaTestata filtri = new FiltriRicercaTestata(idTestataMassive, ParametriConstants.ESCLUDI_DESTINATARI_SENZA_MAIL,
		ParametriConstants.FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE, ParametriConstants.ALLEGA_AVVISI_PAGAMENTO,
		ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE, ParametriConstants.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF);
	ComunicazioneBollettazione comunicazione = this.comunicazioniToBollettazioneService.getComunicazioneBollettazione(filtri);
	comunicazione.setAllegatiFissi(this.comunicazioniMassiveDAO.getAllegatiFissiByIdTestata(idTestataMassive));
	comunicazione.setLettereTipo(this.comunicazioniMassiveDAO.getDettaglioLettereComunicazioneByIdTestata(idTestataMassive));
	comunicazione.setFirmatari(this.comunicazioniMassiveDAO.getDescrizioneFirmatariByIdTestata(idTestataMassive));
	comunicazione.setRighe(this.comunicazioniMassiveDettaglioDAO.getRigheByIdTestata(idTestataMassive));
	return ComunicazioneBollettazioneDetail.FromComunicazioneBollettazione(comunicazione);
    }

    @Override
    public void popolaCommand(BollGestTestata gestTestata, ConfigurazioneComunicazioniBollettazione confComBoll) {

	ComunicazioniBollettazioneCommand command = new ComunicazioniBollettazioneCommand();
    }

    @Override
    public ConfigurazioneComunicazioniBollettazione popolaConfigurazioneComunicazioniBollettazione(
	    ComunicazioniBollettazioneCommand bollettazioneCommand) {

	validaCommand(bollettazioneCommand);
	ConfigurazioneComunicazioniBollettazione conf = new ConfigurazioneComunicazioniBollettazione();
	ConfigurazioneFlyweight flyweight = new ConfigurazioneFlyweight();
	List<Responsabili> firmatari = bollettazioneCommand.getFirmatari();
	for (Responsabili responsabili : firmatari) {
	    flyweight.getSoggettiFirmatari().add(responsabili.getId().getCodice());
	}
	flyweight.setDescrizione(bollettazioneCommand.getDescrizione());
	if (bollettazioneCommand.getConfiguraParametriMailCommand().getSenderAccount() != null
		&& bollettazioneCommand.getConfiguraParametriMailCommand().getMailtipo() != null) {
	    ConfigurazioneMail confMail = new ConfigurazioneMail(
		    bollettazioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId().getCodice(),
		    bollettazioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId().getCodice());
	    flyweight.setConfigurazioneMail(confMail);
	}
	flyweight.addParametro(ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, bollettazioneCommand.isAllegaAvvisiPagamento());
	flyweight.addParametro(ParametriConstants.ESCLUDI_DESTINATARI_SENZA_MAIL,
		bollettazioneCommand.getConfiguraParametriMailCommand().isEscludiNoMail());
	flyweight.addParametro(ConfigurazioneComunicazioniBollettazione.ID_BOLLETTAZIONE, bollettazioneCommand.getGestTestata().getId().getCodice());
	flyweight.addParametro(ParametriConstants.RICHIEDE_PROTOCOLLAZIONE, bollettazioneCommand.getProtocollaParametriCommand().isProtocolla());
	flyweight.addParametro(ParametriConstants.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, bollettazioneCommand.isConvertiPDF());
	flyweight.addParametro(ParametriConstants.FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE, bollettazioneCommand.isSoloPosizioniDebitorieNonPagate());
	flyweight.addParametro(ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE,
		bollettazioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice());
	for (Integer idAllegatoFisso : bollettazioneCommand.getAllegatiFissi()) {
	    flyweight.getAllegatiFissi().add(new AllegatoComunicazione(idAllegatoFisso));
	}
	for (Letteretipo letteretipo : bollettazioneCommand.getAllegaticompilabili()) {
	    LetteraComunicazione comunicazione = new LetteraComunicazione();
	    comunicazione.setCodiceLettera(letteretipo.getId().getCodice());
	    flyweight.getLettereComunicazione().add(comunicazione);
	}
	if (bollettazioneCommand.getProtocollaParametriCommand().isProtocolla()) {
	    List<ParametriProtocolloPerEnte> listParametriPerEnte = convertParametriPerEnte(
		    bollettazioneCommand.getProtocollaParametriCommand().getParametriPerEnte());
	    ParametriProtocollazione parametriProtocollazione = new ParametriProtocollazione(
		    bollettazioneCommand.getProtocollaParametriCommand().getMailtipo().getId().getCodice(), listParametriPerEnte);
	    flyweight.setParametriProtocollazione(parametriProtocollazione);
	}
	conf.inizializzaDaDatiDb(flyweight);
	return conf;
    }

    private List<ParametriProtocolloPerEnte> convertParametriPerEnte(List<IParametriProtocolloPerEnteHelper> parametriPerEnte) {

	List<ParametriProtocolloPerEnte> ret = new ArrayList<ParametriProtocolloPerEnte>();
	for (IParametriProtocolloPerEnteHelper ph : parametriPerEnte) {
	    // check NPE
	    // la validazione dovrebbe garantire che non siano presenti valori nulli
	    String codiceComune = null;
	    if (ph.getComune() != null && StringUtils.isNotBlank(ph.getComune().getCodice())) {
		codiceComune = ph.getComune().getCodice();
	    }
	    ret.add(ParametriProtocolloPerEnte.fromIParametriProtocolloPerEnteHelper(ph));
	}
	return ret;
    }

    @Override
    public void validaCommand(ComunicazioniBollettazioneCommand bollettazioneCommand) throws BusinessValidationException {

	if (bollettazioneCommand.getDescrizione() == null) {
	    throw new BusinessValidationException("Il campo Descrizione è obbligatorio");
	}
	validaConfigurazioneMail(bollettazioneCommand);
	validaProtocollazione(bollettazioneCommand);
    }

    private void validaConfigurazioneMail(ComunicazioniBollettazioneCommand bollettazioneCommand) {

	List<InvalidValue> errori = new ArrayList<InvalidValue>();
	if (bollettazioneCommand.getConfiguraParametriMailCommand() == null
		|| bollettazioneCommand.getConfiguraParametriMailCommand().getMailtipo() == null
		|| bollettazioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId() == null
		|| bollettazioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId().getCodice() == null) {
	    errori.add(new InvalidValue("alert.required", bollettazioneCommand.getClass(), "configuraParametriMailCommand.mailtipo", null,
		    bollettazioneCommand));
	}
	if (bollettazioneCommand.getConfiguraParametriMailCommand() == null
		|| bollettazioneCommand.getConfiguraParametriMailCommand().getSenderAccount() == null
		|| bollettazioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId() == null
		|| bollettazioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId().getCodice() == null) {
	    errori.add(new InvalidValue("alert.required", bollettazioneCommand.getClass(), "configuraParametriMailCommand.senderAccount", null,
		    bollettazioneCommand));
	}
	if (bollettazioneCommand.getConfiguraParametriMailCommand() == null
		|| bollettazioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe() == null
		|| StringUtils.isBlank(bollettazioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice())) {
	    errori.add(new InvalidValue("alert.required", bollettazioneCommand.getClass(), "configuraParametriMailCommand.sceltaMailAnagrafe", null,
		    bollettazioneCommand));
	}
	if (!errori.isEmpty()) {
	    throw new BusinessValidationException(errori, "Errore di validazione dei parametri mail", null);
	}
    }

    private void validaProtocollazione(ComunicazioniBollettazioneCommand bollettazioneCommand) {

	if (bollettazioneCommand.getProtocollaParametriCommand() != null && bollettazioneCommand.getProtocollaParametriCommand().isProtocolla()) {
	    if (bollettazioneCommand.getProtocollaParametriCommand().getParametriPerEnte().isEmpty()) {
		throw new BusinessValidationException("Non sono stati indicati i parametri di protocollazione");
	    }
	    for (IParametriProtocolloPerEnteHelper pp : bollettazioneCommand.getProtocollaParametriCommand().getParametriPerEnte()) {
		if (StringUtils.isBlank(pp.getClassifica())) {
		    throw new BusinessValidationException("Il campo Classifica del protocollo è obbligatorio");
		}
		if (pp.getAmmMittente() == null || pp.getAmmMittente().getId() == null) {
		    throw new BusinessValidationException("Il campo Ammistrazione del protocollo è obbligatorio");
		}
		if (StringUtils.isBlank(pp.getTipodocumento())) {
		    if (pp.getListaTipiDocumento() != null && pp.getListaTipiDocumento().size() > 0) {
			throw new BusinessValidationException("Il campo Tipodocumento del protocollo è obbligatorio");
		    }
		}
	    }
	    if (bollettazioneCommand.getProtocollaParametriCommand().getMailtipo() == null) {
		throw new BusinessValidationException("Il campo MailTipo del protocollo è obbligatorio");
	    }
	}
    }

    @Override
    public List<IParametriProtocolloPerEnteHelper> popolaParametriPerProtocolloCommand(
	    ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand) {

	List<IParametriProtocolloPerEnteHelper> helps = comunicazioniBollettazioneCommand.getProtocollaParametriCommand().getParametriPerEnte();
	if (helps.isEmpty()) {
	    Integer bollGestTestataId = comunicazioniBollettazioneCommand.getGestTestata().getId().getCodice();
	    helps = comunicazioniToBollettazioneService.popolaParametriProtocollazione(bollGestTestataId);
	    List<MetadatoBollettazione> metadati = new BollMetadatiService(this.bollCfgTipoMetadatiService)
		    .elencoMetadati(comunicazioniBollettazioneCommand.getGestTestata());
	    for (IParametriProtocolloPerEnteHelper parametriProtocollo : helps) {
		for (MetadatoBollettazione metadato : metadati) {
		    if (metadato.getCodiceComune() == null || parametriProtocollo.getComune().getCodice().endsWith(metadato.getCodiceComune())) {
			parametriProtocollo.getMetadati().add(new MetadatiBean(metadato.getChiave(), metadato.getValore()));
		    }
		}
	    }
	}
	return helps;
    }
}
