package it.gruppoinit.pal.gp.core.features.autorizzazioni.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AreaPubblica;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AutorizzazioniMercatoSrv;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.AutorizzazioniMercatoSrvBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.PosteggioPerAutBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.PosteggioPerAutBeanDataComparator;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.PosteggioPerAutBeanGiornoComparator;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniCsiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class AutorizzazioniMercatiSrvHelper {

    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniMercatiSrvHelper.class);
    private MercatipresenzeDService mercatipresenzeDService;
    private LayouttestiService layouttestiService;
    private Map<ETICHETTE_MERCATI_SRV_BEAN, String> etichette;
    private Map<Integer, List<PosteggioPerAutBean>> posteggiSganciatiPeraut = null;
    List<AutorizzazioniMercatoSrvBean> autsTrovate = null;
    private Set<String> codiciFiscali;

    private AutorizzazioniMercatiSrvHelper() {

	super();
    }

    public AutorizzazioniMercatiSrvHelper(MercatipresenzeDService mercatipresenzeDService, LayouttestiService layouttestiService,
	    AutorizzazioniCsiService autorizzazioniCsiService, List<AutorizzazioniMercatoSrvBean> autsTrovate, Set<String> codiciFiscali) {

	this();
	this.mercatipresenzeDService = mercatipresenzeDService;
	this.layouttestiService = layouttestiService;
	this.autsTrovate = autsTrovate;
	this.codiciFiscali = codiciFiscali;
	recuperaEtichetteRuolo();
	popolaMappaPosteggiSganciati(autorizzazioniCsiService.existsRecordsPerEnte());
    }

    private Map<Integer, List<PosteggioPerAutBean>> popolaMappaPosteggiSganciati(boolean isAutorizzazioniCSI) {

	posteggiSganciatiPeraut = new HashMap<Integer, List<PosteggioPerAutBean>>();
	if (isAutorizzazioniCSI) {
	    Set<Integer> autSenzaPosteggi = new HashSet<Integer>();
	    for (AutorizzazioniMercatoSrvBean autB : this.autsTrovate) {
		if (StringUtils.isBlank(autB.getCodiceposteggio())) {
		    autSenzaPosteggi.add(autB.getIdaut());
		}
	    }
	    List<PosteggioPerAutBean> dati = mercatipresenzeDService.findAutorizzazioniSganciateDaSIAP(autSenzaPosteggi);
	    Map<Integer, List<PosteggioPerAutBean>> mappaAutPerMercato = new HashMap<Integer, List<PosteggioPerAutBean>>();
	    for (PosteggioPerAutBean pa : dati) {
		List<PosteggioPerAutBean> post = mappaAutPerMercato.get(pa.getIdaut());
		if (post == null) {
		    post = new ArrayList<PosteggioPerAutBean>();
		}
		post.add(pa);
		mappaAutPerMercato.put(pa.getIdaut(), post);
	    }
	    // "autorizzazione frazionata viene associata al posteggio 0111 ma l'ultimo posteggio associato sulla mercatipresenze_d è il 93 ,  occorre prendere sempre il più recente"
	    // per prendere il più recente devo tirare fuori le date e prendere la più recente per mercato
	    for (Entry<Integer, List<PosteggioPerAutBean>> p : mappaAutPerMercato.entrySet()) {
		Integer idAut = p.getKey();
		Map<String, List<PosteggioPerAutBean>> posPerMercato = new HashMap<String, List<PosteggioPerAutBean>>();
		List<PosteggioPerAutBean> ps = p.getValue();
		for (PosteggioPerAutBean pab : ps) {
		    // associo i posteggio ad una chiave mercato uso nella descrizione c'è scritto GRIOLI LUN LO RIPORTO A GRIOLI
		    String key = pab.getMercato().replace(pab.getUso(), "");
		    List<PosteggioPerAutBean> pss = posPerMercato.get(key);
		    if (pss == null) {
			pss = new ArrayList<PosteggioPerAutBean>();
		    }
		    pss.add(pab);
		    posPerMercato.put(key, pss);
		}
		for (Entry<String, List<PosteggioPerAutBean>> pFinale : posPerMercato.entrySet()) {
		    // CILCO I POSTEGGI PER CHIAVE 
		    List<PosteggioPerAutBean> posteggi = pFinale.getValue();
		    //  effettuo il sort e mette il più recente come ultimo elemento
		    Collections.sort(posteggi, new PosteggioPerAutBeanDataComparator());
		    List<PosteggioPerAutBean> post = posteggiSganciatiPeraut.get(idAut);
		    if (post == null) {
			post = new ArrayList<PosteggioPerAutBean>();
		    }
		    int posizione = posteggi.size();
		    String codicePosteggio = "";
		    String giorno = "";
		    PosteggioPerAutBean posteggioTrovato = null;
		    if (posizione == 0) {
			// non dovrebbe capitare
			continue;
		    } else {
			posteggioTrovato = posteggi.get((posizione - 1));
			codicePosteggio = posteggioTrovato.getCodiceposteggio();
			giorno = posteggioTrovato.getGiorno();
			post.add(posteggioTrovato);
		    }
		    // ne aggiungo solo uno per percato/uso l'ultimo in ordine di data presenza su quel mercato
		    // cerco se ci sono altri giorni per il posteggio con quel codice
		    for (PosteggioPerAutBean po : posteggi) {
			if (po.getCodiceposteggio().equalsIgnoreCase(codicePosteggio) && !po.getGiorno().equals(giorno)) {
			    post.add(po);
			}
		    }
		    if(!post.isEmpty()) {
			Collections.sort(post, new PosteggioPerAutBeanGiornoComparator());
		    }
		    posteggiSganciatiPeraut.put(idAut, post);
		}
	    }
	}
	return posteggiSganciatiPeraut;
    }

    public enum ETICHETTE_MERCATI_SRV_BEAN {
	RUOLO_PROPRIETARIO_AUT, //
	RUOLO_PROPRIETARIO_AUT_IN_AFFITTO, // 
	RUOLO_AFFITTUARIO, //
	TIPOLOGIA_TITOLO_ITINERANTE, //
	TIPOLOGIA_TITOLO_CON_POSTEGGIO, //
	STATO_AUTORIZZAZIONE_ATTIVA, //
	STATO_AUTORIZZAZIONE_NON_ATTIVA
    }

    private void recuperaEtichetteRuolo() {

	this.etichette = new HashMap<ETICHETTE_MERCATI_SRV_BEAN, String>();
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_PROPRIETARIO_AUT,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.ruolo.proprietario_aut", ORMHelper.getSoftware()));
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_PROPRIETARIO_AUT_IN_AFFITTO,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.ruolo.proprietario_aut_in_affitto", ORMHelper.getSoftware()));
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_AFFITTUARIO,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.ruolo.affittuario", ORMHelper.getSoftware()));
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.TIPOLOGIA_TITOLO_CON_POSTEGGIO,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.titolo.con_posteggio", ORMHelper.getSoftware()));
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.TIPOLOGIA_TITOLO_ITINERANTE,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.titolo.itinerante", ORMHelper.getSoftware()));
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.TIPOLOGIA_TITOLO_ITINERANTE,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.titolo.itinerante", ORMHelper.getSoftware()));
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.STATO_AUTORIZZAZIONE_ATTIVA,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.stato.attiva", ORMHelper.getSoftware()));
	etichette.put(ETICHETTE_MERCATI_SRV_BEAN.STATO_AUTORIZZAZIONE_NON_ATTIVA,
		layouttestiService.testoDaEtichetta("label.autorizzazioni.stato.non_attiva", ORMHelper.getSoftware()));
    }

    private void checkAutorizzazioneMercatoSrv(List<AutorizzazioniMercatoSrv> ret, AutorizzazioniMercatoSrvBean autBean,
	    boolean escludiAutorizzazioniDateInAffitto) {

	// data la lista dei Codici fiscali devo duplicare il ritorno se presente un record dove sono presenti entrambe
	log.debug("checkAutorizzazioneMercatoSrv codiciFiscali {}", codiciFiscali);
	log.debug("checkAutorizzazioneMercatoSrv titolare {}", autBean.getTitcodicefiscale());
	log.debug("checkAutorizzazioneMercatoSrv occupante {}", autBean.getOcccodicefiscale());
	log.debug("checkAutorizzazioneMercatoSrv gerente {}", autBean.getGercodicefiscale());
	if (codiciFiscali.isEmpty()) {
	    ret.addAll(toAutorizzazioniMercatoSrv(autBean, autBean.getTitcodicefiscale(), escludiAutorizzazioniDateInAffitto));
	    if (StringUtils.isNotBlank(autBean.getOcccodicefiscale())
		    && !autBean.getTitcodicefiscale().equalsIgnoreCase(autBean.getOcccodicefiscale())) {
		ret.addAll(toAutorizzazioniMercatoSrv(autBean, autBean.getOcccodicefiscale(), escludiAutorizzazioniDateInAffitto));
	    }
	    if (StringUtils.isNotBlank(autBean.getGercodicefiscale()) //
		    && !autBean.getTitcodicefiscale().equalsIgnoreCase(autBean.getGercodicefiscale()) //
		    && !autBean.getOcccodicefiscale().equalsIgnoreCase(autBean.getGercodicefiscale())) {
		ret.addAll(toAutorizzazioniMercatoSrv(autBean, autBean.getGercodicefiscale(), escludiAutorizzazioniDateInAffitto));
	    }
	    return;
	}
	if (codiciFiscali.contains(autBean.getTitcodicefiscale())) {
	    ret.addAll(toAutorizzazioniMercatoSrv(autBean, autBean.getTitcodicefiscale(), escludiAutorizzazioniDateInAffitto));
	}
	if (codiciFiscali.contains(autBean.getOcccodicefiscale())
		&& (!autBean.getTitcodicefiscale().equalsIgnoreCase(autBean.getOcccodicefiscale()))) {
	    ret.addAll(toAutorizzazioniMercatoSrv(autBean, autBean.getOcccodicefiscale(), escludiAutorizzazioniDateInAffitto));
	}
	if (codiciFiscali.contains(autBean.getGercodicefiscale())
		&& (!autBean.getGercodicefiscale().equalsIgnoreCase(autBean.getOcccodicefiscale()))) {
	    ret.addAll(toAutorizzazioniMercatoSrv(autBean, autBean.getGercodicefiscale(), escludiAutorizzazioniDateInAffitto));
	}
    }

    public static void main(String[] args) {

	List<AutorizzazioniMercatoSrv> auts = new ArrayList<AutorizzazioniMercatoSrv>();
	auts.addAll(new ArrayList<AutorizzazioniMercatoSrv>());
	System.out.println(auts.size());
	for (AutorizzazioniMercatoSrv autorizzazioniMercatoSrv : auts) {
	    System.out.println("==>" + autorizzazioniMercatoSrv);
	}
    }

    private List<AutorizzazioniMercatoSrv> toAutorizzazioniMercatoSrv(AutorizzazioniMercatoSrvBean autBean, String cfriferimento,
	    boolean escludiAutorizzazioniDateInAffitto) {

	List<AutorizzazioniMercatoSrv> auts = new ArrayList<AutorizzazioniMercatoSrv>();
	AutorizzazioniMercatoSrv ret = new AutorizzazioniMercatoSrv();
	ret.setComuneRilascio(autBean.getComune());
	ret.setDataRilascio(autBean.getAutdata());
	ret.setDataCessazione(autBean.getDatacessazione());
	ret.setId(autBean.getIdaut());
	ret.setNumero(autBean.getAutnum());
	ret.setTipologiaTitolo(getTipologiaTitolo(StringUtils.isNotBlank(autBean.getCodiceposteggio())));
	ret.setStato(statoAutorizzazione(autBean));
	RuoloAutorizzazioneEnum individuaRuoloENominativo = individuaRuoloENominativo(autBean, ret, cfriferimento);
	if (escludiAutorizzazioniDateInAffitto && individuaRuoloENominativo.equals(RuoloAutorizzazioneEnum.DataInAffitto)) {
	    //	    nel pdf non devono comparire le autorizzazioni DATE IN GERENZA ma solamente quelle per le quali si ha titolarità 
	    // (ossia con Ruolo: "Proprietario di autorizzazioni non date in gerenza" e "Prese in gerenza" )
	    //	    Qualora nell' elenco risulti una sola autorizzazione data in gerenza, non è un problema stampare un pdf vuoto, senza autorizzazioni.
	    //	    Servizio Mercatosrv - Anagrafica autorizzazioni
	    //	    come per il pdf, non bisogna estrarre le autorizzazioni DATE IN GERENZA ma solamente quelle per le quali si ha titolarità 
	    // (ossia con Ruolo: "Proprietario di autorizzazioni non date in gerenza" e "Prese in gerenza" )
	    return new ArrayList<AutorizzazioniMercatoSrv>(0);
	}
	if (StringUtils.isNotBlank(autBean.getMercato())) {
	    AreaPubblica ap = new AreaPubblica();
	    ap.setDenominazione(descrizioneMercato(autBean.getMercato(), autBean.getUso()));
	    ap.setGiorno(autBean.getGiorno());
	    ap.setPosteggio(autBean.getCodiceposteggio());
	    ap.setTipologia(autBean.getTipomanif());
	    ret.setAreaPubblica(ap);
	}
	boolean aggiungiQuesta = true;
	// se presenti record in autorizzazioniCSI allora devo anche verificare se presente il posteggio
	if (StringUtils.isBlank(autBean.getMercato())) {
	    List<PosteggioPerAutBean> list = posteggiSganciatiPeraut.get(autBean.getIdaut());
	    if (list != null && !list.isEmpty()) {
		aggiungiQuesta = false; // non aggiungo il template 
		// faccio il controllo solamente per le autorizzazioni senza posteggio potrebbero essere state sganciate 
		// dalla componente IMPORTSIAP
		// verifica se è autorizzazione sganciata da CSI per popolare i dati di mercato , posteggio , giorno
		for (PosteggioPerAutBean posteggioPerAutBean : list) {
		    auts.add(duplicaDaTemplateConPosteggio(ret, posteggioPerAutBean));
		}
	    }
	}
	if (aggiungiQuesta) {
	    auts.add(ret);
	}
	return auts;
    }

    private AutorizzazioniMercatoSrv duplicaDaTemplateConPosteggio(AutorizzazioniMercatoSrv template, PosteggioPerAutBean posteggioPerAutBean) {

	AutorizzazioniMercatoSrv ret = AutorizzazioniMercatoSrv.fromTemplate(template);
	if (ret.getAreaPubblica() == null) {
	    ret.setAreaPubblica(new AreaPubblica());
	}
	ret.getAreaPubblica().setDenominazione(descrizioneMercato(posteggioPerAutBean.getMercato(), posteggioPerAutBean.getUso()));
	ret.getAreaPubblica().setGiorno(posteggioPerAutBean.getGiorno());
	ret.getAreaPubblica().setPosteggio(posteggioPerAutBean.getCodiceposteggio());
	ret.getAreaPubblica().setTipologia(posteggioPerAutBean.getTipomanif());
	ret.setTipologiaTitolo(getTipologiaTitolo(true));
	return ret;
    }

    private String descrizioneMercato(String mercato, String uso) {

	return Utilities.replaceDescrizioneGiornoDaUso(mercato, uso);
    }

    private RuoloAutorizzazioneEnum individuaRuoloENominativo(AutorizzazioniMercatoSrvBean autBean, AutorizzazioniMercatoSrv ret,
	    String cfriferimento) {

	ret.setCodiceFiscale(cfriferimento);
	RuoloAutorizzazioneEnum ruoloIndividuato = null;
	if (cfriferimento.equalsIgnoreCase(autBean.getTitcodicefiscale())) {
	    // proprietario	
	    ret.setNominativo(autBean.getTitnominativo());
	    if (cfriferimento.equalsIgnoreCase(autBean.getOcccodicefiscale())) {
		ruoloIndividuato = RuoloAutorizzazioneEnum.TitolareEOccupante;
		ret.setRuolo(etichette.get(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_PROPRIETARIO_AUT));
	    } else {
		ruoloIndividuato = RuoloAutorizzazioneEnum.DataInAffitto;
		ret.setRuolo(etichette.get(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_PROPRIETARIO_AUT_IN_AFFITTO));
	    }
	} else if (cfriferimento.equalsIgnoreCase(autBean.getOcccodicefiscale())) {
	    //affittuario
	    ret.setNominativo(autBean.getOccnominativo());
	    ruoloIndividuato = RuoloAutorizzazioneEnum.PresaInAffitto;
	    ret.setRuolo(etichette.get(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_AFFITTUARIO)); // HO ESCLUSO I RECORD DOVE OCCUPANTE==TITOLARE
										       //PER CUI DEVEE ESSERE PER FORZA AFFITTUARIO
	} else if (cfriferimento.equalsIgnoreCase(autBean.getGercodicefiscale())) {
	    //affittuario == gerente
	    ret.setNominativo(autBean.getGernominativo());
	    ruoloIndividuato = RuoloAutorizzazioneEnum.PresaInAffitto;
	    ret.setRuolo(etichette.get(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_AFFITTUARIO));
	} else {
	    log.warn("Non sono riuscito a ricavare le info del nominativo e ruolo da {} e {}", autBean, cfriferimento);
	    ret.setNominativo(autBean.getTitnominativo());
	    ruoloIndividuato = RuoloAutorizzazioneEnum.TitolareEOccupante;
	    ret.setRuolo(etichette.get(ETICHETTE_MERCATI_SRV_BEAN.RUOLO_PROPRIETARIO_AUT));
	}
	ret.setRuoloEnum(ruoloIndividuato);
	return ruoloIndividuato;
    }

    private String statoAutorizzazione(AutorizzazioniMercatoSrvBean autBean) {

	//	i f (S t r i n g U t i l s . i s N o t B l a n k ( a u t B e a n . g e t S t at o a u t o r i z ( ) ) ) {
	//	    return autBean.getStatoautoriz();
	//	}
	if (BooleanUtils.isTrue(autBean.getFlagattiva())) {
	    return etichette.get(ETICHETTE_MERCATI_SRV_BEAN.STATO_AUTORIZZAZIONE_ATTIVA);
	}
	return etichette.get(ETICHETTE_MERCATI_SRV_BEAN.STATO_AUTORIZZAZIONE_NON_ATTIVA);
    }

    private String getTipologiaTitolo(boolean isPosteggio) {

	if (isPosteggio) {
	    return etichette.get(ETICHETTE_MERCATI_SRV_BEAN.TIPOLOGIA_TITOLO_CON_POSTEGGIO);
	}
	return etichette.get(ETICHETTE_MERCATI_SRV_BEAN.TIPOLOGIA_TITOLO_ITINERANTE);
    }

    public List<AutorizzazioniMercatoSrv> elaboraAutorizzazioni(boolean escludiAutorizzazioniDateInAffitto) {

	List<AutorizzazioniMercatoSrv> ret = new ArrayList<AutorizzazioniMercatoSrv>();
	for (AutorizzazioniMercatoSrvBean autorizzazioniMercatoSrv : autsTrovate) {
	    checkAutorizzazioneMercatoSrv(ret, autorizzazioniMercatoSrv, escludiAutorizzazioniDateInAffitto);
	}
	return ret;
    }
}
