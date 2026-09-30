package it.gruppoinit.pal.gp.core.features.movimenti.metadati;

import java.util.UUID;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;

public class MovimentoMetadatoUUID extends AbstractMovimentiMetadato {

    public static final String NOME_METADATO = "UUID";

    public static MovimentiMetadati fromMovimento(Integer codice) {

	MovimentiMetadati md = new MovimentiMetadati();
	MovimentiMetadatiId id = new MovimentiMetadatiId(ORMHelper.getIdcomune(), codice, NOME_METADATO);
	md.setId(id);
	md.setValore(UUID.randomUUID().toString() + "-" + System.currentTimeMillis());
	return md;
    }

    @Override
    public String getChiave() {

	return NOME_METADATO;
    }
}
