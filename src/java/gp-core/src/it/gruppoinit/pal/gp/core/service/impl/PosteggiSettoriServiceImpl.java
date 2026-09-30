package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.PosteggiSettoriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PosteggiSettori;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.PosteggiSettoriService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosteggiSettoriServiceImpl extends BaseServiceImpl<PosteggiSettori, PkId> implements PosteggiSettoriService {

    private PosteggiSettoriDAO posteggiSettoriDAO;

    @Autowired
    public void setPosteggiSettoriDAO(PosteggiSettoriDAO posteggiSettoriDAO) {

	this.posteggiSettoriDAO = posteggiSettoriDAO;
    }

    @Override
    public void insert(PosteggiSettori entity) {

	if (validateEntity(entity)) {
	    posteggiSettoriDAO.insert(entity);
	}
    }

    @Override
    public void update(PosteggiSettori entity) {

	if (validateEntity(entity)) {
	    posteggiSettoriDAO.update(entity);
	}
    }

    @Override
    public void delete(PosteggiSettori entity) {

	if (isDeleteAllowed(entity)) {
	    posteggiSettoriDAO.delete(entity);
	}
    }

    @Override
    public List<PosteggiSettori> findAll(Integer firstResult, Integer maxResult) {

	return posteggiSettoriDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "settore", DAOOrderTypeEnum.ASC);
    }

    @Override
    public PosteggiSettori findById(PkId id) {

	return posteggiSettoriDAO.findById(id);
    }

    @Override
    protected Class<PosteggiSettori> getEntityClass() {

	return PosteggiSettori.class;
    }

    @Override
    public List<PosteggiSettori> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("settore", "%" + textToSearch + "%"));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.order("settore", OrderTypeEnum.ASC));
	return posteggiSettoriDAO.findByFilterTable(ft);
    }
}
