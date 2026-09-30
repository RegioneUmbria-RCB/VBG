package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.ComportamentoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.RipartizioneContiHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.StrategiaInvioComunicazioniEnum;

public interface IVerticalizzazioneAbbonamentoPosteggiService {

    boolean isAttiva();

    ComportamentoEnum comportamento();

    RipartizioneContiHelper ripartizione();

    BigDecimal sogliaAvviso();

    boolean isBloccaAssegnazioni();

    String messaggioCreditoSottoSoglia();

    String descrizioneComunicazioneChiusuraGiornata();

    Mailtipo templateProtocollazione();

    Amministrazioni amministrazione();

    String classifica();

    String tipoDocumento();

    boolean escludiDestinatariSenzaMail();

    SceltaTipoMailAnagrafeEnum tipoMailAnagrafe();

    Integer senderAccountId();

    Mailtipo templateMailChiusuraGiornata();

    String descrizioneComunicazioneAperturaPosizioneDebitoriaCreditoInsufficiente();

    Mailtipo templateMailAperturaPosizioneDebitoriaCreditoInsufficiente();

    boolean strategiaInvioComunicazioniSupportata(StrategiaInvioComunicazioniEnum strategiaDaSupportare);
}
