package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwIstanzecollegateDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegate;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegateId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class VwIstanzecollegateDAOImpl extends BaseDAOImpl<VwIstanzecollegate, VwIstanzecollegateId> implements VwIstanzecollegateDAO {

    @Override
    public Class<VwIstanzecollegate> getEntityClass() {

	return VwIstanzecollegate.class;
    }

    @Override
    public List<VwIstanzecollegate> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
