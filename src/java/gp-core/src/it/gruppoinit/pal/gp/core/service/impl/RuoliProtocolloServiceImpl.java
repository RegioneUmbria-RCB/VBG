package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.RuoliProtocolloDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RuoliProtocollo;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniconfigurazioniHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.RuoliProtocolloService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.RuoliProtocolloHelper;

@Service
public class RuoliProtocolloServiceImpl extends BaseServiceImpl<RuoliProtocollo, PkId> implements RuoliProtocolloService {

    private RuoliProtocolloDAO ruoliProtocolloDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private ComuniService comuniService;
    private SoftwareService softwareService;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setRuoliProtocolloDAO(RuoliProtocolloDAO ruoliProtocolloDAO) {

	this.ruoliProtocolloDAO = ruoliProtocolloDAO;
    }

    @Override
    public void insert(RuoliProtocollo entity) {

	if (validateEntity(entity)) {
	    ruoliProtocolloDAO.insert(entity);
	}
    }

    @Override
    public void update(RuoliProtocollo entity) {

	if (validateEntity(entity)) {
	    ruoliProtocolloDAO.update(entity);
	}
    }

    @Override
    public void delete(RuoliProtocollo entity) {

	if (isDeleteAllowed(entity)) {
	    ruoliProtocolloDAO.delete(entity);
	}
    }

    @Override
    public List<RuoliProtocollo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("Non implementato");
    }

    @Override
    public RuoliProtocollo findById(PkId id) {

	return ruoliProtocolloDAO.findById(id);
    }

    @Override
    protected Class<RuoliProtocollo> getEntityClass() {

	return RuoliProtocollo.class;
    }

    @Override
    public List<RuoliProtocollo> findByIdRuolo(Integer idRuolo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("ruoliId", idRuolo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ruoloExt"));
	return ruoliProtocolloDAO.findByFilterTable(ft);
    }

    @Override
    public List<RuoliProtocolloHelper> findHelperByRuolo(Integer idRuolo) {

	List<RuoliProtocolloHelper> h = new ArrayList<RuoliProtocolloHelper>();
	List<VerticalizzazioniconfigurazioniHelper> s = verticalizzazioniService.findListaConfigurazioniPerComuneESoftware(
		WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER, WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_URL_LOGIN);
	Map<String, List<CodiceDescrizioneBean>> mcs = new HashMap<String, List<CodiceDescrizioneBean>>();
	String parametroreq = StringUtils.defaultString(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_URL_LOGIN);
	for (VerticalizzazioniconfigurazioniHelper vp : s) {
	    String comune = "";
	    String software = "";
	    if (StringUtils.isBlank(parametroreq) || vp.getParametro().equalsIgnoreCase(parametroreq)) {
		if (vp.getComune() != null) {
		    comune = vp.getComune().getCodicecomune();
		}
		if (vp.getSoftware() != null) {
		    software = vp.getSoftware().getCodice();
		}
	    }
	    String key = comune;
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice(comune);
	    cdb.setDescrizione(software);
	    List<CodiceDescrizioneBean> cdbs;
	    if (mcs.get(key) == null) {
		cdbs = new ArrayList<CodiceDescrizioneBean>();
	    } else {
		cdbs = mcs.get(key);
	    }
	    cdbs.add(cdb);
	    mcs.put(key, cdbs);
	}
	if (!mcs.isEmpty()) {
	    for (Entry<String, List<CodiceDescrizioneBean>> eee : mcs.entrySet()) {
		String comune = eee.getKey();
		List<CodiceDescrizioneBean> cdbs = eee.getValue();
		RuoliProtocolloHelper rph = new RuoliProtocolloHelper();
		Comuni com = comuniService.findById(comune);
		List<ChiaveValoreBean<Software, String>> ppp = new ArrayList<ChiaveValoreBean<Software, String>>();
		for (CodiceDescrizioneBean cd : cdbs) {
		    String codiceSoftware = cd.getDescrizione();
		    Software sft = softwareService.findById(codiceSoftware);
		    ChiaveValoreBean<Software, String> reco = new ChiaveValoreBean<Software, String>();
		    reco.setChiave(sft);
		    String valore = "";
		    List<RuoliProtocollo> prs = this.findByIdRuoloAndComuneAndSoftware(idRuolo, comune, codiceSoftware);
		    if (prs.size() > 0) {
			valore = prs.get(0).getRuoloExt();
		    }
		    reco.setValore(valore);
		    ppp.add(reco);
		}
		rph.setCvb(ppp);
		rph.setComune(com);
		h.add(rph);
	    }
	}
	return h;
    }

    @Override
    public List<RuoliProtocollo> findByIdRuoloAndComuneAndSoftware(Integer idRuolo, String codiceComune, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("ruoliId", idRuolo, Integer.class));
	if (StringUtils.isBlank(codiceComune)) {
	    FilterRestriction com = new FilterRestriction();
	    com.setAndOrRestriction(AndOrRestriction.OR);
	    com.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    com.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    ft.addRestriction(com);
	} else {
	    fr.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	}
	fr.addFilterField(FilterUtils.equals("software.codice", software, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ruoloExt"));
	return ruoliProtocolloDAO.findByFilterTable(ft);
    }

    @Override
    public void insertRuoliProtocollo(Integer idRuolo, List<RuoliProtocollo> rps) {

	if (rps != null && rps.size() > 0) {
	    for (RuoliProtocollo ruoliProtocollo : rps) {
		String valore = ruoliProtocollo.getRuoloExt();
		Software s = ruoliProtocollo.getSoftware();
		Comuni c = ruoliProtocollo.getComune();
		String comune = null;
		if (c != null) {
		    comune = c.getCodicecomune();
		}
		List<RuoliProtocollo> l = this.findByIdRuoloAndComuneAndSoftware(idRuolo, comune, s.getCodice());
		if (l != null && l.size() > 0) {
		    for (RuoliProtocollo rp : l) {
			// UPDATE
			if (StringUtils.isNotBlank(valore)) {
			    rp.setRuoloExt(valore);
			    this.update(rp);
			} else {
			    this.delete(rp);
			}
		    }
		} else {
		    //INSERT 
		    this.insert(ruoliProtocollo);
		}
	    }
	}
    }
}
