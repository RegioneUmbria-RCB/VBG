package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

public interface IWorkflowComunicazioniService<TConfig, TEnum extends Enum<?>> {

    void elabora(int idDettaglioComunicazione, TConfig configurazione);

    TEnum getStatoConclusivo();
}
