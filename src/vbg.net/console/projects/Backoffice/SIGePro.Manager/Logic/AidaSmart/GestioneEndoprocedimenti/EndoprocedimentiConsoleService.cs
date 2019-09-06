using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneEndoprocedimenti
{
    public class EndoprocedimentiConsoleService
    {

        private enum FlagTipoEndo
        {
            Principale,
            Attivato,
            Ricorrente
        }

        private readonly DataBase _db;
        private readonly ConsoleService _consoleService;
        private readonly string _idComuneBase;

        public EndoprocedimentiConsoleService(DataBase db, ConsoleService consoleService)
        {
            _db = db;
            _consoleService = consoleService;
            _idComuneBase = _consoleService.GetParametriSdeProxy().IdComuneBase;
        }

        public EndoprocedimentiConsole GetByIdIntervento(int idIntervento, string codiceComune, bool utenteTester)
        {

            //var parametriSde = this._consoleService.GetParametriSdeProxy();

            var apManager = new AlberoProcMgr(_db);
            var ramoAlbero = apManager.GetById(idIntervento, _idComuneBase);

            return new EndoprocedimentiConsole
            {
                Principali = GetEndoPrincipali(ramoAlbero, codiceComune, utenteTester),
                Richiesti = GetEndoAttivati(ramoAlbero, codiceComune, utenteTester),
                Ricorrenti = GetEndoRicorrenti(ramoAlbero, codiceComune, utenteTester),
                Altri = GetAltriEndoprocedimenti(ramoAlbero, codiceComune, utenteTester)
            };
        }

        private List<FamigliaEndoprocedimentoDto> GetAltriEndoprocedimenti(AlberoProc ramoAlbero, string codiceComune, bool utenteTester)
        {
            var listaScCodice = ramoAlbero.GetListaScCodice();

            var condWhere = listaScCodice.ToString();

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
                          alberoproc.idcomune = {_db.Specifics.QueryParameterName("idcomune")} AND
                          alberoproc.sc_codice IN ({condWhere}) AND
                          alberoproc.software = {_db.Specifics.QueryParameterName("software")}
                        ORDER BY sc_codice DESC";

            var filtriEndo = _db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", ramoAlbero.Idcomune);
                    mp.AddParameter("software", ramoAlbero.SOFTWARE);
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
                            naturaendobase.codicenatura as codicenatura,
                            naturaendobase.NATURA as descrizioneNatura,
                            naturaendobase.BINARIODIPENDENZE as BINARIODIPENDENZE,
                            inventarioprocedimenti.FLAGTIPITITOLO 
						FROM
							inventarioprocedimenti 

                            inner join tipiendo ON 
								tipiendo.idcomune = inventarioprocedimenti.idcomune AND
								tipiendo.codice = inventarioprocedimenti.codicetipo

							inner join tipifamiglieendo ON 
								tipifamiglieendo.idcomune = tipiendo.idcomune AND
								tipifamiglieendo.codice = tipiendo.codicefamigliaendo

                            left outer join naturaendobase on 
                                inventarioprocedimenti.codicenatura = naturaendobase.codicenatura
						WHERE
							inventarioprocedimenti.idcomune = {_db.Specifics.QueryParameterName("idComune")} and
                            (inventarioprocedimenti.software = {_db.Specifics.QueryParameterName("software")} or inventarioprocedimenti.software = 'TT') and ";

            if (!utenteTester)
            {
                sqlBase += @" inventarioprocedimenti.disabilitato = 0 and 
                            tipiendo.flag_pubblica = 1 and
                            (tipifamiglieendo.codice is null or tipifamiglieendo.flag_pubblica = 1) and ";
            }

            return filtriEndoGroup.SelectMany(filtro =>
            {
                var sqlEndo = sqlBase + " tipiendo.codicefamigliaendo = " + filtro.Key.Value.ToString();

                var listaCategorie = String.Join("','", filtro.ToList().Where(x => x.HasValue).Select(x => x.ToString()));//String.Join( 

                if (!String.IsNullOrEmpty(listaCategorie))
                {
                    sqlEndo += $" and tipiendo.codice in ('{listaCategorie}') ";
                }

                sql += " order by tipifamiglieendo.ordine,tipiendo.ordine,tipifamiglieendo.ordine,inventarioprocedimenti.ordine, inventarioprocedimenti.procedimento";


                return EstraiEndoDaQuery(sqlEndo, ramoAlbero.Idcomune, ramoAlbero.SOFTWARE, codiceComune);
            }).ToList();
        }

        private List<FamigliaEndoprocedimentoDto> GetEndoRicorrenti(AlberoProc ramoAlbero, string codiceComune, bool utenteTester)
        {
            return GetEndoByIdIntervento(ramoAlbero, FlagTipoEndo.Ricorrente, codiceComune, utenteTester);
        }

        private List<FamigliaEndoprocedimentoDto> GetEndoAttivati(AlberoProc ramoAlbero, string codiceComune, bool utenteTester)
        {
            return GetEndoByIdIntervento(ramoAlbero, FlagTipoEndo.Attivato, codiceComune, utenteTester);
        }

        private List<FamigliaEndoprocedimentoDto> GetEndoPrincipali(AlberoProc ramoAlbero, string codiceComune, bool utenteTester)
        {
            return GetEndoByIdIntervento(ramoAlbero, FlagTipoEndo.Principale, codiceComune, utenteTester);
        }

        private List<FamigliaEndoprocedimentoDto> GetEndoByIdIntervento(AlberoProc ramoAlbero, FlagTipoEndo tipo, string codiceComune, bool utenteTester)
        {
            var listaScId = ramoAlbero.GetListaScCodice();

            var condWhere = listaScId.ToString();

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
                                    naturaendobase.NATURA as descrizioneNatura,
                                    naturaendobase.BINARIODIPENDENZE as BINARIODIPENDENZE
								FROM
									alberoproc inner join alberoproc_endo ON
										alberoproc.idComune = alberoproc_endo.idComune AND 
										alberoproc.sc_id = alberoproc_endo.fkscid

									inner join inventarioprocedimenti ON
										inventarioprocedimenti.idcomune = alberoproc_endo.idComune AND
										inventarioprocedimenti.codiceinventario = alberoproc_endo.codiceinventario

									left join tipiendo ON 
										tipiendo.idcomune = inventarioprocedimenti.idcomune AND
										tipiendo.codice = inventarioprocedimenti.codicetipo

									left join tipifamiglieendo ON 
										tipifamiglieendo.idcomune = tipiendo.idcomune AND
										tipifamiglieendo.codice = tipiendo.codicefamigliaendo
                                    
                                    left outer join naturaendobase on 
                                        inventarioprocedimenti.codicenatura = naturaendobase.codicenatura

								WHERE
									alberoproc.idComune = {_db.Specifics.QueryParameterName("idComune")} AND
									alberoproc.software = {_db.Specifics.QueryParameterName("software")} and 
									alberoproc.sc_codice IN (" + condWhere + @") ";

            if (!utenteTester)
            {
                sql += @"and alberoproc_endo.FLAG_PUBBLICA = 1 
                        and (tipiendo.codice is null or tipiendo.flag_pubblica = 1) 
                        and (tipifamiglieendo.codice is null or tipifamiglieendo.flag_pubblica = 1) ";
            }

            if (tipo == FlagTipoEndo.Principale)
            {
                sql += $" and alberoproc_endo.flag_principale = 1";
            }

            if (tipo == FlagTipoEndo.Attivato)
            {
                sql += $" and (alberoproc_endo.flag_principale <> 1 or alberoproc_endo.flag_principale is null) and alberoproc_endo.flag_richiesto = 1 ";
            }

            if (tipo == FlagTipoEndo.Ricorrente)
            {
                sql += $" and (alberoproc_endo.flag_principale <> 1 or alberoproc_endo.flag_principale is null) and (alberoproc_endo.flag_richiesto <> 1 or alberoproc_endo.flag_richiesto is null) ";
            }

            sql += " order by alberoproc_endo.flag_principale desc,alberoproc_endo.flag_richiesto desc,tipiendo.ordine,tipifamiglieendo.ordine,inventarioprocedimenti.ordine, inventarioprocedimenti.procedimento";

            return EstraiEndoDaQuery(sql, _idComuneBase, ramoAlbero.SOFTWARE, codiceComune);
        }

        private List<FamigliaEndoprocedimentoDto> EstraiEndoDaQuery(string sql, string idComune, string software, string codiceComune)
        {
            var dataSet = _db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("software", software);
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
                    Richiesto = endo.obbEndo,
                    Principale = endo.flgPrincipale,
                    Ordine = endo.endoOrdine,
                    BinarioDipendenze = endo.binarioDipendenze,
                    CodiceNatura = endo.codicenatura,
                    Natura = endo.descrizioneNatura,
                    TipoTitoloObbligatorio = endo.tipoTitoloObbligatorio,
                    SubEndo = this.LeggiSubEndo(endo.codEndo.Value, codiceComune)
                };

                
                listaTipi[codTipo].Endoprocedimenti.Add(nuovoEndo);

            });

            return famiglieEndoTrovate;
        }

        private List<FamigliaEndoprocedimentoDto> LeggiSubEndo(int idEndo, string codiceComune)
        {
            var sql = $@"SELECT 
                            tipifamiglieendo.codice as codFamiglia,
                            tipifamiglieendo.tipo as desFamiglia,
                            tipiendo.codice as codTipo,
                            tipiendo.tipo as desTipo,
                            inventarioprocedimenti.codiceinventario as codEndo,
                            inventarioprocedimenti.procedimento as desEndo,
                            inventarioproc_endo.flag_necessario as obbEndo,
                            0 as flgPrincipale,
                            tipifamiglieendo.ordine as famigliaOrdine,
                            tipiendo.ordine as tipoEndoOrdine,
                            inventarioprocedimenti.ordine as endoOrdine,
                            inventarioprocedimenti.codicenatura as codicenatura,
                            inventarioprocedimenti.FLAGTIPITITOLO,
                            naturaendobase.NATURA as descrizioneNatura,
                            naturaendobase.BINARIODIPENDENZE as BINARIODIPENDENZE
                        FROM
                            inventarioproc_endo


                            inner join inventarioprocedimenti ON
                                inventarioprocedimenti.idcomune = inventarioproc_endo.fk_cid_idcomune AND
                                inventarioprocedimenti.codiceinventario = inventarioproc_endo.codiceinventario_d

                            left join tipiendo ON
                                tipiendo.idcomune = inventarioprocedimenti.idcomune AND
                                tipiendo.codice = inventarioprocedimenti.codicetipo

                            left join tipifamiglieendo ON
                                tipifamiglieendo.idcomune = tipiendo.idcomune AND
                                tipifamiglieendo.codice = tipiendo.codicefamigliaendo


                            left outer join naturaendobase on
                                inventarioprocedimenti.codicenatura = naturaendobase.codicenatura

                        WHERE
                            inventarioproc_endo.idComune = {_db.Specifics.QueryParameterName("idComune")} AND
                            inventarioproc_endo.codiceinventario_t = {_db.Specifics.QueryParameterName("idEndo")} and
                            ( inventarioproc_endo.codicecomune = {_db.Specifics.QueryParameterName("codiceComune")} or inventarioproc_endo.codicecomune is null) and
                            inventarioproc_endo.FLAG_PUBBLICA = 1
                        order by inventarioproc_endo.flag_necessario desc, tipiendo.ordine,tipifamiglieendo.ordine,inventarioprocedimenti.ordine, inventarioprocedimenti.procedimento";


            var dataSet = _db.ExecuteReader(sql,
                            mp =>
                            {
                                mp.AddParameter("idComune", _idComuneBase);
                                mp.AddParameter("idEndo", idEndo);
                                mp.AddParameter("codiceComune", codiceComune);
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
                    Richiesto = endo.obbEndo,
                    Principale = endo.flgPrincipale,
                    Ordine = endo.endoOrdine,
                    BinarioDipendenze = endo.binarioDipendenze,
                    CodiceNatura = endo.codicenatura,
                    Natura = endo.descrizioneNatura,
                    TipoTitoloObbligatorio = endo.tipoTitoloObbligatorio
                };

                listaTipi[codTipo].Endoprocedimenti.Add(nuovoEndo);

            });

            return famiglieEndoTrovate;
        }
    }
}
