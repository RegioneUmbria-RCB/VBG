using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using log4net;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCICalcoloTotMgr
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(CCICalcoloTotMgr));

        public void IntegraForeignKey(CCICalcoloTot cls)
        {
            var filtro = new CCICalcoloTContributo();
            filtro.Idcomune = cls.Idcomune;
            filtro.FkCcictId = cls.Id;
            filtro.Codiceistanza = cls.Codiceistanza;

            var contr = new CCICalcoloTContributoMgr(this.db).GetList(filtro);

            for (var i = 0; i < contr.Count; i++)
            {
                if (contr[i].Stato == "A")
                    cls.StatoAttuale = contr[i];
                else if (contr[i].Stato == "P")
                    cls.StatoDiProgetto = contr[i];
            }
        }

        public CCICalcoloTot GetByIdICalcolo(string idComune, int idICalcoli)
        {
            var tcontr = new CCICalcoloTContributo();
            tcontr.Idcomune = idComune;
            tcontr.FkCcicId = idICalcoli;

            tcontr = (CCICalcoloTContributo)this.db.GetClass(tcontr);

            if (tcontr == null) return null;

            return this.GetById(idComune, tcontr.FkCcictId.Value);
        }

        public List<CCICalcoloTot> GetList(string idComune, int codiceIstanza)
        {
            var c = new CCICalcoloTot();
            c.Idcomune = idComune;
            c.Codiceistanza = codiceIstanza;
            c.OrderBy = " ID ASC ";

            return this.GetList(c);
        }

        public List<CCBaseTipoCalcolo> GetTipiCalcoloBase(string idComune, string software, string ccBaseTipoInterventoId, string occBaseDestinazioniId)
        {
            var detMgr = new CCDetermTipoCalcoloMgr(this.db);
            var btcMgr = new CCBaseTipoCalcoloMgr(this.db);

            var tipiCalcoloId = detMgr.GetList(idComune, ccBaseTipoInterventoId, occBaseDestinazioniId, software);

            var ret = new List<CCBaseTipoCalcolo>();

            tipiCalcoloId.ForEach(delegate (CCDetermTipoCalcolo tcId)
                                    {
                                        ret.Add(btcMgr.GetById(tcId.FkCcbtcId));
                                    });

            return ret;
        }

        public decimal CalcolaQuotaContribTotale(CCICalcoloTot cls)
        {
            var mgr = new CCICalcoloTContributoMgr(this.db);

            var c1 = mgr.CalcoloContributo(cls.Idcomune, cls.Id.Value, cls.Codiceistanza.Value, "P");
            var c2 = mgr.CalcoloContributo(cls.Idcomune, cls.Id.Value, cls.Codiceistanza.Value, "A");

            return c1 - c2;
        }

        public DataClass ChildDataIntegrations(DataClass clsTmp)
        {
            var cls = (CCICalcoloTot)clsTmp;

            if (cls.StatoDiProgetto == null)
            {
                cls.StatoDiProgetto = new CCICalcoloTContributo();
                cls.StatoDiProgetto.CostocEdificio = 0.0m;
                cls.StatoDiProgetto.Coefficiente = 0.0m;

            }


            if (cls.StatoDiProgetto != null)
            {
                cls.StatoDiProgetto.Idcomune = cls.Idcomune;
                cls.StatoDiProgetto.Codiceistanza = cls.Codiceistanza;
                cls.StatoDiProgetto.FkCcictId = cls.Id;
                cls.StatoDiProgetto.Stato = "P";
            }





            if (cls.StatoAttuale == null && (cls.FkBcctcId == "M12" || cls.FkBcctcId == "P12"))
            {
                cls.StatoAttuale = new CCICalcoloTContributo();
                cls.StatoAttuale.CostocEdificio = 0.0m;
                cls.StatoAttuale.Coefficiente = 0.0m;
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
            this.IntegraForeignKey(cls);

            return cls.StatoDiProgetto;
        }

        public CCICalcoloTContributo GetStatoAttuale(CCICalcoloTot cls)
        {
            this.IntegraForeignKey(cls);

            return cls.StatoAttuale;
        }

        private CCICalcoloTot DataIntegrations(CCICalcoloTot cls)
        {
            cls.QuotacontribTotale = cls.QuotacontribTotale.GetValueOrDefault(0.0m);

            return cls;
        }

        private CCICalcoloTot ChildInsert(CCICalcoloTot cls)
        {
            var mgrContributo = new CCICalcoloTContributoMgr(this.db);

            if (cls.StatoDiProgetto != null && cls.StatoDiProgetto.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
                mgrContributo.Insert(cls.StatoDiProgetto);


            if (cls.StatoAttuale != null && cls.StatoAttuale.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
                mgrContributo.Insert(cls.StatoAttuale);

            return cls;
        }


        public void Delete(CCICalcoloTot cls)
        {
            var wasInTransaction = this.db.IsInTransaction;

            try
            {
                if (!wasInTransaction)
                    this.db.BeginTransaction();

                this.VerificaRecordCollegati(cls);

                this.EffettuaCancellazioneACascata(cls);

                this.db.Delete(cls);

                if (!wasInTransaction)
                    this.db.CommitTransaction();
            }
            catch (Exception)
            {
                if (!wasInTransaction)
                    this.db.RollbackTransaction();

                throw;
            }
        }

        private void EffettuaCancellazioneACascata(CCICalcoloTot cls)
        {
            var c = new CCICalcoloTContributo();
            c.Idcomune = cls.Idcomune;
            c.FkCcictId = cls.Id;

            var lCalcoloT = new CCICalcoloTContributoMgr(this.db).GetList(c);
            foreach (var calcoloT in lCalcoloT)
            {
                var mgr = new CCICalcoloTContributoMgr(this.db);
                mgr.Delete(calcoloT);
            }
        }

        #region metodi per la lettura di altre tabelle a partire dall'idcalcolotot

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static DataTable GetDestinazioni(string token, int idCalcoloTot)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);
            var db = authInfo.CreateDatabase();

            /* ---------------------------prima query 
				ATTENZIONE se questa query viene modificata molto probabilmente dovrà essere modificata anche 
				la query del metodo GetTipiInterventoDaIdDestinazione
			 */
            var sql1 = @"Select
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
            var sql2 = @"Select
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
            var sql3 = @"Select
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

            var dsRet = new DataSet();

            using (var cmd = db.CreateCommand(sql1))
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

                var closeConnection = false;

                if (db.Connection.State == ConnectionState.Closed)
                {
                    closeConnection = true;
                    db.Connection.Open();
                }

                try
                {
                    using (var dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            var id = dr["id"].ToString();
                            var descrizione = dr["descrizione"].ToString();

                            var drs = dsRet.Tables[0].Select(" id = " + id);

                            if (drs == null || drs.Length > 0) continue;

                            var row = dsRet.Tables[0].NewRow();
                            row["id"] = id;
                            row["descrizione"] = descrizione;

                            dsRet.Tables[0].Rows.Add(row);
                        }
                    }
                }
                finally
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
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);
            var db = authInfo.CreateDatabase();
            /*
			 * ATTENZIONE se questa query viene modificata molto probabilmente dovrà essere modificata anche 
			 * la query del metodo GetAreeDaIdDestinazioneIdTipoIntervento
			 */
            var sql = @"Select
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

            using (var cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", authInfo.IdComune));
                cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

                if (idDestinazione > 0)
                    cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));

                IDataAdapter adp = db.CreateDataAdapter(cmd);
                var dsRet = new DataSet();

                adp.Fill(dsRet);

                if (dsRet.Tables[0].Rows.Count > 0 && dsRet.Tables[0].Rows[0]["ID"] == DBNull.Value)
                    dsRet.Tables[0].Rows[0].Delete();

                return dsRet.Tables[0];
            }

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static DataTable GetAreeDaIdDestinazioneIdTipoIntervento(string token, int idCalcoloTot, int idDestinazione, int idTipoIntervento)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);
            var db = authInfo.CreateDatabase();
            /*
			 * ATTENZIONE se questa query viene modificata molto probabilmente dovrà essere modificata anche 
			 * la query del metodo GetAreeDaIdDestinazioneIdTipoIntervento
			 */
            var sql = @"Select
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

            using (var cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", authInfo.IdComune));
                cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

                if (idDestinazione > 0)
                    cmd.Parameters.Add(db.CreateParameter("IDDESTINAZIONE", idDestinazione));

                if (idTipoIntervento > 0)
                    cmd.Parameters.Add(db.CreateParameter("IDTIPOINTERVENTO", idTipoIntervento));

                IDataAdapter adp = db.CreateDataAdapter(cmd);
                var dsRet = new DataSet();

                adp.Fill(dsRet);

                if (dsRet.Tables[0].Rows.Count > 0 && dsRet.Tables[0].Rows[0]["ID"] == DBNull.Value)
                    dsRet.Tables[0].Rows[0].Delete();

                return dsRet.Tables[0];
            }
        }

        public decimal GetCoefficienteDContributo(string idComune, int idCalcoloTot, int idDestinazione, int idTipoIntervento, int idUbicazione, int idAttivita)
        {
            var sql = @"Select
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

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDCALCOLOTOT"));

            if (idDestinazione >= 0)
                sql += " and VW_CCICT_DESTINAZIONI.CCDE_ID = " + this.db.Specifics.QueryParameterName("IDDESTINAZIONE");

            if (idTipoIntervento >= 0)
                sql += " and CC_COEFFCONTRIBUTO.FK_CCTI_ID = " + this.db.Specifics.QueryParameterName("IDTIPOINTERVENTO");

            if (idUbicazione >= 0)
                sql += " and CC_COEFFCONTRIBUTO.FK_AREE_CODICEAREA = " + this.db.Specifics.QueryParameterName("IDUBICAZIONE");
            else
                sql += " and CC_COEFFCONTRIBUTO.FK_AREE_CODICEAREA is null";

            if (idAttivita >= 0)
                sql += " and CC_COEFFCONTRIBUTO.FK_CCCA_ID = " + this.db.Specifics.QueryParameterName("idAttivita");
            else
                sql += " and CC_COEFFCONTRIBUTO.FK_CCCA_ID is null";

            var closecnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                this.db.Connection.Open();
                closecnn = true;
            }

            try
            {
                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

                    if (idDestinazione > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IDDESTINAZIONE", idDestinazione));

                    if (idTipoIntervento > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IDTIPOINTERVENTO", idTipoIntervento));

                    if (idUbicazione > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IDUBICAZIONE", idUbicazione));

                    if (idAttivita >= 0)
                        cmd.Parameters.Add(this.db.CreateParameter("idAttivita", idAttivita));

                    decimal valore = -1;

                    using (var rd = cmd.ExecuteReader())
                    {
                        while (rd.Read())
                        {
                            // In teoria si dovrebbe leggere un solo valore
                            if (valore >= 0)
                            {
                                this._log.Error($"{idComune} CCICalcoloTotMgr::GetCoefficienteDContributo ha restituito più di una riga. IDCOMUNE={idComune}, " +
                                    $"IDCALCOLOTOT={idCalcoloTot}, IDDESTINAZIONE={idDestinazione}, idTipoIntervento={idTipoIntervento}, idUbicazione={idUbicazione}");

                                return valore;
                            }
                            else
                            {
                                valore = Convert.ToDecimal(rd[0]);
                            }
                        }
                    }

                    return valore < 0 ? 0.0m : valore;
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }

        #endregion

        public class AliquotePerSuperficie
        {
            public class Aliquota
            {
                private readonly double _rapportoStSu = 0.0d;
                private readonly double _aliquota = 0.0d;
                private readonly double _prezzoTot = 0.0d;

                public string ClasseSuperficie { get; private set; }
                public string RapportoStSu { get { return this._rapportoStSu.ToString("N2"); } }
                public string ValoreAliquota { get { return this._aliquota.ToString("N2"); } }
                public string AliquotaTot { get { return this.GetAliquota().ToString("N2"); } }
                public string Contributo { get { return this.GetContributo().ToString("N2"); } }

                public Aliquota(string classeSuperficie, double rapportoStSu, double aliquota, double prezzoTot)
                {
                    this.ClasseSuperficie = classeSuperficie;
                    this._rapportoStSu = Math.Round(rapportoStSu, 2);
                    this._aliquota = Math.Round(aliquota, 2);
                    this._prezzoTot = Math.Round(prezzoTot, 2);
                }

                internal double GetAliquota()
                {
                    return this._aliquota * this._rapportoStSu;
                }

                internal double GetContributo()
                {
                    return (this._prezzoTot / 100.0d) * this.GetAliquota();
                }
            }

            private readonly List<Aliquota> _righe = new List<Aliquota>();
            private double _contributoTotale = 0.0d;
            private double _aliquotaTotale = 0.0d;
            private double _costEdificioTotale = 0.0d;

            public void AggiungiRiga(string classeSuperficie, double rapportoStSu, double aliquota, double prezzoTot)
            {
                var riga = new Aliquota(classeSuperficie, rapportoStSu, aliquota, prezzoTot);

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
            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                sql = this.PreparaQueryParametrica(sql, "idComune", "idCalcoloTot", "stato");

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idCalcoloTot", idCalcoloTot));
                    cmd.Parameters.Add(this.db.CreateParameter("stato", stato == StatoCalcoloEnum.Attuale ? "A" : "P"));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new AliquotePerSuperficie();

                        while (dr.Read())
                        {
                            var costoTotaleEdificio = this.SafeNullable<double>(dr["costoc_edificio"]);
                            var classe = dr["classe"].ToString();
                            var rapportoSu = this.SafeNullable<double>(dr["rapporto_su"]);
                            var aliquotaCalcoloCc = this.SafeNullable<double>(dr["aliquota_calcolo_cc"]);
                            var aliquotaTot = this.SafeNullable<double>(dr["aliquota_tot"]);
                            var costoClasse = this.SafeNullable<double>(dr["costo_classe"]);

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
                    this.db.Connection.Close();
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
