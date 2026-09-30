package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

public interface IAbbonamentoLogger {

    void log();

    void logFineMetodo();

    void logError(String messaggio);
}
