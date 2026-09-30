package it.gruppoinit.pal.gp.core.features.commissioni.auditing;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioni;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.models.CommissioniAuditing;
import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.IAuditingService;

public interface ICommissioniAuditingService extends IAuditingService {

    void log(Integer idCommissione, MessaggioCommissioni messaggio);

    List<CommissioniAuditing> findByCodiceCommissione(Integer idCommissione);

    void delete(Integer idCommissione);
}
