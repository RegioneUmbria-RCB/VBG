using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
    public interface IAltriDatiWriteInterface
    {
        void ImpostaIntervento(int idIntervento, int? idAttivitaAtecoSelezionata = null, IWorkflowService workflowService = null, IResolveDescrizioneIntervento resolveDescrizioneIntervento = null, bool popolaDescrizioneLavoriDaIntervento = true);
        void ImpostaDomicilioElettronico(string indirizzo);
        void ImpostaFlagPrivacy(bool valore);
        void ImpostaCodiceComune(string codiceComune);
        void ImpostaDescrizione(string note, string oggetto, string denominazioneAttivita);
        void ImpostaNaturaBase(string naturaBase);
        void ImpostaNaturaEndoPrincipale(string naturaEndoPrincipale);
        void ImpostaIdDomandaCollegata(int idDomandaCollegata);
        void RimuoviIdDomandaCollegata();
    }
}
