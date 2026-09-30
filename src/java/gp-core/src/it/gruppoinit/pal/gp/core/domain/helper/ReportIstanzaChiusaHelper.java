package it.gruppoinit.pal.gp.core.domain.helper;

public class ReportIstanzaChiusaHelper {

    private static final long serialVersionUID = 9010609930659621817L;
    private boolean chiusa;
    private String messaggioErrore;
    private IstanzeDaChiudereHelper istanza;

    public boolean isChiusa() {

	return chiusa;
    }

    public void setChiusa(boolean chiusa) {

	this.chiusa = chiusa;
    }

    public String getMessaggioErrore() {

	return messaggioErrore;
    }

    public void setMessaggioErrore(String messaggioErrore) {

	this.messaggioErrore = messaggioErrore;
    }

    public IstanzeDaChiudereHelper getIstanza() {

	return istanza;
    }

    public void setIstanza(IstanzeDaChiudereHelper istanza) {

	this.istanza = istanza;
    }
}
