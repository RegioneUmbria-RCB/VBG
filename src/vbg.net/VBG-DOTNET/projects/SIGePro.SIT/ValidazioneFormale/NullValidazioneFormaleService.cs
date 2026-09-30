using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.ValidazioneFormale
{
	internal class NullValidazioneFormaleService: IValidazioneFormaleService
	{
		#region IValidazioneFormaleService Members

		public bool Valida(Init.SIGePro.Sit.Data.Sit sit)
		{
			return true;
		}

		#endregion
	}
}
