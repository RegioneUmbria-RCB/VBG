using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure
{
	public interface IProcureReadInterface
	{
		IEnumerable<ProcuraDomandaOnline> Procure { get; }
		bool IsUtenteProcuratore(string codiceFiscaleUtente);
		string GetCodiceFiscaleDelProcuratoreDi(string codiceFiscaleProcurato);
	}
}
