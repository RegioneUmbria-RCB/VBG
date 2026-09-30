package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.AlberoSintattico;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class Blocco extends TokenBase {

    AlberoSintattico alberoSintattico;

    public Blocco(AlberoSintattico alberoSintattico) {

	super();
	this.alberoSintattico = alberoSintattico;
    }

    @Override
    public String getValore() {

	return alberoSintattico.getFormula();
    }

    public AlberoSintattico getAlberoSintattico() {

	return alberoSintattico;
    }

    @Override
    public TipoToken getTipo() {

	return TipoToken.BLOCCO;
    }

    @Override
    public int getValoreConteggio() {

	// TODO Auto-generated method stub
	return this.alberoSintattico.totaleTokenInclusiAnnidati();
    }
}
