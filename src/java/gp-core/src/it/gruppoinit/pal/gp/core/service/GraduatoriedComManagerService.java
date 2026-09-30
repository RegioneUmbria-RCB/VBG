package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazionePerGraduatoriaEnum;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;

public interface GraduatoriedComManagerService {

    /**
     * Metodo generico che elabora la comunicazione passata(Creazione movimento,protocollazione movimento, creazione
     * allegato,invio mail)
     * 
     * @return
     */
    public PassoCreazioneComunicazionePerGraduatoriaEnum elabora(GraduatoriedCom graduatoriedCom,
	    PassoCreazioneComunicazionePerGraduatoriaEnum passoCreazioneComunicazionePerGraduatoriaEnum);

    public void insertComunicazioni(GraduatorietCom entity, SchedaDinamicaFilter dinamicaFilter);

    /**
     * Il metodo elabora nuovamente la comunicazione passata. Verifica il passo a cui l'elaborazione era terminata causa
     * errore e prova ad completare le operazioni mancanti (inserimento movimento,protocollazione movimento,inserimento
     * allegato,invio mail)
     * 
     * @param graduatoriedCom
     */
    public void elaboroGraduatoriedCom(GraduatoriedCom graduatoriedCom);
}
