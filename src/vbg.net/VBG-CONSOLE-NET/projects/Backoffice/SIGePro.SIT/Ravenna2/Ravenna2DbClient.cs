using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using log4net;
using PersonalLib2.Data;
using Init.SIGePro.Sit.Data;

namespace Init.SIGePro.Sit.Ravenna2
{
    public class Ravenna2DbClient
    {
        public static class Constants
        {
            public const string Particella = "PARTICELLE";

            public static class TabellaRA012
            {
                public const string Nome = "RA012";

                public const string CampoCivico = "numero";
                public const string CampoEsponente = "parte";
                public const string CampoCodiceVia = "cod_via";
                public const string CampoSezione = "sezione";
                public const string CampoCodiceSezione = "cod_sez";
                public const string CampoFoglio = "foglio";
                public const string CampoParticella = "particella";
                public const string CampoEdificio = "cod_edif";
                public const string CodCivico = "indirizzo";
                public const string CampoCAP = "CAP";
                public const string CampoToponimo = "TOPONIMO";
            }
            /*
			public static class TabellaViewVie
			{
				public const string Nome = "view_vie";
				public const string CampoCap = "cap";
				public const string CampoFrazione = "fraz_nome";
				public const string CampoCodiceVia = "via_cod";
			}
            */

            public static class TabellaRA147
            {
                public const string Nome = "RA147";
                public const string CampoObjectId = "OBJECTID";
                public const string CampoCodiceSezione = "COD_SEZ";
                public const string CampoAnno = "ANNO";
                public const string CampoCodFraz = "COD_FRAZ";
                public const string CampoTipSez = "TIP_SEZ";
                public const string CampoCodUe = "COD_UE";
                public const string CampoCodiceCircoscrizione = "COD_CIRC";
                public const string CampoDescrizioneFrazione = "DESCRIZION";
                public const string CampoDescrizi_1 = "DESCRIZI_1";


            }

            public static class TabellaParicelleCatastali
            {
                public const string CampoNome = "PARICELLE_CATASTALI";
                public const string CampoObjectid = "OBJECTID";
                public const string CampoComune = "COMUNE";
                public const string CampoSezione = "SEZIONE";
                public const string CampoFoglio = "FOGLIO";
                public const string CampoAllegato = "ALLEGATO";
                public const string CampoSviluppo = "SVILUPPO";
                public const string CampoOrigine = "ORIGINE";
                public const string CampoNumero = "NUMERO";
                public const string CampoLivello = "LIVELLO";
                public const string CampoChiave = "CHIAVE";
                public const string CampoSez = "SEZ";
                public const string CampoGiskey = "GISKEY";
            }
        }

        string _tablePrefix;
        DataBase _db;
        ILog _log = LogManager.GetLogger(typeof(Ravenna2DbClient));

        string _codViarioNonDefinito = "0";

        public Ravenna2DbClient(DataBase db, string tablePrefix)
        {
            this._tablePrefix = tablePrefix;
            this._db = db;
        }

        internal Ravenna2ResultSet GetListaCivici(string codVia)
        {
            var query = new DistinctQueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);

            query.Select(ra012.Civico);
            query.From(ra012);
            query.WhereEqualParametric(ra012.CodiceVia, codVia);
            query.OrderBy(ra012.Civico);

            return GetMultipleResults(query);
        }

        internal DettagliVia[] GetListaVie()
        {
            var retVal = new List<DettagliVia>();

            var qTable = new Ra012Table(this._tablePrefix);
            var sql = String.Format("select distinct substr(TOPONIMO, 0, instr(TOPONIMO, ' ')) as TOPONIMO, substr(TOPONIMO, instr(TOPONIMO, ' ')) as INDIRIZZO, COD_VIA as CODICEVIARIO from {0} where toponimo is not null", qTable.ToString());

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                }

                using (IDbCommand cmd = this._db.CreateCommand(sql))
                {
                    IDataReader reader = cmd.ExecuteReader();
                    if (reader != null)
                    {
                        while (reader.Read())
                        {
                            retVal.Add(new DettagliVia
                            {
                                Toponimo = reader["TOPONIMO"].ToString(),
                                Denominazione = reader["INDIRIZZO"].ToString(),
                                CodiceViario = reader["CODICEVIARIO"] == null ? "" : reader["CODICEVIARIO"].ToString()
                            });
                        }
                    }
                }

                return retVal.ToArray();
            }
            finally
            {
                this._db.Connection.Close();
            }

        }

        internal Ravenna2ResultSet GetListaEsponenti(string codVia, string civico)
        {
            var sql = new DistinctQueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);

            sql.Select(ra012.Esponente);
            sql.From(ra012);
            sql.WhereEqualParametric(ra012.CodiceVia, codVia);
            sql.WhereEqualParametric(ra012.Civico, civico);
            sql.OrderBy(ra012.Esponente);

            return GetMultipleResults(sql);
        }

        internal Ravenna2ResultSet GetListaSezioni()
        {
            var sql = new DistinctQueryBuilder(this._db);
            var paricelleCatastali = new ParicelleCatastaliTable(this._tablePrefix);

            sql.Select(paricelleCatastali.Sezione);
            sql.From(paricelleCatastali);
            sql.WhereEqualParametric(paricelleCatastali.Livello, Constants.Particella);

            return GetMultipleResults(sql);
        }

        internal Ravenna2ResultSet GetListaSezioni(string codVia, string civico, string esponente)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia ==_codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente))
            {
                return GetListaSezioni();
            }

            var sql = new DistinctQueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);

            sql.Select(ra012.Sezione);
            sql.From(ra012);

            if (!String.IsNullOrEmpty(codVia))
            {
                sql.WhereEqualParametric(ra012.CodiceVia, codVia);
            }

            if (!String.IsNullOrEmpty(civico))
            {
                sql.WhereEqualParametric(ra012.Civico, civico);
            }

            if (!String.IsNullOrEmpty(esponente))
            {
                sql.WhereEqualParametric(ra012.Esponente, esponente);
            }

            sql.OrderBy(ra012.Sezione);

            return GetMultipleResults(sql);
        }

        internal Ravenna2ResultSet GetListaFogli(string sezione)
        {
            var sql = new DistinctQueryBuilder(this._db);
            var paricelleCatastali = new ParicelleCatastaliTable(this._tablePrefix);

            sql.Select(paricelleCatastali.Foglio);
            sql.From(paricelleCatastali);
            sql.WhereEqualParametric(paricelleCatastali.Livello, Constants.Particella);
            sql.WhereEqualParametric(paricelleCatastali.Sezione, sezione);

            return GetMultipleResults(sql);
        }

        internal Ravenna2ResultSet GetListaFogli(string codVia, string civico, string esponente, string sezione)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == _codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione))
            {
                return GetListaFogli(sezione);
            }

            var sql = new DistinctQueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);

            sql.Select(ra012.Foglio);
            sql.From(ra012);

            if (!String.IsNullOrEmpty(codVia))
            {
                sql.WhereEqualParametric(ra012.CodiceVia, codVia);
            }

            if (!String.IsNullOrEmpty(civico))
            {
                sql.WhereEqualParametric(ra012.Civico, civico);
            }

            if (!String.IsNullOrEmpty(esponente))
            {
                sql.WhereEqualParametric(ra012.Esponente, esponente);
            }

            if (!String.IsNullOrEmpty(sezione))
            {
                sql.WhereEqualParametric(ra012.Sezione, sezione);
            }

            sql.OrderBy(ra012.Foglio);

            return GetMultipleResults(sql);
        }

        internal Ravenna2ResultSet GetListaParticelle(string sezione, string foglio)
        {
            var sql = new DistinctQueryBuilder(this._db);
            var paricelleCatastali = new ParicelleCatastaliTable(this._tablePrefix);

            sql.Select(paricelleCatastali.Numero);
            sql.From(paricelleCatastali);
            sql.WhereEqualParametric(paricelleCatastali.Livello, Constants.Particella);
            sql.WhereEqualParametric(paricelleCatastali.Sezione, sezione);
            sql.WhereEqualParametric(paricelleCatastali.Foglio, foglio);

            return GetMultipleResults(sql);
        }

        internal Ravenna2ResultSet GetListaParticelle(string codVia, string civico, string esponente, string sezione, string foglio)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == _codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione) && !String.IsNullOrEmpty(foglio))
            {
                return GetListaParticelle(sezione, foglio);
            }

            var sql = new DistinctQueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);

            sql.Select(ra012.Particella);
            sql.From(ra012);

            if (!String.IsNullOrEmpty(codVia))
            {
                sql.WhereEqualParametric(ra012.CodiceVia, codVia);
            }

            if (!String.IsNullOrEmpty(civico))
            {
                sql.WhereEqualParametric(ra012.Civico, civico);
            }

            if (!String.IsNullOrEmpty(esponente))
            {
                sql.WhereEqualParametric(ra012.Esponente, esponente);
            }

            if (!String.IsNullOrEmpty(sezione))
            {
                sql.WhereEqualParametric(ra012.Sezione, sezione);
            }

            if (!String.IsNullOrEmpty(foglio))
            {
                sql.WhereEqualParametric(ra012.Foglio, foglio);
            }

            sql.OrderBy(ra012.Particella);

            return GetMultipleResults(sql);
        }

        internal Ravenna2Result GetCivico(string codVia, string civico)
        {
            if (String.IsNullOrEmpty(civico))
            {
                return new Ravenna2Result(true);
            }

            var query = new QueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);
            var ra147 = new Ra147Table(this._tablePrefix);

            query.Select(ra012.AllFields());
            query.Select(ra147.AllFields());

            query.From(ra012);
            query.From(ra147);

            query.AddJoin(ra012.CodiceSezione, ra147.CodiceSezione);
            query.WhereEqualParametric(ra012.CodiceVia, codVia);
            query.WhereEqualParametric(ra012.Civico, civico);

            return GetSingleRa012Result(query);
        }

        internal Ravenna2Result GetEsponente(string codVia, string civico, string esponente)
        {
            if (String.IsNullOrEmpty(esponente))
            {
                return new Ravenna2Result(true);
            }

            var query = new QueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);
            var ra147 = new Ra147Table(this._tablePrefix);

            query.Select(ra012.AllFields());
            query.Select(ra147.AllFields());

            query.From(ra012);
            query.From(ra147);

            query.AddJoin(ra012.CodiceSezione, ra147.CodiceSezione);
            query.WhereEqualParametric(ra012.CodiceVia, codVia);
            query.WhereEqualParametric(ra012.Civico, civico);
            query.WhereEqualParametric(ra012.Esponente, esponente);

            return GetSingleRa012Result(query);
        }

        internal Ravenna2Result GetSezione(string sezione)
        {
            if (String.IsNullOrEmpty(sezione))
            {
                return new Ravenna2Result(true);
            }

            var query = new QueryBuilder(this._db);
            var paricelleCatastali = new ParicelleCatastaliTable(this._tablePrefix);
            query.Select(paricelleCatastali.Sezione);
            query.From(paricelleCatastali);
            query.WhereEqualParametric(paricelleCatastali.Sezione, sezione);
            return GetSingleParicelleCatastaliResult(query);
        }

        internal Ravenna2Result GetFoglio(string sezione, string foglio)
        {
            if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio))
            {
                return new Ravenna2Result(true);
            }

            var query = new QueryBuilder(this._db);
            var paricelleCatastali = new ParicelleCatastaliTable(this._tablePrefix);
            query.Select(paricelleCatastali.Foglio);
            query.From(paricelleCatastali);
            query.WhereEqualParametric(paricelleCatastali.Sezione, sezione);
            query.WhereEqualParametric(paricelleCatastali.Foglio, foglio);
            return GetSingleParicelleCatastaliResult(query);
        }

        internal Ravenna2Result GetParticella(string sezione, string foglio, string particella)
        {
            if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella))
            {
                return new Ravenna2Result(true);
            }

            var query = new QueryBuilder(this._db);
            var paricelleCatastali = new ParicelleCatastaliTable(this._tablePrefix);
            query.Select(paricelleCatastali.Numero);
            query.From(paricelleCatastali);
            query.WhereEqualParametric(paricelleCatastali.Sezione, sezione);
            query.WhereEqualParametric(paricelleCatastali.Foglio, foglio);
            query.WhereEqualParametric(paricelleCatastali.Numero, particella);
            return GetSingleParicelleCatastaliResult(query);
        }

        internal Ravenna2Result GetParticella(string codVia, string civico, string esponente, string sezione, string foglio, string particella)
        {
            if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella))
            {
                return new Ravenna2Result(true);
            }

            if (String.IsNullOrEmpty(codVia) || codVia == "0" || String.IsNullOrEmpty(civico))
            {
                return GetParticella(sezione, foglio, particella);
            }

            var query = new QueryBuilder(this._db);
            var ra012 = new Ra012Table(this._tablePrefix);
            var ra147 = new Ra147Table(this._tablePrefix);

            query.Select(ra012.AllFields());
            query.Select(ra147.AllFields());

            query.From(ra012);
            query.From(ra147);

            query.AddJoin(ra012.CodiceSezione, ra147.CodiceSezione);
            query.WhereEqualParametric(ra012.CodiceVia, codVia);
            query.WhereEqualParametric(ra012.Civico, civico);

            if (!String.IsNullOrEmpty(esponente))
            {
                query.WhereEqualParametric(ra012.Esponente, esponente);
            }

            query.WhereEqualParametric(ra012.Sezione, sezione);
            query.WhereEqualParametric(ra012.Foglio, foglio);
            query.WhereEqualParametric(ra012.Particella, particella);

            return GetSingleRa012Result(query);
        }
        
        private Ravenna2ResultSet GetMultipleResults(QueryBuilder qb)
        {
            try
            {
                qb.LogDebug(this._log);

                this._db.Connection.Open();

                using (var cmd = this._db.CreateCommand(qb.ToString()))
                {
                    foreach (var par in qb.GetParameters())
                    {
                        cmd.Parameters.Add(par);
                    }

                    using (var dr = cmd.ExecuteReader())
                    {
                        return new Ravenna2ResultSet(dr);
                    }
                }
            }
            finally
            {
                this._db.Connection.Close();
            }
        }

        private Ravenna2Result GetSingleRa012Result(QueryBuilder qb)
        {
            try
            {
                qb.LogDebug(this._log);

                this._db.Connection.Open();

                using (var cmd = this._db.CreateCommand(qb.ToString()))
                {
                    foreach (var par in qb.GetParameters())
                    {
                        cmd.Parameters.Add(par);
                    }

                    using (var dr = cmd.ExecuteReader())
                    {
                        if (!dr.Read())
                        {
                            this._log.DebugFormat("GetSingleResult: La ricerca effettuata non ha restituito risultati");

                            return new Ravenna2EmptyResult();
                        }

                        var result = new Ra012Result(dr);

                        if (dr.Read())
                        {
                            this._log.DebugFormat("GetSingleResult: La ricerca effettuata ha restituito più di un risultato");

                            return new Ravenna2MultipleResultsFound();
                        }

                        return result;
                    }
                }
            }
            finally
            {
                this._db.Connection.Close();
            }
        }

        private Ravenna2Result GetSingleParicelleCatastaliResult(QueryBuilder qb)
        {
            try
            {
                qb.LogDebug(this._log);

                this._db.Connection.Open();

                using (var cmd = this._db.CreateCommand(qb.ToString()))
                {
                    foreach (var par in qb.GetParameters())
                    {
                        cmd.Parameters.Add(par);
                    }

                    using (var dr = cmd.ExecuteReader())
                    {
                        if (!dr.Read())
                        {
                            this._log.DebugFormat("GetSingleResult: La ricerca effettuata non ha restituito risultati");

                            return new Ravenna2EmptyResult();
                        }

                        var result = new ParicelleCatastaliResult(dr);

                        if (dr.Read())
                        {
                            this._log.DebugFormat("GetSingleResult: La ricerca effettuata ha restituito più di un risultato");

                            return new Ravenna2MultipleResultsFound();
                        }

                        return result;
                    }
                }
            }
            finally
            {
                this._db.Connection.Close();
            }
        }
        
        private IDbDataParameter Parameter(string nomeCampo, string valore)
        {
            return this._db.CreateParameter(nomeCampo, valore);
        }

        private string TableName(object tabName)
        {
            return String.Format("{0}.{1}", this._tablePrefix, tabName);
        }
    }
}
