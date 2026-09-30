/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocpeopleoperDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeopleoper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeopleoperService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Service
public class AlberoprocpeopleoperServiceImpl extends BaseServiceImpl<Alberoprocpeopleoper, PkId> implements AlberoprocpeopleoperService {

    private AlberoprocpeopleoperDAO alberoprocpeopleoperDAO;
    private AlberoprocService alberoprocService;
    private SoftwareService softwareService;

    @Autowired
    public void setAlberoprocpeopleoperDAO(AlberoprocpeopleoperDAO alberoprocpeopleoperDAO) {

	this.alberoprocpeopleoperDAO = alberoprocpeopleoperDAO;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<Alberoprocpeopleoper> getEntityClass() {

	return Alberoprocpeopleoper.class;
    }

    @Override
    public void delete(Alberoprocpeopleoper entity) {

	alberoprocpeopleoperDAO.delete(entity);
    }

    @Override
    public List<Alberoprocpeopleoper> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocpeopleoperDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Alberoprocpeopleoper findById(PkId id) {

	return alberoprocpeopleoperDAO.findById(id);
    }

    @Override
    public void insert(Alberoprocpeopleoper entity) {

	if (validateEntity(entity)) {
	    alberoprocpeopleoperDAO.insert(entity);
	}
    }

    @Override
    public void update(Alberoprocpeopleoper entity) {

	if (validateEntity(entity)) {
	    alberoprocpeopleoperDAO.update(entity);
	}
    }

    @Override
    public List<Alberoprocpeopleoper> findByAlberoProc(Alberoproc alberoproc) {

	return alberoprocpeopleoperDAO.findByAlberoProc(alberoproc);
    }

    @Override
    public List<Alberoprocpeopleoper> findByFilterTable(FilterTable filterTable) {

	return alberoprocpeopleoperDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Alberoprocpeopleoper> findBySettoreAndOperazioniNlaPeople(String settore, List<String> operazioni, String codiceSoftware,
	    boolean searchForSettore) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	// filtro per software
	//FIXME
	Software software = softwareService.findById(codiceSoftware);
	restriction.addFilterField(FilterUtils.equals("software", software, "alberoproc", Software.class));
	//verifico se devo ricercare anche per settore o no
	if (searchForSettore) {
	    restriction.addFilterField(FilterUtils.equals("settore", settore, String.class));
	}
	// filtro per tutta la lista di operazioni 
	String[] operazioneArray = (String[]) operazioni.toArray(new String[0]);
	restriction.addFilterField(FilterUtils.in("operazione", operazioneArray, String.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.order("scCodice", "alberoproc", OrderTypeEnum.DESC));
	return this.findByFilterTable(filterTable);
    }
}
