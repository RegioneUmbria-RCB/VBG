package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

public interface ICreazioneMassiveGDettaglioService {
 //BISOGNA IMPLEMENTARE CASO PER CASO
    
    public void collegaRigheMercatiAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione);

    void collegaRigheIstanzeAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione);
}
