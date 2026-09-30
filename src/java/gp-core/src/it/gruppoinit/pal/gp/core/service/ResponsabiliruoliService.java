/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliruoliId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface ResponsabiliruoliService extends BaseService<Responsabiliruoli, ResponsabiliruoliId> {

    /**
     * Il metodo torna i ruoli associati al responsabile per l'idcomune passato. Se idcomune è nullo o Stringa vuota
     * viene preso l'idcomune dell'ORMHelper. I record sono ordinati per codice ruolo. Se il codiceResponsabile non
     * viene passato allora viene rilanciata una iilegalargumentException.
     * 
     * @param codiceResponsabile
     * @param idcomune
     * @return
     */
    public List<Responsabiliruoli> findByResponsabile(Integer codiceResponsabile, String idcomune);

    /**
     * Il metodo torna i codici dei ruoli associati al responsabile per l'idcomune passato. Se idcomune è nullo o
     * Stringa vuota viene preso l'idcomune dell'ORMHelper. I record sono ordinati per codice ruolo. Se il
     * codiceResponsabile non viene passato allora viene rilanciata una iilegalargumentException.
     * 
     * @param codiceResponsabile
     * @return
     */
    public List<Integer> findCodiciRuoloByResponsabile(Integer codiceResponsabile);

    public boolean userHasRole(Integer codiceResponsabile, String... role);
}
