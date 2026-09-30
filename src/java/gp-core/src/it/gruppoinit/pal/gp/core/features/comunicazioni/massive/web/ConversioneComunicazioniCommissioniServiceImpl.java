package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ComunicazioneCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaTestataCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniToCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneFlyweight;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneMail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@Service
public class ConversioneComunicazioniCommissioniServiceImpl implements IConversioneComunicazioniCommissioniService {

    @Autowired
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    @Autowired
    private IComunicazioniToCommissioniService comunicazioniToCommissioniService;

    @Override
    public ConfigurazioneComunicazioniCommissioni popolaConfigurazioneComunicazioniCommissioni(ComunicazioniCommissioniCommand commissioneCommand) {

	validaCommand(commissioneCommand);
	ConfigurazioneComunicazioniCommissioni configurazioneComunicazioniCommissioni = new ConfigurazioneComunicazioniCommissioni();
	ConfigurazioneFlyweight flyweight = new ConfigurazioneFlyweight();
	List<Responsabili> firmatari = commissioneCommand.getFirmatari();
	for (Responsabili responsabili : firmatari) {
	    flyweight.getSoggettiFirmatari().add(responsabili.getId().getCodice());
	}
	flyweight.setDescrizione(commissioneCommand.getDescrizione());
	if (commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount() != null
		&& commissioneCommand.getConfiguraParametriMailCommand().getMailtipo() != null) {
	    ConfigurazioneMail confMail = new ConfigurazioneMail(
		    commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId().getCodice(),
		    commissioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId().getCodice());
	    flyweight.setConfigurazioneMail(confMail);
	}
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.ESCLUDI_DESTINATARI_SENZA_MAIL,
		commissioneCommand.getConfiguraParametriMailCommand().isEscludiNoMail());
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.ID_COMMISSIONE, commissioneCommand.getCommissione().getId());
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.RICHIEDE_PROTOCOLLAZIONE,
		commissioneCommand.getProtocollaParametriCommand().isProtocolla());
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, commissioneCommand.isConvertiPDF());
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.GESTIONE_SCELTA_MAIL_ANAGRAFE,
		commissioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice());
	for (Integer idAllegatoFisso : commissioneCommand.getAllegatiFissi()) {
	    flyweight.getAllegatiFissi().add(new AllegatoComunicazione(idAllegatoFisso));
	}
	for (Letteretipo letteretipo : commissioneCommand.getAllegaticompilabili()) {
	    LetteraComunicazione comunicazione = new LetteraComunicazione();
	    comunicazione.setCodiceLettera(letteretipo.getId().getCodice());
	    flyweight.getLettereComunicazione().add(comunicazione);
	}
	if (commissioneCommand.getProtocollaParametriCommand().isProtocolla()) {
	    List<ParametriProtocolloPerEnte> listParametriPerEnte = convertParametriPerEnte(
		    commissioneCommand.getProtocollaParametriCommand().getParametriPerEnte());
	    ParametriProtocollazione parametriProtocollazione = new ParametriProtocollazione(
		    commissioneCommand.getProtocollaParametriCommand().getMailtipo().getId().getCodice(), listParametriPerEnte);
	    flyweight.setParametriProtocollazione(parametriProtocollazione);
	}
	configurazioneComunicazioniCommissioni.inizializzaDaDatiDb(flyweight);
	return configurazioneComunicazioniCommissioni;
    }

    @Override
    public void validaCommand(ComunicazioniCommissioniCommand commissioneCommand) throws BusinessValidationException {

	if (commissioneCommand.getDescrizione() == null) {
	    throw new BusinessValidationException("Il campo Descrizione è obbligatorio");
	}
	validaConfigurazioneMail(commissioneCommand);
	validaProtocollazione(commissioneCommand);
    }

    private void validaConfigurazioneMail(ComunicazioniCommissioniCommand commissioneCommand) {

	List<InvalidValue> errori = new ArrayList<InvalidValue>();
	if (commissioneCommand.getConfiguraParametriMailCommand() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getMailtipo() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId().getCodice() == null) {
	    errori.add(new InvalidValue("alert.required", commissioneCommand.getClass(), "configuraParametriMailCommand.mailtipo", null,
		    commissioneCommand));
	}
	if (commissioneCommand.getConfiguraParametriMailCommand() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId().getCodice() == null) {
	    errori.add(new InvalidValue("alert.required", commissioneCommand.getClass(), "configuraParametriMailCommand.senderAccount", null,
		    commissioneCommand));
	}
	if (commissioneCommand.getConfiguraParametriMailCommand() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe() == null
		|| StringUtils.isBlank(commissioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice())) {
	    errori.add(new InvalidValue("alert.required", commissioneCommand.getClass(), "configuraParametriMailCommand.sceltaMailAnagrafe", null,
		    commissioneCommand));
	}
	if (!errori.isEmpty()) {
	    throw new BusinessValidationException(errori, "Errore di validazione dei parametri mail", null);
	}
    }

    private void validaProtocollazione(ComunicazioniCommissioniCommand commissioneCommand) {

	if (commissioneCommand.getProtocollaParametriCommand() != null && commissioneCommand.getProtocollaParametriCommand().isProtocolla()) {
	    if (commissioneCommand.getProtocollaParametriCommand().getParametriPerEnte().isEmpty()) {
		throw new BusinessValidationException("Non sono stati indicati i parametri di protocollazione");
	    }
	    for (IParametriProtocolloPerEnteHelper pp : commissioneCommand.getProtocollaParametriCommand().getParametriPerEnte()) {
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
	    if (commissioneCommand.getProtocollaParametriCommand().getMailtipo() == null) {
		throw new BusinessValidationException("Il campo MailTipo del protocollo è obbligatorio");
	    }
	}
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
    public List<IParametriProtocolloPerEnteHelper> popolaParametriPerProtocolloCommand(ComunicazioniCommissioniCommand cmd) {

	List<IParametriProtocolloPerEnteHelper> helps = cmd.getProtocollaParametriCommand().getParametriPerEnte();
	if (helps.isEmpty()) {
	    Integer idCommissione = cmd.getCommissione().getId();
	    helps = comunicazioniToCommissioniService.popolaParametriProtocollazione(idCommissione);
	}
	return helps;
    }

    @Override
    public ComunicazioneCommissioneDetail popolaCommandDettaglioByIdTestata(Integer idTestataMassive) {

	FiltriRicercaTestataCommissioni filtri = new FiltriRicercaTestataCommissioni(idTestataMassive,
		ConfigurazioneComunicazioniCommissioni.ESCLUDI_DESTINATARI_SENZA_MAIL,
		ConfigurazioneComunicazioniCommissioni.GESTIONE_SCELTA_MAIL_ANAGRAFE,
		ConfigurazioneComunicazioniCommissioni.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF);
	ComunicazioneCommissione comunicazione = this.comunicazioniToCommissioniService.getComunicazioneCommissione(filtri);
	comunicazione.setAllegatiFissi(this.comunicazioniMassiveDAO.getAllegatiFissiByIdTestata(idTestataMassive));
	comunicazione.setLettereTipo(this.comunicazioniMassiveDAO.getDettaglioLettereComunicazioneByIdTestata(idTestataMassive));
	comunicazione.setFirmatari(this.comunicazioniMassiveDAO.getDescrizioneFirmatariByIdTestata(idTestataMassive));
	comunicazione.setRighe(this.comunicazioniMassiveDettaglioDAO.getRigheByIdTestata(idTestataMassive));
	return ComunicazioneCommissioneDetail.FromComunicazioneCommissione(comunicazione);
    }
}
