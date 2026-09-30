package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcClassisuperficiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcClassisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcClassisuperficiDAOImpl extends BaseDAOImpl<CcClassisuperfici, PkId> implements CcClassisuperficiDAO {

    @Override
    public Class<CcClassisuperfici> getEntityClass() {

	return CcClassisuperfici.class;
    }

    @Override
    public List<CcClassisuperfici> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "classe", DAOOrderTypeEnum.ASC);
    }
}
