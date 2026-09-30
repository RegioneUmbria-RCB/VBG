package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;

/**
 * 
 * @author gianpaolot
 */
public interface IstanzecollegateDAO extends BaseDAO<Istanzecollegate, PkId> {

    /**
     * Lista di istanze collegate filtrate per idcomune
     * 
     */
    public List<Istanzecollegate> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna il campo ordine max per il progressivo passato
     * 
     * @param progressivo
     * @return
     */
    public Integer maxOrdineByProgressivo(Integer progressivo);

    /**
     * Ritorna il campo progressivo max per il comune in esame
     * 
     * @param progressivo
     * @return
     */
    public Integer maxProgressivo();

    /**
     * <pre>
     * Il metodo restituisce un oggetto IstanzeCollegateHelper con popolate le liste :
     * 
     *  1- listaIstanzePrecedenti: lista di istanze che sono state collegate all'istanza in esame.
     *  2- listaIstanzeSuccessive: lista delle istanze a cui l'istanza in esame è stata collegata.
     * 
     * Logica di recupero:
     * 
     * <b>listaIstanzePrecedenti :<b>  Ricerco tutti i record in istanze collegate che hanno come campo istanza (CODICEISTANZA) 
     * 				       l'istanza passata e recupero per ogni record il valore del campo istanzaDacollegare
     * 				       (CODICEISTANZACOLLEGATA).Sulla proprietà istanzaDacollegare è applicata una condizione distinc 
     * 				       in quanto ad un istanza può essere collegata più volta la stessa istanza, ma su catene differenti
     *                                 (progressivo differente). Per ogni codice trovato verrà fatta un ricerca per id sulla tabella istanze
     *                                 e inserita sulla lista.
     * 
     *  <b>listaIstanzeSuccessive :<b> Ricerco tutti i record in istanze collegate che hanno come campo istanzaDacollegare 
     *  			       (CODICEISTANZACOLLEGATA) l'istanza passata e recupero per ogni record il valore del 
     *  			       campo istanza (CODICEISTANZA). Sulla proprietà istanza è applicata una condizione distinc 
     * 				       in quanto  un istanza può essere collegata più volte alla stessa istanza, ma su catene differenti
     *                                 (progressivo differente). Per ogni codice trovato verrà fatta un ricerca per id sulla tabella istanze
     *                                 e inserita sulla lista.                                
     * 
     * &#64;param istanza
     * &#64;return
     * 
     * </pre>
     */
    public IstanzecollegateHelper getSchemaPrecedentiAndSuccessive(Istanze istanza);

    public List<Istanzecollegate> findByIdcomuneEProgressivo(String idComune, int progressivo);

    public List<IstanzecollegateHelper> findIstanzecollegateByIstanzaPerVisualizzazione(Integer codiceIstanza);
}
