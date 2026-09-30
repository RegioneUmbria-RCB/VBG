package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;

public class EndoTipo1Helper {

    private String codiceAmministrazioneCart;
    private Amministrazioni amministrazioni;

    //    private String codiciAmministrazioni;
    //    private String codiciTipimovimento;
    public EndoTipo1Helper() {

	this.amministrazioni = new Amministrazioni();
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
}
