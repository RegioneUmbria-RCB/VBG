package it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTMassive;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE;

public interface IMercatipresenzeTMassiveDAO extends BaseDAO<MercatipresenzeTMassive, PkId> {

    boolean exists(Integer idTestata);

    List<ISoftwareComuneData> getSoftwareAndComuneDaDettaglioComunicazione(int idDettaglioComunicazione);

    MercatipresenzeTMassive findByIdTestata(int idTestata);

    boolean comunicazioneCancellabile(Integer idGiornata, String statoComunicazioneCompletata);

    List<MercatipresenzeTMassive> findByIdGiornata(Integer idGiornata);

    List<Integer> findIdMercatiPresenzeDMassive(Integer idTestata);

    void deleteByIdTestata(Integer idTestata);

    List<Integer> findIdComunicazioniNonCompletate(String statoComunicazioneCompletata);

    int countComunicazioniPerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione);

    Integer recuperaPrimaComunicazionePerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione);

    boolean comunicazioneCancellabilePerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione,
	    String statoComunicazioneCompletata);

    List<Integer> recuperaComunicazioniPerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione);

    boolean comunicazioneCancellabilePerTipologiaEPResenza(Integer idGiornata, String name, TIPO_COMUNICAZIONE tipoComunicazione,
	    Integer idMercatipresenzeD, String statoComunicazioneCompletata);

    /**
     * Torna il dato mercatipresenze_t_massive.fkid_massive_testata
     * @param idGiornata
     * @param tipoComunicazione
     * @param idMercatipresenzeD
     * @return
     */
    List<Integer> recuperaComunicazioniPerTipologiaEGiornataEPresenza(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione,
	    Integer idMercatipresenzeD);

    List<Integer> findIdMercatiPresenzeDMassiveByTestataAndPresenza(Integer idTestata, Integer idMercatipresenzeD);
}
