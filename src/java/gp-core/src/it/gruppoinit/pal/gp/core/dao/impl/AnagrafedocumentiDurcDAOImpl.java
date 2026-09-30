package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafedocumentiDurcDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AnagrafedocumentiDurc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AnagrafedocumentiDurcDAOImpl extends BaseDAOImpl<AnagrafedocumentiDurc, PkId> implements AnagrafedocumentiDurcDAO {

    @Override
    public Class<AnagrafedocumentiDurc> getEntityClass() {

	return AnagrafedocumentiDurc.class;
    }

    @Override
    public List<AnagrafedocumentiDurc> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "issuedate", DAOOrderTypeEnum.ASC);
    }
}
