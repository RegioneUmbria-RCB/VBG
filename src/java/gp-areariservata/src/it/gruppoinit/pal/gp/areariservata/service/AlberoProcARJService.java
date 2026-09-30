package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

public interface AlberoProcARJService extends BaseService<Alberoproc, PkId> {

    public List<Alberoproc> findSubTree(String scCodice);

    public boolean hasSubTree(String scCodice);

    public List<AlberoprocEndo> findListaEndoPubblicati(Alberoproc intervento);
}
