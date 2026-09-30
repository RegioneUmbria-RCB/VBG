package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocEndoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioprocEndoServiceImpl extends BaseServiceImpl<InventarioprocEndo, PkId> implements InventarioprocEndoService {

    private InventarioprocEndoDAO inventarioprocEndoDAO;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private ComuniService comuniService;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setInventarioprocEndoDAO(InventarioprocEndoDAO inventarioprocEndoDAO) {

	this.inventarioprocEndoDAO = inventarioprocEndoDAO;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Override
    public void insert(InventarioprocEndo entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    inventarioprocEndoDAO.insert(entity);
	}
    }

    private boolean isInsertOrUpdateAllowed(InventarioprocEndo entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Integer endoD = entity.getInventarioprocEndoD().getId().getCodice();
	Integer endoT = entity.getInventarioprocEndoT().getId().getCodice();
	if (endoD.equals(endoT)) {
	    _ivs.add(new InvalidValue("service_error.inventario_procedimento_usato_padre", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	Boolean isUsatoComeRaggruppamento = this.isUsatoComeRaggruppamento(entity.getInventarioprocEndoD().getId().getIdcomune(), entity
		.getInventarioprocEndoD().getId().getCodice());
	if (isUsatoComeRaggruppamento) {
	    _ivs.add(new InvalidValue("service_error.inventario_procedimento_usato_come_raggruppamento", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    public Boolean isUsatoComeRaggruppamento(String idcomune, Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("inventarioprocEndoTId", codice, Integer.class));
	filterTable.addRestriction(fr);
	boolean isUsato = inventarioprocEndoDAO.existsRecords(filterTable);
	return isUsato;
    }

    @Override
    public void update(InventarioprocEndo entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    inventarioprocEndoDAO.update(entity);
	}
    }

    @Override
    public void delete(InventarioprocEndo entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    inventarioprocEndoDAO.delete(entity);
	}
    }

    @Override
    public List<InventarioprocEndo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public List<InventarioprocEndo> findByInventarioprocT(String idcomune, Integer codiceInventarioprocT, Boolean flagPubblica, Integer firstResult,
	    Integer maxResult, String codiceComune, boolean soloAbilitati) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("inventarioprocEndoTId", codiceInventarioprocT, Integer.class));
	if (flagPubblica != null) {
	    fr.addFilterField(FilterUtils.equals("flagPubblica", flagPubblica, Boolean.class));
	}
	if (soloAbilitati) {
	    FilterRestriction endotpubblicato = new FilterRestriction();
	    endotpubblicato.addFilterField(FilterUtils.notEquals("disabilitato", Boolean.TRUE, "inventarioprocEndoT", Boolean.class));
	    filterTable.addRestriction(endotpubblicato);
	    FilterRestriction endodpubblicato = new FilterRestriction();
	    endodpubblicato.addFilterField(FilterUtils.notEquals("disabilitato", Boolean.TRUE, "inventarioprocEndoD", Boolean.class));
	    filterTable.addRestriction(endodpubblicato);
	}
	if (flagPubblica != null) {
	    fr.addFilterField(FilterUtils.equals("flagPubblica", flagPubblica, Boolean.class));
	}
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction comunerestriction = new FilterRestriction();
	    comunerestriction.setAndOrRestriction(AndOrRestriction.OR);
	    comunerestriction.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    comunerestriction.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    filterTable.addRestriction(comunerestriction);
	}
	filterTable.addRestriction(fr);
	return inventarioprocEndoDAO.findByFilterTable(filterTable, null, null);
    }

    @Override
    public List<InventarioprocEndo> findByInventarioprocD(String idcomune, Integer codiceInventarioprocD, Boolean flagPubblica) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocEndoD.id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("inventarioprocEndoD.id.codice", codiceInventarioprocD, Integer.class));
	if (flagPubblica != null) {
	    fr.addFilterField(FilterUtils.equals("flagPubblica", flagPubblica, Boolean.class));
	}
	filterTable.addRestriction(fr);
	return inventarioprocEndoDAO.findByFilterTable(filterTable);
    }

    @Override
    public InventarioprocEndo findById(PkId id) {

	return inventarioprocEndoDAO.findById(id);
    }

    private void dataIntegration(InventarioprocEndo entity) {

	if (entity.getFlagNecessario() == null) {
	    entity.setFlagNecessario(Boolean.FALSE);
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(InventarioprocEndo entity) {

	// ATTENZIONE!! NON ABILITARE LA BINDdOMAIN SU INVENTARIOPROCE XCHE' CAMBIATA LA LOGICA
	//	Inventarioprocedimenti inventarioprocEndoT = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocEndoT(), PkId.class,
	//		"id.codice");
	//	entity.setInventarioprocEndoT(inventarioprocEndoT);
	//	Inventarioprocedimenti inventarioprocEndoD = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocEndoD(), PkId.class,
	//		"id.codice");
	//	entity.setInventarioprocEndoD(inventarioprocEndoD);
	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
    }

    @Override
    protected Class<InventarioprocEndo> getEntityClass() {

	return InventarioprocEndo.class;
    }
}
