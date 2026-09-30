package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;

public interface PayRegcausaliParametriService extends BaseService<PayRegcausaliParametri, PkId> {
    //    /**
    //     * Può tornare nullo
    //     * 
    //     * @param idcausale
    //     * @param chiave
    //     * @return
    //     */
    //    public String findValoreByCausaleAndChiave(Integer idcausale, String chiave);

    public List<PayRegcausaliParametri> findByCausale(Integer idCausale);
}
