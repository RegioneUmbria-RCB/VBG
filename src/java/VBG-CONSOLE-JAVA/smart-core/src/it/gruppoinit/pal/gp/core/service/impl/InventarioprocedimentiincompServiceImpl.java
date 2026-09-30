package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiincompDAO;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiincompService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class InventarioprocedimentiincompServiceImpl extends BaseServiceImpl<Inventarioprocedimentiincomp, PkId> implements
	InventarioprocedimentiincompService {

    private InventarioprocedimentiincompDAO inventarioprocedimentiincompDAO;
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setInventarioprocedimentiincompDAO(InventarioprocedimentiincompDAO inventarioprocedimentiincompDAO) {

	this.inventarioprocedimentiincompDAO = inventarioprocedimentiincompDAO;
    }

    @Override
    protected Class<Inventarioprocedimentiincomp> getEntityClass() {

	return Inventarioprocedimentiincomp.class;
    }

    @Override
    public List<Inventarioprocedimentiincomp> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocedimentiincompDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Inventarioprocedimentiincomp entity) {

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    inventarioprocedimentiincompDAO.insert(entity);
	}
    }

    @Override
    public Inventarioprocedimentiincomp findById(PkId id) {

	return inventarioprocedimentiincompDAO.findById(id);
    }

    @Override
    public void update(Inventarioprocedimentiincomp entity) {

	if (validateEntity(entity)) {
	    inventarioprocedimentiincompDAO.update(entity);
	}
    }

    @Override
    public void delete(Inventarioprocedimentiincomp entity) {

	if (isDeleteAllowed(entity)) {
	    inventarioprocedimentiincompDAO.delete(entity);
	}
    }

    @Override
    public List<Inventarioprocedimentiincomp> findByEndoprocedimento(Inventarioprocedimenti inventarioprocedimenti) {

	return inventarioprocedimentiincompDAO.findByEndoprocedimento(inventarioprocedimenti);
    }

    @Override
    public Inventarioprocedimentiincomp findByEndoprocedimentoAndEndoprocedimentoIncomp(Inventarioprocedimenti inventarioprocedimenti,
	    Inventarioprocedimenti inventarioprocedimentoIncomp) {

	return inventarioprocedimentiincompDAO.findByEndoprocedimentoAndEndoprocedimentoIncomp(inventarioprocedimenti, inventarioprocedimentoIncomp);
    }

    protected boolean isInsertAllowed(Inventarioprocedimentiincomp entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity == null) {
	    _ivs.add(new InvalidValue("service_error.endoprocedimento_non_selezionato", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	if (entity.getInventarioprocedimento().getId().getCodice().equals(entity.getInventarioprocedimentoincompatibile().getId().getCodice())) {
	    _ivs.add(new InvalidValue("service_error.stesso_endoprocediemento", null, null, entity.getId().getCodice(), null));
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public void addOrRemoveIncompatibilitaendo(Inventarioprocedimenti inventarioprocedimenti, String codiciDegliEndoIncompatibili,
	    String codiciTotaliDegliEndoPerUnProcedimento) {

	// Elimina endo incompatibili nel caso in cui stiamo togliendo tutte le incompatibilità
	if (StringUtils.isNotBlank(codiciDegliEndoIncompatibili)) {
	    String[] codiciEndo = codiciDegliEndoIncompatibili.split(",");
	    String[] codiciEndoTotali = codiciTotaliDegliEndoPerUnProcedimento.split(",");
	    List<String> listaCodiciEndoTotali = Arrays.asList(codiciEndoTotali);
	    List<String> listaCodiciEndo = Arrays.asList(codiciEndo);
	    List<String> listaCodiciEndoCheVogliamoCompatibili = ListUtils.subtract(listaCodiciEndoTotali, listaCodiciEndo);
	    for (String codComp : listaCodiciEndoCheVogliamoCompatibili) {
		Inventarioprocedimenti inventarioprocedimentoNonCompatibile = inventarioprocedimentiService.findById(new PkId(Integer
			.parseInt(codComp)));
		Inventarioprocedimentiincomp inventarioprocedimentoIncomp = inventarioprocedimentiincompDAO
			.findByEndoprocedimentoAndEndoprocedimentoIncomp(inventarioprocedimenti, inventarioprocedimentoNonCompatibile);
		if (inventarioprocedimentoIncomp != null) {
		    inventarioprocedimentiincompDAO.delete(inventarioprocedimentoIncomp);
		}
	    }
	    for (String codIncomp : listaCodiciEndo) {
		Inventarioprocedimenti inventarioprocedimentoNonCompatibile = inventarioprocedimentiService.findById(new PkId(Integer
			.parseInt(codIncomp)));
		Inventarioprocedimentiincomp inventarioprocedimentoIncomp = inventarioprocedimentiincompDAO
			.findByEndoprocedimentoAndEndoprocedimentoIncomp(inventarioprocedimenti, inventarioprocedimentoNonCompatibile);
		if (inventarioprocedimentoIncomp == null) {
		    Inventarioprocedimentiincomp objectInsert = new Inventarioprocedimentiincomp();
		    objectInsert.setInventarioprocedimento(inventarioprocedimenti);
		    objectInsert.setInventarioprocedimentoincompatibile(inventarioprocedimentoNonCompatibile);
		    inventarioprocedimentiincompDAO.insert(objectInsert);
		}
	    }
	}
	if (StringUtils.isBlank(codiciDegliEndoIncompatibili)) {
	    String[] codiciEndoTotali = codiciTotaliDegliEndoPerUnProcedimento.split(",");
	    List<String> listaCodiciEndoTotali = Arrays.asList(codiciEndoTotali);
	    for (String codice : listaCodiciEndoTotali) {
		Inventarioprocedimenti endoprocedimentoIncomaptibile = inventarioprocedimentiService.findById(new PkId(Integer.parseInt(codice)));
		Inventarioprocedimentiincomp inventarioprocedimentoIncomp = inventarioprocedimentiincompDAO
			.findByEndoprocedimentoAndEndoprocedimentoIncomp(inventarioprocedimenti, endoprocedimentoIncomaptibile);
		if (inventarioprocedimentoIncomp != null)
		    inventarioprocedimentiincompDAO.delete(inventarioprocedimentoIncomp);
	    }
	}
    }

    // protected boolean isDeleteAllowed(Inventarioprocedimentiincomp entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //
    // // esempio:
    // if (entity.get.size() > 0) {
    // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
    @Override
    public Set<Inventarioprocedimentiincomp> checkEndoIncompatibili(List<String> idEndos) {

	Set<Inventarioprocedimentiincomp> incompatibili = new HashSet<Inventarioprocedimentiincomp>();
	if (idEndos != null && !idEndos.isEmpty()) {
	    for (String idEndo : idEndos) {
		Inventarioprocedimenti endoCheck = new Inventarioprocedimenti();
		PkId idEndoPk = inventarioprocedimentiService.getIdFromEndoprocedimentoKey(idEndo);
		endoCheck.setId(idEndoPk);
		List<Inventarioprocedimentiincomp> incomps = findByEndoprocedimento(endoCheck);
		if (incomps != null) {
		    for (Inventarioprocedimentiincomp ipinc : incomps) {
			for (String idEndoInc : idEndos) {
			    String key = inventarioprocedimentiService.getEndoprocedimentoKey(ipinc.getInventarioprocedimentoincompatibile());
			    if (idEndoInc.equalsIgnoreCase(key)) {
				incompatibili.add(ipinc);
			    }
			}
		    }
		}
	    }
	}
	return incompatibili;
    }
}
