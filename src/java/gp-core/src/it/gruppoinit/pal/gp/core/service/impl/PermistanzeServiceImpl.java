package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.PermistanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.PermistanzeId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.PermistanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class PermistanzeServiceImpl extends BaseServiceImpl<Permistanze, PermistanzeId> implements PermistanzeService {

    private static final Logger log = LoggerFactory.getLogger(PermistanzeServiceImpl.class);
    private PermistanzeDAO permistanzeDAO;
    private ResponsabiliService responsabiliService;
    private IstanzeService istanzeService;

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setPermistanzeDAO(PermistanzeDAO permistanzeDAO) {

	this.permistanzeDAO = permistanzeDAO;
    }

    @Override
    protected Class<Permistanze> getEntityClass() {

	return Permistanze.class;
    }

    @Override
    public List<Permistanze> findAll(Integer firstResult, Integer maxResult) {

	return permistanzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Permistanze entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    permistanzeDAO.insert(entity);
	}
    }

    @Override
    public Permistanze findById(PermistanzeId id) {

	return permistanzeDAO.findById(id);
    }

    @Override
    public void update(Permistanze entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    permistanzeDAO.update(entity);
	}
    }

    private void dataIntegration(Permistanze entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza passata è nulla");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Permistanze entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
	Responsabili responsabili = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabili);
	if (istanze != null) {
	    entity.getId().setCodiceistanza(istanze.getId().getCodice());
	}
	if (responsabili != null) {
	    entity.getId().setCodiceresponsabile(responsabili.getId().getCodice());
	}
    }

    @Override
    public void delete(Permistanze entity) {

	if (isDeleteAllowed(entity)) {
	    permistanzeDAO.delete(entity);
	}
    }

    @Override
    public List<Permistanze> findByFilterTable(FilterTable filterTable) {

	return permistanzeDAO.findByFilterTable(filterTable);
    }

    protected boolean isDeleteAllowed(Permistanze entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public boolean checkByIstanzaAndResponsabile(Istanze istanza, Responsabili responsabile) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    log.error("findByIstanzaAndResponsabile: Parametro non valido [istanza]");
	    throw new IllegalArgumentException("Parametro non valido [istanza]");
	}
	if (EntityUtils.getNestedProperty(responsabile, "id.codice") == null) {
	    log.error("findByIstanzaAndResponsabile: Parametro non valido [responsabile]");
	    throw new IllegalArgumentException("Parametro non valido [responsabile]");
	}
	PermistanzeId id = new PermistanzeId(istanza.getId().getCodice(), responsabile.getId().getCodice());
	Permistanze permesso = this.findById(id);
	return permesso == null ? false : true;
    }

    @Override
    public List<Permistanze> findByIstanza(Istanze istanza) {

	return findByIstanzeAndResponsabile(istanza, null, false);
    }

    private List<Permistanze> findByIstanzeAndResponsabile(Istanze istanza, Responsabili responsabile, boolean checkResponsabileNotNull) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    log.error("findByIstanzaAndResponsabile: Parametro non valido [istanza]");
	    throw new IllegalArgumentException("Parametro non valido [istanza]");
	}
	if (checkResponsabileNotNull) {
	    if (EntityUtils.getNestedProperty(responsabile, "id.codice") == null) {
		log.error("findByIstanzaAndResponsabile: Parametro non valido [responsabile]");
		throw new IllegalArgumentException("Parametro non valido [responsabile]");
	    }
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanze", Integer.class));
	if (EntityUtils.getNestedProperty(responsabile, "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", responsabile.getId().getCodice(), "responsabile", Integer.class));
	}
	ft.addRestriction(fr);
	return permistanzeDAO.findByFilterTable(ft);
    }

    @Override
    public void notCheckinsertInFaseDiaccettazioneRuoloIstruttore(Permistanze entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    permistanzeDAO.insert(entity);
	}
    }
}
