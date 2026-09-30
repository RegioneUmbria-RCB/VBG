namespace Init.SIGePro.Manager.Logic.GestioneRisorseTestuali
{
    public interface ILayoutTestiAuditService
    {
        void LogTestoCreato(string software, string codiceTesto, string nuovoTesto);
        void LogTestoEliminato(string software, string codiceTesto);
        void LogTestoModificato(string software, string codiceTesto, string nuovoTesto);
    }
}