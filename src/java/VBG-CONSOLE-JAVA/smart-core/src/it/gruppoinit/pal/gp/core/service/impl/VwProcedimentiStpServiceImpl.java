package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwProcedimentiStpDAO;
import it.gruppoinit.pal.gp.core.domain.VwProcedimentiStp;
import it.gruppoinit.pal.gp.core.domain.VwProcedimentiStpId;
import it.gruppoinit.pal.gp.core.service.VwProcedimentiStpService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwProcedimentiStpServiceImpl extends BaseServiceImpl<VwProcedimentiStp, VwProcedimentiStpId> implements VwProcedimentiStpService {

    @Autowired
    private VwProcedimentiStpDAO vwProcedimentiStpDAO;

    @Override
    protected Class<VwProcedimentiStp> getEntityClass() {

	return VwProcedimentiStp.class;
    }

    @Override
    public void delete(VwProcedimentiStp entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<VwProcedimentiStp> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VwProcedimentiStp findById(VwProcedimentiStpId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void insert(VwProcedimentiStp entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(VwProcedimentiStp entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<VwProcedimentiStp> findByCodiceStp(String codiceStp) {

	return vwProcedimentiStpDAO.findByCodiceStp(codiceStp);
    }

    @Override
    public List<VwProcedimentiStp> findByCodiceInventario(Integer codiceInventario) {

	return vwProcedimentiStpDAO.findByCodiceInventario(codiceInventario);
    }
}
