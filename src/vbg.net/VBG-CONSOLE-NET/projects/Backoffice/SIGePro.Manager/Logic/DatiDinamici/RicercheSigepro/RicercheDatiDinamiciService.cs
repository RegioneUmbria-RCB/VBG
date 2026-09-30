using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro
{
	public class RicercheDatiDinamiciService
	{
		public class RisultatoRicercaDatiDinamici
		{
			public string Value { get; set; }
			public string Label { get; set; }
		}

		string _token;

		public RicercheDatiDinamiciService(string token)
		{
			this._token = token;
		}

		private AuthenticationInfo CheckToken()
		{
			var authInfo = AuthenticationManager.CheckToken(this._token);

			if (authInfo == null)
				throw new InvalidTokenException(this._token);

			return authInfo;
		}

		public RisultatoRicercaDatiDinamici InitializeControl(int idCampo, string valore)
		{
			if(String.IsNullOrEmpty( valore ))
				return new RisultatoRicercaDatiDinamici { Value = "", Label = "" };

			var authInfo = CheckToken();

			using (var db = authInfo.CreateDatabase())
			{
				var proprietaReader = new ProprietaCampoRicercaReader(authInfo.IdComune, idCampo, new Dyn2CampiProprietaMgr(db));
				var ricercheService = new RicercheSigeproHelper(authInfo, proprietaReader);

				var result = ricercheService
								.ExecuteQuery(valore, false, Enumerable.Empty<ValoreFiltroRicerca>(), true)
								.Select(x => new RisultatoRicercaDatiDinamici { Value = x.Key, Label = x.Value })
								.FirstOrDefault();

				if (result != null)
					return result;

				return new RisultatoRicercaDatiDinamici { Value = valore, Label = "ERRORE:Impossibile inizializzare il campo" };
			}
		}

        public IEnumerable<RisultatoRicercaDatiDinamici> GetCompletionList(int idCampo, string partial, List<ValoreFiltroRicerca> filtri)
		{
			if (String.IsNullOrEmpty(partial))
				return new List<RisultatoRicercaDatiDinamici> { new RisultatoRicercaDatiDinamici { Value = "", Label = "" } };

			var authInfo = CheckToken();

			using (var db = authInfo.CreateDatabase())
			{
				var proprietaReader = new ProprietaCampoRicercaReader(authInfo.IdComune, idCampo, new Dyn2CampiProprietaMgr(db));
				var ricercheService = new RicercheSigeproHelper(authInfo, proprietaReader);

				return ricercheService
						.ExecuteQuery(partial, true, filtri)
						.Select(x => new RisultatoRicercaDatiDinamici { Value = x.Key, Label = x.Value });
			}

		}
	}
}
