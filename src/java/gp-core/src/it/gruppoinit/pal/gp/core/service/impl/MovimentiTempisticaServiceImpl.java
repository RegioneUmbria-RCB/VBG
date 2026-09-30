package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentiTempisticaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiTempistica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MovimentiTempisticaService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MovimentiTempisticaServiceImpl extends BaseServiceImpl<MovimentiTempistica, PkId> implements MovimentiTempisticaService {

    private MovimentiTempisticaDAO movimentitempisticaDAO;

    @Autowired
    public void setMovimentiTempisticaDAO(MovimentiTempisticaDAO movimentitempisticaDAO) {

	this.movimentitempisticaDAO = movimentitempisticaDAO;
    }

    @Override
    protected Class<MovimentiTempistica> getEntityClass() {

	return MovimentiTempistica.class;
    }

    @Override
    public List<MovimentiTempistica> findAll(Integer firstResult, Integer maxResult) {

	return movimentitempisticaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MovimentiTempistica entity) {

	if (validateEntity(entity)) {
	    movimentitempisticaDAO.insert(entity);
	}
    }

    @Override
    public MovimentiTempistica findById(PkId id) {

	return movimentitempisticaDAO.findById(id);
    }

    @Override
    public void update(MovimentiTempistica entity) {

	if (validateEntity(entity)) {
	    movimentitempisticaDAO.update(entity);
	}
    }

    @Override
    public void delete(MovimentiTempistica entity) {

	if (isDeleteAllowed(entity)) {
	    movimentitempisticaDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(MovimentiTempistica entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<MovimentiTempistica> findByFilterTable(FilterTable filterTable) {

	return movimentitempisticaDAO.findByFilterTable(filterTable);
    }

    @Override
    public MovimentiTempistica findByMovimentoApertura(Movimenti entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoByFkAperturaId", entity.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	List<MovimentiTempistica> result = movimentitempisticaDAO.findByFilterTable(ft);
	if (result.size() > 0) {
	    if (result.size() == 1) {
		return result.get(0);
	    } else {
		throw new BusinessValidationException("Il metodo findByMovimentoApertura per il movimento [" + entity.getId()
			+ "] ha ritornato più di un record");
	    }
	}
	return null;
    }

    @Override
    public MovimentiTempistica findByMovimentoChiusura(Movimenti entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoByFkChiusuraId", entity.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	List<MovimentiTempistica> result = movimentitempisticaDAO.findByFilterTable(ft);
	if (result.size() > 0) {
	    if (result.size() == 1) {
		result.get(0);
	    } else {
		throw new BusinessValidationException("Il metodo findByMovimentoApertura per il movimento [" + entity.getId()
			+ "] ha ritornato più di un record");
	    }
	}
	return null;
    }

    @Override
    public List<MovimentiTempistica> findByMovimentiPerEvento(Istanze istanza, TIPO_EVENTO tipoEvento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), "movimentoByFkApertura", Integer.class));
	fr.addFilterField(FilterUtils.equals("evento", tipoEvento.toString(), String.class));
	ft.addRestriction(fr);
	List<MovimentiTempistica> result = movimentitempisticaDAO.findByFilterTable(ft);
	return result;
    }

    @Override
    public List<MovimentiTempistica> findByIstanza(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), "movimentoByFkApertura", Integer.class));
	ft.addRestriction(fr);
	List<MovimentiTempistica> result = movimentitempisticaDAO.findByFilterTable(ft);
	return result;
    }

    @Override
    public int findDurataProrogaPerIstanza(Integer codiceIstanza) {

	return movimentitempisticaDAO.findDurataProrogaPerIstanza(codiceIstanza);
    }

    @Override
    public int countByFilterTable(FilterTable ft) {

	return movimentitempisticaDAO.countRecord(ft);
    }

    @Override
    public Date findDataUltimaInterruzione(Integer codiceIstanza) {

	return movimentitempisticaDAO.findDataUltimaInterruzione(codiceIstanza);
    }

    @Override
    public boolean isIstanzaInterrotta(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, "movimentoByFkApertura", Integer.class));
	fr.addFilterField(FilterUtils.equals("evento", MovimentiTempisticaService.TIPO_EVENTO.I.toString(), String.class));
	fr.addFilterField(FilterUtils.isNull("movimentoByFkChiusuraId"));
	ft.addRestriction(fr);
	return movimentitempisticaDAO.existsRecords(ft);
    }
}
