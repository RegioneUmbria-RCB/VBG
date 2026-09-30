package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwAlberoprocStpDAO;
import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStp;
import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStpId;
import it.gruppoinit.pal.gp.core.service.VwAlberoprocStpService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwAlberoprocStpServiceImpl extends BaseServiceImpl<VwAlberoprocStp, VwAlberoprocStpId> implements VwAlberoprocStpService {

    @Autowired
    private VwAlberoprocStpDAO vwAlberoprocStpDAO;

    @Override
    protected Class<VwAlberoprocStp> getEntityClass() {

	return VwAlberoprocStp.class;
    }

    @Override
    public void delete(VwAlberoprocStp entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<VwAlberoprocStp> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VwAlberoprocStp findById(VwAlberoprocStpId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void insert(VwAlberoprocStp entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(VwAlberoprocStp entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<VwAlberoprocStp> findByCodiceStp(String idProcedimento) {

	return vwAlberoprocStpDAO.findByCodiceStp(idProcedimento);
    }
}
