package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.anagrafetributaria.AnagrafeTributariaDAO;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ATTipologiaTracciatoEnum;

@Component(value = "aTParserFactory")
public class ATParserFactory {

    @Autowired
    private AnagrafeTributariaDAO anagrafeTributariaDAO;

    public ITracciatoATParser getParser(String contenutoTracciato) {

	ATTipologiaTracciatoEnum tipo = null;
	try {
	    tipo = leggiTracciato(contenutoTracciato);
	} catch (IOException e) {
	    throw new AtParserException("Non è stato possibile recuperare il tipo di tracciato dal file", e);
	}
	switch (tipo) {
	case COMMERCIO:
	    return new CommercioTracciatoATParser(anagrafeTributariaDAO);
	case EDILIZIA:
	    return new EdiliziaTracciatoATParser(anagrafeTributariaDAO);
	}
	throw new AtParserException("Non è stato possibile recuperare il tipo di tracciato dal file");
    }

    private ATTipologiaTracciatoEnum leggiTracciato(String contenuto) throws IOException {

	StringReader sr = new StringReader(contenuto);
	BufferedReader r = new BufferedReader(sr);
	String firstLine = StringUtils.defaultString(r.readLine());
	int len = firstLine.length();
	if (len == 368) {
	    return ATTipologiaTracciatoEnum.EDILIZIA;
	}
	if (len == 199) {
	    return ATTipologiaTracciatoEnum.COMMERCIO;
	}
	throw new AtParserException("Il tracciato non rispetta le specifiche " + len);
    }
}
