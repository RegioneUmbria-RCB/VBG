package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

public class TempirispostaHelperBean {

    private String tipomovimento;
    private String tipocontromovimento;
    private List<TempirispostaValoriHelper> valori;

    public TempirispostaHelperBean(String tipomovimento, String tipocontromovimento, List<TempirispostaValoriHelper> valori) {

	super();
	this.tipomovimento = tipomovimento;
	this.tipocontromovimento = tipocontromovimento;
	this.valori = valori;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public String getTipocontromovimento() {

	return tipocontromovimento;
    }

    public List<TempirispostaValoriHelper> getValori() {

	if (this.valori == null) {
	    this.valori = new ArrayList<TempirispostaValoriHelper>();
	}
	return valori;
    }
}
