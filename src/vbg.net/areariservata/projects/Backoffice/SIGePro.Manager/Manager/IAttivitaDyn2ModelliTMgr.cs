
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IAttivitaDyn2ModelliTMgr
    {
		public List<int> GetListaIndiciScheda(string idComune, int idModello, int codiceAttivita)
		{
			string sql = @"SELECT 
							  distinct i_attivitadyn2dati.indice
							FROM 
							  dyn2_modellid,
							  i_attivitadyn2dati
							WHERE
							  i_attivitadyn2dati.idcomune  = dyn2_modellid.idcomune AND
							  i_attivitadyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
							  dyn2_modellid.idcomune = {0} AND
							  dyn2_modellid.fk_d2mt_id = {1} AND
							  i_attivitadyn2dati.fk_ia_id = {2}
							order by i_attivitadyn2dati.indice asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("codiceModello"),
										db.Specifics.QueryParameterName("codiceAttivita"));


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
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceModello", idModello));
					cmd.Parameters.Add(db.CreateParameter("codiceAttivita", codiceAttivita));

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

		public List<BaseDto<int, string>> GetModelliNonUtilizzati(string idComune, int codiceAttivita, string partial, string software)
		{
			string sql = @"SELECT 
								DYN2_MODELLIT.id,
								DYN2_MODELLIT.descrizione 
							FROM
								DYN2_MODELLIT
							WHERE
								DYN2_MODELLIT.idcomune = {0} AND
								DYN2_MODELLIT.fk_d2bc_id = 'AT' AND 
								DYN2_MODELLIT.Software = {1} AND " +
								 db.Specifics.UCaseFunction("DYN2_MODELLIT.descrizione") + @" like {2} and
								DYN2_MODELLIT.id NOT IN 
								( 
									SELECT 
										FK_D2MT_ID
									FROM 
										i_attivitadyn2modellit
									WHERE
										i_attivitadyn2modellit.idcomune = {3} AND
										i_attivitadyn2modellit.fk_ia_id = {4}
								)
							order by descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune1"),
									 db.Specifics.QueryParameterName("software"),
									 db.Specifics.QueryParameterName("partial"),
									 db.Specifics.QueryParameterName("idComune2"),
									 db.Specifics.QueryParameterName("codiceAttivita"));

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
					cmd.Parameters.Add(db.CreateParameter("software", software));
					cmd.Parameters.Add(db.CreateParameter("partial", "%" + partial.ToUpperInvariant() + "%"));
					cmd.Parameters.Add(db.CreateParameter("idComune2", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceAttivita", codiceAttivita));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<BaseDto<int, string>>();

						while (dr.Read())
							rVal.Add(new BaseDto<int, string>(Convert.ToInt32(dr["id"]), dr["descrizione"].ToString()));

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

		public List<ElementoListaModelli> GetModelliCollegati(string idComune, int codiceAttivita)
		{
			string sql = @"SELECT 
							  dyn2_modellit.id,
							  dyn2_modellit.descrizione 
							FROM
							  i_attivitadyn2modellit,
							  dyn2_modellit
							WHERE
							   dyn2_modellit.idcomune	= i_attivitadyn2modellit.idcomune  AND
							   dyn2_modellit.id			= i_attivitadyn2modellit.fk_d2mt_id AND
							   i_attivitadyn2modellit.idcomune		= {0} AND
							   i_attivitadyn2modellit.fk_ia_id	= {1}
							order by dyn2_modellit.descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
										db.Specifics.QueryParameterName("codiceAttivita"));

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}

			List<ElementoListaModelli> list = new List<ElementoListaModelli>();

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceAttivita", codiceAttivita));

					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							int key		= Convert.ToInt32(rd["id"]);
							string val	= rd["descrizione"].ToString();

							list.Add(new ElementoListaModelli(key, val));
						}
					}
					return list;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public void AggiungiSchedaDinamicaAdAttivita(string idComune , int idAttivita , int idScheda)
		{
			var mod = new IAttivitaDyn2ModelliT
			{
				Idcomune = idComune,
				FkIaId = idAttivita,
				FkD2mtId = idScheda
			};

			Insert(mod);

			//var software = GetSoftwareDaIdAttivita(idComune, idAttivita);

			var svc = new EventiSchedeDinamicheAttivitaService();

			svc.Handle(new SchedaDinamicaAggiuntaAdAttivita( idAttivita));

		}

		public void Delete(IAttivitaDyn2ModelliT cls)
		{
			var logicaEliminazione = new LogicaEliminazioneDatiSchedaAttivita(db, cls.Idcomune, cls.FkIaId.Value);

			logicaEliminazione.Elimina(cls.FkD2mtId.Value);
		}
	}
}
				