package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.MailConfig;

public class MailConfigCommand extends BaseCommand {

    private MailConfig entity;
    private String newLoginpass;
    private String newInLoginpass;

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

    
    public String getNewLoginpass() {
    
        return this.newLoginpass;
    }

    
    public void setNewLoginpass(String newLoginpass) {
    
        this.newLoginpass = newLoginpass;
    }

    
    public String getNewInLoginpass() {
    
        return this.newInLoginpass;
    }

    
    public void setNewInLoginpass(String newInLoginpass) {
    
        this.newInLoginpass = newInLoginpass;
    }
}
