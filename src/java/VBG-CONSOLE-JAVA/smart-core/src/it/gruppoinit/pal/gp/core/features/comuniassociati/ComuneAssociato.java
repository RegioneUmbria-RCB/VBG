package it.gruppoinit.pal.gp.core.features.comuniassociati;

import org.opensaml.artifact.InvalidArgumentException;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;

public class ComuneAssociato {

    private String codiceComune;
    private String comune;

    public String getCodiceComune() {

	return codiceComune;
    }

    public String getComune() {

	return comune;
    }

    public static ComuneAssociato FromComuniassociati(Comuniassociati comune) {

	if (comune == null || comune.getComune() == null) {
	    throw new InvalidArgumentException("Impossibile invocare ComuneAssociato.FromComuniassociati passando comune null");
	}
	ComuneAssociato comuneAssociato = new ComuneAssociato();
	comuneAssociato.codiceComune = comune.getComune().getCodicecomune();
	comuneAssociato.comune = comune.getComune().getComune();
	return comuneAssociato;
    }
}
