using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using PersonalLib2.Data;
using System.Data;
using System.Text.RegularExpressions;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders
{
	public class QuerySigepro : IDyn2QueryDatiDinamiciManager
	{
		private DataBase Database { get; set; }

		public QuerySigepro(DataBase db)
		{
			Database = db;
		}

		#region IDyn2QueryDatiDinamiciManager Members

		public DataSet EseguiQuery(string idComune, string campiSelect, string tabelleSelect, string condizioneJoin, string condizioniWhere, string nomeCampoTesto, string nomeCampoValore)
		{

			StringBuilder sb = new StringBuilder();

			sb.Append("select ");
			sb.Append(String.IsNullOrEmpty(campiSelect) ? "*" : campiSelect);
			sb.Append(" from ");
			sb.Append(tabelleSelect);

			if (!String.IsNullOrEmpty(condizioneJoin) || !String.IsNullOrEmpty(condizioniWhere))
			{
				sb.Append(" where 1=1 ");

				if (!String.IsNullOrEmpty(condizioneJoin))
				{
					sb.Append(" and ");
					sb.Append(condizioneJoin);
				}

				if (!String.IsNullOrEmpty(condizioniWhere))
				{
					sb.Append(" and ");
					sb.Append(condizioniWhere);
				}
			}

			sb.Append(" order by ");
			sb.Append(nomeCampoTesto);
			sb.Append(" asc");

			string sql = sb.ToString();

			sql = Regex.Replace(sql, "([@][Ii][Dd][Cc][Oo][Mm][Uu][Nn][Ee])", "'" + idComune + "'");
			//sql = sql.Replace("@idcomune", authInfo.IdComune);

			try
			{
				using (IDbCommand cmd = Database.CreateCommand(sql))
				{
					IDbDataAdapter adp = Database.CreateDataAdapter(cmd);

					DataSet ds = new DataSet();
					adp.Fill(ds);

					//Debug.WriteLine("Databinding di " + this.ID);

					DataRow dr = ds.Tables[0].NewRow();
					dr[nomeCampoTesto] = String.Empty;
					dr[nomeCampoValore] = String.Empty;

					ds.Tables[0].Rows.InsertAt(dr, 0);

					return ds;
				}
			}
			catch (Exception ex)
			{
				DataSet ds = new DataSet();
				var dt = new DataTable();
				dt.Columns.Add(new DataColumn(nomeCampoTesto, typeof(string)));
				dt.Columns.Add(new DataColumn(nomeCampoValore, typeof(string)));

				ds.Tables.Add(dt);

				var row = dt.NewRow();
				row[nomeCampoTesto] = ex.Message;
				row[nomeCampoValore] = String.Empty;

				ds.Tables[0].Rows.InsertAt(row, 0);

				return ds;
			}


		}

		#endregion
	}
}
