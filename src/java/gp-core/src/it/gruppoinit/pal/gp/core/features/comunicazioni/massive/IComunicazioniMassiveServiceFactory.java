package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

public interface IComunicazioniMassiveServiceFactory {

    @SuppressWarnings("rawtypes")
    IComunicazioniMassiveService getService(Integer idComunicazione);
}
