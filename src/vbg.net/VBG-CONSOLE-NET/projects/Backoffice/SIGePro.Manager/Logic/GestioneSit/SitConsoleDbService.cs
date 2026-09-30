using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.Logic.ServiziConsole;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.GestioneSit
{
    public class SitConsoleDbService
    {
        private readonly DataBase _db;
        private static string NOME_MODULO = "SIT_CONSOLE";

        public SitConsoleDbService(DataBase db)
        {
            _db = db;
        }
        public List<string> ListCodiciComuniSitAttivi(string alias)
        {
            var sql = $@"
                SELECT DISTINCT  codicecomune
                FROM sdeproxy 
                INNER JOIN verticalizzazioni ON 
                        verticalizzazioni.idcomune = sdeproxy.alias_ente AND
                        verticalizzazioni.modulo = {_db.Specifics.QueryParameterName("modulo")} AND
                        verticalizzazioni.attivo = 1
                WHERE
                    sdeproxy.idente = {_db.Specifics.QueryParameterName("alias")}
            ";

            var codiciComuni = _db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("modulo", NOME_MODULO);
                    mp.AddParameter("alias", alias);
                },
                dr => new
                {
                    Codice = dr.GetString("codicecomune"),
                });

            if (!codiciComuni.Any())
            {
                return new List<string>();
            }

            return codiciComuni.Select(x => x.Codice).ToList();
        }
    }
}
