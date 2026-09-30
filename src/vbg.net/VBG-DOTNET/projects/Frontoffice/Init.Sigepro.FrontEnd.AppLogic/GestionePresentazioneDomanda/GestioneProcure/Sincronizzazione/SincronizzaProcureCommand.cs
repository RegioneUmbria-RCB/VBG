using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure.Sincronizzazione
{
	public class SincronizzaProcureCommand
	{
		public class DatiProcuraDaSincronizzare
		{
			public string CodiceFiscaleSottoscrivente { get; set; }
			public string CodiceFiscaleProcuratore { get; set; }
		}

		public IEnumerable<DatiProcuraDaSincronizzare> ProcureDaSincronizzare { get; private set; }

		internal SincronizzaProcureCommand(IEnumerable<DatiProcuraDaSincronizzare> procureDaSincronizzare)
		{
			this.ProcureDaSincronizzare = procureDaSincronizzare;
		}

		internal SincronizzaProcureCommand(string codiceFiscaleSottoscrivente, string codiceFiscaleProcuratore)
		{
			var datiProcuraDaSincronizzare = new DatiProcuraDaSincronizzare
			{
				CodiceFiscaleProcuratore = codiceFiscaleProcuratore,
				CodiceFiscaleSottoscrivente = codiceFiscaleSottoscrivente
			};

			this.ProcureDaSincronizzare = new DatiProcuraDaSincronizzare[] { datiProcuraDaSincronizzare };

		}
	}
}
