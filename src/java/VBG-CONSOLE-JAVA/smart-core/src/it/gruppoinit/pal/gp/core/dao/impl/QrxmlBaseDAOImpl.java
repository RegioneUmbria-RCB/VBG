package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.QrxmlBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.QrxmlBase;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class QrxmlBaseDAOImpl extends BaseDAOImpl<QrxmlBase, PkId> implements QrxmlBaseDAO {

    @Override
    public Class<QrxmlBase> getEntityClass() {

	return QrxmlBase.class;
    }

    @Override
    public List<QrxmlBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "codice", DAOOrderTypeEnum.ASC);
    }
}
