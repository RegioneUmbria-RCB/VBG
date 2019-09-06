using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriWorkflow : IParametriConfigurazione
	{
		public readonly string PaginaIniziale;
		public readonly IWorkflowDomandaOnline DefaultWorkflow;
		public readonly bool VerificaHashFilesFirmati;
		public readonly int? IdCampoDinamicoPerAttivitaAtecoPrevalente;
		public readonly bool ImpostaAutomaticamenteAnagraficaUtenteCorrente;

		internal ParametriWorkflow(string paginaIniziale, StepCollectionType workflowSteps, bool verificaHashFilesFirmati, int? idCampoDinamicoPerAttivitaAtecoPrevalente, bool impostaAutomaticamenteAnagraficaUtenteCorrente)
		{
			Condition.Requires(paginaIniziale, "paginaIniziale").IsNotNullOrEmpty();
			Condition.Requires(workflowSteps, "workflowSteps").IsNotNull();

			this.PaginaIniziale = paginaIniziale;
			this.DefaultWorkflow = new WorkflowDomandaOnline(workflowSteps);
			this.VerificaHashFilesFirmati = verificaHashFilesFirmati;
			this.IdCampoDinamicoPerAttivitaAtecoPrevalente = idCampoDinamicoPerAttivitaAtecoPrevalente;
			this.ImpostaAutomaticamenteAnagraficaUtenteCorrente = impostaAutomaticamenteAnagraficaUtenteCorrente;
		}
	}
}
