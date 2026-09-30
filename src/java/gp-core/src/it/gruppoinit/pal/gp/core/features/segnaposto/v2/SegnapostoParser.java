package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

public class SegnapostoParser {

    public StrutturaSegnaposto analizza(String segnaposto) {

	if (segnaposto.startsWith("[-")) {
	    // Il segnaposto è nel formato [-SEGNAPOSTO-]
	    if (segnaposto.endsWith("-]")) {
		segnaposto = segnaposto.substring(2, segnaposto.length() - 2);
	    } else {
		throw new IllegalArgumentException("Il segnaposto " + segnaposto + " ha un formato non valido");
	    }
	}
	int parentesiAperta = segnaposto.indexOf('(');
	if (parentesiAperta == -1) {
	    return new StrutturaSegnaposto(segnaposto);
	}
	String argomenti = segnaposto.substring(parentesiAperta + 1, segnaposto.length() - 1);
	return new StrutturaSegnaposto(segnaposto.substring(0, parentesiAperta), argomenti);
    }
}
