package it.sgp.middleware.security.domain;

public class ComunisecurityCommand extends BaseCommand<Comunisecurity> {

    public ComunisecurityCommand() {

	this.entity = new Comunisecurity();
    }

    private Comunisecurity entity;

    @Override
    public Comunisecurity getEntity() {

	return this.entity;
    }

    @Override
    public void setEntity(Comunisecurity entity) {

	this.entity = entity;
    }
}
