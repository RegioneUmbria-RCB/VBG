package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniSearchFilter;

import java.util.List;

public interface FesteSagreService extends BaseService<FesteSagre, PkId> {

    public List<FesteSagre> findByFilter(ManifestazioniSearchFilter filter);
}
