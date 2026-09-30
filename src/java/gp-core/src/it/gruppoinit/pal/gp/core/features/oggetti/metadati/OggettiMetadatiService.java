package it.gruppoinit.pal.gp.core.features.oggetti.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface OggettiMetadatiService extends BaseService<OggettiMetadati, OggettiMetadatiId> {

    public static final String MD5_SUM_MD = "MD5_SUM";
    public static final String SHA1_HASH_MD = "FILE_SHA1_HASH";
    public static final String SHA256_HASH_MD = "FILE_SHA256_HASH";
    public static final String FILE_SIZE_MD = "FILE_SIZE";
    public static final String FILE_CONTENT_TYPE_MD = "FILE_CONTENT_TYPE";
    public static final String CHIAVE_FIRMA_DIGITALE_PRESENTE = "FIRMA_DIGITALE_PRESENTE";
    public static final String VALORE_FIRMA_DIGITALE_PRESENTE_TRUE = "S";
    public static final String VALORE_FIRMA_DIGITALE_PRESENTE_FALSE = "N";
    public static final String DATO_SENSIBILE = "DATO_SENSIBILE";
    public static final String UID = "UID";

    static enum MD_FUNZIONE {
	CREA_MD_ISTANZA,
	CREA_MD_MOVIMENTO,
	CREA_MD_ENDO,
	CREA_MD_ARCHIVI
    }

    public void deleteByOggetto(Integer codiceOggetto);

    public void insertMetadatiPerOggetto(Integer codiceOggetto, List<MetadatiBean> metadati);

    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto);

    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore);

    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore);

    public Integer findByChiaveEValore(String chiave, String valore);

    /**
     * Ritorna una lista di metadati per l'oggetto e la chiave passati nel caso non sia presente ritorna Null
     */
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto, String chiave);

    public String calcolaMd5(Integer codiceOggetto);

    public boolean isOggettoFirmatoDigitalmente(Integer codiceOggetto);

    public String getMessaggioModificaMetadato(Integer codiceoggetto, String metadato, String valore);

    public String getUIDFromCodiceOggetto(Integer codiceOggetto);

    public void rimuoviConservazioneSospesa(Integer codiceOggetto);
}
