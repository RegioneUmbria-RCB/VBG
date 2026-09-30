package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;

public interface VerticalizzazioniDAO extends BaseDAO<Verticalizzazioni, PkId> {

    /**
     * recupera la verticalizzazione specificata dal valore <b>modulo</b>, controlla in sequenza: <br>
     * 1)se è presente per il software corrente <br>
     * 2)se è presente per il software TT <br>
     * (non controlla se è attiva)
     * 
     * @param modulo
     * @return il record della tabella verticalizzazioni
     */
    public Verticalizzazioni findByModuloEComuneESoftware(String modulo, String codiceComune, String software);

    /**
     * recupera la lista di verticalizzazione per una verticalizzazione base passata
     * 
     * @param verticalizzazionibase
     * @return
     */
    public List<Verticalizzazioni> findByVerticalizzazionibase(Verticalizzazionibase verticalizzazionibase);

    public List<Verticalizzazioni> findAttivazioni(String modulo);
}
