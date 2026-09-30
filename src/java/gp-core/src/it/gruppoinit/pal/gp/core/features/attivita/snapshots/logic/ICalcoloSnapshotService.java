package it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic;

import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;

public interface ICalcoloSnapshotService {

    public void ricalcola(Integer idAttivita);

    public void calcola(ParametriCalcoloSnapshot parametri);

    void chiudiAttivitaTemporanea(Integer idAttivita);
}
