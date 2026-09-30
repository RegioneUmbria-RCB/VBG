package it.gruppoinit.pal.gp.core.features.nodopagamenti;

public class PosizioniDebitorieIstanzeoneriBean {

    private Integer codiceIstanzeOneri;
    private Integer idDettPosizioneDebitoria;
    private String messaggioErrore;

    public PosizioniDebitorieIstanzeoneriBean(Integer codiceIstanzeOneri, Integer idDettPosizioneDebitoria) {

	super();
	this.codiceIstanzeOneri = codiceIstanzeOneri;
	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
    }

    public PosizioniDebitorieIstanzeoneriBean(Integer codiceIstanzeOneri, String messaggioErrore) {

	super();
	this.codiceIstanzeOneri = codiceIstanzeOneri;
	this.messaggioErrore = messaggioErrore;
    }

    public Integer getCodiceIstanzeOneri() {

	return codiceIstanzeOneri;
    }

    public Integer getIdDettPosizioneDebitoria() {

	return idDettPosizioneDebitoria;
    }

    public String getMessaggioErrore() {

	return messaggioErrore;
    }
}
