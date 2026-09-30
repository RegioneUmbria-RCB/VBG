package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsabiliServiceImpl extends BaseServiceImpl<Responsabili, PkId> implements ResponsabiliService {

    private ResponsabiliDAO responsabiliDAO;
    private ComuniService comuniService;
    private ResponsabilicomuniService responsabilicomuniService;
    private ComuniassociatiService comuniassociatiService;

    @Override
    public void insertResponsabilePerComune(Responsabili entity, String codicecomune) {

	Comuni comuni = new Comuni(codicecomune);
	comuni = comuniService.findByCodiceComune(comuni);
	entity.setAmministratore(false);
	this.insert(entity);
	ResponsabilicomuniId responsabilicomuniId = new ResponsabilicomuniId(entity.getId().getCodice(), codicecomune);
	Responsabilicomuni responsabilicomuni = new Responsabilicomuni(responsabilicomuniId);
	responsabilicomuni.setResponsabile(entity);
	responsabilicomuni.setComune(comuni);
	responsabilicomuniService.insert(responsabilicomuni);
    }

    @Override
    public void deleteResponsabilePerComune(String codiceresponsabile, String codicecomune) {

	Responsabilicomuni responsabilicomuni = responsabilicomuniService.findById(new ResponsabilicomuniId(Integer.valueOf(codiceresponsabile),
		codicecomune));
	Responsabili responsabili = this.findById(new PkId(Integer.valueOf(codiceresponsabile)));
	responsabilicomuniService.delete(responsabilicomuni);
	this.delete(responsabili);
    }

    @Override
    public void insertComunePerResponsabile(String codiceresp, String codicecomune) {

	Responsabili responsabili = this.findById(new PkId(Integer.valueOf(codiceresp)));
	Comuni comuni = comuniService.findByCodiceComune(new Comuni(codicecomune));
	ResponsabilicomuniId responsabilicomuniId = new ResponsabilicomuniId(responsabili.getId().getCodice(), codicecomune);
	Responsabilicomuni responsabilicomuni = new Responsabilicomuni(responsabilicomuniId);
	responsabilicomuni.setResponsabile(responsabili);
	responsabilicomuni.setComune(comuni);
	responsabilicomuniService.insert(responsabilicomuni);
    }

    @Override
    public void eliminaComunePerResponsabile(String codiceresp, String codicecomune) {

	ResponsabilicomuniId responsabilicomuniId = new ResponsabilicomuniId(Integer.valueOf(codiceresp), codicecomune);
	Responsabilicomuni responsabilicomuni = responsabilicomuniService.findById(responsabilicomuniId);
	responsabilicomuniService.delete(responsabilicomuni);
    }

    @Override
    public void insertTuttiIComuniPerResponsabile(String codiceresp) {

	Responsabili responsabili = this.findById(new PkId(Integer.valueOf(codiceresp)));
	List<Comuniassociati> comuniassociatiList = comuniassociatiService.findAll();
	for (Comuniassociati comuniassociati : comuniassociatiList) {
	    String codicecomune = comuniassociati.getId().getCodicecomune();
	    Comuni comuni = comuniService.findByCodiceComune(new Comuni(codicecomune));
	    ResponsabilicomuniId responsabilicomuniId = new ResponsabilicomuniId(responsabili.getId().getCodice(), codicecomune);
	    Responsabilicomuni responsabilicomuni = new Responsabilicomuni(responsabilicomuniId);
	    responsabilicomuni.setResponsabile(responsabili);
	    responsabilicomuni.setComune(comuni);
	    responsabilicomuniService.insert(responsabilicomuni);
	}
    }

    @Override
    public List<Responsabili> findAll() {

	return responsabiliDAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE, "responsabile", DAOOrderTypeEnum.ASC);
    }

    @Override
    public void delete(Responsabili entity) {

	if (isDeleteAllowed(entity)) {
	    responsabiliDAO.delete(entity);
	}
    }

    @Override
    public void insert(Responsabili entity) {

	if (validateEntity(entity)) {
	    fixMerge(entity);
	    responsabiliDAO.insert(entity);
	}
    }

    @Override
    public Responsabili findById(PkId id) {

	return responsabiliDAO.findById(id);
    }

    @Override
    public Responsabili findByUserId(String userId) {

	return responsabiliDAO.findByUserid(userId);
    }

    @Override
    public void update(Responsabili entity) {

	if (validateEntity(entity)) {
	    fixMerge(entity);
	    responsabiliDAO.update(entity);
	}
    }

    private void fixMerge(Responsabili entity) {

	if (entity.getAmministratore() == null) {
	    entity.setAmministratore(false);
	}
	if (entity.getPassword() == null) {
	    entity.setPassword("");
	}
	if (entity.getDisabilitato() == null) {
	    entity.setDisabilitato(false);
	}
	if (entity.getGestioneFesteSagre() == null) {
	    entity.setGestioneFesteSagre(false);
	}
	if (entity.getGestioneFiereMostre() == null) {
	    entity.setGestioneFiereMostre(false);
	}
	if (entity.getReadonly() == null) {
	    entity.setReadonly(false);
	}
    }

    @Autowired
    public void setResponsabiliDAO(ResponsabiliDAO responsabiliDAO) {

	this.responsabiliDAO = responsabiliDAO;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setResponsabilicomuniService(ResponsabilicomuniService responsabilicomuniService) {

	this.responsabilicomuniService = responsabilicomuniService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Override
    public Class<Responsabili> getEntityClass() {

	return Responsabili.class;
    }
}
