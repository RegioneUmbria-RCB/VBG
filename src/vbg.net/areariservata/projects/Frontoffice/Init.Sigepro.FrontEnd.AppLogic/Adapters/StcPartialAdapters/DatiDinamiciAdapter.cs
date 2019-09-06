using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{


	internal class DatiDinamiciAdapter : IStcPartialAdapter
	{
		IStrutturaModelloReader _modelloReader;

		internal DatiDinamiciAdapter(IStrutturaModelloReader modelloReader)
		{
			this._modelloReader = modelloReader;
		}


		public void Adapt(IDomandaOnlineReadInterface readInterface, DettaglioPraticaType dettaglioPratica)
		{
			var schede = readInterface.DatiDinamici.Modelli.Select( x => this._modelloReader.Read( x.IdModello ) );

			dettaglioPratica.schede = schede.Select( x => new SchedeStcAdapter( x , readInterface.DatiDinamici ).CreaSchedaStc()).ToArray();
		}

		
	}
}
