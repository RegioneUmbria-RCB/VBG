package it.gruppoinit.pal.gp.core.features.sistema;

public interface IVerticalizzazioneParametriSistemaService {

    static final String NOME_VERTICALIZZAZIONE = "PARAMETRI_SISTEMA";
    static final String PAR_ATTIVA_AUDIT_WEB = "ATTIVA_AUDIT_WEB";
    static final String PAR_ATTIVA_COMPORTAMENTI_SICUREZZA = "ATTIVA_COMPORTAMENTI_SICUREZZA";
    static final String PAR_NASCONDI_SCRIPT_LOCATION = "NASCONDI_SCRIPT_LOCATION";
    static final String PAR_OVERRIDE_URL_GENERA_ALLEGATO = "OVERRIDE_URL_GENERA_ALLEGATO";
    static final String PAR_PEC_CLIENT_CALL_TIMEOUT = "PEC_CLIENT_CALL_TIMEOUT";
    static final String PAR_VERIFICA_FIRMA_OGG_INSERITI = "VERIFICA_FIRMA_OGG_INSERITI";

    boolean isAttiva();

    boolean attivaAuditWeb();

    boolean attivaComportamentiSicurezza();

    boolean nascondiScriptLocation();

    String overrideUrlGeneraAllegato();

    Long pecClientCallTimeout();

    boolean verificaFirmaOggettiInseriti();
}
