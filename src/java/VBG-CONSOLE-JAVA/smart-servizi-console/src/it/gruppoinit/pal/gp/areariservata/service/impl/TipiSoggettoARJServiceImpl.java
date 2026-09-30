package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.TipiSoggettoARJService;
import it.gruppoinit.pal.gp.areariservata.web.util.TipiSoggettoTipoAnagrafe;
import it.gruppoinit.pal.gp.areariservata.web.util.TipiSoggettoTipoDato;
import it.gruppoinit.pal.gp.core.dao.TipisoggettoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.TipisoggettoOrdineDescrizioneComparator;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipiSoggettoARJServiceImpl extends BaseServiceImpl<Tipisoggetto, PkId> implements TipiSoggettoARJService {

    @Autowired
    private TipisoggettoService tipisoggettoService;
    @Autowired
    private TipisoggettoDAO tipisoggettoDAO;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    private List<Tipisoggetto> findTipiSoggettoPerTipoAnagrafe(TipiSoggettoTipoAnagrafe tipoAnagrafe) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction utilizzo = new FilterRestriction();
	//filtro i tipisoggetti con utilizzo: F=frontoffice o null=frontoffice e backoffice
	utilizzo.addFilterField(FilterUtils.equals("utilizzo", "F", String.class));
	utilizzo.addFilterField(FilterUtils.isNull("utilizzo"));
	utilizzo.setAndOrRestriction(AndOrRestriction.OR);
	ft.addRestriction(utilizzo);
	FilterRestriction tipoAnag = new FilterRestriction();
	//filtro i tipisoggetti con tipoanagrafe: F=FISICA o null
	tipoAnag.addFilterField(FilterUtils.equals("tipoanagrafe", tipoAnagrafe.name(), String.class));
	tipoAnag.addFilterField(FilterUtils.isNull("tipoanagrafe"));
	tipoAnag.setAndOrRestriction(AndOrRestriction.OR);
	ft.addRestriction(tipoAnag);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tiposoggetto"));
	List<Tipisoggetto> list = tipisoggettoService.findByFilterTable(ft);
	return list;
    }

    private List<Tipisoggetto> findTipiSoggettoObbligatori() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction utilizzo = new FilterRestriction();
	//filtro i tipisoggetti con utilizzo: F=frontoffice o null=frontoffice e backoffice
	utilizzo.addFilterField(FilterUtils.equals("utilizzo", "F", String.class));
	utilizzo.addFilterField(FilterUtils.isNull("utilizzo"));
	utilizzo.setAndOrRestriction(AndOrRestriction.OR);
	ft.addRestriction(utilizzo);
	FilterRestriction tipoAnag = new FilterRestriction();
	tipoAnag.addFilterField(FilterUtils.equals("foObbligatorio", Boolean.TRUE, Boolean.class));
	ft.addRestriction(tipoAnag);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tiposoggetto"));
	List<Tipisoggetto> list = tipisoggettoService.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Tipisoggetto> findTipiSoggettoObbligatoriPerIntervento(Integer codiceIntervento) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceIntervento));
	List<Tipisoggetto> result = new ArrayList<Tipisoggetto>();
	if (alberoproc != null) {
	    AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(alberoproc, null);
	    if (helper != null) {
		Set<AlberoprocTipisoggetto> aps = helper.getAlberoprocTipisoggettos();
		if (aps.isEmpty()) {
		    List<Tipisoggetto> tsList = this.findTipiSoggettoObbligatori();
		    for (Tipisoggetto ts : tsList) {
			if (StringUtils.defaultString(ts.getUtilizzo(), "F").equalsIgnoreCase("F")) {
			    if (BooleanUtils.isTrue(ts.getFoObbligatorio())) {
				result.add(ts);
			    }
			}
		    }
		} else {
		    for (AlberoprocTipisoggetto ats : aps) {
			Tipisoggetto ts = ats.getTipisoggetto();
			if (ts != null) {
			    if (StringUtils.defaultString(ts.getUtilizzo(), "F").equalsIgnoreCase("F")) {
				if (BooleanUtils.isTrue(ts.getFoObbligatorio())) {
				    result.add(ts);
				}
			    }
			}
		    }
		}
	    }
	}
	Collections.sort(result, new TipisoggettoOrdineDescrizioneComparator());
	return result;
    }

    @Override
    public List<Tipisoggetto> findTipiSoggettoPerInterventoAndTipoDato(Integer codiceIntervento, TipiSoggettoTipoDato tipoDato) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceIntervento));
	List<Tipisoggetto> result = new ArrayList<Tipisoggetto>();
	if (alberoproc != null) {
	    AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(alberoproc, null);
	    if (helper != null) {
		Set<AlberoprocTipisoggetto> aps = helper.getAlberoprocTipisoggettos();
		for (AlberoprocTipisoggetto ats : aps) {
		    Tipisoggetto ts = ats.getTipisoggetto();
		    if (ts != null) {
			if (StringUtils.defaultString(ts.getUtilizzo(), "F").equalsIgnoreCase("F")) {
			    if (StringUtils.defaultString(ts.getTipodato(), tipoDato.name()).equalsIgnoreCase(tipoDato.name())) {
				result.add(ts);
			    }
			}
		    }
		}
	    }
	}
	if (result.isEmpty()) {
	    return this.findTipiSoggettoPerTipoDato(tipoDato);
	}
	Collections.sort(result, new TipisoggettoOrdineDescrizioneComparator());
	return result;
    }

    @Override
    public List<Tipisoggetto> findTipiSoggettoPerInterventoAndTipoAnagrafe(Integer codiceIntervento, TipiSoggettoTipoAnagrafe tipoAnagrafe) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceIntervento));
	List<Tipisoggetto> result = new ArrayList<Tipisoggetto>();
	if (alberoproc != null) {
	    AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(alberoproc, null);
	    if (helper != null) {
		Set<AlberoprocTipisoggetto> aps = helper.getAlberoprocTipisoggettos();
		for (AlberoprocTipisoggetto ats : aps) {
		    Tipisoggetto ts = ats.getTipisoggetto();
		    if (ts != null) {
			if (StringUtils.defaultString(ts.getUtilizzo(), "F").equalsIgnoreCase("F")) {
			    if (StringUtils.defaultString(ts.getTipoanagrafe(), tipoAnagrafe.name()).equalsIgnoreCase(tipoAnagrafe.name())) {
				result.add(ts);
			    }
			}
		    }
		}
	    }
	}
	if (result.isEmpty()) {
	    return this.findTipiSoggettoPerTipoAnagrafe(tipoAnagrafe);
	}
	Collections.sort(result, new TipisoggettoOrdineDescrizioneComparator());
	return result;
    }

    private List<Tipisoggetto> findTipiSoggettoPerTipoDato(TipiSoggettoTipoDato tipoDato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction utilizzo = new FilterRestriction();
	//filtro i tipisoggetti con utilizzo: F=frontoffice o null=frontoffice e backoffice
	utilizzo.addFilterField(FilterUtils.equals("utilizzo", "F", String.class));
	utilizzo.addFilterField(FilterUtils.isNull("utilizzo"));
	utilizzo.setAndOrRestriction(AndOrRestriction.OR);
	ft.addRestriction(utilizzo);
	FilterRestriction tipoAnag = new FilterRestriction();
	tipoAnag.addFilterField(FilterUtils.equals("tipodato", tipoDato.name(), String.class));
	ft.addRestriction(tipoAnag);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tiposoggetto"));
	List<Tipisoggetto> list = tipisoggettoService.findByFilterTable(ft);
	return list;
    }

    @Override
    public void delete(Tipisoggetto arg0) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Tipisoggetto> findAll(Integer arg0, Integer arg1) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Tipisoggetto findById(PkId arg0) {

	return tipisoggettoDAO.findById(arg0);
    }

    @Override
    public void insert(Tipisoggetto arg0) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(Tipisoggetto arg0) {

	// TODO Auto-generated method stub
    }

    @Override
    protected Class<Tipisoggetto> getEntityClass() {

	return Tipisoggetto.class;
    }
}
