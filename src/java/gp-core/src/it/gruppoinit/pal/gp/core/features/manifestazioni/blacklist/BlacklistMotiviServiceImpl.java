package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BlacklistAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.BlacklistSrcPDebSp;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class BlacklistMotiviServiceImpl extends BaseServiceImpl<BlacklistMotivi, PkId> implements BlacklistMotiviService {

    private enum StatoBlackList {
	POSIZIONE_DEBITORIA_NON_PRESENTE,
	SCOLLEGATA_DA_PRESENZA,
	RIMUOVIBILE_DA_BLACKLIST,
	ID_AUTORIZZAZIONE_DIVERSO,
	IN_CORSO
    }

    private static final Logger log = LoggerFactory.getLogger(BlacklistMotiviServiceImpl.class);
    private BlacklistMotiviDAO blacklistMotiviDAO;
    private BlacklistSrcPDebSpService blacklistSrcPDebSpService;
    private BlacklistAutorizzazioniService blacklistAutorizzazioniService;
    private MercatipresenzeDService mercatipresenzeDService;
    private AutorizzazioniService autorizzazioniService;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private VerticalizzazioniService verticalizzazioniService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setDettPosizioneDebitoriaService(DettPosizioneDebitoriaService dettPosizioneDebitoriaService) {

	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setBlacklistAutorizzazioniService(BlacklistAutorizzazioniService blacklistAutorizzazioniService) {

	this.blacklistAutorizzazioniService = blacklistAutorizzazioniService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setBlacklistSrcPDebSpService(BlacklistSrcPDebSpService blacklistSrcPDebSpService) {

	this.blacklistSrcPDebSpService = blacklistSrcPDebSpService;
    }

    @Autowired
    public void setBlacklistMotiviDAO(BlacklistMotiviDAO blacklistMotiviDAO) {

	this.blacklistMotiviDAO = blacklistMotiviDAO;
    }
    
    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {
    
        this.anagrafeService = anagrafeService;
    }

    private void dataIntegration(BlacklistMotivi entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(BlacklistMotivi entity) {

	super.fixMergeEntityProperties(entity);
    }

    @Override
    public void insert(BlacklistMotivi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    blacklistMotiviDAO.insert(entity);
	}
    }

    @Override
    public void update(BlacklistMotivi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    blacklistMotiviDAO.update(entity);
	}
    }

    @Override
    public void delete(BlacklistMotivi entity) {

	if (isDeleteAllowed(entity)) {
	    blacklistMotiviDAO.delete(entity);
	}
    }

    @Override
    public List<BlacklistMotivi> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public BlacklistMotivi findById(PkId id) {

	return blacklistMotiviDAO.findById(id);
    }

    @Override
    protected Class<BlacklistMotivi> getEntityClass() {

	return BlacklistMotivi.class;
    }

    @Override
    public StringBuilder updateBlackList() {

	String idOperazione = Utilities.generaPassword(4) + "-" + System.currentTimeMillis();
	Date dataOraVerifica = Calendar.getInstance().getTime();
	StringBuilder result = new StringBuilder();
	String response = this.updateBlackList(idOperazione, dataOraVerifica);
	result.append(response);
	response = this.impostaDataFine(idOperazione, dataOraVerifica);
	result.append(response);
	return result;
    }

    @SuppressWarnings("unchecked")
    private String updateBlackList(String idOperazione, Date dataOraVerifica) {

	Map<Integer, String> mercatiCodiciComuni = new HashMap<Integer, String>();
	StringBuilder response = new StringBuilder();
	log.debug("{} Inizio verifica delle posizioni debitorie concessionari per la blacklist", idOperazione);
	//	log.debug("{} valore della verticalizzazione {}.{} ==> {}",
	//		new Object[] { idOperazione, VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE,
	//			VerticalizzazioneNodoPagamentiServiceImpl.BLACKLIST_TIME_CHECK_PAGAM,
	//			this.vertNodoPagamentiService.blackListTimeCheckPagam() });
	List<String> statiPagabili = new StatiPosizioniDebitorieConverter().getElencoNomiStatiPerBlackList();
	/*La struttura del codice resterà abbastanza simile fatta eccezione per qualche particolare, quindi ritengo
	 * oppurtuno raggruppare le posizioni debitorie in mappa a seconda del contesto
	 */
	BlackListContestoEnum[] tipi = new BlackListContestoEnum[] { BlackListContestoEnum.PRESENZE, BlackListContestoEnum.BOLLETTAZIONE };
	List<PosizioneDaAggiungereABlackList> posizioniPresenze = this.blacklistMotiviDAO.findElencoPosizioniDaAggiungereABlackList(statiPagabili,
		BlackListContestoEnum.PRESENZE);
	List<PosizioneDaAggiungereABlackList> posizioniBollettazione = this.blacklistMotiviDAO
		.findElencoPosizioniDaAggiungereABlackList(statiPagabili, BlackListContestoEnum.BOLLETTAZIONE);
	//Li sto raggruppando per posizione debitoria
	Map<Integer, List<PosizioneDaAggiungereABlackList>> posizioniBollettazioneMap = new HashMap<Integer, List<PosizioneDaAggiungereABlackList>>();
	for (PosizioneDaAggiungereABlackList pb : posizioniBollettazione) {
	    if (!posizioniBollettazioneMap.containsKey(pb.getIdDettPosizioneDebitoria())) {
		posizioniBollettazioneMap.put(pb.getIdDettPosizioneDebitoria(), new ArrayList<PosizioneDaAggiungereABlackList>());
	    }
	    posizioniBollettazioneMap.get(pb.getIdDettPosizioneDebitoria()).add(pb);
	}
	response.append("Lista delle posizioni debitorie non pagate \n[");
	response.append(posizioniPresenze).append("   ").append(posizioniBollettazioneMap);
	response.append("]");
	response.append("\ndata di verifica ");
	response.append(dataOraVerifica);
	log.debug("{} Posizioni debitorieTrovate intervalloInserimentoBL ==> {}", idOperazione,
		posizioniPresenze + "   " + posizioniBollettazioneMap);
	/*
	 * Abbiamo detto che il valore della verticalizzazione non cambierà a seconda del codicecomune, quindi per il primo codicecomune che troveremo
	 * memorizzeremo il valore della verticalizzazione e lo riutilizzeremo per ogni codice comune non vuoto
	 */
	String blacklistTimeBollettazione = null;
	for (BlackListContestoEnum tipo : tipi) {
	    Iterable<?> iterable = BlackListContestoEnum.PRESENZE == tipo ? posizioniPresenze : posizioniBollettazioneMap.entrySet();
	    for (Object elem : iterable) {
		//Per presenze 	
		PosizioneDaAggiungereABlackList posizionePresenze = null;
		//Per bollettazione
		Map.Entry<Integer, List<PosizioneDaAggiungereABlackList>> posizioneBollettazione = null;
		Integer idPosizioneDebitoria;
		if (BlackListContestoEnum.PRESENZE == tipo) {
		    posizionePresenze = (PosizioneDaAggiungereABlackList) elem;
		    idPosizioneDebitoria = posizionePresenze.getIdDettPosizioneDebitoria();
		} else {
		    posizioneBollettazione = (Map.Entry<Integer, List<PosizioneDaAggiungereABlackList>>) elem;
		    idPosizioneDebitoria = posizioneBollettazione.getKey();
		}
		log.debug("{} Verifico la posizione debitoria ==> {}", idOperazione, idPosizioneDebitoria);
		DettPosizioneDebitoria dettPosizioneDebitoria = dettPosizioneDebitoriaService.findById(new PkId(idPosizioneDebitoria));
		//
		Date dataFine = null;
		if (BlackListContestoEnum.PRESENZE == tipo) {
		    String blacklistTime = null;
		    //Qui conserveremo il comportamento
		    MercatipresenzeD presenza = mercatipresenzeDService.findById(new PkId(posizionePresenze.getIdPresenza()));
		    String codiceComune = mercatiCodiciComuni.get(presenza.getMercatiPresenzeT().getMercato().getId().getCodice());
		    if (StringUtils.isBlank(codiceComune) && presenza.getMercatiPresenzeT().getMercato().getComune() != null) {
			codiceComune = presenza.getMercatiPresenzeT().getMercato().getComune().getCodicecomune();
			mercatiCodiciComuni.put(presenza.getMercatiPresenzeT().getMercato().getId().getCodice(), codiceComune);
		    }
		    presenza.getMercatiPresenzeT().getMercato().getComune();
		    blacklistTime = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune)
			    .blackListTimeCheckPagam(BlackListContestoEnum.PRESENZE);
		    if (StringUtils.isBlank(blacklistTime)) {
			blacklistTime = VerticalizzazioneNodoPagamentiServiceImpl.getDefaultBlackListTime();
		    }
		    dataFine = Utilities.addDuration(dettPosizioneDebitoria.getDataUltimoStato(), blacklistTime);
		} else {
		    dataFine = dettPosizioneDebitoria.getDataScadenza(); // la data di accesso in black list per la bollettazione coincide con la data di scadenza
		    // della posizione debitoria
		    if (dataFine == null) {
			log.warn("{} data scadenza nulla per la posizione debitoria ==> {}", idOperazione, idPosizioneDebitoria);
			String blacklistTime = null;
			String codiceComune = dettPosizioneDebitoria.getComune() != null ? dettPosizioneDebitoria.getComune().getCodicecomune()
				: null;
			if (!StringUtils.isBlank(codiceComune)) {
			    if (blacklistTimeBollettazione != null) {
				blacklistTime = blacklistTimeBollettazione;
			    } else {
				blacklistTime = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune)
					.blackListTimeCheckPagam(BlackListContestoEnum.BOLLETTAZIONE);
				if (!StringUtils.isBlank(blacklistTime)) {
				    blacklistTimeBollettazione = blacklistTime; //Popoliamo la variabile
				} else {
				    blacklistTime = VerticalizzazioneNodoPagamentiServiceImpl.getDefaultBlackListTimeBollettazione();
				}
			    }
			    dataFine = Utilities.addDuration(dettPosizioneDebitoria.getDataUltimoStato(), blacklistTime);
			} else {
			    /*
			     * Se non c'è il codicecomune salto l'iterazione, se invece esiste
			     * userò il valore di blacklistTimeBollettazione se già valorizzato
			     */
			    log.warn("{} Codice comune vuoto per la posizione debitoria ==> {}", idOperazione, idPosizioneDebitoria);
			    //NON SI PUO' FARE LETTURA PERCHE' NON PUOI ISTANZIARE IL SERVICE SENZA CODICECOMUNE VALIDO
			    continue;
			}
		    }
		}
		if (log.isDebugEnabled()) {
		    response.append("\n==");
		    response.append("\n");
		    response.append(getTempoEsecuzione());
		    response.append("\nLa posizione debitoria ");
		    response.append(dettPosizioneDebitoria);
		    response.append(" non è presente nella blacklist");
		    response.append("\nLo stato della posizione debitoria è ");
		    response.append(dettPosizioneDebitoria.getStato());
		}
		log.debug("{} data richiesta pagamento {} data limite {}",
			new Object[] { idOperazione, dettPosizioneDebitoria.getDataUltimoStato(), dataFine });
		if (dataOraVerifica.compareTo(dataFine) <= 0) {
		    if (log.isDebugEnabled()) {
			response.append("\nLa posizionedebitoria non è stata inserita nella blacklist ");
			response.append("\n==");
		    }
		    continue;
		}
		//
		if (log.isDebugEnabled()) {
		    log.debug("{} dataOraVerifica ==> {}", idOperazione, Utilities.formatDate(dataOraVerifica, true));
		}
		if (log.isDebugEnabled()) {
		    response.append("\nInserisco la posizionedebitoria nella blacklist ");
		    log.debug("{} La posizione {} non è stata pagata inserisco nella blacklist ",
			    new Object[] { idOperazione, idPosizioneDebitoria });
		}
		//
		BlacklistMotivi entity = new BlacklistMotivi(dettPosizioneDebitoria.getMotivo(), dataOraVerifica);
		entity.setContesto(tipo.name().toLowerCase());//Qui valorizziamo il nuovo campo CONTESTO	    
		this.insert(entity);
		//
		BlacklistSrcPDebSp sorgente = new BlacklistSrcPDebSp(entity, dettPosizioneDebitoria.getDataUltimoStato(), dettPosizioneDebitoria);
		blacklistSrcPDebSpService.insert(sorgente);
		//
		if (BlackListContestoEnum.PRESENZE == tipo) {
		    MercatipresenzeD mpd = this.mercatipresenzeDService.findById(new PkId(posizionePresenze.getIdPresenza()));
		    if (mpd.getAutorizzazioni() != null) {
			Autorizzazioni a = mpd.getAutorizzazioni();
			Anagrafe titolare = autorizzazioniService.findAnagrafeAutorizzazione(a.getId().getCodice());
			this.blacklistAutorizzazioniService.aggiungiABlackList(entity, mpd, titolare);
		    }
		} else {
		    for (PosizioneDaAggiungereABlackList bean : posizioneBollettazione.getValue()) {
			Autorizzazioni a = autorizzazioniService.findById(new PkId(bean.getIdAutorizzazione()));
			Anagrafe anagrafe = anagrafeService.findById(new PkId(bean.getCodiceanagrafe()));
			this.blacklistAutorizzazioniService.insert(new BlacklistAutorizzazioni(entity, a, anagrafe, null, Boolean.TRUE));
		    }
		}
		this.blacklistMotiviDAO.commit();
		this.blacklistMotiviDAO.flush();
		this.blacklistMotiviDAO.clear();
	    }
	}
	return response.toString();
    }

    private String impostaDataFine(String idOperazione, Date dataOraVerifica) {

	StringBuilder response = new StringBuilder();
	response.append("\n").append(this.getTempoEsecuzione()).append("\nRicerco le posizioni debitorie in blacklist da verificare ");
	log.debug("{} Ricerco le posizioni debitorie in blacklist da verificare", idOperazione);
	Map<BlackListContestoEnum, List<ElementoBlackListDaChiudereBean>> posizioniDebitorieMap = new HashMap<BlackListContestoEnum, List<ElementoBlackListDaChiudereBean>>();
	List<ElementoBlackListDaChiudereBean> elencoPresenze = this.blacklistMotiviDAO.findBlackListAperte(BlackListContestoEnum.PRESENZE);
	List<ElementoBlackListDaChiudereBean> elencoBollettazione = this.blacklistMotiviDAO.findBlackListAperte(BlackListContestoEnum.BOLLETTAZIONE);
	int posizioniDebitorieSize = elencoPresenze.size() + elencoBollettazione.size();
	posizioniDebitorieMap.put(BlackListContestoEnum.PRESENZE, elencoPresenze);
	posizioniDebitorieMap.put(BlackListContestoEnum.BOLLETTAZIONE, elencoBollettazione);
	log.debug("# blacklistSrcPDebSp {} record da aggiornare", posizioniDebitorieSize);
	response.append("\nTrovate ").append(posizioniDebitorieSize).append(" posizioni debitorie in blacklist da verificare ");
	for (Map.Entry<BlackListContestoEnum, List<ElementoBlackListDaChiudereBean>> entry : posizioniDebitorieMap.entrySet()) {
	    List<ElementoBlackListDaChiudereBean> elenco = entry.getValue();
	    for (ElementoBlackListDaChiudereBean elemento : elenco) {
		StatoBlackList stato = this.getStatoBlackList(elemento, entry.getKey());
		String messaggio = "";
		switch (stato) {
		case POSIZIONE_DEBITORIA_NON_PRESENTE:
		    messaggio = this.chiudiMotivoNonAssociatoAPosizioneDebitoria(elemento, dataOraVerifica);
		    break;
		case RIMUOVIBILE_DA_BLACKLIST:
		    messaggio = this.chiudiMotivoPosizionePagataOAnnullata(elemento, dataOraVerifica);
		    break;
		case SCOLLEGATA_DA_PRESENZA:
		    messaggio = this.chiudiMotivoNonAssociatoAPresenza(elemento, dataOraVerifica);
		    break;
		case ID_AUTORIZZAZIONE_DIVERSO:
		    messaggio = this.chiudiMotivoAutorizzazioneDiversa(elemento, dataOraVerifica);
		    break;
		case IN_CORSO:
		    messaggio = "In corso";
		    break;
		}
		if (log.isDebugEnabled()) {
		    StringBuilder s = new StringBuilder();
		    s.append(idOperazione).append("\n");
		    s.append(elemento.toString());
		    s.append("\n==");
		    //
		    log.debug("{}: {}", s, messaggio);
		    response.append(s.toString());
		    ///
		    response.append("\n").append(messaggio).append("\n==\n");
		}
	    }
	}
	return response.toString();
    }

    
    private StatoBlackList getStatoBlackList(ElementoBlackListDaChiudereBean elemento, BlackListContestoEnum contesto) {
	
	if(BlackListContestoEnum.PRESENZE == contesto){
	    return getStatoBlackListPresenze(elemento);
	}else if(BlackListContestoEnum.BOLLETTAZIONE == contesto){
	    return getStatoBlackListBollettazione(elemento);
	}else{
	    throw new RuntimeException("Nessun contesto valido");
	}
	
    }
    
    
    private StatoBlackList getStatoBlackListPresenze(ElementoBlackListDaChiudereBean elemento) {

	if (elemento.getIdDettPosizioneDebitoria() == null) {
	    return StatoBlackList.POSIZIONE_DEBITORIA_NON_PRESENTE;
	}
	if (elemento.getIdMercatiPresenzeD() == null) {
	    return StatoBlackList.SCOLLEGATA_DA_PRESENZA;
	}
	if (new StatiPosizioniDebitorieConverter().rimuovibileDaBlackList(elemento.getStato())) {
	    return StatoBlackList.RIMUOVIBILE_DA_BLACKLIST;
	}
	if (!elemento.getIdAutBlackList().equals(elemento.getIdAutMercatiPresenzeD())) {
	    return StatoBlackList.ID_AUTORIZZAZIONE_DIVERSO;
	}
	return StatoBlackList.IN_CORSO;
    }
    
    private StatoBlackList getStatoBlackListBollettazione(ElementoBlackListDaChiudereBean elemento) {

	if (new StatiPosizioniDebitorieConverter().rimuovibileDaBlackList(elemento.getStato())) {
	    return StatoBlackList.RIMUOVIBILE_DA_BLACKLIST;
	}
	return StatoBlackList.IN_CORSO;
    }

    private String chiudiMotivoAutorizzazioneDiversa(ElementoBlackListDaChiudereBean elemento, Date dataOraVerifica) {

	StringBuilder s = new StringBuilder();
	s.append("l'autorizzazione in blacklist ");
	s.append(elemento.getIdAutBlackList());
	s.append(" non coincide con quella legata alle presenze ");
	s.append(elemento.getIdAutMercatiPresenzeD());
	s.append(" pertanto verrà chiusa in data ");
	s.append(dataOraVerifica);
	BlacklistMotivi mot = this.findById(new PkId(elemento.getIdBlackListMotivi()));
	mot.setDataFineBl(dataOraVerifica);
	this.update(mot);
	return s.toString();
    }

    private String chiudiMotivoNonAssociatoAPresenza(ElementoBlackListDaChiudereBean elemento, Date dataOraVerifica) {

	StringBuilder s = new StringBuilder();
	s.append("la posizione non è agganciata a nessun posteggio - mercatipresenze_d - pertanto verrà chiusa in data ");
	s.append(dataOraVerifica);
	BlacklistMotivi mot = this.findById(new PkId(elemento.getIdBlackListMotivi()));
	mot.setDataFineBl(dataOraVerifica);
	this.update(mot);
	return s.toString();
    }

    private String chiudiMotivoPosizionePagataOAnnullata(ElementoBlackListDaChiudereBean elemento, Date dataOraVerifica) {

	StringBuilder s = new StringBuilder();
	s.append("la posizione debitoria è in stato ");
	s.append(elemento.getStato());
	s.append(" pertanto verrà chiusa in data ");
	s.append(dataOraVerifica);
	BlacklistMotivi mot = this.findById(new PkId(elemento.getIdBlackListMotivi()));
	mot.setDataFineBl(dataOraVerifica);
	this.update(mot);
	return s.toString();
    }

    private String chiudiMotivoNonAssociatoAPosizioneDebitoria(ElementoBlackListDaChiudereBean elemento, Date dataOraVerifica) {

	StringBuilder s = new StringBuilder();
	s.append("la blacklist non ha una posizione debitoria pertanto verrà chiusa in data ");
	s.append(dataOraVerifica);
	BlacklistMotivi mot = this.findById(new PkId(elemento.getIdBlackListMotivi()));
	mot.setDataFineBl(dataOraVerifica);
	this.update(mot);
	return s.toString();
    }

    private String getTempoEsecuzione() {

	return Utilities.formatDate(new Date(System.currentTimeMillis()), true);
    }

    @Override
    public List<BlacklistMotivi> findByAutorizzazioneEUso(Integer idAutorizzazione, Integer idMercatiUso, boolean soloGliAttivi,
	    BlackListContestoEnum[] contesti) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAutorizzazione, "blacklistAutorizzazionis", Integer.class));
	if (soloGliAttivi) {
	    fr.addFilterField(FilterUtils.isNull("dataFineBl"));
	}
	ft.addOrder(FilterUtils.orderAsc("dataInizioBl"));
	ft.addRestriction(fr);
	if (idMercatiUso != null) {
	    FilterRestriction usoOr = new FilterRestriction();
	    usoOr.setAndOrRestriction(AndOrRestriction.OR);
	    usoOr.addFilterField(FilterUtils.isNull("mercatiUsoId", "blacklistAutorizzazionis"));
	    usoOr.addFilterField(FilterUtils.equals("mercatiUsoId", idMercatiUso, "blacklistAutorizzazionis", Integer.class));
	    ft.addRestriction(usoOr);
	}
	if (contesti != null && contesti.length > 0) {
	    Set<String> contesto = new HashSet<String>();
	    for (BlackListContestoEnum con : contesti) {
		contesto.add(con.name().toLowerCase());
	    }
	    fr.addFilterField(FilterUtils.in("contesto", contesto.toArray(), String.class));
	}
	return blacklistMotiviDAO.findByFilterTable(ft);
    }
    
    @Override
    public List<Date> findAllDataAccertamentoByContesto(BlackListContestoEnum contesto){
	return blacklistMotiviDAO.findAllDataAccertamentoByContesto(contesto);
    }
    
    @Override
    public List<BlackListResultBean> getBlackListResultFe(BlackListContestoEnum contesto, String dataaccertamento, Date dalladatablacklist,
	    Date alladatablacklist, Date dalladataiuv, Date alladataiuv, String iuv, String titolare, String cf, Integer firstresult, Integer maxresult){
	return blacklistMotiviDAO.getBlackListResultFe(contesto, 
		dataaccertamento, dalladatablacklist, alladatablacklist, dalladataiuv, alladataiuv, iuv, titolare,
		cf, firstresult, maxresult);
    }
    
    @Override
    public List<BlackListResultBean> getBlackListChiuseExport(BlackListContestoEnum contesto){
	return blacklistMotiviDAO.getBlackListChiuseExport(contesto);
    }
}
