using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Ricerche
{
	public interface IRicercheDatiDinamiciService
	{
		RisultatoRicercaDatiDinamici InitializeControl(string token, int idCampo, string value);
		RisultatoRicercaDatiDinamici[] GetCompletionList(string token, int idCampo, string partial, ValoreFiltroRicerca[] filtri);
	}

	public class RicercheDatiDinamiciService : IRicercheDatiDinamiciService
	{
		IDatiDinamiciRepository _repository;

		public RicercheDatiDinamiciService(IDatiDinamiciRepository repository)
		{
			this._repository = repository;
		}

		#region IRicercheDatiDinamiciService Members

		public RisultatoRicercaDatiDinamici InitializeControl(string token, int idCampo, string value)
		{
			return this._repository.InitializeControl(token, idCampo, value);
		}

		public RisultatoRicercaDatiDinamici[] GetCompletionList(string token, int idCampo, string partial, ValoreFiltroRicerca[] filtri)
		{
			return this._repository.GetCompletionList(token, idCampo, partial, filtri);
		}

		#endregion
	}
}
