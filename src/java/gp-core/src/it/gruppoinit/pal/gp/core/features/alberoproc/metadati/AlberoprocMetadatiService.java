package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocMetadati;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public interface AlberoprocMetadatiService {

    /**
     * Ritorna la lista dei record ordinati per chiave
     * 
     * @param scId
     * @return
     */
    public ConfigurazioneMetadati findByAlberoproc(Integer scId);

    /**
     * Elimina il metadato configurato sulla voce dell'albero
     * 
     * @param codiceInterventoProc
     * @param chiave
     */
    public void delete(Integer codiceInterventoProc, String chiave);

    /**
     * Effettua l'inserimento del metadato
     * 
     * @param metadato
     */
    public void insert(MetadatoAlberoproc metadato);

    /**
     * Ritorna un metadato ricercandolo per intervento e chiave in maniera ricorsiva
     * 
     * @param codiceInterventoProc
     * @param chiave
     * @return
     */
    public AlberoprocMetadati findByInterventoRicorsivoEChiave(Integer codiceInterventoProc, String chiave);

    /**
     * Ritorna il valore di un metadato ricercandolo per intervento e chiave in maniera ricorsiva
     * 
     * @see AlberoprocMetadatiService#findByInterventoRicorsivoEChiave(Integer, String)
     * 
     *      torna nullo se non trovato
     * 
     * @param codiceInterventoProc
     * @param chiave
     * @return
     */
    public String findValoreByInterventoRicorsivoEChiave(Integer codiceInterventoProc, String chiave);

    /**
     * Ritorna la lista dei metadati configurati per la chiave passata a prescindere dal software di albero proc Viene
     * utilizzato principalmente per verificare se è stato fatto un override di un parametro di verticalizzazione
     * 
     * @param chiave
     * @return
     */
    public List<AlberoprocMetadati> findAllByChiave(String chiave);

    /**
     * Verifica se ci sono dei metadati configurati per la chiave passata a prescindere dal software di albero proc
     * Viene utilizzato principalmente per verificare se è stato fatto un override di un parametro di verticalizzazione
     * 
     * @param chiave
     * @return
     */
    public boolean metadatiPresenti(List<String> chiavi);

    public List<String> listaMetadatiConfigurabili();
}
