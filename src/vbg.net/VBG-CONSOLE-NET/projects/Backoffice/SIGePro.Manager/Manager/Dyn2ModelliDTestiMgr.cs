
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Utils;
using Init.SIGePro.Manager.DTO.DatiDinamici;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class Dyn2ModelliDTestiMgr : IDyn2TestoModelloManager
    {
		#region IDyn2TestoModelloManager Members
		/*
		IDyn2TestoModello IDyn2TestoModelloManager.GetById(string idComune, int idTesto)
		{
			return GetById(idComune, idTesto);
		}
		*/
		public List<Dyn2TestoModello> GetList(string idComune, int idModello)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = PreparaQueryParametrica(@"SELECT 
														  
dyn2_modellidtesti.idcomune,
dyn2_modellidtesti.id,
dyn2_modellidtesti.fk_d2btt_id,
dyn2_modellidtesti.testo,
dyn2_modellid.id as idNelModello
														FROM 
														  dyn2_modellid,
														  dyn2_modellidtesti
														WHERE
														  dyn2_modellidtesti.idcomune = dyn2_modellid.idcomune AND                            
														  dyn2_modellidtesti.id = dyn2_modellid.fk_d2mdt_id AND                  
														  dyn2_modellid.idcomune = {0} and               
														  dyn2_modellid.fk_d2mt_id = {1}",
													"idComune", "idModello");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idModello", idModello));

					var rVal = new List<Dyn2TestoModello>();

					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							var idcomune = dr["idcomune"].ToString();
							var id = dr["id"] == DBNull.Value ? (int?)null : Convert.ToInt32(dr["id"]);
							var idNelModello = dr["idNelModello"] == DBNull.Value ? (int?)null : Convert.ToInt32(dr["idNelModello"]);
							var fk_d2btt_id = dr["fk_d2btt_id"].ToString();
							var testo = dr["testo"].ToString();

							rVal.Add(new Dyn2TestoModello(idcomune, id, idNelModello, fk_d2btt_id, testo));
						}
					}

					return rVal;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public SerializableDictionary<int, IDyn2TestoModello> GetListaTestiDaIdModello(string idComune, int idModello)
		{
			var l = GetList(idComune, idModello);

			var rVal = new SerializableDictionary<int, IDyn2TestoModello>();

			l.ForEach(x => rVal.Add(x.Id.Value, x));

			return rVal;
		}

		#endregion
	}
}
				