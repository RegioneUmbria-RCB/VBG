package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentiHelper;
import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentoHelper;

public interface NuovaIstanzaProcedimentiService extends NuovaIstanzaBaseService {

    public ProcedimentiHelper getProcedimenti(Integer codiceIntervento);

    public ProcedimentoHelper getProcedimento(Integer codiceProcedimento);
}
