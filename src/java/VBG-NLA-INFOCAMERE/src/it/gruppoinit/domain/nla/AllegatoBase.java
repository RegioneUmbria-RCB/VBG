package it.gruppoinit.domain.nla;

import java.math.BigInteger;

public abstract class AllegatoBase {

    protected String descrizione;
    protected String nomeFileOriginale;
    protected String mime;
    protected String mimeBase;
    protected BigInteger dimensione;
    protected String tipoCodice;
    protected String tipovalore;

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getNomeFileOriginale() {

	return nomeFileOriginale;
    }

    public void setNomeFileOriginale(String nomeFileOriginale) {

	this.nomeFileOriginale = nomeFileOriginale;
    }

    public String getMime() {

	return mime;
    }

    public void setMime(String mime) {

	this.mime = mime;
    }

    public String getMimeBase() {

	return mimeBase;
    }

    public void setMimeBase(String mimeBase) {

	this.mimeBase = mimeBase;
    }

    public BigInteger getDimensione() {

	return dimensione;
    }

    public void setDimensione(BigInteger dimensione) {

	this.dimensione = dimensione;
    }

    public String getTipoCodice() {

	return tipoCodice;
    }

    public void setTipoCodice(String tipoCodice) {

	this.tipoCodice = tipoCodice;
    }

    public String getTipovalore() {

	return tipovalore;
    }

    public void setTipovalore(String tipovalore) {

	this.tipovalore = tipovalore;
    }
}
