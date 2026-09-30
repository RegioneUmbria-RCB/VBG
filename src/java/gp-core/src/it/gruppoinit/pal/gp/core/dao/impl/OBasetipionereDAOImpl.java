package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OBasetipionereDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OBasetipionere;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OBasetipionereDAOImpl extends BaseDAOImpl<OBasetipionere, String> implements OBasetipionereDAO {

    @Override
    public Class<OBasetipionere> getEntityClass() {

	return OBasetipionere.class;
    }

    @Override
    public List<OBasetipionere> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
