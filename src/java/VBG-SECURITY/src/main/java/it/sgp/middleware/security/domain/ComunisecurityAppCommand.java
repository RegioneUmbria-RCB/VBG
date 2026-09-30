package it.sgp.middleware.security.domain;

public class ComunisecurityAppCommand extends BaseCommand<ComunisecurityApp> {

    public ComunisecurityAppCommand() {

	this.entity = new ComunisecurityApp();
    }

    private ComunisecurityApp entity;

    @Override
    public ComunisecurityApp getEntity() {

	return this.entity;
    }

    @Override
    public void setEntity(ComunisecurityApp entity) {

	this.entity = entity;
    }
}
