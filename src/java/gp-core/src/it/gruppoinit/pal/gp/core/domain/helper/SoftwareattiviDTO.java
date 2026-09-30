package it.gruppoinit.pal.gp.core.domain.helper;

public class SoftwareattiviDTO {

    private String codice;
    private String descrizione;
    private boolean attivo;
    private boolean attivoFo;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public boolean isAttivo() {

	return attivo;
    }

    public void setAttivo(boolean attivo) {

	this.attivo = attivo;
    }

    public boolean isAttivoFo() {

	return attivoFo;
    }

    public void setAttivoFo(boolean attivoFo) {

	this.attivoFo = attivoFo;
    }
}
