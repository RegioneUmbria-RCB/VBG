package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovStcAlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipimovStcAlberoprocService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class TipimovStcAlberoprocServiceImpl extends BaseServiceImpl<TipimovStcAlberoproc, PkId> implements TipimovStcAlberoprocService {

    private TipimovStcAlberoprocDAO tipimovstcalberoprocDAO;

    @Autowired
    public void setTipimovStcAlberoprocDAO(TipimovStcAlberoprocDAO tipimovstcalberoprocDAO) {

	this.tipimovstcalberoprocDAO = tipimovstcalberoprocDAO;
    }

    @Override
    protected Class<TipimovStcAlberoproc> getEntityClass() {

	return TipimovStcAlberoproc.class;
    }

    @Override
    public List<TipimovStcAlberoproc> findAll(Integer firstResult, Integer maxResult) {

	return tipimovstcalberoprocDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipimovStcAlberoproc entity) {

	if (validateEntity(entity)) {
	    if (validateInsert(entity)) {
		tipimovstcalberoprocDAO.insert(entity);
	    }
	}
    }

    @Override
    public TipimovStcAlberoproc findById(PkId id) {

	return tipimovstcalberoprocDAO.findById(id);
    }

    @Override
    public void update(TipimovStcAlberoproc entity) {

	if (validateEntity(entity)) {
	    tipimovstcalberoprocDAO.update(entity);
	}
    }

    @Override
    public void delete(TipimovStcAlberoproc entity) {

	if (isDeleteAllowed(entity)) {
	    tipimovstcalberoprocDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(TipimovStcAlberoproc entity) {

	boolean delete = true;
	// List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		
	// if (!_ivs.isEmpty()) {
	// this.throwValidationMessages(_ivs);
	// }
	return delete;
    }

    /**
     * controlla se è già stato inserito una configurazione per il movimento e per l'amministrazione. Non posso infatti
     * indicare due voci differenti per la stessa amministrazione.
     * 
     * @param entity
     * @return
     */
    private boolean validateInsert(TipimovStcAlberoproc entity) {

	List<TipimovStcAlberoproc> list = this.findByTipimovimento(entity.getTipimovimento().getId());
	int codiceAmmnistrazioneDaInserire = entity.getAmministrazioni().getId().getCodice().intValue();
	for (TipimovStcAlberoproc tipimovStcAlberoproc : list) {
	    int codiceAmministrazione = tipimovStcAlberoproc.getAmministrazioni().getId().getCodice().intValue();
	    if (codiceAmministrazione == codiceAmmnistrazioneDaInserire) {
		this.throwValidationMessage(new InvalidValue("service_error.informazione_gia_associata_per_il_dato", null, null, tipimovStcAlberoproc
			.getAmministrazioni().getAmministrazione(), null));
	    }
	}
	return true;
    }

    @Override
    public List<TipimovStcAlberoproc> findByTipimovimento(TipimovimentoId tipimovimentoId) {

	return tipimovstcalberoprocDAO.findByTipimovimento(tipimovimentoId);
    }

    @Override
    public List<TipimovStcAlberoproc> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	filterTable.addRestriction(fr);
	return tipimovstcalberoprocDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<TipimovStcAlberoproc> findByTipimovimentoAndAmministrazione(String tipomovimento, Integer codiceAmministrazione) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByTipimovimentoAndAmministrazione: il parametro codiceAmministrazione e' nullo");
	}
	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("findByTipimovimentoAndAmministrazione: il parametro tipomovimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipomovimento, String.class));
	filterTable.addRestriction(fr);
	return tipimovstcalberoprocDAO.findByFilterTable(filterTable);
    }
}
