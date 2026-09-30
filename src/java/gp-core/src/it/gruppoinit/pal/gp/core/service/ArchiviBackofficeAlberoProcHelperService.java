package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;

public interface ArchiviBackofficeAlberoProcHelperService {

    public void insertOrUpdateAlberoProcSistemaEsterno(String codiceSistemaEsterno, String descrizione, String codicenaturaPorcedura,
	    String idNodoPadre, boolean isAggiorna, Software sw);

    public Tipifamiglieendo insertOrUpdateTipiFamiglieEndoSistemaEsterno(String codiceSistemaEsterno, String descrizione, boolean isAggiorna,
	    Software sw);

    public Tipiendo insertOrUpdateTipiCategorieEndoSistemaEsterno(String codiceSistemaEsterno, String descrizione, boolean isAggiorna,
	    Tipifamiglieendo tipifamiglieendo, Software sw);

    public Inventarioprocedimenti insertOrUpdateInventarioProcedimentiSistemaEsterno(String codiceSistemaEsterno, String descrizione, String prefix,
	    Tipiendo tipiEndo, Tempificazioni tempificazione, Naturaendo naturaendo, Amministrazioni amministrazione, boolean isAggiorna, Software sw);

    public boolean insertOrUpdateAlberoprocEndoSistemaEsterno(String codiceSistemaEsternoAlberoProc, String codiceSistemaEsternoInventarioproc,
	    String prefix, boolean flagPrincipale, boolean flagPubblicato, boolean flagRichiesto, boolean flagUsaNelback, String azAzione, Software sw);
}
