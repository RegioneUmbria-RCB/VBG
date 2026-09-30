package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcoloMercati;

public class InfoGiornataPresenzaBean {

    private boolean concessionarioPresente;
    private boolean spuntistaPresente;
    private boolean assenzaGiustificata;
    private String categoriaMerceologicaSalvata;

    public InfoGiornataPresenzaBean(boolean concessionarioPresente, boolean spuntistaPresente, boolean assenzaGiustificata,
	    String categoriaMerceologicaSalvata) {

	this.concessionarioPresente = concessionarioPresente;
	this.spuntistaPresente = spuntistaPresente;
	this.assenzaGiustificata = assenzaGiustificata;
	this.categoriaMerceologicaSalvata = categoriaMerceologicaSalvata;
    }

    public boolean isConcessionarioPresente() {

	return concessionarioPresente;
    }

    public boolean isSpuntistaPresente() {

	return spuntistaPresente;
    }

    public boolean isAssenzaGiustificata() {

	return assenzaGiustificata;
    }

    public String getCategoriaMerceologicaSalvata() {

	return categoriaMerceologicaSalvata;
    }

    public static InfoGiornataPresenzaBean fromPresenza(MercatipresenzeD presenza) {

	if (presenza == null) {
	    return new InfoGiornataPresenzaBean(false, false, false, null);
	}
	boolean spuntistaPresente = BooleanUtils.toBoolean(presenza.isSpuntista());
	String catMerc = null;
	if (presenza.getAttivita() != null && presenza.getAttivita().getId() != null) {
	    catMerc = presenza.getAttivita().getId().getCodiceistat();
	}
	return new InfoGiornataPresenzaBean((presenza.getOccupante() != null && !spuntistaPresente), spuntistaPresente,
		BooleanUtils.toBoolean(presenza.getFlagAssenzaGiust()), catMerc);
    }

    public static InfoGiornataPresenzaBean fromRigaDettaglioCalcoloMercati(RigaDettaglioCalcoloMercati rigaDettaglioCalcoloMercati) {

	return new InfoGiornataPresenzaBean(rigaDettaglioCalcoloMercati.isConcpresente(), rigaDettaglioCalcoloMercati.isSpuntpresente(),
		rigaDettaglioCalcoloMercati.isAssenzaGiustificata(), rigaDettaglioCalcoloMercati.getCatmerc());
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
