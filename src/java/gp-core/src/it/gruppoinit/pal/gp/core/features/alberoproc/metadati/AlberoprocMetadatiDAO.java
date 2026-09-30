package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocMetadati;

public interface AlberoprocMetadatiDAO extends BaseDAO<AlberoprocMetadati, AlberoprocMetadatiId> {

    /**
     * Effettua la cancellazione di un metadato dell'albero
     * 
     * @param codiceInterventoProc
     * @param chiave
     */
    public void delete(Integer codiceInterventoProc, String chiave);

    /**
     * Ritorna un metadato ricercandolo per intervento e chiave
     * 
     * @param codiceInterventoProc
     * @param chiave
     * @return
     */
    public AlberoprocMetadati findByInterventoEChiave(Integer codiceInterventoProc, String chiave);

    /**
     * Ritorna un metadato ricercandolo per intervento e chiave in maniera ricorsiva
     * 
     * @param codiceInterventoProc
     * @param chiave
     * @return
     */
    public AlberoprocMetadati findByInterventoRicorsivoEChiave(Integer codiceInterventoProc, String chiave);

    /**
     * Ritorna la lista dei metadati configurati per l'intervento ed eventualmente quelli degli interventi padre Vengono
     * esclusi eventuali metadati sovrascritti secondo la logica della "risalita a salmone"
     * 
     * @param codiceInterventoProc
     * @return
     */
    public Set<AlberoprocMetadati> findAllByIntervento(Integer codiceInterventoProc);

    /**
     * Ritorna la lista dei metadati configurati per la chiave passata a prescindere dal software di albero proc Viene
     * utilizzato principalmente per verificare se è stato fatto un override di un parametro di verticalizzazione
     * 
     * @param chiave
     * @return
     */
    public List<AlberoprocMetadati> findAllByChiave(String chiave);

    /**
     * Effettua l'inserimento del metadato
     * 
     * @param codiceInterventoProc
     * @param chiave
     * @param valore
     */
    public void insert(Integer codiceInterventoProc, String chiave, String valore);

    /**
     * Verifica se ci sono dei metadati configurati per la chiave passata a prescindere dal software di albero proc
     * Viene utilizzato principalmente per verificare se è stato fatto un override di un parametro di verticalizzazione
     * 
     * @param chiave
     * @return
     */
    public boolean metadatiPresenti(List<String> chiavi);
}
