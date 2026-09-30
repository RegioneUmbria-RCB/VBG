package it.gruppoinit.pal.gp.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.MercatiAudPresenzeDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiStoricoInsBean;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiAudPresenze;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.service.MercatiAudPresenzeService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;

@Service
public class MercatiAudPresenzeServiceImpl extends BaseServiceImpl<MercatiAudPresenze, PkId> implements MercatiAudPresenzeService{
    
    private static final Logger log = LoggerFactory.getLogger(MercatiAudPresenzeServiceImpl.class);

    @Autowired
    private MercatiAudPresenzeDAO mercatiAudPresenze;
    @Autowired
    private MercatipresenzeDDAO mercatipresenzeDDAO;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    @Autowired
    private MercatiConfigurazioneDAO mercatiConfigurazioneDAO;
    
    @Override
    public void insert(MercatiAudPresenze entity) {

	mercatiAudPresenze.insert(entity);
	
    }

    @Override
    public void update(MercatiAudPresenze entity) {

	// TODO Auto-generated method stub
	
    }

    @Override
    public void delete(MercatiAudPresenze entity) {

	// TODO Auto-generated method stub
	
    }

    @Override
    public List<MercatiAudPresenze> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public MercatiAudPresenze findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected Class<MercatiAudPresenze> getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void spostaPresenze(Integer idsource, Integer iddest) {

	Integer idsorgente = idsource;
	Integer iddestinazione = iddest;
	log.info("idsorgente original: " + idsource);
	log.info("iddestinazione original: " + iddestinazione);
	
	if(idsorgente == null || iddestinazione == null || idsorgente.equals(iddestinazione)) {
	    log.error("errore sugli input: " + " idsorgente [" + idsorgente + "]" + " , iddestinazione [" + iddestinazione + "]");
	    throw new RuntimeException("Errore sugli input");
	}
	
	String autoriznumerosorg;
	String autoriznumerodest;
	
	Autorizzazioni sorgente = autorizzazioniService.findById(new PkId(idsorgente));
	Autorizzazioni collsorgente = null; //questa serve per la seconda eventuale disattivazione
	if(sorgente.getAutorizzazioniConcessionisForFkAutconcAutcoll() == null || sorgente.getAutorizzazioniConcessionisForFkAutconcAutcoll().isEmpty()) {
	    autoriznumerosorg = sorgente.getAutoriznumero();
	    
	    if(sorgente.getAutorizzazioniConcessionisForFkAutconcAutatt() != null && !sorgente.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
		AutorizzazioniConcessioni ac = null;
		for (AutorizzazioniConcessioni concessioni : sorgente.getAutorizzazioniConcessionisForFkAutconcAutatt()) {
		    ac = concessioni;
		    break;
		}
		collsorgente = ac.getAutorizzazioniByFkAutconcAutcoll();
	    }
	    
	    
	}else {
	    
	    collsorgente = sorgente;
	    
	    Autorizzazioni sorgente2 = null;
	    for(AutorizzazioniConcessioni concessioni : sorgente.getAutorizzazioniConcessionisForFkAutconcAutcoll()) {
		sorgente2 = concessioni.getAutorizzazioniByFkAutconcAutatt();
		break;
	    }
	    idsorgente = sorgente2.getId().getCodice();
	    autoriznumerosorg = sorgente2.getAutoriznumero();
	    sorgente = sorgente2;
	}
	
	Autorizzazioni destinazione = autorizzazioniService.findById(new PkId(iddestinazione));
	if(destinazione.getAutorizzazioniConcessionisForFkAutconcAutcoll() == null || destinazione.getAutorizzazioniConcessionisForFkAutconcAutcoll().isEmpty()) {
	    autoriznumerodest = destinazione.getAutoriznumero();
	}else {
	    Autorizzazioni destinazione2 = null;
	    for(AutorizzazioniConcessioni concessioni : destinazione.getAutorizzazioniConcessionisForFkAutconcAutcoll()) {
		
		destinazione2 = concessioni.getAutorizzazioniByFkAutconcAutatt();
		break;
	    }
	    iddestinazione = destinazione2.getId().getCodice();
	    autoriznumerodest = destinazione2.getAutoriznumero();
	    destinazione = destinazione2;

	}
	log.info("idsorgente original: " + idsource);
	log.info("iddestinazione original: " + iddestinazione);
	if(idsorgente == null || iddestinazione == null || idsorgente.equals(iddestinazione)) {
	    log.error("errore sugli input dopo il calcolo: " + " idsorgente [" + idsorgente + "]" + " , iddestinazione [" + iddestinazione + "]");
	    throw new RuntimeException("Errore sugli input dopo il calcolo");
	}
	
	//Questo per fare check su potenziali duplicati della destinazione
	//INIZIO
	List<MercatipresenzeStorico> storicoByAutorizzazioneDL = mercatipresenzeStoricoService.findByAutorizzazione(destinazione);
	if(storicoByAutorizzazioneDL == null) {
	    storicoByAutorizzazioneDL = new ArrayList<MercatipresenzeStorico>();
	}
	Map<MercatiStoricoInsBean, Integer[]> mapStorico = new HashMap<MercatiStoricoInsBean,Integer[]>();
	for(MercatipresenzeStorico storico : storicoByAutorizzazioneDL) {
	    
	    Integer codicemercato = storico.getMercato() == null ? null : storico.getMercato().getId().getCodice();
	    Integer codiceuso = storico.getMercatoUso() == null ? null : storico.getMercatoUso().getId().getCodice();
	    Integer codiceanagrafe = storico.getAnagrafe() == null ? null : storico.getAnagrafe().getId().getCodice();
	    
	    Calendar c = Calendar.getInstance();
	    c.setTime(storico.getData());
	    String data = c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);
	    
	    MercatiStoricoInsBean bean = new MercatiStoricoInsBean(storico.getId().getIdcomune(), codicemercato,
		    codiceuso, codiceanagrafe, data,
		    destinazione.getId().getCodice());
	    Integer[] v = new Integer[2];
	    v[0] = storico.getId().getCodice();
	    v[1] = storico.getNumeropresenze() == null ? 0 : storico.getNumeropresenze();
	    mapStorico.put(bean,v);
	}
	//FINE
	
	List<Object[]> presenze = mercatipresenzeDDAO.findPresenzeByAutorizzazione(idsorgente);
	if (presenze == null) {
	    presenze = new ArrayList<Object[]>();
	}
	int numeropresenze = presenze.size();
	
	MercatiAudPresenze mercatiAudPresenze = new MercatiAudPresenze();
	mercatiAudPresenze.setId(new PkId());
	mercatiAudPresenze.setDataOperazione(new java.util.Date());
	mercatiAudPresenze.setTotPresenze(numeropresenze);
	
	
	Responsabili caud = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (caud != null) {
	    mercatiAudPresenze.setCodiceOperatore(caud.getId().getCodice());   
	}else {
	    log.info("Empty codiceoperatore found");
	}
	
	Date now = new Date();
	SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	String formattedDate = sdff.format(now);

	StringBuilder sb = new StringBuilder();
	sb.append("### SPOSTAMENTO PRESENZE AUTORIZZAZIONE### in data ");
	sb.append(formattedDate);
	if(caud == null) {
	    sb.append(" sono state spostate ");
	}else {
	    sb.append(" l'operatore ").append(caud.getResponsabile()).append(" ha spostato ");
	}
	
	
	
	sb.append("\"").append(numeropresenze).append("\"");
	sb.append(" presenze dall'autorizzazione ");
	sb.append("\"").append(autoriznumerosorg).append("\"").append(" ponte ");
	sb.append(" ed assegnate a ");
	sb.append("\"").append(autoriznumerodest).append("\"");	
	
	mercatiAudPresenze.setNote(sb.toString());
	
	
	mercatiAudPresenze.setFkIdAutSorgente(idsorgente);
	mercatiAudPresenze.setFkIdAutDest(iddestinazione);

	insert(mercatiAudPresenze);
	
	Map<MercatiStoricoInsBean, MercatipresenzeStorico> mp = new HashMap<MercatiStoricoInsBean, MercatipresenzeStorico>();
	
	//Popoliamo la mappa secondo chiave univoca sulla collection delle presenze
	//INIZIO
	for(Object[] o : presenze) {
	    
	    String idcomune = ORMHelper.getIdcomune();
	    
	    java.util.Date d = (java.util.Date)o[0];
	    Calendar c = Calendar.getInstance();c.setTime(d);
	    
	    String data = c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);
	    
	    Integer codiceMercato = (Integer)o[1];
	    Integer codiceUso = (Integer)o[2];
	    Integer codiceAnagrafe = (Integer)o[3];
	    Integer fkidposteggio = (Integer)o[4];
	    MercatiStoricoInsBean bean = new MercatiStoricoInsBean(idcomune, codiceMercato,
		    codiceUso, codiceAnagrafe, data,
		    destinazione.getId().getCodice());
	    
	    if(mp.containsKey(bean)) {
		MercatipresenzeStorico st = mp.get(bean);
		int prsnz = st.getNumeropresenze() == null ? 0 : st.getNumeropresenze();
		st.setNumeropresenze(prsnz + 1);
		continue;
	    }
	    
	    MercatipresenzeStorico storico = new MercatipresenzeStorico();
	    storico.setId(new PkId());storico.getId().setIdcomune(idcomune);
	    
	    
	    
	    storico.setData(d);
	    storico.setAnno(c.get(Calendar.YEAR));
	    
	    Mercati m = new Mercati();m.setId(new PkId(idcomune, codiceMercato));
	    storico.setMercato(m);
	    
	    MercatiUso mu = new MercatiUso(); mu.setId(new PkId(idcomune, codiceUso));
	    storico.setMercatoUso(mu);
	    
	    Anagrafe a = new Anagrafe(); a.setId(new PkId(codiceAnagrafe));
	    storico.setAnagrafe(a);
	    
	    storico.setNumeropresenze(1);
	    
	    MercatiD posteggio = new MercatiD();posteggio.setId(new PkId(idcomune, fkidposteggio));
	    storico.setPosteggio(posteggio);
	    
	    storico.setAutorizzazioni(destinazione);
	    storico.setNumPresProprietario(0);
	    
	    mp.put(bean, storico);
	}
	//FINE
	
	List<MercatipresenzeStorico> storicoByAutorizzazioneL = mercatipresenzeStoricoService.findByAutorizzazione(sorgente);
	if(storicoByAutorizzazioneL == null) {
	    storicoByAutorizzazioneL = new ArrayList<MercatipresenzeStorico>();
	}
	
	//Popoliamo la mappa secondo chiave univoca sulla collection dello storico by sorgente
	//INIZIO
	for(MercatipresenzeStorico sto : storicoByAutorizzazioneL) {
	    MercatipresenzeStorico storico = new MercatipresenzeStorico();
	    storico.setId(new PkId());storico.getId().setIdcomune(sto.getId().getIdcomune());
	    
	    storico.setData(sto.getData());
	    storico.setAnno(sto.getAnno());
	    storico.setMercato(sto.getMercato());
	    storico.setMercatoUso(sto.getMercatoUso());
	    storico.setAnagrafe(sto.getAnagrafe());
	    storico.setNumeropresenze(sto.getNumeropresenze());
	    storico.setPosteggio(sto.getPosteggio());
	    storico.setAutorizzazioni(destinazione);
	    storico.setNumPresProprietario(sto.getNumPresProprietario());
	    
	    storico.setCatMerc(sto.getCatMerc());
	    //IDENT_AUT non è più gestito
	    
	    //Check del duplicato ed eventuale update
	    //INIZIO
	    Integer codicemercato = storico.getMercato() == null ? null : storico.getMercato().getId().getCodice();
	    Integer codiceuso = storico.getMercatoUso() == null ? null : storico.getMercatoUso().getId().getCodice();
	    Integer codiceanagrafe = storico.getAnagrafe() == null ? null : storico.getAnagrafe().getId().getCodice();
	    
	    Calendar c = Calendar.getInstance();
	    c.setTime(storico.getData());
	    String data = c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);
	    
	    MercatiStoricoInsBean bean = new MercatiStoricoInsBean(storico.getId().getIdcomune(), codicemercato,
		    codiceuso, codiceanagrafe, data,
		    destinazione.getId().getCodice());
	    
	    if(mp.containsKey(bean)) {
		MercatipresenzeStorico st = mp.get(bean);
		int prsnz = st.getNumeropresenze() == null ? 0 : st.getNumeropresenze();
		int storiconumpresenze = storico.getNumeropresenze() == null ? 0 : storico.getNumeropresenze();
		st.setNumeropresenze(prsnz + storiconumpresenze);
		continue;
	    }
	    
	    mp.put(bean, storico);
	    
	}
	//FINE
	
	//Ora che la mappa è pronta possiamo fare insert o update
	//INIZIO
	for(Map.Entry<MercatiStoricoInsBean, MercatipresenzeStorico> entry : mp.entrySet()) {
	    
	    if(mapStorico.containsKey(entry.getKey())) {
		
		Integer[] v = mapStorico.get(entry.getKey());
		String idcomune = entry.getValue().getId().getIdcomune();
		
		MercatipresenzeStorico riga = mercatipresenzeStoricoService.findById(new PkId(idcomune,v[0]));
		riga.setNumeropresenze(entry.getValue().getNumeropresenze() + v[1]);
		mercatipresenzeStoricoService.update(riga);
		v[1] = riga.getNumeropresenze();
		continue;
		
	    }
	    
	    mercatipresenzeStoricoService.insert(entry.getValue());
	}
	//FINE
	
	
	
	MercatiConfigurazione configurazione = mercatiConfigurazioneDAO.findConfigurazione();
	Concessionicausali causaleCessSist = null;
	if(configurazione != null) {
	    causaleCessSist = configurazione.getCausaleCessSistema();
	}
	
	sorgente.setFlagAttiva(false);
	sorgente.setDataCessazione(new java.util.Date());
	sorgente.setConcessionicausaliByFkAutConccausCess(causaleCessSist);
	autorizzazioniService.update(sorgente);
	
	if(collsorgente != null) {
	    collsorgente.setFlagAttiva(false);
	    collsorgente.setDataCessazione(new java.util.Date());
	    collsorgente.setConcessionicausaliByFkAutConccausCess(causaleCessSist);
	    autorizzazioniService.update(collsorgente);
	}
	
	LoggerModificheIstanze.log(sb.toString());
	
    }
}
