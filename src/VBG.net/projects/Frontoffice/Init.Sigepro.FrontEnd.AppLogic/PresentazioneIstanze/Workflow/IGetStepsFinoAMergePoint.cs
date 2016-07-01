using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
	internal interface ISearchWorkflowMergePoint
	{
		int GetMergePoint();
	}

	internal class SearchWorkflowMergePointDaWebConfig: ISearchWorkflowMergePoint
	{
		private static class Constants
		{
			public const string NomeControlloStepSelezioneIntervento = "~/Reserved/InserimentoIstanza/GestioneInterventiAteco.aspx";
			public const string AppConfigKey = "DynamicWorkflow.WorkflowMergePoint";
		}

		IWorkflowDomandaOnline _workflow;
		string _nomeControlloStepSelezioneIntervento;

		public SearchWorkflowMergePointDaWebConfig(IWorkflowDomandaOnline workflow)
		{
			this._workflow = workflow;

			// Se il nome dello step di selezione interventi è stato configurato nel web.config allora lo utilizzo per cercare il mergePoint
			this._nomeControlloStepSelezioneIntervento = ConfigurationManager.AppSettings[Constants.AppConfigKey];

			if (String.IsNullOrEmpty(this._nomeControlloStepSelezioneIntervento))
				this._nomeControlloStepSelezioneIntervento = Constants.NomeControlloStepSelezioneIntervento;			
		}

		#region ISearchWorkflowMergePoint Members

		public int GetMergePoint()
		{
			for (int i = 0; i < this._workflow.NumeroSteps(); i++)
			{
				var stepUrl = this._workflow.GetStepUrl( i );

				if (stepUrl.ToUpperInvariant().Equals(this._nomeControlloStepSelezioneIntervento.ToUpperInvariant()))
					return i+1;
			}

			throw new InvalidOperationException("Step di selezione interventi non trovato nel workflow corrente");
		}

		#endregion
	}
}
