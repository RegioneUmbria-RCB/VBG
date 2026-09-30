package it.gruppoinit.pal.gp.core.domain.helper;

public class PosteggioPianoRotazioneHelper {

    private Integer ordine;
    private MercatiUsoDTO giorno;
    private MercatiDDTO posteggio;
    private Integer codiceIstanza;
    private Integer codiceGraduatoriad;

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public MercatiUsoDTO getGiorno() {

	return giorno;
    }

    public void setGiorno(MercatiUsoDTO giorno) {

	this.giorno = giorno;
    }

    public MercatiDDTO getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(MercatiDDTO posteggio) {

	this.posteggio = posteggio;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceGraduatoriad() {

	return codiceGraduatoriad;
    }

    public void setCodiceGraduatoriad(Integer codiceGraduatoriad) {

	this.codiceGraduatoriad = codiceGraduatoriad;
    }
}
