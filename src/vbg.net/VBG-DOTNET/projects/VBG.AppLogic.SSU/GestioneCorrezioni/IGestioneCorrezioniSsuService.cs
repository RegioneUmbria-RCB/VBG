namespace VBG.AppLogic.SSU.GestioneCorrezioni
{
    public interface IGestioneCorrezioniSsuService
    {
        bool DomandaDaCorreggere(int idDomanda);
        IEnumerable<CorrezioneDomandaSsu> GetListaCorrezioniDaFare(int idDomanda);
        void InserisciCorrezioniDaIdentificativoDomanda(string identificativoDomanda, IEnumerable<SsuProcedimentoCorrezione> correzioniSsu);
    }
}