/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.VwOneriregulus;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface VwOneriregulusService {

    /**
     * Metodo per recuperare dalla vista le righe che hanno un determinato codicefiscale o partita iva
     * 
     * @param codiceFiscale
     * @return
     */
    public List<VwOneriregulus> getDebtSituationOneriRegulus(String codiceFiscale);
}
