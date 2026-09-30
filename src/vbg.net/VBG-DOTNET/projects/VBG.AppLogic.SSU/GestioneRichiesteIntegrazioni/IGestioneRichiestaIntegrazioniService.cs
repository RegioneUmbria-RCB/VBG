namespace VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni
{
    public interface IGestioneRichiestaIntegrazioniService
    {
        void InserisciRichiestaIntegrazioneDaIdentificativoDomanda(string identificativoDomanda, IEnumerable<SsuRichiestaIntegrazione> integrazioniSsu);
        SsuRichiestaIntegrazione GetListaIntegrazioniDaFare(int idDomanda);
        bool DomandaDaIntegrare(int idDomanda);
        void InserisciRichiestaIntegrazione(int idDomanda, SsuRichiestaIntegrazione richiesta);
        void MarcaDomandaComeIntegrata(int idDomanda);
    }
}
