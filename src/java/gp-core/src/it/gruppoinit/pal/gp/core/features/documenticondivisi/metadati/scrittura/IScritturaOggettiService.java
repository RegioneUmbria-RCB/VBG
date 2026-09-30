package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.scrittura;

public interface IScritturaOggettiService {

    /**
     * La funzionalità legge come InputStream un oggetto ( tramite il service degli Oggetti ) e lo salva in un percorso
     * FTP ottenuto dalla verticalizzazione DOCUMENTI_CONDIVISI
     * 
     * @param codiceOggetto
     * @param nomeFile
     * @param percorsoRelativo
     */
    void scriviSuFTP(Integer codiceOggetto, String nomeFile, String percorsoRelativo);
}
