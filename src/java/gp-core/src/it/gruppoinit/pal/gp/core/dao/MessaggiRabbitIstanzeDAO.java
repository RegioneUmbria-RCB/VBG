package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitIstanze;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitIstanzeId;

public interface MessaggiRabbitIstanzeDAO extends BaseDAO<MessaggiRabbitIstanze, MessaggiRabbitIstanzeId> {

    int countByUidIstanza(String uuIdIstanza);
}
