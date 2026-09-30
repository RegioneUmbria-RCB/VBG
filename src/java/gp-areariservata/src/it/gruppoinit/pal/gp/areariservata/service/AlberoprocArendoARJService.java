package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentiAltriHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;

import java.util.List;

public interface AlberoprocArendoARJService {

    public List<AlberoprocArendo> findByAlberoproc(Integer codiceIntervento);

    public ProcedimentiAltriHelper createProcedimentiAltriHelper(Integer codiceIntervento);
}
