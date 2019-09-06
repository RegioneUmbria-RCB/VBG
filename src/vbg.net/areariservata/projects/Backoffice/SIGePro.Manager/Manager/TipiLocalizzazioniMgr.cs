using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Manager
{
	public class TipiLocalizzazioniMgr : BaseManager
	{
		string _idComune;

		public TipiLocalizzazioniMgr(DataBase db, string idComune):base(db)
		{
			this._idComune = idComune;
		}

		public TipiLocalizzazioni GetById(int id)
		{
			return (TipiLocalizzazioni)base.db.GetClass(new TipiLocalizzazioni
			{
				IdComune = this._idComune,
				Id = id
			});
		}
	}
}
