package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;
import java.util.Date;

public class MercatiDLivelloServizioDTO {

    private Integer codice;
    private BigDecimal fattoreMoltiplicativo;
    private Boolean usaMqPosteggio;
    private Date dataInizio;
    private Date dataFine;
    private MercatiUsoDTO mercatiUso;
    // campi di mercatiLivelloServizio
    private Integer codiceservizio;
    private String descrizione;
    private Boolean attivo;
    private BigDecimal tariffa;
    private Integer codiceposteggio;

    //
    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public MercatiUsoDTO getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUsoDTO mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public BigDecimal getFattoreMoltiplicativo() {

	return fattoreMoltiplicativo;
    }

    public void setFattoreMoltiplicativo(BigDecimal fattoreMoltiplicativo) {

	this.fattoreMoltiplicativo = fattoreMoltiplicativo;
    }

    public Boolean getUsaMqPosteggio() {

	return usaMqPosteggio;
    }

    public void setUsaMqPosteggio(Boolean usaMqPosteggio) {

	this.usaMqPosteggio = usaMqPosteggio;
    }

    public Date getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    public Date getDataFine() {

	return dataFine;
    }

    public void setDataFine(Date dataFine) {

	this.dataFine = dataFine;
    }

    public Integer getCodiceservizio() {

	return codiceservizio;
    }

    public void setCodiceservizio(Integer codiceservizio) {

	this.codiceservizio = codiceservizio;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Boolean getAttivo() {

	return attivo;
    }

    public void setAttivo(Boolean attivo) {

	this.attivo = attivo;
    }

    public BigDecimal getTariffa() {

	return tariffa;
    }

    public void setTariffa(BigDecimal tariffa) {

	this.tariffa = tariffa;
    }

    public Integer getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(Integer codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }
}
