package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagraficaPagatore", propOrder = { "tipoCodicePagatore", "codicePagatore", "nomeCognomePagatore", "indirizzoPagatore",
	"numeroCivicoPagatore", "capPagatore", "localitaPagatore", "provinciaPagatore", "statoPagatore", "cellPagatore", "emailPagatore" })
public class AnagraficaPagatore {

    @XmlElement(name = "tipo_codice_pagatore")
    private String tipoCodicePagatore;
    @XmlElement(name = "codice_pagatore")
    private String codicePagatore;
    @XmlElement(name = "nome_cognome_pagatore")
    private String nomeCognomePagatore;
    @XmlElement(name = "indirizzo_pagatore")
    private String indirizzoPagatore;
    @XmlElement(name = "numero_civico_pagatore")
    private String numeroCivicoPagatore;
    @XmlElement(name = "cap_pagatore")
    private String capPagatore;
    @XmlElement(name = "localita_pagatore")
    private String localitaPagatore;
    @XmlElement(name = "provincia_pagatore")
    private String provinciaPagatore;
    @XmlElement(name = "stato_pagatore")
    private String statoPagatore;
    @XmlElement(name = "cell_pagatore")
    private String cellPagatore;
    @XmlElement(name = "email_pagatore")
    private String emailPagatore;

    public String getTipoCodicePagatore() {

	return tipoCodicePagatore;
    }

    public void setTipoCodicePagatore(String tipoCodicePagatore) {

	this.tipoCodicePagatore = tipoCodicePagatore;
    }

    public String getCodicePagatore() {

	return codicePagatore;
    }

    public void setCodicePagatore(String codicePagatore) {

	this.codicePagatore = codicePagatore;
    }

    public String getNomeCognomePagatore() {

	return nomeCognomePagatore;
    }

    public void setNomeCognomePagatore(String nomeCognomePagatore) {

	this.nomeCognomePagatore = nomeCognomePagatore;
    }

    public String getIndirizzoPagatore() {

	return indirizzoPagatore;
    }

    public void setIndirizzoPagatore(String indirizzoPagatore) {

	this.indirizzoPagatore = indirizzoPagatore;
    }

    public String getNumeroCivicoPagatore() {

	return numeroCivicoPagatore;
    }

    public void setNumeroCivicoPagatore(String numeroCivicoPagatore) {

	this.numeroCivicoPagatore = numeroCivicoPagatore;
    }

    public String getCapPagatore() {

	return capPagatore;
    }

    public void setCapPagatore(String capPagatore) {

	this.capPagatore = capPagatore;
    }

    public String getLocalitaPagatore() {

	return localitaPagatore;
    }

    public void setLocalitaPagatore(String localitaPagatore) {

	this.localitaPagatore = localitaPagatore;
    }

    public String getProvinciaPagatore() {

	return provinciaPagatore;
    }

    public void setProvinciaPagatore(String provinciaPagatore) {

	this.provinciaPagatore = provinciaPagatore;
    }

    public String getStatoPagatore() {

	return statoPagatore;
    }

    public void setStatoPagatore(String statoPagatore) {

	this.statoPagatore = statoPagatore;
    }

    public String getCellPagatore() {

	return cellPagatore;
    }

    public void setCellPagatore(String cellPagatore) {

	this.cellPagatore = cellPagatore;
    }

    public String getEmailPagatore() {

	return emailPagatore;
    }

    public void setEmailPagatore(String emailPagatore) {

	this.emailPagatore = emailPagatore;
    }
}
