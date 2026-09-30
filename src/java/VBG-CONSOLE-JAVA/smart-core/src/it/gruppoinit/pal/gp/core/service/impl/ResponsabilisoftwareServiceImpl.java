/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabilisoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class ResponsabilisoftwareServiceImpl extends BaseServiceImpl<Responsabilisoftware, ResponsabilisoftwareId> implements
	ResponsabilisoftwareService {

    private ResponsabilisoftwareDAO responsabilisoftwareDAO;

    @Autowired
    public void setResponsabilisoftwareDAO(ResponsabilisoftwareDAO responsabilisoftwareDAO) {

	this.responsabilisoftwareDAO = responsabilisoftwareDAO;
    }

    @Override
    protected Class<Responsabilisoftware> getEntityClass() {

	return Responsabilisoftware.class;
    }

    @Override
    public void delete(Responsabilisoftware entity) {

	responsabilisoftwareDAO.delete(entity);
    }

    @Override
    public List<Responsabilisoftware> findAll(Integer firstResult, Integer maxResult) {

	return responsabilisoftwareDAO.findAll(null, null);
    }

    @Override
    public Responsabilisoftware findById(ResponsabilisoftwareId id) {

	return responsabilisoftwareDAO.findById(id);
    }

    @Override
    public void insert(Responsabilisoftware entity) {

	if (validateEntity(entity)) {
	    responsabilisoftwareDAO.insert(entity);
	}
    }

    @Override
    public void update(Responsabilisoftware entity) {

	if (validateEntity(entity)) {
	    responsabilisoftwareDAO.update(entity);
	}
    }

    @Override
    public void deleteByResponsabile(Responsabili entity) {

	List<Responsabilisoftware> list = this.findByResponsabile(entity);
	for (Responsabilisoftware responsabilisoftware : list) {
	    this.delete(responsabilisoftware);
	}
    }

    @Override
    public List<Responsabilisoftware> findByResponsabile(Responsabili responsabili) {

	return responsabilisoftwareDAO.findByResponsabile(responsabili);
    }

    @Override
    public List<Responsabilisoftware> findBySoftware(Responsabili responsabili, Software software) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction responsabilisoftwareRestriction = new FilterRestriction();
	// Responsabile
	if (EntityUtils.getNestedProperty(responsabili, "id.codice") != null) {
	    responsabilisoftwareRestriction.addFilterField(FilterUtils.equals("id.codiceresponsabile", responsabili.getId().getCodice(),
		    Integer.class));
	}
	// Software
	if (EntityUtils.getNestedProperty(software, "descrizione") != null) {
	    String hierarchyresponsabilisoftware = "software";
	    responsabilisoftwareRestriction.addFilterField(FilterUtils.startsWith("descrizione", software.getDescrizione(),
		    hierarchyresponsabilisoftware));
	}
	filterTable.addRestriction(responsabilisoftwareRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("ordine", "software"));
	filterTable.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	return responsabilisoftwareDAO.findByFilterTable(filterTable);
    }
}
