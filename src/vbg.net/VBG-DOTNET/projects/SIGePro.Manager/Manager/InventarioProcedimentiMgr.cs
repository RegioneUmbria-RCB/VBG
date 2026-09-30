using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager.Utils.Extensions;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager
{
    ///<summary>
    /// Descrizione di riepilogo per InventarioProcedimentiMgr.\n	
    /// </summary>
    [DataObject]
    public partial class InventarioProcedimentiMgr
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(InventarioProcedimentiMgr));

        public InventarioProcedimenti GetByIdEndoprocedimentoMappato(string idComune, string idRemoto)
        {
            var sql = @"SELECT 
                          inventarioprocedimenti.*
                        FROM 
                          inventarioprocedimenti
                            INNER JOIN inventarioprocedimentipeople ON
                              inventarioprocedimenti.idcomune = inventarioprocedimentipeople.idcomune AND 
                              inventarioprocedimenti.codiceinventario = inventarioprocedimentipeople.codiceinventario
                        WHERE 
                          inventarioprocedimentipeople.idcomune = {0} AND
                          inventarioprocedimentipeople.cod_proc_people = {1}";

            sql = this.PreparaQueryParametrica(sql, "idcomune", "idRemoto");

            return this.ExecuteInConnection(() =>
            {
                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idRemoto", idRemoto));

                    return this.db.GetClassList<InventarioProcedimenti>(cmd).FirstOrDefault();
                }
            });

        }

        public List<InventarioProcedimenti> GetListByCodiceIstanza(string idComune, string codiceIstanza)
        {
            string sql = @"SELECT inventarioprocedimenti.* 
                            FROM istanzeprocedimenti 
                            INNER JOIN inventarioprocedimenti 
                            ON istanzeprocedimenti.idcomune = inventarioprocedimenti.idcomune 
                            AND istanzeprocedimenti.codiceinventario = inventarioprocedimenti.codiceinventario 
                            WHERE istanzeprocedimenti.idcomune = {0} 
                            AND istanzeprocedimenti.codiceistanza = {1}";

            sql = this.PreparaQueryParametrica(sql, "IdComune", "CodiceIstanza");

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("CodiceIstanza", codiceIstanza));

                    // return db.GetClassList(cmd, new InventarioProcedimenti(), false, true).ToList<InventarioProcedimenti>();
                    return this.db.GetClassList<InventarioProcedimenti>(cmd, new GetClassListFlags
                    {
                        UseForeign = PersonalLib2.Sql.useForeignEnum.No,
                        SingleRowException = false
                    });
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }


        }
        /*
        public List<InventarioProcedimenti> GetListByTipo(string idComune, string software, string tipoFamiglia, string tipoEndo)
        {
            string sql = @"
SELECT  inventarioProcedimenti.*
FROM    inventarioProcedimenti,
        tipiendo
WHERE   tipiendo.idcomune                = inventarioProcedimenti.idcomune
    AND tipiendo.codice                  = inventarioProcedimenti.codiceTipo
    AND inventarioProcedimenti.idcomune  = {0}
    AND (inventarioProcedimenti.software = {1}
     OR inventarioProcedimenti.software  = 'TT')
    AND tipiendo.codice LIKE {2}";

            if (!String.IsNullOrEmpty(tipoFamiglia))
                sql += " AND tipiendo.codicefamigliaendo = " + db.Specifics.QueryParameterName("codiceFamiglia");

            sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
                                     db.Specifics.QueryParameterName("software"),
                                     db.Specifics.QueryParameterName("tipoEndo"));


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
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("software", software));
                    cmd.Parameters.Add(db.CreateParameter("tipoEndo", String.IsNullOrEmpty(tipoEndo) ? "%" : tipoEndo));

                    if (!String.IsNullOrEmpty(tipoFamiglia))
                        cmd.Parameters.Add(db.CreateParameter("codiceFamiglia", tipoFamiglia));

                    return db.GetClassList(cmd, new InventarioProcedimenti(), false, true).ToList<InventarioProcedimenti>();
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }


        }

         public List<InventarioProcedimenti> GetEndoprocedimentiList(string idComune, List<int> listaCodici)
        {
            if (listaCodici.Count == 0)
                return new List<InventarioProcedimenti>();

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var sb = new StringBuilder();

                listaCodici.ForEach(x =>
               {
                   if (sb.Length > 0)
                       sb.Append(",");
                   sb.Append(x);
               });

                string sql = PreparaQueryParametrica(@"SELECT * FROM inventarioprocedimenti where idcomune={0} ", "idComune");

                sql += " and codiceinventario in (" + sb.ToString() + ") order by procedimento asc";

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));

                    return db.GetClassList<InventarioProcedimenti>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }
        }
        */
        public IEnumerable<SchedaDinamicaDto> FvgGetSchedeDinamicheDaEndoprocedimento(string idComune, int idEndo)
        {
            string sql = $@"SELECT 
									inventarioprocdyn2modellit.codiceinventario,
									dyn2_modellit.id,
                                    dyn2_modellit.codice_scheda,
									dyn2_modellit.descrizione,
									flag_tipofirma,
									flag_facoltativa,
                                    flag_fvg_mostra_nel_back,
                                    inventarioprocdyn2modellit.ordine,
                                    flag_pubblica
                                FROM
									inventarioprocdyn2modellit
										JOIN dyn2_modellit ON
											dyn2_modellit.idcomune = inventarioprocdyn2modellit.idcomune AND
											dyn2_modellit.id = inventarioprocdyn2modellit.fk_d2mt_id
								WHERE
									inventarioprocdyn2modellit.idcomune = {this.db.Specifics.QueryParameterName("idComune")} and
                                    inventarioprocdyn2modellit.codiceinventario = {this.db.Specifics.QueryParameterName("idEndo")}
                                order by inventarioprocdyn2modellit.ordine asc, dyn2_modellit.descrizione asc";


            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", idComune);
                    mp.AddParameter("idendo", idEndo);
                },
                dr => new SchedaDinamicaDto
                {
                    Id = dr.GetInt("id").Value,
                    CodiceScheda = dr.GetString("codice_scheda"),
                    Descrizione = dr.GetString("descrizione"),
                    TipoFirma = (TipoFirmaEnum)dr.GetInt("flag_tipofirma").GetValueOrDefault(0),
                    Facoltativa = dr.GetInt("flag_facoltativa").GetValueOrDefault(0) == 1,
                    FvgMostraNelBackoffice = dr.GetInt("flag_fvg_mostra_nel_back").GetValueOrDefault(0) == 1,
                    Ordine = dr.GetInt("ordine").GetValueOrDefault(9999),
                    Pubblica = dr.GetInt("flag_pubblica").GetValueOrDefault(0) == 1
                });
        }

        public List<SchedaDinamicaEndoprocedimentoDto> GetSchedeDinamicheDaEndoprocedimentiList(string idComune, List<int> listaCodiciEndo, IEnumerable<string> listaTipiLocalizzazioni, bool ignoraTipiLocalizzazioni)
        {
            if (listaCodiciEndo.Count == 0)
                return new List<SchedaDinamicaEndoprocedimentoDto>();


            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sb = new StringBuilder();

                listaCodiciEndo.ForEach(x =>
                {
                    if (sb.Length > 0)
                        sb.Append(",");
                    sb.Append(x);
                });

                string sql = this.PreparaQueryParametrica(@"SELECT 
															inventarioprocdyn2modellit.codiceinventario,
															dyn2_modellit.id,
                                                            dyn2_modellit.codice_scheda,
															dyn2_modellit.descrizione,
															flag_tipofirma,
															flag_facoltativa,
                                                            flag_fvg_mostra_nel_back
                                                       FROM
															inventarioprocdyn2modellit
																JOIN dyn2_modellit ON
																	dyn2_modellit.idcomune = inventarioprocdyn2modellit.idcomune AND
																	dyn2_modellit.id = inventarioprocdyn2modellit.fk_d2mt_id
																left JOIN tipi_localizzazioni ON
																	tipi_localizzazioni.idcomune = inventarioprocdyn2modellit.idcomune AND
																	tipi_localizzazioni.id = inventarioprocdyn2modellit.fk_tipilocalizzazione_id 
														WHERE
														  inventarioprocdyn2modellit.idcomune = {0}  and 
														  inventarioprocdyn2modellit.FLAG_PUBBLICA = 1", "idComune");

                var filtroLocalizzazioni = String.Empty;

                if (!ignoraTipiLocalizzazioni)
                {
                    var sbTipiLocalizzazioni = new StringBuilder(" and (tipi_localizzazioni.descrizione is null ");

                    if (listaTipiLocalizzazioni.Count() > 0)
                    {

                        sbTipiLocalizzazioni.
                            Append(" or ").
                            Append(this.db.Specifics.UCaseFunction("tipi_localizzazioni.descrizione")).
                            Append(" in ('").
                            Append(String.Join("','", listaTipiLocalizzazioni.Select(x => x.ToUpper().Replace("'", "\\'")))).
                            Append("')");

                    }

                    sbTipiLocalizzazioni.Append(")");

                    filtroLocalizzazioni = sbTipiLocalizzazioni.ToString();
                }
                sql += filtroLocalizzazioni;
                sql += " and inventarioprocdyn2modellit.codiceinventario IN (" + sb.ToString() + ") order by inventarioprocdyn2modellit.ordine, dyn2_modellit.descrizione asc";


                var rVal = new List<SchedaDinamicaEndoprocedimentoDto>();

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));

                    using (var dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            var codInventario = Convert.ToInt32(dr["codiceinventario"]);
                            var idModello = Convert.ToInt32(dr["id"]);
                            var codice = dr["codice_scheda"].ToString();
                            var desModello = dr["descrizione"].ToString();
                            var tipoFirma = TipoFirmaEnum.NessunaFirma;
                            var objTipoFirma = dr["flag_tipofirma"];
                            var facoltativa = dr["flag_facoltativa"].ToString() == String.Empty ? false : dr["flag_facoltativa"].ToString() == "1";
                            var fvgMostraNelBack = dr["flag_fvg_mostra_nel_back"].ToString() == String.Empty ? false : dr["flag_fvg_mostra_nel_back"].ToString() == "1";

                            if (objTipoFirma != DBNull.Value)
                                tipoFirma = (TipoFirmaEnum)Convert.ToInt32(objTipoFirma);

                            var scheda = new SchedaDinamicaEndoprocedimentoDto
                            {
                                CodiceEndo = codInventario,
                                Id = idModello,
                                CodiceScheda = codice,
                                Descrizione = desModello,
                                TipoFirma = tipoFirma,
                                Facoltativa = facoltativa,
                                FvgMostraNelBackoffice = fvgMostraNelBack
                            };

                            rVal.Add(scheda);
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

        public string GetNaturaBaseDaIdEndoprocedimento(string idComune, int idEndoprocedimento)
        {
            var sql = this.PreparaQueryParametrica(@"SELECT 
                          naturaendo.naturabase
                        FROM
                          inventarioprocedimenti
                            INNER JOIN naturaendo ON
                              naturaendo.idcomune = inventarioprocedimenti.idcomune AND
                              naturaendo.codicenatura = inventarioprocedimenti.codicenatura
                        WHERE
                          inventarioprocedimenti.idcomune = {0} AND
                          inventarioprocedimenti.codiceinventario = {1}", "idComune", "codiceinventario");

            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("codiceinventario", idEndoprocedimento);
                },
                dr => dr.GetString("naturabase")
            ).FirstOrDefault();
        }
        /*
        public IEnumerable<EndoBreveFrontoffice> GetEndoprocedimentiPropostiDaCodiceIntervento(string idComune, int codiceIntervento)
        {

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                string sql = @"SELECT 
								inventarioprocedimenti.codiceinventario,
								inventarioProcedimenti.procedimento
							FROM 
								alberoproc_endo,
								inventarioprocedimenti 
							WHERE 
								inventarioprocedimenti.idcomune = alberoproc_endo.idcomune AND 
								inventarioprocedimenti.codiceinventario = alberoproc_endo.codiceinventario AND  
								alberoproc_endo.idComune={0} AND
								alberoproc_endo.fkscid={1} AND 
								flag_richiesto = 1 and
								inventarioprocedimenti.disabilitato = 0";

                sql = PreparaQueryParametrica(sql, "IdComune", "CodiceIntervento");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {

                    cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("CodiceIntervento", codiceIntervento));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<EndoBreveFrontoffice>();

                        while (dr.Read())
                        {
                            var el = new EndoBreveFrontoffice
                            {
                                Codice = Convert.ToInt32(dr["codiceinventario"]),
                                Descrizione = dr["procedimento"].ToString()
                            };

                            rVal.Add(el);
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
        */
        public List<OnereDto> GetOneriDaCodiceEndo(string idComune, int codiceEndo)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"select 
								tipicausalioneri.co_id as codice,
								tipicausalioneri.co_descrizione as descrizione,
								inventarioprocedimentioneri.importo as importo,
								inventarioprocedimenti.codiceinventario as codiceprocedimento,
								inventarioprocedimenti.procedimento as procedimento,
								inventarioprocedimentioneri.note as note,
                                inventarioprocedimentioneri.DYN2CAMPI_ONERI_FRONT as D2C
							from 
								inventarioprocedimenti,
								inventarioprocedimentioneri,
								tipicausalioneri
							where
								inventarioprocedimenti.idcomune = inventarioprocedimentioneri.idcomune and
								inventarioprocedimenti.codiceinventario = inventarioprocedimentioneri.codiceinventario and
								tipicausalioneri.idcomune = inventarioprocedimentioneri.idcomune and
								tipicausalioneri.co_id = inventarioprocedimentioneri.fk_coid and
								inventarioprocedimentioneri.idcomune = {0} and
								inventarioprocedimentioneri.codiceinventario = {1}";

                sql = this.PreparaQueryParametrica(sql, "idComune", "codiceinventario");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceinventario", codiceEndo));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<OnereDto>();

                        while (dr.Read())
                        {
                            var el = new OnereDto
                            {
                                Codice = Convert.ToInt32(dr["Codice"]),
                                Descrizione = dr["descrizione"].ToString(),
                                Importo = dr["importo"] == DBNull.Value ? 0.0f : Convert.ToSingle(dr["importo"]),
                                OrigineOnere = "E",
                                CodiceInterventoOEndoOrigine = Convert.ToInt32(dr["codiceprocedimento"]),
                                InterventoOEndoOrigine = dr["procedimento"].ToString(),
                                Note = dr["note"].ToString(),
                                CampoDinamico = dr["D2C"] == DBNull.Value ? (int?)null : Convert.ToInt32(dr["D2C"])
                            };

                            rVal.Add(el);
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

        public List<BaseDto<int, string>> GetFamiglieEndoFrontoffice(string idComune, string software)
        {
            return this.GetInfoEndoPerFrontoffice(idComune, software, "tipifamiglieendo.codice,tipifamiglieendo.tipo", "tipifamiglieendo.ordine", "tipifamiglieendo.flag_pubblica=1");
        }

        public List<BaseDto<int, string>> GetCategorieEndoFrontoffice(string idComune, string software, int codiceFamiglia)
        {
            var where = "tipiendo.flag_pubblica=1 and " + (codiceFamiglia == -1 ? "tipiendo.codicefamigliaendo is null" : "tipiendo.codicefamigliaendo=" + codiceFamiglia);

            return this.GetInfoEndoPerFrontoffice(idComune, software, "tipiendo.codice,tipiendo.tipo", "tipiendo.ordine", where);
        }

        public List<BaseDto<int, string>> GetEndoFrontoffice(string idComune, string software, int codiceCategoria)
        {
            var where = "inventarioprocedimenti.flag_pubblica=1 and " + (codiceCategoria == -1 ? "inventarioprocedimenti.codicetipo is null" : "inventarioprocedimenti.codicetipo=" + codiceCategoria);

            return this.GetInfoEndoPerFrontoffice(idComune, software, "inventarioprocedimenti.codiceinventario,inventarioprocedimenti.procedimento", "inventarioprocedimenti.ordine", where);
        }

        private List<BaseDto<int, string>> GetInfoEndoPerFrontoffice(string idComune, string software, string campiSelect, string campoOrderBy, string condizioneWhere = "")
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = "select distinct " + campiSelect + ", " + campoOrderBy + " " +
@"FROM 
    inventarioprocedimenti 
        left JOIN tipiendo ON
            inventarioprocedimenti.idcomune = tipiendo.idcomune AND
            inventarioprocedimenti.codicetipo = tipiendo.codice 
        left JOIN tipifamiglieendo ON
            tipiendo.idcomune = tipifamiglieendo.idcomune AND
            tipiendo.codicefamigliaendo = tipifamiglieendo.codice
WHERE
	inventarioprocedimenti.idcomune = {0} and
	inventarioprocedimenti.disabilitato = 0 and
	(inventarioprocedimenti.software='TT' or inventarioprocedimenti.software={1}) " + (String.IsNullOrEmpty(condizioneWhere) ? "" : "and " + condizioneWhere) + @"
order by " + campoOrderBy;

                sql = this.PreparaQueryParametrica(sql, "idcomune", "software");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("software", software));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<BaseDto<int, string>>();

                        while (dr.Read())
                        {
                            var codice = dr[0].ToString();
                            var descrizione = dr[1].ToString();

                            if (String.IsNullOrEmpty(codice))
                                codice = "-1";

                            if (String.IsNullOrEmpty(descrizione))
                                descrizione = "Endoprocedimento";

                            rVal.Add(new BaseDto<int, string>
                            {
                                Codice = Convert.ToInt32(codice),
                                Descrizione = descrizione
                            });
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
    }
}



