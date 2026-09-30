using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.DTO.Oneri;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.ServiziConsole.GestioneOneri
{
    public class OneriConsoleService : IOneriConsoleService
    {
        private readonly DataBase _db;
        private readonly IConsoleService _console;
        private readonly ILog _log = LogManager.GetLogger(typeof(OneriConsoleService));

        public OneriConsoleService(DataBase db, IConsoleService console)
        {
            this._db = db;
            this._console = console;
        }

        public IEnumerable<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(int codiceIntervento, IEnumerable<int> listaIdEndo, string codiceComuneAssociato)
        {
            InventarioProcedimentiMgr endoMgr = new InventarioProcedimentiMgr(this._db);
            AlberoProcMgr intervMgr = new AlberoProcMgr(this._db);
            SDEProxyDto parametriConsole = this._console.GetParametriSdeProxy();
            AuthenticationInfo parametriComuneLocale = this._console.LoginSuAliasLocale();

            List<OnereDto> rVal = new List<OnereDto>(intervMgr.GetListaOneriDaIdIntervento(parametriConsole.IdComuneBase, codiceIntervento));

            IEnumerable<OnereDto> oneriCondivisi = this.GetOneriPerProcedimento(parametriConsole.IdComuneBase, parametriConsole.IdComuneBase, listaIdEndo);
            IEnumerable<OnereDto> oneriLocali = this.GetOneriPerProcedimento(parametriConsole.IdComuneBase, parametriComuneLocale.IdComune, listaIdEndo, codiceComuneAssociato);

            this._log.ErrorFormat("Oneri condivisi: {0}", string.Join(", ", oneriCondivisi.Select(x => x.InterventoOEndoOrigine + " " + x.Codice + " --")));

            // Dictionary<int, OnereDto> oneriCondivisiDict = oneriCondivisi.ToDictionary(x => x.Codice);
            List<OnereDto> oneriLocaliDaAggiungere = new List<OnereDto>();

            foreach (OnereDto onereLocale in oneriLocali)
            {
                OnereDto tmpOnere = oneriCondivisi.FirstOrDefault(x => x.Codice == onereLocale.Codice && x.CodiceInterventoOEndoOrigine == onereLocale.CodiceInterventoOEndoOrigine);

                if (tmpOnere == null)
                {
                    oneriLocaliDaAggiungere.Add(onereLocale);
                    continue;
                }
                /*
                if (!oneriCondivisiDict.TryGetValue(onereLocale.Codice, out OnereDto tmpOnere))
                {
                    oneriLocaliDaAggiungere.Add(onereLocale);
                    continue;
                }
                */
                tmpOnere.Importo = onereLocale.Importo;
                tmpOnere.Note = onereLocale.Note;
                tmpOnere.CampoDinamico = onereLocale.CampoDinamico;
            }

            rVal.AddRange(oneriCondivisi);
            rVal.AddRange(oneriLocaliDaAggiungere);

            return rVal;
        }

        private IEnumerable<OnereDto> GetOneriPerProcedimento(string idComuneEndo, string idComuneLocale, IEnumerable<int> idEndoprocedimenti, string codiceComuneAssociato = "")
        {
            var listaId = string.Join(",", idEndoprocedimenti.Select(x => x.ToString()).ToArray());

            var sql = $@"SELECT 
                          tipicausalioneri.co_id             AS codice,
                          tipicausalioneri.co_descrizione         AS descrizione,
                          inventarioprocedimentioneri.importo     AS importo,
                          inventarioprocedimenti.codiceinventario AS codiceprocedimento,
                          inventarioprocedimenti.procedimento     AS procedimento,
                          inventarioprocedimentioneri.note        AS note,
                          inventarioprocedimentioneri.DYN2CAMPI_ONERI_FRONT as D2C
                        FROM 
                          inventarioprocedimentioneri

                            inner join inventarioprocedimenti on 
                                inventarioprocedimenti.idcomune           = inventarioprocedimentioneri.FK_INVPROC_IDCOMUNE and 
                                inventarioprocedimenti.codiceinventario   = inventarioprocedimentioneri.codiceinventario

                            inner join tipicausalioneri on 
                                tipicausalioneri.idcomune   = inventarioprocedimentioneri.FK_COIDCOMUNE and
                                tipicausalioneri.co_id      = inventarioprocedimentioneri.fk_coid
                        WHERE
                              inventarioprocedimentioneri.FK_INVPROC_IDCOMUNE = {this._db.Specifics.QueryParameterName("idComuneEndo")}
                          AND inventarioprocedimentioneri.codiceinventario in (" + listaId + $@") 
                          and inventarioprocedimentioneri.idcomune = {this._db.Specifics.QueryParameterName("idComuneLocale")} and
                          {this._db.Specifics.NvlFunction("FLAG_DISATTIVO", 0)} = 0";

            if (!string.IsNullOrEmpty(codiceComuneAssociato))
            {
                sql += $" and inventarioprocedimentioneri.codicecomune={this._db.Specifics.QueryParameterName("codiceComune")}";
            }

            sql += " order by inventarioprocedimenti.procedimento asc";

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComuneEndo", idComuneEndo);
                    mp.AddParameter("idComuneLocale", idComuneLocale);

                    if (!string.IsNullOrEmpty(codiceComuneAssociato))
                    {
                        mp.AddParameter("codiceComune", codiceComuneAssociato);
                    }
                },
                dr => new OnereDto
                {
                    Codice = dr.GetInt("Codice").Value,
                    Descrizione = dr.GetString("descrizione"),
                    Importo = dr.GetFloat("importo").GetValueOrDefault(0.0f),
                    OrigineOnere = "E",
                    CodiceInterventoOEndoOrigine = Convert.ToInt32(dr["codiceprocedimento"]),
                    InterventoOEndoOrigine = dr["procedimento"].ToString(),
                    Note = dr["note"].ToString(),
                    CampoDinamico = dr["D2C"] == DBNull.Value ? (int?)null : Convert.ToInt32(dr["D2C"])
                });

        }
    }
}
