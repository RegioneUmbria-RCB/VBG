using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OICalcoloTotMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OICalcoloTot> Find(string token, int codiceIstanza)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            var mgr = new OICalcoloTotMgr(authInfo.CreateDatabase());

            var filtro = new OICalcoloTot();
            filtro.Idcomune = authInfo.IdComune;
            filtro.Codiceistanza = codiceIstanza;
            filtro.OrderBy = "Id asc";

            return mgr.GetList(filtro);

        }

        public string GetSoftwareDaCalcoloTot(string idComune, int idCalcoloTot)
        {
            var sql = @"SELECT 
							  istanze.software
							FROM
							  istanze,
							  o_icalcolotot
							WHERE
							  istanze.idcomune = o_icalcolotot.idcomune AND
							  istanze.codiceistanza = o_icalcolotot.codiceistanza AND
							  o_icalcolotot.Idcomune = {0} AND
							  o_icalcolotot.Id = {1}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDCALCOLOTOT"));

            var closecnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closecnn = true;
                this.db.Connection.Open();
            }

            try
            {
                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

                    var software = cmd.ExecuteScalar();

                    if (software == null || software == DBNull.Value)
                        throw new ArgumentException("Impossibile ricavare il software per o_icalcolotot.Id = " + idCalcoloTot.ToString());

                    return software.ToString();
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }

        #region generazione del dataset degli oneri di urbanizzazione
        public DataSet GeneraDataSetOneriUrbanizzazione(string idComune, int idCalcoloTot)
        {
            var dsOneri = new DataSet();

            var closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }

            try
            {

                var destinazioni = this.LeggiDestinazioniBaseAttive(idComune, idCalcoloTot);

                var tipiOnere = this.LeggiTipiOnere(idComune, idCalcoloTot);

                var dt = new DataTable("OneriUrbanizzazione");

                dt.Columns.Add("Destinazione", typeof(string));

                foreach (var tipo in tipiOnere)
                    dt.Columns.Add(tipo["ID"], typeof(double));

                // Se ho trovato almeno un tipoonere aggiungo la colonna dei totali
                if (tipiOnere.Count > 0)
                {
                    var sumExpr = "";

                    foreach (var tipo in tipiOnere)
                    {
                        var nomeColonna = tipo["ID"];

                        if (sumExpr != String.Empty)
                            sumExpr += " + ";

                        sumExpr += nomeColonna;
                    }

                    dt.Columns.Add("Totale", typeof(double), sumExpr);
                }

                dt.Columns.Add("Comandi", typeof(int));

                foreach (var destinazione in destinazioni)
                {
                    var row = dt.NewRow();

                    var idTestata = Convert.ToInt32(destinazione["ID"]);

                    row["Destinazione"] = destinazione["DESTINAZIONE"];
                    row["Comandi"] = idTestata;

                    foreach (var tipo in tipiOnere)
                    {
                        var nomeColonna = tipo["ID"];
                        var valore = this.ValoreTipoOnerePerIdTestata(idComune, idTestata, nomeColonna);
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
                    this.db.Connection.Close();
            }
        }

        private double ValoreTipoOnerePerIdTestata(string idComune, int idTestata, string baseTipoOnere)
        {
            var sql = @"SELECT 
							  costotot
							FROM 
							  O_ICALCOLOCONTRIBT_BTO
							WHERE
							  IDCOMUNE = {0} AND
							  FK_OICCT_ID = {1} AND
							  FK_BTO_ID = {2}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                    this.db.Specifics.QueryParameterName("IDTESTATA"),
                                    this.db.Specifics.QueryParameterName("IDBASETIPOONERE"));

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDTESTATA", idTestata));
                cmd.Parameters.Add(this.db.CreateParameter("IDBASETIPOONERE", baseTipoOnere));

                var obj = cmd.ExecuteScalar();

                if (obj == null || obj == DBNull.Value) return 0.0d;

                return Convert.ToDouble(obj);
            }
        }

        private List<Dictionary<string, string>> LeggiTipiOnere(string idComune, int idCalcoloTot)
        {
            var sql = @"Select 
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

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                    this.db.Specifics.QueryParameterName("IDCALCOLOTOT"));

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

                using (var rd = cmd.ExecuteReader())
                {
                    var ret = new List<Dictionary<string, string>>();

                    while (rd.Read())
                    {
                        var row = new Dictionary<string, string>();
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
            var sql = @"SELECT
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

            sql = string.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                                                        this.db.Specifics.QueryParameterName("IDCALCOLOTOT"));

            var destinazioniBase = new List<Dictionary<string, string>>();

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));

                using (var dr = cmd.ExecuteReader())
                {
                    while (dr.Read())
                    {
                        var row = new Dictionary<string, string>();
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

            // Elimino le testate e le righe che non sono più presenti nei dettagli
            this.EliminaDettagliInutilizzati(idComune, idCalcoloTot);
            this.EliminaTestateInutilizzate(idComune, idCalcoloTot);

            // Aggiungo le righe di testata e dettaglio che corrispondono alle destinazionibase che 
            // ancora non sono presenti nelle tabelle O_ICALCOLOCONTRIBT 
            this.IntegraNuoveTestate(idComune, idCalcoloTot);

            var mgrContribT = new OICalcoloContribTMgr(this.db);

            var listaTestate = mgrContribT.GetListByIdCalcoloTot(idComune, idCalcoloTot);

            listaTestate.ForEach(delegate (OICalcoloContribT testata) { mgrContribT.Elabora(testata); });
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
            var iddaEliminare = this.TrovaDettagliInutilizzati(idComune, idCalcoloTot);

            // Trovati gli id da eliminare, elimino prima eventuali riduzioni (o_icalcolocontribr_riduz) e poi elimino le righe (O_ICALCOLOCONTRIBR)
            foreach (var id in iddaEliminare)
            {
                FormattableString sql = $"DELETE FROM O_ICALCOLOCONTRIBR_RIDUZ WHERE idcomune={idComune} AND FK_OICCR_ID={id}";
                this.db.ExecuteNonQuery(sql);

                sql = $"DELETE FROM O_ICALCOLOCONTRIBR WHERE idcomune={idComune} AND ID={id}";
                this.db.ExecuteNonQuery(sql);
            }

        }

        /// <summary>
        /// Individua tutte le righe di dettaglio le cui destinazioni non sono più utilizzate nella tabella
        /// O_ICALCOLO_DETTAGLIOT.
        /// </summary>
        /// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
        /// <param name="idComune">id comune</param>
        /// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
        private IEnumerable<int> TrovaDettagliInutilizzati(string idComune, int idCalcoloTot)
        {
            FormattableString sql = $@"SELECT 
						  O_ICALCOLOCONTRIBR.ID 
						FROM 
						  O_ICALCOLOCONTRIBT,
						  O_ICALCOLOCONTRIBR 
						WHERE 
						  O_ICALCOLOCONTRIBT.IDCOMUNE = O_ICALCOLOCONTRIBR.IDCOMUNE AND
						  O_ICALCOLOCONTRIBT.ID = O_ICALCOLOCONTRIBR.FK_OICCT_ID and
						  O_ICALCOLOCONTRIBT.FK_OICT_ID = {idCalcoloTot} AND
						  O_ICALCOLOCONTRIBR.IDCOMUNE   = {idComune} AND
						  O_ICALCOLOCONTRIBR.FK_ODE_ID IN 
						  (
							SELECT 
							  fk_ode_id 
							FROM 
							  O_ICALCOLO_DETTAGLIOT
							WHERE
							  O_ICALCOLO_DETTAGLIOT.IDCOMUNE = {idComune} AND
							  O_ICALCOLO_DETTAGLIOT.FK_OIC_ID = {idCalcoloTot}
						  )";


            return this.db.ExecuteReader(sql, dr => dr.GetInt("ID")!.Value);
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
            var idDaEliminare = this.TrovaTestateInutilizzate(idComune, idCalcoloTot);

            foreach (var id in idDaEliminare)
            {
                // Elimina tutte le righe di testata (BTO) le cui destinazioni base non sono più utilizzate nella tabella
                // O_ICALCOLO_DETTAGLIOT.
                FormattableString sql = $"DELETE FROM O_ICALCOLOCONTRIBT_BTO WHERE idcomune={idComune} AND FK_OICCT_ID={id}";
                this.db.ExecuteNonQuery(sql);

                sql = $"DELETE FROM O_ICALCOLOCONTRIBT WHERE idcomune={idComune} AND ID={id}";
                this.db.ExecuteNonQuery(sql);
            }
        }

        /// <summary>
        /// Individua tutte le righe di testata le cui destinazioni base non sono più utilizzate nella tabella
        /// O_ICALCOLO_DETTAGLIOT.
        /// </summary>
        /// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
        /// <param name="idComune">id comune</param>
        /// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
        private IEnumerable<int> TrovaTestateInutilizzate(string idComune, int idCalcoloTot)
        {
            FormattableString sql = $@"SELECT 
							  O_ICALCOLOCONTRIBT.ID 
							FROM 
								O_ICALCOLOCONTRIBT 
							WHERE 
								O_ICALCOLOCONTRIBT.IDCOMUNE = {idComune} AND   
							  O_ICALCOLOCONTRIBT.FK_OICT_ID = {idCalcoloTot} and
								O_ICALCOLOCONTRIBT.FK_OCCBDE_ID NOT IN 
							(
							  SELECT 
								  FK_OCCBDE_ID
							  FROM 
								  O_ICALCOLO_DETTAGLIOT
							  WHERE
								  O_ICALCOLO_DETTAGLIOT.IDCOMUNE = {idComune} AND
									O_ICALCOLO_DETTAGLIOT.FK_OIC_ID = {idCalcoloTot}
							)";

            return this.db.ExecuteReader(sql, dr => dr.GetInt("ID")!.Value);
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
            var idDestinazioni = this.TrovaDestinazioniBaseUtilizzate(idComune, idCalcoloTot);

            foreach (var destinazioneBase in idDestinazioni)
            {
                if (!this.EsisteTestataPerDestinazioneBase(idComune, idCalcoloTot, destinazioneBase))
                    this.InserisciTestataPerDestinazione(idComune, idCalcoloTot, destinazioneBase);
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
            var mgr = new OICalcoloContribTMgr(this.db);

            var cls = new OICalcoloContribT();
            cls.Idcomune = idComune;
            cls.FkOictId = idCalcoloTot;
            cls.FkOccbdeId = idDestinazioneBase;
            cls.Codiceistanza = this.CodiceIstanzadaIdCalcoloTot(idComune, idCalcoloTot);

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
        private bool EsisteTestataPerDestinazioneBase(string idComune, int idCalcoloTot, string idDestinazioneBase)
        {
            FormattableString sql = $"SELECT Count(*) FROM O_ICALCOLOCONTRIBT WHERE IDCOMUNE={idComune} AND FK_OICT_ID = {idCalcoloTot} AND FK_OCCBDE_ID = {idDestinazioneBase}";

            var count = this.db.ExecuteScalar(sql, 0);

            return count != 0;
        }

        /// <summary>
        /// Trova le destinazionibase utilizzate nella tabella O_ICALCOLO_DETTAGLIOT
        /// </summary>
        /// <remarks>E'necessario che la connessione al database sia già stata aperta dalla funzione chiamante</remarks>
        /// <param name="idComune">id comune</param>
        /// <param name="idCalcoloTot">O_ICALCOLOTOT.ID</param>
        private IEnumerable<string> TrovaDestinazioniBaseUtilizzate(string idComune, int idCalcoloTot)
        {
            FormattableString sql = $@"SELECT 
						  FK_OCCBDE_ID
						from
						  O_ICALCOLO_DETTAGLIOT
						WHERE
						  IDCOMUNE  = {idComune} AND 
						  FK_OIC_ID = {idCalcoloTot}
						GROUP BY
						  FK_OCCBDE_ID ";

            return this.db.ExecuteReader(sql, dr => dr.GetString("FK_OCCBDE_ID"));
        }


        #endregion

        private int CodiceIstanzadaIdCalcoloTot(string idComune, int idCalcoloTot)
        {
            FormattableString sql = $"SELECT codiceistanza FROM o_icalcolotot WHERE idcomune = {idComune} AND id = {idCalcoloTot}";

            var codiceIstanza = this.db.ExecuteScalar(sql, -1);

            if (codiceIstanza == -1)
            {
                throw new ArgumentException("Non è stato possibile trovare il codice istanza relativo all'idCalcoloTot " + idCalcoloTot.ToString() + " per l'id comune " + idComune);
            }

            return codiceIstanza;
        }

        #endregion

        private void EffettuaCancellazioneACascata(OICalcoloTot cls)
        {
            var a = new OICalcoloDettaglioT();
            a.Idcomune = cls.Idcomune;
            a.FkOicId = cls.Id;

            var lCalcoloDett = new OICalcoloDettaglioTMgr(this.db).GetList(a);
            foreach (var calcoloDett in lCalcoloDett)
            {
                var mgr = new OICalcoloDettaglioTMgr(this.db);
                mgr.Delete(calcoloDett);
            }

            var b = new OICalcoloContribT();
            b.Idcomune = cls.Idcomune;
            b.FkOictId = cls.Id;

            var lCalcoloContrib = new OICalcoloContribTMgr(this.db).GetList(b);
            foreach (var calcoloContrib in lCalcoloContrib)
            {
                var mgr = new OICalcoloContribTMgr(this.db);
                mgr.Delete(calcoloContrib);
            }
        }
    }
}
