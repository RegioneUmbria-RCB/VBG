/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocpeoplehrefDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeoplehref;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeoplehrefService;
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
public class AlberoprocpeoplehrefServiceImpl extends BaseServiceImpl<Alberoprocpeoplehref, PkId> implements AlberoprocpeoplehrefService {

    private AlberoprocpeoplehrefDAO alberoprocpeoplehrefDAO;
    private AlberoprocService alberoprocService;
    private SoftwareService softwareService;

    @Autowired
    public void setAlberoprocpeoplehrefDAO(AlberoprocpeoplehrefDAO alberoprocpeoplehrefDAO) {

	this.alberoprocpeoplehrefDAO = alberoprocpeoplehrefDAO;
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
    protected Class<Alberoprocpeoplehref> getEntityClass() {

	return Alberoprocpeoplehref.class;
    }

    @Override
    public void delete(Alberoprocpeoplehref entity) {

	alberoprocpeoplehrefDAO.delete(entity);
    }

    @Override
    public List<Alberoprocpeoplehref> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocpeoplehrefDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Alberoprocpeoplehref findById(PkId id) {

	return alberoprocpeoplehrefDAO.findById(id);
    }

    @Override
    public void insert(Alberoprocpeoplehref entity) {

	if (validateEntity(entity)) {
	    alberoprocpeoplehrefDAO.insert(entity);
	}
    }

    @Override
    public void update(Alberoprocpeoplehref entity) {

	if (validateEntity(entity)) {
	    alberoprocpeoplehrefDAO.update(entity);
	}
    }

    @Override
    public List<Alberoprocpeoplehref> findByAlberoProc(Alberoproc alberoproc) {

	return alberoprocpeoplehrefDAO.findByAlberoProc(alberoproc);
    }

    @Override
    public List<Alberoprocpeoplehref> findByFilterTable(FilterTable filterTable) {

	return alberoprocpeoplehrefDAO.findByFilterTable(filterTable);
    }

    @Override
    public Alberoprocpeoplehref findByTagNlaPeople(String nomeTag, List<String> valoriTag, String codiceSoftware) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	Software software = softwareService.findById(codiceSoftware);
	restriction.addFilterField(FilterUtils.equals("software", software, "alberoproc", Software.class));
	restriction.addFilterField(FilterUtils.equals("nometag", nomeTag, String.class));
	restriction.addFilterField(FilterUtils.in("valoretag", valoriTag.toArray(), String.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderDesc("scCodice", "alberoproc"));
	List<Alberoprocpeoplehref> list = this.findByFilterTable(filterTable);
	if (list != null && !list.isEmpty()) {
	    list.get(0);
	}
	return null;
    }
}
