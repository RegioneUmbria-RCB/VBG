using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CuttingEdge.Conditions;

namespace Init.SIGePro.Sit.ValidazioneFormale
{
	internal class CompositeValidazioneFormaleService : IValidazioneFormaleService
	{
		IEnumerable<IValidazioneFormaleService> _serviziValidazione;

		public CompositeValidazioneFormaleService(IEnumerable<IValidazioneFormaleService> serviziValidazione)
		{
			Condition.Requires(serviziValidazione, "serviziValidazione");

			this._serviziValidazione = serviziValidazione;
		}

		#region IValidazioneFormaleService Members

		public bool Valida(Init.SIGePro.Sit.Data.Sit sit)
		{
			foreach (var servizioValidazione in this._serviziValidazione)
			{
				if (servizioValidazione.Valida(sit))
					return true;
			}

			return false;
		}

		#endregion
	}
}
