package it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;

public class MovimentiAllegatiResolverRequest {

    private DocumentMergeHelper documentMergeHelper;
    private Integer codiceIstanza;
    private Integer codiceMovimento;
    private String tipoMovimento;
    private Integer codiceLettera;
    private Integer codiceOggetto;
    private String descrizione;

    public DocumentMergeHelper getDocumentMergeHelper() {

	return documentMergeHelper;
    }

    public void setDocumentMergeHelper(DocumentMergeHelper documentMergeHelper) {

	this.documentMergeHelper = documentMergeHelper;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public String getTipoMovimento() {

	return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {

	this.tipoMovimento = tipoMovimento;
    }

    public Integer getCodiceLettera() {

	return codiceLettera;
    }

    public void setCodiceLettera(Integer codiceLettera) {

	this.codiceLettera = codiceLettera;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
