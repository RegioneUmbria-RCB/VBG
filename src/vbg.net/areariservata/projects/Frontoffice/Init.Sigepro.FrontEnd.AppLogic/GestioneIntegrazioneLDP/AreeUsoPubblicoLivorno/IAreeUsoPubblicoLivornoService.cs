using System;
namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public interface IAreeUsoPubblicoLivornoService
    {
        void AggiornaDatiOccupazione(AggiornaDatiOccupazioneCommand cmd);
        string GetUrlCompilazioneDomanda(int idDomanda);
    }
}
