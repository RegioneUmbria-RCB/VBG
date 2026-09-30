package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.elaborazione;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RichiestaCalcoloBollettazioneMercato;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.AbstractQueryBollettazioneMercatiHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;

public interface ElaborazioneCalcoloDAO {

    Integer elabora(IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService, RichiestaCalcoloBollettazioneMercato richiesta,
	    Boolean intestaAzienda, AbstractQueryBollettazioneMercatiHelper queryHelper, BollettazioneDAO bollettazioneDAO, String guidOperazione,
	    CreazioneBollTestata creazioneBollTestata, BollCfgTipo bollCfgTipo, ContiService contiService);
}
