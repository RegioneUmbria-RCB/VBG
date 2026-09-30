package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.TipimovimentodoctipoId;

public interface TipimovimentodoctipoDAO extends BaseDAO<Tipimovimentodoctipo, TipimovimentodoctipoId> {

    /**
     * Restituisce un oggetto tipo documento filtrati per tipo movimento e lettera tipo, se non esiste restituisce null
     * 
     * 
     */
    public Tipimovimentodoctipo findByTipoMovimentoAndTipoLettera(String codicemovimento, Integer codicelettera);

    public List<Integer> findCodiciLettereAutomaticheByTipoMovimentoAndFase(String tipoMovimento, FasiDiEsecuzioneEnum faseEsecuzione);
}
