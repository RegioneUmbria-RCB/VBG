package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class ProceduraInvioNodoBollettazioneBase {

    public final static Logger LOGGER_BOLL_INVIO = LoggerFactory.getLogger(ProceduraInvioNodoBollettazioneBase.class);

    public String getKeyBollettazioneInvioNodoString(int idBollettazioneTestata) {

	return ORMHelper.getIdcomuneAlias() + "-" + idBollettazioneTestata;
    }
}
