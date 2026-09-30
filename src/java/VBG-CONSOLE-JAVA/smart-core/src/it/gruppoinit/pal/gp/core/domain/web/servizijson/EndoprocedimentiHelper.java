package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.helper.InventarioprocedimentiWrapper;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class EndoprocedimentiHelper {

    private InventarioprocedimentiService inventarioprocedimentiService;
    private InventarioprocEndoService inventarioprocEndoService;
    private List<FamiglieEndoBean> endoprocedimenti = new ArrayList<FamiglieEndoBean>();
    private Map<Integer, Set<Integer>> famiglie = new HashMap<Integer, Set<Integer>>();
    private Map<Integer, Set<EndoprocedimentoSimpleBean>> tipologie = new HashMap<Integer, Set<EndoprocedimentoSimpleBean>>();
    private Map<Integer, FamiglieEndoBean> famiglieEndo = new HashMap<Integer, FamiglieEndoBean>();
    private Map<Integer, TipologiaEndoBean> tipologieEndo = new HashMap<Integer, TipologiaEndoBean>();

    private EndoprocedimentiHelper() {

	super();
    }

    public EndoprocedimentiHelper(InventarioprocedimentiService inventarioprocedimentiService, InventarioprocEndoService inventarioprocEndoService) {

	this();
	this.inventarioprocedimentiService = inventarioprocedimentiService;
	this.inventarioprocEndoService = inventarioprocEndoService;
    }

    public InventarioprocedimentiService getInventarioprocedimentiService() {

	return inventarioprocedimentiService;
    }

    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    public List<FamiglieEndoBean> elaboraEndo(Set<InventarioprocedimentiWrapper> endos, Set<String> codiciPrincipale, String codiceComune) {

	endoprocedimenti = new ArrayList<FamiglieEndoBean>();
	famiglie = new HashMap<Integer, Set<Integer>>();
	tipologie = new HashMap<Integer, Set<EndoprocedimentoSimpleBean>>();
	famiglieEndo = new HashMap<Integer, FamiglieEndoBean>();
	tipologieEndo = new HashMap<Integer, TipologiaEndoBean>();
	for (InventarioprocedimentiWrapper ip : endos) {
	    Integer codiceTipoendo = -1;
	    String tipiEndoDescrizione = "ENDOPROCEDIMENTI";
	    Integer tipiEndoOrdine = -1;
	    if (ip.getIp().getTipoendo() != null) {
		codiceTipoendo = ip.getIp().getTipoendo().getId().getCodice();
		tipiEndoDescrizione = ip.getIp().getTipoendo().getTipo();
		tipiEndoOrdine = ip.getIp().getTipoendo().getOrdine() == null ? 0 : ip.getIp().getTipoendo().getOrdine();
	    }
	    Set<EndoprocedimentoSimpleBean> endo = tipologie.get(codiceTipoendo);
	    if (endo == null) {
		endo = new HashSet<EndoprocedimentoSimpleBean>();
	    }
	    EndoprocedimentoSimpleBean e = new EndoprocedimentoSimpleBean();
	    e.setId(ip.getId().getCodice());
	    e.setIdcomune(ip.getId().getIdcomune());
	    e.setNome(ip.getIp().getProcedimento());
	    e.setIntervento(ip.isIntervento());
	    if (StringUtils.isNotBlank(ip.getOvverrideDescrizione())) {
		e.setNome(ip.getOvverrideDescrizione());
	    }
	    e.setOrdine(ip.getIp().getOrdine() == null ? 0 : ip.getIp().getOrdine().intValue());
	    e.setPrincipale(Boolean.FALSE);
	    String codiceRiferimento = inventarioprocedimentiService.getEndoprocedimentoKey(ip.getIp());
	    if (codiciPrincipale != null) {
		if (codiciPrincipale.contains(codiceRiferimento)) {
		    e.setPrincipale(Boolean.TRUE);
		}
	    }
	    if (ORMHelper.getIdcomunebase().equalsIgnoreCase(ip.getId().getIdcomune())) {
		e.setRegionale(Boolean.TRUE);
	    } else {
		e.setRegionale(Boolean.FALSE);
	    }
	    List<InventarioprocEndo> s = inventarioprocEndoService.findByInventarioprocT(ip.getId().getIdcomune(), ip.getId().getCodice(),
		    Boolean.TRUE, null, null, codiceComune, true);
	    if (s != null) {
		List<EndoprocedimentoSimpleBean> lsub = new ArrayList<EndoprocedimentoSimpleBean>();
		for (InventarioprocEndo inventarioprocEndo : s) {
		    EndoprocedimentoSimpleBean es = new EndoprocedimentoSimpleBean();
		    es.setId(inventarioprocEndo.getInventarioprocEndoD().getId().getCodice());
		    es.setIdcomune(inventarioprocEndo.getInventarioprocEndoD().getId().getIdcomune());
		    es.setNome(inventarioprocEndo.getInventarioprocEndoD().getProcedimento());
		    es.setOrdine(inventarioprocEndo.getInventarioprocEndoD().getOrdine() == null ? 0 : inventarioprocEndo.getInventarioprocEndoD()
			    .getOrdine().intValue());
		    es.setPrincipale(Boolean.FALSE);
		    String codiceRiferimento2 = inventarioprocedimentiService.getEndoprocedimentoKey(inventarioprocEndo.getInventarioprocEndoD());
		    if (codiciPrincipale != null) {
			if (codiciPrincipale.contains(codiceRiferimento2)) {
			    es.setPrincipale(Boolean.TRUE);
			}
		    }
		    if (ORMHelper.getIdcomunebase().equalsIgnoreCase(inventarioprocEndo.getInventarioprocEndoD().getId().getIdcomune())) {
			es.setRegionale(Boolean.TRUE);
		    } else {
			es.setRegionale(Boolean.FALSE);
		    }
		    lsub.add(es);
		}
		if (lsub.size() > 0) {
		    e.setProcedimentiCollegati(lsub);
		}
	    }
	    endo.add(e);
	    TipologiaEndoBean tipiEndo = new TipologiaEndoBean();
	    tipiEndo.setId(codiceTipoendo);
	    tipiEndo.setNome(tipiEndoDescrizione);
	    tipiEndo.setOrdine(tipiEndoOrdine);
	    tipiEndo.setIntervento(e.isIntervento());
	    tipologieEndo.put(codiceTipoendo, tipiEndo);
	    tipologie.put(codiceTipoendo, endo);
	    Integer famigliaEndoCodice = -1;
	    String famigliaEndoDescrizione = "ENDOPROCEDIMENTI";
	    Integer famigliaEndoOrdine = -1;
	    if (ip.getIp().getTipoendo() != null) {
		if (ip.getIp().getTipoendo().getTipifamiglieendo() != null) {
		    famigliaEndoCodice = ip.getIp().getTipoendo().getTipifamiglieendo().getId().getCodice();
		    famigliaEndoDescrizione = ip.getIp().getTipoendo().getTipifamiglieendo().getTipo();
		    famigliaEndoOrdine = ip.getIp().getTipoendo().getTipifamiglieendo().getOrdine() == null ? 0 : ip.getIp().getTipoendo()
			    .getTipifamiglieendo().getOrdine();
		}
	    }
	    FamiglieEndoBean famigliaEndo = new FamiglieEndoBean();
	    famigliaEndo.setId(famigliaEndoCodice);
	    famigliaEndo.setFamiglia(famigliaEndoDescrizione);
	    famigliaEndo.setOrdine(famigliaEndoOrdine);
	    famigliaEndo.setIntervento(e.isIntervento());
	    famiglieEndo.put(famigliaEndoCodice, famigliaEndo);
	    Set<Integer> codiciTipiEndoFamiglia = famiglie.get(famigliaEndoCodice);
	    if (codiciTipiEndoFamiglia == null) {
		codiciTipiEndoFamiglia = new HashSet<Integer>();
	    }
	    codiciTipiEndoFamiglia.add(codiceTipoendo);
	    famiglie.put(famigliaEndoCodice, codiciTipiEndoFamiglia);
	}
	TipologiaEndoComparator teComp = new TipologiaEndoComparator();
	EndoprocedimentoSimpleBeanComparator epsc = new EndoprocedimentoSimpleBeanComparator();
	for (Map.Entry<Integer, Set<Integer>> famiglia : famiglie.entrySet()) {
	    Integer codiceFamiglia = famiglia.getKey();
	    Set<Integer> tipiEndo = famiglia.getValue();
	    FamiglieEndoBean f = famiglieEndo.get(codiceFamiglia);
	    FamiglieEndoBean fcopy = new FamiglieEndoBean();
	    fcopy.setFamiglia(f.getFamiglia());
	    fcopy.setId(f.getId());
	    fcopy.setOrdine(f.getOrdine());
	    fcopy.setIntervento(f.isIntervento());
	    List<TipologiaEndoBean> teb = new ArrayList<TipologiaEndoBean>();
	    // for (Map.Entry<Integer, Set<EndoprocedimentoSimpleBean>> tipoE : tipiEndo.entrySet()) {
	    for (Integer codiceTipologiaEndo : tipiEndo) {
		// Integer codiceTipologiaEndo = tipoE.getKey();
		Set<EndoprocedimentoSimpleBean> endoprocs = tipologie.get(codiceTipologiaEndo);
		TipologiaEndoBean t = tipologieEndo.get(codiceTipologiaEndo);
		TipologiaEndoBean tcopy = new TipologiaEndoBean();
		tcopy.setId(t.getId());
		tcopy.setNome(t.getNome());
		tcopy.setOrdine(t.getOrdine());
		tcopy.setIntervento(t.isIntervento());
		List<EndoprocedimentoSimpleBean> endops = new ArrayList<EndoprocedimentoSimpleBean>();
		for (EndoprocedimentoSimpleBean e : endoprocs) {
		    EndoprocedimentoSimpleBean copy = new EndoprocedimentoSimpleBean();
		    copy.setId(e.getId());
		    copy.setNome(e.getNome());
		    copy.setOrdine(e.getOrdine());
		    copy.setPrincipale(e.getPrincipale());
		    copy.setRegionale(e.getRegionale());
		    copy.setProcedimentiCollegati(e.getProcedimentiCollegati());
		    copy.setIntervento(e.isIntervento());
		    endops.add(copy);
		}
		Collections.sort(endops, epsc);
		tcopy.setEndoprocedimenti(endops);
		teb.add(tcopy);
	    }
	    Collections.sort(teb, teComp);
	    fcopy.setTipologie(teb);
	    endoprocedimenti.add(fcopy);
	}
	Collections.sort(endoprocedimenti, new FamiglieEndoComparator());
	return endoprocedimenti;
    }

    public EndoprocedimentoSimpleBean elaboraIntervento(AlberoprocEndoLoc ape, String codiceComune) {

	EndoprocedimentoSimpleBean e = new EndoprocedimentoSimpleBean();
	e.setId(ape.getInventarioprocedimenti().getId().getCodice());
	e.setIdcomune(ape.getInventarioprocedimenti().getId().getIdcomune());
	e.setNome(ape.getInventarioprocedimenti().getProcedimento());
	if (StringUtils.isNotBlank(StringUtils.defaultString(ape.getDescrizione()).trim())) {
	    e.setNome(ape.getDescrizione());
	}
	e.setOrdine(ape.getInventarioprocedimenti().getOrdine() == null ? 0 : ape.getInventarioprocedimenti().getOrdine().intValue());
	e.setPrincipale(Boolean.FALSE);
	e.setPrincipale(Boolean.FALSE);
	if (ORMHelper.getIdcomunebase().equalsIgnoreCase(ape.getInventarioprocedimenti().getId().getIdcomune())) {
	    e.setRegionale(Boolean.TRUE);
	} else {
	    e.setRegionale(Boolean.FALSE);
	}
	List<InventarioprocEndo> s = inventarioprocEndoService.findByInventarioprocT(ape.getInventarioprocedimenti().getId().getIdcomune(), ape
		.getInventarioprocedimenti().getId().getCodice(), Boolean.TRUE, null, null, codiceComune, true);
	if (s != null) {
	    List<EndoprocedimentoSimpleBean> lsub = new ArrayList<EndoprocedimentoSimpleBean>();
	    for (InventarioprocEndo inventarioprocEndo : s) {
		EndoprocedimentoSimpleBean es = new EndoprocedimentoSimpleBean();
		es.setId(inventarioprocEndo.getInventarioprocEndoD().getId().getCodice());
		es.setIdcomune(inventarioprocEndo.getInventarioprocEndoD().getId().getIdcomune());
		es.setNome(inventarioprocEndo.getInventarioprocEndoD().getProcedimento());
		es.setOrdine(inventarioprocEndo.getInventarioprocEndoD().getOrdine() == null ? 0 : inventarioprocEndo.getInventarioprocEndoD()
			.getOrdine().intValue());
		es.setPrincipale(Boolean.FALSE);
		es.setPrincipale(Boolean.FALSE);
		if (ORMHelper.getIdcomunebase().equalsIgnoreCase(inventarioprocEndo.getInventarioprocEndoD().getId().getIdcomune())) {
		    es.setRegionale(Boolean.TRUE);
		} else {
		    es.setRegionale(Boolean.FALSE);
		}
		lsub.add(es);
	    }
	    if (lsub.size() > 0) {
		e.setProcedimentiCollegati(lsub);
	    }
	}
	return e;
    }
}
