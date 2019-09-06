
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
using Init.SIGePro.DatiDinamici.Scripts;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class Dyn2CampiScriptMgr : IDyn2ScriptCampiManager
    {
		public IDyn2ScriptCampo GetById(string idComune, int idCampo, TipoScriptEnum tipoContesto)
		{
			return GetById(idComune, idCampo, tipoContesto.ToString());
		}

		public List<Dyn2CampiScript> GetList(string idComune, int idCampo)
		{
			var filtro = new Dyn2CampiScript
			{
				Idcomune = idComune,
				FkD2cId = idCampo
			};

			return GetList(filtro);
		}

		public List<Dyn2CampiScript> GetListDaIdModello(string idComune, int idModello)
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
														  dyn2_campi_script.* 
														FROM 
														  dyn2_campi_script,
														  dyn2_modellid
														WHERE 
														  dyn2_campi_script.idComune = dyn2_modellid.idComune AND
														  dyn2_campi_script.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
														  dyn2_modellid.idComune = {0} AND
														  dyn2_modellid.fk_d2mt_id = {1}", "idComune", "idModello");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idModello", idModello));

					return db.GetClassList<Dyn2CampiScript>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}




			
		}

		#region IDyn2ScriptCampiManager Members

		public Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(string idComune, int idCampo)
		{
			var l = GetList(idComune, idCampo);

			var rVal = new Dictionary<TipoScriptEnum, IDyn2ScriptCampo>( l.Count);

			l.ForEach(x => { rVal.Add((TipoScriptEnum)Enum.Parse(typeof(TipoScriptEnum), x.Evento), x); });

			return rVal;
		}

		#endregion
	}
}
				