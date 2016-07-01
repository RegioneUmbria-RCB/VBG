using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
	public class AltriDatiWriteInterface : IAltriDatiWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public AltriDatiWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region IAltriDatiWriteInterface Members

		public void ImpostaIntervento(int idIntervento, string descrizioneEstesaIntervento, int? idAttivitaAtecoSelezionata, PresentazioneIstanze.Workflow.IWorkflowService workflowService)
		{
			VerificaEsistenzaRiga();

			if (GetCodiceInterventoCorrente() == idIntervento)
				return;

			var dataRow = this._database.ISTANZE[0];

			// Imposto il nuovo id e descrizione intervento
			dataRow.CODICEINTERVENTO			= idIntervento;
			dataRow.DescrizioneEstesaIntervento = descrizioneEstesaIntervento;
			dataRow.OGGETTO						= descrizioneEstesaIntervento;

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
			VerificaEsistenzaRiga();

			this._database.ISTANZE[0].IndirizzoDomicilioEletronico = indirizzo;
		}

		public void ImpostaFlagPrivacy(bool valore)
		{
			VerificaEsistenzaRiga();

			this._database.ISTANZE[0].FlgPrivacy = valore;
		}

		public void ImpostaCodiceComune(string codiceComune)
		{
			VerificaEsistenzaRiga();

			this._database.ISTANZE[0].CODICECOMUNE = codiceComune;
		}


		public void ImpostaDescrizione(string note, string oggetto, string denominazioneAttivita)
		{
			VerificaEsistenzaRiga();

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


	}
}
