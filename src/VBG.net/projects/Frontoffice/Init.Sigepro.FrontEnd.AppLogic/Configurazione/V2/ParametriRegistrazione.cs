using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriRegistrazione : IParametriConfigurazione
	{
		public readonly string MessaggioRegistrazioneCompletata;

		internal ParametriRegistrazione(string messaggioRegistrazione)
		{
			this.MessaggioRegistrazioneCompletata = messaggioRegistrazione;
		}
	}
}
