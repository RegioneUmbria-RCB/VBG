using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
    public interface IAltriDatiWriteInterface
    {
        void ImpostaIntervento(int idIntervento, string descrizioneIntervento, bool popolaDescrizioneLavoriDaIntervento = true);
        void ImpostaIntervento(int idIntervento, int? idAttivitaAtecoSelezionata = null, IResolveDescrizioneIntervento resolveDescrizioneIntervento = null, bool popolaDescrizioneLavoriDaIntervento = true);
        void ImpostaDomicilioElettronico(string indirizzo);
        void ImpostaFlagPrivacy(bool valore);
        void ImpostaCodiceComune(string codiceComune, string codiceIstatComune);
        void ImpostaDescrizione(string note, string oggetto, string denominazioneAttivita);
        void ImpostaNaturaBase(string naturaBase);
        void ImpostaIdDomandaCollegata(int idDomandaCollegata);
    }
}
