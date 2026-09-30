using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class ParametriHelper
	{
		internal ParametroType CreaParametroType(string nome, string valore)
		{
			return CreaParametroType(nome, valore, valore);
		}

		internal ParametroType CreaParametroType(string nome, string codice, string descrizione)
		{
			return new ParametroType
			{
				nome = nome,
				valore = new ValoreParametroType[]{
					new ValoreParametroType
					{
						codice = codice,
						descrizione = descrizione
					}
				}
			};
		}

	}
}
