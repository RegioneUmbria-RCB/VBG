// -----------------------------------------------------------------------
// <copyright file="MovimentiDyn2ModelliTMgr.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Data;
	using System.Data;
using Init.SIGePro.Manager.DTO;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public partial class MovimentiDyn2ModelliTMgr
	{
		public IEnumerable<Dyn2ModelliT> GetSchedeDelMovimento(string idComune, int codiceMovimento)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = PreparaQueryParametrica(@"SELECT 
														dyn2_modellit.* 
													FROM 
														dyn2_modellit,
														movimentidyn2modellit
													WHERE
														dyn2_modellit.idcomune = movimentidyn2modellit.idcomune AND
														dyn2_modellit.id = movimentidyn2modellit.fk_d2mt_id AND
														movimentidyn2modellit.idcomune = {0} AND
														movimentidyn2modellit.codicemovimento = {1}",
													"idComune",
													"codiceMovimento");

				using (var cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceMovimento", codiceMovimento));

					return db.GetClassList<Dyn2ModelliT>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		public IEnumerable<BaseDto<int, string>> GetModelliNonUtilizzati(string idComune, int codiceMovimento, string partial, bool usaSoftwareTT)
		{
			var movimento = new MovimentiMgr(db).GetById(idComune, codiceMovimento);
			var istanza = new IstanzeMgr(db).GetById(idComune, Convert.ToInt32(movimento.CODICEISTANZA));

			var filtroSoftware = "'" + istanza.SOFTWARE + "'";

			if (usaSoftwareTT)
				filtroSoftware += ",'TT'";

			string sql = @"SELECT 
								DYN2_MODELLIT.id,
								DYN2_MODELLIT.descrizione 
							FROM
								DYN2_MODELLIT
							WHERE
								DYN2_MODELLIT.idcomune = {0} AND
								DYN2_MODELLIT.fk_d2bc_id = 'IS' AND
								DYN2_MODELLIT.Software in (" + filtroSoftware + @") AND
								" + db.Specifics.UCaseFunction("DYN2_MODELLIT.descrizione") + @" like {1} and
								DYN2_MODELLIT.id NOT IN 
								( 
									SELECT 
										FK_D2MT_ID
									FROM 
										movimentidyn2modellit
									WHERE
										movimentidyn2modellit.idcomune = {2} AND
										movimentidyn2modellit.codiceMovimento = {3}
								)
							order by descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune1"),
									 db.Specifics.QueryParameterName("partial"),
									 db.Specifics.QueryParameterName("idComune2"),
									 db.Specifics.QueryParameterName("codiceMovimento"));

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				db.Connection.Open();
				closeCnn = true;
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune1", idComune));
					cmd.Parameters.Add(db.CreateParameter("partial", "%" + partial.ToUpperInvariant() + "%"));
					cmd.Parameters.Add(db.CreateParameter("idComune2", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceMovimento", codiceMovimento));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<BaseDto<int, string>>();

						while (dr.Read())
							rVal.Add(new BaseDto<int, string>(Convert.ToInt32(dr[ "id" ]), dr[ "descrizione" ].ToString()));

						return rVal;
					}
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
