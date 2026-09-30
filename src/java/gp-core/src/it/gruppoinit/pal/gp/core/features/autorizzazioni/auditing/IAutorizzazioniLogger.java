package it.gruppoinit.pal.gp.core.features.autorizzazioni.auditing;

public interface IAutorizzazioniLogger {

    void log();

    void logFineMetodo();

    void logError(String messaggio);

    public void logError(String messaggio, Throwable e);
}
