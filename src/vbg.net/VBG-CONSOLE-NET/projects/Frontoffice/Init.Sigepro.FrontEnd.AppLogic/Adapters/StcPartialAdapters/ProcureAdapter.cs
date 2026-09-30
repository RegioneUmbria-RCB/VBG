using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class ProcureAdapter : IStcPartialAdapter
	{
        private readonly IAliasResolver _aliasResolver;

        public ProcureAdapter(IAliasResolver aliasResolver)
        {
            _aliasResolver = aliasResolver;
        }


		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			_dettaglioPratica.procure = _readInterface.Procure
														.Procure
														.Where(x => x.Procuratore != null)
														.Select(procura => new ProcuraType
														{
															cfProcuratore = procura.Procuratore.CodiceFiscale,
															cfRappresentato = procura.Procurato.CodiceFiscale,
															procura = procura.Allegato == null ? null : procura.Allegato.ToDocumentiType(this._aliasResolver),
                                                            documentoIdentita = procura.DocumentoIdentita == null ? null : procura.DocumentoIdentita.ToDocumentiType(this._aliasResolver)
														})
														.ToArray();
		}
	}
}
