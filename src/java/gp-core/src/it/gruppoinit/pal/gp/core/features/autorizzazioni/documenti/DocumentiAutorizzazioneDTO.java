package it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti;

import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazioneId;

public class DocumentiAutorizzazioneDTO {

    private DocumentiAutorizzazioneId id;
    private Integer codiceIstanza;
    private Integer codiceMovimento;
    private Integer codiceInventario;
    private Integer codiceAnagrafe;
    private Integer codiceDocumentiistanza;
    private Integer codiceMovimentiallegati;
    private Integer codiceIstanzeallegati;
    private Integer codiceAnagrafedocumenti;
    private Integer codiceIstanzeprocure;
    private String nomeFile;
    private String numeroAutorizzazione;
    private String numeroIstanza;
    private String movimento;
    private String procedimento;
    private String anagrafe;
    private String nominativo;
    private String nome;
    private String procure;
    private String descFileDocumentiistanza;
    private String descFileMovimentiallegati;
    private String descFileIstanzeallegati;
    private String descFileDocumentianagrafe;
    private String descFileIstanzeprocure;
    private boolean principale;

    public String getProcedimento() {

	return procedimento;
    }

    public void setProcedimento(String procedimento) {

	this.procedimento = procedimento;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getDescFileDocumentiistanza() {

	return descFileDocumentiistanza;
    }

    public void setDescFileDocumentiistanza(String descFileDocumentiistanza) {

	this.descFileDocumentiistanza = descFileDocumentiistanza;
    }

    public String getDescFileMovimentiallegati() {

	return descFileMovimentiallegati;
    }

    public void setDescFileMovimentiallegati(String descFileMovimentiallegati) {

	this.descFileMovimentiallegati = descFileMovimentiallegati;
    }

    public String getDescFileIstanzeallegati() {

	return descFileIstanzeallegati;
    }

    public void setDescFileIstanzeallegati(String descFileIstanzeallegati) {

	this.descFileIstanzeallegati = descFileIstanzeallegati;
    }

    public String getDescFileDocumentianagrafe() {

	return descFileDocumentianagrafe;
    }

    public void setDescFileDocumentianagrafe(String descFileDocumentianagrafe) {

	this.descFileDocumentianagrafe = descFileDocumentianagrafe;
    }

    public String getDescFileIstanzeprocure() {

	return descFileIstanzeprocure;
    }

    public void setDescFileIstanzeprocure(String descFileIstanzeprocure) {

	this.descFileIstanzeprocure = descFileIstanzeprocure;
    }

    public DocumentiAutorizzazioneId getId() {

	return id;
    }

    public void setId(DocumentiAutorizzazioneId id) {

	this.id = id;
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

    public Integer getCodiceInventario() {

	return codiceInventario;
    }

    public void setCodiceInventario(Integer codiceInventario) {

	this.codiceInventario = codiceInventario;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public String getMovimento() {

	return movimento;
    }

    public void setMovimento(String movimento) {

	this.movimento = movimento;
    }

    public String getEndo() {

	return procedimento;
    }

    public void setEndo(String endo) {

	this.procedimento = endo;
    }

    public String getAnagrafe() {

	this.anagrafe = this.getNome() + " " + this.getNominativo();
	return this.anagrafe;
    }

    public Integer getCodiceDocumentiistanza() {

	return codiceDocumentiistanza;
    }

    public void setCodiceDocumentiistanza(Integer codiceDocumentiistanza) {

	this.codiceDocumentiistanza = codiceDocumentiistanza;
    }

    public Integer getCodiceMovimentiallegati() {

	return codiceMovimentiallegati;
    }

    public void setCodiceMovimentiallegati(Integer codiceMovimentiallegati) {

	this.codiceMovimentiallegati = codiceMovimentiallegati;
    }

    public Integer getCodiceIstanzeallegati() {

	return codiceIstanzeallegati;
    }

    public void setCodiceIstanzeallegati(Integer codiceIstanzeallegati) {

	this.codiceIstanzeallegati = codiceIstanzeallegati;
    }

    public Integer getCodiceAnagrafedocumenti() {

	return codiceAnagrafedocumenti;
    }

    public void setCodiceAnagrafedocumenti(Integer codiceAnagrafedocumenti) {

	this.codiceAnagrafedocumenti = codiceAnagrafedocumenti;
    }

    public Integer getCodiceIstanzeprocure() {

	return codiceIstanzeprocure;
    }

    public void setCodiceIstanzeprocure(Integer codiceIstanzeprocure) {

	this.codiceIstanzeprocure = codiceIstanzeprocure;
    }

    public void setAnagrafe(String anagrafe) {

	this.anagrafe = anagrafe;
    }

    public String getProcure() {

	return procure;
    }

    public void setProcure(String procure) {

	this.procure = procure;
    }

    public String getNumeroAutorizzazione() {

	return numeroAutorizzazione;
    }

    public void setNumeroAutorizzazione(String numeroAutorizzazione) {

	this.numeroAutorizzazione = numeroAutorizzazione;
    }

    public boolean isPrincipale() {

	return principale;
    }

    public void setPrincipale(boolean principale) {

	this.principale = principale;
    }
}
