package it.gruppoinit.pal.gp.core.domain.helper;

/**
 * Questa classe di appoggio permette di gestire la visualizzazione delle IstanzeAccessoAttiD nella view
 * (istanzeaccessoattit/form).
 * 
 * @author simone.vernata
 *
 */
public class IstanzeAccessoAttiDHelper {

    private Integer codiceIstanza;
    private String numeroistanza;
    private String descrizioneRichiedente;
    private Integer flgVisualizzaDoc;

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getDescrizioneRichiedente() {

	return descrizioneRichiedente;
    }

    public void setDescrizioneRichiedente(String descrizioneRichiedente) {

	this.descrizioneRichiedente = descrizioneRichiedente;
    }

    public Integer getFlgVisualizzaDoc() {

	return flgVisualizzaDoc;
    }

    public void setFlgVisualizzaDoc(Integer flgVisualizzaDoc) {

	this.flgVisualizzaDoc = flgVisualizzaDoc;
    }
}
