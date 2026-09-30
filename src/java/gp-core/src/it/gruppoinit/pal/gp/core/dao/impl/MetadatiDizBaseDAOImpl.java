package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MetadatiDizBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MetadatiDizBase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MetadatiDizBaseDAOImpl extends BaseDAOImpl<MetadatiDizBase, String> implements MetadatiDizBaseDAO {

    @Override
    public Class<MetadatiDizBase> getEntityClass() {

	return MetadatiDizBase.class;
    }

    @Override
    public List<MetadatiDizBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "label", DAOOrderTypeEnum.ASC);
    }
}
