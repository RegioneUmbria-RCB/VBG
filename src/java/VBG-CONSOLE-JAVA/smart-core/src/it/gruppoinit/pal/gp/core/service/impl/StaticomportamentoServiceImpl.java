/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StaticomportamentoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Staticomportamento;
import it.gruppoinit.pal.gp.core.service.StaticomportamentoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class StaticomportamentoServiceImpl extends BaseServiceImpl<Staticomportamento, Integer> implements StaticomportamentoService {

    private StaticomportamentoDAO staticomportamentoDAO;

    @Autowired
    public void setStaticomportamentoDAO(StaticomportamentoDAO staticomportamentoDAO) {

	this.staticomportamentoDAO = staticomportamentoDAO;
    }

    @Override
    protected Class<Staticomportamento> getEntityClass() {

	return Staticomportamento.class;
    }

    @Override
    public void delete(Staticomportamento entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Staticomportamento> findAll(Integer firstResult, Integer maxResult) {

	return staticomportamentoDAO.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "comportamento", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Staticomportamento findById(Integer id) {

	return staticomportamentoDAO.findById(id);
    }

    @Override
    public void insert(Staticomportamento entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Staticomportamento entity) {

	throw new NotImplementedException();
    }
}
