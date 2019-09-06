using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.ValidazioneFormale
{
	internal class ValidazioneFormaleTramiteFoglioParticellaSubService: IValidazioneFormaleService
	{
		#region IValidazioneFormaleService Members

		public bool Valida(Init.SIGePro.Sit.Data.Sit sit)
		{
			return !String.IsNullOrEmpty(sit.Foglio) &&
					!String.IsNullOrEmpty(sit.Particella) &&
					!String.IsNullOrEmpty(sit.Sub);
		}

		#endregion
	}
}
