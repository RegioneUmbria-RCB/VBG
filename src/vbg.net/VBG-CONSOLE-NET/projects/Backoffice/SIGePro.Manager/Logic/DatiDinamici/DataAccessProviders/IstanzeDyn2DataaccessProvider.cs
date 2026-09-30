using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders
{
	public class IstanzeDyn2DataAccessProvider : Dyn2DataAccessProvider
	{
		int _codiceIstanza;
		string _idComune;

		public IstanzeDyn2DataAccessProvider(DataBase db, int codiceIstanza, string idComune )
			: base(db)
		{
			this._codiceIstanza = codiceIstanza;
			this._idComune = idComune;
		}

		public override IQueryLocalizzazioni GetQueryLocalizzazioni()
		{
			return new QueryLocalizzazioni(this.Database, this._codiceIstanza, this._idComune);
		}
	}
}
