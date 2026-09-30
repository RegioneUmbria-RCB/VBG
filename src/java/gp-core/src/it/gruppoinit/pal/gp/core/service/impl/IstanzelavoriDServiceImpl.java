package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzelavoriDDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriDService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriTService;
import it.gruppoinit.pal.gp.core.service.TipiunitamisuraService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzelavoriDServiceImpl extends BaseServiceImpl<IstanzelavoriD, PkId> implements IstanzelavoriDService {

    private IstanzelavoriDDAO istanzelavoridDAO;
    private TipicausalioneriService tipicausalioneriService;
    private TipiunitamisuraService tipiunitamisuraService;
    private IstanzelavoriTService istanzelavoriTService;

    @Autowired
    public void setIstanzelavoriDDAO(IstanzelavoriDDAO istanzelavoridDAO) {

	this.istanzelavoridDAO = istanzelavoridDAO;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setTipiunitamisuraService(TipiunitamisuraService tipiunitamisuraService) {

	this.tipiunitamisuraService = tipiunitamisuraService;
    }

    @Autowired
    public void setIstanzelavoriTService(IstanzelavoriTService istanzelavoriTService) {

	this.istanzelavoriTService = istanzelavoriTService;
    }

    @Override
    protected Class<IstanzelavoriD> getEntityClass() {

	return IstanzelavoriD.class;
    }

    @Override
    public List<IstanzelavoriD> findAll(Integer firstResult, Integer maxResult) {

	return istanzelavoridDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzelavoriD entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzelavoridDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    @Override
    public IstanzelavoriD findById(PkId id) {

	return istanzelavoridDAO.findById(id);
    }

    @Override
    public void update(IstanzelavoriD entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzelavoridDAO.update(entity);
	    childDataUpdate(entity);
	}
    }

    @Override
    public void delete(IstanzelavoriD entity) {

	if (isDeleteAllowed(entity)) {
	    istanzelavoridDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzelavoriD entity) {

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

    private void dataIntegration(IstanzelavoriD entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanzaLavorid passata è nulla");
	}
	if (entity.getCostoUnitarioUm() == null) {
	    entity.setCostoUnitarioUm(new BigDecimal(0));
	}
	if (entity.getQuantita() == null) {
	    entity.setQuantita(new BigDecimal(0));
	}
	entity.setTotale(entity.getQuantita().multiply(entity.getCostoUnitarioUm()));
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(IstanzelavoriD entity) {

	Tipicausalioneri tipicausalioneri = tipicausalioneriService.bindDomainObject(entity.getTipicausalioneri(), PkId.class, "id.codice");
	entity.setTipicausalioneri(tipicausalioneri);
	Tipiunitamisura tipiunitamisura = tipiunitamisuraService.bindDomainObject(entity.getTipiunitamisura(), PkId.class, "id.codice");
	entity.setTipiunitamisura(tipiunitamisura);
	IstanzelavoriT istanzelavoriT = istanzelavoriTService.bindDomainObject(entity.getIstanzelavoriT(), PkId.class, "id.codice");
	entity.setIstanzelavoriT(istanzelavoriT);
    }

    private void childDataInsert(IstanzelavoriD entity) {

    }

    private void childDataUpdate(IstanzelavoriD entity) {

	// TODO Auto-generated method stub
    }
}
