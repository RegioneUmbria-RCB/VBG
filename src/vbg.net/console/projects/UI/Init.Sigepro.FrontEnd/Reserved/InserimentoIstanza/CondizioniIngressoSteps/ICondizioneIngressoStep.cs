using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps
{
	public interface ICondizioneIngressoStep
	{
		bool Verificata();
	}
}
