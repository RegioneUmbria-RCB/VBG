using Init.SIGePro.Data;
using Init.Utils.Math;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class OICalcoloContribTMgr
    {
        public List<OICalcoloContribT> GetListByIdCalcoloTot(string idComune, int idCalcoloTot)
        {
            FormattableString sql = $"select * from O_ICALCOLOCONTRIBT where IDCOMUNE = {idComune} and FK_OICT_ID = {idCalcoloTot}";

            return this.db.GetClassList<OICalcoloContribT>(sql);
        }

        #region Prima elaborazione e creazione righe in O_ICALCOLOCONTRIBR

        public void Elabora(OICalcoloContribT testata)
        {
            if (testata.PrimoInserimento)
                return;

            var calcoloTot = new OICalcoloTotMgr(this.db).GetById(testata.Idcomune, testata.FkOictId!.Value);

            var closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }

            try
            {
                var idComune = testata.Idcomune;
                var idCalcoloTot = testata.FkOictId.Value;
                var idBaseDestinazione = testata.FkOccbdeId;

                var idDestinazioni = this.TrovaDestinazioniDaCalcoloContribT(idComune, idCalcoloTot, idBaseDestinazione);

                this.AggiornaOCreaContribR_TabABC(testata, calcoloTot, idDestinazioni);
                this.AggiornaOCreaContribR_TabD(testata, calcoloTot, idDestinazioni);

                var idContribT = testata.Id!.Value;
                var codiceIstanza = testata.Codiceistanza!.Value;

                this.ElaboraBto(idComune, idContribT, codiceIstanza);
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }


        private void AggiornaOCreaContribR_TabD(OICalcoloContribT testata, OICalcoloTot calcoloTot, IEnumerable<int> idDestinazioni)
        {
            foreach (var idDestinazione in idDestinazioni)
            {
                var idTipiOneri = this.TrovaTipiOneriDaTabellaD(testata.Idcomune, idDestinazione, calcoloTot.FkOvcId!.Value, testata.FkOclaId);

                foreach (var tipoOnere in idTipiOneri)
                {
                    var costom = this.TrovaCostoDaTabellaD(testata.Idcomune, idDestinazione, calcoloTot.FkOvcId!.Value, tipoOnere, testata.FkOclaId);

                    var superficie = this.TrovaSuperficieDaDestinazioneECalcoloTot(testata.Idcomune, testata.FkOictId!.Value, idDestinazione);

                    this.AggiornaDatoContribR(testata, idDestinazione, tipoOnere, costom, superficie);
                }
            }

        }

        private void AggiornaOCreaContribR_TabABC(OICalcoloContribT testata, OICalcoloTot calcoloTot, IEnumerable<int> idDestinazioni)
        {
            foreach (var idDestinazione in idDestinazioni)
            {
                var idTipiOneri = this.TrovaTipiOneriDaTabellaABC(testata.Idcomune, idDestinazione, calcoloTot.FkOvcId, testata.FkAreeCodiceareaZto, testata.FkAreeCodiceareaPrg, testata.FkOitId, testata.FkOinId);

                foreach (var idTipoOnere in idTipiOneri)
                {
                    var idComune = testata.Idcomune;
                    var idValiditaCoeff = calcoloTot.FkOvcId;
                    var idAreaZto = testata.FkAreeCodiceareaZto;
                    var idAreaPrg = testata.FkAreeCodiceareaPrg;
                    var idOit = testata.FkOitId;
                    var idOin = testata.FkOinId;

                    var costom = this.TrovaCostoDaTabellaABC(idComune, idDestinazione, idValiditaCoeff, idTipoOnere, idAreaZto, idAreaPrg, idOit, idOin);

                    var idCalcoloTot = testata.FkOictId!.Value;

                    var superficie = this.TrovaSuperficieDaDestinazioneECalcoloTot(idComune, idCalcoloTot, idDestinazione);

                    this.AggiornaDatoContribR(testata, idDestinazione, idTipoOnere, costom, superficie);
                }
            }
        }


        private void AggiornaDatoContribR(OICalcoloContribT testata, int idDestinazione, int idTipoOnere, decimal costom, decimal superficie)
        {
            var rMgr = new OICalcoloContribRMgr(this.db);
            var cls = rMgr.GetByContribTTipoOnereDestinazione(testata.Idcomune, testata.Id!.Value, idTipoOnere, idDestinazione);

            if (cls == null)
            {
                cls = new OICalcoloContribR();
                cls.Idcomune = testata.Idcomune;
                cls.Codiceistanza = testata.Codiceistanza;
                cls.FkOicctId = testata.Id;
                cls.FkOtoId = idTipoOnere;
                cls.FkOdeId = idDestinazione;
            }

            cls.SuperficieCubatura = Arrotondamento.PerEccesso(superficie, 2);
            cls.Costom = Arrotondamento.PerEccesso(costom, 2);

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
        private decimal TrovaSuperficieDaDestinazioneECalcoloTot(string idComune, int idCalcoloTot, int idDestinazione)
        {
            FormattableString sql = $@"select 
								sum(TOTALE)
							From 
							  O_ICALCOLO_DETTAGLIOT
							Where 
							  IDCOMUNE  = {idComune} AND 
							  FK_OIC_ID = {idCalcoloTot} AND
							  FK_ODE_ID = {idDestinazione}";

            return this.db.ExecuteScalar(sql, 0.0m);

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
        private decimal TrovaCostoDaTabellaABC(string idComune, int idDestinazione, int? idValiditaCoeff, int idTipoOnere, int? idAreaZto, int? idAreaPrg, int? idOit, int? idOin)
        {
            var sql = @"Select 
							  COSTO 
							From 
							  O_TABELLAABC 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} AND
							  FK_OTO_ID = {3} ";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDDESTINAZIONE"),
                                        this.db.Specifics.QueryParameterName("IDVALIDITACOEFF"),
                                        this.db.Specifics.QueryParameterName("IDTIPOONERE"));

            if (idAreaZto.HasValue)
                sql += " AND FK_AREE_CODICEAREA_ZTO = " + this.db.Specifics.QueryParameterName("IDAREAZTO");

            if (idAreaPrg.HasValue)
                sql += " AND FK_AREE_CODICEAREA_PRG = " + this.db.Specifics.QueryParameterName("IDAREAPRG");

            if (idOit.HasValue)
                sql += " AND FK_OIT_ID = " + this.db.Specifics.QueryParameterName("IDOIT");

            if (idOin.HasValue)
                sql += " AND FK_OIN_ID = " + this.db.Specifics.QueryParameterName("IDOIN");

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDDESTINAZIONE", idDestinazione));
                cmd.Parameters.Add(this.db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));
                cmd.Parameters.Add(this.db.CreateParameter("IDTIPOONERE", idTipoOnere));

                if (idAreaZto.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDAREAZTO", idAreaZto));

                if (idAreaPrg.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDAREAPRG", idAreaPrg));

                if (idOit.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDOIT", idOit));

                if (idOin.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDOIN", idOin));

                using (var dr = cmd.ExecuteReader())
                {
                    if (dr.Read())
                        return Convert.ToDecimal(dr[0]);

                    return 0.0m;
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
        private decimal TrovaCostoDaTabellaD(string idComune, int idDestinazione, int idValiditaCoeff, int idTipoOnere, int? idOca)
        {
            var sql = @"Select 
							  COSTO 
							From 
							  O_TABELLAD 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} AND
							  FK_OTO_ID = {3} ";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDDESTINAZIONE"),
                                        this.db.Specifics.QueryParameterName("IDVALIDITACOEFF"),
                                        this.db.Specifics.QueryParameterName("IDTIPOONERE"));

            if (idOca.HasValue)
                sql += " AND FK_OCA_ID = " + this.db.Specifics.QueryParameterName("IDOCA");

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDDESTINAZIONE", idDestinazione));
                cmd.Parameters.Add(this.db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));
                cmd.Parameters.Add(this.db.CreateParameter("IDTIPOONERE", idTipoOnere));

                if (idOca.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDOCA", idOca));

                using (var dr = cmd.ExecuteReader())
                {
                    if (dr.Read())
                        return Convert.ToDecimal(dr[0]);

                    return 0.0m;
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
        private IEnumerable<int> TrovaTipiOneriDaTabellaABC(string idComune, int idDestinazione, int? idValiditaCoeff, int? idAreaZto, int? idAreaPrg, int? idOit, int? idOin)
        {
            var sql = @"Select 
							  FK_OTO_ID 
							From 
							  O_TABELLAABC 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} ";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDDESTINAZIONE"),
                                        this.db.Specifics.QueryParameterName("IDVALIDITACOEFF"));

            if (idAreaZto.HasValue)
                sql += " AND FK_AREE_CODICEAREA_ZTO = " + this.db.Specifics.QueryParameterName("IDAREAZTO");

            if (idAreaPrg.HasValue)
                sql += " AND FK_AREE_CODICEAREA_PRG = " + this.db.Specifics.QueryParameterName("IDAREAPRG");

            if (idOit.HasValue)
                sql += " AND FK_OIT_ID = " + this.db.Specifics.QueryParameterName("IDOIT");

            if (idOin.HasValue)
                sql += " AND FK_OIN_ID = " + this.db.Specifics.QueryParameterName("IDOIN");

            sql += " Group By FK_OTO_ID";

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDDESTINAZIONE", idDestinazione));
                cmd.Parameters.Add(this.db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));

                if (idAreaZto.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDAREAZTO", idAreaZto));

                if (idAreaPrg.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDAREAPRG", idAreaPrg));

                if (idOit.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDOIT", idOit));

                if (idOin.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDOIN", idOin));

                using (var dr = cmd.ExecuteReader())
                {
                    var idTipiOnere = new List<int>();

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
        private IEnumerable<int> TrovaTipiOneriDaTabellaD(string idComune, int idDestinazione, int idValiditaCoeff, int? idOca)
        {
            var sql = @"Select 
							  FK_OTO_ID 
							From 
							  O_TABELLAD 
							Where 
							  IDCOMUNE = {0} AND
							  FK_ODE_ID = {1} and
							  FK_OVC_ID = {2} ";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDDESTINAZIONE"),
                                        this.db.Specifics.QueryParameterName("IDVALIDITACOEFF"));

            if (idOca.HasValue)
                sql += " AND FK_OCA_ID = " + this.db.Specifics.QueryParameterName("IDOCA");

            sql += " Group By FK_OTO_ID";

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDDESTINAZIONE", idDestinazione));
                cmd.Parameters.Add(this.db.CreateParameter("IDVALIDITACOEFF", idValiditaCoeff));

                if (idOca.HasValue)
                    cmd.Parameters.Add(this.db.CreateParameter("IDOCA", idOca));

                using (var dr = cmd.ExecuteReader())
                {
                    var idTipiOnere = new List<int>();

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
        private IEnumerable<int> TrovaDestinazioniDaCalcoloContribT(string idComune, int idCalcoloTot, string idBaseDestinazione)
        {
            FormattableString sql = $@"SELECT
							  FK_ODE_ID
							FROM 
							  O_ICALCOLO_DETTAGLIOT
							WHERE
							  IDCOMUNE  = {idComune} and
							  FK_OIC_ID = {idCalcoloTot} AND
							  FK_OCCBDE_ID = {idBaseDestinazione}
							GROUP BY 
							  FK_ODE_ID";

            return this.db.ExecuteReader(sql, dr => dr.GetInt("fk_ode_id").Value);

        }

        #endregion

        #region Seconda elaborazione e popolamento della tabella O_ICALCOLOCONTRIBT_BTO

        public void ElaboraBto(string idComune, int idContribT, int codiceIstanza)
        {
            // eliminazione delle vecchie righe
            this.EliminaBto(idComune, idContribT);

            // inserimento delle nuove righe gruppate per tipionerebase
            this.InserisciRigheBto(idComune, idContribT, codiceIstanza);
        }

        private void InserisciRigheBto(string idComune, int idContribT, int codiceIstanza)
        {
            FormattableString sql = $@"SELECT 
								O_TIPIONERI.FK_BTO_ID as ID , 
								Sum( O_ICALCOLOCONTRIBR.COSTOTOT ) AS TOTALE
							FROM 
							  O_ICALCOLOCONTRIBR,
							  O_TIPIONERI
							WHERE
							   O_TIPIONERI.IDCOMUNE = O_ICALCOLOCONTRIBR.IDCOMUNE AND
							   O_TIPIONERI.ID = O_ICALCOLOCONTRIBR.FK_OTO_ID AND
							   O_ICALCOLOCONTRIBR.IDCOMUNE = {idComune} AND
							   O_ICALCOLOCONTRIBR.FK_OICCT_ID = {idContribT}
							GROUP BY 
							   O_TIPIONERI.FK_BTO_ID";

            var valori = this.db.ExecuteReader(sql, dr => new
            {
                Id = dr.GetString("ID"),
                Totale = dr.GetDecimal("TOTALE").GetValueOrDefault(0.0m)
            });

            var btoMgr = new OICalcoloContribTBTOMgr(this.db);

            foreach (var val in valori)
            {
                var bto = new OICalcoloContribTBTO();

                bto.Idcomune = idComune;
                bto.FkOicctId = idContribT;
                bto.Codiceistanza = codiceIstanza;
                bto.FkBtoId = val.Id;
                bto.Costotot = val.Totale;

                btoMgr.Insert(bto);
            }
        }

        private void EliminaBto(string idComune, int idContribT)
        {
            FormattableString sql = $"DELETE FROM O_ICALCOLOCONTRIBT_BTO WHERE IDCOMUNE={idComune} AND FK_OICCT_ID = {idContribT}";

            this.db.ExecuteNonQuery(sql);
        }

        #endregion

        #region Generazione del dataset del calcolo contributo di urbanizzazione
        public DataSet GeneraDatasetContributoUrbanizzazione(string idComune, int idContribT)
        {
            var closecnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closecnn = true;
                this.db.Connection.Open();
            }

            try
            {
                var dsContributo = new DataSet();

                var destinazioni = this.GetListaDestinazioni(idComune, idContribT);

                var dt = new DataTable("ContributoUrbanizzazione");

                dt.Columns.Add(new DataColumn("IdDestinazione", typeof(int)));
                dt.Columns.Add(new DataColumn("Destinazione", typeof(string)));
                dt.Columns.Add(new DataColumn("Cubatura", typeof(double)));

                var tipiOnere = this.GetListaTipiOnere(idComune, idContribT);

                for (var i = 0; i < tipiOnere.Count; i++)
                {
                    dt.Columns.Add(new DataColumn(tipiOnere[i].ToString() + "_costom", typeof(string)));
                    dt.Columns.Add(new DataColumn(tipiOnere[i].ToString() + "_riduzione", typeof(string)));
                    dt.Columns.Add(new DataColumn(tipiOnere[i].ToString() + "_costoTot", typeof(string)));
                    dt.Columns.Add(new DataColumn(tipiOnere[i].ToString() + "_percriduzione", typeof(string)));
                }

                // inserisco una riga che conterrà le intestazioni
                var drTemp = dt.NewRow();
                drTemp["IdDestinazione"] = -1;
                drTemp["Destinazione"] = "";
                drTemp["Cubatura"] = -1;

                for (var i = 0; i < tipiOnere.Count; i++)
                {
                    drTemp[tipiOnere[i].ToString() + "_costom"] = "Costo al metro";
                    drTemp[tipiOnere[i].ToString() + "_costoTot"] = "Costo totale";
                    drTemp[tipiOnere[i].ToString() + "_riduzione"] = "Variazione";
                    drTemp[tipiOnere[i].ToString() + "_percriduzione"] = "";
                }

                dt.Rows.Add(drTemp);

                // inserimanto delle righe
                foreach (var dest in destinazioni)
                {
                    var dr = dt.NewRow();

                    var idDestinazione = Convert.ToInt32(dest.Key);

                    dr["IdDestinazione"] = idDestinazione;
                    dr["Destinazione"] = dest.Value;
                    dr["Cubatura"] = this.GetCubaturaPerDestinazione(idComune, idContribT, Convert.ToInt32(dest.Key));

                    for (var i = 0; i < tipiOnere.Count; i++)
                    {
                        var val = this.GetImportiPerDestinazioneTipoOnere(idComune, idContribT, idDestinazione, tipiOnere[i]);

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
                    this.db.Connection.Close();
            }
        }

        private double GetCubaturaPerDestinazione(string idComune, int idContribT, int idDestinazione)
        {
            var sql = @"Select 
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

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IdComune"),
                                                this.db.Specifics.QueryParameterName("IdContribT"),
                                                this.db.Specifics.QueryParameterName("IdDestinazione"));

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IdContribT", idContribT));
                cmd.Parameters.Add(this.db.CreateParameter("IdDestinazione", idDestinazione));

                var val = cmd.ExecuteScalar();

                if (val == null || val == DBNull.Value) return 0.0d;

                return Convert.ToDouble(val);
            }
        }

        internal List<int> GetListaTipiOnere(string idComune, int idContribT)
        {
            var sql = @"SELECT 
							  FK_OTO_ID AS id
							FROM 
							  O_ICALCOLOCONTRIBR
							WHERE 
							  O_ICALCOLOCONTRIBR.IDCOMUNE = {0} AND
							  O_ICALCOLOCONTRIBR.FK_OICCT_ID = {1}
							GROUP BY 
							  FK_OTO_ID";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IdComune"),
                                        this.db.Specifics.QueryParameterName("IdContribT"));

            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("IdContribT", idContribT));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var idOneri = new List<int>();

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
                    this.db.Connection.Close();
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
                get { return this.m_riduzione; }
                set { this.m_riduzione = value; }
            }

            public double RiduzionePerc
            {
                get { return this.m_riduzionePerc; }
                set { this.m_riduzionePerc = value; }
            }

            public double CostoTot
            {
                get { return this.m_costoTot; }
                set { this.m_costoTot = value; }
            }



            public double Costom
            {
                get { return this.m_costom; }
                set { this.m_costom = value; }
            }

        }


        private GetImportiPerDestinazioneTipoOnereResult GetImportiPerDestinazioneTipoOnere(string idComune, int idContribT, int idDestinazione, int idTipoOnere)
        {
            var sql = @"SELECT 
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

            sql = string.Format(sql, this.db.Specifics.QueryParameterName("IdComune"),
                                        this.db.Specifics.QueryParameterName("IdContribT"),
                                        this.db.Specifics.QueryParameterName("IdDestinazione"),
                                        this.db.Specifics.QueryParameterName("IdTipoOnere"));

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IdContribT", idContribT));
                cmd.Parameters.Add(this.db.CreateParameter("IdDestinazione", idDestinazione));
                cmd.Parameters.Add(this.db.CreateParameter("IdTipoOnere", idTipoOnere));

                using (var dr = cmd.ExecuteReader())
                {
                    if (dr.Read())
                    {
                        var ret = new GetImportiPerDestinazioneTipoOnereResult();

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
            var sql = @"SELECT 
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

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IdComune"),
                                        this.db.Specifics.QueryParameterName("IdContribT"));

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IdContribT", idContribt));

                using (var dr = cmd.ExecuteReader())
                {
                    var dest = new List<KeyValuePair<string, string>>();

                    while (dr.Read())
                    {
                        var key = dr["id"].ToString();
                        var val = dr["destinazione"].ToString();

                        dest.Add(new KeyValuePair<string, string>(key, val));
                    }

                    return dest;
                }
            }
        }
        #endregion

        private string GetSoftwareDaContribT(string idComune, int idContribT)
        {
            var sql = @"SELECT 
							  istanze.software
							FROM
							  istanze,
							  o_icalcolocontribt
							WHERE
							  istanze.idcomune = o_icalcolocontribt.idcomune AND
							  istanze.codiceistanza = o_icalcolocontribt.codiceistanza AND
							  o_icalcolocontribt.Idcomune = {0} AND
							  o_icalcolocontribt.Id = {1}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDCONTRIBT"));

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
                    cmd.Parameters.Add(this.db.CreateParameter("IDCONTRIBT", idContribT));

                    var software = cmd.ExecuteScalar();

                    if (software == null || software == DBNull.Value)
                        throw new ArgumentException("Impossibile ricavare il software per o_icalcolocontribt.Id = " + idContribT.ToString());

                    return software.ToString();
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }

        #region lettura dei valori delle combo per i dettagli del calcolo
        public string GetDescrizioneComboZonaOmogenea(string idComune, int idContribT)
        {
            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue) == int.MinValue)
                return String.Empty;

            var ta = new TipiAreeMgr(this.db).GetById(idComune, cfg.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue));

            if (ta == null)
                return string.Empty;

            return ta.Tipoarea;
        }

        public string GetDescrizioneComboZonaPrg(string idComune, int idContribT)
        {
            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue) == int.MinValue)
                return String.Empty;

            var ta = new TipiAreeMgr(this.db).GetById(idComune, cfg.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue));

            if (ta == null)
                return string.Empty;

            return ta.Tipoarea;
        }


        public List<KeyValuePair<string, string>> GetValoriComboZonaOmogenea(string idComune, int idContribT)
        {
            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue) == int.MinValue)
                return new List<KeyValuePair<string, string>>();

            var sql = @"Select distinct
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

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("idContribT"),
                                        this.db.Specifics.QueryParameterName("cfgTipoArea"));

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
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idContribT", idContribT));
                    cmd.Parameters.Add(this.db.CreateParameter("cfgTipoArea", cfg.FkTipiareeCodiceZto));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var ret = new List<KeyValuePair<string, string>>();

                        while (dr.Read())
                        {
                            var key = dr["id"].ToString();
                            var val = dr["descrizione"].ToString();

                            ret.Add(new KeyValuePair<string, string>(key, val));
                        }

                        return ret;
                    }
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }

        public List<KeyValuePair<string, string>> GetValoriComboZonaPrg(string idComune, int idContribT, int idZonaOmogenea)
        {
            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            if (cfg == null || cfg.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue) == int.MinValue)
                return new List<KeyValuePair<string, string>>();

            var sql = @"Select distinct
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

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("idContribT"),
                                        this.db.Specifics.QueryParameterName("cfgTipoArea"));


            if (idZonaOmogenea > 0)
                sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_ZTO = " + this.db.Specifics.QueryParameterName("IdZonaOmogenea");

            sql += " order by AREE.Denominazione asc";

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
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idContribT", idContribT));
                    cmd.Parameters.Add(this.db.CreateParameter("cfgTipoArea", cfg.FkTipiareeCodicePrg));

                    if (idZonaOmogenea > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IdZonaOmogenea", idZonaOmogenea));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var ret = new List<KeyValuePair<string, string>>();

                        while (dr.Read())
                        {
                            var key = dr["id"].ToString();
                            var val = dr["descrizione"].ToString();

                            ret.Add(new KeyValuePair<string, string>(key, val));
                        }

                        return ret;
                    }
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }


        public List<KeyValuePair<string, string>> GetValoriComboTipoIntervento(string idComune, int idContribT, int idZonaOmogenea, int idZonaPrg)
        {
            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            var sql = @"Select distinct
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

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("idContribT"));


            if (idZonaOmogenea > 0)
                sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_ZTO = " + this.db.Specifics.QueryParameterName("IdZonaOmogenea");

            if (idZonaPrg > 0)
                sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_PRG = " + this.db.Specifics.QueryParameterName("IdZonaPrg");

            sql += " order by O_INTERVENTI.ORDINAMENTO ASC";

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
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idContribT", idContribT));

                    if (idZonaOmogenea > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IdZonaOmogenea", idZonaOmogenea));

                    if (idZonaPrg > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IdZonaPrg", idZonaPrg));


                    using (var dr = cmd.ExecuteReader())
                    {
                        var ret = new List<KeyValuePair<string, string>>();

                        while (dr.Read())
                        {
                            var key = dr["id"].ToString();
                            var val = dr["descrizione"].ToString();

                            ret.Add(new KeyValuePair<string, string>(key, val));
                        }

                        return ret;
                    }
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }


        public List<KeyValuePair<string, string>> GetValoriComboIndiciTerritoriali(string idComune, int idContribT, int idZonaOmogenea, int idZonaPrg, int idIntervento)
        {
            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            var sql = @"Select distinct
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


            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("idContribT"));


            if (idZonaOmogenea > 0)
                sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_ZTO = " + this.db.Specifics.QueryParameterName("IdZonaOmogenea");

            if (idZonaPrg > 0)
                sql += " AND O_TABELLAABC.FK_AREE_CODICEAREA_PRG = " + this.db.Specifics.QueryParameterName("IdZonaPrg");

            if (idIntervento > 0)
                sql += " AND O_TABELLAABC.FK_OIN_ID = " + this.db.Specifics.QueryParameterName("IdTipoIntervento");

            sql += " order by o_indiciterritoriali.Descrizione asc";

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
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idContribT", idContribT));

                    if (idZonaOmogenea > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IdZonaOmogenea", idZonaOmogenea));

                    if (idZonaPrg > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IdZonaPrg", idZonaPrg));

                    if (idIntervento > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IdTipoIntervento", idIntervento));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var ret = new List<KeyValuePair<string, string>>();

                        while (dr.Read())
                        {
                            var key = dr["id"].ToString();
                            var val = dr["Descrizione"].ToString();

                            ret.Add(new KeyValuePair<string, string>(key, val));
                        }

                        return ret;
                    }
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }



        public List<KeyValuePair<string, string>> GetValoriComboInterventiTabd(string idComune, int idContribT)
        {
            var testata = this.GetById(idComune, idContribT);
            var calcoloTot = new OICalcoloTotMgr(this.db).GetById(testata.Idcomune, testata.FkOictId.GetValueOrDefault(int.MinValue));

            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            var sql = @"Select distinct
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


            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("idContribT"),
                                        this.db.Specifics.QueryParameterName("idValiditaCoeff"));

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
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idContribT", idContribT));
                    cmd.Parameters.Add(this.db.CreateParameter("idValiditaCoeff", calcoloTot.FkOvcId));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var ret = new List<KeyValuePair<string, string>>();

                        while (dr.Read())
                        {
                            var key = dr["id"].ToString();
                            var val = dr["descrizione"].ToString();

                            ret.Add(new KeyValuePair<string, string>(key, val));
                        }

                        return ret;
                    }
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }

        public List<KeyValuePair<string, string>> GetValoriComboClassiAddetti(string idComune, int idContribT, int idIntervento)
        {
            var testata = this.GetById(idComune, idContribT);
            var calcoloTot = new OICalcoloTotMgr(this.db).GetById(testata.Idcomune, testata.FkOictId.GetValueOrDefault(int.MinValue));

            var cfg = new OConfigurazioneMgr(this.db).GetById(idComune, this.GetSoftwareDaContribT(idComune, idContribT));

            var sql = @"Select distinct
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


            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("idContribT"),
                                        this.db.Specifics.QueryParameterName("idValiditaCoeff"));

            if (idIntervento > 0)
                sql += " and O_TABELLAD.FK_OIN_ID = " + this.db.Specifics.QueryParameterName("IdIntervento");

            sql += " order by O_CLASSIADDETTI.ORDINAMENTO ASC";

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
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idContribT", idContribT));
                    cmd.Parameters.Add(this.db.CreateParameter("idValiditaCoeff", calcoloTot.FkOvcId));

                    if (idIntervento > 0)
                        cmd.Parameters.Add(this.db.CreateParameter("IdIntervento", idIntervento));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var ret = new List<KeyValuePair<string, string>>();

                        while (dr.Read())
                        {
                            var key = dr["id"].ToString();
                            var val = dr["descrizione"].ToString();

                            ret.Add(new KeyValuePair<string, string>(key, val));
                        }

                        return ret;
                    }
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }





        #endregion

        private void EffettuaCancellazioneACascata(OICalcoloContribT cls)
        {
            var a = new OICalcoloContribTBTO();
            a.Idcomune = cls.Idcomune;
            a.FkOicctId = cls.Id;

            var lCalcoloBTO = new OICalcoloContribTBTOMgr(this.db).GetList(a);
            foreach (var calcoloBTO in lCalcoloBTO)
            {
                var mgr = new OICalcoloContribTBTOMgr(this.db);
                mgr.Delete(calcoloBTO);
            }

            var b = new OICalcoloContribR();
            b.Idcomune = cls.Idcomune;
            b.FkOicctId = cls.Id;

            var lCalcoloR = new OICalcoloContribRMgr(this.db).GetList(b);
            foreach (var calcoloR in lCalcoloR)
            {
                var mgr = new OICalcoloContribRMgr(this.db);
                mgr.Delete(calcoloR);
            }
        }
    }
}
