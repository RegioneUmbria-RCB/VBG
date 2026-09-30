package it.gruppoinit.pal.gp.core.features.configurazionecalcoli;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneCalcoli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IConfigurazioneCalcoliService extends BaseService<ConfigurazioneCalcoli, PkId> {

    List<CalcoloListItem> findAll();

    void deleteById(Integer codice);

    void aggiornaDescrizione(Integer codice, String descrizione);
}
