package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser;

import java.util.ArrayList;
import java.util.List;

public class AnTribParserGruppi {

    private int ordine;
    private List<AnTribRiferimentiIstanze> istanzeTrovate;
    private List<AnTribParserErrori> errori;

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    public List<AnTribRiferimentiIstanze> getIstanzeTrovate() {

	if (this.istanzeTrovate == null) {
	    this.istanzeTrovate = new ArrayList<AnTribRiferimentiIstanze>();
	}
	return istanzeTrovate;
    }

    public List<AnTribParserErrori> getErrori() {

	if (this.errori == null) {
	    this.errori = new ArrayList<AnTribParserErrori>();
	}
	return errori;
    }
}
