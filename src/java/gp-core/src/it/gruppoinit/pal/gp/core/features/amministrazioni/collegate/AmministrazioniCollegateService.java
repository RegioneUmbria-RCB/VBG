package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.AmmCollComuneBean;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.AmministrazioneCollegataBean;

public interface AmministrazioniCollegateService {

    /**
     * Data una amministrazione madre ed un codice comune verifica se presente una amministrazione figlia
     * 
     * @param codiceAmministrazione
     * @param codiceComune
     * @return l'amministrazione figlia trovata o null
     */
    public Amministrazioni findCollegataByAmministrazioneAndComune(Integer codiceAmministrazione, String codiceComune);

    /**
     * Trova tutte le configurazioni per l'amministrazione madre
     * 
     * @param codiceAmministrazione
     * @return
     */
    public List<AmministrazioneCollegataBean> findByAmministrazione(Integer codiceAmministrazione);

    /**
     * Trova tutte le configurazioni per l'amministrazione madre e quella collegata
     * 
     * @param codiceAmministrazione
     * @return
     */
    public List<AmministrazioneCollegataBean> findByAmministrazioneCollegata(Integer codiceAmministrazione, Integer codiceAmministrazioneCollegata);

    /**
     * salva una configurazione
     * 
     * @param codiceAmministrazione
     * @param codicesottoamministrazione
     * @param codiceComune
     */
    public void salva(Integer codiceAmministrazione, Integer codicesottoamministrazione, String codiceComune);

    /**
     * elimina una configurazione
     * 
     * @param codiceAmministrazione
     * @param codicesottoamministrazione
     * @param codiceComune
     */
    public void elimina(Integer codiceAmministrazione, Integer codicesottoamministrazione, String codiceComune);

    /**
     * 
     * Torna la lista dei comuni, presa da comuniassociati che non sono stati ancora usati per questa amministrazione
     * madre
     * 
     * @param codiceAmministrazione
     * @return
     */
    public List<AmmCollComuneBean> comuniDisponibili(Integer codiceAmministrazione);

    public List<Integer> findCodiciAmmCollegate(Integer idAmministrazione);
}
