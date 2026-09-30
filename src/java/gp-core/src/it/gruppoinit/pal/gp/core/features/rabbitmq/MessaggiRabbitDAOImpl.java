package it.gruppoinit.pal.gp.core.features.rabbitmq;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbit;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.dao.IMessaggiRabbitDAO;

public class MessaggiRabbitDAOImpl extends BaseDAOImpl<MessaggiRabbit, MessaggiRabbitId> implements IMessaggiRabbitDAO {

    @Override
    public Class<MessaggiRabbit> getEntityClass() {

	return MessaggiRabbit.class;
    }
}
