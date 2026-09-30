package it.gruppoinit.pal.gp.core.features.anagrafetributaria;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AtEsitoGruppo;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ListaEsitiTracciatoBean;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.TestataEsitoTracciatoModel;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnTribDettaglioRigheTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnagrafeTribRigheEsito;

public interface AnagrafeTributariaService {

    /**
     * Torna le informazioni per renderizzare la pagina con le informazioni di testata
     * 
     * @param idTestata
     *            l'identificativo del record AT_TESTATA
     * @return
     */
    public TestataEsitoTracciatoModel getTestataById(Integer idTestata);

    /**
     * Inserisce un nuovo tracciato ed esito tracciati
     * 
     * @param descrizione
     * @param codiceResponsabile
     * @param contenutoFileEsiti
     * @param contenutoFileTracciato
     * @return
     */
    public TestataEsitoTracciatoModel salvaNuovoEsitoTracciato(String descrizione, Integer codiceResponsabile, String contenutoFileEsiti,
	    String contenutoFileTracciato);

    /**
     * metodo per assegnare ad un gruppo un codice istanza
     * 
     * @param idGruppo
     * @param codiceIstanza
     * @return 
     */
    public AtEsitoGruppo aggiornaGruppoConIstanza(Integer idGruppo, Integer codiceIstanza);

    /**
     * Metodo per aggiornare come validata o meno una riga di errore
     * 
     * @param idRigaErrore
     * @param segnaValido
     */
    public void aggiornaValidaErrore(Integer idRigaErrore, boolean segnaValido);

    public List<ListaEsitiTracciatoBean> findEsitiSalvati(Integer offset, Integer limit);

    public void delete(Integer codice);

    public AnagrafeTribRigheEsito getRigheEsito(Integer idTestata, Integer offset, Integer limit);

    public List<AnTribDettaglioRigheTracciato> getDettaglioRigheTracciato(Integer idGruppo);
}
