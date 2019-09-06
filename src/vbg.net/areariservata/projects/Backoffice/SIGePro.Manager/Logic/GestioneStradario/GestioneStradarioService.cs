using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO.StradarioComune;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{
	public class GestioneStradarioService
	{
		DataBase	_database;
		string		_idComune;

		public GestioneStradarioService (DataBase database, string idComune)
		{
			this._database = database;
			this._idComune = idComune;
		}

		public IEnumerable<StradarioDto> FindByMatchParziale(string codiceComune, string comuneLocalizzazione, string indirizzo)
		{
			var queryRicerca = new CompositeQueryRicercaStradario(new IQueryRicercaStradario[]{ 
					new QueryRicercaStradario( "AND" , indirizzo ),
					new QueryRicercaStradario( "OR" , indirizzo )
				});

			return new RicercaStradarioStrategy(this._database, this._idComune, codiceComune, comuneLocalizzazione).FindByPartialMatch(queryRicerca);
		}
	}
}
