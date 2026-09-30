package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglio;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

@Service
public class AssegnazioneGruppiDettaglioServiceImpl implements IAssegnazioneGruppiDettaglioService {

    private IAssegnazioneGruppiDettaglioDAO assegnazioneGruppiDettaglioDAO;

    @Autowired
    public void setAssegnazioneGruppiDettaglioDAO(IAssegnazioneGruppiDettaglioDAO assegnazioneGruppiDettaglioDAO) {

	this.assegnazioneGruppiDettaglioDAO = assegnazioneGruppiDettaglioDAO;
    }

    @Override
    public void insert(AssegnazioneGruppiDettaglio assegnazioneGruppiDettaglio) {

	this.assegnazioneGruppiDettaglioDAO.insert(assegnazioneGruppiDettaglio);
    }

    @Override
    public List<IdentificativoDescrizioneBean> findIstanzeByResponsabileETestata(Integer codiceResp, Integer idTestata, String tipo) {

	return this.assegnazioneGruppiDettaglioDAO.findIstanzeByResponsabileETestata(codiceResp, idTestata, tipo);
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeRespPerAssegnazioneTestata(Integer idTestata, String tipo) {

	return this.assegnazioneGruppiDettaglioDAO.countPresenzeRespPerAssegnazioneTestata(idTestata, tipo);
    }

    @Override
    public List<Integer> findIstanzeAperte(Integer idTestata, Integer codiceResponsabile, String tipo) {

	return this.assegnazioneGruppiDettaglioDAO.findIstanzeAperte(idTestata, codiceResponsabile, tipo);
    }
}
