package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.QrxmlBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.QrxmlBase;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.QrxmlBaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QrxmlBaseServiceImpl extends BaseServiceImpl<QrxmlBase, PkId> implements QrxmlBaseService {

    private QrxmlBaseDAO qrxmlBaseDAO;

    @Autowired
    public void setQrxmlBaseDAO(QrxmlBaseDAO qrxmlBaseDAO) {

	this.qrxmlBaseDAO = qrxmlBaseDAO;
    }

    @Override
    public List<QrxmlBase> findByDescrizione(String term) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.like("codice", term + "%"));
	fr.addFilterField(FilterUtils.like("titolo", "%" + term + "%"));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codice"));
	ft.addOrder(FilterUtils.orderAsc("titolo"));
	return qrxmlBaseDAO.findByFilterTable(ft);
    }

    @Override
    public void insert(QrxmlBase entity) {

	if (validateEntity(entity)) {
	    qrxmlBaseDAO.insert(entity);
	}
    }

    @Override
    public void update(QrxmlBase entity) {

	if (validateEntity(entity)) {
	    qrxmlBaseDAO.update(entity);
	}
    }

    @Override
    public void delete(QrxmlBase entity) {

	qrxmlBaseDAO.delete(entity);
    }

    @Override
    public List<QrxmlBase> findAll(Integer firstResult, Integer maxResult) {

	return qrxmlBaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public QrxmlBase findById(PkId id) {

	return qrxmlBaseDAO.findById(id);
    }

    @Override
    protected Class<QrxmlBase> getEntityClass() {

	return QrxmlBase.class;
    }
}
