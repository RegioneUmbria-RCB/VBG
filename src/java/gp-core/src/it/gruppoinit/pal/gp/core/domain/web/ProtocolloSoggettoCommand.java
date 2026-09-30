package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.IndirizzoMailResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.TipoDestinatarioEnum;

public class ProtocolloSoggettoCommand {

    private Anagrafe anagrafe;
    private Amministrazioni amministrazioni;
    private Boolean perConoscenza;
    private Boolean perConoscenzaAmm;
    private ProtocolloMezzi mezzo;
    private ProtocolloModalitainvio modInvio;
    private String email;

    private ProtocolloSoggettoCommand() {

	this.anagrafe = new Anagrafe();
	this.amministrazioni = new Amministrazioni();
	this.mezzo = new ProtocolloMezzi();
	this.modInvio = new ProtocolloModalitainvio();
    }

    //Introdotto per retrocompatibilità, meglio dismetterlo
    public static ProtocolloSoggettoCommand fromGeneric() {

	ProtocolloSoggettoCommand command = new ProtocolloSoggettoCommand();
	return command;
    }

    public static ProtocolloSoggettoCommand fromAmministrazione(Amministrazioni amministrazione, ProtocolloMezzi mezzo) {

	ProtocolloSoggettoCommand command = new ProtocolloSoggettoCommand();
	command.setAmministrazioni(amministrazione);
	command.setEmail(amministrazione.getPec());
	command.setMezzo(mezzo);
	return command;
    }

    public static ProtocolloSoggettoCommand fromAmministrazione(Amministrazioni amministrazione, ProtocolloMezzi mezzo,
	    ProtocolloModalitainvio modInvio) {

	ProtocolloSoggettoCommand command = new ProtocolloSoggettoCommand();
	command.setAmministrazioni(amministrazione);
	command.setEmail(amministrazione.getPec());
	command.setMezzo(mezzo);
	command.setModInvio(modInvio);
	return command;
    }

    public static ProtocolloSoggettoCommand fromRichiedente(IndirizzoMailResolver mailResolver, Anagrafe richiedente, String domicilioElettronico,
	    ProtocolloMezzi mezzo) {

	ProtocolloSoggettoCommand command = new ProtocolloSoggettoCommand();
	command.setAnagrafe(richiedente);
	command.setEmail(mailResolver.getMailAnagrafe(richiedente, domicilioElettronico, TipoDestinatarioEnum.RICHIEDENTE));
	command.setMezzo(mezzo);
	return command;
    }

    public static ProtocolloSoggettoCommand fromTitolareLegale(IndirizzoMailResolver mailResolver, Anagrafe titolareLegale,
	    String domicilioElettronico, ProtocolloMezzi mezzo) {

	ProtocolloSoggettoCommand command = new ProtocolloSoggettoCommand();
	command.setAnagrafe(titolareLegale);
	command.setEmail(mailResolver.getMailAnagrafe(titolareLegale, domicilioElettronico, TipoDestinatarioEnum.AZIENDA));
	command.setMezzo(mezzo);
	return command;
    }

    public static ProtocolloSoggettoCommand fromProfessionista(IndirizzoMailResolver mailResolver, Anagrafe professionista,
	    String domicilioElettronico, ProtocolloMezzi mezzo) {

	ProtocolloSoggettoCommand command = new ProtocolloSoggettoCommand();
	command.setAnagrafe(professionista);
	command.setEmail(mailResolver.getMailAnagrafe(professionista, domicilioElettronico, TipoDestinatarioEnum.PROFESSIONISTA));
	command.setMezzo(mezzo);
	return command;
    }

    public static ProtocolloSoggettoCommand fromAltroSoggetto(Anagrafe anagrafe, ProtocolloMezzi mezzo) {

	ProtocolloSoggettoCommand command = new ProtocolloSoggettoCommand();
	command.setAnagrafe(anagrafe);
	command.setEmail(anagrafe.getPec());
	command.setMezzo(mezzo);
	return command;
    }

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public Boolean getPerConoscenza() {

	return perConoscenza;
    }

    public void setPerConoscenza(Boolean perConoscenza) {

	this.perConoscenza = perConoscenza;
    }

    public Boolean getPerConoscenzaAmm() {

	return perConoscenzaAmm;
    }

    public void setPerConoscenzaAmm(Boolean perConoscenzaAmm) {

	this.perConoscenzaAmm = perConoscenzaAmm;
    }

    public ProtocolloMezzi getMezzo() {

	return mezzo;
    }

    public void setMezzo(ProtocolloMezzi mezzo) {

	this.mezzo = mezzo;
    }

    public ProtocolloModalitainvio getModInvio() {

	return modInvio;
    }

    public void setModInvio(ProtocolloModalitainvio modInvio) {

	this.modInvio = modInvio;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }
}
