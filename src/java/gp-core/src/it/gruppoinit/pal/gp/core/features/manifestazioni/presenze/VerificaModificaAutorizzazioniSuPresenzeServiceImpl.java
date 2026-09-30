package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioneCancellazioneAutConcException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.CheckSubentroRequest;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaInserita;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class VerificaModificaAutorizzazioniSuPresenzeServiceImpl implements IVerificaModificaAutorizzazioniSuPresenzeService {

    private static final String TESTO_E_PRESENTE_UN_PAGAMENTO_IN_STATO = " è presente un pagamento in stato ";
    private static final String TESTO_SARA_AGGIORNATA_CON = " sarà aggiornata con";
    private static final String TESTO_L_ANAGRAFE = ".<br />L'anagrafe ";
    @Autowired
    private MercatipresenzeDDAO mercatipresenzeDDAO;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private IPagamentiService pagamentiService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;

    @Override
    public EsitoElaborazioneEvento checkPossoSubentrare(CheckSubentroRequest request) {

	mercatipresenzeDDAO.flush();
	List<OperazioneEventoBean> warnings = new ArrayList<OperazioneEventoBean>();
	List<OperazioneEventoBean> errors = new ArrayList<OperazioneEventoBean>();
	Integer idAutConcSubentro = request.getIdAutConc();
	Istanze i = mercatipresenzeDDAO.getById(Istanze.class, request.getCodiceIstanzaSubentro());
	Anagrafe occupante = i.getTitolareLegaleORichiedente();
	// trovo le presenze
	List<MercatipresenzeDBean> presenze = mercatipresenzeDDAO.findPresenzePerAutorizzazioneDallaData(idAutConcSubentro,
		request.getDatiCausali().getDataCessazione());
	for (MercatipresenzeDBean presenza : presenze) {
	    // l'autorizzazione potrebbe essere stata utilizzata o nella colonna FK_AUTORIZZAZIONI_ID o nel campo AUT_CONCESSIONARIO
	    // o in entrambe (la presenza allora è del concessionario)
	    // controllo se posizione debitoria
	    // Se non presente la posizione Debitoria o posizione non pagata allora aggiungo lo warning
	    // aggiungo l'errore
	    Integer codiceOccupanteAutAttuale = occupante.getId().getCodice();
	    String infoPresenza = getInfoPresenza(presenza);
	    Integer idAutorizzazionePresenza = presenza.getIdautpresenza();
	    Integer idAutorizzazioneConcessionario = presenza.getIdautconcessionario();
	    boolean checkPagamento = false;
	    if (idAutConcSubentro.equals(idAutorizzazionePresenza)) {
		// il pagamento è possibile solo se chi ha la presenza ha aperto pos deb o scalato borsellino
		checkPagamento = presenza.getIdposizionedebitoria() != null;
	    }
	    if (checkPagamento && !pagamentiService.isPresenzaConPagamentoAnnullabile(presenza.getIdpresenza())) {
		String messaggio = infoPresenza + TESTO_E_PRESENTE_UN_PAGAMENTO_IN_STATO + presenza.getStatoposdeb();
		// la posizione non è annullabile inserisco errori
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		errors.add(ope);
	    }
	    boolean giaElaborato = false;
	    if (idAutorizzazioneConcessionario != null && presenza.getCodiceconcessionario() != null
		    && idAutorizzazioneConcessionario.equals(idAutConcSubentro)
		    && !codiceOccupanteAutAttuale.equals(presenza.getCodiceconcessionario())) {
		String messaggio = testoMessaggio(occupante, presenza, infoPresenza, true);
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		warnings.add(ope);
		giaElaborato = true;
	    }
	    if (idAutorizzazionePresenza != null && presenza.getCodiceoccupante() != null && idAutorizzazionePresenza.equals(idAutConcSubentro)
		    && !codiceOccupanteAutAttuale.equals(presenza.getCodiceoccupante()) && !giaElaborato) {
		String messaggio = testoMessaggio(occupante, presenza, infoPresenza, false);
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		warnings.add(ope);
	    }
	}
	return new EsitoElaborazioneEvento(warnings, errors);
    }

    @Override
    public void effettuaSubentroSuPresenza(int idPresenza, Integer idSubentroEffettuato) throws OperazioniSubentriException {

	// recupera la presenza
	MercatipresenzeD presenza = mercatipresenzeDDAO.findById(new PkId(idPresenza));
	// recupera il subentro e l'autorizzazione
	AutorizzazioniSubentri sub = mercatipresenzeDDAO.getById(AutorizzazioniSubentri.class, idSubentroEffettuato);
	// processa spuntista
	Integer autorizzazioneDelSubentro = sub.getAutorizzazioni().getId().getCodice();
	Integer idAutorizzazionePresenza = null;
	Integer idAutorizzazioneConcessionario = null;
	if (presenza.getAutorizzazioni() != null) {
	    idAutorizzazionePresenza = presenza.getAutorizzazioni().getId().getCodice();
	}
	if (presenza.getAutorizzazioneConcessionarioAssente() != null) {
	    idAutorizzazioneConcessionario = presenza.getAutorizzazioneConcessionarioAssente().getId().getCodice();
	}
	Integer numeroPresenze = presenza.getNumeropresenze();
	boolean rilanciaEventoPresenzaInserita = false;
	if (autorizzazioneDelSubentro.equals(idAutorizzazionePresenza)) {
	    presenza.setAutorizzazioni(null);
	    presenza.setOccupante(null);
	    presenza.setNumeropresenze(0);
	    mercatipresenzeDDAO.update(presenza);
	    boolean presenteSpuntista = presenza.isSpuntista();
	    Autorizzazioni aut = mercatipresenzeDDAO.getById(Autorizzazioni.class, idAutorizzazionePresenza);
	    //	// annullo anagrafica o le anagrafiche
	    //	EventoPresenzaRevocata	    
	    if (presenza.getPosteggio() != null) {
		try {
		    eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaRevocata(idPresenza, aut, presenteSpuntista));
		    rilanciaEventoPresenzaInserita = true;
		} catch (EventAbortedException e) {
		    throw new OperazioniSubentriException(e);
		}
	    }
	    //	// assegna anagrafica o le anagrafiche
	    //	EventoPresenzaInserita
	    presenza.setAutorizzazioni(aut);
	    presenza.setOccupante(aut.getOccupante());
	    presenza.setNumeropresenze(numeroPresenze);
	    mercatipresenzeDDAO.update(presenza);
	}
	if (autorizzazioneDelSubentro.equals(idAutorizzazioneConcessionario)) {
	    Autorizzazioni aut = mercatipresenzeDDAO.getById(Autorizzazioni.class, idAutorizzazioneConcessionario);
	    presenza.setAutorizzazioneConcessionarioAssente(aut);
	    presenza.setConcessionario(aut.getOccupante());
	    mercatipresenzeDDAO.update(presenza);
	}
	if (rilanciaEventoPresenzaInserita) {
	    try {
		eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaInserita(presenza));
	    } catch (EventAbortedException e) {
		throw new OperazioniSubentriException(e);
	    }
	}
    }

    private String getOccupante(Anagrafe occupante) {

	return occupante.getDescrizioneRichiedente();
    }

    private String getAnagrafeOccupante(MercatipresenzeDBean presenza, boolean usaDatiConcessionario) {

	if (usaDatiConcessionario) {
	    String result = presenza.getCognomeconc();
	    if (presenza.getNomeconc() != null) {
		result += " " + presenza.getNomeconc();
	    }
	    if (presenza.getCfconc() != null) {
		result += " cf: " + presenza.getCfconc();
	    }
	    if (presenza.getPivaconc() != null) {
		result += " piva: " + presenza.getPivaconc();
	    }
	    return result;
	}
	String result = presenza.getCognomeocc();
	if (presenza.getNomeocc() != null) {
	    result += " " + presenza.getNomeocc();
	}
	if (presenza.getCfocc() != null) {
	    result += " cf: " + presenza.getCfocc();
	}
	if (presenza.getPivaocc() != null) {
	    result += " piva: " + presenza.getPivaocc();
	}
	return result;
    }

    private String getInfoPresenza(MercatipresenzeDBean presenza) {

	StringBuilder sb = new StringBuilder();
	sb.append("Presenza ").append(" del <b>").append(Utilities.formatDate(presenza.getDatagiornata(), false)).append("</b> su manifestazione <b>")
		.append(presenza.getDescrizionegiorno()).append("</b>");
	if (presenza.getCodiceposteggio() != null) {
	    sb.append(" su posteggio <b>").append(presenza.getCodiceposteggio()).append("</b>");
	} else {
	    sb.append(". La presenza è senza assegnazione posteggio");
	}
	return sb.toString();
    }

    private String testoMessaggio(Anagrafe occupante, MercatipresenzeDBean presenza, String infoPresenza, boolean usaDatiConcessionario) {

	return infoPresenza + //
	       TESTO_L_ANAGRAFE + // 
	       "<b>" + //
	       getAnagrafeOccupante(presenza, usaDatiConcessionario) + //
	       "</b>" + // 
	       TESTO_SARA_AGGIORNATA_CON + //
	       " <b>" + //
	       getOccupante(occupante) + // 
	       "</b>";
    }

    @Override
    public EsitoElaborazioneEvento checkPossoModificareOccupante(Integer idAutOConc, Integer nuovoOccupante) {

	mercatipresenzeDDAO.flush();
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutOConc));
	Anagrafe occupanteAttuale = aut.getOccupante();
	List<OperazioneEventoBean> warnings = new ArrayList<OperazioneEventoBean>();
	List<OperazioneEventoBean> errors = new ArrayList<OperazioneEventoBean>();
	List<MercatipresenzeDBean> presenze = mercatipresenzeDDAO.findPresenzePerAutorizzazioneDallaData(idAutOConc, new Date());
	for (MercatipresenzeDBean presenza : presenze) {
	    // l'autorizzazione potrebbe essere stata utilizzata o nella colonna FK_AUTORIZZAZIONI_ID o nel campo AUT_CONCESSIONARIO
	    // o in entrambe (la presenza allora è del concessionario)
	    // controllo se posizione debitoria
	    // Se non presente la posizione Debitoria o posizione non pagata allora aggiungo lo warning
	    // aggiungo l'errore
	    Integer codiceOccupanteAutAttuale = occupanteAttuale.getId().getCodice();
	    String infoPresenza = getInfoPresenza(presenza);
	    Integer idAutorizzazionePresenza = presenza.getIdautpresenza();
	    Integer idAutorizzazioneConcessionario = presenza.getIdautconcessionario();
	    boolean checkPagamento = false;
	    if (idAutOConc.equals(idAutorizzazionePresenza)) {
		// il pagamento è possibile solo se chi ha la presenza ha aperto pos deb o scalato borsellino
		checkPagamento = presenza.getIdposizionedebitoria() != null;
	    }
	    if (checkPagamento && !pagamentiService.isPresenzaConPagamentoAnnullabile(presenza.getIdpresenza())) {
		String messaggio = infoPresenza + TESTO_E_PRESENTE_UN_PAGAMENTO_IN_STATO + presenza.getStatoposdeb();
		// la posizione non è annullabile inserisco errori
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		errors.add(ope);
	    }
	    if (idAutorizzazioneConcessionario != null && presenza.getCodiceconcessionario() != null
		    && idAutorizzazioneConcessionario.equals(idAutOConc) && !codiceOccupanteAutAttuale.equals(presenza.getCodiceconcessionario())) {
		String messaggio = testoMessaggio(occupanteAttuale, presenza, infoPresenza, true);
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		warnings.add(ope);
	    }
	    if (idAutorizzazionePresenza != null && presenza.getCodiceoccupante() != null && idAutorizzazionePresenza.equals(idAutOConc)
		    && !codiceOccupanteAutAttuale.equals(presenza.getCodiceoccupante())) {
		String messaggio = testoMessaggio(occupanteAttuale, presenza, infoPresenza, false);
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		warnings.add(ope);
	    }
	}
	return new EsitoElaborazioneEvento(warnings, errors);
    }

    @Override
    public void effettuaModificaOccupanteSuPresenza(int idPresenza, Integer idAutorizzazione) throws OperazioniSubentriException {

	// recupera la presenza
	MercatipresenzeD presenza = mercatipresenzeDDAO.findById(new PkId(idPresenza));
	// recupera il subentro e l'autorizzazione
	// processa spuntista
	Integer idAutorizzazionePresenza = null;
	Integer idAutorizzazioneConcessionario = null;
	if (presenza.getAutorizzazioni() != null) {
	    idAutorizzazionePresenza = presenza.getAutorizzazioni().getId().getCodice();
	}
	if (presenza.getAutorizzazioneConcessionarioAssente() != null) {
	    idAutorizzazioneConcessionario = presenza.getAutorizzazioneConcessionarioAssente().getId().getCodice();
	}
	Integer numeroPresenze = presenza.getNumeropresenze();
	boolean rilanciaEventoPresenzaInserita = false;
	if (idAutorizzazione.equals(idAutorizzazionePresenza)) {
	    presenza.setAutorizzazioni(null);
	    presenza.setOccupante(null);
	    presenza.setNumeropresenze(0);
	    mercatipresenzeDDAO.update(presenza);
	    boolean presenteSpuntista = presenza.isSpuntista();
	    Autorizzazioni aut = mercatipresenzeDDAO.getById(Autorizzazioni.class, idAutorizzazionePresenza);
	    //	// annullo anagrafica o le anagrafiche
	    //	EventoPresenzaRevocata	    
	    if (presenza.getPosteggio() != null) {
		try {
		    eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaRevocata(idPresenza, aut, presenteSpuntista));
		    rilanciaEventoPresenzaInserita = true;
		} catch (EventAbortedException e) {
		    throw new OperazioniSubentriException(e);
		}
	    }
	    //	// assegna anagrafica o le anagrafiche
	    //	EventoPresenzaInserita
	    presenza.setAutorizzazioni(aut);
	    presenza.setOccupante(aut.getOccupante());
	    presenza.setNumeropresenze(numeroPresenze);
	    mercatipresenzeDDAO.update(presenza);
	}
	if (idAutorizzazione.equals(idAutorizzazioneConcessionario)) {
	    Autorizzazioni aut = mercatipresenzeDDAO.getById(Autorizzazioni.class, idAutorizzazioneConcessionario);
	    presenza.setAutorizzazioneConcessionarioAssente(aut);
	    presenza.setConcessionario(aut.getOccupante());
	    mercatipresenzeDDAO.update(presenza);
	}
	if (rilanciaEventoPresenzaInserita) {
	    try {
		eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaInserita(presenza));
	    } catch (EventAbortedException e) {
		throw new OperazioniSubentriException(e);
	    }
	}
    }

    @Override
    public EsitoElaborazioneEvento checkPossoModificareDataCessazioneSubentro(Integer idSubentroDaModificare, Date nuovaDataCessazione) {

	mercatipresenzeDDAO.flush();
	AutorizzazioniSubentri autSub = autorizzazioniSubentriService.findById(new PkId(idSubentroDaModificare));
	int compareDates = Utilities.compareDates(autSub.getDataCessazione(), nuovaDataCessazione);
	List<OperazioneEventoBean> warnings = new ArrayList<OperazioneEventoBean>();
	List<OperazioneEventoBean> errors = new ArrayList<OperazioneEventoBean>();
	validazionePreliminareDatacessazione(autSub, compareDates, nuovaDataCessazione, errors);
	Anagrafe occupanteAttuale = autSub.getAutorizzazioni().getOccupante();
	Anagrafe occupanteSubentro = autSub.getOccupante();
	Integer idAutOConc = autSub.getAutorizzazioni().getId().getCodice();
	//	se nuova data da sostituire > vecchia data 
	//	prendo presenze a partire da vecchia data
	//	calcolo se possibile farlo con logica dei subentri
	//	se nuova data < vecchia data
	//	prendo presenze a partire da nuova data
	//	calcolo se possibile farlo con logica dei subentri
	boolean isNuovaDataMaggioreDataSubentro = Utilities.isDateGreater(nuovaDataCessazione, autSub.getDataCessazione());
	List<MercatipresenzeDBean> presenze = getPresenzePerModificaDataCessazione(nuovaDataCessazione, autSub, idAutOConc,
		isNuovaDataMaggioreDataSubentro);
	Anagrafe nuovoOccupante = occupanteAttuale;
	// ### B) Devo quindi modificare le presenze del subentrante che saranno assegnate all'attuale occupante dell'autorizzazione 
	// a partire dalla data di cessazione orignaria alla nuova data di cessazione
	// all'attuale occupante dell'autorizzazione. 
	if (isNuovaDataMaggioreDataSubentro) {
	    // ### A) Le presenze saranno assegnate dalla data di cessazione orignaria alla nuova data di cessazione
	    // all'occupante del subentro dell'autorizzazione. 
	    nuovoOccupante = occupanteSubentro;
	}
	for (MercatipresenzeDBean presenza : presenze) {
	    Date dataGiornata = presenza.getDatagiornata();
	    // la data giornata da valutare è quella >= nuova dataCessazione e <= vecchiaDataCessazione
	    // la nuova data cessazione non PUO' mai essere maggiore della vecchia data di cessazione (vedi controlli di validazione)
	    if ((Utilities.compareDates(dataGiornata, nuovaDataCessazione) >= 0)
		    && (Utilities.compareDates(dataGiornata, autSub.getDataCessazione()) < 0)) {
		// l'autorizzazione potrebbe essere stata utilizzata o nella colonna FK_AUTORIZZAZIONI_ID o nel campo AUT_CONCESSIONARIO
		// o in entrambe (la presenza allora è del concessionario)
		// controllo posizione debitoria
		// Se non presente o la posizione Debitoria o posizione risulta annullabile (non pagata) allora aggiungo lo warning
		// altrimenti aggiungo l'errore
		String infoPresenza = getInfoPresenza(presenza);
		Integer idAutorizzazionePresenza = presenza.getIdautpresenza();
		Integer idAutorizzazioneConcessionario = presenza.getIdautconcessionario();
		boolean checkPagamento = false;
		if (idAutOConc.equals(idAutorizzazionePresenza)) {
		    // il pagamento è possibile solo se chi ha la presenza ha aperto pos deb o scalato borsellino
		    checkPagamento = presenza.getIdposizionedebitoria() != null;
		}
		if (checkPagamento && !pagamentiService.isPresenzaConPagamentoAnnullabile(presenza.getIdpresenza())) {
		    String messaggio = infoPresenza + TESTO_E_PRESENTE_UN_PAGAMENTO_IN_STATO + presenza.getStatoposdeb();
		    // la posizione non è annullabile inserisco errori
		    OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		    errors.add(ope);
		}
		boolean giaElaborato = false;
		if (idAutorizzazioneConcessionario != null && presenza.getCodiceconcessionario() != null
			&& idAutorizzazioneConcessionario.equals(idAutOConc)
			&& !nuovoOccupante.getId().getCodice().equals(presenza.getCodiceconcessionario())) {
		    String messaggio = testoMessaggio(nuovoOccupante, presenza, infoPresenza, true);
		    OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		    warnings.add(ope);
		    giaElaborato = true;
		}
		if (idAutorizzazionePresenza != null && presenza.getCodiceoccupante() != null && idAutorizzazionePresenza.equals(idAutOConc)
			&& !nuovoOccupante.getId().getCodice().equals(presenza.getCodiceoccupante()) && !giaElaborato) {
		    String messaggio = testoMessaggio(nuovoOccupante, presenza, infoPresenza, false);
		    OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		    warnings.add(ope);
		}
	    }
	}
	return new EsitoElaborazioneEvento(warnings, errors);
    }

    /**
     * <pre>
     * 
     * se isNuovaDataMaggioreDataSubentro
     *  La nuova data di cessazione è maggiore della vecchia. 
     *  ### A) Le presenze saranno assegnate dalla data di
     * cessazione orignaria alla nuova data di cessazione all'occupante del subentro dell'autorizzazione. 
     * In questo caso devo quindi valutare le presenze da data precedente cessazione a nuova data cessazione
     * 
     * altrimenti
     * 
     *  La nuova data di cessazione è minore della vecchia. 
     *  ### B) Devo quindi modificare le presenze del subentrante che saranno assegnate all'attuale occupante dell'autorizzazione 
     *  a partire dalla data di cessazione orignaria alla nuova data di cessazione
     *  all'attuale occupante dell'autorizzazione. 
     *  In questo caso devo quindi valutare le presenze da nuova data cessazione a data precedente cessazione
     * </pre>
     * 
     * @param nuovaDataCessazione
     * @param autSub
     * @param idAutOConc
     * @param isNuovaDataMaggioreDataSubentro
     * @return
     */
    private List<MercatipresenzeDBean> getPresenzePerModificaDataCessazione(Date nuovaDataCessazione, AutorizzazioniSubentri autSub,
	    Integer idAutOConc, boolean isNuovaDataMaggioreDataSubentro) {

	if (isNuovaDataMaggioreDataSubentro) {
	    // La nuova data di cessazione è maggiore della vecchia. 
	    // ### A) Le presenze saranno assegnate dalla data di cessazione orignaria alla nuova data di cessazione
	    // all'occupante del subentro dell'autorizzazione. 
	    // In questo caso devo quindi valutare le presenze da data precedente cessazione a nuova data cessazione
	    return mercatipresenzeDDAO.findPresenzePerAutorizzazioneDallaData(idAutOConc, autSub.getDataCessazione());
	}
	// La nuova data di cessazione è minore della vecchia. 
	// ### B) Devo quindi modificare le presenze del subentrante che saranno assegnate all'attuale occupante dell'autorizzazione 
	// a partire dalla data di cessazione orignaria alla nuova data di cessazione
	// all'attuale occupante dell'autorizzazione. 
	// In questo caso devo quindi valutare le presenze da nuova data cessazione a data precedente cessazione 
	return mercatipresenzeDDAO.findPresenzePerAutorizzazioneDallaData(idAutOConc, nuovaDataCessazione);
    }

    private void validazionePreliminareDatacessazione(AutorizzazioniSubentri autSub, int compareDates, Date nuovaDataCessazione,
	    List<OperazioneEventoBean> errors) {

	//	non posso modificare data cessazione con data maggiore della data cessazione autorizzazione attuale se cessata
	if (autSub.getAutorizzazioni().getDataCessazione() != null
		&& Utilities.isDateGreater(nuovaDataCessazione, autSub.getAutorizzazioni().getDataCessazione())) {
	    errors.add(new OperazioneEventoBean("-1",
		    "Non è possibile impostare la nuova data di cessazione <b>" + Utilities.formatDate(nuovaDataCessazione, false) +
						      "</b> che risulta uguale o superiore alla data di cessazione dell'atto <b>" +
						      Utilities.formatDate(autSub.getAutorizzazioni().getDataCessazione(), false) + "</b>",
		    CHIAMANTE.MERCATIPRESENZE_D));
	}
	if (compareDates < 0) {
	    OperazioneEventoBean ope = new OperazioneEventoBean("",
		    "Non è possibile modificare la data di un subentro con data maggiore alla data dell'ultimo subentro",
		    CHIAMANTE.MERCATIPRESENZE_D);
	    errors.add(ope);
	}
	//	non posso modificare data cessazione con data uguale alla vecchia data cessazione		
	if (compareDates == 0) {
	    errors.add(new OperazioneEventoBean("-1", "Non è possibile modificare la data di cessazione con data uguale alla vecchia data cessazione",
		    CHIAMANTE.MERCATIPRESENZE_D));
	}
	Date dataOdierna = Calendar.getInstance().getTime();
	boolean compareDatesOggi = Utilities.isDateGreater(nuovaDataCessazione, dataOdierna);
	//	non posso modificare data cessazione con data maggiore della data attuale
	if (compareDatesOggi) {
	    errors.add(new OperazioneEventoBean("-1",
		    "Non è possibile modificare la data cessazione del subentro con data maggiore della data attuale", CHIAMANTE.MERCATIPRESENZE_D));
	}
	//	non posso modificare data cessazione subentro con data antecedente alla data di cessazione del subentro che precede quello che sto modificando.
	List<AutorizzazioniSubentri> subentris = autorizzazioniSubentriService.findByAutorizzazione(autSub.getAutorizzazioni().getId().getCodice(),
		null, null);
	// sono ordinati per data cessazione;
	boolean takeNext = false;
	Date dataCessazioneSubentroPrecedente = null;
	for (AutorizzazioniSubentri sub : subentris) {
	    Integer autsubel = sub.getId().getCodice();
	    if (takeNext) {
		dataCessazioneSubentroPrecedente = sub.getDataCessazione();
		break;
	    }
	    if (autsubel.equals(autSub.getId().getCodice())) {
		takeNext = true;
	    }
	}
	if (dataCessazioneSubentroPrecedente != null) {
	    boolean compareDatesprecedente = Utilities.isDateGreater(dataCessazioneSubentroPrecedente, nuovaDataCessazione);
	    if (compareDatesprecedente) {
		errors.add(new OperazioneEventoBean("-1",
			"Non è possibile modificare la data cessazione del subentro con data antecedente al subentro che precede quello che sto modificando",
			CHIAMANTE.MERCATIPRESENZE_D));
	    }
	}
    }

    @Override
    public void effettuaModificaDataCessazioneSubentroSuPresenza(Integer idPresenza, Integer idAutorizzazioniSubentri, Date vecchiaDataCessazione)
	    throws OperazioniSubentriException {

	MercatipresenzeD presenza = mercatipresenzeDDAO.findById(new PkId(idPresenza));
	// recupera il subentro e l'autorizzazione
	AutorizzazioniSubentri sub = mercatipresenzeDDAO.getById(AutorizzazioniSubentri.class, idAutorizzazioniSubentri);
	Date nuovaDataCessazione = sub.getDataCessazione();
	Anagrafe occupanteAttuale = sub.getAutorizzazioni().getOccupante();
	Anagrafe occupanteSubentro = sub.getOccupante();
	// ### B) Devo quindi modificare le presenze del subentrante che saranno assegnate all'attuale occupante dell'autorizzazione 
	// a partire dalla data di cessazione orignaria alla nuova data di cessazione
	// all'attuale occupante dell'autorizzazione. 
	Anagrafe nuovoOccupante = occupanteAttuale;
	boolean isNuovaDataMaggioreDataSubentro = Utilities.isDateGreater(nuovaDataCessazione, vecchiaDataCessazione);
	if (isNuovaDataMaggioreDataSubentro) {
	    // ### A) Le presenze saranno assegnate dalla data di cessazione orignaria alla nuova data di cessazione
	    // all'occupante del subentro dell'autorizzazione. 
	    nuovoOccupante = occupanteSubentro;
	}
	Integer idAutorizzazionePresenza = null;
	Integer idAutorizzazioneConcessionario = null;
	if (presenza.getAutorizzazioni() != null) {
	    idAutorizzazionePresenza = presenza.getAutorizzazioni().getId().getCodice();
	}
	if (presenza.getAutorizzazioneConcessionarioAssente() != null) {
	    idAutorizzazioneConcessionario = presenza.getAutorizzazioneConcessionarioAssente().getId().getCodice();
	}
	Integer numeroPresenze = presenza.getNumeropresenze();
	boolean rilanciaEventoPresenzaInserita = false;
	Integer idAutorizzazione = sub.getAutorizzazioni().getId().getCodice();
	if (idAutorizzazione.equals(idAutorizzazionePresenza)) {
	    presenza.setAutorizzazioni(null);
	    presenza.setOccupante(null);
	    presenza.setNumeropresenze(0);
	    mercatipresenzeDDAO.update(presenza);
	    boolean presenteSpuntista = presenza.isSpuntista();
	    Autorizzazioni aut = mercatipresenzeDDAO.getById(Autorizzazioni.class, idAutorizzazionePresenza);
	    //	// annullo anagrafica o le anagrafiche
	    //	EventoPresenzaRevocata	    
	    if (presenza.getPosteggio() != null) {
		try {
		    eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaRevocata(idPresenza, aut, presenteSpuntista));
		    rilanciaEventoPresenzaInserita = true;
		} catch (EventAbortedException e) {
		    throw new OperazioniSubentriException(e);
		}
	    }
	    //	// assegna anagrafica o le anagrafiche
	    //	EventoPresenzaInserita
	    presenza.setAutorizzazioni(aut);
	    presenza.setOccupante(nuovoOccupante);
	    presenza.setNumeropresenze(numeroPresenze);
	    mercatipresenzeDDAO.update(presenza);
	}
	if (idAutorizzazione.equals(idAutorizzazioneConcessionario)) {
	    Autorizzazioni aut = mercatipresenzeDDAO.getById(Autorizzazioni.class, idAutorizzazioneConcessionario);
	    presenza.setAutorizzazioneConcessionarioAssente(aut);
	    presenza.setConcessionario(nuovoOccupante);
	    mercatipresenzeDDAO.update(presenza);
	}
	if (rilanciaEventoPresenzaInserita) {
	    try {
		eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaInserita(presenza));
	    } catch (EventAbortedException e) {
		throw new OperazioniSubentriException(e);
	    }
	}
    }

    @Override
    public EsitoElaborazioneEvento checkPossoEliminareUltimoPassaggioSubentriAutConc(Integer idSubentroDaRipristinare) {

	mercatipresenzeDDAO.flush();
	List<OperazioneEventoBean> warnings = new ArrayList<OperazioneEventoBean>();
	List<OperazioneEventoBean> errors = new ArrayList<OperazioneEventoBean>();
	AutorizzazioniSubentri autSub = autorizzazioniSubentriService.findById(new PkId(idSubentroDaRipristinare));
	Integer idAutConcSubentro = autSub.getAutorizzazioni().getId().getCodice();
	List<MercatipresenzeDBean> presenze = mercatipresenzeDDAO.findPresenzePerAutorizzazioneDallaData(idAutConcSubentro,
		autSub.getDataCessazione());
	Integer codiceOccupanteAutAttuale = autSub.getOccupante().getId().getCodice();
	Anagrafe occupante = mercatipresenzeDDAO.getById(Anagrafe.class, codiceOccupanteAutAttuale);
	for (MercatipresenzeDBean presenza : presenze) {
	    String infoPresenza = getInfoPresenza(presenza);
	    Integer idAutorizzazionePresenza = presenza.getIdautpresenza();
	    Integer idAutorizzazioneConcessionario = presenza.getIdautconcessionario();
	    boolean checkPagamento = false;
	    if (idAutConcSubentro.equals(idAutorizzazionePresenza)) {
		// il pagamento è possibile solo se chi ha la presenza ha aperto pos deb o scalato borsellino
		checkPagamento = presenza.getIdposizionedebitoria() != null;
	    }
	    if (checkPagamento && !pagamentiService.isPresenzaConPagamentoAnnullabile(presenza.getIdpresenza())) {
		String messaggio = infoPresenza + TESTO_E_PRESENTE_UN_PAGAMENTO_IN_STATO + presenza.getStatoposdeb();
		// la posizione non è annullabile inserisco errori
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		errors.add(ope);
	    }
	    boolean giaElaborato = false;
	    if (idAutorizzazioneConcessionario != null && idAutorizzazioneConcessionario.equals(idAutorizzazionePresenza) // solo se presente
		    && codiceOccupanteAutAttuale.equals(presenza.getCodiceoccupante()) // se chi ha preso la presenza
		    && !codiceOccupanteAutAttuale.equals(autSub.getOccupante().getId().getCodice())) { // sull'ultimo passaggio il codice anagrafe potrebbe essere lo stesso allora posso fare la modifica
		String messaggio = testoMessaggio(occupante, presenza, infoPresenza, true);
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		warnings.add(ope);
		giaElaborato = true;
	    }
	    if (idAutorizzazioneConcessionario != null && presenza.getCodiceconcessionario() != null
		    && idAutorizzazioneConcessionario.equals(idAutConcSubentro)
		    && !codiceOccupanteAutAttuale.equals(presenza.getCodiceconcessionario()) && !giaElaborato) {
		String messaggio = testoMessaggio(occupante, presenza, infoPresenza, true);
		OperazioneEventoBean ope = new OperazioneEventoBean(presenza.getIdpresenza().toString(), messaggio, CHIAMANTE.MERCATIPRESENZE_D);
		warnings.add(ope);
	    }
	}
	return new EsitoElaborazioneEvento(warnings, errors);
    }

    @Override
    public void effettuaOperazioniCancellazioneAutConcSuPresenza(Integer idPresenza, Integer idAutorizzazione)
	    throws OperazioneCancellazioneAutConcException {

	MercatipresenzeD presenza = mercatipresenzeDDAO.findById(new PkId(idPresenza));
	// recupera il subentro e l'autorizzazione
	Autorizzazioni aut = mercatipresenzeDDAO.getById(Autorizzazioni.class, idAutorizzazione);
	Anagrafe occupanteAttuale = aut.getOccupante();
	// ### B) Devo quindi modificare le presenze del subentrante che saranno assegnate all'attuale occupante dell'autorizzazione 
	// a partire dalla data di cessazione orignaria alla nuova data di cessazione
	// all'attuale occupante dell'autorizzazione. 	
	Integer idAutorizzazionePresenza = null;
	Integer idAutorizzazioneConcessionario = null;
	if (presenza.getAutorizzazioni() != null) {
	    idAutorizzazionePresenza = presenza.getAutorizzazioni().getId().getCodice();
	}
	if (presenza.getAutorizzazioneConcessionarioAssente() != null) {
	    idAutorizzazioneConcessionario = presenza.getAutorizzazioneConcessionarioAssente().getId().getCodice();
	}
	Integer numeroPresenze = presenza.getNumeropresenze();
	boolean rilanciaEventoPresenzaInserita = false;
	if (idAutorizzazione.equals(idAutorizzazionePresenza)) {
	    presenza.setAutorizzazioni(null);
	    presenza.setOccupante(null);
	    presenza.setNumeropresenze(0);
	    mercatipresenzeDDAO.update(presenza);
	    boolean presenteSpuntista = presenza.isSpuntista();
	    //  annullo anagrafica o le anagrafiche
	    //	EventoPresenzaRevocata	    
	    if (presenza.getPosteggio() != null) {
		try {
		    eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaRevocata(idPresenza, aut, presenteSpuntista));
		    rilanciaEventoPresenzaInserita = true;
		} catch (EventAbortedException e) {
		    throw new OperazioneCancellazioneAutConcException(e);
		}
	    }
	    //	// assegna anagrafica o le anagrafiche
	    //	EventoPresenzaInserita
	    presenza.setAutorizzazioni(aut);
	    presenza.setOccupante(occupanteAttuale);
	    presenza.setNumeropresenze(numeroPresenze);
	    mercatipresenzeDDAO.update(presenza);
	}
	if (idAutorizzazione.equals(idAutorizzazioneConcessionario)) {
	    presenza.setAutorizzazioneConcessionarioAssente(aut);
	    // presenza.setOccupante(aut.getOccupante()); // LO FACCIO SOPRA
	    presenza.setConcessionario(occupanteAttuale);
	    mercatipresenzeDDAO.update(presenza);
	}
	if (rilanciaEventoPresenzaInserita) {
	    try {
		eventPublisher.publishAndThrowOnAllSubscriberFailure(new EventoPresenzaInserita(presenza));
	    } catch (EventAbortedException e) {
		throw new OperazioneCancellazioneAutConcException(e);
	    }
	}
    }
}