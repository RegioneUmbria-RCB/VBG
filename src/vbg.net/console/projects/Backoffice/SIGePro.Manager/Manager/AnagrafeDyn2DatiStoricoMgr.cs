
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
using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class AnagrafeDyn2DatiStoricoMgr : IAnagrafeDyn2DatiStoricoManager
    {


		public List<int> LeggiIndiciVersione(string idComune, int codiceAnagrafe, int idModello, int idVersione)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT DISTINCT 
									indice 
								FROM 
									anagrafedyn2dati_storico
								WHERE
									idcomune = {0} AND
									codiceanagrafe = {1} AND
									fk_d2mt_id = {2} AND
									idVersione = {3} order by indice asc";

				sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
											db.Specifics.QueryParameterName("codiceanagrafe"),
											db.Specifics.QueryParameterName("fk_d2mt_id"),
											db.Specifics.QueryParameterName("idVersione"));

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceanagrafe", codiceAnagrafe));
					cmd.Parameters.Add(db.CreateParameter("fk_d2mt_id", idModello));
					cmd.Parameters.Add(db.CreateParameter("idVersione", idVersione));

					List<int> rVal = new List<int>();

					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
							rVal.Add(Convert.ToInt32(dr[0]));
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

		#region IAnagrafeDyn2DatiStoricoManager Members

		public List<IAnagrafeDyn2DatiStorico> GetValoriCampo(string idComune, int idAnagrafe, int idCampo, int indiceCampo, int idVersione)
		{
			AnagrafeDyn2DatiStorico filtro = new AnagrafeDyn2DatiStorico();
			filtro.Idcomune = idComune;
			filtro.Codiceanagrafe = idAnagrafe;
			filtro.FkD2cId = idCampo;
			filtro.Indice = indiceCampo;
			filtro.Idversione = idVersione;
			filtro.OrderBy = "indice_molteplicita asc";

			var lista = GetList(filtro);

			var rVal = new List<IAnagrafeDyn2DatiStorico>();

			lista.ForEach(x => rVal.Add(x));

			return rVal;
		}

		#endregion
	}
}
				