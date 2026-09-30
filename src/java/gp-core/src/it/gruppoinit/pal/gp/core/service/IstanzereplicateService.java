package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzereplicate;
import it.gruppoinit.pal.gp.core.domain.IstanzereplicateId;

public interface IstanzereplicateService extends BaseService<Istanzereplicate, IstanzereplicateId> {

    /**
     * Trova tutte le istanze che sono state generate da una istanza padre ordinate per figlia.codiceistanza asc
     * 
     * @param istanzaPadre
     * @return
     */
    public List<Istanze> findIstanzeReplicate(Istanze istanzaPadre);

    /**
     * Trova l'istanza che ha generato le n° repliche o null se l'istanza non è stata creata
     * 
     * @param istanzaFiglia
     * @return
     */
    public Istanze findIstanzaPadre(Istanze istanzaFiglia);

    /**
     * Controlla se l'istanza appartiene ad una catena di repliche(come padre o come figlia).
     * 
     * @param istanza
     * @return
     */
    public Istanzereplicate findSeIstanzaReplicata(Istanze istanza);
}
