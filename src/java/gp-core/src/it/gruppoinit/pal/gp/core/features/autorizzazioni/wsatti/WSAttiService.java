package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.ElencoFirmatariResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.InserisciDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.LeggiDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.NumeraDeterminaResponse;

public interface WSAttiService {

    public InserisciDeterminaResponse inserisci(String codiceComune, String oggetto, Integer idAlberoProc, String codiceFirmatario);

    public NumeraDeterminaResponse numera(Integer idDocumento, Date dataInserimento, String codiceComune);

    public LeggiDeterminaResponse leggi(Integer idDocumento, String codiceComune);

    public ElencoFirmatariResponse elencoFirmatari(String codiceComune);

    public String aggiungiAllegato(String codiceComune, Allegato allegato);

    public void fascicola(String codiceComune, Fascicolo datiFascicolo);

    public boolean isFascicolata(String codiceComune, Integer idDocumento);

    public void precompilaAllegatiSecondari(Autorizzazioni aut);

    public void generaAllegatoPrincipale(Autorizzazioni aut);

    public void registraCompletamentoAtto(Autorizzazioni autorizzazione);
}
