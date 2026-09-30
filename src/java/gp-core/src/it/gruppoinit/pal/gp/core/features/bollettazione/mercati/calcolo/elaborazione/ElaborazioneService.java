package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.elaborazione;

import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RichiestaCalcoloBollettazioneMercato;

public interface ElaborazioneService {

    Integer elabora(RichiestaCalcoloBollettazioneMercato request, TitolaritaPagamentiEnum titolarita, Boolean intestaAzienda, Boolean conguaglio,
	    CreazioneBollTestata creazioneBollTestata, BollCfgTipo bollCfgTipo);
}
