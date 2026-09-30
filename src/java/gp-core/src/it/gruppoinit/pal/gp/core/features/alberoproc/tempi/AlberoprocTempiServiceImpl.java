package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTempi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTempiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempiFoD;
import it.gruppoinit.pal.gp.core.domain.TempiFoT;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class AlberoprocTempiServiceImpl implements AlberoprocTempiService {

    private static final Logger logger = LoggerFactory.getLogger(AlberoprocTempiServiceImpl.class);
    private AlberoprocDAO alberoprocDAO;
    private AlberoprocTempiDAO alberoprocTempiDAO;
    private TempiFoTDAO tempiFoTDAO;
    private TempiFoDDAO tempiFoDDAO;

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    @Autowired
    public void setAlberoprocTempiDAO(AlberoprocTempiDAO alberoprocTempiDAO) {

	this.alberoprocTempiDAO = alberoprocTempiDAO;
    }

    @Autowired
    public void setTempiFoDDAO(TempiFoDDAO tempiFoDDAO) {

	this.tempiFoDDAO = tempiFoDDAO;
    }

    @Autowired
    public void setTempiFoTDAO(TempiFoTDAO tempiFoTDAO) {

	this.tempiFoTDAO = tempiFoTDAO;
    }

    @Override
    public FindAlberoProcTempiResponse findTempiFromAlberoProcId(Integer codiceIntervento) {

	List<AlberoprocTempi> tempi = this.alberoprocTempiDAO.findByAlberoProcId(codiceIntervento);
	if (tempi.size() == 0) {
	    return new FindAlberoProcTempiResponse();
	}
	return FindAlberoProcTempiResponse.FromAlberoprocTempi(tempi);
    }

    @Override
    public SalvaAlberoprocTempiResponse salvaAlberoprocTempi(SalvaAlberoprocTempiRequest request) {

	if (request == null || request.getDettaglio() == null || request.getDettaglio().size() == 0) {
	    throw new IllegalArgumentException("Nessun dato presente da salvare");
	}
	if (request.getCodiceIntervento() == null) {
	    throw new IllegalArgumentException("Nessun è stato passato il riferimento all'albero degli interventi");
	}
	Alberoproc albero = this.alberoprocDAO.findById(new PkId(request.getCodiceIntervento()));
	if (albero == null) {
	    throw new IllegalArgumentException("Non esiste nessun intervento con id " + request.getCodiceIntervento());
	}
	//1. Inserisco o aggiorno la testata
	TempiFoT testata = new TempiFoT();
	if (request.getIdTempot() != null) {
	    testata.setId(new PkId(request.getIdTempot()));
	}
	testata.setTitolo(request.getDescTempot());
	this.tempiFoTDAO.insertOrUpdate(testata, testata.getId(), true);
	//2. Cancello eventuali dettagli
	this.tempiFoDDAO.deleteByIdTestata(testata.getId().getCodice());
	//3. Inserisco solo i dettagli esistenti 
	for (TempoFoDModel tempo : request.getDettaglio()) {
	    if (TipoTempiFOEnum.ASSOLUTA.value().equalsIgnoreCase(tempo.getTipo()) && StringUtils.isBlank(tempo.getScadenza())) {
		throw new IllegalArgumentException("Non è possibile indicare una scadenza assoluta senza specificare la data di scadenza");
	    }
	    if (TipoTempiFOEnum.RELATIVA.value().equalsIgnoreCase(tempo.getTipo()) && (tempo.getGiorni() == null || tempo.getGiorni() <= 0)) {
		throw new IllegalArgumentException("Non è possibile indicare una scadenza relativa senza indicare i giorni");
	    }
	    TempiFoD tempiFO = new TempiFoD();
	    tempiFO.setDescrizione(tempo.getDescrizione());
	    tempiFO.setGiorni(tempo.getGiorni());
	    if (tempo.getIdTempod() != null) {
		tempiFO.setId(new PkId(tempo.getIdTempod()));
	    }
	    tempiFO.setOrdine(tempo.getOrdine());
	    if (StringUtils.isNotBlank(tempo.getScadenza())) {
		tempiFO.setScadenza(Utilities.parseDateString(tempo.getScadenza(), WebConstants.DATE_FORMAT_PATTERN));
	    }
	    tempiFO.setTestata(testata);
	    tempiFO.setTipo(tempo.getTipo());
	    tempiFO.setTitolo(tempo.getTitolo());
	    this.tempiFoDDAO.insertOrUpdate(tempiFO, tempiFO.getId(), true);
	}
	//4. Verifico se già agganciata alla voce dell'albero
	AlberoprocTempi tempi = new AlberoprocTempi();
	tempi.setId(new AlberoprocTempiId(request.getCodiceIntervento(), testata.getId().getCodice()));
	tempi.setTempiFO(testata);
	tempi.setAlberoProc(albero);
	this.alberoprocTempiDAO.insertOrUpdate(tempi, tempi.getId(), false);
	return SalvaAlberoprocTempiResponse.OK(request.getCodiceIntervento(), testata.getId().getCodice());
    }

    @Override
    public void eliminaAlberoProcTempi(EliminaAlberoProcTempiRequest request) {

	if (request == null) {
	    throw new IllegalArgumentException("Nessun dato da cancellare");
	}
	if (request.getCodiceIntervento() == null) {
	    throw new IllegalArgumentException("Impossibile cancellare i tempi senza passare il riferimento all'albero degli interventi");
	}
	if (request.getCodiceTempoFo() == null) {
	    throw new IllegalArgumentException("Impossibile cancellare i tempi senza passare il riferimento alla tempistica");
	}
	//1. Cancello l'associazione
	this.alberoprocTempiDAO.deleteById(request.getCodiceIntervento(), request.getCodiceTempoFo());
	//2. Cancello anche tempi_fo_t e tempi_fo_d visto che non ha un menu di gestione
	this.tempiFoTDAO.deleteById(request.getCodiceTempoFo());
    }
}
