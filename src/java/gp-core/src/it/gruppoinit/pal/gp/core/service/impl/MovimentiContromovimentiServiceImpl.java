package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentiContromovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MovimentiContromovimentiService;

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
public class MovimentiContromovimentiServiceImpl extends BaseServiceImpl<MovimentiContromovimenti, PkId> implements MovimentiContromovimentiService {

    private MovimentiContromovimentiDAO movimenticontromovimentiDAO;

    @Autowired
    public void setMovimentiContromovimentiDAO(MovimentiContromovimentiDAO movimenticontromovimentiDAO) {

	this.movimenticontromovimentiDAO = movimenticontromovimentiDAO;
    }

    @Override
    protected Class<MovimentiContromovimenti> getEntityClass() {

	return MovimentiContromovimenti.class;
    }

    @Override
    public List<MovimentiContromovimenti> findAll(Integer firstResult, Integer maxResult) {

	return movimenticontromovimentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MovimentiContromovimenti entity) {

	if (validateEntity(entity)) {
	    movimenticontromovimentiDAO.insert(entity);
	}
    }

    @Override
    public MovimentiContromovimenti findById(PkId id) {

	return movimenticontromovimentiDAO.findById(id);
    }

    @Override
    public void update(MovimentiContromovimenti entity) {

	if (validateEntity(entity)) {
	    movimenticontromovimentiDAO.update(entity);
	}
    }

    @Override
    public void delete(MovimentiContromovimenti entity) {

	if (isDeleteAllowed(entity)) {
	    movimenticontromovimentiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(MovimentiContromovimenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<MovimentiContromovimenti> findByFilterTable(FilterTable filterTable) {

	return movimenticontromovimentiDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<MovimentiContromovimenti> findByMovimentoByFkFiglio(Movimenti movimentoByFkFiglio) {

	if (EntityUtils.getNestedProperty(movimentoByFkFiglio, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro movimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("movimentoByFkFiglioId", movimentoByFkFiglio.getId().getCodice(), Integer.class));
	ft.addRestriction(criterio);
	List<MovimentiContromovimenti> list = movimenticontromovimentiDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<MovimentiContromovimenti> findByMovimentoByFkPadreAndFkFiglio(Integer codiceMovPadre, Integer codiceMovFiglio) {

	if (codiceMovFiglio == null) {
	    throw new IllegalArgumentException("Il parametro movimento figlio non è valido");
	}
	if (codiceMovPadre == null) {
	    throw new IllegalArgumentException("Il parametro movimento padre non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("movimentoByFkFiglioId", codiceMovFiglio, Integer.class));
	criterio.addFilterField(FilterUtils.equals("movimentoByFkPadreId", codiceMovPadre, Integer.class));
	ft.addRestriction(criterio);
	List<MovimentiContromovimenti> list = movimenticontromovimentiDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<MovimentiContromovimenti> findByMovimentoByFkPadre(Movimenti movimentoByFkPadre) {

	if (EntityUtils.getNestedProperty(movimentoByFkPadre, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro movimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("movimentoByFkPadreId", movimentoByFkPadre.getId().getCodice(), Integer.class));
	ft.addRestriction(criterio);
	List<MovimentiContromovimenti> list = movimenticontromovimentiDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public int countByMovimentoByFkPadreAndFkFiglio(Integer codiceMovimentoPadre, Integer codiceMovimentoFiglio) {

	if (codiceMovimentoFiglio == null) {
	    throw new IllegalArgumentException("countByMovimentoByFkPadreAndFkFiglio: Il parametro movimento figlio non è valido");
	}
	if (codiceMovimentoPadre == null) {
	    throw new IllegalArgumentException("countByMovimentoByFkPadreAndFkFiglio: Il parametro movimento padre non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("movimentoByFkFiglioId", codiceMovimentoFiglio, Integer.class));
	criterio.addFilterField(FilterUtils.equals("movimentoByFkPadreId", codiceMovimentoPadre, Integer.class));
	ft.addRestriction(criterio);
	return movimenticontromovimentiDAO.countRecord(ft);
    }

    @Override
    public int countByFilterTable(FilterTable filterTable) {

	return movimenticontromovimentiDAO.countRecord(filterTable);
    }
}
