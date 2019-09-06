using System;
namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PraticheEdilizieSiena
{
    public interface IPraticheEdilizieSienaService
    {
        void AggiornaDatiLocalizzazione(int idDomanda);
        void AllegaPdfADomanda(int idDomanda, string nomeFilePdf);
        string GetUrlCompilazioneDomanda(string token, int idDomanda);
    }
}
