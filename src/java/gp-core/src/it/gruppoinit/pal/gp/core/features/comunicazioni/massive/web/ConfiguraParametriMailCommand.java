package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;

public class ConfiguraParametriMailCommand {

    private Mailtipo mailtipo;
    private Mailtipo mailDaInviare;
    private MailConfig senderAccount;
    private boolean escludiNoMail;
    private CodiceDescrizioneBean sceltaMailAnagrafe;

    public ConfiguraParametriMailCommand() {

	this.mailDaInviare = new Mailtipo();
	this.mailtipo = new Mailtipo();
	this.senderAccount = new MailConfig();
	this.sceltaMailAnagrafe = new CodiceDescrizioneBean(SceltaTipoMailAnagrafeEnum.SOLO_MAIL.name(),
		SceltaTipoMailAnagrafeEnum.SOLO_MAIL.getDescrizione()); // default impostato a 
    }

    public Mailtipo getMailtipo() {

	return mailtipo;
    }

    public void setMailtipo(Mailtipo mailtipo) {

	this.mailtipo = mailtipo;
    }

    public Mailtipo getMailDaInviare() {

	return mailDaInviare;
    }

    public void setMailDaInviare(Mailtipo mailDaInviare) {

	this.mailDaInviare = mailDaInviare;
    }

    public MailConfig getSenderAccount() {

	return senderAccount;
    }

    public void setSenderAccount(MailConfig senderAccount) {

	this.senderAccount = senderAccount;
    }

    public boolean isEscludiNoMail() {

	return escludiNoMail;
    }

    public void setEscludiNoMail(boolean escludiNoMail) {

	this.escludiNoMail = escludiNoMail;
    }

    public CodiceDescrizioneBean getSceltaMailAnagrafe() {

	return sceltaMailAnagrafe;
    }

    public void setSceltaMailAnagrafe(CodiceDescrizioneBean sceltaMailAnagrafe) {

	this.sceltaMailAnagrafe = sceltaMailAnagrafe;
    }
}
