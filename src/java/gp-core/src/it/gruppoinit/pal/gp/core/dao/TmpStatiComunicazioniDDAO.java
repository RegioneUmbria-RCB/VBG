package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazioneEnum;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;

import java.util.List;

public interface TmpStatiComunicazioniDDAO extends BaseDAO<TmpStatiComunicazioniD, Integer> {

    public void insert(Integer posizione, Integer fkComunicazioniD, String stato);

    public List<TmpStatiComunicazioniD> findByIdComunicazioned(Integer codiceComunicazione);

    public void update(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum insertMovimento, String errore);

    public void delete(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum PassoCreazioneComunicazione);

    public void delete(Integer codiceComunicazioneD);

    public TmpStatiComunicazioniD findPrimoPassoConErrore(Integer idcomunicazioned);

    public List<TmpStatiComunicazioniD> findComunicazioniBloccateInvioEmail(Integer codiceCominicazioneT);
}
