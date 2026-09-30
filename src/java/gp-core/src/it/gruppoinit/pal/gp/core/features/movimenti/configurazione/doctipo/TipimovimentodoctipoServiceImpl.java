/**
 * 
 */
package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.TipimovimentodoctipoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * @author lucap
 * 
 */
@Service
public class TipimovimentodoctipoServiceImpl extends BaseServiceImpl<Tipimovimentodoctipo, TipimovimentodoctipoId>
	implements TipimovimentodoctipoService {

    private TipimovimentodoctipoDAO tipimovimentodoctipoDAO;

    @Autowired
    public void setTipimovimentodoctipoDAO(TipimovimentodoctipoDAO tipimovimentodoctipoDAO) {

	this.tipimovimentodoctipoDAO = tipimovimentodoctipoDAO;
    }

    @Override
    protected Class<Tipimovimentodoctipo> getEntityClass() {

	return Tipimovimentodoctipo.class;
    }

    @Override
    public void delete(Tipimovimentodoctipo entity) {

	if (isDeleteAllowed(entity)) {
	    tipimovimentodoctipoDAO.delete(entity);
	}
    }

    @Override
    public List<Tipimovimentodoctipo> findAll(Integer firstResult, Integer maxResult) {

	return tipimovimentodoctipoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipimovimentodoctipo findById(TipimovimentodoctipoId id) {

	return tipimovimentodoctipoDAO.findById(id);
    }

    @Override
    public void insert(Tipimovimentodoctipo entity) {

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    tipimovimentodoctipoDAO.insert(entity);
	}
    }

    @Override
    protected boolean validateEntity(Tipimovimentodoctipo entity) {

	super.validateEntity(entity);
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	//1. Verifica della congruenza tra il flag generazione automatica e la fase di generazione
	if (Boolean.TRUE.equals(entity.getFlgGeneraAut()) && StringUtils.isBlank(entity.getFaseEsecuzione())) {
	    ivs.add(new InvalidValue("tipimovimento.label.dettaglio_tipimovimentodoctipo.service_error.fase_esecuzione", entity.getClass(),
		    "faseEsecuzione", entity.getFaseEsecuzione(), entity));
	}
	if (Boolean.FALSE.equals(entity.getFlgGeneraAut()) && !StringUtils.isBlank(entity.getFaseEsecuzione())) {
	    ivs.add(new InvalidValue(
		    "Errore di sistema! E' stata indicata la fase di esecuzione per un documento che non deve essere generato automaticamente",
		    entity.getClass(), "faseEsecuzione", entity.getFaseEsecuzione(), entity));
	}
	if (ivs.size() > 0) {
	    this.throwValidationMessages(ivs);
	}
	// §§§END§§§
	return true;
    }

    @Override
    public void update(Tipimovimentodoctipo entity) {

	if (validateEntity(entity)) {
	    tipimovimentodoctipoDAO.update(entity);
	}
    }

    @Override
    public Tipimovimentodoctipo findByTipoMovimentoAndTipoLettera(String codicemovimento, Integer codicelettera) {

	return tipimovimentodoctipoDAO.findByTipoMovimentoAndTipoLettera(codicemovimento, codicelettera);
    }

    /**
     * Il metodo deve controllare che non esiste già un record con il codice lettera già esistente che stiamo passando
     * 
     * @param entity
     * @return
     */
    protected boolean isInsertAllowed(Tipimovimentodoctipo entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Tipimovimentodoctipo objectDB = tipimovimentodoctipoDAO.findByTipoMovimentoAndTipoLettera(
		entity.getTipomovimento().getId().getTipomovimento(), entity.getId().getCodicelettera().intValue());
	if (objectDB != null) {
	    _ivs.add(new InvalidValue("tipimovimento.service_error.duplicate_doc_tipo", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public List<Tipimovimentodoctipo> findByTipoMovimento(String tipoMovimento) {

	if (tipoMovimento == null) {
	    throw new IllegalArgumentException("findByTipoMovimento: il parametro tipoMovimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction tipomov = new FilterRestriction();
	tipomov.addFilterField(FilterUtils.equals("id.tipomovimento", tipoMovimento, String.class));
	filterTable.addRestriction(tipomov);
	return tipimovimentodoctipoDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Tipimovimentodoctipo> findByLetteretipo(Letteretipo letteretipo, int firstResult, int maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codicelettera", letteretipo.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine", "tipomovimento.software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "tipomovimento.software"));
	ft.addOrder(FilterUtils.orderAsc("movimento", "tipomovimento"));
	return tipimovimentodoctipoDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<Integer> findCodiciLettereAutomaticheByTipoMovimentoAndFase(String tipoMovimento, FasiDiEsecuzioneEnum faseEsecuzione) {

	if (StringUtils.isEmpty(tipoMovimento)) {
	    throw new IllegalArgumentException("findCodiciLettereAutomaticheByTipoMovimentoAndFase: il parametro tipoMovimento non è stato indicato");
	}
	if (faseEsecuzione == null) {
	    throw new IllegalArgumentException(
		    "findCodiciLettereAutomaticheByTipoMovimentoAndFase: il parametro faseEsecuzione non è stato indicato");
	}
	return this.tipimovimentodoctipoDAO.findCodiciLettereAutomaticheByTipoMovimentoAndFase(tipoMovimento, faseEsecuzione);
    }
}
