/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloResponsabiliflussiDAO;
import it.gruppoinit.pal.gp.core.domain.ProtocolloResponsabiliflussi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloResponsabiliflussiId;
import it.gruppoinit.pal.gp.core.service.ProtocolloResponsabiliflussiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class ProtocolloResponsabiliflussiServiceImpl extends BaseServiceImpl<ProtocolloResponsabiliflussi, ProtocolloResponsabiliflussiId> implements
	ProtocolloResponsabiliflussiService {

    private ProtocolloResponsabiliflussiDAO protocolloResponsabiliflussiDAO;

    @Autowired
    public void setProtocolloResponsabiliflussiDAO(ProtocolloResponsabiliflussiDAO protocolloResponsabiliflussiDAO) {

	this.protocolloResponsabiliflussiDAO = protocolloResponsabiliflussiDAO;
    }

    @Override
    protected Class<ProtocolloResponsabiliflussi> getEntityClass() {

	return ProtocolloResponsabiliflussi.class;
    }

    @Override
    public void delete(ProtocolloResponsabiliflussi entity) {

	protocolloResponsabiliflussiDAO.delete(entity);
    }

    @Override
    public List<ProtocolloResponsabiliflussi> findAll(Integer firstResult, Integer maxResult) {

	return protocolloResponsabiliflussiDAO.findAll(null, null);
    }

    @Override
    public ProtocolloResponsabiliflussi findById(ProtocolloResponsabiliflussiId id) {

	return protocolloResponsabiliflussiDAO.findById(id);
    }

    @Override
    public void insert(ProtocolloResponsabiliflussi entity) {

	if (validateEntity(entity)) {
	    protocolloResponsabiliflussiDAO.insert(entity);
	}
    }

    @Override
    public void update(ProtocolloResponsabiliflussi entity) {

	if (validateEntity(entity)) {
	    protocolloResponsabiliflussiDAO.update(entity);
	}
    }
}
