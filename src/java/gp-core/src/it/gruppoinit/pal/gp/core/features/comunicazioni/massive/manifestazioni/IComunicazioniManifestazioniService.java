package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.model.ComunicazioneMassivaGenModel;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaModel;

public interface IComunicazioniManifestazioniService extends IComunicazioniMassiveService<ConfigurazioneComunicazioniManifestazioni> {

    ComunicazioneMassivaModel getComunicazioneByIdTestata(int idTestata);
    
    ComunicazioneMassivaGenModel getComunicazioneByIdGenTestata(int idTestata);

    void elabora();

    List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(int idDettaglioComunicazione);

    boolean comunicazioneCancellabile(Integer idGiornata);

    void eliminaComunicazioniDellaGiornata(Integer idGiornata, Responsabili operatore);

    boolean presentiComunicazioniPerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione);

    Integer recuperaPrimaComunicazionePerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione);

    boolean comunicazioneCancellabilePerTipologia(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione);

    void eliminaComunicazioniDellaGiornataPerTipologia(Integer idGiornata, Responsabili operatore, TIPO_COMUNICAZIONE tipoComunicazione);

    /**
     * Torna False se: <br/>
     * - non sono presenti comunicazioni per quella presenza <br/>
     * - sono presenti comunicazioni per quella presenza in stato non conclusivo
     * 
     * @param idGiornata
     * @param tipoComunicazione
     * @param idMercatipresenzeD
     * @return
     */
    boolean comunicazioneCancellabilePerTipologiaEPResenza(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione, Integer idMercatipresenzeD);

    void eliminaComunicazioniDellaGiornataPerTipologiaEPResenza(Integer idGiornata, Responsabili operatore, TIPO_COMUNICAZIONE tipoComunicazione,
	    Integer idMercatipresenzeD);
}
