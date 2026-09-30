using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
    public class AltriDatiWriteInterface : IAltriDatiWriteInterface
    {
        private readonly PresentazioneIstanzaDbV2 _database;

        public AltriDatiWriteInterface(PresentazioneIstanzaDbV2 database)
        {
            this._database = database;
        }

        #region IAltriDatiWriteInterface Members

        public void ImpostaIntervento(int idIntervento, int? idAttivitaAtecoSelezionata = null, IWorkflowService workflowService = null, IResolveDescrizioneIntervento resolveDescrizioneIntervento = null, bool popolaDescrizioneLavoriDaIntervento = true)
        {
            this.VerificaEsistenzaRiga();

            if (this.GetCodiceInterventoCorrente() == idIntervento)
                return;

            var descrizioneIntervento = "";

            if (resolveDescrizioneIntervento != null)
            {
                descrizioneIntervento = resolveDescrizioneIntervento.GetDescrizioneDaCodiceintervento(idIntervento);
            }

            var dataRow = this._database.ISTANZE[0];

            // Imposto il nuovo id e descrizione intervento
            dataRow.CODICEINTERVENTO = idIntervento;
            dataRow.DescrizioneEstesaIntervento = descrizioneIntervento;

            if (popolaDescrizioneLavoriDaIntervento)
            {
                dataRow.OGGETTO = descrizioneIntervento;
            }

            // Reimposto l'id dell'attività ateco 
            this._database.AttivitaAteco.Clear();

            if (idAttivitaAtecoSelezionata.HasValue)
                this._database.AttivitaAteco.AddAttivitaAtecoRow(idAttivitaAtecoSelezionata.Value, true);

            // Devo resettare il workflow in quanto in seguito alla selezione dell'intervento il wf
            // potrebbe prendere una strada diversa
            if (workflowService != null)
                workflowService.ResetWorkflow();
        }

        private int GetCodiceInterventoCorrente()
        {
            if (this._database.ISTANZE.Count == 0)
                return int.MinValue;

            if (this._database.ISTANZE[0].IsCODICEINTERVENTONull())
                return int.MinValue;

            return this._database.ISTANZE[0].CODICEINTERVENTO;
        }

        public void ImpostaDomicilioElettronico(string indirizzo)
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0].IndirizzoDomicilioEletronico = indirizzo;
        }

        public void ImpostaFlagPrivacy(bool valore)
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0].FlgPrivacy = valore;
        }

        public void ImpostaCodiceComune(string codiceComune)
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0].CODICECOMUNE = codiceComune;
        }


        public void ImpostaDescrizione(string note, string oggetto, string denominazioneAttivita)
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0].NOTE = note;
            this._database.ISTANZE[0].OGGETTO = oggetto;
            this._database.ISTANZE[0].DENOMINAZIONE_ATTIVITA = denominazioneAttivita;
        }

        #endregion

        private void VerificaEsistenzaRiga()
        {
            if (this._database.ISTANZE.Count == 0)
                this._database.ISTANZE.AddISTANZERow(this._database.ISTANZE.NewISTANZERow());
        }

        public void ImpostaNaturaBase(string naturaBase)
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0].NaturaBase = naturaBase;
        }

        public void ImpostaNaturaEndoPrincipale(string naturaEndoPrincipale)
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0].NaturaEndoPrincipale = naturaEndoPrincipale;
        }

        public void ImpostaIdDomandaCollegata(int idDomandaCollegata)
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0].IdDomandaCollegata = idDomandaCollegata;
        }

        public void RimuoviIdDomandaCollegata()
        {
            this.VerificaEsistenzaRiga();

            this._database.ISTANZE[0]["IdDomandaCollegata"] = DBNull.Value;
        }
    }
}
