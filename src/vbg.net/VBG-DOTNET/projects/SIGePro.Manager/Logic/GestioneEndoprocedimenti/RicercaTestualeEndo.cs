using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Authentication;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{
	public class RisultatoRicercaTestualeEndo
	{
		public FamigliaEndoFrontoffice[] Famiglie { get; set; }
		public CategoriaEndoFrontoffice[] Categorie { get; set; }
		public EndoBreveFrontoffice[] Endoprocedimenti { get; set; }
	}

	public class RicercaTestualeEndo
	{
		private readonly AuthenticationInfo _authInfo;
		private readonly string _software;
		private readonly string _idComune;

		public RicercaTestualeEndo(AuthenticationInfo authInfo, string software )
		{
			this._authInfo = authInfo;
			this._software = software;
			this._idComune = authInfo.IdComune;
		}

		public RisultatoRicercaTestualeEndo TrovaEndo(string partial, TipoRicercaEnum tipoRicerca)
		{
			var terminiRicerca = new TerminiRicerca(tipoRicerca, partial, "parametro");

			var tskRicercaFamiglie = Task.Factory.StartNew(() =>
			{
				using (var db = this._authInfo.CreateDatabase())
				{
					return new RicercaTestualeEndoService(db, this._idComune).RicercaTestualeFamiglie(this._software, terminiRicerca);
				}
			});

			var tskRicercaCategorie = Task.Factory.StartNew(() =>
			{
				using (var db = this._authInfo.CreateDatabase())
				{
					return new RicercaTestualeEndoService(db, this._idComune).RicercaTestualeCategorie(this._software, terminiRicerca);
				}
			});

			var tskRicercaEndo = Task.Factory.StartNew(() =>
			{
				using (var db = this._authInfo.CreateDatabase())
				{
					return new RicercaTestualeEndoService(db, this._idComune).RicercaTestualeEndo(this._software, terminiRicerca);
				}
			});

			Task.WaitAll(new Task[] { 
				tskRicercaFamiglie,
				tskRicercaEndo,
				tskRicercaCategorie
			});

			return new RisultatoRicercaTestualeEndo
			{
				Famiglie = tskRicercaFamiglie.Result
											 .Select( x => new FamigliaEndoFrontoffice
											 { 
												 Codice = x.Codice,
												 Descrizione = x.Descrizione
											 })
											 .ToArray(),

				Endoprocedimenti = tskRicercaEndo.Result
												.Select(x => new EndoBreveFrontoffice
												{
													Codice = x.Codice,
													Descrizione = x.Descrizione
												})
												.ToArray(),

				Categorie = tskRicercaCategorie.Result
												.Select(x => new CategoriaEndoFrontoffice
												{
													Codice = x.Codice,
													Descrizione = x.Descrizione
												})
												.ToArray(),
			};
		}
	}

	
}
