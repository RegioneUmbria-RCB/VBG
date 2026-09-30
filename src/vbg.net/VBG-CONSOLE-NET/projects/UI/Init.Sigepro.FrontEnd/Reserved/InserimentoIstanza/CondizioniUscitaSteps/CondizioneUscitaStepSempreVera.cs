using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
	public class CondizioneUscitaStepSempreVera: ICondizioneUscitaStep
	{
		#region ICondizioneUscitaStep Members

		public bool Verificata()
		{
			return true;
		}

		#endregion
	}
}
