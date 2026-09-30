package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.List;

public interface IGeneratoreEventiDatiDinamiciService {

    /**
     * Il metodo genwera l'evento della scheda collegata a fronte di una scheda collegata all'attività
     */
    public void generaEventoSchedaAttivitaAggiunta(Integer idAttivita, Integer idSchedaDinamica);

    /**
     * Il metodo genwera l'evento della scheda salvata a fronte di una scheda collegata all'attività
     */
    public void generaEventoSchedaAttivitaSalvata(Integer idAttivita, Integer idSchedaDinamica);

    /**
     * Il metodo genwera l'evento della scheda eliminata a fronte di una scheda collegata all'attività
     */
    public void generaEventoSchedaDinamicaAttivitaEliminata(Integer idAttivita, Integer idSchedaDinamica, List<Integer> idCampiDinamiciDaEliminare);

    /**
     * Il metodo genwera l'evento della scheda salvata a fronte di una scheda collegata ad una istanza dell'attività
     */
    public void generaEventoSchedaDinamicaIstanzaSalvata(Integer idIstanza, Integer idSchedaDinamica);

    /**
     * Il metodo genwera l'evento della scheda eliminata a fronte di una scheda collegata ad una istanza dell'attività
     */
    public void generaEventoSchedaDinamicaIstanzaEliminata(Integer idIstanza, Integer idSchedaDinamica);
}
