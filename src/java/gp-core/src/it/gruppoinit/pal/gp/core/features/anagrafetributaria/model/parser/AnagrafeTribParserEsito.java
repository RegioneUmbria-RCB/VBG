package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ATTipologiaTracciatoEnum;

public class AnagrafeTribParserEsito {

    private ATTipologiaTracciatoEnum tipologia;
    private List<AnTribParserGruppi> gruppi;

    public List<AnTribParserGruppi> getGruppi() {

	if (this.gruppi == null) {
	    this.gruppi = new ArrayList<AnTribParserGruppi>();
	}
	return gruppi;
    }

    public ATTipologiaTracciatoEnum getTipologia() {

	return tipologia;
    }

    public void setTipologia(ATTipologiaTracciatoEnum tipologia) {

	this.tipologia = tipologia;
    }
}
