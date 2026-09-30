using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using PersonalLib2.Sql;
using System.Data;
using Init.SIGePro.Authentication;
using PersonalLib2.Data;
using System.ComponentModel;
using Init.SIGePro.Utils;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class CCICalcoloTotMgr
	{
		public void IntegraForeignKey(CCICalcoloTot cls)
		{
			CCICalcoloTContributo filtro = new CCICalcoloTContributo();
			filtro.Idcomune = cls.Idcomune;
			filtro.FkCcictId = cls.Id;
			filtro.Codiceistanza = cls.Codiceistanza;

			List<CCICalcoloTContributo> contr = new CCICalcoloTContributoMgr(db).GetList(filtro);

			for (int i = 0; i < contr.Count; i++)
			{
				if (contr[i].Stato == "A")
					cls.StatoAttuale = contr[i];
				else if (contr[i].Stato == "P")
					cls.StatoDiProgetto = contr[i];
			}
		}

		public CCICalcoloTot GetByIdICalcolo(string idComune, int idICalcoli)
		{
			CCICalcoloTContributo tcontr = new CCICalcoloTContributo();
			tcontr.Idcomune = idComune;
			tcontr.FkCcicId = idICalcoli;

			tcontr = (CCICalcoloTContributo)db.GetClass( tcontr );

			if (tcontr == null ) return null;

			return GetById( idComune , tcontr.FkCcictId.Value );
		}

		public List<CCICalcoloTot> GetList(string idComune, int codiceIstanza)
		{
			CCICalcoloTot c = new CCICalcoloTot();
			c.Idcomune = idComune;
			c.Codiceistanza = codiceIstanza;
			c.OrderBy = " ID ASC ";

			return GetList(c);
		}

		public List<CCBaseTipoCalcolo> GetTipiCalcolo(string idComune, string software, string ccBaseTipoInterventoId, string occBaseDestinazioniId)
		{
			CCDetermTipoCalcoloMgr detMgr = new CCDetermTipoCalcoloMgr(db);
			CCBaseTipoCalcoloMgr btcMgr = new CCBaseTipoCalcoloMgr(db);

			List<CCDetermTipoCalcolo> tipiCalcoloId = detMgr.GetList(idComune, int.MinValue, ccBaseTipoInterventoId, occBaseDestinazioniId, software, null);

			List<CCBaseTipoCalcolo> ret = new List<CCBaseTipoCalcolo>();

			tipiCalcoloId.ForEach(delegate(CCDetermTipoCalcolo tcId)
									{
										ret.Add(btcMgr.GetById(tcId.FkCcbtcId));
									});

			return ret;
		}

		public double CalcolaQuotaContribTotale(CCICalcoloTot cls)
		{
			CCICalcoloTContributoMgr mgr = new CCICalcoloTContributoMgr(this.db);

            double c1 = mgr.CalcoloContributo(cls.Idcomune, cls.Id.Value, cls.Codiceistanza.Value, "P");
            double c2 = mgr.CalcoloContributo(cls.Idcomune, cls.Id.Value, cls.Codiceistanza.Value, "A");

			return c1 - c2;
		}

		public override DataClass ChildDataIntegrations(DataClass clsTmp)
		{
			CCICalcoloTot cls = (CCICalcoloTot)clsTmp;

			if (cls.StatoDiProgetto == null )
			{
				cls.StatoDiProgetto = new CCICalcoloTContributo();
				cls.StatoDiProgetto.CostocEdificio = 0.0f;
				cls.StatoDiProgetto.Coefficiente = 0.0f;

			}


			if (cls.StatoDiProgetto != null)
			{
				cls.StatoDiProgetto.Idcomune = cls.Idcomune;
				cls.StatoDiProgetto.Codiceistanza = cls.Codiceistanza;
				cls.StatoDiProgetto.FkCcictId = cls.Id;
				cls.StatoDiProgetto.Stato = "P";
			}





			if (cls.StatoAttuale == null &&  (cls.FkBcctcId == "M12" || cls.FkBcctcId == "P12" ) )
			{
				cls.StatoAttuale = new CCICalcoloTContributo();
				cls.StatoAttuale.CostocEdificio = 0.0f;
				cls.StatoAttuale.Coefficiente = 0.0f;
			}

			if (cls.StatoAttuale != null)
			{
				cls.StatoAttuale.Idcomune = cls.Idcomune;
				cls.StatoAttuale.Codiceistanza = cls.Codiceistanza;
				cls.StatoAttuale.FkCcictId = cls.Id;
				cls.StatoAttuale.Stato = "A";
			}

			return cls;
		}

		public CCICalcoloTContributo GetStatoDiProgetto(CCICalcoloTot cls)
		{
			IntegraForeignKey(cls);

			return cls.StatoDiProgetto;
		}

		public CCICalcoloTContributo GetStatoAttuale(CCICalcoloTot cls)
		{
			IntegraForeignKey(cls);

			return cls.StatoAttuale;
		}

		private CCICalcoloTot DataIntegrations(CCICalcoloTot cls)
		{
            if (cls.QuotacontribTotale.GetValueOrDefault(float.MinValue) == float.MinValue)
				cls.QuotacontribTotale = 0.0f;//CalcolaQuotaContribTotale(cls);
			
			return cls;
		}

		private CCICalcoloTot ChildInsert(CCICalcoloTot cls)
		{
			CCICalcoloTContributoMgr mgrContributo = new CCICalcoloTContributoMgr(db);

            if (cls.StatoDiProgetto != null && cls.StatoDiProgetto.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
				mgrContributo.Insert(cls.StatoDiProgetto);


            if (cls.StatoAttuale != null && cls.StatoAttuale.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
				mgrContributo.Insert(cls.StatoAttuale);

			return cls;
		}


		public void Delete(CCICalcoloTot cls)
		{
			var wasInTransaction = db.IsInTransaction;

			try
			{
				if (!wasInTransaction)
					db.BeginTransaction();

				VerificaRecordCollegati(cls);

				EffettuaCancellazioneACascata(cls);

				db.Delete(cls);

				if (!wasInTransaction)
					db.CommitTransaction();
			}
			catch (Exception ex)
			{
				if (!wasInTransaction)
					db.RollbackTransaction();

				throw;
			}
		}

        private void EffettuaCancellazioneACascata(CCICalcoloTot cls)
        {
            CCICalcoloTContributo c = new CCICalcoloTContributo();
            c.Idcomune = cls.Idcomune;
            c.FkCcictId = cls.Id;

            List<CCICalcoloTContributo> lCalcoloT = new CCICalcoloTContributoMgr(db).GetList(c);
            foreach (CCICalcoloTContributo calcoloT in lCalcoloT)
            {
                CCICalcoloTContributoMgr mgr = new CCICalcoloTContributoMgr(db);
                mgr.Delete(calcoloT);       
            }
        }

		#region metodi per la lettura di altre tabelle a partire dall'idcalcolotot

		[DataObjectMethod( DataObjectMethodType.Select )]
		public static DataTable GetDestinazioni(string token, int idCalcoloTot)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);
			DataBase db = authInfo.CreateDatabase();

			/* ---------------------------prima query 
				ATTENZIONE se questa query viene modificata molto probabilmente dovrà essere modificata anche 
				la query del metodo GetTipiInterventoDaIdDestinazione
			 */
			string sql1 = @"Select
								distinct VW_CCICT_DESTINAZIONI.CCDE_ID as ID,VW_CCICT_DESTINAZIONI.DESTINAZIONE as DESCRIZIONE
							From
								CC_COEFFCONTRIBUTO, VW_CCICT_DESTINAZIONI, VW_CCICT_TIPIINTERVENTO, CC_ICALCOLOTOT
							Where
								VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
								VW_CCICT_TIPIINTERVENTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_TIPIINTERVENTO.CCICT_ID = CC_ICALCOLOTOT.ID and
								CC_COEFFCONTRIBUTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								CC_COEFFCONTRIBUTO.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
								CC_COEFFCONTRIBUTO.FK_CCTI_ID = VW_CCICT_TIPIINTERVENTO.CCTI_ID and
								CC_COEFFCONTRIBUTO.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
								CC_ICALCOLOTOT.IDCOMUNE = {0} and
								CC_ICALCOLOTOT.ID = {1} Order by VW_CCICT_DESTINAZIONI.DESTINAZIONE asc";

			sql1 = String.Format(sql1, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLO"));

			/* ---------------------------seconda query (da usare solo se la prima non ha ritornato records) */
			string sql2 = @"Select
								distinct VW_CCICT_DESTINAZIONI.CCDE_ID as ID,VW_CCICT_DESTINAZIONI.DESTINAZIONE as DESCRIZIONE
							From
								CC_COEFFCONTRIBUTO, VW_CCICT_DESTINAZIONI, CC_ICALCOLOTOT
							Where
								VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
								CC_COEFFCONTRIBUTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								CC_COEFFCONTRIBUTO.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
								CC_COEFFCONTRIBUTO.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
								CC_ICALCOLOTOT.IDCOMUNE = {0} and
								CC_ICALCOLOTOT.ID = {1} Order by VW_CCICT_DESTINAZIONI.DESTINAZIONE asc";

			sql2 = String.Format(sql2, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLO"));

			/* ---------------------------terza query (il risultato di questa query va comunque accodato al risultato della prima o della seconda) */
			string sql3 = @"Select
								distinct VW_CCICT_DESTINAZIONI.CCDE_ID as ID,VW_CCICT_DESTINAZIONI.DESTINAZIONE as DESCRIZIONE
							From
								CC_COEFFCONTRIB_ATTIVITA, VW_CCICT_DESTINAZIONI, CC_ICALCOLOTOT
							Where
								VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
								CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
								CC_ICALCOLOTOT.IDCOMUNE = {0} and
								CC_ICALCOLOTOT.ID = {1} Order by VW_CCICT_DESTINAZIONI.DESTINAZIONE asc";

			sql3 = String.Format(sql3, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLO"));

			DataSet dsRet = new DataSet();

			using (IDbCommand cmd = db.CreateCommand(sql1))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", authInfo.IdComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLO", idCalcoloTot));

				IDataAdapter adp = null;

				adp = db.CreateDataAdapter(cmd);
				adp.Fill(dsRet);


				// Se la prima query non ha trovato risultati provo con la seconda
				if (dsRet.Tables[0].Rows.Count == 0)
				{
					cmd.CommandText = sql2;

					adp = db.CreateDataAdapter(cmd);
					adp.Fill(dsRet);
				}


				// Accodo comunque i risultati della terza query
				cmd.CommandText = sql3;

				bool closeConnection = false;

				if (db.Connection.State == ConnectionState.Closed)
				{
					closeConnection = true;
					db.Connection.Open();
				}

				try
				{
					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							string id = dr["id"].ToString();
							string descrizione = dr["descrizione"].ToString();

							DataRow[] drs = dsRet.Tables[0].Select(" id = " + id);

							if (drs == null || drs.Length > 0) continue;

							DataRow row = dsRet.Tables[0].NewRow();
							row["id"] = id;
							row["descrizione"] = descrizione;

							dsRet.Tables[0].Rows.Add(row);
						}
					}
				}finally
				{
					if (closeConnection)
						db.Connection.Close();
				}


				//adp = db.CreateDataAdapter(cmd);
				//adp.Fill(dsRet);
			}


			dsRet.Tables[0].DefaultView.Sort = "ID";

			return dsRet.Tables[0];
		}

		[DataObjectMethod(DataObjectMethodType.Select)]
		public static DataTable GetTipiInterventoDaIdDestinazione(string token, int idCalcoloTot, int idDestinazione)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);
			DataBase db = authInfo.CreateDatabase();
			/*
			 * ATTENZIONE se questa query viene modificata molto probabilmente dovrà essere modificata anche 
			 * la query del metodo GetAreeDaIdDestinazioneIdTipoIntervento
			 */
			string sql = @"Select
							distinct 
							VW_CCICT_TIPIINTERVENTO.CCTI_ID as ID,
							VW_CCICT_TIPIINTERVENTO.INTERVENTO as DESCRIZIONE
						From
							CC_COEFFCONTRIBUTO, 
							VW_CCICT_DESTINAZIONI, 
							VW_CCICT_TIPIINTERVENTO, 
							CC_ICALCOLOTOT
						Where
							VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
							VW_CCICT_TIPIINTERVENTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							VW_CCICT_TIPIINTERVENTO.CCICT_ID = CC_ICALCOLOTOT.ID and
							CC_COEFFCONTRIBUTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							CC_COEFFCONTRIBUTO.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
							CC_COEFFCONTRIBUTO.FK_CCTI_ID = VW_CCICT_TIPIINTERVENTO.CCTI_ID and
							CC_COEFFCONTRIBUTO.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
							CC_ICALCOLOTOT.IDCOMUNE = {0} and
							CC_ICALCOLOTOT.ID = {1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			if (idDestinazione > 0)
				sql += " and VW_CCICT_DESTINAZIONI.CCDE_ID = " + db.Specifics.QueryParameterName("IDDESTINAZIONE");

			sql += " order by VW_CCICT_TIPIINTERVENTO.INTERVENTO asc";

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", authInfo.IdComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

				if (idDestinazione > 0)
					cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));

				IDataAdapter adp = db.CreateDataAdapter(cmd);
				DataSet dsRet = new DataSet();

				adp.Fill(dsRet);

				if (dsRet.Tables[0].Rows.Count > 0 && dsRet.Tables[0].Rows[0]["ID"] == DBNull.Value)
					dsRet.Tables[0].Rows[0].Delete();

				return dsRet.Tables[0];
			}

		}

		[DataObjectMethod( DataObjectMethodType.Select )]
		public static DataTable GetAreeDaIdDestinazioneIdTipoIntervento(string token, int idCalcoloTot, int idDestinazione, int idTipoIntervento)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);
			DataBase db = authInfo.CreateDatabase();
			/*
			 * ATTENZIONE se questa query viene modificata molto probabilmente dovrà essere modificata anche 
			 * la query del metodo GetAreeDaIdDestinazioneIdTipoIntervento
			 */
			string sql = @"Select
							distinct AREE.CODICEAREA as ID,
							AREE.DENOMINAZIONE as DESCRIZIONE
						From
							CC_COEFFCONTRIBUTO, 
							VW_CCICT_DESTINAZIONI, 
							VW_CCICT_TIPIINTERVENTO, 
							CC_ICALCOLOTOT,
							AREE
						Where
							AREE.IDCOMUNE = CC_COEFFCONTRIBUTO.IDCOMUNE and
							AREE.CODICEAREA = CC_COEFFCONTRIBUTO.FK_AREE_CODICEAREA and
							VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
							VW_CCICT_TIPIINTERVENTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							VW_CCICT_TIPIINTERVENTO.CCICT_ID = CC_ICALCOLOTOT.ID and
							CC_COEFFCONTRIBUTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							CC_COEFFCONTRIBUTO.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
							CC_COEFFCONTRIBUTO.FK_CCTI_ID = VW_CCICT_TIPIINTERVENTO.CCTI_ID and
							CC_COEFFCONTRIBUTO.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
							CC_ICALCOLOTOT.IDCOMUNE = {0} and
							CC_ICALCOLOTOT.ID = {1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			if (idDestinazione > 0)
				sql += " and VW_CCICT_DESTINAZIONI.CCDE_ID = " + db.Specifics.QueryParameterName("IDDESTINAZIONE");

			if (idTipoIntervento > 0)
				sql += " and CC_COEFFCONTRIBUTO.FK_CCTI_ID = " + db.Specifics.QueryParameterName("IDTIPOINTERVENTO");

			sql += " order by AREE.DENOMINAZIONE asc";

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", authInfo.IdComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

				if (idDestinazione > 0)
					cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));

				if(idTipoIntervento > 0)
					cmd.Parameters.Add(db.CreateParameter("IDTIPOINTERVENTO", idTipoIntervento));

				IDataAdapter adp = db.CreateDataAdapter(cmd);
				DataSet dsRet = new DataSet();

				adp.Fill(dsRet);

				if (dsRet.Tables[0].Rows.Count > 0 && dsRet.Tables[0].Rows[0]["ID"] == DBNull.Value)
					dsRet.Tables[0].Rows[0].Delete();

				return dsRet.Tables[0];
			}
		}

		public float GetCoefficienteDContributo(string idComune, int idCalcoloTot, int idDestinazione, int idTipoIntervento, int idUbicazione,int idAttivita )
		{
			string sql = @"Select
							CC_COEFFCONTRIBUTO.COEFFICIENTE
						From
							CC_COEFFCONTRIBUTO, 
							VW_CCICT_DESTINAZIONI, 
							VW_CCICT_TIPIINTERVENTO, 
							CC_ICALCOLOTOT
						Where
							VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
							VW_CCICT_TIPIINTERVENTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							VW_CCICT_TIPIINTERVENTO.CCICT_ID = CC_ICALCOLOTOT.ID and
							CC_COEFFCONTRIBUTO.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
							CC_COEFFCONTRIBUTO.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
							CC_COEFFCONTRIBUTO.FK_CCTI_ID = VW_CCICT_TIPIINTERVENTO.CCTI_ID and
							CC_COEFFCONTRIBUTO.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
							CC_ICALCOLOTOT.IDCOMUNE = {0} and
							CC_ICALCOLOTOT.ID = {1} ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"));

			if (idDestinazione >= 0)
				sql += " and VW_CCICT_DESTINAZIONI.CCDE_ID = " + db.Specifics.QueryParameterName("IDDESTINAZIONE");

			if (idTipoIntervento >= 0)
				sql += " and CC_COEFFCONTRIBUTO.FK_CCTI_ID = " + db.Specifics.QueryParameterName("IDTIPOINTERVENTO");

			if (idUbicazione >= 0)
				sql += " and CC_COEFFCONTRIBUTO.FK_AREE_CODICEAREA = " + db.Specifics.QueryParameterName("IDUBICAZIONE");
			else
				sql += " and CC_COEFFCONTRIBUTO.FK_AREE_CODICEAREA is null";

			if (idAttivita >= 0)
				sql += " and CC_COEFFCONTRIBUTO.FK_CCCA_ID = " + db.Specifics.QueryParameterName("idAttivita");
			else
				sql += " and CC_COEFFCONTRIBUTO.FK_CCCA_ID is null";

			bool closecnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				db.Connection.Open();
				closecnn = true;
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
					cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

					if (idDestinazione > 0)
						cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));

					if (idTipoIntervento > 0)
						cmd.Parameters.Add(db.CreateParameter("IDTIPOINTERVENTO", idTipoIntervento));

					if (idUbicazione > 0)
						cmd.Parameters.Add(db.CreateParameter("IDUBICAZIONE", idUbicazione));

					if (idAttivita >= 0)
						cmd.Parameters.Add(db.CreateParameter("idAttivita", idAttivita));

					float valore = -1;

					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							// In teoria si dovrebbe leggere un solo valore
							if (valore >= 0)
							{
								string errmsg = "CCICalcoloTotMgr::GetCoefficienteDContributo ha restituito più di una riga. IDCOMUNE={0}, IDCALCOLOTOT={1}, IDDESTINAZIONE={2}, IDUBICAZIONE={3}";
								Logger.LogEvent(db, idComune, "Calcolo oneri", String.Format(errmsg, idComune, idCalcoloTot, idDestinazione, idTipoIntervento, idUbicazione), "ERRORE");

								return valore;
							}
							else
							{
								valore = Convert.ToSingle(rd[0]);
							}
						}
					}

					return valore < 0 ? 0.0f : valore;
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

		#endregion

		public class AliquotePerSuperficie
		{
			public class Aliquota
			{
				double _rapportoStSu = 0.0d;
				double _aliquota = 0.0d;
				double _prezzoTot = 0.0d;

				public string ClasseSuperficie { get; private set; }
				public string RapportoStSu { get { return this._rapportoStSu.ToString("N2"); } }
				public string ValoreAliquota { get { return this._aliquota.ToString("N2"); } }
				public string AliquotaTot { get { return GetAliquota().ToString("N2"); } }
				public string Contributo { get { return GetContributo().ToString("N2"); } }

				public Aliquota(string classeSuperficie, double rapportoStSu, double aliquota, double prezzoTot)
				{
					this.ClasseSuperficie = classeSuperficie;
					this._rapportoStSu = Math.Round(rapportoStSu,2);
					this._aliquota = Math.Round(aliquota,2);
					this._prezzoTot = Math.Round(prezzoTot, 2);
				}

				internal double GetAliquota()
				{
					return this._aliquota * this._rapportoStSu;
				}

				internal double GetContributo()
				{
					return (this._prezzoTot / 100.0d) * GetAliquota();
				}
			}

			List<Aliquota> _righe = new List<Aliquota>();
			double _contributoTotale = 0.0d;
			double _aliquotaTotale = 0.0d;
			double _costEdificioTotale = 0.0d;

			public void AggiungiRiga(string classeSuperficie, double rapportoStSu, double aliquota, double prezzoTot)
			{
				var riga = new Aliquota(classeSuperficie, rapportoStSu, aliquota , prezzoTot );

				this._righe.Add(riga);

				this._contributoTotale += riga.GetContributo();
				this._aliquotaTotale += riga.GetAliquota();
			}

			public void SetCostoTotaleEdificio(double costo)
			{
				this._costEdificioTotale = costo;
			}

			public IEnumerable<Aliquota> Aliquote { get { return this._righe; } }
			
			public string ContributoTotale { get { return this._contributoTotale.ToString("N2"); } }
			public string AliquotaTotale { get { return this._aliquotaTotale.ToString("N2"); } }
			public string CostoEdificioTotale { get { return this._costEdificioTotale.ToString("N2"); } }

			public static AliquotePerSuperficie AliquotaNonTrovata()
			{
				return new AliquotePerSuperficie();
			}
		}

		public enum StatoCalcoloEnum
		{
			Attuale,
			Progetto
		}

		public AliquotePerSuperficie GetAliquotePerClassiSuperficie(string idComune, int idCalcoloTot, StatoCalcoloEnum stato)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = @"SELECT 
								costoc_edificio,
								classe, 
								rapporto_su, 
								aliquota_calcolo_cc, 
								rapporto_su * aliquota_calcolo_cc as aliquota_tot, 
								(costoc_edificio / 100 ) * rapporto_su * aliquota_calcolo_cc as costo_classe
							FROM 
								cc_icalcolo_tcontributo 
									JOIN cc_itabella1 ON
										cc_itabella1.idcomune 	= cc_icalcolo_tcontributo.idcomune AND
										cc_itabella1.fk_ccic_id = cc_icalcolo_tcontributo.fk_ccic_id
									JOIN cc_classisuperfici ON 
										cc_classisuperfici.idcomune = cc_itabella1.idcomune AND
										cc_classisuperfici.id = cc_itabella1.fk_cccs_id 
							WHERE
								cc_icalcolo_tcontributo.idcomune = {0} AND
								cc_icalcolo_tcontributo.fk_ccict_id = {1} AND
								cc_icalcolo_tcontributo.stato = {2}	";

				sql = PreparaQueryParametrica(sql, "idComune", "idCalcoloTot", "stato");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idCalcoloTot", idCalcoloTot));
					cmd.Parameters.Add(db.CreateParameter("stato", stato == StatoCalcoloEnum.Attuale ? "A" : "P"));

					using(var dr = cmd.ExecuteReader())
					{
						var rVal = new AliquotePerSuperficie();

						while (dr.Read())
						{
							var costoTotaleEdificio = SafeNullable<double>(dr["costoc_edificio"]);
							var classe = dr["classe"].ToString();
							var rapportoSu = SafeNullable<double>(dr["rapporto_su"] );
							var aliquotaCalcoloCc = SafeNullable<double>(dr["aliquota_calcolo_cc"]);
							var aliquotaTot = SafeNullable<double>(dr["aliquota_tot"]);
							var costoClasse = SafeNullable<double>(dr["costo_classe"]);

							if (!aliquotaCalcoloCc.HasValue)
								return AliquotePerSuperficie.AliquotaNonTrovata();

							rVal.AggiungiRiga(classe, rapportoSu.Value, aliquotaCalcoloCc.Value, costoTotaleEdificio.Value);
							rVal.SetCostoTotaleEdificio(costoTotaleEdificio.Value);
						}

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

		private T? SafeNullable<T>(object obj) where T : struct
		{
			if (obj == null || obj == DBNull.Value)
				return (T?)null;

			return (T)Convert.ChangeType(obj, typeof(T));
		}
	}
}
