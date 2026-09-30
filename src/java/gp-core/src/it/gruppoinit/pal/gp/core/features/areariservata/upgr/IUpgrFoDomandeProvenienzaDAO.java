package it.gruppoinit.pal.gp.core.features.areariservata.upgr;

import java.util.List;

public interface IUpgrFoDomandeProvenienzaDAO {

    List<InterventoSoloDomandaOnlineBean> recuperaInterventiSoloDomandaOnline();

    List<InterventoSoloDomandaOnlineBean> recuperaFigli(InterventoSoloDomandaOnlineBean interventoPadre);

    void aggiornaProvenienza(String string);

    void aggiornaProvenienza(String string, List<InterventoSoloDomandaOnlineBean> interventi);
}
