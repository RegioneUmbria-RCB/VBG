package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class OrariEContattiBean {

    private String orari;
    private SupportoBean supporto;
    /**
     * 1.Nominativo del responsabile dello sportello (responsabileSportello sotto l’elemento root) 2.Denominazione
     * comune (denominazioneComune sotto l’elemento root) 3.Denominazione sportello (denominazioneSportello sotto
     * l’elemento root) 4.Indirizzo sportello (indirizzoSportello sotto l’elemento root)
     */
    private String responsabileSportello;
    private String denominazioneComune;
    private String denominazioneSportello;
    private String indirizzoSportello;
    private String responsabileTrattamento;
    private String dataProtectionOfficer;

    public String getOrari() {

	return orari;
    }

    public void setOrari(String orari) {

	this.orari = orari;
    }

    public SupportoBean getSupporto() {

	return supporto;
    }

    public void setSupporto(SupportoBean supporto) {

	this.supporto = supporto;
    }

    public String getResponsabileSportello() {

	return responsabileSportello;
    }

    public void setResponsabileSportello(String responsabileSportello) {

	this.responsabileSportello = responsabileSportello;
    }

    public String getDenominazioneComune() {

	return denominazioneComune;
    }

    public void setDenominazioneComune(String denominazioneComune) {

	this.denominazioneComune = denominazioneComune;
    }

    public String getDenominazioneSportello() {

	return denominazioneSportello;
    }

    public void setDenominazioneSportello(String denominazioneSportello) {

	this.denominazioneSportello = denominazioneSportello;
    }

    public String getIndirizzoSportello() {

	return indirizzoSportello;
    }

    public void setIndirizzoSportello(String indirizzoSportello) {

	this.indirizzoSportello = indirizzoSportello;
    }

    public String getResponsabileTrattamento() {

	return responsabileTrattamento;
    }

    public void setResponsabileTrattamento(String responsabileTrattamento) {

	this.responsabileTrattamento = responsabileTrattamento;
    }

    public String getDataProtectionOfficer() {

	return dataProtectionOfficer;
    }

    public void setDataProtectionOfficer(String dataProtectionOfficer) {

	this.dataProtectionOfficer = dataProtectionOfficer;
    }
}
