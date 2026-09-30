package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocTipisogBackDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisogBackService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AlberoprocTipisogBackServiceImpl extends BaseServiceImpl<AlberoprocTipisogBack, PkId> implements AlberoprocTipisogBackService {

    private AlberoprocTipisogBackDAO alberoproctipisogbackDAO;
    private AlberoprocService alberoprocService;
    private TipisoggettoService tipisoggettoService;

    @Autowired
    public void setAlberoprocTipisogBackDAO(AlberoprocTipisogBackDAO alberoproctipisogbackDAO) {

	this.alberoproctipisogbackDAO = alberoproctipisogbackDAO;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setTipisoggettoService(TipisoggettoService tipisoggettoService) {

	this.tipisoggettoService = tipisoggettoService;
    }

    @Override
    protected Class<AlberoprocTipisogBack> getEntityClass() {

	return AlberoprocTipisogBack.class;
    }

    @Override
    public List<AlberoprocTipisogBack> findAll(Integer firstResult, Integer maxResult) {

	return alberoproctipisogbackDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocTipisogBack entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoproctipisogbackDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocTipisogBack findById(PkId id) {

	return alberoproctipisogbackDAO.findById(id);
    }

    @Override
    public void update(AlberoprocTipisogBack entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoproctipisogbackDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocTipisogBack entity) {

	if (isDeleteAllowed(entity)) {
	    alberoproctipisogbackDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocTipisogBack> findByAlberoproc(Integer codiceAlberoproc) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAlberoproc, "alberoproc", Integer.class));
	filterTable.addRestriction(fr);
	return alberoproctipisogbackDAO.findByFilterTable(filterTable);
    }

    private void dataIntegration(AlberoprocTipisogBack entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(AlberoprocTipisogBack entity) {

	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
	Tipisoggetto tipisoggetto = tipisoggettoService.bindDomainObject(entity.getTipisoggetto(), PkId.class, "id.codice");
	entity.setTipisoggetto(tipisoggetto);
    }
    //    protected boolean isDeleteAllowed(AlberoprocTipisogBack entity) {
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
