package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayPosDebMassive;

public interface PayPosDebMassiveDAO extends BaseDAO<PayPosDebMassive, PkId> {

    Map<String, List<Integer>> findPosizioniDaElaborare();
}
