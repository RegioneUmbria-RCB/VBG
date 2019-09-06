using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO.Oneri;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneOneri
{
    public class OneriConsoleService : IOneriConsoleService
    {
        private readonly DataBase _db;
        private readonly IConsoleService _console;

        public OneriConsoleService(DataBase db, IConsoleService console)
        {
            _db = db;
            _console = console;
        }

        public IEnumerable<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(int codiceIntervento, IEnumerable<int> listaIdEndo, string codiceComuneAssociato)
        {
            var endoMgr = new InventarioProcedimentiMgr(this._db);
            var intervMgr = new AlberoProcMgr(this._db);
            var parametriConsole = this._console.GetParametriSdeProxy();
            var parametriComuneLocale = this._console.LoginSuAliasLocale();

            var rVal = new List<OnereDto>(intervMgr.GetListaOneriDaIdIntervento(parametriConsole.IdComuneBase, codiceIntervento));

            var oneriCondivisi = GetOneriPerProcedimento(parametriConsole.IdComuneBase, parametriConsole.IdComuneBase, listaIdEndo);
            var oneriLocali = GetOneriPerProcedimento(parametriConsole.IdComuneBase, parametriComuneLocale.IdComune, listaIdEndo, codiceComuneAssociato);

            var oneriCondivisiDict = oneriCondivisi.ToDictionary(x => x.Codice);
            var oneriLocaliDaAggiungere = new List<OnereDto>(); 
                
            foreach (var onereLocale in oneriLocali)
            {
                if (!oneriCondivisiDict.TryGetValue(onereLocale.Codice, out var tmpOnere))
                {
                    oneriLocaliDaAggiungere.Add(onereLocale);
                    continue;
                }

                tmpOnere.Importo = onereLocale.Importo;
                tmpOnere.Note = onereLocale.Note;
            }

            rVal.AddRange(oneriCondivisi);
            rVal.AddRange(oneriLocaliDaAggiungere);

            return rVal;
        }

        private IEnumerable<OnereDto> GetOneriPerProcedimento(string idComuneEndo, string idComuneLocale, IEnumerable<int> idEndoprocedimenti, string codiceComuneAssociato = "")
        {
            var listaId = String.Join(",", idEndoprocedimenti.Select(x => x.ToString()).ToArray());

            var sql = $@"SELECT 
                          tipicausalioneri.co_id             AS codice,
                          tipicausalioneri.co_descrizione         AS descrizione,
                          inventarioprocedimentioneri.importo     AS importo,
                          inventarioprocedimenti.codiceinventario AS codiceprocedimento,
                          inventarioprocedimenti.procedimento     AS procedimento,
                          inventarioprocedimentioneri.note        AS note
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
                          and inventarioprocedimentioneri.idcomune = {this._db.Specifics.QueryParameterName("idComuneLocale")}";

            if (!String.IsNullOrEmpty(codiceComuneAssociato))
            {
                sql += $" and inventarioprocedimentioneri.codicecomune={this._db.Specifics.QueryParameterName("codiceComune")}";
            }

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComuneEndo", idComuneEndo);
                    mp.AddParameter("idComuneLocale", idComuneLocale);

                    if (!String.IsNullOrEmpty(codiceComuneAssociato))
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
                    Note = dr["note"].ToString()
                });

        }
    }
}
