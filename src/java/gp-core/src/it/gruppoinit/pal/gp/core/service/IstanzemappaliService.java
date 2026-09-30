package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface IstanzemappaliService extends BaseService<Istanzemappali, PkId> {

    /**
     * Metodo per ricercare l'istanzamappale primaria collegata ad una istanza
     * 
     * @param istanza
     * @return
     */
    public Istanzemappali findByPrimarioIstanza(Istanze istanza);

    /**
     * Ritorna la lista dei mappali associati allo stradario
     * 
     * @param codice
     * @return
     */
    public List<Istanzemappali> findByIstanzaStradario(Integer codiceIstanzaStradario);

    /**
     * Recupera tutti i mappali legati ad un istanza
     * 
     * @param codice
     * @return
     */
    public List<Istanzemappali> findByIstanza(Integer codice);
}
