using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps
{
	public class CondizioneIngressoStepSempreVera : ICondizioneIngressoStep
	{
		#region ICondizioneIngressoStep Members

		public bool Verificata()
		{
			return true;
		}

		#endregion
	}
}