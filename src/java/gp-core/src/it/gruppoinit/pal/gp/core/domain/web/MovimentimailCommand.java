package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;

public class MovimentimailCommand extends BaseCommand {

    private Movimentimail entity;
    private DocumentiHelper documentiHelper;
    private Boolean flgInvialinkallmail;
    private Letteretipo letteraTipoAllegati;
    private MailConfig mailConfig;
    private Boolean flgInvioMailZipLogico;

    public Movimentimail getEntity() {

	return entity;
    }

    public void setEntity(Movimentimail entity) {

	this.entity = entity;
    }

    public DocumentiHelper getDocumentiHelper() {

	return documentiHelper;
    }

    public void setDocumentiHelper(DocumentiHelper documentiHelper) {

	this.documentiHelper = documentiHelper;
    }

    public Boolean getFlgInvialinkallmail() {

	return flgInvialinkallmail;
    }

    public void setFlgInvialinkallmail(Boolean flgInvialinkallmail) {

	this.flgInvialinkallmail = flgInvialinkallmail;
    }

    public Letteretipo getLetteraTipoAllegati() {

	return letteraTipoAllegati;
    }

    public void setLetteraTipoAllegati(Letteretipo letteraTipoAllegati) {

	this.letteraTipoAllegati = letteraTipoAllegati;
    }

    public MailConfig getMailConfig() {

	return mailConfig;
    }

    public void setMailConfig(MailConfig mailConfig) {

	this.mailConfig = mailConfig;
    }

    public Boolean getFlgInvioMailZipLogico() {

	return flgInvioMailZipLogico;
    }

    public void setFlgInvioMailZipLogico(Boolean flgInvioMailZipLogico) {

	this.flgInvioMailZipLogico = flgInvioMailZipLogico;
    }
}
