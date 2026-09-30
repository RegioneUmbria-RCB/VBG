package it.gruppoinit.pal.gp.core.features.alberoproc.esclusioni;

import org.opensaml.artifact.InvalidArgumentException;

import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusi;

public class ComuniEsclusi {

    private String codiceComune;
    private String comune;
    private Integer codiceInterventoProc;
    private String intervento;

    public String getCodiceComune() {

	return codiceComune;
    }

    public String getComune() {

	return comune;
    }

    public Integer getCodiceInterventoProc() {

	return codiceInterventoProc;
    }

    public String getIntervento() {

	return intervento;
    }

    public static ComuniEsclusi fromAlberoprocComuniEsclusi(AlberoprocComuniEsclusi alberoprocComuneEscluso) {

	if (alberoprocComuneEscluso == null || alberoprocComuneEscluso.getId() == null) {
	    throw new InvalidArgumentException("Impossibile utilizzare il metodo statico FromAlberoprocComuniEsclusi passando il parametro null");
	}
	if (alberoprocComuneEscluso.getComune() == null) {
	    throw new InvalidArgumentException(
		    "Impossibile utilizzare il metodo statico FromAlberoprocComuniEsclusi passando il parametro con comune null");
	}
	if (alberoprocComuneEscluso.getAlberoproc() == null || alberoprocComuneEscluso.getAlberoproc().getId() == null) {
	    throw new InvalidArgumentException(
		    "Impossibile utilizzare il metodo statico FromAlberoprocComuniEsclusi passando il parametro con alberoproc null");
	}
	ComuniEsclusi comuneEscluso = new ComuniEsclusi();
	comuneEscluso.codiceComune = alberoprocComuneEscluso.getId().getCodiceComune();
	comuneEscluso.comune = alberoprocComuneEscluso.getComune().getComune();
	comuneEscluso.codiceInterventoProc = alberoprocComuneEscluso.getAlberoproc().getId().getCodice();
	comuneEscluso.intervento = alberoprocComuneEscluso.getAlberoproc().getDescrizioneCompleta();
	return comuneEscluso;
    }
}