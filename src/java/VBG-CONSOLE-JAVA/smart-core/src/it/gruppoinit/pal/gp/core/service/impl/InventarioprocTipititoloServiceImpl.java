package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocTipititoloDAO;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.InventarioprocTipititoloService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class InventarioprocTipititoloServiceImpl extends BaseServiceImpl<InventarioprocTipititolo, PkId> implements InventarioprocTipititoloService {

    private InventarioprocTipititoloDAO inventarioproctipititoloDAO;
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setInventarioprocTipititoloDAO(InventarioprocTipititoloDAO inventarioproctipititoloDAO) {

	this.inventarioproctipititoloDAO = inventarioproctipititoloDAO;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Override
    protected Class<InventarioprocTipititolo> getEntityClass() {

	return InventarioprocTipititolo.class;
    }

    @Override
    public List<InventarioprocTipititolo> findAll(Integer firstResult, Integer maxResult) {

	return inventarioproctipititoloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(InventarioprocTipititolo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioproctipititoloDAO.insert(entity);
	}
    }

    @Override
    public InventarioprocTipititolo findById(PkId id) {

	return inventarioproctipititoloDAO.findById(id);
    }

    @Override
    public void update(InventarioprocTipititolo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioproctipititoloDAO.update(entity);
	}
    }

    @Override
    public void delete(InventarioprocTipititolo entity) {

	if (isDeleteAllowed(entity)) {
	    inventarioproctipititoloDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(InventarioprocTipititolo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<InventarioprocTipititolo> findByInventarioproc(Integer codiceInventarioproc) {

	return inventarioproctipititoloDAO.findByInventarioproc(codiceInventarioproc);
    }

    private void dataIntegration(InventarioprocTipititolo entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro InventarioprocTipititolo è nullo");
	}
	if (entity.getFlgMostraData() == null) {
	    entity.setFlgMostraData(Boolean.FALSE);
	}
	if (entity.getFlgMostraNumero() == null) {
	    entity.setFlgMostraNumero(Boolean.FALSE);
	}
	if (entity.getFlgMostraRilasciatoDa() == null) {
	    entity.setFlgMostraRilasciatoDa(Boolean.FALSE);
	}
	if (entity.getFlgRichiedeAllegato() == null) {
	    entity.setFlgRichiedeAllegato(Boolean.FALSE);
	}
	if (entity.getFlgVerificaFirmaAllegato() == null) {
	    entity.setFlgVerificaFirmaAllegato(Boolean.FALSE);
	}
	if (entity.getFlgAllObbligatorio() == null) {
	    entity.setFlgAllObbligatorio(Boolean.FALSE);
	}
	if (entity.getFlgNonPubblicare() == null) {
	    entity.setFlgNonPubblicare(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(InventarioprocTipititolo entity) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(),
		PkId.class, "id.codice");
	entity.setInventarioprocedimenti(inventarioprocedimenti);
    }
}
