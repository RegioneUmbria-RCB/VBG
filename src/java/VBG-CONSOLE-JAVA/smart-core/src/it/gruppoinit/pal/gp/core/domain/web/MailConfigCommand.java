package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.MailConfig;

public class MailConfigCommand extends BaseCommand {

    private MailConfig entity;

    public MailConfigCommand() {

	super();
	this.entity = new MailConfig();
    }

    public MailConfig getEntity() {

	return entity;
    }

    public void setEntity(MailConfig entity) {

	this.entity = entity;
    }
}
