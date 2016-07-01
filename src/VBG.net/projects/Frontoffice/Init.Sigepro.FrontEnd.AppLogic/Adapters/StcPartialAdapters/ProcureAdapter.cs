using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class ProcureAdapter : IStcPartialAdapter
	{
		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			_dettaglioPratica.procure = _readInterface.Procure
														.Procure
														.Where(x => x.Procuratore != null)
														.Select(procura => new ProcuraType
														{
															cfProcuratore = procura.Procuratore.CodiceFiscale,
															cfRappresentato = procura.Procurato.CodiceFiscale,
															procura = procura.Allegato == null ? null : procura.Allegato.ToDocumentiType()
														})
														.ToArray();
		}
	}
}
