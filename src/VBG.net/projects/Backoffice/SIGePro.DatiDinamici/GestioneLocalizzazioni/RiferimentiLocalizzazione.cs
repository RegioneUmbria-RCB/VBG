using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.GestioneLocalizzazioni
{
	public class RiferimentiLocalizzazione
	{
		public static RiferimentiLocalizzazione Vuota()
		{
			return new RiferimentiLocalizzazione
			{
				Codice = String.Empty,
				Descrizione = String.Empty
			};
		}

		public string Codice { get; set; }
		public string Descrizione { get; set; }
	}
}
