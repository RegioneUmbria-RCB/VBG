package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglio;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglioId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public interface IAssegnazioneGruppiDettaglioDAO extends BaseDAO<AssegnazioneGruppiDettaglio, AssegnazioneGruppiDettaglioId> {

    List<IdentificativoDescrizioneBean> findIstanzeByResponsabileETestata(Integer codiceResp, Integer idTestata, String tipo);

    List<ChiaveValoreBean<Integer, Integer>> countPresenzeRespPerAssegnazioneTestata(Integer idTestata, String tipo);

    List<Integer> findIstanzeAperte(Integer idTestata, Integer codiceResponsabile, String tipo);
}
