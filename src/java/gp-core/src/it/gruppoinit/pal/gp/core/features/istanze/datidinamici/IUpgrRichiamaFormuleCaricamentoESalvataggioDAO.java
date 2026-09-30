package it.gruppoinit.pal.gp.core.features.istanze.datidinamici;

import java.util.Set;
import java.util.TreeSet;

public interface IUpgrRichiamaFormuleCaricamentoESalvataggioDAO {

    TreeSet<Integer> getCodiciIstanzaByInterventi(Set<Integer> elencoInterventi);
}
