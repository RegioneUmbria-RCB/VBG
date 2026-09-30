package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.FiereMostreMerceologieCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.FiereMostrePeriodiCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniSearchFilter;

import java.util.List;

public interface FiereMostreService extends BaseService<FiereMostre, PkId> {

    public List<FiereMostre> findByFilter(ManifestazioniSearchFilter filter);

    public void insert(FiereMostre entity, FiereMostrePeriodi periodo, FiereMostreMerceologie merceologia);

    public void insert(FiereMostre entity, List<FiereMostrePeriodi> periodi, List<FiereMostreMerceologie> merceologie);

    public void update(FiereMostre entity, List<FiereMostrePeriodiCommand> periodi, FiereMostrePeriodi periodo,
	    List<FiereMostreMerceologieCommand> merceologie, FiereMostreMerceologie merceologia);
}
