package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;

public interface PaySoggettiDebitoriDAO extends BaseDAO<PaySoggettiDebitori, PkId> {

    public List<PaySoggettiDebitori> findByCodiceFiscale(String cfpi, Boolean attivo);

    //public PaySoggettiDebitori findAttivoByCodiceFiscale(String cfpi);
}
