package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollMassiveT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ComunicazioneBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.DettaglioBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaDettagli;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaTestata;

public interface IBollettazioneComunicazioniMassiveDAO extends BaseDAO<BollMassiveT, PkId> {

    void collegaBollettazioneAComunicazioni(int idTestata, int idBollettazione);

    void collegaDettaglioBollettazioneADettaglioComunicazioni(int idDettaglioComunicazione, int idDettaglioBollettazione);

    List<DettaglioBollettazione> getDettagli(FiltriRicercaDettagli filtri);

    public ComunicazioneBollettazione getComunicazioneBollettazione(FiltriRicercaTestata filtri);

    boolean exists(Integer idTestata);

    BollMassiveT findByIdTestata(Integer idTestata);
}
