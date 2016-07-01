using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
	public interface IAltriDatiWriteInterface
	{
		void ImpostaIntervento(int idIntervento, string descrizioneEstesaIntervento, int? idAttivitaAtecoSelezionata, IWorkflowService workflowService);
		void ImpostaDomicilioElettronico(string indirizzo);
		void ImpostaFlagPrivacy(bool valore);
		void ImpostaCodiceComune(string codiceComune);
		void ImpostaDescrizione(string note, string oggetto, string denominazioneAttivita);
	}
}
