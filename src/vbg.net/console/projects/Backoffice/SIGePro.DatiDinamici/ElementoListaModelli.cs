using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici
{
	public class ElementoListaModelli
	{
		public int Id{get;protected set;}
		public string Descrizione{get;protected set;}

		public ElementoListaModelli(int id, string descrizione)
		{
			Id = id; Descrizione = descrizione;
		}
	}
}
