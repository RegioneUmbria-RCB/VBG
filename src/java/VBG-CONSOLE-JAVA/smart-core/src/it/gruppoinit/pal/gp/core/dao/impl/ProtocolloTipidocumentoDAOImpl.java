package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloTipidocumentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloTipidocumento;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ProtocolloTipidocumentoDAOImpl extends BaseDAOImpl<ProtocolloTipidocumento, PkId> implements ProtocolloTipidocumentoDAO {

    @Override
    public Class<ProtocolloTipidocumento> getEntityClass() {

	return ProtocolloTipidocumento.class;
    }

    @Override
    public List<ProtocolloTipidocumento> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
