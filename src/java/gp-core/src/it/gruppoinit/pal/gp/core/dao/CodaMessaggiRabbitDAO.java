package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CodaMessaggiRabbit;
import it.gruppoinit.pal.gp.core.domain.CodaMessaggiRabbitId;

public interface CodaMessaggiRabbitDAO extends BaseDAO<CodaMessaggiRabbit, CodaMessaggiRabbitId> {

    int countByUidIstanza(String idcomune, String uuidPratica);
}
