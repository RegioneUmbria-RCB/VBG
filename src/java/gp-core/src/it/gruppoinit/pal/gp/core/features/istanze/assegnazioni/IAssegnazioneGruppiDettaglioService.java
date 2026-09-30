package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglio;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public interface IAssegnazioneGruppiDettaglioService {

    public void insert(AssegnazioneGruppiDettaglio assegnazioneGruppiDettaglio);

    /**
     * Trova la lista di istanze lavorate per operatore e testata
     * 
     * @param codiceResp
     * @param idTestata
     * @param tipo
     * @return
     */
    public List<IdentificativoDescrizioneBean> findIstanzeByResponsabileETestata(Integer codiceResp, Integer idTestata, String tipo);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeRespPerAssegnazioneTestata(Integer idTestata, String string);

    /**
     * Torna la lista di istanze aperte per operatore e testata
     * 
     * @param idTestata
     * @param codiceResponsabile
     * @param tipo
     * @return
     */
    public List<Integer> findIstanzeAperte(Integer idTestata, Integer codiceResponsabile, String tipo);
}
