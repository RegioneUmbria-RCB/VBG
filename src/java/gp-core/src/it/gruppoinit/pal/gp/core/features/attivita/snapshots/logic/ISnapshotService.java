package it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.Snapshot;

public interface ISnapshotService {

    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo);

    public Map<Date, Integer> findSnapshotsSuccessivi(Integer idAttivita, Date dataDiRiferimento);

    public boolean findIfIsAttivitaOperanteFromMovimenti(Integer idAttivita, Date dataSnapshot);

    public Date findMinDataValidita(Integer idAttivita);

    public Date findMinDataSnapshot(Integer idAttivita);

    public IAttivitaSnapshot findSnapshotPiuRecente(Integer idAttivita);

    public void salvaSnapshot(Snapshot snapshot);

    public List<IAttivitaSnapshot> findByAttivita(Integer codiceAttivita);

    public boolean isAttivaAllaData(Integer idAttivita, Date dataValidita);

    public IAttivita findAttivitaById(Integer idAttivita);

    public void updateAttivita(IAttivita attivita);

    public Integer generaCodiceOsservatorio();

    public void delete(Integer idSnapshot);
}
