package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.List;

public interface ICalcoloDisponibilitaService {

    public List<DisponibilitaResponsabile> calcola(Integer codiceIstanza);

    public NumeriIstanzaResponse dettaglio(Integer codiceResponsabile, Integer idTestata);
}
