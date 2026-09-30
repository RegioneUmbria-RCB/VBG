package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;

import java.util.List;

public interface OggettiMetadatiService extends BaseService<OggettiMetadati, OggettiMetadatiId> {

    public static final String MD5_SUM_MD = "MD5_SUM";
    public static final String SHA1_HASH_MD = "SHA1_HASH";
    public static final String FILE_SIZE_MD = "FILE_SIZE";
    public static final String CONTENT_TYPE_MD = "CONTENT_TYPE";

    public void deleteByOggetto(Integer codiceOggetto, String idcomune);

    public void insertMetadatiPerOggetto(Integer codiceOggetto, List<MetadatiBean> metadati, String idcomune);

    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto);

    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore, String idcomune);

    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore, String idcomune);

    public Integer findByChiaveEValore(String chiave, String valore);

    /**
     * Ritorna una lista di metadati per l'oggetto e la chiave passati nel caso non sia presente ritorna Null
     */
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto, String chiave, String idcomune);

    public String calcolaMd5(Integer codiceOggetto);

    public Oggetti findByGUID(List<String> idcomunes, String guid, boolean isLazyFetch);
    
    
}
