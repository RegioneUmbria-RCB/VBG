package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.VwIstanzesoggetticollegatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.VwIstanzesoggetticollegatiHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegati;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegatiId;
import it.gruppoinit.pal.gp.core.domain.helper.VwIstanzesoggetticollegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.VwIstanzesoggetticollegatiService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VwIstanzesoggetticollegatiServiceImpl extends BaseServiceImpl<VwIstanzesoggetticollegati, VwIstanzesoggetticollegatiId>
	implements VwIstanzesoggetticollegatiService {

    private VwIstanzesoggetticollegatiDAO vwistanzesoggetticollegatiDAO;
    private SoftwareService softwareService;
    private IstanzestradarioService istanzestradarioService;
    private IstanzeService istanzeService;

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setVwIstanzesoggetticollegatiDAO(VwIstanzesoggetticollegatiDAO vwistanzesoggetticollegatiDAO) {

	this.vwistanzesoggetticollegatiDAO = vwistanzesoggetticollegatiDAO;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Override
    protected Class<VwIstanzesoggetticollegati> getEntityClass() {

	return VwIstanzesoggetticollegati.class;
    }

    @Override
    public List<VwIstanzesoggetticollegati> findAll(Integer firstResult, Integer maxResult) {

	return vwistanzesoggetticollegatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(VwIstanzesoggetticollegati entity) {

	throw new NotImplementedException();
    }

    @Override
    public VwIstanzesoggetticollegati findById(VwIstanzesoggetticollegatiId id) {

	return vwistanzesoggetticollegatiDAO.findById(id);
    }

    @Override
    public void update(VwIstanzesoggetticollegati entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwIstanzesoggetticollegati entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwIstanzesoggetticollegatiHelper> findByRichiedenteAndGroupBySoftware(Anagrafe richiedente) {

	List<VwIstanzesoggetticollegatiHelper> risultato = new ArrayList<VwIstanzesoggetticollegatiHelper>();
	List<Software> listSoftware = softwareService.findSoftwareAttivi(false);
	VwIstanzesoggetticollegatiHelper istanzesoggetticollegatiHelper = null;
	for (Software software : listSoftware) {
	    istanzesoggetticollegatiHelper = new VwIstanzesoggetticollegatiHelper();
	    List<VwIstanzesoggetticollegatiDTO> list = vwistanzesoggetticollegatiDAO.findByRichiedenteAndSoftware(richiedente, software);
	    Map<Integer, Integer> codiciIstanzaPresenti = new HashMap<Integer, Integer>();
	    if (!list.isEmpty()) {
		List<Istanze> istanzes = new ArrayList<Istanze>();
		for (VwIstanzesoggetticollegatiDTO vwIstanzesoggetticollegatiDTO : list) {
		    if (codiciIstanzaPresenti.get(vwIstanzesoggetticollegatiDTO.getCodiceistanza()) == null) {
			codiciIstanzaPresenti.put(vwIstanzesoggetticollegatiDTO.getCodiceistanza(), vwIstanzesoggetticollegatiDTO.getCodiceistanza());
			Istanze istanza = istanzeService.findById(new PkId(vwIstanzesoggetticollegatiDTO.getCodiceistanza()));
			// Ritorna la lista degli stradari mettendo in prima posizione il primario se esiste 
			List<Istanzestradario> listStradario = istanzestradarioService.findByIstanza(istanza.getId().getCodice());
			if (!listStradario.isEmpty()) {
			    istanza.setLocalizzazioneTransient(getLocalizzazioneIstanza(listStradario.get(0)));
			}
			istanzes.add(istanza);
		    }
		}
		istanzesoggetticollegatiHelper.setSoftware(software);
		istanzesoggetticollegatiHelper.setIstanzesoggetticollegatis(istanzes);
		risultato.add(istanzesoggetticollegatiHelper);
	    }
	}
	return risultato;
    }

    private String getLocalizzazioneIstanza(Istanzestradario istanzestradario) {

	String descrizioneLocalizzazione = "";
	Stradario stradario = istanzestradario.getStradario();
	if (stradario != null) {
	    descrizioneLocalizzazione = StringUtils.defaultIfEmpty(stradario.getPrefisso(), "");
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(StringUtils.defaultIfEmpty(stradario.getDescrizione(), ""));
	}
	if (StringUtils.isNotBlank(istanzestradario.getCivico())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(istanzestradario.getCivico());
	}
	if (StringUtils.isNotBlank(istanzestradario.getEsponente())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat("/").concat(istanzestradario.getEsponente());
	}
	if (EntityUtils.getNestedProperty(istanzestradario, "stradariocolore.id.codicecolore") != null) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(istanzestradario.getStradariocolore().getColore());
	}
	if (stradario != null) {
	    if (StringUtils.isNotBlank(stradario.getCap())) {
		descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" - ").concat(stradario.getCap());
	    }
	    if (StringUtils.isNotBlank(stradario.getLocfraz())) {
		descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" - ").concat(stradario.getLocfraz());
	    }
	}
	return descrizioneLocalizzazione;
    }

    @Override
    public List<VwIstanzesoggetticollegati> findByIstanza(Integer codiceistanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceistanza", codiceistanza, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codicerichiedente"));
	return vwistanzesoggetticollegatiDAO.findByFilterTable(ft);
    }
}
