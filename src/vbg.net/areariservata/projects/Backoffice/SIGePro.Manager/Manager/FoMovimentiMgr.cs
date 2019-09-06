// -----------------------------------------------------------------------
// <copyright file="FoMovimentiMgr.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using PersonalLib2.Data;
	using System.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class FoMovimentiMgr
	{
		DataBase _db;

		public FoMovimentiMgr(DataBase db)
		{
			this._db = db;
		}

		public string GetDati(string idComune, int idMovimentoDaFare)
		{

			bool closeCnn = false;

			try
			{
				if (_db.Connection.State == ConnectionState.Closed)
				{
					_db.Connection.Open();
					closeCnn = true;
				}

				var riferimenti = GetRiferimentiMovimentoDaIdScadenza(idComune, idMovimentoDaFare);

				if (riferimenti == null)
					throw new Exception("L'identificativo scadenza " + idMovimentoDaFare + " non esiste nel comune " + idComune);

				var sql = String.Format("SELECT dati FROM fo_movimenti WHERE idcomune = {0} AND codmovorig={1} AND tipomovdafare={2}",
										this._db.Specifics.QueryParameterName("idComune"),
										this._db.Specifics.QueryParameterName("codmovorig"),
										this._db.Specifics.QueryParameterName("tipomovdafare"));

				using (IDbCommand cmd = _db.CreateCommand(sql))
				{
					cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(this._db.CreateParameter("codmovorig", riferimenti.Item1));
					cmd.Parameters.Add(this._db.CreateParameter("tipomovdafare", riferimenti.Item2));

					var txt = cmd.ExecuteScalar();

					return txt == null || String.IsNullOrEmpty(txt.ToString()) ? null : txt.ToString();
				}
			}
			finally
			{
				if (closeCnn)
					this._db.Connection.Close();
			}

		}

		public void SalvaDati(string idComune, int idMovimentoDaFare, string dati)
		{
			bool closeCnn = false;

			try
			{
				if (_db.Connection.State == ConnectionState.Closed)
				{
					_db.Connection.Open();
					closeCnn = true;
				}

				var riferimenti = GetRiferimentiMovimentoDaIdScadenza(idComune, idMovimentoDaFare);

				if (riferimenti == null)
					throw new Exception("L'identificativo scadenza " + idMovimentoDaFare + " non esiste nel comune " + idComune) ;

				var sql = String.Format("SELECT count(*) FROM fo_movimenti WHERE idcomune = {0} AND codmovorig={1} AND tipomovdafare={2}",
										this._db.Specifics.QueryParameterName("idComune"),
										this._db.Specifics.QueryParameterName("codmovorig"),
										this._db.Specifics.QueryParameterName("tipomovdafare"));

				using (IDbCommand cmd = _db.CreateCommand(sql))
				{
					cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(this._db.CreateParameter("codmovorig", riferimenti.Item1));
					cmd.Parameters.Add(this._db.CreateParameter("tipomovdafare", riferimenti.Item2));

					var objCount = cmd.ExecuteScalar();

					if (objCount == DBNull.Value || Convert.ToInt32(objCount) == 0)
						InsertDatiMovimento(idComune, riferimenti.Item1, riferimenti.Item2, dati);
					else
						UpdateDatiMovimento(idComune, riferimenti.Item1, riferimenti.Item2, dati);
				}
			}
			finally
			{
				if (closeCnn)
					this._db.Connection.Close();
			}
		}

		public void ImpostaMovimentoComeTrasmesso(string idComune, int idMovimentoDaFare)
		{
			bool closeCnn = false;

			try
			{
				if (_db.Connection.State == ConnectionState.Closed)
				{
					_db.Connection.Open();
					closeCnn = true;
				}

				var riferimenti = GetRiferimentiMovimentoDaIdScadenza(idComune, idMovimentoDaFare);
				var idMovimentoOrigine = riferimenti.Item1;
				var tipoMovimentoDaEffettuare = riferimenti.Item2;

				var sql = String.Format("update fo_movimenti set trasmesso = 1 where idcomune = {0} and codmovorig = {1} and tipomovdafare = {2}",
											this._db.Specifics.QueryParameterName("idcomune"),
											this._db.Specifics.QueryParameterName("codmovorig"),
											this._db.Specifics.QueryParameterName("tipomovdafare"));

				using (IDbCommand cmd = _db.CreateCommand(sql))
				{
					cmd.Parameters.Add(this._db.CreateParameter("idcomune", idComune));
					cmd.Parameters.Add(this._db.CreateParameter("codmovorig", idMovimentoOrigine));
					cmd.Parameters.Add(this._db.CreateParameter("tipomovdafare", tipoMovimentoDaEffettuare));

					cmd.ExecuteNonQuery();
				}
			}
			finally
			{
				if (closeCnn)
					_db.Connection.Close();
			}

		}

		private void UpdateDatiMovimento(string idComune, int idMovimentoOrigine, string tipoMovimentoDaEffettuare, string dati)
		{

			bool closeCnn = false;

			try
			{
				if (_db.Connection.State == ConnectionState.Closed)
				{
					_db.Connection.Open();
					closeCnn = true;
				}

				var sql = String.Format("update fo_movimenti set dati = {3} where idcomune = {0} and codmovorig = {1} and tipomovdafare = {2}",
											this._db.Specifics.QueryParameterName("idcomune"),			
											this._db.Specifics.QueryParameterName("codmovorig"),
											this._db.Specifics.QueryParameterName("tipomovdafare"),
											this._db.Specifics.QueryParameterName("dati") );

				using (IDbCommand cmd = _db.CreateCommand(sql))
				{
					cmd.Parameters.Add(this._db.CreateParameter("idcomune",idComune));
					cmd.Parameters.Add(this._db.CreateParameter("codmovorig", idMovimentoOrigine));
					cmd.Parameters.Add(this._db.CreateParameter("tipomovdafare", tipoMovimentoDaEffettuare));
					cmd.Parameters.Add(this._db.CreateParameter("dati", dati));

					cmd.ExecuteNonQuery();
				}
			}
			finally
			{
				if (closeCnn)
					_db.Connection.Close();
			}

		}

		private void InsertDatiMovimento(string idComune, int idMovimentoOrigine, string tipoMovimentoDaEffettuare, string dati)
		{
			bool closeCnn = false;

			try
			{
				if (_db.Connection.State == ConnectionState.Closed)
				{
					_db.Connection.Open();
					closeCnn = true;
				}

				var sql = String.Format("INSERT INTO fo_movimenti (idcomune,codmovorig,tipomovdafare,dati,trasmesso) VALUES ({0},{1},{2},{3},0)",
											this._db.Specifics.QueryParameterName("idcomune"),
											this._db.Specifics.QueryParameterName("codmovorig"),
											this._db.Specifics.QueryParameterName("tipomovdafare"),
											this._db.Specifics.QueryParameterName("dati"));

				using (IDbCommand cmd = _db.CreateCommand(sql))
				{
					cmd.Parameters.Add(this._db.CreateParameter("idcomune", idComune));
					cmd.Parameters.Add(this._db.CreateParameter("codmovorig", idMovimentoOrigine));
					cmd.Parameters.Add(this._db.CreateParameter("tipomovdafare", tipoMovimentoDaEffettuare));
					cmd.Parameters.Add(this._db.CreateParameter("dati", dati));

					cmd.ExecuteNonQuery();
				}
			}
			finally
			{
				if (closeCnn)
					_db.Connection.Close();
			}
		}

		private Tuple<int, string> GetRiferimentiMovimentoDaIdScadenza(string idComune, int idScadenza)
		{
			bool closeCnn = false;

			try
			{
				if (_db.Connection.State == ConnectionState.Closed)
				{
					_db.Connection.Open();
					closeCnn = true;
				}

				// Leggo l'id del movimento di origine ed il tipo di movimento da fare
				var sql = String.Format(@"select 
											mf.codicemovimento    as codicemovimento,
											mdf.tipomovimento     as tipomovimentodafare
										FROM 
											movimenti mdf 
												LEFT JOIN movimenti_contromovimenti mcm
													ON  mcm.idcomune                =mdf.idcomune
													AND mcm.codicecontromovimento   =mdf.codicemovimento
												LEFT JOIN movimenti mf
													ON  mf.idcomune        =mcm.idcomune
													AND mf.codicemovimento =mcm.codicemovimento
										WHERE 
											mdf.idcomune={0} AND 
											mdf.codicemovimento={1}",
										this._db.Specifics.QueryParameterName("idComune"),
										this._db.Specifics.QueryParameterName("codicescadenza"));

				using (IDbCommand cmd = _db.CreateCommand(sql))
				{
					cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(this._db.CreateParameter("codicescadenza", idScadenza));

					using (var dr = cmd.ExecuteReader())
					{
						if (!dr.Read())
							return null;

						return new Tuple<int, string>(Convert.ToInt32(dr["CODICEMOVIMENTO"]), dr["TIPOMOVIMENTODAFARE"].ToString());
					}
				}
			}
			finally
			{
				if (closeCnn)
					this._db.Connection.Close();
			}
		}
	}
}
