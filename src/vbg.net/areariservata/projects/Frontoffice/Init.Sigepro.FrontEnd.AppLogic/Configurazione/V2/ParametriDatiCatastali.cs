using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriDatiCatastali : IParametriConfigurazione
	{
		public readonly bool MostraDatiCatastaliEstesi;

		internal ParametriDatiCatastali(bool mostraDatiCatastaliEstesi)
		{
			this.MostraDatiCatastaliEstesi = mostraDatiCatastaliEstesi;
		}
	}
}
