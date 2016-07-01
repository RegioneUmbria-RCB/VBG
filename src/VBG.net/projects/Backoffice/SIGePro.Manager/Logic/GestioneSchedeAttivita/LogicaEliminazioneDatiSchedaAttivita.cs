using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using System.Data;

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita
{
	public class LogicaEliminazioneDatiSchedaAttivita
	{
		IEnumerable<int> _listaCampiDaEliminare;
		DataBase _db;
		string _idComune;
		int _idAttivita;

		public LogicaEliminazioneDatiSchedaAttivita(DataBase db, string idComune, int idAttivita)
		{
			this._db = db;
			this._idComune = idComune;
			this._idAttivita = idAttivita;
		}

		public void Elimina( int idModello )
		{
			bool closeCnn = false;
			bool commitTransaction = false;

			if (!this._db.IsInTransaction)
			{
				this._db.BeginTransaction();
				commitTransaction = true;
			}

			try
			{
				if (this._db.Connection.State == ConnectionState.Closed)
				{
					this._db.Connection.Open();
					closeCnn = true;
				}

				SelezionaCampiDaEliminare(idModello);
				EliminaValoriSnapshot();
				EliminaModelloDaSnapshot(idModello);
				EliminaValori();
				EliminaModello(idModello);

				if (commitTransaction)
					this._db.CommitTransaction();
			}
			catch (Exception)
			{
				if (commitTransaction)
					this._db.RollbackTransaction();

				throw;
			}
			finally
			{
				if (closeCnn)
					this._db.Connection.Close();
			}
		}

		private void EliminaModello(int idModello)
		{
			var sql = @"delete from i_attivitadyn2modellit 
						WHERE idcomune = {0} AND 
							  fk_d2mt_id = {1} AND 
							  fk_ia_id = {2}";

			sql = PreparaQueryParametrica(sql, "idComune", "idModello", "idAttivita");

			using (var cmd = this._db.CreateCommand(sql))
			{
				AddParameter(cmd, "idComune", this._idComune);
				AddParameter(cmd, "idModello", idModello);
				AddParameter(cmd, "idAttivita", this._idAttivita);

				cmd.ExecuteNonQuery();
			}
		}

		private void EliminaModelloDaSnapshot(int idModello)
		{
			var sql = @"delete from i_attivitadyn2mod_t_snapshot 
						WHERE idcomune = {0} AND 
							  fk_d2mt_id = {1} AND 
							  fk_ia_id = {2}";

			sql = PreparaQueryParametrica(sql, "idComune", "idModello", "idAttivita");

			using (var cmd = this._db.CreateCommand(sql))
			{
				AddParameter(cmd, "idComune", this._idComune);
				AddParameter(cmd, "idModello", idModello);
				AddParameter(cmd, "idAttivita", this._idAttivita);

				cmd.ExecuteNonQuery();
			}
		}

		private void EliminaValori()
		{
			var sql = @"DELETE FROM i_attivitadyn2dati 
						WHERE idcomune  = {0} AND 
							  fk_IA_ID  = {1} AND 
							  FK_D2C_ID = {2}";

			sql = PreparaQueryParametrica(sql, "idComune", "idAttivita", "idCampo");

			foreach (var id in _listaCampiDaEliminare)
			{
				using (var cmd = this._db.CreateCommand(sql))
				{
					AddParameter(cmd, "idComune", this._idComune);
					AddParameter(cmd, "idAttivita", this._idAttivita);
					AddParameter(cmd, "idCampo", id);

					cmd.ExecuteNonQuery();
				}
			}

		}

		private void EliminaValoriSnapshot()
		{
			var sql = @"DELETE FROM i_attivitadyn2dati_snapshot 
						WHERE idcomune  = {0} AND 
							  fk_IA_ID  = {1} AND 
							  FK_D2C_ID = {2}";

			sql = PreparaQueryParametrica(sql, "idComune", "idAttivita", "idCampo");

			foreach (var id in _listaCampiDaEliminare)
			{
				using (IDbCommand cmd = this._db.CreateCommand(sql))
				{
					AddParameter(cmd, "idComune", this._idComune);
					AddParameter(cmd, "idAttivita", this._idAttivita);
					AddParameter(cmd, "idCampo", id);

					cmd.ExecuteNonQuery();
				}
			}
		}

		private void SelezionaCampiDaEliminare(int idScheda)
		{
			var sql = @"SELECT 
							dyn2_modellid.fk_d2c_ID
						FROM 
							i_attivitaDyn2modellit,
							dyn2_modellid
						WHERE
							dyn2_modellid.idcomune   = i_attivitaDyn2modellit.idComune AND
							dyn2_modellid.fk_d2mt_id = i_attivitaDyn2modellit.fk_d2mt_id and 
							dyn2_modellid.fk_d2mdt_id IS NULL and
							i_attivitaDyn2modellit.idComune = {0} AND
							i_attivitaDyn2modellit.fk_ia_id = {1} AND
							i_attivitaDyn2modellit.fk_d2mt_id = {2} AND
							dyn2_modellid.fk_d2c_ID NOT IN 
							(
								SELECT 
									dyn2_modellid.fk_d2c_ID                         
								FROM 
									i_attivitaDyn2modellit,
									dyn2_modellid
								WHERE
									dyn2_modellid.idcomune      =   i_attivitaDyn2modellit.idComune AND
									dyn2_modellid.fk_d2mt_id    =   i_attivitaDyn2modellit.fk_d2mt_id and 
									dyn2_modellid.fk_d2mdt_id IS NULL and
									i_attivitaDyn2modellit.idComune = {3} AND
									i_attivitaDyn2modellit.fk_ia_id = {4} AND
									i_attivitaDyn2modellit.fk_d2mt_id <> {5}
							)";

			sql = PreparaQueryParametrica(sql, "idcomune1", "idAttivita1", "idModello1", "idcomune2", "idAttivita2", "idModello2");

			using (IDbCommand cmd = this._db.CreateCommand(sql))
			{
				AddParameter(cmd, "idcomune1", this._idComune);
				AddParameter(cmd, "idAttivita1", this._idAttivita);
				AddParameter(cmd, "idModello1", idScheda);
				AddParameter(cmd, "idcomune2", this._idComune);
				AddParameter(cmd, "idAttivita2", this._idAttivita);
				AddParameter(cmd, "idModello2", idScheda);

				using (var dr = cmd.ExecuteReader())
				{
					var campiDaEliminare = new List<int>();

					while (dr.Read())
						campiDaEliminare.Add(Convert.ToInt32(dr[0]));

					this._listaCampiDaEliminare = campiDaEliminare;
				}
			}

		}

		protected string PreparaQueryParametrica(string sql, params string[] nomiParametri)
		{
			for (int i = 0; i < nomiParametri.Length; i++)
				nomiParametri[i] = this._db.Specifics.QueryParameterName(nomiParametri[i]);

			return String.Format(sql, nomiParametri);
		}

		private void AddParameter(IDbCommand cmd, string parameterName, object parameterValue)
		{
			cmd.Parameters.Add(this._db.CreateParameter(parameterName, parameterValue));
		}
	}
}
