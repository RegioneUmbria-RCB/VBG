using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class SalvataggioModelloDinamicoException: Exception
	{
		public List<ErroreValidazione> Errori { get; private set; }

		public SalvataggioModelloDinamicoException(IEnumerable<ErroreValidazione> errori): base()
		{
			this.Errori = errori.ToList();
		}
	}
}
