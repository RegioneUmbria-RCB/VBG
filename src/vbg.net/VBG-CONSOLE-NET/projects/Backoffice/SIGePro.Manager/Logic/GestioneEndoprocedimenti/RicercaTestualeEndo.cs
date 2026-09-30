using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Authentication;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{
	public class RisultatoRicercaTestualeEndo
	{
		public BaseDto<string, string>[] Famiglie { get; set; }
		public BaseDto<string, string>[] Categorie { get; set; }
		public BaseDto<string, string>[] Endoprocedimenti { get; set; }
	}



	public class RicercaTestualeEndo
	{
		AuthenticationInfo _authInfo;
		string _software;
		string _idComune;

		public RicercaTestualeEndo(AuthenticationInfo authInfo, string software )
		{
			this._authInfo = authInfo;
			this._software = software;
			this._idComune = authInfo.IdComune;
		}

		public RisultatoRicercaTestualeEndo TrovaEndo(string partial, InventarioProcedimentiMgr.TipoRicercaEnum tipoRicerca)
		{
			var terminiRicerca = new InventarioProcedimentiMgr.TerminiRicerca(tipoRicerca, partial, "parametro");

			var tskRicercaFamiglie = Task.Factory.StartNew(() =>
			{
				return new InventarioProcedimentiMgr(this._authInfo.CreateDatabase()).RicercaTestualeFamiglie(this._idComune, this._software, terminiRicerca);
			});

			var tskRicercaCategorie = Task.Factory.StartNew(() =>
			{
				return new InventarioProcedimentiMgr(this._authInfo.CreateDatabase()).RicercaTestualeCategorie(this._idComune, this._software, terminiRicerca);
			});

			var tskRicercaEndo = Task.Factory.StartNew(() =>
			{
				return new InventarioProcedimentiMgr(this._authInfo.CreateDatabase()).RicercaTestualeEndo(this._idComune, this._software, terminiRicerca);
			});

			Task.WaitAll(new Task[] { 
				tskRicercaFamiglie,
				tskRicercaEndo,
				tskRicercaCategorie
			});

			return new RisultatoRicercaTestualeEndo
			{
				Famiglie = tskRicercaFamiglie.Result.ToArray(),
				Endoprocedimenti = tskRicercaEndo.Result.ToArray(),
				Categorie = tskRicercaCategorie.Result.ToArray()
			};
		}
	}

	
}
