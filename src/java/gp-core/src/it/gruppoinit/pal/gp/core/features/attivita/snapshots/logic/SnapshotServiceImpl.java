package it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.Snapshot;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;

@Service
public class SnapshotServiceImpl implements ISnapshotService {

    @Autowired
    private ISnapshotDAO snapshotDAO;
    @Autowired
    private IAttivitaSnapshotService attivitaSnapshotService;
    @Autowired
    private IAttivitaIstanzeService attivitaIstanzeService;
    @Autowired
    private IAttivitaService iAttivitaService;

    @Override
    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo) {

	return this.snapshotDAO.findSnapshots(idAttivita, dataRicalcolo);
    }

    @Override
    public Map<Date, Integer> findSnapshotsSuccessivi(Integer idAttivita, Date dataDiRiferimento) {

	return this.snapshotDAO.findSnapshotsSuccessivi(idAttivita, dataDiRiferimento);
    }

    @Override
    public boolean findIfIsAttivitaOperanteFromMovimenti(Integer idAttivita, Date dataSnapshot) {

	return this.snapshotDAO.findIfIsAttivitaOperanteFromMovimenti(idAttivita, dataSnapshot);
    }

    @Override
    public Date findMinDataValidita(Integer idAttivita) {

	return this.snapshotDAO.findMinDataValidita(idAttivita);
    }

    @Override
    public Date findMinDataSnapshot(Integer idAttivita) {

	return this.snapshotDAO.findMinDataSnapshot(idAttivita);
    }

    @Override
    public IAttivitaSnapshot findSnapshotPiuRecente(Integer idAttivita) {

	return this.snapshotDAO.findSnapshotPiuRecente(idAttivita);
    }

    @Override
    public void salvaSnapshot(Snapshot snapshot) {

	IAttivitaSnapshot entity = new IAttivitaSnapshot(snapshot);
	if (snapshot.getId() == null) {
	    attivitaSnapshotService.insert(entity);
	} else {
	    attivitaSnapshotService.update(entity);
	}
    }

    @Override
    public List<IAttivitaSnapshot> findByAttivita(Integer codiceAttivita) {

	return this.attivitaSnapshotService.findByAttivita(codiceAttivita);
    }

    @Override
    public boolean isAttivaAllaData(Integer idAttivita, Date dataValidita) {

	return this.attivitaIstanzeService.isAttivaAllaData(idAttivita, dataValidita);
    }

    @Override
    public IAttivita findAttivitaById(Integer idAttivita) {

	return this.iAttivitaService.findById(new PkId(idAttivita));
    }

    @Override
    public void updateAttivita(IAttivita attivita) {

	this.iAttivitaService.update(attivita);
    }

    @Override
    public Integer generaCodiceOsservatorio() {

	return this.iAttivitaService.generaCodiceOsservatorio();
    }

    @Override
    public void delete(Integer idSnapshot) {

	this.snapshotDAO.delete(idSnapshot);
    }
}
