package it.gruppoinit.pal.gp.core.dao;

import java.io.InputStream;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface OggettiDAO extends BaseDAO<Oggetti, PkId> {

    /**
     * la funzione controlla se è possibile o meno eliminare un oggetto dalla tabella oggetti
     * 
     * @param nomeTabella
     *            il nome della tabella per la quale non verificare la dipendenza
     * @param nomeColonna
     *            il nome della colonna per la quale non verificare la dipendenza
     * @param codiceOggetto
     *            la chiave primaria della tabella oggetti per la quale verificare se possibile cancellare o meno il
     *            record
     * @return
     *         <ul>
     *         <li><b>true</b> se è stato possibile cancellare il file perché non usato</li>
     *         <li><b>false</b> se non è stato possibile cancellare il file perché usato</li>
     */
    // boolean controllaCancellaOggetto(String nomeTabella, String nomeColonna, Integer codiceOggetto);
    public void updateOggetto(String nomeTabella, String nomeColonna, Integer codiceOggetto, Integer codiceOggettoNew);

    /**
     * recupera il nome del file
     * 
     * @param id
     * @return
     */
    public String findNomeById(PkId id);

    /**
     * Si occupa di recuperare le informazioni principali ( escluso il contenuto Binario ) dell'oggetto dalla base dati.
     * Serve per quelle funzionalità che non utilizzano il contenuto binario (che non viene recuperato) ad esempio
     * durante la cancellazione di un oggetto. Può tornare un oggetto nullo. Se l'oggetto viene recuperato allora è
     * svincolato dalla Sessione di Hibernate e in questo caso non deve essere riassociato come oggetto ad una entity in
     * quanto può verificarsi una perdita di dati. Deve in pratica essere usato solamente come oggetto per dei
     * controlli. Per tutte le altre operazioni va usato il metodo findById().
     */
    public Oggetti findByIdLazy(PkId id);

    public Oggetti findByIdLazyComplete(Integer codiceOggetto);

    public InputStream getInputStreamFromBLOB(Integer codiceOggetto);

    public void updatePercorsoOggetto(Integer codiceOggetto, String percorso);
}
