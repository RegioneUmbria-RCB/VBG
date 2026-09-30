package it.gruppoinit.pal.gp.core.domain.helper;

public class ComunicazioniDDTO {

    private Integer idcomunicazioned;
    private Integer oggetto;
    private Integer codicecomunicazionit;
    private Integer movimenti;
    private String descMovimenti;
    private Integer movimentimail;
    private Integer statoelaborazione;
    private String eventoTmpStatiComunicazioniD;
    //    private List<Istanzeeventi> istanzeeventis;
    //    private Istanzeeventi istanzeeventi;
    // Variabili utilizzate per settare se una mail è stata accetta dal server di posta
    // e se è stata consegnata al destinatario
    private Boolean accettata;
    private Boolean consegnata;

    public ComunicazioniDDTO() {

	this.accettata = Boolean.FALSE;
	this.consegnata = Boolean.FALSE;
    }

    public Integer getIdcomunicazioned() {

	return idcomunicazioned;
    }

    public void setIdcomunicazioned(Integer idcomunicazioned) {

	this.idcomunicazioned = idcomunicazioned;
    }

    public Integer getOggetto() {

	return oggetto;
    }

    public void setOggetto(Integer oggetto) {

	this.oggetto = oggetto;
    }

    public Integer getCodicecomunicazionit() {

	return codicecomunicazionit;
    }

    public void setCodicecomunicazionit(Integer codicecomunicazionit) {

	this.codicecomunicazionit = codicecomunicazionit;
    }

    //    public AnagrafeDTO getAnagrafeDTO() {
    //
    //	return anagrafeDTO;
    //    }
    //
    //    public void setAnagrafeDTO(AnagrafeDTO anagrafeDTO) {
    //
    //	this.anagrafeDTO = anagrafeDTO;
    //    }
    public Integer getMovimenti() {

	return movimenti;
    }

    public void setMovimenti(Integer movimenti) {

	this.movimenti = movimenti;
    }

    public String getDescMovimenti() {

	return descMovimenti;
    }

    public void setDescMovimenti(String descMovimenti) {

	this.descMovimenti = descMovimenti;
    }

    public Integer getMovimentimail() {

	return movimentimail;
    }

    public void setMovimentimail(Integer movimentimail) {

	this.movimentimail = movimentimail;
    }

    public Integer getStatoelaborazione() {

	return statoelaborazione;
    }

    public void setStatoelaborazione(Integer statoelaborazione) {

	this.statoelaborazione = statoelaborazione;
    }

    public String getEventoTmpStatiComunicazioniD() {

	return eventoTmpStatiComunicazioniD;
    }

    public void setEventoTmpStatiComunicazioniD(String eventoTmpStatiComunicazioniD) {

	this.eventoTmpStatiComunicazioniD = eventoTmpStatiComunicazioniD;
    }

    //    public List<Istanzeeventi> getIstanzeeventis() {
    //
    //	return istanzeeventis;
    //    }
    //
    //    public void setIstanzeeventis(List<Istanzeeventi> istanzeeventis) {
    //
    //	this.istanzeeventis = istanzeeventis;
    //    }
    //
    //    public Istanzeeventi getIstanzeeventi() {
    //
    //	return istanzeeventi;
    //    }
    //    public void setIstanzeeventi(Istanzeeventi istanzeeventi) {
    //
    //	this.istanzeeventi = istanzeeventi;
    //    }
    public Boolean getAccettata() {

	return accettata;
    }

    public void setAccettata(Boolean accettata) {

	this.accettata = accettata;
    }

    public Boolean getConsegnata() {

	return consegnata;
    }

    public void setConsegnata(Boolean consegnata) {

	this.consegnata = consegnata;
    }
}
