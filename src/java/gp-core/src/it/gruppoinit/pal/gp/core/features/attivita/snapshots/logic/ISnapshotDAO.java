package it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic;

import java.util.Date;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;

public interface ISnapshotDAO {

    public boolean findIfIsAttivitaOperanteFromMovimenti(Integer idAttivita, Date dataSnapshot);

    public Map<Date, Integer> findSnapshots(Integer idAttivita);

    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo);

    public Map<Date, Integer> findSnapshotsSuccessivi(Integer idAttivita, Date dataDiRiferimento);

    public Date findMinDataValidita(Integer idAttivita);

    public Date findMinDataSnapshot(Integer idAttivita);

    public IAttivitaSnapshot findSnapshotPiuRecente(Integer idAttivita);

    public Integer findIdSnapshot(Integer idAttivita, Date dataRicalcolo);

    public void delete(Integer idSnapshot);
}
