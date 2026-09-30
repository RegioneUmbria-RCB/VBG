package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoftwareServiceImpl extends BaseServiceImpl<Software, String> implements SoftwareService {

    private SoftwareDAO softwareDAO;
    private SoftwareattiviService softwareattiviService;
    private ResponsabilisoftwareService responsabilisoftwareService;

    @Autowired
    public void setResponsabilisoftwareService(ResponsabilisoftwareService responsabilisoftwareService) {

	this.responsabilisoftwareService = responsabilisoftwareService;
    }

    @Autowired
    public void setSoftwareDAO(SoftwareDAO softwareDAO) {

	this.softwareDAO = softwareDAO;
    }

    @Autowired
    public void setSoftwareattiviService(SoftwareattiviService softwareattiviService) {

	this.softwareattiviService = softwareattiviService;
    }

    @Override
    public void delete(Software entity) {

	softwareDAO.delete(entity);
    }

    @Override
    public List<Software> findAll(Integer firstResult, Integer maxResult) {

	return softwareDAO.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "ordine", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Software findById(String id) {

	return softwareDAO.findById(id);
    }

    @Override
    public void insert(Software entity) {

	if (validateEntity(entity))
	    softwareDAO.insert(entity);
    }

    @Override
    public void update(Software entity) {

	if (validateEntity(entity))
	    softwareDAO.update(entity);
    }

    @Override
    public Class<Software> getEntityClass() {

	return Software.class;
    }

    @Override
    public List<Software> findByFilter(Software entity) {

	return softwareDAO.findByFilter(entity);
    }

    @Override
    public List<Software> findSoftwareAttivi(boolean frontoffice) {

	return softwareDAO.findSoftwareAttivi(frontoffice);
    }

    @Override
    public List<Software> findSoftwareAbilitati(Responsabili responsabile) {

	return this.findSoftwareAbilitati(responsabile, false);
    }

    @Override
    public boolean isSoftwareAttivo(String codiceSoftware) {

	boolean isAttivo = false;
	Softwareattivi softwareattivi = softwareattiviService.findById(new SoftwareattiviId(codiceSoftware));
	if (softwareattivi != null) {
	    isAttivo = true;
	}
	return isAttivo;
    }

    @Override
    public boolean isSoftwareAbilitato(Responsabili responsabile, String codiceSoftware) {

	boolean success = false;
	if (this.isSoftwareAttivo(codiceSoftware)) {
	    ResponsabilisoftwareId rsid = new ResponsabilisoftwareId(codiceSoftware, responsabile.getId().getCodice());
	    Responsabilisoftware rs = responsabilisoftwareService.findById(rsid);
	    return rs != null;
	}
	return success;
    }

    @Override
    public List<Software> findSoftwareAbilitati(Responsabili responsabile, boolean escludiNonOpzionali) {

	return softwareDAO.findSoftwareAbilitati(responsabile, escludiNonOpzionali);
    }

    @Override
    public List<Software> findAttiviAndExcludeTT(boolean isAttiviFO) {

	List<Software> softwares = new ArrayList<Software>();
	List<Softwareattivi> softwareattivis = softwareattiviService.findAllAndExcludeTT(isAttiviFO);
	for (Softwareattivi softwareattivi : softwareattivis) {
	    softwares.add(softwareattivi.getSoftware());
	}
	return softwares;
    }
}
