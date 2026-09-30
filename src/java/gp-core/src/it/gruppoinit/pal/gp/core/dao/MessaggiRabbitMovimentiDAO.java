package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitMovimenti;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitMovimentiId;

public interface MessaggiRabbitMovimentiDAO extends BaseDAO<MessaggiRabbitMovimenti, MessaggiRabbitMovimentiId> {

    List<MessaggiRabbitMovimenti> findByCodiceMovimento(Integer codiceMovimento);

    void deleteByUuIdMovimento(String uuIdMovimento);
}
