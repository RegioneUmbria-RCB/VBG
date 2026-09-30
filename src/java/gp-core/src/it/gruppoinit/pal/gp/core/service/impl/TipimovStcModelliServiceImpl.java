package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovStcModelliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcModelli;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipimovStcModelliService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TipimovStcModelliServiceImpl extends BaseServiceImpl<TipimovStcModelli, PkId> implements TipimovStcModelliService {

    private TipimovStcModelliDAO tipimovStcModelliDAO;

    @Autowired
    public void setTipimovStcModelliDAO(TipimovStcModelliDAO tipimovStcModelliDAO) {

	this.tipimovStcModelliDAO = tipimovStcModelliDAO;
    }

    @Override
    protected Class<TipimovStcModelli> getEntityClass() {

	return TipimovStcModelli.class;
    }

    @Override
    public List<TipimovStcModelli> findByTipimovimento(TipimovimentoId tipimovimentoId) {

	return tipimovStcModelliDAO.findByTipimovimento(tipimovimentoId);
    }

    @Override
    public void delete(TipimovStcModelli entity) {

	tipimovStcModelliDAO.delete(entity);
    }

    @Override
    public List<TipimovStcModelli> findAll(Integer firstResult, Integer maxResult) {

	return tipimovStcModelliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public TipimovStcModelli findById(PkId id) {

	return tipimovStcModelliDAO.findById(id);
    }

    @Override
    public void insert(TipimovStcModelli entity) {

	if (validateEntity(entity)) {
	    if (validateDuplicateKey(entity)) {
		tipimovStcModelliDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(TipimovStcModelli entity) {

	if (validateEntity(entity)) {
	    if (validateDuplicateKey(entity)) {
		tipimovStcModelliDAO.update(entity);
	    }
	}
    }

    /**
     * validazione interna per valori duplicati di tipomovimento, codiceamministrazioni, fk_dyn2_modellit
     * 
     * @param entity
     * @return
     */
    private boolean validateDuplicateKey(TipimovStcModelli entity) {

	TipimovimentoId tipimovimentoId = entity.getTipimovimento().getId();
	List<TipimovStcModelli> list = tipimovStcModelliDAO.findByTipimovimento(tipimovimentoId);
	Integer codiceAmministrazione = entity.getAmministrazioni().getId().getCodice();
	Integer dyn2ModellitId = entity.getDyn2Modellit().getId().getCodice();
	Integer id = entity.getId().getCodice();
	for (TipimovStcModelli tipimovStcModelli : list) {
	    Integer amm = tipimovStcModelli.getAmministrazioni().getId().getCodice();
	    Integer d2mid = tipimovStcModelli.getDyn2Modellit().getId().getCodice();
	    Integer id2 = tipimovStcModelli.getId().getCodice();
	    if (amm.equals(codiceAmministrazione) && d2mid.equals(dyn2ModellitId)) {
		if ((id == null)) { // sono in insert
		    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		    InvalidValue iv = new InvalidValue("validator.unique.constraint", entity.getClass(), "dyn2Modellit", null, entity);
		    _ivs.add(iv);
		    this.throwValidationMessages(_ivs);
		    break;
		} else { // sono in update
		    if (!id.equals(id2)) {
			List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
			InvalidValue iv = new InvalidValue("validator.unique.constraint", entity.getClass(), "dyn2Modellit", null, entity);
			_ivs.add(iv);
			this.throwValidationMessages(_ivs);
			break;
		    }
		}
	    }
	}
	return true;
    }

    @Override
    public List<TipimovStcModelli> findByTipimovimentoAndAmministrazioni(String tipoMovimento, Integer codiceAmministrazioneStc) {

	Assert.notNull(tipoMovimento, "Il parametro tipoMovimento non può essere nullo");
	Assert.hasLength(tipoMovimento, "Il parametro tipoMovimento non può essere vuoto");
	Assert.notNull(codiceAmministrazioneStc, "Il parametro codiceAmministrazione non può essere nullo");
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipoMovimento, String.class));
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazioneStc, Integer.class));
	ft.addRestriction(fr);
	List<TipimovStcModelli> lst = tipimovStcModelliDAO.findByFilterTable(ft);
	return lst;
    }

    @Override
    public List<TipimovStcModelli> findByAmministrazione(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	filterTable.addRestriction(fr);
	return tipimovStcModelliDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
