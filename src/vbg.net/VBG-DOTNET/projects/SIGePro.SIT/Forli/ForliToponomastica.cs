using Init.SIGePro.Sit.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Sit.Forli
{

    public class ForliToponomastica
    {
        public class ElementoListaEsponenti
        {
            public string Civico { get; set; }
            public string Lettera { get; set; }
            public string CivKey { get; set; }
        }

        public class ElementoListaInterni
        {
            public string CodiceVia { get; set; }
            public string Civico { get; set; }
            public string Lettera { get; set; }
            public string CivKey { get; set; }
            public string Interno { get; set; }
        }

        private readonly DataBase _db;

        public ForliToponomastica(string connectionString)
        {
            this._db = new DataBase(connectionString, ProviderType.OracleClient);
        }

        public DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            var sql = "select * from CED_V_VIE";

            if (filtro == FiltroRicercaListaVie.Attiva)
            {
                sql += " where (stato='ESISTENTE' or stato='FITTIZIA')";
            }

            if (filtro == FiltroRicercaListaVie.Cessata)
            {
                sql += " where stato='SOPPRESSA'";
            }

            return this._db.ExecuteReader(sql, (_) => { }, dr => new DettagliVia
            {
                Toponimo = dr.GetString("SPECIE"),
                Denominazione = dr.GetString("DENOMINAZ"),
                CodiceViario = dr.GetString("COD_VIA")
            }).ToArray();
        }

        public IEnumerable<string> GetListaCivici(string codVia)
        {
            var sql = $@"select DISTINCT civico from CED_V_CIVICI WHERE COD_VIA = {this._db.QueryParameter("codVia")} ORDER BY civico";

            return this._db.ExecuteReader(sql, mp => mp.Add("codVia", codVia), dr => dr.GetString("civico"));
        }

        public IEnumerable<ElementoListaEsponenti> GetListaEsponenti(string codVia, string civico)
        {
            var sql = $@"select 
                            civico,
                            lettera,
                            civkey
                        from 
                            CED_V_CIVICI 
                        WHERE 
                            COD_VIA = {this._db.QueryParameter("codVia")} and 
                            civico={this._db.QueryParameter("civico")} 
                        ORDER BY lettera";

            return this._db.ExecuteReader(
                sql,
                mp => mp.Add("codVia", codVia)
                        .Add("civico", civico),
                dr => new ElementoListaEsponenti
                {
                    Civico = dr.GetString("civico"),
                    Lettera = dr.GetString("lettera"),
                    CivKey = dr.GetString("civkey")
                });
        }

        public ElementoListaEsponenti GetEsponente(string codVia, string civico, string esponente)
        {
            var sql = $@"select 
                            civico,
                            lettera,
                            civkey
                        from 
                            CED_V_CIVICI 
                        WHERE 
                            COD_VIA = {this._db.QueryParameter("codVia")} and 
                            civico={this._db.QueryParameter("civico")}";

            if (string.IsNullOrEmpty(esponente))
            {
                sql += " and lettera is null";
            }
            else
            {
                sql += $" and lettera = {this._db.QueryParameter("esponente")}";
            }

            return this._db.ExecuteReader(
                sql,
                mp =>
                {
                    mp.Add("codVia", codVia)
                      .Add("civico", civico);

                    if (!string.IsNullOrEmpty(esponente))
                    {
                        mp.Add("esponente", esponente);
                    }
                },
                dr => new ElementoListaEsponenti
                {
                    Civico = dr.GetString("civico"),
                    Lettera = dr.GetString("lettera"),
                    CivKey = dr.GetString("civkey")
                }).FirstOrDefault();
        }

        public IEnumerable<ElementoListaInterni> GetListaInterniByCivKey(string civKey)
        {
            var sql = $@"SELECT
                            civkey, 
                            cod_via, 
                            civico, 
                            lettera, 
                            interno
                        FROM
                            ced_v_interni
                        WHERE
                            civkey = {this._db.QueryParameter("civkey")}
                        order by interno";

            return this._db.ExecuteReader(
                sql,
                mp => mp.Add("civkey", civKey),
                dr => new ElementoListaInterni
                {
                    CivKey = dr.GetString("civkey"),
                    CodiceVia = dr.GetString("cod_via"),
                    Civico = dr.GetString("civico"),
                    Lettera = dr.GetString("lettera"),
                    Interno = dr.GetString("interno"),
                });
        }

        public IEnumerable<ElementoListaInterni> GetListaInterniByIndirizzo(string codVia, string civico, string lettera)
        {
            var sql = $@"SELECT
                            civkey, 
                            cod_via, 
                            civico, 
                            lettera, 
                            interno
                        FROM
                            ced_v_interni
                        WHERE
                            cod_via = {this._db.QueryParameter("codVia")} and 
                            civico = {this._db.QueryParameter("civico")}";

            if (string.IsNullOrEmpty(lettera))
            {
                sql += " and lettera is null";
            }
            else
            {
                sql += $" and lettera = {this._db.QueryParameter("lettera")}";
            }

            sql += " order by interno";

            return this._db.ExecuteReader(
                sql,
                mp =>
                {
                    mp.Add("codVia", codVia)
                        .Add("civico", civico);

                    if (!string.IsNullOrEmpty(lettera))
                    {
                        mp.Add("lettera", lettera);
                    }
                },
                dr => new ElementoListaInterni
                {
                    CivKey = dr.GetString("civkey"),
                    CodiceVia = dr.GetString("cod_via"),
                    Civico = dr.GetString("civico"),
                    Lettera = dr.GetString("lettera"),
                    Interno = dr.GetString("interno"),
                });
        }

        public ElementoListaInterni GetInterno(string codVia, string civico, string lettera, string interno)
        {
            var sql = $@"SELECT
                            civkey, 
                            cod_via, 
                            civico, 
                            lettera, 
                            interno
                        FROM
                            ced_v_interni
                        WHERE
                            cod_via = {this._db.QueryParameter("codVia")} and 
                            civico = {this._db.QueryParameter("civico")} and
                            interno = {this._db.QueryParameter("interno")}";

            if (string.IsNullOrEmpty(lettera))
            {
                sql += " and lettera is null";
            }
            else
            {
                sql += $" and lettera = {this._db.QueryParameter("lettera")}";
            }

            sql += " order by interno";

            return this._db.ExecuteReader(
                sql,
                mp =>
                {
                    mp.Add("codVia", codVia)
                        .Add("civico", civico)
                        .Add("interno", interno);

                    if (!string.IsNullOrEmpty(lettera))
                    {
                        mp.Add("lettera", lettera);
                    }
                },
                dr => new ElementoListaInterni
                {
                    CivKey = dr.GetString("civkey"),
                    CodiceVia = dr.GetString("cod_via"),
                    Civico = dr.GetString("civico"),
                    Lettera = dr.GetString("lettera"),
                    Interno = dr.GetString("interno"),
                }).FirstOrDefault();
        }
    }
}
