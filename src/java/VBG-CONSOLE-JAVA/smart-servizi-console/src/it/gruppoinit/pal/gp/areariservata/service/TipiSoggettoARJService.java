package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.web.util.TipiSoggettoTipoAnagrafe;
import it.gruppoinit.pal.gp.areariservata.web.util.TipiSoggettoTipoDato;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

public interface TipiSoggettoARJService extends BaseService<Tipisoggetto, PkId> {

    // public List<Tipisoggetto> findTipiSoggetto(TipiSoggettoTipoAnagrafe tipoAnagrafe);
    public List<Tipisoggetto> findTipiSoggettoObbligatoriPerIntervento(Integer codiceIntervento);

    public List<Tipisoggetto> findTipiSoggettoPerInterventoAndTipoDato(Integer codiceIntervento, TipiSoggettoTipoDato tipoDato);

    public List<Tipisoggetto> findTipiSoggettoPerInterventoAndTipoAnagrafe(Integer codiceIntervento, TipiSoggettoTipoAnagrafe f);
}
