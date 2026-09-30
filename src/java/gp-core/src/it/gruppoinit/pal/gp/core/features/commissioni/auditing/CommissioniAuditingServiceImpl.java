package it.gruppoinit.pal.gp.core.features.commissioni.auditing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.CommissioniEdilizieLog;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.CommissioniCategorieEnum;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioni;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.models.CommissioniAuditing;

@Service
public class CommissioniAuditingServiceImpl implements ICommissioniAuditingService {

    private ICommissioniAuditingDAO commissioniAuditingDAO;

    @Autowired
    public CommissioniAuditingServiceImpl(ICommissioniAuditingDAO commissioniAuditingDAO) {

	this.commissioniAuditingDAO = commissioniAuditingDAO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void log(Integer idCommissione, MessaggioCommissioni messaggio) {

	if (idCommissione == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare la funzione di log senza passare l'identificativo della commissione");
	}
	if (messaggio == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare la funzione di log senza passare il messaggio");
	}
	CommissioniEdilizieLog log = new CommissioniEdilizieLog();
	log.setCategoria(messaggio.getCategoria().toString());
	CommissioniedilizieT testata = new CommissioniedilizieT();
	testata.setId(new PkId(idCommissione));
	log.setCommissioniedilizieT(testata);
	log.setData(messaggio.getDataSistema());
	log.setMessaggio(messaggio.getTestoMessaggio());
	this.commissioniAuditingDAO.insert(log);
    }

    @Override
    public List<CommissioniAuditing> findByCodiceCommissione(Integer idCommissione) {

	List<CommissioniEdilizieLog> ListLog = commissioniAuditingDAO.findByCodiceCommissione(idCommissione);
	List<CommissioniAuditing> listAuditing = new ArrayList<CommissioniAuditing>();
	for (CommissioniEdilizieLog commissioniEdilizieLog : ListLog) {
	    CommissioniAuditing auditing = new CommissioniAuditing(commissioniEdilizieLog.getId().getCodice(),
		    CommissioniCategorieEnum.valueOf(commissioniEdilizieLog.getCategoria()).getNomeCategoria(), commissioniEdilizieLog.getData(),
		    commissioniEdilizieLog.getMessaggio());
	    listAuditing.add(auditing);
	}
	return listAuditing;
    }

    @Override
    public void delete(Integer idCommissione) {

	this.commissioniAuditingDAO.deleteByIdCommissione(idCommissione);
    }
}
