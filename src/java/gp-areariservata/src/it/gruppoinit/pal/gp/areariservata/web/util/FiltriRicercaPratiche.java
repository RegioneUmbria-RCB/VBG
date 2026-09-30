package it.gruppoinit.pal.gp.areariservata.web.util;

import java.util.Date;

public class FiltriRicercaPratiche {

    private boolean cercaComeAziendaRichiedente;
    private boolean cercaComeRichiedente;
    private boolean cercaComeIntermediario;
    private boolean cercaComeSoggettoCollegato;
    private Date dataPresentazioneDa;
    private Date dataPresentazioneA;
    private String numeroPratica;
    private String numeroProtocollo;
    private Date dataProtocolloDa;
    private Date dataProtocolloA;
    private String oggetto;

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataPresentazioneDa() {

	return dataPresentazioneDa;
    }

    public void setDataPresentazioneDa(Date dataPresentazioneDa) {

	this.dataPresentazioneDa = dataPresentazioneDa;
    }

    public Date getDataPresentazioneA() {

	return dataPresentazioneA;
    }

    public void setDataPresentazioneA(Date dataPresentazioneA) {

	this.dataPresentazioneA = dataPresentazioneA;
    }

    public String getNumeroPratica() {

	return numeroPratica;
    }

    public void setNumeroPratica(String numeroPratica) {

	this.numeroPratica = numeroPratica;
    }

    public Date getDataProtocolloDa() {

	return dataProtocolloDa;
    }

    public void setDataProtocolloDa(Date dataProtocolloDa) {

	this.dataProtocolloDa = dataProtocolloDa;
    }

    public Date getDataProtocolloA() {

	return dataProtocolloA;
    }

    public void setDataProtocolloA(Date dataProtocolloA) {

	this.dataProtocolloA = dataProtocolloA;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public boolean isCercaComeAziendaRichiedente() {

	return cercaComeAziendaRichiedente;
    }

    public void setCercaComeAziendaRichiedente(boolean cercaComeAziendaRichiedente) {

	this.cercaComeAziendaRichiedente = cercaComeAziendaRichiedente;
    }

    public boolean isCercaComeRichiedente() {

	return cercaComeRichiedente;
    }

    public void setCercaComeRichiedente(boolean cercaComeRichiedente) {

	this.cercaComeRichiedente = cercaComeRichiedente;
    }

    public boolean isCercaComeIntermediario() {

	return cercaComeIntermediario;
    }

    public void setCercaComeIntermediario(boolean cercaComeIntermediario) {

	this.cercaComeIntermediario = cercaComeIntermediario;
    }

    public boolean isCercaComeSoggettoCollegato() {

	return cercaComeSoggettoCollegato;
    }

    public void setCercaComeSoggettoCollegato(boolean cercaComeSoggettoCollegato) {

	this.cercaComeSoggettoCollegato = cercaComeSoggettoCollegato;
    }
}
