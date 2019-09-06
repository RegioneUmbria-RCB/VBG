using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Ricerche
{
	public interface IRicercheDatiDinamiciService
	{
		RisultatoRicercaDatiDinamici InitializeControl(int idCampo, string value);
		RisultatoRicercaDatiDinamici[] GetCompletionList(int idCampo, string partial, ValoreFiltroRicerca[] filtri);
	}

	public class RicercheDatiDinamiciService : IRicercheDatiDinamiciService
	{
		IDatiDinamiciRepository _repository;

		public RicercheDatiDinamiciService(IDatiDinamiciRepository repository)
		{
			this._repository = repository;
		}

		#region IRicercheDatiDinamiciService Members

		public RisultatoRicercaDatiDinamici InitializeControl(int idCampo, string value)
		{
			return this._repository.InitializeControl(idCampo, value);
		}

		public RisultatoRicercaDatiDinamici[] GetCompletionList(int idCampo, string partial, ValoreFiltroRicerca[] filtri)
		{
			return this._repository.GetCompletionList(idCampo, partial, filtri);
		}

		#endregion
	}
}
