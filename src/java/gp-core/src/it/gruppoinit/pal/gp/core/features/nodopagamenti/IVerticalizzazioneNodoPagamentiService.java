package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListContestoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettiPendenzaEnum;

public interface IVerticalizzazioneNodoPagamentiService {

    boolean isAttiva();

    String arUrlBack();

    String arUrlRitorno();

    String blackListTimeCheckPagam(BlackListContestoEnum contesto);

    String arCodFiscEnteCreditore();

    Integer idModalitaPagamento();

    String urlWs();

    String tipomovimentoDocFattura();

    String tipomovimentoDocAvviso();

    SoggettiPendenzaEnum soggettoPendenza();

    boolean creaPerSoggettiCollegati();

    List<String> findCfEntiCreditoriConfigurati();

    String findUrlConfigurato();
}
