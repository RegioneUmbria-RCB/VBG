package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;

public interface PayRegcausaliParametriDAO extends BaseDAO<PayRegcausaliParametri, PkId> {

    public String findValoreByCausaleAndChiave(Integer idCausale, String chiave);

    public List<PayRegcausaliParametri> findByCausale(Integer idCausale);
}
