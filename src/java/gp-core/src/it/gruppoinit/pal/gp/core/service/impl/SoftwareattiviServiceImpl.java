package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.SoftwareattiviDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.domain.helper.SoftwareattiviDTO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class SoftwareattiviServiceImpl extends BaseServiceImpl<Softwareattivi, SoftwareattiviId> implements SoftwareattiviService {

    private SoftwareattiviDAO softwareattiviDAO;
    private SoftwareService softwareService;

    @Autowired
    public void setSoftwareattiviDAO(SoftwareattiviDAO softwareattiviDAO) {

	this.softwareattiviDAO = softwareattiviDAO;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<Softwareattivi> getEntityClass() {

	return Softwareattivi.class;
    }

    @Override
    public List<Softwareattivi> findAll(Integer firstResult, Integer maxResult) {

	return softwareattiviDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Softwareattivi entity) {

	if (validateEntity(entity)) {
	    softwareattiviDAO.insert(entity);
	}
    }

    @Override
    public Softwareattivi findById(SoftwareattiviId id) {

	return softwareattiviDAO.findById(id);
    }

    @Override
    public void update(Softwareattivi entity) {

	if (validateEntity(entity)) {
	    softwareattiviDAO.update(entity);
	}
    }

    @Override
    public void delete(Softwareattivi entity) {

	if (isDeleteAllowed(entity)) {
	    softwareattiviDAO.delete(entity);
	}
    }

    @Override
    public List<Softwareattivi> findAllAndExcludeTT(boolean isAttiviFO) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.notEquals("codice", WebConstants.SOFTWARE_TT, "software", String.class));
	if (isAttiviFO) {
	    restriction.addFilterField(FilterUtils.equals("attivoFo", isAttiviFO, Boolean.class));
	}
	ft.addRestriction(restriction);
	ft.addOrder(FilterUtils.orderAsc("ordine", "software"));
	return softwareattiviDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(Softwareattivi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// la cancellazione è sempre permessa.
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<SoftwareattiviDTO> findAllSoftwareattiviDTO() {

	List<SoftwareattiviDTO> softwareattiviDTOs = new ArrayList<SoftwareattiviDTO>();
	List<Softwareattivi> softwareattivis = findAllAndExcludeTT(false);
	List<Software> softwares = softwareService.findAll(null, null);
	for (Software software : softwares) {
	    if (!software.getCodice().equals(WebConstants.SOFTWARE_TT)) {
		SoftwareattiviDTO softwareattiviDTO = new SoftwareattiviDTO();
		softwareattiviDTO.setCodice(software.getCodice());
		softwareattiviDTO.setDescrizione(software.getDescrizione());
		for (Softwareattivi softwareattivi : softwareattivis) {
		    if (softwareattivi.getSoftware().getCodice().equals(software.getCodice())) {
			softwareattiviDTO.setAttivo(true);
			softwareattiviDTO.setAttivoFo(softwareattivi.isAttivoFo());
		    }
		}
		softwareattiviDTOs.add(softwareattiviDTO);
	    }
	}
	return softwareattiviDTOs;
    }
}
