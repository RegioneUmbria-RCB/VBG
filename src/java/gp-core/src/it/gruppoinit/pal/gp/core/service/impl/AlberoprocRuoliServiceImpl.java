/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocRuoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AlberoProcRuoliBean;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoliId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocRuoliService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliruoliService;

/**
 * @author francescop
 * 
 */
@Service
public class AlberoprocRuoliServiceImpl extends BaseServiceImpl<AlberoprocRuoli, AlberoprocRuoliId> implements AlberoprocRuoliService {

    private AlberoprocRuoliDAO alberoprocRuoliDAO;
    private ResponsabiliruoliService responsabiliruoliService;

    @Autowired
    public void setResponsabiliruoliService(ResponsabiliruoliService responsabiliruoliService) {

	this.responsabiliruoliService = responsabiliruoliService;
    }

    @Autowired
    public void setAlberoprocRuoliDAO(AlberoprocRuoliDAO alberoprocRuoliDAO) {

	this.alberoprocRuoliDAO = alberoprocRuoliDAO;
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_ALBEROPROCRUOLI" })
    public void delete(AlberoprocRuoli entity) {

	alberoprocRuoliDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ALBEROPROCRUOLI" })
    public List<AlberoprocRuoli> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocRuoliDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_ALBEROPROCRUOLI" })
    public AlberoprocRuoli findById(AlberoprocRuoliId id) {

	return alberoprocRuoliDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_ALBEROPROCRUOLI" })
    public void insert(AlberoprocRuoli entity) {

	if (validateEntity(entity)) {
	    alberoprocRuoliDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_ALBEROPROCRUOLI" })
    public void update(AlberoprocRuoli entity) {

	if (validateEntity(entity)) {
	    alberoprocRuoliDAO.update(entity);
	}
    }

    @Override
    protected Class<AlberoprocRuoli> getEntityClass() {

	return AlberoprocRuoli.class;
    }

    @Override
    public List<AlberoprocRuoli> findByAlberoprocId(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkScId", codice, Integer.class));
	ft.addRestriction(fr);
	return alberoprocRuoliDAO.findByFilterTable(ft);
    }

    @Override
    public void deleteByAlberoprocId(Integer codice) {

	List<AlberoprocRuoli> list = this.findByAlberoprocId(codice);
	for (AlberoprocRuoli alberoprocRuoli : list) {
	    this.delete(alberoprocRuoli);
	}
    }

    @Override
    public Set<Integer> trovaVociPerRuoliDelResponsabile(Integer codiceResponsabile) {

	List<Integer> ruoliR = responsabiliruoliService.findCodiciRuoloByResponsabile(codiceResponsabile);
	if (ruoliR.isEmpty()) {
	    return new HashSet<Integer>();
	}
	Set<Integer> idRuoliOperatore = new HashSet<Integer>();
	idRuoliOperatore.addAll(ruoliR);
	List<AlberoProcRuoliBean> list = alberoprocRuoliDAO.findRuoliPerVoce();
	Map<String, Set<Integer>> m = new HashMap<String, Set<Integer>>();
	// inserisco in mappa
	for (AlberoProcRuoliBean ar : list) {
	    Set<Integer> r = m.get(ar.getPercorso());
	    if (r == null) {
		r = new HashSet<Integer>();
	    }
	    if (ar.getIdruolo() != null) {
		r.add(ar.getIdruolo());
	    }
	    m.put(ar.getPercorso(), r);
	}
	Set<Integer> result = new HashSet<Integer>();
	// ciclo gli oggetti e verifico dalla mappa come inserire 
	for (AlberoProcRuoliBean ar : list) {
	    String scCodice = ar.getPercorso();
	    Integer scId = ar.getCodice();
	    aggiungiRuoli(idRuoliOperatore, m, result, scCodice, scId);
	    if (scCodice.length() > 2) {
		boolean continua = true;
		while (continua) {
		    scCodice = scCodice.substring(0, scCodice.length() - 2);
		    aggiungiRuoli(idRuoliOperatore, m, result, scCodice, scId);
		    if (scCodice.length() == 2) {
			continua = false;
		    }
		}
	    }
	}
	return result;
    }

    private void aggiungiRuoli(Set<Integer> idRuoli, Map<String, Set<Integer>> m, Set<Integer> result, String scCodice, Integer scId) {

	Set<Integer> ruoli = m.get(scCodice);
	if (!(ruoli == null || ruoli.isEmpty())) {
	    for (Integer r : ruoli) {
		if (idRuoli.contains(r)) {
		    result.add(scId);
		}
	    }
	}
    }

    @Override
    public boolean isAssegnabilePerRuoloDelResponsabile(Integer codiceAlberoproc, Integer codiceResponsabile) {

	Set<Integer> vociAlbero = this.trovaVociPerRuoliDelResponsabile(codiceResponsabile);
	if (vociAlbero.isEmpty()) {
	    return false;
	}
	return vociAlbero.contains(codiceAlberoproc);
    }
}
