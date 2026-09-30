package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemStatusBean;

public interface OggettiFileSystemDAO extends BaseDAO<Oggetti, PkId> {

    /**
     * Questo metodo scorre i record della tabella OGGETTI che hanno il campo PERCORSO impostato a null.<br>
     * Se OGGETTO != null il file si trova memorizzato nel campo BLOB del DB. In questo caso la procedura trasferisce il
     * documento dal campo BLOB al filesystem e crea il file in un percorso generato secondo la seguente regola:
     * <ol>
     * <li>PAD a zeri a sinistra di CODICEOGGETTO fino a 10 caratteri (Es.: 12345 --> 0000012345)</li>
     * <li>substring dei primi 8 caratteri del CODICEOGGETTO restituito al passo 1 (Es.: 0000012345 --> 00000123)</li>
     * <li>il path è dato da una directory che ha come nome i primi 4 caratteri seguito da una sottodirectory definita
     * dal quinto e sesto carattere e una altra sottodirectory definita dagli ultimi due caratteri. In questo modo
     * ognuna delle sottodirectory finali conterrà al massimo 100 files. (Es.: 00000123 --> /0000/01/23)</li>
     * </ol>
     * La sottodirectory in cui viene scritto il file viene infine memorizzata nel campo PERCORSO.
     * 
     * @param minCodiceOggetto
     * 
     */
    public int spostaBlobSuFileSystem(String rootPathOggetti, boolean setBlobNull, int maxDocs, int maxMinutes, OggettiFileSystemStatusBean status,
	    Integer minCodiceOggetto);

    /**
     * Questo metodo scorre i record della tabella OGGETTI che hanno il campo PERCORSO impostato a null.<br>
     * Se OGGETTO == null la procedura individua il file corrispondente che si trova nella root directory prevista per
     * l'archiviazione su file system, e, in base al codice oggetto, lo copia in una sottodirectory secondo la seguente
     * regola:
     * <ol>
     * <li>PAD a zeri a sinistra di CODICEOGGETTO fino a 10 caratteri (Es.: 12345 --> 0000012345)</li>
     * <li>substring dei primi 8 caratteri del CODICEOGGETTO restituito al passo 1 (Es.: 0000012345 --> 00000123)</li>
     * <li>il path è dato da una directory che ha come nome i primi 4 caratteri seguito da una sottodirectory definita
     * dal quinto e sesto carattere e una altra sottodirectory definita dagli ultimi due caratteri. In questo modo
     * ognuna delle sottodirectory finali conterrà al massimo 100 files. (Es.: 00000123 --> /0000/01/23)</li>
     * </ol>
     * La sottodirectory in cui viene scritto il file viene infine memorizzata nel campo PERCORSO.
     * 
     */
    public int ottimizzaFileSystem(String rootPathOggetti, OggettiFileSystemStatusBean status);

    public int verificaIncongruenze(OggettiFileSystemStatusBean status);

    public int countDocumentsAtRoot();

    public int countDocumentsInBlob();
}
