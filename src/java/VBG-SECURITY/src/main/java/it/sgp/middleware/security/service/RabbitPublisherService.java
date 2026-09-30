package it.sgp.middleware.security.service;

import it.sgp.middleware.security.domain.ComunisecuritySession;

public interface RabbitPublisherService extends RabbitService {

	public void sendMessageIfRabbitEnabled(ComunisecuritySession session);
}
