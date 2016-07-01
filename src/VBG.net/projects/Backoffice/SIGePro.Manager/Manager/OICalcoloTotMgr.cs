using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using System.Data;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class OICalcoloTotMgr
	{
		[DataObjectMethod( DataObjectMethodType.Select ) ]
		public static List<OICalcoloTot> Find(string token , int codiceIstanza)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			OICalcoloTotMgr mgr = new OICalcoloTotMgr(authInfo.CreateDatabase());

			OICalcoloTot filtro = new OICalcoloTot();
			filtro.Idcomune = authInfo.IdComune;
			filtro.Codiceistanza = codiceIstanza;
			filtro.OrderBy = "Id asc";

			return mgr.GetList(filtro);

		}

		public string GetSoftwareDaCalcoloTot(string idComune, int idCalcoloTot)
		{
			string sql = @"SELECT 
							  istanze.software
							FROM
							  istanze,
							  o_icalcolotot
							WHERE
							  istanze.idcomune = o_icalcolotot.idcomune AND
							  istanze.codiceistanza = o_icalcolotot.codiceistanza AND
							  o_icalcolotot.Idcomune = {0} AND
							  o_icalcolotot.Id = {1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			bool closecnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closecnn = true;
				db.Connection.Open();
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
					cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

					object software = cmd.ExecuteScalar();

					if (software == null || software == DBNull.Value)
						throw new ArgumentException("Impossibile ricavare il software per o_icalcolotot.Id = " + idCalcoloTot.ToString());

					return software.ToString();
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

		#region generazione del dataset degli oneri di urbanizzazione
		public DataSet GeneraDataSetOneriUrbanizzazione(string idComune, int idCalcoloTot)
		{
			DataSet dsOneri = new DataSet();

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}

			try
			{

				List<Dictionary<string, string>> destinazioni = LeggiDestinazioniBaseAttive(idComune, idCalcoloTot);

				List<Dictionary<string, string>> tipiOnere = LeggiTipiOnere(idComune, idCalcoloTot);

				DataTable dt = new DataTable("OneriUrbanizzazione");

				dt.Columns.Add("Destinazione", typeof(string));
				
				foreach ( Dictionary<string, string> tipo in tipiOnere )
					dt.Columns.Add(tipo["ID"], typeof(double));

				// Se ho trovato almeno un tipoonere aggiungo la colonna dei totali
				if (tipiOnere.Count > 0)
				{
					string sumExpr = "";

					foreach (Dictionary<string, string> tipo in tipiOnere)
					{
						string nomeColonna = tipo["ID"];

						if (sumExpr != String.Empty)
							sumExpr += " + ";

						sumExpr += nomeColonna;
					}

					dt.Columns.Add("Totale", typeof(double), sumExpr);
				}

				dt.Columns.Add("Comandi", typeof(int));

				foreach (Dictionary<string, string> destinazione in destinazioni)
				{
					DataRow row = dt.NewRow();

					int idTestata = Convert.ToInt32( destinazione["ID"] );

					row["Destinazione"] = destinazione["DESTINAZIONE"];
					row["Comandi"] = idTestata;

					foreach ( Dictionary<string, string> tipo in tipiOnere )
					{
						string nomeColonna = tipo["ID"];
						double valore = ValoreTipoOnerePerIdTestata( idComune , idTestata , nomeColonna );
						row[nomeColonna] = valore;
					}

					dt.Rows.Add(row);
				}

				dsOneri.Tables.Add(dt);

				return dsOneri;
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		private double ValoreTipoOnerePerIdTestata(string idComune, int idTestata, string baseTipoOnere)
		{
			string sql = @"SELECT 
							  costotot
							FROM 
							  O_ICALCOLOCONTRIBT_BTO
							WHERE
							  IDCOMUNE = {0} AND
							  FK_OICCT_ID = {1} AND
							  FK_BTO_ID = {2}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
									db.Specifics.QueryParameterName("IDTESTATA"),
									db.Specifics.QueryParameterName("IDBASETIPOONERE"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDTESTATA", idTestata));
				cmd.Parameters.Add(db.CreateParameter("IDBASETIPOONERE", baseTipoOnere));

				object obj = cmd.ExecuteScalar();

				if (obj == null || obj == DBNull.Value) return 0.0d;

				return Convert.ToDouble(obj);
			}
		}

		private List<Dictionary<string, string>> LeggiTipiOnere(string idComune, int idCalcoloTot)
		{
			string sql = @"Select 
							  O_BASETIPIONERE.ID,
							  O_BASETIPIONERE.DESCRIZIONE 
							FROM
							  O_ICALCOLOCONTRIBT, 
							  O_ICALCOLOCONTRIBT_BTO,
							  O_BASETIPIONERE 
							Where 
							  O_ICALCOLOCONTRIBT_BTO.IDCOMUNE  = O_ICALCOLOCONTRIBT.IDCOMUNE AND
							  O_ICALCOLOCONTRIBT_BTO.FK_OICCT_ID = O_ICALCOLOCONTRIBT.ID AND
							  O_BASETIPIONERE.ID = O_ICALCOLOCONTRIBT_BTO.FK_BTO_ID and
							  O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
							  O_ICALCOLOCONTRIBT.FK_OICT_ID = {1}
							Group By 
							  O_BASETIPIONERE.ID,
							  O_BASETIPIONERE.DESCRIZIONE";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
									db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

				using (IDataReader rd = cmd.ExecuteReader())
				{
					List<Dictionary<string, string>> ret = new List<Dictionary<string, string>>();

					while (rd.Read())
					{
						Dictionary<string, string> row = new Dictionary<string, string>();
						row["ID"] = rd["ID"].ToString();
						row["DESCRIZIONE"] = rd["DESCRIZIONE"].ToString();

						ret.Add(row);
					}

					return ret;
				}
			}


		}

		private List<Dictionary<string, string>> LeggiDestinazioniBaseAttive(string idComune, int idCalcoloTot)
		{
			string sql = @"SELECT
											  O_ICALCOLOCONTRIBT.ID as ID,
											  OCC_BASEDESTINAZIONI.ID as IDDESTINAZIONE,
											  OCC_BASEDESTINAZIONI.DESTINAZIONE
											From 
											  O_ICALCOLOCONTRIBT,
											  OCC_BASEDESTINAZIONI 
											Where 
											  OCC_BASEDESTINAZIONI.ID = O_ICALCOLOCONTRIBT.FK_OCCBDE_ID AND
											  O_ICALCOLOCONTRIBT.IDCOMUNE   = {0} AND
											  O_ICALCOLOCONTRIBT.FK_OICT_ID = {1}";

			sql = string.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
																		db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			List<Dictionary<string, string>> destinazioniBase = new List<Dictionary<string, string>>();

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

				using (IDataReader dr = cmd.ExecuteReader())
				{
					while (dr.Read())
					{
						Dictionary<string, string> row = new Dictionary<string, string>();
						row["ID"] = dr["ID"].ToString();
						row["IDDESTINAZIONE"] = dr["IDDESTINAZIONE"].ToString();
						row["DESTINAZIONE"] = dr["DESTINAZIONE"].ToString();

						destinazioniBase.Add(row);
					}

				}

			}

			return destinazioniBase;
		}

		#endregion

		#region elaborazione del calcolo

		public void Elabora(string idComune, int idCalcoloTot)
		{
			bool closecnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closecnn = true;
				db.Connection.Open();
			}

			try
			{
				// Elimino le testate e le righe che non sono più presenti nei dettagli
				EliminaDettagliInutilizzati(idComune, idCalcoloTot);
				EliminaTestateInutilizzate(idComune, idCalcoloTot);

				// Aggiungo le righe di testata e dettaglio che corrispondono alle destinazionibase che 
				// ancora non sono presenti nelle tabelle O_ICALCOLOCONTRIBT 
				IntegraNuoveTestate(idComune, idCalcoloTot);
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}

			OICalcoloContribTMgr mgrContribT = new OICalcoloContribTMgr(db);

			List<OICalcoloContribT> listaTestate = mgrContribT.GetListByIdCalcoloTot(idComune, idCalcoloTot);

			listaTestate.ForEach(delegate(OICalcoloContribT testata) { mgrContribT.Elabora(testata); });
		}

		#region eliminazione delle righe inutilizzate
		/// <summary>
		/// Elimina tutte le righe di dettaglio le cui destinazioni non sono più utilizzate nella tabella
		/// O_ICALCOLO_DETTAGLIOT.
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		private void EliminaDettagliInutilizzati(string idComune, int idCalcoloTot)
		{
			List<int> iddaEliminare = TrovaDettagliInutilizzati(idComune, idCalcoloTot);

			string sql = "DELETE FROM O_ICALCOLOCONTRIBR WHERE idcomune={0} AND ID={1}";

			sql = String.Format( sql , db.Specifics.QueryParameterName( "IDCOMUNE" ),
										db.Specifics.QueryParameterName( "ID" ) );

			using (IDbCommand cmd = db.CreateCommand( sql ))
			{
				for (int i = 0; i < iddaEliminare.Count; i++)
				{
					cmd.Parameters.Clear();

					cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune ));
					cmd.Parameters.Add(db.CreateParameter("ID", iddaEliminare[i] ));

					cmd.ExecuteNonQuery();
				}
			}
		}

		/// <summary>
		/// Individua tutte le righe di dettaglio le cui destinazioni non sono più utilizzate nella tabella
		/// O_ICALCOLO_DETTAGLIOT.
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		private List<int> TrovaDettagliInutilizzati(string idComune, int idCalcoloTot)
		{
			string sql = @"SELECT 
						  O_ICALCOLOCONTRIBR.ID 
						FROM 
						  O_ICALCOLOCONTRIBT,
						  O_ICALCOLOCONTRIBR 
						WHERE 
						  O_ICALCOLOCONTRIBT.IDCOMUNE = O_ICALCOLOCONTRIBR.IDCOMUNE AND
						  O_ICALCOLOCONTRIBT.ID = O_ICALCOLOCONTRIBR.FK_OICCT_ID and
						  O_ICALCOLOCONTRIBT.FK_OICT_ID = {0} AND
						  O_ICALCOLOCONTRIBR.IDCOMUNE   = {1} AND
						  O_ICALCOLOCONTRIBR.FK_ODE_ID IN 
						  (
							SELECT 
							  fk_ode_id 
							FROM 
							  O_ICALCOLO_DETTAGLIOT
							WHERE
							  O_ICALCOLO_DETTAGLIOT.IDCOMUNE = {2} AND
							  O_ICALCOLO_DETTAGLIOT.FK_OIC_ID = {3}
						  )";

			sql = string.Format(sql, db.Specifics.QueryParameterName("IDCALCOLOTOT"),
										db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCOMUNE1"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT1")
										);

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE1", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT1", idCalcoloTot));


				List<int> idDaEliminare = new List<int>();

				using (IDataReader dr = cmd.ExecuteReader())
				{
					while (dr.Read())
						idDaEliminare.Add(Convert.ToInt32(dr[0]));
				}

				return idDaEliminare;
			}
		}

		/// <summary>
		/// Elimina tutte le righe di testata le cui destinazioni base non sono più utilizzate nella tabella
		/// O_ICALCOLO_DETTAGLIOT.
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		private void EliminaTestateInutilizzate(string idComune, int idCalcoloTot)
		{
			List<int> idDaEliminare = TrovaTestateInutilizzate(idComune, idCalcoloTot);

			EliminaTestateBtoInutilizzate(idComune, idDaEliminare);


			string sql = "DELETE FROM O_ICALCOLOCONTRIBT WHERE idcomune={0} AND ID={1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("ID"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				for (int i = 0; i < idDaEliminare.Count; i++)
				{
					cmd.Parameters.Clear();

					cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
					cmd.Parameters.Add(db.CreateParameter("ID", idDaEliminare[i]));

					cmd.ExecuteNonQuery();
				}
			}
		}

		/// <summary>
		/// Elimina tutte le righe di testata (BTO) le cui destinazioni base non sono più utilizzate nella tabella
		/// O_ICALCOLO_DETTAGLIOT.
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idDaEliminare">id delle testate da eliminare</param>
		private void EliminaTestateBtoInutilizzate(string idComune, List<int> idDaEliminare)
		{
			string sql = "DELETE FROM O_ICALCOLOCONTRIBT_BTO WHERE idcomune={0} AND FK_OICCT_ID={1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDTESTATA"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				for (int i = 0; i < idDaEliminare.Count; i++)
				{
					cmd.Parameters.Clear();

					cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
					cmd.Parameters.Add(db.CreateParameter("IDTESTATA", idDaEliminare[i]));

					cmd.ExecuteNonQuery();
				}
			}
		}



		/// <summary>
		/// Individua tutte le righe di testata le cui destinazioni base non sono più utilizzate nella tabella
		/// O_ICALCOLO_DETTAGLIOT.
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		private List<int> TrovaTestateInutilizzate(string idComune, int idCalcoloTot)
		{
			string sql = @"SELECT 
							  O_ICALCOLOCONTRIBT.ID 
							FROM 
								O_ICALCOLOCONTRIBT 
							WHERE 
								O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND   
							  O_ICALCOLOCONTRIBT.FK_OICT_ID = {1} and
								O_ICALCOLOCONTRIBT.FK_OCCBDE_ID NOT IN 
							(
							  SELECT 
								  FK_OCCBDE_ID
							  FROM 
								  O_ICALCOLO_DETTAGLIOT
							  WHERE
								  O_ICALCOLO_DETTAGLIOT.IDCOMUNE = {2} AND
									O_ICALCOLO_DETTAGLIOT.FK_OIC_ID = {3}
							)";

			sql = string.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"), 
										db.Specifics.QueryParameterName("IDCALCOLOTOT"),
										db.Specifics.QueryParameterName("IDCOMUNE1"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT1"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE1", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT1", idCalcoloTot));
				


				List<int> idDaEliminare = new List<int>();

				using (IDataReader dr = cmd.ExecuteReader())
				{
					while (dr.Read())
						idDaEliminare.Add(Convert.ToInt32(dr[0]));
				}

				return idDaEliminare;
			}
		}
		#endregion

		#region inserimento delle nuove testate 

		/// <summary>
		/// Aggiunge tutte le righe di testata le cui destinazionibase non sono ancora presenti nella tabella
		/// O_ICALCOLOCONTRIBT.
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		private void IntegraNuoveTestate(string idComune, int idCalcoloTot)
		{
			List<string> idDestinazioni = TrovaDestinazioniBaseUtilizzate(idComune, idCalcoloTot);

			for (int i = 0; i < idDestinazioni.Count; i++)
			{
				if (!VerificaTestataPerDestinazioneBase(idComune, idCalcoloTot, idDestinazioni[i]))
					InserisciTestataPerDestinazione(idComune, idCalcoloTot, idDestinazioni[i]);
			}
		}

		/// <summary>
		/// Crea una nuova riga in O_ICALCOLOCONTRIBT per il tipo di destinazionebase passato
		/// </summary>
		/// <remarks>
		/// - E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante
		/// - La funzione non effettua controlli sull'eventuale esistenza di una riga per la destinazione base passata
		///   tale compito è lasciato alla funzione chiamante
		/// </remarks>
		/// <param name="idComune"></param>
		/// <param name="idCalcoloTot"></param>
		/// <param name="idDestinazioneBase"></param>
		private void InserisciTestataPerDestinazione(string idComune, int idCalcoloTot, string idDestinazioneBase)
		{
			OICalcoloContribTMgr mgr = new OICalcoloContribTMgr(db);

			OICalcoloContribT cls = new OICalcoloContribT();
			cls.Idcomune = idComune;
			cls.FkOictId = idCalcoloTot;
			cls.FkOccbdeId = idDestinazioneBase;
			cls.Codiceistanza = CodiceIstanzadaIdCalcoloTot(idComune, idCalcoloTot);

			mgr.Insert(cls);

		}


		/// <summary>
		/// Verifica che esista una riga nella tabella O_ICALCOLOCONTRIBT per il tipo di destinazionebase passato
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		/// <param name="idDestinazioneBase">id destinazionebase</param>
		/// <returns>true se esiste già una riga</returns>
		private bool VerificaTestataPerDestinazioneBase(string idComune, int idCalcoloTot, string idDestinazioneBase)
		{
			string sql = "SELECT Count(*) FROM O_ICALCOLOCONTRIBT WHERE IDCOMUNE={0} AND FK_OICT_ID = {1} AND FK_OCCBDE_ID = {2}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"),
										db.Specifics.QueryParameterName("IDDESTINAZIONEBASE"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
				cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONEBASE", idDestinazioneBase));

				object count = cmd.ExecuteScalar();

				if (count == null || count == DBNull.Value) return false;

				return Convert.ToInt32(count) != 0;
			}
		}

		/// <summary>
		/// Trova le destinazionibase utilizzate nella tabella O_ICALCOLO_DETTAGLIOT
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		private List<string> TrovaDestinazioniBaseUtilizzate(string idComune, int idCalcoloTot)
		{
			string sql = @"SELECT 
						  FK_OCCBDE_ID
						from
						  O_ICALCOLO_DETTAGLIOT
						WHERE
						  IDCOMUNE  = {0} AND 
						  FK_OIC_ID = {1}
						GROUP BY
						  FK_OCCBDE_ID ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			using (IDbCommand cmd = db.CreateCommand( sql ))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

				List<string> idDestinazioni = new List<string>();

				using (IDataReader dr = cmd.ExecuteReader())
				{
					while (dr.Read())
						idDestinazioni.Add(dr[0].ToString());
				}

				return idDestinazioni;
				
			}
		}


		#endregion


		/*
		#region inserimento delle nuove righe
		/// <summary>
		/// Aggiunge tutte le righe di dettaglio le cui destinazioni non sono ancora presenti nella tabella
		/// O_ICALCOLOCONTRIBR.
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
		private void IntegraNuoviDettagli(string idComune, int idCalcoloTot)
		{
			List<OICalcoloContribT> listaTestate = new OICalcoloContribTMgr(db).GetListByIdCalcoloTot(idComune, idCalcoloTot);

			foreach (OICalcoloContribT testata in listaTestate)
			{
				List<int> listaDestinazioni = TrovaDestinazioniDaCalcoloContribT(testata);

				for( int i = 0 ; i < listaDestinazioni.Count ; i++ )
				{
					if (!VerificaDettaglioPerDestinazione(idComune, testata.Id, listaDestinazioni[i]))
						InserisciDettaglioPerDestinazione(testata, listaDestinazioni[i]);
				}
			}
		}

		/// <summary>
		/// Crea una nuova riga in O_ICALCOLOCONTRIBR per il tipo di destinazionebase passato
		/// </summary>
		/// <remarks>
		/// - E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante
		/// - La funzione non effettua controlli sull'eventuale esistenza di una riga per la destinazione passata
		///   tale compito è lasciato alla funzione chiamante
		/// </remarks>
		/// <param name="testata"></param>
		/// <param name="p"></param>
		private void InserisciDettaglioPerDestinazione(OICalcoloContribT testata, int p)
		{
			throw new Exception("The method or operation is not implemented.");
		}

		/// <summary>
		/// Verifica che esista una riga nella tabella O_ICALCOLOCONTRIBR per il tipo di destinazione passato
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idTestata">O_ICALCOLOCONTRIBT.ID</param>
		/// <param name="idDestinazioneBase">id destinazione</param>
		/// <returns>true se esiste già una riga</returns>
		private bool VerificaDettaglioPerDestinazione(string idComune, int idTestata, int idDestinazione)
		{
			string sql = @"SELECT
							  Count(*)
							FROM 
							  O_ICALCOLOCONTRIBR
							WHERE
							  IDCOMUNE  = {0} and
							  FK_OICCT_ID = {1} AND
							  FK_ODE_ID = {2}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDTESTATA"),
										db.Specifics.QueryParameterName("IDDESTINAZIONE"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDTESTATA", idTestata));
				cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));

				object count = cmd.ExecuteScalar();

				if (count == null || count == DBNull.Value) return false;

				return Convert.ToInt32(count) > 0;
			}
		}

		/// <summary>
		/// Trova a partire da una riga di O_ICALCOLOCONTRIBT tutte le destinazioni utilizzate nella tabella O_ICALCOLO_DETTAGLIOT
		/// per la destinazionebase corrispondente
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="testata">riga di O_ICALCOLOCONTRIBT</param>
		/// <returns>Lista di id di destinazioni</returns>
		private List<int> TrovaDestinazioniDaCalcoloContribT(OICalcoloContribT testata)
		{
			string sql = @"SELECT
							  FK_ODE_ID
							FROM 
							  O_ICALCOLO_DETTAGLIOT
							WHERE
							  IDCOMUNE  = {0} and
							  FK_OIC_ID = {1} AND
							  FK_OCCBDE_ID = {2}
							GROUP BY 
							  FK_ODE_ID";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"),
										db.Specifics.QueryParameterName("IDBASEDESTINAZIONE"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", testata.Idcomune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", testata.FkOictId));
				cmd.Parameters.Add(db.CreateParameter("IDBASEDESTINAZIONE", testata.FkOccbdeId));

				List<int> idDest = new List<int>();

				using (IDataReader dr = cmd.ExecuteReader())
				{
					while (dr.Read())
						idDest.Add(Convert.ToInt32(dr[0]));
				}

				return idDest;
			}
		}
		#endregion
		*/
		private int CodiceIstanzadaIdCalcoloTot(string idComune, int idCalcoloTot)
		{
			string sql = "SELECT codiceistanza FROM o_icalcolotot WHERE idcomune = {0} AND id = {1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

				object codiceIstanza = cmd.ExecuteScalar();

				if (codiceIstanza == null || codiceIstanza == DBNull.Value)
					throw new ArgumentException("Non è stato possibile trovare il codice istanza relativo all'idCalcoloTot " + idCalcoloTot.ToString() + " per l'id comune " + idComune);

				return Convert.ToInt32(codiceIstanza);
			}
		}

#endregion

        private void EffettuaCancellazioneACascata(OICalcoloTot cls)
        {
            OICalcoloDettaglioT a = new OICalcoloDettaglioT();
            a.Idcomune = cls.Idcomune;
            a.FkOicId = cls.Id;

            List<OICalcoloDettaglioT> lCalcoloDett = new OICalcoloDettaglioTMgr(db).GetList(a);
            foreach (OICalcoloDettaglioT calcoloDett in lCalcoloDett)
            {
                OICalcoloDettaglioTMgr mgr = new OICalcoloDettaglioTMgr(db);
                mgr.Delete(calcoloDett);
            }

            OICalcoloContribT b = new OICalcoloContribT();
            b.Idcomune = cls.Idcomune;
            b.FkOictId = cls.Id;

            List<OICalcoloContribT> lCalcoloContrib = new OICalcoloContribTMgr(db).GetList(b);
            foreach (OICalcoloContribT calcoloContrib in lCalcoloContrib)
            {
                OICalcoloContribTMgr mgr = new OICalcoloContribTMgr(db);
                mgr.Delete(calcoloContrib);
            }
        }
	}
}
