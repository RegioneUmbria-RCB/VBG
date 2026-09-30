using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.Data;
using Init.Utils.Math;
using Init.Utils;

namespace Init.SIGePro.Manager
{
	public partial class OICalcoloContribTMgr
	{
		public List<OICalcoloContribT> GetListByIdCalcoloTot(string idComune, int idCalcoloTot)
		{
			OICalcoloContribT filtro = new OICalcoloContribT();
			filtro.Idcomune = idComune;
			filtro.FkOictId = idCalcoloTot;

			return GetList(filtro);
		}

		#region Prima elaborazione e creazione righe in O_ICALCOLOCONTRIBR

		public void Elabora(OICalcoloContribT testata)
		{
			if (testata.PrimoInserimento )
				return;

			OICalcoloTot calcoloTot = new OICalcoloTotMgr( db ).GetById( testata.Idcomune , testata.FkOictId.Value );

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed )
			{
				closeCnn = true;
				db.Connection.Open();
			}

			try
			{
				List<int> idDestinazioni = TrovaDestinazioniDaCalcoloContribT(testata);

				AggiornaOCreaContribR_TabABC(testata, calcoloTot, idDestinazioni);
				AggiornaOCreaContribR_TabD(testata, calcoloTot, idDestinazioni);

				ElaboraBto(testata);
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
			
		}


		private void AggiornaOCreaContribR_TabD(OICalcoloContribT testata, OICalcoloTot calcoloTot, List<int> idDestinazioni)
		{
			for (int i = 0; i < idDestinazioni.Count; i++)
			{
				List<int> idTipiOneri = TrovaTipiOneriDaTabellaD(testata.Idcomune, idDestinazioni[i], calcoloTot.FkOvcId.Value, testata.FkOclaId.GetValueOrDefault(int.MinValue) );

				if (idTipiOneri.Count == 0) continue;

				for (int j = 0; j < idTipiOneri.Count; j++)
				{
                    double costom = TrovaCostoDaTabellaD(testata.Idcomune, idDestinazioni[i], calcoloTot.FkOvcId.GetValueOrDefault(int.MinValue), idTipiOneri[j], testata.FkOclaId.GetValueOrDefault(int.MinValue));

                    double superficie = TrovaSuperficieDaDestinazioneECalcoloTot(testata.Idcomune, testata.FkOictId.GetValueOrDefault(int.MinValue), idDestinazioni[i]);

					AggiornaDatoContribR(testata, idDestinazioni[i], idTipiOneri[j], costom, superficie);
				}
			}
		}

		private void AggiornaOCreaContribR_TabABC(OICalcoloContribT testata, OICalcoloTot calcoloTot, List<int> idDestinazioni)
		{
			for (int i = 0; i < idDestinazioni.Count; i++)
			{
                List<int> idTipiOneri = TrovaTipiOneriDaTabellaABC(testata.Idcomune, idDestinazioni[i], calcoloTot.FkOvcId.GetValueOrDefault(int.MinValue), testata.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue), testata.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue), testata.FkOitId.GetValueOrDefault(int.MinValue), testata.FkOinId.GetValueOrDefault(int.MinValue));

				if (idTipiOneri.Count == 0) continue;

				for (int j = 0; j < idTipiOneri.Count; j++)
				{
                    string idComune = testata.Idcomune;
                    int idDestinazione = idDestinazioni[i];
                    int idValiditaCoeff = calcoloTot.FkOvcId.GetValueOrDefault(int.MinValue);
                    int idAreaZto = testata.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue);
                    int idAreaPrg = testata.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue);
                    int idOit = testata.FkOitId.GetValueOrDefault(int.MinValue);
                    int idOin = testata.FkOinId.GetValueOrDefault(int.MinValue);

                    double costom = TrovaCostoDaTabellaABC(idComune, idDestinazione, idValiditaCoeff, idTipiOneri[j], idAreaZto, idAreaPrg, idOit, idOin);

                    int idCalcoloTot = testata.FkOictId.GetValueOrDefault(int.MinValue);

                    double superficie = TrovaSuperficieDaDestinazioneECalcoloTot(idComune, idCalcoloTot, idDestinazioni[i]);

					AggiornaDatoContribR(testata, idDestinazioni[i], idTipiOneri[j], costom, superficie);
				}
			}
		}


		private void AggiornaDatoContribR(OICalcoloContribT testata, int idDestinazione, int idTipoOnere, double costom, double superficie)
		{
			OICalcoloContribRMgr rMgr = new OICalcoloContribRMgr(db);
			OICalcoloContribR cls = rMgr.GetByContribTTipoOnereDestinazione(testata.Idcomune, testata.Id.Value, idTipoOnere, idDestinazione);

			if (cls == null)
			{
				cls = new OICalcoloContribR();
				cls.Idcomune = testata.Idcomune;
				cls.Codiceistanza = testata.Codiceistanza;
				cls.FkOicctId = testata.Id;
				cls.FkOtoId = idTipoOnere;
				cls.FkOdeId = idDestinazione;
			}

			cls.SuperficieCubatura	= Arrotondamento.PerEccesso(superficie,2);
			cls.Costom				= Arrotondamento.PerEccesso(costom, 2);

			rMgr.RicalcolaRiduzioniECostoTotale(cls, true);
		}

		/// <summary>
		/// Legge la superficie totale per il tipo di destinazione passata
		/// </summary>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// <param name="idComune">id comune</param>
		/// <param name="idCalcoloTot">id calcolotot</param>
		/// <param name="idDestinazione">destinazione</param>
		/// <returns></returns>
		private double TrovaSuperficieDaDestinazioneECalcoloTot(string idComune, int idCalcoloTot, int idDestinazione )
		{
			string sql = @"select 
								sum(TOTALE)
							From 
							  O_ICALCOLO_DETTAGLIOT
							Where 
							  IDCOMUNE  = {0} AND 
							  FK_OIC_ID = {1} AND
							  FK_ODE_ID = {2}";

			sql = string.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"),
										db.Specifics.QueryParameterName("IDDESTINAZIONE"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
				cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione ));

				object tot = cmd.ExecuteScalar();

				if (tot == null || tot == DBNull.Value)
					return 0.0d;

				return Convert.ToDouble(tot);
			}

		}


		/// <summary>
		/// Legge il costo disponibile per i filtri passati
		/// </summary>
		/// <remarks>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// </remarks>
		/// <param name="idComune">idcomune</param>
		/// <param name="idDestinazione">destinazione</param>
		/// <param name="idValiditaCoeff">listino</param>
		/// <param name="idAreaZto">Area zto</param>
		/// <param name="idAreaPrg">Area prg</param>
		/// <param name="idOit">oit</param>
		/// <param name="idOin">oin</param>
		/// <returns>Lista di tipi onere</returns>
		private double TrovaCostoDaTabellaABC(string idComune, int idDestinazione, int idValiditaCoeff, int idTipoOnere, int idAreaZto, int idAreaPrg, int idOit, int idOin)
		{
			string sql = @"Select 
							  COSTO 
							From 
							  O_TABELLAABC 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} AND
							  FK_OTO_ID = {3} ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDDESTINAZIONE"),
										db.Specifics.QueryParameterName("IDVALIDITACOEFF"),
										db.Specifics.QueryParameterName("IDTIPOONERE") );

			if (idAreaZto > 0)
				sql += " AND FK_AREE_CODICEAREA_ZTO = " + db.Specifics.QueryParameterName("IDAREAZTO");

			if (idAreaPrg > 0)
				sql += " AND FK_AREE_CODICEAREA_PRG = " + db.Specifics.QueryParameterName("IDAREAPRG");

			if (idOit > 0)
				sql += " AND FK_OIT_ID = " + db.Specifics.QueryParameterName("IDOIT");

			if (idOin > 0)
				sql += " AND FK_OIN_ID = " + db.Specifics.QueryParameterName("IDOIN");

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));
				cmd.Parameters.Add(db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));
				cmd.Parameters.Add(db.CreateParameter("IDTIPOONERE", idTipoOnere));

				if (idAreaZto > 0)
					cmd.Parameters.Add(db.CreateParameter("IDAREAZTO", idAreaZto));

				if (idAreaPrg > 0)
					cmd.Parameters.Add(db.CreateParameter("IDAREAPRG", idAreaPrg));

				if (idOit > 0)
					cmd.Parameters.Add(db.CreateParameter("IDOIT", idOit));

				if (idOin > 0)
					cmd.Parameters.Add(db.CreateParameter("IDOIN", idOin));

				using (IDataReader dr = cmd.ExecuteReader())
				{
					if (dr.Read())
						return Convert.ToDouble(dr[0]);

					return 0.0d;
				}
			}
		}


		/// <summary>
		/// Legge il costo disponibile per i filtri passati
		/// </summary>
		/// <remarks>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// </remarks>
		/// <param name="idComune">idcomune</param>
		/// <param name="idDestinazione">destinazione</param>
		/// <param name="idValiditaCoeff">listino</param>
		/// <param name="idOca">Classi addetti</param>
		/// <returns>Lista di tipi onere</returns>
		private double TrovaCostoDaTabellaD(string idComune, int idDestinazione, int idValiditaCoeff, int idTipoOnere, int idOca)
		{
			string sql = @"Select 
							  COSTO 
							From 
							  O_TABELLAD 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} AND
							  FK_OTO_ID = {3} ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDDESTINAZIONE"),
										db.Specifics.QueryParameterName("IDVALIDITACOEFF"),
										db.Specifics.QueryParameterName("IDTIPOONERE"));

			if (idOca > 0)
				sql += " AND FK_OCA_ID = " + db.Specifics.QueryParameterName("IDOCA");

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));
				cmd.Parameters.Add(db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));
				cmd.Parameters.Add(db.CreateParameter("IDTIPOONERE", idTipoOnere));

				if (idOca > 0)
					cmd.Parameters.Add(db.CreateParameter("IDOCA", idOca));

				using (IDataReader dr = cmd.ExecuteReader())
				{
					if (dr.Read())
						return Convert.ToDouble(dr[0]);

					return 0.0d;
				}
			}
		}


		/// <summary>
		/// Legge i tipi di onere disponibili per i filtri passati
		/// </summary>
		/// <remarks>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// </remarks>
		/// <param name="idComune">idcomune</param>
		/// <param name="idDestinazione">destinazione</param>
		/// <param name="idValiditaCoeff">listino</param>
		/// <param name="idAreaZto">Area zto</param>
		/// <param name="idAreaPrg">Area prg</param>
		/// <param name="idOit">oit</param>
		/// <param name="idOin">oin</param>
		/// <returns>Lista di tipi onere</returns>
		private List<int> TrovaTipiOneriDaTabellaABC(string idComune, int idDestinazione , int idValiditaCoeff, int idAreaZto, int idAreaPrg, int idOit, int idOin)
		{
			string sql = @"Select 
							  FK_OTO_ID 
							From 
							  O_TABELLAABC 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDDESTINAZIONE"),
										db.Specifics.QueryParameterName("IDVALIDITACOEFF"));

			if (idAreaZto > 0)
				sql += " AND FK_AREE_CODICEAREA_ZTO = " + db.Specifics.QueryParameterName("IDAREAZTO");

			if (idAreaPrg > 0)
				sql += " AND FK_AREE_CODICEAREA_PRG = " + db.Specifics.QueryParameterName("IDAREAPRG");

			if ( idOit > 0 )
				sql += " AND FK_OIT_ID = " + db.Specifics.QueryParameterName("IDOIT");

			if (idOin > 0)
				sql += " AND FK_OIN_ID = " + db.Specifics.QueryParameterName("IDOIN");

			sql += " Group By FK_OTO_ID";

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add( db.CreateParameter( "IDCOMUNE" , idComune ) );
				cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));
				cmd.Parameters.Add(db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));

				if (idAreaZto > 0)
					cmd.Parameters.Add( db.CreateParameter("IDAREAZTO", idAreaZto ) );

				if (idAreaPrg > 0)
					cmd.Parameters.Add( db.CreateParameter("IDAREAPRG", idAreaPrg ) );

				if (idOit > 0)
					cmd.Parameters.Add( db.CreateParameter("IDOIT", idOit ) );

				if (idOin > 0)
					cmd.Parameters.Add( db.CreateParameter("IDOIN", idOin ) );

				using (IDataReader dr = cmd.ExecuteReader())
				{
					List<int> idTipiOnere = new List<int>();

					while (dr.Read())
						idTipiOnere.Add(Convert.ToInt32(dr[0]));

					return idTipiOnere;
				}
			}
		}


		/// <summary>
		/// Legge i tipi di onere disponibili per i filtri passati
		/// </summary>
		/// <remarks>
		/// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
		/// </remarks>
		/// <param name="idComune">idcomune</param>
		/// <param name="idDestinazione">destinazione</param>
		/// <param name="idValiditaCoeff">listino</param>
		/// <param name="idOca">Classi addetti</param>
		/// <returns>Lista di tipi onere</returns>
		private List<int> TrovaTipiOneriDaTabellaD(string idComune, int idDestinazione, int idValiditaCoeff, int idOca )
		{
			string sql = @"Select 
							  FK_OTO_ID 
							From 
							  O_TABELLAD 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDDESTINAZIONE"),
										db.Specifics.QueryParameterName("IDVALIDITACOEFF"));

			if (idOca > 0 )
				sql += " AND FK_OCA_ID = " + db.Specifics.QueryParameterName("IDOCA");

			sql += " Group By FK_OTO_ID";

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));
				cmd.Parameters.Add(db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));

				if (idOca > 0)
					cmd.Parameters.Add(db.CreateParameter("IDOCA", idOca));

				using (IDataReader dr = cmd.ExecuteReader())
				{
					List<int> idTipiOnere = new List<int>();

					while (dr.Read())
						idTipiOnere.Add(Convert.ToInt32(dr[0]));

					return idTipiOnere;
				}
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

		#region Seconda elaborazione e popolamento della tabella O_ICALCOLOCONTRIBT_BTO

		public void ElaboraBto(OICalcoloContribT testata)
		{
			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}

			try
			{

				// eliminazione delle vecchie righe
				EliminaBto(testata.Idcomune, testata.Id.Value);

				// inserimento delle nuove righe gruppate per tipionerebase
				InserisciRigheBto(testata);
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		private void InserisciRigheBto(OICalcoloContribT testata)
		{
			string sql = @"SELECT 
								O_TIPIONERI.FK_BTO_ID as ID , 
								Sum( O_ICALCOLOCONTRIBR.COSTOTOT ) AS TOTALE
							FROM 
							  O_ICALCOLOCONTRIBR,
							  O_TIPIONERI
							WHERE
							   O_TIPIONERI.IDCOMUNE = O_ICALCOLOCONTRIBR.IDCOMUNE AND
							   O_TIPIONERI.ID = O_ICALCOLOCONTRIBR.FK_OTO_ID AND
							   O_ICALCOLOCONTRIBR.IDCOMUNE = {0} AND
							   O_ICALCOLOCONTRIBR.FK_OICCT_ID = {1}
							GROUP BY 
							   O_TIPIONERI.FK_BTO_ID";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCONTRIBT"));

			List<KeyValuePair<string, double>> valori = new List<KeyValuePair<string, double>>();

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", testata.Idcomune));
				cmd.Parameters.Add(db.CreateParameter("IDCONTRIBT", testata.Id));

				using (IDataReader dr = cmd.ExecuteReader())
				{
					while (dr.Read())
					{
						string bto = dr["ID"].ToString();
						double val = Convert.ToDouble(dr["TOTALE"]);

						valori.Add(new KeyValuePair<string, double>(bto, val));
					}
				}
			}

			OICalcoloContribTBTOMgr btoMgr = new OICalcoloContribTBTOMgr(db);

			foreach (KeyValuePair<string, double> val in valori)
			{
				OICalcoloContribTBTO bto = new OICalcoloContribTBTO();

				bto.Idcomune = testata.Idcomune;
				bto.FkOicctId = testata.Id;
				bto.Codiceistanza = testata.Codiceistanza;
				bto.FkBtoId = val.Key;
				bto.Costotot = val.Value;

				btoMgr.Insert(bto);
			}
		}

		private void EliminaBto(string idComune, int idContribT)
		{
			string sql = "DELETE FROM O_ICALCOLOCONTRIBT_BTO WHERE IDCOMUNE={0} AND FK_OICCT_ID = {1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCONTRIBT"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCONTRIBT", idContribT));

				cmd.ExecuteNonQuery();
			}
		}

		#endregion

		#region Generazione del dataset del calcolo contributo di urbanizzazione
		public DataSet GeneraDatasetContributoUrbanizzazione(string idComune, int idContribT)
		{
			bool closecnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closecnn = true;
				db.Connection.Open();
			}

			try
			{
				DataSet dsContributo = new DataSet();

				List<KeyValuePair<string, string>> destinazioni = GetListaDestinazioni(idComune, idContribT);

				DataTable dt = new DataTable("ContributoUrbanizzazione");

				dt.Columns.Add(new DataColumn("IdDestinazione", typeof(int)));
				dt.Columns.Add(new DataColumn("Destinazione", typeof(string)));
				dt.Columns.Add(new DataColumn("Cubatura", typeof(double)));

				List<int> tipiOnere = GetListaTipiOnere(idComune, idContribT);

				for (int i = 0; i < tipiOnere.Count; i++)
				{
					dt.Columns.Add(new DataColumn( tipiOnere[i].ToString() + "_costom", typeof(string)));
					dt.Columns.Add(new DataColumn(tipiOnere[i].ToString() + "_riduzione", typeof(string)));
					dt.Columns.Add(new DataColumn(tipiOnere[i].ToString() + "_costoTot", typeof(string)));
					dt.Columns.Add(new DataColumn(tipiOnere[i].ToString() + "_percriduzione", typeof(string)));
				}

				// inserisco una riga che conterrà le intestazioni
				DataRow drTemp = dt.NewRow();
				drTemp["IdDestinazione"] = -1;
				drTemp["Destinazione"] = "";
				drTemp["Cubatura"] = -1;

				for (int i = 0; i < tipiOnere.Count; i++)
				{
					drTemp[tipiOnere[i].ToString() + "_costom"] = "Costo al metro";
					drTemp[tipiOnere[i].ToString() + "_costoTot"] = "Costo totale";
					drTemp[tipiOnere[i].ToString() + "_riduzione"] = "Variazione";
					drTemp[tipiOnere[i].ToString() + "_percriduzione"] = "";
				}

				dt.Rows.Add(drTemp);

				// inserimanto delle righe
				foreach (KeyValuePair<string, string> dest in destinazioni)
				{
					DataRow dr = dt.NewRow();

					int idDestinazione = Convert.ToInt32(dest.Key);

					dr["IdDestinazione"]	= idDestinazione;
					dr["Destinazione"]		= dest.Value;
					dr["Cubatura"]			= GetCubaturaPerDestinazione(idComune, idContribT, Convert.ToInt32(dest.Key));

					for (int i = 0; i < tipiOnere.Count; i++)
					{
						GetImportiPerDestinazioneTipoOnereResult val = GetImportiPerDestinazioneTipoOnere(idComune, idContribT, idDestinazione, tipiOnere[i]);

						dr[tipiOnere[i].ToString() + "_costom"] = val.Costom;
						dr[tipiOnere[i].ToString() + "_costoTot"] = val.CostoTot;
						dr[tipiOnere[i].ToString() + "_riduzione"] = val.Riduzione;
						dr[tipiOnere[i].ToString() + "_percriduzione"] = val.RiduzionePerc;
					}

					dt.Rows.Add(dr);
				}

				dsContributo.Tables.Add(dt);

				return dsContributo;
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

		private double GetCubaturaPerDestinazione(string idComune, int idContribT, int idDestinazione)
		{
			string sql = @"Select 
							  Sum(O_ICALCOLO_DETTAGLIOT.TOTALE) 
							From 
							  O_ICALCOLO_DETTAGLIOT,
							  O_ICALCOLOCONTRIBT 
							WHERE
							  O_ICALCOLO_DETTAGLIOT.IDCOMUNE  = O_ICALCOLOCONTRIBT.IDCOMUNE and
							  O_ICALCOLO_DETTAGLIOT.FK_OIC_ID = O_ICALCOLOCONTRIBT.FK_OICT_ID AND
							  O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
							  O_ICALCOLOCONTRIBT.ID = {1} and
							  O_ICALCOLO_DETTAGLIOT.FK_ODE_ID = {2}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IdComune"),
												db.Specifics.QueryParameterName("IdContribT"),
												db.Specifics.QueryParameterName("IdDestinazione"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
				cmd.Parameters.Add(db.CreateParameter("IdContribT", idContribT));
				cmd.Parameters.Add(db.CreateParameter("IdDestinazione", idDestinazione));

				object val = cmd.ExecuteScalar();

				if (val == null || val == DBNull.Value) return 0.0d;

				return Convert.ToDouble(val);
			}
		}

		internal List<int> GetListaTipiOnere(string idComune, int idContribT)
		{
			string sql = @"SELECT 
							  FK_OTO_ID AS id
							FROM 
							  O_ICALCOLOCONTRIBR
							WHERE 
							  O_ICALCOLOCONTRIBR.IDCOMUNE = {0} AND
							  O_ICALCOLOCONTRIBR.FK_OICCT_ID = {1}
							GROUP BY 
							  FK_OTO_ID";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IdComune"),
										db.Specifics.QueryParameterName("IdContribT"));
			
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("IdContribT", idContribT));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						List<int> idOneri = new List<int>();

						while (dr.Read())
						{
							idOneri.Add(Convert.ToInt32(dr["id"]));
						}

						return idOneri;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}



		protected class GetImportiPerDestinazioneTipoOnereResult
		{
			private double m_costom;
			private double m_costoTot;
			private double m_riduzionePerc;
			private double m_riduzione;

			public double Riduzione
			{
				get { return m_riduzione; }
				set { m_riduzione = value; }
			}

			public double RiduzionePerc
			{
				get { return m_riduzionePerc; }
				set { m_riduzionePerc = value; }
			}

			public double CostoTot
			{
				get { return m_costoTot; }
				set { m_costoTot = value; }
			}



			public double Costom
			{
				get { return m_costom; }
				set { m_costom = value; }
			}

		}


		private GetImportiPerDestinazioneTipoOnereResult GetImportiPerDestinazioneTipoOnere(string idComune, int idContribT, int idDestinazione, int idTipoOnere)
		{
			string sql = @"SELECT 
							  costom,
							  costotot,
							  riduzioneperc,
							  riduzione
							from
							  O_ICALCOLOCONTRIBR
							WHERE
							  O_ICALCOLOCONTRIBR.IDCOMUNE = {0} AND
							  O_ICALCOLOCONTRIBR.FK_OICCT_ID = {1} AND
							  O_ICALCOLOCONTRIBR.FK_ODE_ID = {2} AND
							  O_ICALCOLOCONTRIBR.FK_OTO_ID = {3}";

			sql = string.Format(sql, db.Specifics.QueryParameterName("IdComune"),
										db.Specifics.QueryParameterName("IdContribT"),
										db.Specifics.QueryParameterName("IdDestinazione"),
										db.Specifics.QueryParameterName("IdTipoOnere"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
				cmd.Parameters.Add(db.CreateParameter("IdContribT", idContribT));
				cmd.Parameters.Add(db.CreateParameter("IdDestinazione", idDestinazione));
				cmd.Parameters.Add(db.CreateParameter("IdTipoOnere", idTipoOnere));

				using (IDataReader dr = cmd.ExecuteReader())
				{
					if (dr.Read())
					{
						GetImportiPerDestinazioneTipoOnereResult ret = new GetImportiPerDestinazioneTipoOnereResult();

						ret.Costom = Convert.ToDouble(dr["costom"]);
						ret.CostoTot = Convert.ToDouble(dr["costotot"]);
						ret.RiduzionePerc = dr["riduzioneperc"] == DBNull.Value ? 0.0d : Convert.ToDouble(dr["riduzioneperc"]);
						ret.Riduzione = dr["riduzione"] == DBNull.Value ? 0.0d : Convert.ToDouble(dr["riduzione"]);

						return ret;
					}

					return null;
				}
			}

		}

		public List<KeyValuePair<string, string>> GetListaDestinazioni(string idComune, int idContribt)
		{
			string sql = @"SELECT 
							  O_DESTINAZIONI.ID as id,
							  O_DESTINAZIONI.DESTINAZIONE as destinazione
							FROM 
							  O_ICALCOLOCONTRIBR,
							  O_DESTINAZIONI
							WHERE 
							  O_DESTINAZIONI.IDCOMUNE = O_ICALCOLOCONTRIBR.IDCOMUNE and
							  O_DESTINAZIONI.ID       = O_ICALCOLOCONTRIBR.FK_ODE_ID AND
							  O_ICALCOLOCONTRIBR.IDCOMUNE = {0} AND
							  O_ICALCOLOCONTRIBR.FK_OICCT_ID = {1}
							GROUP BY 
							  O_DESTINAZIONI.ID,
							  O_DESTINAZIONI.DESTINAZIONE";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IdComune"),
										db.Specifics.QueryParameterName("IdContribT"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
				cmd.Parameters.Add(db.CreateParameter("IdContribT", idContribt));

				using (IDataReader dr = cmd.ExecuteReader())
				{
					List<KeyValuePair<string, string>> dest = new List<KeyValuePair<string, string>>();

					while (dr.Read())
					{
						string key = dr["id"].ToString();
						string val = dr["destinazione"].ToString();

						dest.Add(new KeyValuePair<string, string>(key, val));
					}

					return dest;
				}
			}
		}
		#endregion

		private string GetSoftwareDaContribT( string idComune , int idContribT )
		{
			string sql = @"SELECT 
							  istanze.software
							FROM
							  istanze,
							  o_icalcolocontribt
							WHERE
							  istanze.idcomune = o_icalcolocontribt.idcomune AND
							  istanze.codiceistanza = o_icalcolocontribt.codiceistanza AND
							  o_icalcolocontribt.Idcomune = {0} AND
							  o_icalcolocontribt.Id = {1}";

			sql = String.Format( sql , db.Specifics.QueryParameterName( "IDCOMUNE" ),
										db.Specifics.QueryParameterName( "IDCONTRIBT" ) );

			bool closecnn = false;

			if ( db.Connection.State == ConnectionState.Closed )
			{
				closecnn = true;
				db.Connection.Open();
			}

			try
			{
				using(IDbCommand cmd = db.CreateCommand( sql ) )
				{
					cmd.Parameters.Add( db.CreateParameter( "IDCOMUNE" , idComune ) );
					cmd.Parameters.Add( db.CreateParameter( "IDCONTRIBT" , idContribT ) );

					object software = cmd.ExecuteScalar();

					if ( software == null || software == DBNull.Value )
						throw new ArgumentException( "Impossibile ricavare il software per o_icalcolocontribt.Id = " + idContribT.ToString() );

					return software.ToString();
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

		#region lettura dei valori delle combo per i dettagli del calcolo
		public string GetDescrizioneComboZonaOmogenea(string idComune, int idContribT)
		{
			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue) == int.MinValue)
				return String.Empty;

			TipiAree ta = new TipiAreeMgr(db).GetById(idComune, cfg.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue));

			if (ta == null)
				return string.Empty;

			return ta.Tipoarea;
		}

		public string GetDescrizioneComboZonaPrg(string idComune, int idContribT)
		{
			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue) == int.MinValue)
				return String.Empty;

			TipiAree ta = new TipiAreeMgr(db).GetById(idComune, cfg.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue));

			if (ta == null)
				return string.Empty;

			return ta.Tipoarea;
		}


		public List<KeyValuePair<string, string>> GetValoriComboZonaOmogenea(string idComune, int idContribT)
		{
			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue) == int.MinValue)
				return new List<KeyValuePair<string, string>>();

			string sql = @"Select distinct
								AREE.CodiceArea as id,
								AREE.Denominazione as descrizione
						  from 
								O_TABELLAABC,
								AREE,
								O_DESTINAZIONI,
								O_ICALCOLOCONTRIBT,
								O_ICALCOLOTOT 
						  Where 
								AREE.IDCOMUNE   = O_TABELLAABC.IDCOMUNE and 
								AREE.CODICEAREA = O_TABELLAABC.FK_AREE_CODICEAREA_ZTO AND
								O_TABELLAABC.IDCOMUNE  = O_DESTINAZIONI.IDCOMUNE AND
								O_TABELLAABC.FK_ODE_ID = O_DESTINAZIONI.ID and 
								O_TABELLAABC.FK_OVC_ID = O_ICALCOLOTOT.FK_OVC_ID AND	
								O_DESTINAZIONI.IDCOMUNE     = O_ICALCOLOCONTRIBT.IDCOMUNE AND
								O_DESTINAZIONI.FK_OCCBDE_ID = O_ICALCOLOCONTRIBT.FK_OCCBDE_ID AND
								O_ICALCOLOTOT.IDCOMUNE		= O_ICALCOLOCONTRIBT.IDCOMUNE AND
								O_ICALCOLOTOT.ID			= O_ICALCOLOCONTRIBT.FK_OICT_ID AND  
								O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
								O_ICALCOLOCONTRIBT.ID = {1} and  
								AREE.CODICETIPOAREA = {2} 
						  order by AREE.Denominazione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("idContribT"),
										db.Specifics.QueryParameterName("cfgTipoArea"));

						bool closecnn = false;

			if ( db.Connection.State == ConnectionState.Closed )
			{
				closecnn = true;
				db.Connection.Open();
			}

			try
			{
				using(IDbCommand cmd = db.CreateCommand( sql ) )
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idContribT", idContribT));
					cmd.Parameters.Add(db.CreateParameter("cfgTipoArea", cfg.FkTipiareeCodiceZto));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

						while (dr.Read())
						{
							string key = dr["id"].ToString();
							string val = dr["descrizione"].ToString();

							ret.Add(new KeyValuePair<string, string>(key, val));
						}

						return ret;
					}
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

		public List<KeyValuePair<string, string>> GetValoriComboZonaPrg(string idComune, int idContribT , int idZonaOmogenea)
		{
			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue) == int.MinValue)
				return new List<KeyValuePair<string, string>>();

			string sql = @"Select distinct
								AREE.CodiceArea as id,
								AREE.Denominazione as descrizione
							from 
								O_TABELLAABC,
								AREE,
								O_DESTINAZIONI,
								O_ICALCOLOCONTRIBT 
							Where 
								AREE.IDCOMUNE   = O_TABELLAABC.IDCOMUNE and 
								AREE.CODICEAREA = O_TABELLAABC.FK_AREE_CODICEAREA_PRG AND
								O_TABELLAABC.IDCOMUNE  = O_DESTINAZIONI.IDCOMUNE AND
								O_TABELLAABC.FK_ODE_ID = O_DESTINAZIONI.ID and 
								O_DESTINAZIONI.IDCOMUNE     = O_ICALCOLOCONTRIBT.IDCOMUNE AND
								O_DESTINAZIONI.FK_OCCBDE_ID = O_ICALCOLOCONTRIBT.FK_OCCBDE_ID AND
								O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
								O_ICALCOLOCONTRIBT.ID = {1} and  
								AREE.CODICETIPOAREA = {2}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("idContribT"),
										db.Specifics.QueryParameterName("cfgTipoArea"));


			if (idZonaOmogenea > 0)
				sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_ZTO = " + db.Specifics.QueryParameterName("IdZonaOmogenea");

			sql += " order by AREE.Denominazione asc";

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
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idContribT", idContribT));
					cmd.Parameters.Add(db.CreateParameter("cfgTipoArea", cfg.FkTipiareeCodicePrg));

					if (idZonaOmogenea > 0)
						cmd.Parameters.Add(db.CreateParameter("IdZonaOmogenea", idZonaOmogenea));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

						while (dr.Read())
						{
							string key = dr["id"].ToString();
							string val = dr["descrizione"].ToString();

							ret.Add(new KeyValuePair<string, string>(key, val));
						}

						return ret;
					}
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}
		
		
		public List<KeyValuePair<string, string>> GetValoriComboTipoIntervento(string idComune, int idContribT, int idZonaOmogenea , int idZonaPrg)
		{
			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

			string sql = @"Select distinct
							O_INTERVENTI.ID AS id,
							O_INTERVENTI.INTERVENTO AS descrizione ,
							O_INTERVENTI.ORDINAMENTO
						from 
							O_TABELLAABC,
							O_INTERVENTI,
							O_DESTINAZIONI,
							O_ICALCOLOCONTRIBT,
                            O_ICALCOLOTOT 
						Where 
							O_INTERVENTI.IDCOMUNE  = O_TABELLAABC.IDCOMUNE and 
							O_INTERVENTI.ID        = O_TABELLAABC.FK_OIN_ID AND
							O_TABELLAABC.IDCOMUNE  = O_DESTINAZIONI.IDCOMUNE AND
							O_TABELLAABC.FK_ODE_ID = O_DESTINAZIONI.ID and 
							O_TABELLAABC.FK_OVC_ID = O_ICALCOLOTOT.FK_OVC_ID AND							
							O_DESTINAZIONI.IDCOMUNE     = O_ICALCOLOCONTRIBT.IDCOMUNE AND
							O_DESTINAZIONI.FK_OCCBDE_ID = O_ICALCOLOCONTRIBT.FK_OCCBDE_ID AND
                            O_ICALCOLOTOT.IDCOMUNE		= O_ICALCOLOCONTRIBT.IDCOMUNE AND
                            O_ICALCOLOTOT.ID			= O_ICALCOLOCONTRIBT.FK_OICT_ID AND                            
							O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
							O_ICALCOLOCONTRIBT.ID = {1} ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("idContribT") );


			if (idZonaOmogenea > 0)
				sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_ZTO = " + db.Specifics.QueryParameterName("IdZonaOmogenea");
			
			if (idZonaPrg > 0)
				sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_PRG = " + db.Specifics.QueryParameterName("IdZonaPrg");

			sql += " order by O_INTERVENTI.ORDINAMENTO ASC";

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
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idContribT", idContribT));

					if (idZonaOmogenea > 0)
						cmd.Parameters.Add(db.CreateParameter("IdZonaOmogenea", idZonaOmogenea));

					if (idZonaPrg > 0)
						cmd.Parameters.Add(db.CreateParameter("IdZonaPrg", idZonaPrg));


					using (IDataReader dr = cmd.ExecuteReader())
					{
						List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

						while (dr.Read())
						{
							string key = dr["id"].ToString();
							string val = dr["descrizione"].ToString();

							ret.Add(new KeyValuePair<string, string>(key, val));
						}

						return ret;
					}
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}


		public List<KeyValuePair<string, string>> GetValoriComboIndiciTerritoriali(string idComune, int idContribT, int idZonaOmogenea, int idZonaPrg , int idIntervento)
		{
			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

			string sql = @"Select distinct
								o_indiciterritoriali.ID AS id,
								o_indiciterritoriali.Descrizione AS Descrizione
							from 
								O_TABELLAABC,
								o_indiciterritoriali,                                  
								O_DESTINAZIONI,
								O_ICALCOLOCONTRIBT,
								O_ICALCOLOTOT  
							Where 
								o_indiciterritoriali.IDCOMUNE   = O_TABELLAABC.IDCOMUNE and 
								o_indiciterritoriali.ID         = O_TABELLAABC.FK_OIT_ID AND
								O_TABELLAABC.IDCOMUNE		= O_DESTINAZIONI.IDCOMUNE AND
								O_TABELLAABC.FK_ODE_ID		= O_DESTINAZIONI.ID and 
								O_TABELLAABC.FK_OVC_ID		= O_ICALCOLOTOT.FK_OVC_ID and
								O_ICALCOLOTOT.IDCOMUNE		= O_ICALCOLOCONTRIBT.IDCOMUNE AND
								O_ICALCOLOTOT.ID			= O_ICALCOLOCONTRIBT.FK_OICT_ID AND
								O_DESTINAZIONI.IDCOMUNE     = O_ICALCOLOCONTRIBT.IDCOMUNE AND
								O_DESTINAZIONI.FK_OCCBDE_ID = O_ICALCOLOCONTRIBT.FK_OCCBDE_ID AND
								O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
								O_ICALCOLOCONTRIBT.ID = {1}"; 
							

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("idContribT"));


			if (idZonaOmogenea > 0)
				sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_ZTO = " + db.Specifics.QueryParameterName("IdZonaOmogenea");

			if (idZonaPrg > 0)
				sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_PRG = " + db.Specifics.QueryParameterName("IdZonaPrg");

			if (idIntervento > 0)
				sql += " AND O_TABELLAABC.FK_OIN_ID = " + db.Specifics.QueryParameterName("IdTipoIntervento");

			sql += " order by o_indiciterritoriali.Descrizione asc";

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
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idContribT", idContribT));

					if (idZonaOmogenea > 0)
						cmd.Parameters.Add(db.CreateParameter("IdZonaOmogenea", idZonaOmogenea));

					if (idZonaPrg > 0)
						cmd.Parameters.Add(db.CreateParameter("IdZonaPrg", idZonaPrg));

					if (idIntervento > 0)
						cmd.Parameters.Add(db.CreateParameter("IdTipoIntervento", idIntervento));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

						while (dr.Read())
						{
							string key = dr["id"].ToString();
							string val = dr["Descrizione"].ToString();

							ret.Add(new KeyValuePair<string, string>(key, val));
						}

						return ret;
					}
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}



		public List<KeyValuePair<string, string>> GetValoriComboInterventiTabd(string idComune, int idContribT )
		{
			OICalcoloContribT testata = GetById(idComune, idContribT);
			OICalcoloTot calcoloTot = new OICalcoloTotMgr(db).GetById(testata.Idcomune, testata.FkOictId.GetValueOrDefault(int.MinValue));

			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

			string sql = @"Select distinct
							O_INTERVENTI.ID AS id,
							O_INTERVENTI.INTERVENTO AS DESCRIZIONE,
							O_INTERVENTI.ORDINAMENTO
						from 
							O_TABELLAD,
							O_INTERVENTI,                                  
							O_DESTINAZIONI,
							O_ICALCOLOCONTRIBT 
						Where 
							O_INTERVENTI.IDCOMUNE   = O_TABELLAD.IDCOMUNE and 
							O_INTERVENTI.ID         = O_TABELLAD.FK_OIN_ID AND
							O_TABELLAD.IDCOMUNE  = O_DESTINAZIONI.IDCOMUNE AND
							O_TABELLAD.FK_ODE_ID = O_DESTINAZIONI.ID and 
							O_DESTINAZIONI.IDCOMUNE     = O_ICALCOLOCONTRIBT.IDCOMUNE AND
							O_DESTINAZIONI.FK_OCCBDE_ID = O_ICALCOLOCONTRIBT.FK_OCCBDE_ID AND
							O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
							O_ICALCOLOCONTRIBT.ID		= {1} AND
							O_TABELLAD.FK_OVC_ID		= {2} order by O_INTERVENTI.ORDINAMENTO ASC";


			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("idContribT"),
										db.Specifics.QueryParameterName("idValiditaCoeff"));

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
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idContribT", idContribT));
					cmd.Parameters.Add(db.CreateParameter("idValiditaCoeff", calcoloTot.FkOvcId));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

						while (dr.Read())
						{
							string key = dr["id"].ToString();
							string val = dr["descrizione"].ToString();

							ret.Add(new KeyValuePair<string, string>(key, val));
						}

						return ret;
					}
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

		public List<KeyValuePair<string, string>> GetValoriComboClassiAddetti(string idComune, int idContribT , int idIntervento )
		{
			OICalcoloContribT testata = GetById(idComune, idContribT);
            OICalcoloTot calcoloTot = new OICalcoloTotMgr(db).GetById(testata.Idcomune, testata.FkOictId.GetValueOrDefault(int.MinValue));

			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, GetSoftwareDaContribT(idComune, idContribT));

			string sql = @"Select distinct
							O_CLASSIADDETTI.ID AS id,
							O_CLASSIADDETTI.CLASSE AS DESCRIZIONE,
							O_CLASSIADDETTI.ORDINAMENTO
						from 
							O_TABELLAD,
							O_CLASSIADDETTI,                                  
							O_DESTINAZIONI,
							O_ICALCOLOCONTRIBT 
						Where 
							O_CLASSIADDETTI.IDCOMUNE   = O_TABELLAD.IDCOMUNE and 
							O_CLASSIADDETTI.ID         = O_TABELLAD.FK_OCA_ID AND
							O_TABELLAD.IDCOMUNE  = O_DESTINAZIONI.IDCOMUNE AND
							O_TABELLAD.FK_ODE_ID = O_DESTINAZIONI.ID and 
							O_DESTINAZIONI.IDCOMUNE     = O_ICALCOLOCONTRIBT.IDCOMUNE AND
							O_DESTINAZIONI.FK_OCCBDE_ID = O_ICALCOLOCONTRIBT.FK_OCCBDE_ID AND
							O_ICALCOLOCONTRIBT.IDCOMUNE = {0} AND
							O_ICALCOLOCONTRIBT.ID		= {1} AND
							O_TABELLAD.FK_OVC_ID		= {2}";
 

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("idContribT"),
										db.Specifics.QueryParameterName("idValiditaCoeff"));

			if (idIntervento > 0)
				sql += " and O_TABELLAD.FK_OIN_ID = " + db.Specifics.QueryParameterName("IdIntervento");

			sql += " order by O_CLASSIADDETTI.ORDINAMENTO ASC";

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
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idContribT", idContribT));
					cmd.Parameters.Add(db.CreateParameter("idValiditaCoeff", calcoloTot.FkOvcId ) );

					if (idIntervento > 0)
						cmd.Parameters.Add(db.CreateParameter("IdIntervento", idIntervento));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

						while (dr.Read())
						{
							string key = dr["id"].ToString();
							string val = dr["descrizione"].ToString();

							ret.Add(new KeyValuePair<string, string>(key, val));
						}

						return ret;
					}
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

		



		#endregion

        private void EffettuaCancellazioneACascata(OICalcoloContribT cls)
        {
            OICalcoloContribTBTO a = new OICalcoloContribTBTO();
            a.Idcomune = cls.Idcomune;
            a.FkOicctId = cls.Id;

            List<OICalcoloContribTBTO> lCalcoloBTO = new OICalcoloContribTBTOMgr(db).GetList(a);
            foreach (OICalcoloContribTBTO calcoloBTO in lCalcoloBTO)
            {
                OICalcoloContribTBTOMgr mgr = new OICalcoloContribTBTOMgr(db);
                mgr.Delete(calcoloBTO);
            }

            OICalcoloContribR b = new OICalcoloContribR();
            b.Idcomune = cls.Idcomune;
            b.FkOicctId = cls.Id;

            List<OICalcoloContribR> lCalcoloR = new OICalcoloContribRMgr(db).GetList(b);
            foreach (OICalcoloContribR calcoloR in lCalcoloR)
            {
                OICalcoloContribRMgr mgr = new OICalcoloContribRMgr(db);
                mgr.Delete(calcoloR);
            }
        }
	}
}
