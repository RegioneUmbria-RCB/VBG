package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoTipo1;

public class EndoTipo1Helper {

    private String codiceAmministrazioneCart;
    private Amministrazioni amministrazioni;
    private Tipimovimento tipimovimento;
    private Tipimovimento tipimovimentoOrdinario;
    private Tipimovimento tipimovimentoComunicazione;
    private EndoTipo1 datiEndo;

    //    private String codiciAmministrazioni;
    //    private String codiciTipimovimento;
    public EndoTipo1Helper() {

	this.amministrazioni = new Amministrazioni();
	this.tipimovimento = new Tipimovimento();
	this.tipimovimentoOrdinario = new Tipimovimento();
	this.tipimovimentoComunicazione = new Tipimovimento();
    }

    public String getCodiceAmministrazioneCart() {

	return codiceAmministrazioneCart;
    }

    public void setCodiceAmministrazioneCart(String codiceAmministrazioneCart) {

	this.codiceAmministrazioneCart = codiceAmministrazioneCart;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public Tipimovimento getTipimovimento() {

	return tipimovimento;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }

    public Tipimovimento getTipimovimentoOrdinario() {

	return tipimovimentoOrdinario;
    }

    public void setTipimovimentoOrdinario(Tipimovimento tipimovimentoOrdinario) {

	this.tipimovimentoOrdinario = tipimovimentoOrdinario;
    }

    public Tipimovimento getTipimovimentoComunicazione() {

	return tipimovimentoComunicazione;
    }

    public void setTipimovimentoComunicazione(Tipimovimento tipimovimentoComunicazione) {

	this.tipimovimentoComunicazione = tipimovimentoComunicazione;
    }
    //    public String getCodiciAmministrazioni() {
    //
    //	return codiciAmministrazioni;
    //    }
    //
    //    public void setCodiciAmministrazioni(String codiciAmministrazioni) {
    //
    //	this.codiciAmministrazioni = codiciAmministrazioni;
    //    }
    //    public String getCodiciTipimovimento() {
    //
    //	return codiciTipimovimento;
    //    }
    //
    //    public void setCodiciTipimovimento(String codiciTipimovimento) {
    //
    //	this.codiciTipimovimento = codiciTipimovimento;
    //    }

    
    public EndoTipo1 getDatiEndo() {
    
        return datiEndo;
    }

    
    public void setDatiEndo(EndoTipo1 datiEndo) {
    
        this.datiEndo = datiEndo;
    }
}
