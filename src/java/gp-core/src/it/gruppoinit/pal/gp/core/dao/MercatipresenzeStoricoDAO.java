package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.PresenzeAutorizzazioniHelper;

import java.util.List;
import java.util.Set;

public interface MercatipresenzeStoricoDAO extends BaseDAO<MercatipresenzeStorico, PkId> {

    /**
     * metodo per il recupero degli anni (colonna ANNO) presenti in MERCATIPRESENZE_STORICO
     * 
     * @return
     */
    public List<Integer> findAnniDaStorico();

    /**
     * metodo che esegue una query in MERCATIPRESENZE_STORICO per il calcolo del totale delle presenze
     * 
     * @param autorizzazione
     *            (obbligatorio)
     * @param mercato
     *            (obbligatorio)
     * @param uso
     *            (obbligatorio)
     * @param posteggio
     *            (opzionale)
     * @param catMerc
     *            (opzionale)
     * @param anno
     *            (opzionale)
     * @return
     */
    public MercatiPresenzeDTO findSommaDellePresenzeDaStorico(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno);

    public List<MercatipresenzeStorico> findByAutorizzazione(Autorizzazioni aut);

    public List<Integer> findAutorizzazioniPerCalcoloPresenze(Mercati _mercato, MercatiUso _uso, MercatiD posteggio, String catMerc, Integer anno);

    public void updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso);

    public void updateCampoPresenzaAZero(Integer codiceMercatopresenzaStorico);

    /**
     * 
     * @param lista
     *            delle autorizzazioni
     * @param anno
     *            filtra per mercatipresenzeT.anno o mercatipresenze_storico.anno --> il parametro potrebbe essere nullo
     * @return
     */
    public List<PresenzeAutorizzazioniHelper> findPresenzeSpuntistiByAutorizzazioni(Set<Integer> autsS, Integer anno);
}
