package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BandiAlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BandiAlberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BandiAlberoprocService;

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
public class BandiAlberoprocServiceImpl extends BaseServiceImpl<BandiAlberoproc, PkId> implements BandiAlberoprocService {

    private BandiAlberoprocDAO bandialberoprocDAO;

    @Autowired
    public void setBandiAlberoprocDAO(BandiAlberoprocDAO bandialberoprocDAO) {

	this.bandialberoprocDAO = bandialberoprocDAO;
    }

    @Override
    protected Class<BandiAlberoproc> getEntityClass() {

	return BandiAlberoproc.class;
    }

    @Override
    public List<BandiAlberoproc> findAll(Integer firstResult, Integer maxResult) {

	return bandialberoprocDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BandiAlberoproc entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    bandialberoprocDAO.insert(entity);
	}
    }

    private void dataIntegration(BandiAlberoproc entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare tipo bando nulla");
	}
	//	int max = this.findMaxOrdine(entity.getBandi().getId().getCodice());
	//	entity.setOrdine(max + 1);
	fixMergeEntityProperties(entity);
    }

    private boolean isInsertOrUpdateAllowed(BandiAlberoproc entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	int percTot = entity.getPercentualeEstrazione();
	//	if (entity.getPercentualeEstrazione() != null) {
	//	    percTot = entity.getPercentualeEstrazione();
	//	} else {
	//	    _ivs.add(new InvalidValue("service_errore.deve_essere_impostata_percentuale", null, null, null, null));
	//	}
	//Controllo se la somma delle percentuali supera 100%
	List<BandiAlberoproc> list = this.findByBandi(entity.getBandi().getId().getCodice());
	for (BandiAlberoproc bandiAlberoproc : list) {
	    percTot += bandiAlberoproc.getPercentualeEstrazione();
	    if (percTot > 100) {
		isInsert = false;
		_ivs.add(new InvalidValue("service_errore.la_somma_delle_percentuali_supera_100", null, null, null, null));
		break;
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    @Override
    public BandiAlberoproc findById(PkId id) {

	return bandialberoprocDAO.findById(id);
    }

    @Override
    public void update(BandiAlberoproc entity) {

	if (validateEntity(entity)) {
	    bandialberoprocDAO.update(entity);
	}
    }

    @Override
    public void delete(BandiAlberoproc entity) {

	if (isDeleteAllowed(entity)) {
	    bandialberoprocDAO.delete(entity);
	}
    }

    //    protected boolean isDeleteAllowed(BandiAlberoproc entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
    @Override
    public List<BandiAlberoproc> findByBandi(Integer codiceBando) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", codiceBando, "bandi", Integer.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("ordine"));
	return bandialberoprocDAO.findByFilterTable(filterTable);
    }

    @Override
    public int findMaxOrdine(Integer codiceBando) {

	return bandialberoprocDAO.findMaxOrdine(codiceBando);
    }

    @Override
    public void updateUpOrdine(Integer codiceBandoAlberoProc, Integer codiceBandoAlberoProcSup) {

	BandiAlberoproc bandialberoproc = this.findById(new PkId(codiceBandoAlberoProc));
	BandiAlberoproc bandiAlberoprocSup = this.findById(new PkId(codiceBandoAlberoProcSup));
	Integer ordineBandoAlberoProc = bandialberoproc.getOrdine();
	Integer ordineBandoAlberoProcSup = bandiAlberoprocSup.getOrdine();
	bandialberoproc.setOrdine(ordineBandoAlberoProcSup);
	bandiAlberoprocSup.setOrdine(ordineBandoAlberoProc);
	this.update(bandialberoproc);
	this.update(bandiAlberoprocSup);
    }

    @Override
    public void updateDownOrdine(Integer codiceBandoAlberoProc, Integer codiceBandoAlberoProcInf) {

	BandiAlberoproc bandiAlberoproc = this.findById(new PkId(codiceBandoAlberoProc));
	BandiAlberoproc bandiAlberoprocInf = this.findById(new PkId(codiceBandoAlberoProcInf));
	Integer ordinebandiAlberoproc = bandiAlberoproc.getOrdine();
	Integer ordinebandiAlberoprocInf = bandiAlberoprocInf.getOrdine();
	bandiAlberoproc.setOrdine(ordinebandiAlberoprocInf);
	bandiAlberoprocInf.setOrdine(ordinebandiAlberoproc);
	this.update(bandiAlberoproc);
	this.update(bandiAlberoprocInf);
    }

    @Override
    public List<Integer> findDistinctMercatiByBando(Integer codiceBando) {

	return bandialberoprocDAO.findDistinctMercatiByBando(codiceBando);
    }

    @Override
    public List<Integer> findDistinctMercatiUsoByBando(Integer codiceBando) {

	return bandialberoprocDAO.findDistinctMercatiUsoByBando(codiceBando);
    }
}
