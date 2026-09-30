/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PecInboxDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PecInboxId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author francol
 * 
 */
@Repository
public class PecInboxDAOImpl extends BaseDAOImpl<PecInbox, PecInboxId> implements PecInboxDAO {

    @Override
    public List<PecInbox> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "pecDate", DAOOrderTypeEnum.DESC);
    }

    @Override
    public Class<PecInbox> getEntityClass() {

	return PecInbox.class;
    }
}
