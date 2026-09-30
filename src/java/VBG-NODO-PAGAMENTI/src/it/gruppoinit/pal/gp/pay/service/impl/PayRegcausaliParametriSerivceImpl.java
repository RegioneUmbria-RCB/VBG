package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayRegcausaliParametriDAO;
import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;
import it.gruppoinit.pal.gp.pay.service.PayRegcausaliParametriService;

@Service
public class PayRegcausaliParametriSerivceImpl extends BaseServiceImpl<PayRegcausaliParametri, PkId> implements PayRegcausaliParametriService {

    @Autowired
    PayRegcausaliParametriDAO payRegcausaliParametriDAO;

    @Override
    public void insert(PayRegcausaliParametri entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(PayRegcausaliParametri entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(PayRegcausaliParametri entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<PayRegcausaliParametri> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PayRegcausaliParametri findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }
    //    @Override
    //    public String findValoreByCausaleAndChiave(Integer idcausale, String chiave) {
    //
    //	return payRegcausaliParametriDAO.findValoreByCausaleAndChiave(idcausale, chiave);
    //    }

    @Override
    protected Class<PayRegcausaliParametri> getEntityClass() {

	return PayRegcausaliParametri.class;
    }

    @Override
    public List<PayRegcausaliParametri> findByCausale(Integer idCausale) {

	return this.payRegcausaliParametriDAO.findByCausale(idCausale);
    }
}
