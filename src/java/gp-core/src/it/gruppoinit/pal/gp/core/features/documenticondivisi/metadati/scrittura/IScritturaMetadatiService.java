package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.scrittura;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public interface IScritturaMetadatiService {

    /**
     * La funzionalità permette di creare un file XML a partire dalla lista dei metadati e lo salva in un percorso FTP
     * ottenuto dalla verticalizzazione DOCUMENTI_CONDIVISI
     * 
     * @param metadati
     * @param nomeFile
     * @param percorsoRelativo
     */
    void scriviSuFTP(List<DocumentiCondivisiMetadato> metadati, String nomeFile, String percorsoRelativo);
}
