package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modTSnapshotDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshotId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modTSnapshotService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class IAttivitadyn2modTSnapshotServiceImpl extends BaseServiceImpl<IAttivitadyn2modTSnapshot, IAttivitadyn2modTSnapshotId>
	implements IAttivitadyn2modTSnapshotService {

    private IAttivitadyn2modTSnapshotDAO iattivitadyn2modtsnapshotDAO;

    @Autowired
    public void setIAttivitadyn2modTSnapshotDAO(IAttivitadyn2modTSnapshotDAO iattivitadyn2modtsnapshotDAO) {

	this.iattivitadyn2modtsnapshotDAO = iattivitadyn2modtsnapshotDAO;
    }

    @Override
    protected Class<IAttivitadyn2modTSnapshot> getEntityClass() {

	return IAttivitadyn2modTSnapshot.class;
    }

    @Override
    public List<IAttivitadyn2modTSnapshot> findAll(Integer firstResult, Integer maxResult) {

	return iattivitadyn2modtsnapshotDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitadyn2modTSnapshot entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2modtsnapshotDAO.insert(entity);
	}
    }

    @Override
    public IAttivitadyn2modTSnapshot findById(IAttivitadyn2modTSnapshotId id) {

	return iattivitadyn2modtsnapshotDAO.findById(id);
    }

    @Override
    public void update(IAttivitadyn2modTSnapshot entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2modtsnapshotDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitadyn2modTSnapshot entity) {

	if (isDeleteAllowed(entity)) {
	    iattivitadyn2modtsnapshotDAO.delete(entity);
	}
    }

    @Override
    public List<IAttivitadyn2modTSnapshot> findByfindByIAttivitaSnapshot(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "attivitaSnapshot", Integer.class));
	ft.addRestriction(fr);
	return iattivitadyn2modtsnapshotDAO.findByFilterTable(ft);
    }

    @Override
    public void deleteBySnapshot(Integer idSnapshot) {

	iattivitadyn2modtsnapshotDAO.deleteBySnapshot(idSnapshot);
    }
    //    protected boolean isDeleteAllowed(IAttivitadyn2modTSnapshot entity) {
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
}
