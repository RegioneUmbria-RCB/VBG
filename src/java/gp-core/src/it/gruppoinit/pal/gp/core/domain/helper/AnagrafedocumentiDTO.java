package it.gruppoinit.pal.gp.core.domain.helper;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class AnagrafedocumentiDTO {

    //id.codice
    private PkId id;
    //istanza.id.codice
    private Integer codiceIstanza;
    //tipidocumento.documento
    private String documento;
    //oggetto.id.codice
    private Integer codiceOggetto;
    //oggetto.nomefile
    private String nomeFile;
    //anagrafe.nome
    private String nome;
    //anagrafe.nominativo
    private String nominativo;
    // Il campo è utilizzato per popolare l'anagraficaa cui è associato il documento
    private String transientTipoSoggettoAndRichiedente;
    private boolean transientSegnaPerInvio;
    private String codiceComune;
    private String tipoDocumento;
    private boolean transientSegnaPerInvioPec;

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getDocumento() {

	return documento;
    }

    public void setDocumento(String documento) {

	this.documento = documento;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public boolean isTransientSegnaPerInvio() {

	return transientSegnaPerInvio;
    }

    public void setTransientSegnaPerInvio(boolean transientSegnaPerInvio) {

	this.transientSegnaPerInvio = transientSegnaPerInvio;
    }

    public String getTransientTipoSoggettoAndRichiedente() {

	transientTipoSoggettoAndRichiedente = "";
	if (StringUtils.isNotBlank(getNome())) {
	    transientTipoSoggettoAndRichiedente += getNome() + " ";
	}
	transientTipoSoggettoAndRichiedente += getNominativo();
	return transientTipoSoggettoAndRichiedente;
    }

    public void setTransientTipoSoggettoAndRichiedente(String transientTipoSoggettoAndRichiedente) {

	this.transientTipoSoggettoAndRichiedente = transientTipoSoggettoAndRichiedente;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public String getTipoDocumento() {

	return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    public boolean isTransientSegnaPerInvioPec() {

	return transientSegnaPerInvioPec;
    }

    public void setTransientSegnaPerInvioPec(boolean transientSegnaPerInvioPec) {

	this.transientSegnaPerInvioPec = transientSegnaPerInvioPec;
    }
}
