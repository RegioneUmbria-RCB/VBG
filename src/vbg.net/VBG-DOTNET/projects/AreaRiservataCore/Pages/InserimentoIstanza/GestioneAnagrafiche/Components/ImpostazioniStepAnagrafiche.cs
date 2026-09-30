namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche.Components
{
    public class ImpostazioniStepAnagrafiche
    {
        public bool VerificaPecObbligatoria = false;
        public string MessaggioAvvertimentoVerificaPEC = "";
        public bool RendiModificabiliDatiAnagraficheEsistenti = true;
        public bool IgnoraRicercaBackofficePerPersoneFisiche = false;
        public bool PermettiModificaTipoSoggetto = true;
        public bool EmailoPecObbligatori = false;
        public bool TelefonooCellulareObbligatori = false;

        public class ImpostazioniPersonaGiuridica
        {
            public bool TelefonoVisibile = true;
            public bool TelefonoObbligatorio;
            public bool CellulareVisibile = true;
            public bool CellulareObbligatorio;
            public bool FaxVisibile = true;
            public bool FaxObbligatorio;
            public bool CciaaVisibile = true;
            public bool CciaaObbligatoria;
            public bool RegTribVisibile = true;
            public bool RegTribObbligatorio;
            public bool ReaVisibile = true;
            public bool ReaObbligatoria;
            public bool InpsVisibile = true;
            public bool InpsObbligatoria;
            public bool InailVisibile = true;
            public bool InailObbligatoria;
            public bool EmailVisibile = true;
            public bool EmailObbligatoria;
            public bool PecVisibile = true;
            public bool PecObbligatoria;
            public bool PartitaIvaVisibile = true;
            public bool PartitaIvaObbligatoria;
            public bool CorrispondenzaVisibile = true;
            public bool CorrispondenzaObbligatoria;
            public bool SedeLegaleVisibile = true;
            public bool SedeLegaleObbligatoria = false;
            public bool DataCostituzioneVisibile = true;
            public bool DataCostituzioneObbligatoria = false;
        }

        public class ImpostazioniPersonaFisica
        {
            public bool TitoloObbligatorio;
            public bool ResidenzaVisible = true;
            public bool ResidenzaObbligatoria = false;
            public bool TelefonoVisible = true;
            public bool TelefonoObbligatorio;
            public bool CellulareVisible = true;
            public bool CellulareObbligatorio;
            public bool FaxObbligatorio;
            public bool EmailVisible = true;
            public bool EmailObbligatoria;
            public bool PecVisible = true;
            public bool PecObbligatoria;
            public bool CorrispondenzaVisibile = true;
            public bool CorrispondenzaObbligatoria = false;
            public string TitoloBloccoIndirizzoCorrispondenza = "Indirizzo per la corrispondenza";
            public bool CittadinanzaVisible = true;
            public bool CittadinanzaObbligatoria;
            public bool TitoloVisibile = true;
            public bool FaxVisible = true;


            internal string LimitaDatiAlbo = "";
        }

        public ImpostazioniPersonaGiuridica DettagliPg { get; } = new();
        public ImpostazioniPersonaFisica DettagliPf { get; } = new();
    }
}
