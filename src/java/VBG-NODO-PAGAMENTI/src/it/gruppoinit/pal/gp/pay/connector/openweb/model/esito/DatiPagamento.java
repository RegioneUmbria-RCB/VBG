package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "iuv", //
	"causale", //
	"importo", //
	"idElemento", //
	"tipoPersonaVersante", //
	"cfVersante", //
	"cognomeVersante", //
	"nomeVersante", //
	"viaVersante", //
	"civicoVersante", //    
	"comuneVersante", //    
	"capVersante", //    
	"proVersante", //    
	"nazioneVersante", //
	"emailVersante", //
	"tipoPersonaPagatore", //
	"cfPagatore", //
	"cognomePagatore", //
	"nomePagatore", //
	"viaPagatore", //
	"civicoPagatore", //
	"comunePagatore", //
	"capPagatore", //
	"provPagatore", //
	"nazionePagatore", //
	"emailPagatore", //
	"scadenza", //    
	"dataPagamento", //
	"metodoPagamento", //
	"stato", //
	"flussoRiversamento", //
	"dataRiversamento", //
	"commissioni", //
	"istitutoCredito", //
	"importoPagato", //
	"croPagamento", //
	"importoRiversato", //
	"accertamento", //
	"idTipoDovuto", //
	"titoloTipoDovuto", //
	"codiceTipoDovuto", //
	"identificativoRiversamento", //
	"numeroProvvisorio", //
	"dataProvvisorio", //
	"avviso", //
	"annoCompetenza", //
	"dataInserimento" //
})
public class DatiPagamento {

    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "causale")
    private String causale;
    @XmlElement(name = "importo")
    private String importo;
    @XmlElement(name = "id_elemento")
    private String idElemento;
    @XmlElement(name = "tipo_persona_versante")
    private String tipoPersonaVersante;
    @XmlElement(name = "cf_versante")
    private String cfVersante;
    @XmlElement(name = "cognome_versante")
    private String cognomeVersante;
    @XmlElement(name = "nome_versante")
    private String nomeVersante;
    @XmlElement(name = "via_versante")
    private String viaVersante;
    @XmlElement(name = "civico_versante")
    private String civicoVersante;
    @XmlElement(name = "comune_versante")
    private String comuneVersante;
    @XmlElement(name = "cap_versante")
    private String capVersante;
    @XmlElement(name = "prov_versante")
    private String proVersante;
    @XmlElement(name = "nazione_versante")
    private String nazioneVersante;
    @XmlElement(name = "nazione_email_versanteversante")
    private String emailVersante;
    @XmlElement(name = "tipo_persona_pagatore")
    private String tipoPersonaPagatore;
    @XmlElement(name = "cf_pagatore")
    private String cfPagatore;
    @XmlElement(name = "cognome_pagatore")
    private String cognomePagatore;
    @XmlElement(name = "nome_pagatore")
    private String nomePagatore;
    @XmlElement(name = "via_pagatore")
    private String viaPagatore;
    @XmlElement(name = "civico_pagatore")
    private String civicoPagatore;
    @XmlElement(name = "comune_pagatore")
    private String comunePagatore;
    @XmlElement(name = "cap_pagatore")
    private String capPagatore;
    @XmlElement(name = "prov_pagatore")
    private String provPagatore;
    @XmlElement(name = "nazione_pagatore")
    private String nazionePagatore;
    @XmlElement(name = "email_pagatore")
    private String emailPagatore;
    @XmlElement(name = "scadenza")
    private String scadenza;
    @XmlElement(name = "data_pagamento")
    private String dataPagamento;
    @XmlElement(name = "metodo_pagamento")
    private String metodoPagamento;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "flusso_riversamento")
    private String flussoRiversamento;
    @XmlElement(name = "data_riversamento")
    private String dataRiversamento;
    @XmlElement(name = "commissioni")
    private String commissioni;
    @XmlElement(name = "istituto_credito")
    private String istitutoCredito;
    @XmlElement(name = "importo_pagato")
    private String importoPagato;
    @XmlElement(name = "cro_pagamento")
    private String croPagamento;
    @XmlElement(name = "importo_riversato")
    private String importoRiversato;
    @XmlElement(name = "accertamento")
    private String accertamento;
    @XmlElement(name = "id_tipo_dovuto")
    private String idTipoDovuto;
    @XmlElement(name = "titolo_tipo_dovuto")
    private String titoloTipoDovuto;
    @XmlElement(name = "codice_tipo_dovuto")
    private String codiceTipoDovuto;
    @XmlElement(name = "identificativo_riversamento")
    private String identificativoRiversamento;
    @XmlElement(name = "numero_provvisorio")
    private String numeroProvvisorio;
    @XmlElement(name = "data_provvisorio")
    private String dataProvvisorio;
    @XmlElement(name = "avviso")
    private Integer avviso;
    @XmlElement(name = "anno_competenza")
    private Integer annoCompetenza;
    @XmlElement(name = "data_inserimento")
    private String dataInserimento;

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public String getImporto() {

	return importo;
    }

    public void setImporto(String importo) {

	this.importo = importo;
    }

    public String getIdElemento() {

	return idElemento;
    }

    public void setIdElemento(String idElemento) {

	this.idElemento = idElemento;
    }

    public String getTipoPersonaVersante() {

	return tipoPersonaVersante;
    }

    public void setTipoPersonaVersante(String tipoPersonaVersante) {

	this.tipoPersonaVersante = tipoPersonaVersante;
    }

    public String getCfVersante() {

	return cfVersante;
    }

    public void setCfVersante(String cfVersante) {

	this.cfVersante = cfVersante;
    }

    public String getCognomeVersante() {

	return cognomeVersante;
    }

    public void setCognomeVersante(String cognomeVersante) {

	this.cognomeVersante = cognomeVersante;
    }

    public String getNomeVersante() {

	return nomeVersante;
    }

    public void setNomeVersante(String nomeVersante) {

	this.nomeVersante = nomeVersante;
    }

    public String getViaVersante() {

	return viaVersante;
    }

    public void setViaVersante(String viaVersante) {

	this.viaVersante = viaVersante;
    }

    public String getCivicoVersante() {

	return civicoVersante;
    }

    public void setCivicoVersante(String civicoVersante) {

	this.civicoVersante = civicoVersante;
    }

    public String getComuneVersante() {

	return comuneVersante;
    }

    public void setComuneVersante(String comuneVersante) {

	this.comuneVersante = comuneVersante;
    }

    public String getCapVersante() {

	return capVersante;
    }

    public void setCapVersante(String capVersante) {

	this.capVersante = capVersante;
    }

    public String getProVersante() {

	return proVersante;
    }

    public void setProVersante(String proVersante) {

	this.proVersante = proVersante;
    }

    public String getNazioneVersante() {

	return nazioneVersante;
    }

    public void setNazioneVersante(String nazioneVersante) {

	this.nazioneVersante = nazioneVersante;
    }

    public String getEmailVersante() {

	return emailVersante;
    }

    public void setEmailVersante(String emailVersante) {

	this.emailVersante = emailVersante;
    }

    public String getTipoPersonaPagatore() {

	return tipoPersonaPagatore;
    }

    public void setTipoPersonaPagatore(String tipoPersonaPagatore) {

	this.tipoPersonaPagatore = tipoPersonaPagatore;
    }

    public String getCfPagatore() {

	return cfPagatore;
    }

    public void setCfPagatore(String cfPagatore) {

	this.cfPagatore = cfPagatore;
    }

    public String getCognomePagatore() {

	return cognomePagatore;
    }

    public void setCognomePagatore(String cognomePagatore) {

	this.cognomePagatore = cognomePagatore;
    }

    public String getNomePagatore() {

	return nomePagatore;
    }

    public void setNomePagatore(String nomePagatore) {

	this.nomePagatore = nomePagatore;
    }

    public String getViaPagatore() {

	return viaPagatore;
    }

    public void setViaPagatore(String viaPagatore) {

	this.viaPagatore = viaPagatore;
    }

    public String getCivicoPagatore() {

	return civicoPagatore;
    }

    public void setCivicoPagatore(String civicoPagatore) {

	this.civicoPagatore = civicoPagatore;
    }

    public String getComunePagatore() {

	return comunePagatore;
    }

    public void setComunePagatore(String comunePagatore) {

	this.comunePagatore = comunePagatore;
    }

    public String getCapPagatore() {

	return capPagatore;
    }

    public void setCapPagatore(String capPagatore) {

	this.capPagatore = capPagatore;
    }

    public String getProvPagatore() {

	return provPagatore;
    }

    public void setProvPagatore(String provPagatore) {

	this.provPagatore = provPagatore;
    }

    public String getNazionePagatore() {

	return nazionePagatore;
    }

    public void setNazionePagatore(String nazionePagatore) {

	this.nazionePagatore = nazionePagatore;
    }

    public String getEmailPagatore() {

	return emailPagatore;
    }

    public void setEmailPagatore(String emailPagatore) {

	this.emailPagatore = emailPagatore;
    }

    public String getScadenza() {

	return scadenza;
    }

    public void setScadenza(String scadenza) {

	this.scadenza = scadenza;
    }

    public String getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    public String getMetodoPagamento() {

	return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {

	this.metodoPagamento = metodoPagamento;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getFlussoRiversamento() {

	return flussoRiversamento;
    }

    public void setFlussoRiversamento(String flussoRiversamento) {

	this.flussoRiversamento = flussoRiversamento;
    }

    public String getDataRiversamento() {

	return dataRiversamento;
    }

    public void setDataRiversamento(String dataRiversamento) {

	this.dataRiversamento = dataRiversamento;
    }

    public String getCommissioni() {

	return commissioni;
    }

    public void setCommissioni(String commissioni) {

	this.commissioni = commissioni;
    }

    public String getIstitutoCredito() {

	return istitutoCredito;
    }

    public void setIstitutoCredito(String istitutoCredito) {

	this.istitutoCredito = istitutoCredito;
    }

    public String getImportoPagato() {

	return importoPagato;
    }

    public void setImportoPagato(String importoPagato) {

	this.importoPagato = importoPagato;
    }

    public String getCroPagamento() {

	return croPagamento;
    }

    public void setCroPagamento(String croPagamento) {

	this.croPagamento = croPagamento;
    }

    public String getImportoRiversato() {

	return importoRiversato;
    }

    public void setImportoRiversato(String importoRiversato) {

	this.importoRiversato = importoRiversato;
    }

    public String getAccertamento() {

	return accertamento;
    }

    public void setAccertamento(String accertamento) {

	this.accertamento = accertamento;
    }

    public String getIdTipoDovuto() {

	return idTipoDovuto;
    }

    public void setIdTipoDovuto(String idTipoDovuto) {

	this.idTipoDovuto = idTipoDovuto;
    }

    public String getTitoloTipoDovuto() {

	return titoloTipoDovuto;
    }

    public void setTitoloTipoDovuto(String titoloTipoDovuto) {

	this.titoloTipoDovuto = titoloTipoDovuto;
    }

    public String getCodiceTipoDovuto() {

	return codiceTipoDovuto;
    }

    public void setCodiceTipoDovuto(String codiceTipoDovuto) {

	this.codiceTipoDovuto = codiceTipoDovuto;
    }

    public String getIdentificativoRiversamento() {

	return identificativoRiversamento;
    }

    public void setIdentificativoRiversamento(String identificativoRiversamento) {

	this.identificativoRiversamento = identificativoRiversamento;
    }

    public String getNumeroProvvisorio() {

	return numeroProvvisorio;
    }

    public void setNumeroProvvisorio(String numeroProvvisorio) {

	this.numeroProvvisorio = numeroProvvisorio;
    }

    public String getDataProvvisorio() {

	return dataProvvisorio;
    }

    public void setDataProvvisorio(String dataProvvisorio) {

	this.dataProvvisorio = dataProvvisorio;
    }

    public Integer getAvviso() {

	return avviso;
    }

    public void setAvviso(Integer avviso) {

	this.avviso = avviso;
    }

    public Integer getAnnoCompetenza() {

	return annoCompetenza;
    }

    public void setAnnoCompetenza(Integer annoCompetenza) {

	this.annoCompetenza = annoCompetenza;
    }

    public String getDataInserimento() {

	return dataInserimento;
    }

    public void setDataInserimento(String dataInserimento) {

	this.dataInserimento = dataInserimento;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
