package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

public interface AlberoprocTempiService {

    FindAlberoProcTempiResponse findTempiFromAlberoProcId(Integer codiceIntervento);

    SalvaAlberoprocTempiResponse salvaAlberoprocTempi(SalvaAlberoprocTempiRequest request);

    void eliminaAlberoProcTempi(EliminaAlberoProcTempiRequest request);
}
