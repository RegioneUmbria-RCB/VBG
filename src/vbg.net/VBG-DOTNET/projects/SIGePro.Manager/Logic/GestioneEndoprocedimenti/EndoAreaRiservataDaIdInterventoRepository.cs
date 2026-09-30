using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.Logic.GestioneAlberoInterventi;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{
    public class EndoAreaRiservataDaIdInterventoRepository : IEndoDaIdInterventoRepository
    {
        private enum FlagTipoEndo
        {
            Principale,
            Richiesto,
            Ricorrente
        }


        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly string _codiceComune;
        private readonly IAlberoInterventiService _alberoInterventiService;
        private readonly bool _utenteTester;

        public EndoAreaRiservataDaIdInterventoRepository(DataBase db, string idComune, string codiceComune, IAlberoInterventiService alberoInterventiService, bool utenteTester)
        {
            this._db = db;
            this._idComune = idComune;
            this._codiceComune = codiceComune;
            this._alberoInterventiService = alberoInterventiService;
            this._utenteTester = utenteTester;
        }

        private IEnumerable<FamigliaEndoprocedimentoDto> GetByIdIntervento(FlagTipoEndo tipo, int idIntervento)
        {
            var intervento = this._alberoInterventiService.GetById(idIntervento);
            var condWhereScId = intervento.GetListaScCodice().ToCondizioneWhere();

            string sql = $@"SELECT 
									tipifamiglieendo.codice as codFamiglia,
									tipifamiglieendo.tipo as desFamiglia,
									tipiendo.codice as codTipo,
									tipiendo.tipo as desTipo,
									inventarioprocedimenti.codiceinventario as codEndo,
									inventarioprocedimenti.procedimento as desEndo,
									alberoproc_endo.flag_richiesto as obbEndo,
									alberoproc_endo.flag_principale as flgPrincipale,
									tipifamiglieendo.ordine as famigliaOrdine,
									tipiendo.ordine as tipoEndoOrdine,
									inventarioprocedimenti.ordine as endoOrdine,
									inventarioprocedimenti.codicenatura as codicenatura,
                                    inventarioprocedimenti.FLAGTIPITITOLO,
                                    naturaendo.NATURA as descrizioneNatura,
                                    naturaendo.BINARIODIPENDENZE as BINARIODIPENDENZE
								FROM
									alberoproc inner join alberoproc_endo ON
										alberoproc.idComune = alberoproc_endo.idComune AND 
										alberoproc.sc_id = alberoproc_endo.fkscid

									inner join inventarioprocedimenti ON
										inventarioprocedimenti.idcomune =alberoproc_endo.idComune AND
										inventarioprocedimenti.codiceinventario =alberoproc_endo.codiceinventario

									left join tipiendo ON 
										tipiendo.idcomune = inventarioprocedimenti.idcomune AND
										tipiendo.codice = inventarioprocedimenti.codicetipo

									left join tipifamiglieendo ON 
										tipifamiglieendo.idcomune = tipiendo.idcomune AND
										tipifamiglieendo.codice = tipiendo.codicefamigliaendo

                                    left outer join naturaendo on 
                                        naturaendo.codicenatura = inventarioprocedimenti.codicenatura AND
                                        naturaendo.idcomune = inventarioprocedimenti.idcomune 
								WHERE
									alberoproc.idComune = {this._db.Specifics.QueryParameterName("idComune")} AND
									alberoproc.software = '{intervento.SOFTWARE}' and 
									alberoproc.sc_codice IN ({condWhereScId}) ";

            if (!this._utenteTester)
            {
                sql += @" and alberoproc_endo.FLAG_PUBBLICA = 1 
                        and (tipiendo.codice is null or tipiendo.flag_pubblica = 1) 
                        and (tipifamiglieendo.codice is null or tipifamiglieendo.flag_pubblica = 1) ";
            }


            if (tipo == FlagTipoEndo.Principale)
            {
                sql += $" and alberoproc_endo.flag_principale = 1";
            }

            if (tipo == FlagTipoEndo.Richiesto)
            {
                sql += $" and (alberoproc_endo.flag_principale <> 1 or alberoproc_endo.flag_principale is null) and alberoproc_endo.flag_richiesto = 1 ";
            }

            if (tipo == FlagTipoEndo.Ricorrente)
            {
                sql += $" and (alberoproc_endo.flag_principale <> 1 or alberoproc_endo.flag_principale is null) and (alberoproc_endo.flag_richiesto <> 1 or alberoproc_endo.flag_richiesto is null) ";
            }

            sql += @" order by 
                        alberoproc_endo.flag_principale desc,
                        alberoproc_endo.flag_richiesto desc,
                        tipifamiglieendo.ordine,                        
                        tipiendo.ordine,
                        inventarioprocedimenti.ordine,
                        tipifamiglieendo.tipo,
                        tipiendo.tipo,
                        inventarioprocedimenti.procedimento";

            return this.EstraiEndoDaQuery(sql);

        }

        private IEnumerable<FamigliaEndoprocedimentoDto> EstraiEndoDaQuery(string sql)
        {
            var dataSet = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                },
                dr => new
                {
                    codFamiglia = dr.GetInt("codFamiglia"),
                    desFamiglia = dr.GetString("desFamiglia"),
                    codTipo = dr.GetInt("codTipo"),
                    desTipo = dr.GetString("desTipo"),
                    codEndo = dr.GetInt("codEndo"),
                    desEndo = dr.GetString("desEndo"),
                    obbEndo = dr.GetString("obbEndo") == "1",
                    flgPrincipale = dr.GetString("flgPrincipale") == "1",
                    famigliaOrdine = dr.GetInt("famigliaOrdine").GetValueOrDefault(9999),
                    tipoEndoOrdine = dr.GetInt("tipoEndoOrdine").GetValueOrDefault(9999),
                    endoOrdine = dr.GetInt("endoOrdine").GetValueOrDefault(9999),
                    codicenatura = dr.GetInt("codicenatura"),
                    descrizioneNatura = dr.GetString("descrizioneNatura"),
                    tipoTitoloObbligatorio = dr.GetString("FLAGTIPITITOLO") == "1",
                    binarioDipendenze = dr.GetInt("BINARIODIPENDENZE").GetValueOrDefault(0)
                });

            var famiglieEndoTrovate = new List<FamigliaEndoprocedimentoDto>();
            var listaFamiglie = new Dictionary<int, FamigliaEndoprocedimentoDto>();
            var listaTipi = new Dictionary<int, TipoEndoprocedimentoDto>();

            dataSet.ToList().ForEach(endo =>
            {
                var codFamiglia = endo.codFamiglia.GetValueOrDefault(-1);
                var desFamiglia = endo.codFamiglia.HasValue ? endo.desFamiglia : "Endoprocedimenti";

                var codTipo = endo.codTipo.GetValueOrDefault(-1);
                var desTipo = endo.codTipo.HasValue ? endo.desTipo : "Endoprocedimenti";

                if (!listaFamiglie.ContainsKey(codFamiglia))
                {
                    var famiglia = new FamigliaEndoprocedimentoDto { Codice = codFamiglia, Descrizione = desFamiglia };
                    famiglia.Ordine = endo.famigliaOrdine;
                    listaFamiglie.Add(codFamiglia, famiglia);
                    famiglieEndoTrovate.Add(famiglia);
                }

                if (!listaTipi.ContainsKey(codTipo))
                {
                    var tipo = new TipoEndoprocedimentoDto { Codice = codTipo, Descrizione = desTipo };
                    tipo.Ordine = endo.tipoEndoOrdine;
                    listaTipi.Add(codTipo, tipo);
                    listaFamiglie[codFamiglia].TipiEndoprocedimenti.Add(tipo);
                }

                var nuovoEndo = new EndoprocedimentoDto
                {
                    Codice = endo.codEndo.Value,
                    Descrizione = endo.desEndo,
                    Richiesto = endo.obbEndo,
                    Principale = endo.flgPrincipale,
                    Ordine = endo.endoOrdine,
                    BinarioDipendenze = endo.binarioDipendenze,
                    CodiceNatura = endo.codicenatura,
                    Natura = endo.descrizioneNatura,
                    TipoTitoloObbligatorio = endo.tipoTitoloObbligatorio,
                    SubEndo = this.LeggiSubEndo(endo.codEndo.Value).ToList()
                };


                listaTipi[codTipo].Endoprocedimenti.Add(nuovoEndo);

            });

            return famiglieEndoTrovate;
        }


        private IEnumerable<FamigliaEndoprocedimentoDto> LeggiSubEndo(int idEndo)
        {
            if (String.IsNullOrEmpty(this._codiceComune))
            {
                return Enumerable.Empty<FamigliaEndoprocedimentoDto>();
            }

            var sql = $@"SELECT 
                            tipifamiglieendo.codice as codFamiglia,
                            tipifamiglieendo.tipo as desFamiglia,
                            tipiendo.codice as codTipo,
                            tipiendo.tipo as desTipo,
                            inventarioprocedimenti.codiceinventario as codEndo,
                            inventarioprocedimenti.procedimento as desEndo,
                            inventarioproc_endo.flag_necessario as endo_richiesto,
                            0 as flgPrincipale,
                            tipifamiglieendo.ordine as famigliaOrdine,
                            tipiendo.ordine as tipoEndoOrdine,
                            inventarioprocedimenti.ordine as endoOrdine,
                            inventarioprocedimenti.codicenatura as codicenatura,
                            inventarioprocedimenti.FLAGTIPITITOLO,
                            naturaendo.NATURA as descrizioneNatura,
                            naturaendo.BINARIODIPENDENZE as BINARIODIPENDENZE,
                            inventarioproc_endo.ordine AS ordine
                        FROM
                            inventarioproc_endo

                            inner join inventarioprocedimenti ON
                                inventarioprocedimenti.idcomune = inventarioproc_endo.idcomune AND
                                inventarioprocedimenti.codiceinventario = inventarioproc_endo.codiceinventario_d

                            left join tipiendo ON
                                tipiendo.idcomune = inventarioprocedimenti.idcomune AND
                                tipiendo.codice = inventarioprocedimenti.codicetipo

                            left join tipifamiglieendo ON
                                tipifamiglieendo.idcomune = tipiendo.idcomune AND
                                tipifamiglieendo.codice = tipiendo.codicefamigliaendo

                            left outer join naturaendo on 
                                naturaendo.codicenatura = inventarioprocedimenti.codicenatura AND
                                naturaendo.idcomune = inventarioprocedimenti.idcomune 

                        WHERE
                            inventarioproc_endo.idComune = {this._db.Specifics.QueryParameterName("idComune")} AND
                            inventarioproc_endo.codiceinventario_t = {this._db.Specifics.QueryParameterName("idEndo")} and
                            ( inventarioproc_endo.codicecomune = {this._db.Specifics.QueryParameterName("codiceComune")} or inventarioproc_endo.codicecomune is null) ";

            if (!this._utenteTester)
            {
                sql += "and inventarioproc_endo.FLAG_PUBBLICA = 1 ";
            }

            sql += @"order by 
                        inventarioproc_endo.flag_necessario desc,
                        inventarioproc_endo.ordine";

            var dataSet = this._db.ExecuteReader(sql,
                            mp =>
                            {
                                mp.AddParameter("idComune", this._idComune);
                                mp.AddParameter("idEndo", idEndo);
                                mp.AddParameter("codiceComune", this._codiceComune);
                            },
                            dr => new
                            {
                                codFamiglia = dr.GetInt("codFamiglia"),
                                desFamiglia = dr.GetString("desFamiglia"),
                                codTipo = dr.GetInt("codTipo"),
                                desTipo = dr.GetString("desTipo"),
                                codEndo = dr.GetInt("codEndo"),
                                desEndo = dr.GetString("desEndo"),
                                endoRichiesto = (dr.GetInt("endo_richiesto") ?? 0) == 1,
                                flgPrincipale = dr.GetString("flgPrincipale") == "1",
                                //famigliaOrdine = dr.GetInt("famigliaOrdine") ?? 9999,
                                //tipoEndoOrdine = dr.GetInt("tipoEndoOrdine") ?? 9999,
                                //endoOrdine = dr.GetInt("endoOrdine") ?? 9999,
                                codicenatura = dr.GetInt("codicenatura"),
                                descrizioneNatura = dr.GetString("descrizioneNatura"),
                                tipoTitoloObbligatorio = dr.GetString("FLAGTIPITITOLO") == "1",
                                binarioDipendenze = dr.GetInt("BINARIODIPENDENZE") ?? 0,
                                ordine = dr.GetInt("ordine") ?? 9999
                            });

            var famiglieEndoTrovate = new List<FamigliaEndoprocedimentoDto>();
            var listaFamiglie = new Dictionary<int, FamigliaEndoprocedimentoDto>();
            var listaTipi = new Dictionary<int, TipoEndoprocedimentoDto>();

            dataSet.ToList().ForEach(endo =>
            {
                var codFamiglia = endo.codFamiglia.GetValueOrDefault(-1);
                var desFamiglia = endo.codFamiglia.HasValue ? endo.desFamiglia : "Endoprocedimenti";

                var codTipo = endo.codTipo.GetValueOrDefault(-1);
                var desTipo = endo.codTipo.HasValue ? endo.desTipo : "Endoprocedimenti";

                if (!listaFamiglie.ContainsKey(codFamiglia))
                {
                    var famiglia = new FamigliaEndoprocedimentoDto { Codice = codFamiglia, Descrizione = desFamiglia };
                    listaFamiglie.Add(codFamiglia, famiglia);
                    famiglieEndoTrovate.Add(famiglia);
                }

                if (!listaTipi.ContainsKey(codTipo))
                {
                    var tipo = new TipoEndoprocedimentoDto { Codice = codTipo, Descrizione = desTipo };
                    listaTipi.Add(codTipo, tipo);
                    listaFamiglie[codFamiglia].TipiEndoprocedimenti.Add(tipo);
                }

                var nuovoEndo = new EndoprocedimentoDto
                {
                    Codice = endo.codEndo.Value,
                    Descrizione = endo.desEndo,
                    Richiesto = endo.endoRichiesto,
                    Principale = endo.flgPrincipale,
                    Ordine = endo.ordine,
                    BinarioDipendenze = endo.binarioDipendenze,
                    CodiceNatura = endo.codicenatura,
                    Natura = endo.descrizioneNatura,
                    TipoTitoloObbligatorio = endo.tipoTitoloObbligatorio,
                };

                listaTipi[codTipo].Endoprocedimenti.Add(nuovoEndo);

            });

            return famiglieEndoTrovate;
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetPrincipali(int codiceIntervento)
        {
            return this.GetByIdIntervento(FlagTipoEndo.Principale, codiceIntervento);
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetRichiesti(int codiceIntervento)
        {
            return this.GetByIdIntervento(FlagTipoEndo.Richiesto, codiceIntervento);
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetRicorrenti(int codiceIntervento)
        {
            return this.GetByIdIntervento(FlagTipoEndo.Ricorrente, codiceIntervento);
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetAltri(int idIntervento)
        {
            var intervento = this._alberoInterventiService.GetById(idIntervento);
            var condWhereScCodice = intervento.GetListaScCodice().ToCondizioneWhere();

            // prendo gli endo con il campo sc_codice più lungo (impostare "altri endo" su un ramo sovrascrive e annulla tutti i precedenti)
            var sql = $@"SELECT 
                          alberoproc.sc_codice,
                          alberoproc_arendo.fk_famigliaendo AS idFamigliaEndo,
                          alberoproc_arendo.fk_categoriaendo AS idCategoriaEndo 
                        FROM 
                          alberoproc INNER JOIN 
                              alberoproc_arendo ON 
                                  alberoproc_arendo.idcomune = alberoproc.idcomune AND
                                  alberoproc_arendo.fk_scid = alberoproc.sc_id 
                        WHERE 
                          alberoproc.idcomune = {this._db.Specifics.QueryParameterName("idcomune")} AND
                          alberoproc.sc_codice IN ({condWhereScCodice}) AND
                          alberoproc.software = {this._db.Specifics.QueryParameterName("software")}
                        ORDER BY sc_codice DESC";

            var filtriEndo = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", this._idComune);
                    mp.AddParameter("software", intervento.SOFTWARE);
                },
                dr => new
                {
                    ScCodice = dr.GetString("sc_codice"),
                    IdFamigliaEndo = dr.GetInt("idFamigliaEndo"),
                    IdCategoriaEndo = dr.GetInt("idCategoriaEndo"),
                });

            if (!filtriEndo.Any())
            {
                return new List<FamigliaEndoprocedimentoDto>();
            }

            var maxLen = filtriEndo.Max(x => x.ScCodice.Length);

            filtriEndo = filtriEndo.Where(x => x.ScCodice.Length == maxLen);

            var filtriEndoGroup = filtriEndo.GroupBy(x => x.IdFamigliaEndo, x => x.IdCategoriaEndo);

            var sqlBase = $@"SELECT 
							tipifamiglieendo.codice as codFamiglia,
							tipifamiglieendo.tipo as desFamiglia,
							tipiendo.codice as codTipo,
							tipiendo.tipo as desTipo,
							inventarioprocedimenti.codiceinventario as codEndo,
							inventarioprocedimenti.procedimento as desEndo,
							'0' as obbEndo,
							'0' as flgPrincipale,
							tipifamiglieendo.ordine as famigliaOrdine,
							tipiendo.ordine as tipoEndoOrdine,
							inventarioprocedimenti.ordine as endoOrdine,
                            naturaendo.codicenatura as codicenatura,
                            naturaendo.NATURA as descrizioneNatura,
                            naturaendo.BINARIODIPENDENZE as BINARIODIPENDENZE,
                            inventarioprocedimenti.FLAGTIPITITOLO 
						FROM
							inventarioprocedimenti 

                            inner join tipiendo ON 
								tipiendo.idcomune = inventarioprocedimenti.idcomune AND
								tipiendo.codice = inventarioprocedimenti.codicetipo

							inner join tipifamiglieendo ON 
								tipifamiglieendo.idcomune = tipiendo.idcomune AND
								tipifamiglieendo.codice = tipiendo.codicefamigliaendo

                            left outer join naturaendo on 
                                naturaendo.codicenatura = inventarioprocedimenti.codicenatura and 
                                naturaendo.idcomune = inventarioprocedimenti.idcomune
						WHERE
							inventarioprocedimenti.idcomune = {this._db.Specifics.QueryParameterName("idComune")} and
                            (inventarioprocedimenti.software = '{intervento.SOFTWARE}' or inventarioprocedimenti.software = 'TT') and ";

            if (!this._utenteTester)
            {
                sqlBase += @" inventarioprocedimenti.disabilitato = 0 and 
                            tipiendo.flag_pubblica = 1 and
                            (tipifamiglieendo.codice is null or tipifamiglieendo.flag_pubblica = 1) and ";
            }

            return filtriEndoGroup.SelectMany(filtro =>
            {
                var sqlEndo = sqlBase + " tipiendo.codicefamigliaendo = " + filtro.Key.Value.ToString();

                var listaCategorie = string.Join("','", filtro.ToList().Where(x => x.HasValue).Select(x => x.ToString()));//String.Join( 

                if (!string.IsNullOrEmpty(listaCategorie))
                {
                    sqlEndo += $" and tipiendo.codice in ('{listaCategorie}') ";
                }

                sql += " order by tipifamiglieendo.ordine,tipiendo.ordine,tipifamiglieendo.ordine,inventarioprocedimenti.ordine, inventarioprocedimenti.procedimento";


                return this.EstraiEndoDaQuery(sqlEndo);
            }).ToList();
        }
    }
}
