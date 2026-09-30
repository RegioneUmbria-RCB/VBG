package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.custom;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneService;

public interface NumerazioneCustomService extends NumerazioneService {

    public String getDescrizione();

    public void setMovimento(Movimenti movimento);

    public void setIstanza(Istanze istanza);

    public void setCodiceFirmatario(String codiceFirmatario);
}
