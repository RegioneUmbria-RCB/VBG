package it.gruppoinit.pal.gp.pay.service.helper;

public class TipoCaricamentoPosizionidebitorie {

    private boolean caricamentoMassivo;
    private String identificativoOperazione;

    public TipoCaricamentoPosizionidebitorie(boolean caricamentoMassivo, String identificativoOperazione) {

	super();
	this.caricamentoMassivo = caricamentoMassivo;
	this.identificativoOperazione = identificativoOperazione;
    }

    public boolean isCaricamentoMassivo() {

	return caricamentoMassivo;
    }

    public String getIdentificativoOperazione() {

	return identificativoOperazione;
    }
}
