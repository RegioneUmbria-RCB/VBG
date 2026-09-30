package it.gruppoinit.pdd.utils;

public class IstanzaAllegatiHelper {

    private Integer id;
    private String tipo;
    private Integer codiceOggetto;
    private String descrizioneDocumento;
    private String nomeFile;
    private Integer codiceInventario;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getDescrizioneDocumento() {

	return descrizioneDocumento;
    }

    public void setDescrizioneDocumento(String descrizioneDocumento) {

	this.descrizioneDocumento = descrizioneDocumento;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public Integer getCodiceInventario() {

	return codiceInventario;
    }

    public void setCodiceInventario(Integer codiceInventario) {

	this.codiceInventario = codiceInventario;
    }
}
