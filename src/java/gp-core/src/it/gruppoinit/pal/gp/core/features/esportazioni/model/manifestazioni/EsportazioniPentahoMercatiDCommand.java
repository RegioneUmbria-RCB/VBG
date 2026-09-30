package it.gruppoinit.pal.gp.core.features.esportazioni.model.manifestazioni;

import it.gruppoinit.pal.gp.core.features.esportazioni.model.EsportazioniPentahoGenericCommand;

public class EsportazioniPentahoMercatiDCommand extends EsportazioniPentahoGenericCommand {

    private Integer codiceMercato;
    private Integer codiceUso;
    private Integer codiceStradario;
    private String idAttivitaIstat;
    private Integer attivitaAmmissibile;
    private String note;

    public Integer getCodiceMercato() {

	return codiceMercato;
    }

    public void setCodiceMercato(Integer codiceMercato) {

	this.codiceMercato = codiceMercato;
    }

    public Integer getCodiceUso() {

	return codiceUso;
    }

    public void setCodiceUso(Integer codiceUso) {

	this.codiceUso = codiceUso;
    }

    public Integer getCodiceStradario() {

	return codiceStradario;
    }

    public void setCodiceStradario(Integer codiceStradario) {

	this.codiceStradario = codiceStradario;
    }

    public String getIdAttivitaIstat() {

	return idAttivitaIstat;
    }

    public void setIdAttivitaIstat(String idAttivitaIstat) {

	this.idAttivitaIstat = idAttivitaIstat;
    }

    public Integer getAttivitaAmmissibile() {

	return attivitaAmmissibile;
    }

    public void setAttivitaAmmissibile(Integer attivitaAmmissibile) {

	this.attivitaAmmissibile = attivitaAmmissibile;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
