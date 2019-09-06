using System;
using System.Linq;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per InventarioProcedimentiInCompMgr.\n	/// </summary>
	public class InventarioProcedimentiIncompMgr: BaseManager
	{
		string _idComune;


		public InventarioProcedimentiIncompMgr(DataBase dataBase, string idComune) : base(dataBase) 
		{
			this._idComune = idComune;
		}

		public IEnumerable<InventarioProcedimentiInComp> GetListByListaCodiciEndo(IEnumerable<int> listaEndoprocedimenti)
		{

			bool closeCnn = false;

			try
			{
				var listaIdEndoAttivati = String.Join("','", listaEndoprocedimenti.Select(x => x.ToString()).ToArray());
				var sql = PreparaQueryParametrica("select * from inventarioprocedimentiincomp where idcomune = {0} and codiceinventario = {1} and codiceincompatibile in ('" + listaIdEndoAttivati + "')",
													"idComune", "codiceEndo");


				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var listaIncompatibili = new List<InventarioProcedimentiInComp>();

				using (var cmd = db.CreateCommand(sql))
				{
					var idComuneParameter = db.CreateParameter("idComune", this._idComune);
					var codEndoParameter = db.CreateParameter("codiceEndo", 0);

					cmd.Parameters.Add(idComuneParameter);
					cmd.Parameters.Add(codEndoParameter);

					foreach (var idEndo in listaEndoprocedimenti)
					{
						codEndoParameter.Value = idEndo;

						listaIncompatibili.AddRange(db.GetClassList<InventarioProcedimentiInComp>(cmd));
					}

					return listaIncompatibili;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

	}
}
