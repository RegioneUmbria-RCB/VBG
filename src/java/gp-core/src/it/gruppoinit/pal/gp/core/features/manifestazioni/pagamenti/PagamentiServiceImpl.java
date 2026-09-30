package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoTabellaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoAutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AbbonamentoConfigModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.DestinatariEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi.EventoMovimentoInserito;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.CreditoBorsellinoInsufficienteException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.BorsellinoMovimentiFactory;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.BorsellinoMovimentiHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.IBorsellinoMovimentiDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.IVerticalizzazioneAbbonamentoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.EventoPagamentoStornato;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.EventoPosizioneDebitoriaPerCreditoInsufficiente;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;

@Service
public class PagamentiServiceImpl implements IPagamentiService {

    private static final Logger logger = LoggerFactory.getLogger(PagamentiServiceImpl.class);
    private IBorsellinoAutorizzazioniDAO borsellinoAutDAO;
    private IBorsellinoDAO borsellinoDAO;
    private IBorsellinoMovimentiDAO borsellinoMovimentiDAO;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;
    private ContiService contiService;
    private MailtipoService mailTipoService;
    private AmministrazioniService amministrazioniService;
    private MercatipresenzeDService mercatipresenzeDService;
    private NodoPagamentiService nodoPagamentiService;
    private VerticalizzazioniService verticalizzazioniService;
    private IEventPublisher publisher;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private IAbbonamentoService abbonamentoService;
    private IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService;

    @Autowired
    public void setRecuperaInformazioniGiornataService(IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService) {

	this.recuperaInformazioniGiornataService = recuperaInformazioniGiornataService;
    }

    @Autowired
    public void setBorsellinoAutDAO(IBorsellinoAutorizzazioniDAO borsellinoAutDAO) {

	this.borsellinoAutDAO = borsellinoAutDAO;
    }

    @Autowired
    public void setBorsellinoDAO(IBorsellinoDAO borsellinoDAO) {

	this.borsellinoDAO = borsellinoDAO;
    }

    @Autowired
    public void setBorsellinoMovimentiDAO(IBorsellinoMovimentiDAO borsellinoMovimentiDAO) {

	this.borsellinoMovimentiDAO = borsellinoMovimentiDAO;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @Autowired
    public void setMailTipoService(MailtipoService mailTipoService) {

	this.mailTipoService = mailTipoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setNodoPagamentiService(NodoPagamentiService nodoPagamentiService) {

	this.nodoPagamentiService = nodoPagamentiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setPublisher(IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Autowired
    public void setDettPosizioneDebitoriaService(DettPosizioneDebitoriaService dettPosizioneDebitoriaService) {

	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
    }
    
    @Autowired
    public void setAbbonamentoService(IAbbonamentoService abbonamentoService) {
    
        this.abbonamentoService = abbonamentoService;
    }

    private Integer findMovimentoDaStornare(Integer idPosteggio, Integer idMercatiPresenzeT, Integer idAutorizzazione) {

	//1. Tramite idPosteggio, idMercatiPresenzeT e idAutorizzazione risalgo al movimento
	return this.borsellinoMovimentiDAO.findIdNonStornatoByRiferimenti(idPosteggio, idMercatiPresenzeT, idAutorizzazione);
    }

    @Override
    public void stornaPresenza(MercatipresenzeD presenza) {

	this.stornaPresenza(presenza, presenza.getAutorizzazioni());
    }

    @Override
    public void stornaPresenza(MercatipresenzeD presenza, Autorizzazioni autorizzazione) {

	//1. Verifica dei parametri passati
	if (presenza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare la gestione dei pagamenti in merito allo storno senza passare il riferimento alla giornata");
	}
	if (autorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare la gestione dei pagamenti in merito allo storno non potendo identificae l'autorizzazione dalla giornata");
	}
	if (presenza.getPosteggio().getMercati().getComune() == null
		|| StringUtils.isBlank(presenza.getPosteggio().getMercati().getComune().getCodicecomune())) {
	    throw new IllegalArgumentException("La manifestazione " + presenza.getPosteggio().getMercati().getDescrizione() +
					       " non è associata a nessun comune. Correggere la configurazione e riprovare");
	}
	if (!gestiscePagamenti(presenza.getMercatiPresenzeT().getMercato())) {
	    return;
	}
	//2. Verifico la presenza di un borsellino o di una posizione debitoria
	IVerticalizzazioneAbbonamentoPosteggiService vert = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService,
		presenza.getPosteggio().getMercati().getComune().getCodicecomune());
	boolean borsellinoAttivo = this.isBorsellinoAttivoAllaData(autorizzazione.getId().getCodice(), presenza.isSpuntista(),
		presenza.getMercatiPresenzeT().getDataRegistrazione(), vert);
	Integer idMovimentoDaStornare = this.findMovimentoDaStornare(presenza.getPosteggio().getId().getCodice(),
		presenza.getMercatiPresenzeT().getId().getCodice(), autorizzazione.getId().getCodice());
	boolean posizioneDebitoriaPresente = this.posizioneDebitoriaPresente(presenza);
	if (!borsellinoAttivo && !posizioneDebitoriaPresente && idMovimentoDaStornare == null) {
	    return;
	}
	//3. Tento l'annullamento della posizione debitoria
	boolean stornato = false;
	if (posizioneDebitoriaPresente) {
	    if (!this.nodoPagamentiAttivo(presenza.getPosteggio().getMercati())) {
		throw new InvalidConfigurationException(
			"Non è possibile tentare l'annullamento della posizione debitoria, in quanto non è attivo il nodo pagamenti");
	    }
	    //3.1 Annullo la posizione debitoria
	    try {
		DettPosizioneDebitoria dett = dettPosizioneDebitoriaService
			.findById(new PkId(presenza.getDettPosizioneDebitoria().getId().getCodice()));
		StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
		if (!c.isStatoAnnullato(dett.getStato())) {
		    // annullo solo se non annullato
		    this.nodoPagamentiService.annullaPosizioneDebitoria(presenza.getDettPosizioneDebitoria().getId().getCodice());
		}
		presenza.setDettPosizioneDebitoria(null);
		this.mercatipresenzeDService.update(presenza);
	    } catch (FunzioneBusinessRemotaException ex) {
		logger.error("Errore nell'eliminazione della presenza dell'occupante per la giornata con id {}. Dettaglio errore: {}",
			presenza.getId().getCodice(), ex);
		throw new RuntimeException("Non è possibile annullare la posizione debitoria con DETT_POSIZIONE_DEBITORIA.ID " +
					   presenza.getDettPosizioneDebitoria().getId().getCodice() + " per il seguente motivo " + ex.getMessage());
	    }
	    stornato = true;
	}
	boolean isActiveWalletForMarket = presenza.getPosteggio().getMercati().getActiveWalletMarket();
	if (isActiveWalletForMarket) {
	    //4. Tento lo storno dal borsellino
	    if (idMovimentoDaStornare != null) {
		this.stornaMovimento(idMovimentoDaStornare);
		stornato = true;
	    }
	}
	if (stornato) {
	    this.publisher.publish(new EventoPagamentoStornato(presenza.getId().getCodice()));// ==> sottoscrittore per eliminare le massive con tipologia CREDITO INS
	}
    }

    @Override
    public void generaPagamentoPresenza(MercatipresenzeD presenza) throws CreditoBorsellinoInsufficienteException {

	//1. Verifiche sui parametri passati
	if (presenza == null) {
	    throw new IllegalArgumentException("Impossibile generare un pagamento per la presenza senza passare la giornata di riferimento");
	}
	if (presenza.getAutorizzazioni() == null || presenza.getAutorizzazioni().getId() == null
		|| presenza.getAutorizzazioni().getId().getCodice() == null) {
	    throw new IllegalArgumentException("Impossibile risalire all'autorizzazione coinvolta");
	}
	if (presenza.getPosteggio().getMercati().getComune() == null
		|| StringUtils.isBlank(presenza.getPosteggio().getMercati().getComune().getCodicecomune())) {
	    throw new IllegalArgumentException("La manifestazione " + presenza.getPosteggio().getMercati().getDescrizione() +
					       " non è associata a nessun comune. Correggere la configurazione e riprovare");
	}
	if (!gestiscePagamenti(presenza.getMercatiPresenzeT().getMercato())) {
	    return;
	}
	Integer idAutorizzazione = presenza.getAutorizzazioni().getId().getCodice();
	//2. Verifico la presenza di un borsellino o della possibilità di aprire una posizione debitoria
	boolean nodoPagamentiAttivo = this.nodoPagamentiAttivo(presenza.getPosteggio().getMercati());
	// se la funzionalità è attiva e vert.isBloccaAssegnazioni() allora se non attivo borsellino lancio eccezione
	IVerticalizzazioneAbbonamentoPosteggiService vert = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService,
		presenza.getPosteggio().getMercati().getComune().getCodicecomune());
	boolean isRegolaBorsellinoAttivo = vert.isAttiva();
	if (!isRegolaBorsellinoAttivo && !nodoPagamentiAttivo) {
	    return;
	}
	//3. Calcolo il costo del posteggio
	PosteggioInfoRestBean costo = this.mercatipresenzeDService.calcolaCostoPosteggio(presenza.getId().getCodice());
	if (costo.getImporto() == null) {
	    return;
	}
	//4. Verifico se scalare l'importo dal borsellino
	boolean borsellinoAttivo = this.isBorsellinoAttivoAllaData(idAutorizzazione, //
		presenza.isSpuntista(), //
		presenza.getMercatiPresenzeT().getDataRegistrazione(), vert); //
	boolean bloccaAssegnazioni = vert.isBloccaAssegnazioni();
	boolean siEVerificatoILCreditoInsufficiente = false;
	boolean isActiveWalletForMarket = presenza.getPosteggio().getMercati().getActiveWalletMarket();

	
	AbbonamentoConfigModel findAbbonamentoConfig = abbonamentoService.findAbbonamentoConfig();
	boolean isAllowedWallet = false;
	if (findAbbonamentoConfig.getDestinatari() == null || DestinatariEnum.TUTTI.name().equals(findAbbonamentoConfig.getDestinatari())) {
	    isAllowedWallet = true;
	} else if (DestinatariEnum.CONCESSIONARI.name().equals(findAbbonamentoConfig.getDestinatari())) {
	    String idAttivita = null;
	    if (presenza.getAttivita() != null && presenza.getAttivita().getId() != null) {
		idAttivita = presenza.getAttivita().getId().getCodiceistat();
	    }
	    if (!presenza.isSpuntista()
		    || (presenza.isSpuntista() && recuperaInformazioniGiornataService.isBattitore(StringUtils.defaultString(idAttivita, "N0nTro")))) {
		isAllowedWallet = true;
	    }
	} else if (DestinatariEnum.SPUNTISTI.name().equals(findAbbonamentoConfig.getDestinatari())) {
	    if (presenza.isSpuntista()) {
		isAllowedWallet = true;
	    }
	} else {
	    throw new RuntimeException(
		    "Il valore attivapagamenti trovato in AbbonamentoConfig non risulta valido: " + findAbbonamentoConfig.getDestinatari());
	}
	
	//TODO verificare logica 
	if (isAllowedWallet && isActiveWalletForMarket) {
	    if (borsellinoAttivo) {
		//4.1 Scalo dal borsellino
		try {
		    this.scalaGiornataDaBorsellino(presenza, costo.getImportoHelper());
		    return;
		} catch (CreditoBorsellinoInsufficienteException cbie) {
		    siEVerificatoILCreditoInsufficiente = true;
		    //4.3 Verifico se, in caso di mancato credito, posso utilizzare le posizioni debitorie 		
		    if (bloccaAssegnazioni) {
			throw cbie;
		    }
		}
	    } else {
		if (isRegolaBorsellinoAttivo && bloccaAssegnazioni && (presenza.isSpuntista()
			|| verificaDataPosDebConcessionariAllaData(presenza.getMercatiPresenzeT().getDataRegistrazione()))) {
		    // è attiva la funzionalità borsellino attivo e l'utente non ha un borsellino attivo
		    // se la verticalizzazione mi dice di bloccare le assegnazioni allora rilancio
		    // la presenza è di un concessionario e devo controllare se
		    //3. L'autorizzazione potrebbe essere del concessionario ma configurato di non utilizzare il borsellino per i concessionari
		    throw new CreditoBorsellinoInsufficienteException("Non è presente un abbonamento per l'autorizzazione scelta");
		}
	    }
	}
	//5. Non posso scalare da borsellino dopo i vari controlli, per cui tento l'apertura della posizione debitoria
	if (nodoPagamentiAttivo) {
	    try {
		Integer dettPosizioneDebitoriaId = this.nodoPagamentiService
			.registraPosizioneDebitoriaDaPresenzaSuMercato(presenza.getId().getCodice());
		presenza.setDettPosizioneDebitoria(this.dettPosizioneDebitoriaService.findById(new PkId(dettPosizioneDebitoriaId)));
		this.mercatipresenzeDService.update(presenza);
		if (siEVerificatoILCreditoInsufficiente) {
		    // EventoPosizioneDebitoriaPerCreditoInsufficiente ==> presenza asincrono
		    this.publisher.publish(new EventoPosizioneDebitoriaPerCreditoInsufficiente(presenza.getId().getCodice()));
		}
	    } catch (FunzioneBusinessRemotaException fbre) {
		throw new RuntimeException(fbre);
	    }
	}
    }

    private void scalaGiornataDaBorsellino(MercatipresenzeD giornata, PosteggioImportoHelper importoHelper)
	    throws CreditoBorsellinoInsufficienteException {

	if (giornata == null) {
	    throw new IllegalArgumentException("Impossibile scalare la giornata dal borsellino senza passare la giornata di riferimento");
	}
	Integer idAutorizzazione = giornata.getAutorizzazioni().getId().getCodice();
	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException("Impossibile risalire al borsellino senza indicare l'autorizzazione coinvolta");
	}
	if (importoHelper == null || importoHelper.getListaImporti() == null) {
	    throw new IllegalArgumentException("Impossibile scalare un importo dal borsellino senza indicare l'importo e la sua ripartizione");
	}
	Integer idBorsellino = this.borsellinoAutDAO.findBorsellinoAttivo(idAutorizzazione);
	if (idBorsellino == null) {
	    throw new IllegalArgumentException(
		    "Impossibile scalare l'importo dal borsellino in quanto non ci sono borsellini attivi legati all'autorizzazione passata");
	}
	BigDecimal residuo = this.borsellinoAutDAO.proiezione(idBorsellino, importoHelper.getImporto());
	if (residuo.compareTo(BigDecimal.ZERO) < 0) {
	    throw new CreditoBorsellinoInsufficienteException("Impossibile scalare l'importo dal borsellino: credito insufficiente");
	}
	// dopo la proiezione verifico che le posizioni debitorie siano in stato pagate altrimenti il credito non sarebbe valido
	// le posizioni debitorie dovrebbero riferirsi al comune/cf_ente_creditore 
	// (meglio perché potrei associare una posizione debitoria per comuni che hanno lo stesso CF_ENTE) per le quali sono state pagate	
	// 1. Aggiungo il movimento e il suo dettaglio
	BorsellinoMovimentiHelper helper = new BorsellinoMovimentiHelper();
	helper.setGiornata(giornata);
	helper.setIdBorsellino(idBorsellino);
	helper.setImporti(importoHelper.getListaImporti());
	helper.setImporto(importoHelper.getImporto());
	BorsellinoMovimentiFactory factory = new BorsellinoMovimentiFactory(this.contiService, this.borsellinoDAO);
	BorsellinoMovimenti newmovimento = factory.buildUscita(helper);
	this.borsellinoMovimentiDAO.insert(newmovimento);
	
	try {
	    publisher.publishThrowOnFailure(new EventoMovimentoInserito(idBorsellino, newmovimento.getImporto(), newmovimento.getCreditoFinale()));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
    }

    private boolean isBorsellinoAttivoAllaData(Integer idAutorizzazione, boolean spuntista, Date dataGiornata,
	    IVerticalizzazioneAbbonamentoPosteggiService vert) {

	//1. Verifico se la verticalizzazione è attiva	
	if (!vert.isAttiva()) {
	    return false;
	}
	//2. Verifico se autorizzazione collegata a borsellino attivo
	boolean attivo = this.borsellinoAutDAO.isBorsellinoAttivo(idAutorizzazione);
	if (!attivo) {
	    return false;
	}
	if (!spuntista) {
	    // la presenza è di un concessionario e devo controllare se
	    //3. L'autorizzazione potrebbe essere del concessionario ma configurato di non utilizzare il borsellino per i concessionari
	    return verificaDataPosDebConcessionariAllaData(dataGiornata);
	}
	return attivo;
    }

    /**
     * La presenza è di un concessionario e devo controllare se l'autorizzazione potrebbe essere del concessionario ma
     * configurato di non utilizzare il borsellino per i concessionari
     * 
     * @param dataGiornata
     * 
     * @return
     */
    @Override
    public boolean verificaDataPosDebConcessionariAllaData(Date dataGiornata) {

	if (!comportamentiMercatiService.isAttiva() || comportamentiMercatiService.dataPosDebConcessionari() == null) {
	    return false;
	}
	return dataGiornata.compareTo(comportamentiMercatiService.dataPosDebConcessionari()) >= 0;
    }

    @Override
    public boolean nodoPagamentiAttivo(Mercati mercato) {

	//1. Controllo dei parametri
	if (mercato == null) {
	    throw new IllegalArgumentException("Non è stato passato il mercato di riferimento per la verifica dell'attivazione del nodo pagamenti");
	}
	if (mercato.getFlagAttivanodoPagam() == null || !mercato.getFlagAttivanodoPagam().booleanValue()) {
	    return false;
	}
	if (mercato.getComune() == null || StringUtils.isEmpty(mercato.getComune().getCodicecomune())) {
	    throw new IllegalArgumentException("Nel mercato " + mercato
		    .getDescrizione() + " non è stato configurato il comune di riferimento per la verifica dell'attivazione del nodo pagamenti");
	}
	VerticalizzazioneNodoPagamentiServiceImpl vertNodoPagamentiService = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService,
		mercato.getComune().getCodicecomune());
	return vertNodoPagamentiService.isAttiva();
    }

    private boolean posizioneDebitoriaPresente(MercatipresenzeD giornata) {

	return giornata.getDettPosizioneDebitoria() != null && giornata.getDettPosizioneDebitoria().getId() != null
		&& giornata.getDettPosizioneDebitoria().getId().getCodice() != null;
    }

    private void stornaMovimento(Integer idMovimentoDaStornare) {

	//1. Inserisco una copia della riga da stornare, volgendo l'importo al negativo
	BorsellinoMovimenti movimento = this.borsellinoMovimentiDAO.findById(new PkId(idMovimentoDaStornare));
	BorsellinoMovimenti storno = BorsellinoMovimenti.fromMovimentoDaStornare(movimento);
	
	BigDecimal saldototale;
	if(movimento.getBorsellino().getSaldoTotale() == null){
	    //Mi affido a questo come saldo
	    BigDecimal saldo = AbbonamentoTabellaModel.fromBorsellino(movimento.getBorsellino()).getCreditoResiduo();
	    storno.setCreditoIniziale(saldo);
	    
	    saldototale = saldo.add(storno.getImporto());
	    storno.setCreditoFinale(saldototale);
	}else{
	    storno.setCreditoIniziale(movimento.getBorsellino().getSaldoTotale());
	    
	    saldototale = movimento.getBorsellino().getSaldoTotale().add(storno.getImporto());
	    storno.setCreditoFinale(saldototale);
	}
	
	this.borsellinoMovimentiDAO.insert(storno);
	//2. Aggiorno la riga da stornare, inserendo l'id della riga che l'ha stornata
	movimento.setMovimentoStorno(storno);
	this.borsellinoMovimentiDAO.update(movimento);
	
	try {
	    publisher.publishThrowOnFailure(new EventoMovimentoInserito(movimento.getBorsellinoID(), storno.getImporto(), saldototale));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public boolean isPresenzaConPagamentoAnnullabile(Integer idPresenza) {

	MercatipresenzeD presenza = mercatipresenzeDService.findById(new PkId(idPresenza));
	Integer idPosteggio = null;
	if (presenza.getPosteggio() != null) {
	    idPosteggio = presenza.getPosteggio().getId().getCodice();
	    Integer movimenti = borsellinoMovimentiDAO.findIdNonStornatoByRiferimenti(idPosteggio, presenza.getMercatiPresenzeT().getId().getCodice(),
		    presenza.getAutorizzazioni().getId().getCodice());
	    if (movimenti != null) {
		return false;
	    }
	    if (presenza.getDettPosizioneDebitoria() != null) {
		return new StatiPosizioniDebitorieConverter().isStatoAnnullamentoAmmesso(presenza.getDettPosizioneDebitoria().getStato());
	    }
	}
	return true;
    }

    public boolean gestiscePagamenti(Mercati m) {

	return org.apache.commons.lang.BooleanUtils.toBoolean(m.getFlagContabilita());
    }
}
