package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazioneEnum;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;

import java.util.List;

public interface TmpStatiComunicazioniDService extends BaseService<TmpStatiComunicazioniD, Integer> {

    public void insert(Integer posizione, Integer fkComunicazioniD, String stato);

    public List<TmpStatiComunicazioniD> findByIdComunicazioned(Integer codice);

    public void update(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum insertMovimento, String errore);

    public void delete(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum insertMovimento);

    public void delete(Integer codiceComunicazioneD);

    public TmpStatiComunicazioniD findPrimoPassoConErrore(Integer idcomunicazioned);

    public List<TmpStatiComunicazioniD> findComunicazioniBloccateInvioEmail(Integer codiceCominicazioneT);
}
