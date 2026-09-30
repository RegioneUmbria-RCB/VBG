package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class AutorizzazioniConcessioniDTO {

    private PkId id;
    private AutorizzazioniDTO autorizzazioni;
    private String codiceposteggio;
    private Integer idposteggio;
    private String mercatiUsoDescrizione;
    private Integer codiceMercatiUso;

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public AutorizzazioniDTO getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(AutorizzazioniDTO autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public Integer getIdposteggio() {

	return idposteggio;
    }

    public void setIdposteggio(Integer idposteggio) {

	this.idposteggio = idposteggio;
    }

    public Integer getCodiceMercatiUso() {

	return codiceMercatiUso;
    }

    public void setCodiceMercatiUso(Integer codiceMercatiUso) {

	this.codiceMercatiUso = codiceMercatiUso;
    }

    public String getMercatiUsoDescrizione() {

	return mercatiUsoDescrizione;
    }

    public void setMercatiUsoDescrizione(String mercatiUsoDescrizione) {

	this.mercatiUsoDescrizione = mercatiUsoDescrizione;
    }
}
