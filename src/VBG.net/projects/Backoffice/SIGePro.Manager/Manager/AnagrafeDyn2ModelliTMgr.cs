
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.DTO;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class AnagrafeDyn2ModelliTMgr
    {
		/// <summary>
		/// Ritorna la lista di tutti i modelli utilizzati nell'anagrafica
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="codiceAnagrafe"></param>
		/// <returns></returns>
		public List<ElementoListaModelli> GetModelliCollegati(string idComune, int codiceAnagrafe)
		{
			string sql = @"SELECT 
							  dyn2_modellit.id,
							  dyn2_modellit.descrizione 
							FROM
							  anagrafeDyn2Modellit,
							  dyn2_modellit
							WHERE
							   dyn2_modellit.idcomune	= anagrafeDyn2Modellit.idcomune  AND
							   dyn2_modellit.id			= anagrafeDyn2Modellit.fk_d2mt_id AND
							   anagrafeDyn2Modellit.idcomune		= {0} AND
							   anagrafeDyn2Modellit.codiceanagrafe	= {1}
							order by dyn2_modellit.descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
										db.Specifics.QueryParameterName("codiceAnagrafe"));

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
					cmd.Parameters.Add(db.CreateParameter("codiceAnagrafe", codiceAnagrafe));

					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							int key		= Convert.ToInt32(rd["id"]);
							string val	= rd["descrizione"].ToString();

							list.Add( new ElementoListaModelli( key , val ) );
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

		/// <summary>
		/// Data un anagrafica ritorna la lista dei modelli non utilizzati
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="codiceAnagrafe"></param>
		/// <returns></returns>
		public IEnumerable<BaseDto<int, string>> GetModelliNonUtilizzati(string idComune, int codiceAnagrafe, string partial)
		{
			string sql = @"SELECT 
								DYN2_MODELLIT.id,
								DYN2_MODELLIT.descrizione 
							FROM
								DYN2_MODELLIT
							WHERE
								DYN2_MODELLIT.idcomune = {0} AND
								DYN2_MODELLIT.fk_d2bc_id = 'AN' AND
								" + db.Specifics.UCaseFunction("DYN2_MODELLIT.descrizione") + @" like {1} and
								DYN2_MODELLIT.id NOT IN 
								( 
									SELECT 
										FK_D2MT_ID
									FROM 
										ANAGRAFEDYN2MODELLIT
									WHERE
										ANAGRAFEDYN2MODELLIT.idcomune = {2} AND
										ANAGRAFEDYN2MODELLIT.codiceAnagrafe = {3}
								)
							order by descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune1"),
										db.Specifics.QueryParameterName("partial"),
										db.Specifics.QueryParameterName("idComune2"),
										db.Specifics.QueryParameterName("codiceAnagrafe"));

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
					cmd.Parameters.Add(db.CreateParameter("codiceAnagrafe", codiceAnagrafe));

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

		private void EffettuaCancellazioneACascata(AnagrafeDyn2ModelliT cls)
		{
			// se sono stati inseriti valori elmino tutti quelli che non sono già utilizzati in altri modelli
			EliminaValoriInutilizzati(cls);

		}

		private void EliminaValoriInutilizzati(AnagrafeDyn2ModelliT cls)
		{
			string sql = @"SELECT 
							dyn2_modellid.fk_d2c_ID
						FROM 
							anagrafeDyn2modellit,
							dyn2_modellid
						WHERE
							dyn2_modellid.idcomune		= anagrafeDyn2modellit.idComune AND
							dyn2_modellid.fk_d2mt_id	= anagrafeDyn2modellit.fk_d2mt_id and 
							dyn2_modellid.fk_d2mdt_id IS NULL and
							anagrafeDyn2modellit.idComune		= {0} AND
							anagrafeDyn2modellit.CodiceAnagrafe	= {1} AND
							anagrafeDyn2modellit.fk_d2mt_id		= {2} AND
							dyn2_modellid.fk_d2c_ID NOT IN 
							(
								SELECT 
								  dyn2_modellid.fk_d2c_ID
								FROM 
								  anagrafeDyn2modellit,
								  dyn2_modellid
								WHERE
								  dyn2_modellid.idcomune	= anagrafeDyn2modellit.idComune AND
								  dyn2_modellid.fk_d2mt_id	= anagrafeDyn2modellit.fk_d2mt_id and 
								  dyn2_modellid.fk_d2mdt_id IS NULL and
								  anagrafeDyn2modellit.idComune			= {3} AND
								  anagrafeDyn2modellit.CodiceAnagrafe	= {4} AND
								  anagrafeDyn2modellit.fk_d2mt_id <> {5}
							)";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune1"),
									db.Specifics.QueryParameterName("CodiceAnagrafe1"),
									db.Specifics.QueryParameterName("codiceModello1"),
									db.Specifics.QueryParameterName("idComune2"),
									db.Specifics.QueryParameterName("CodiceAnagrafe2"),
									db.Specifics.QueryParameterName("codiceModello2"));

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				db.Connection.Open();
				closeCnn = true;
			}

			List<int> ids = new List<int>();

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune1", cls.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("codiceAnagrafe1", cls.Codiceanagrafe));
					cmd.Parameters.Add(db.CreateParameter("codiceModello1", cls.FkD2mtId));
					cmd.Parameters.Add(db.CreateParameter("idComune2", cls.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("codiceAnagrafe2", cls.Codiceanagrafe));
					cmd.Parameters.Add(db.CreateParameter("codiceModello2", cls.FkD2mtId));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
							ids.Add(Convert.ToInt32(dr[0]));
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

			AnagrafeDyn2DatiMgr mgrDati = new AnagrafeDyn2DatiMgr(db);

			foreach (int id in ids)
			{
                List<AnagrafeDyn2Dati> dati = mgrDati.GetByIdNoIndice(cls.Idcomune, cls.Codiceanagrafe.GetValueOrDefault(int.MinValue), id);

				for (int i = 0; i < dati.Count; i++)
					mgrDati.Delete(dati[i]);
			}
		}


		public List<int> GetListaIndiciScheda(string idComune, int codiceanagrafe, int codiceModello)
		{
			string sql = @"SELECT 
							  distinct anagrafedyn2dati.indice
							FROM 
							  dyn2_modellid,
							  anagrafedyn2dati
							WHERE
							  anagrafedyn2dati.idcomune = dyn2_modellid.idcomune AND
							  anagrafedyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
							  dyn2_modellid.idcomune = {0} AND
							  dyn2_modellid.fk_d2mt_id = {1} AND
							  anagrafedyn2dati.codiceanagrafe = {2}
							order by anagrafedyn2dati.indice asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("codiceModello"),
										db.Specifics.QueryParameterName("codiceanagrafe"));


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
					cmd.Parameters.Add(db.CreateParameter("codiceModello", codiceModello));
					cmd.Parameters.Add(db.CreateParameter("codiceanagrafe", codiceanagrafe));

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
	}
}
				