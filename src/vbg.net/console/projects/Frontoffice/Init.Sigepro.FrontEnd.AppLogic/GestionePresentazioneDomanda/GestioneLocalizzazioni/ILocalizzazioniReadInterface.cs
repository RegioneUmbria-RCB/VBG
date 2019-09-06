using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
	public interface ILocalizzazioniReadInterface
	{
		IEnumerable<IndirizzoStradario> Indirizzi { get; }
		bool ContieneRiferimentiCatastali { get; }
	}
}
