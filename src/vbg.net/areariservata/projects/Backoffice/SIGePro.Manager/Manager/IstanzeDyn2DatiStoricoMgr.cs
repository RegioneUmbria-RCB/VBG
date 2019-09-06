
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
using PersonalLib2.Data;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class IstanzeDyn2DatiStoricoMgr : IIstanzeDyn2DatiStoricoManager
    {
		internal List<IstanzeDyn2DatiStorico> GetValoriNoIndiceNoVersione(string idComune, int idIstanza, int idCampo)
		{
			IstanzeDyn2DatiStorico filtro = new IstanzeDyn2DatiStorico();
			filtro.Idcomune = idComune;
			filtro.Codiceistanza = idIstanza;
			filtro.FkD2cId = idCampo;

			return GetList(filtro);
		}

		public List<int> LeggiIndiciVersione(string idComune, int codiceIstanza, int idModello, int idVersione)
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
									istanzedyn2dati_storico
								WHERE
									idcomune = {0} AND
									codiceistanza = {1} AND
									fk_d2mt_id = {2} AND
									idVersione = {3} order by indice asc";

				sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
											db.Specifics.QueryParameterName("codiceistanza"),
											db.Specifics.QueryParameterName("fk_d2mt_id"),
											db.Specifics.QueryParameterName("idVersione"));

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceistanza", codiceIstanza));
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

		#region IIstanzeDyn2DatiStoricoManager Members

		public List<IIstanzeDyn2DatiStorico> GetValoriCampo(string idComune, int codiceIstanza, int codiceCampo, int indiceModello, int idVersioneStorico)
		{
			var filtro = new IstanzeDyn2DatiStorico{
				Idcomune = idComune,
				Codiceistanza = codiceIstanza,
				FkD2cId = codiceCampo,
				Indice = indiceModello,
				Idversione = idVersioneStorico,
				OrderBy = "indice_molteplicita asc"
			};

			var list = GetList(filtro);

			var rVal = new List<IIstanzeDyn2DatiStorico>(list.Count);

			list.ForEach(x => rVal.Add(x));

			return rVal;
		}

		#endregion
	}
}
				