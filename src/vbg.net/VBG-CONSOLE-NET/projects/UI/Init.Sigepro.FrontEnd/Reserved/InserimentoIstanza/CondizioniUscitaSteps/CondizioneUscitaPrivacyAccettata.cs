using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
	public class CondizioneUscitaPrivacyAccettata : CondizioneUscitaStepBase, ICondizioneUscitaStep
	{
		public string MessaggioErrore { get; set; }

		protected static class Constants
		{
			public const string ErrorePrivacyNonAccettata = "Per proseguire è necessario leggere ed accettare le condizioni riportate";
		}

		public CondizioneUscitaPrivacyAccettata(IIdDomandaResolver idDomandaResolver,DomandeOnlineService domandeService)
			:base( idDomandaResolver , domandeService )
		{

		}

		#region ICondizioneUscitaStep Members

		public bool Verificata()
		{
			var domanda = base.Domanda;

			if (!domanda.ReadInterface.AltriDati.FlagPrivacy)
			{
				var messaggioErrore = String.IsNullOrEmpty(this.MessaggioErrore) ? Constants.ErrorePrivacyNonAccettata : this.MessaggioErrore;

				throw new StepException(messaggioErrore);
			}

			return true;
		}

		#endregion
	}
}