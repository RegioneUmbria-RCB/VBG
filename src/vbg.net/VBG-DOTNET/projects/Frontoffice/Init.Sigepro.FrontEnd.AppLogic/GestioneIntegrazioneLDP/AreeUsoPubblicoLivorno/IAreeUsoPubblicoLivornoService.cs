using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using System;
namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public interface IAreeUsoPubblicoLivornoService: IIntegrazioneSITDaScadenzario
    {
        void AggiornaDatiOccupazione(AggiornaDatiOccupazioneCommand cmd);
        string GetUrlCompilazioneDomanda(int idDomanda);

    }
}
