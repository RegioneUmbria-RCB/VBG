using Init.SIGePro.Authentication;
using PersonalLib2.Data;
using System;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.AidaSmart
{
    public class ConsoleService : IConsoleService
    {
        private readonly DataBase _db;
        private readonly string _idEnte;
        private readonly Lazy<SDEProxyDto> _parametriSde;

        public ConsoleService(DataBase db, string idEnte)
        {
            _db = db;
            _idEnte = idEnte;

            this._parametriSde = new Lazy<SDEProxyDto>(GetParametriSdeProxyInternal);
        }


        public SDEProxyDto GetParametriSdeProxy()
        {
            return _parametriSde.Value;
        }

        private SDEProxyDto GetParametriSdeProxyInternal()
        {
            var sql = $"select * from sdeproxy where idente={_db.Specifics.QueryParameterName("alias")}";

            var parametri = _db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("alias", this._idEnte);
                },
                x => new SDEProxyDto
                {
                    AliasEnte = x.GetString("alias_ente"),
                    CodiceCatastaleComune = x.GetString("codicecatastalecomune"),
                    Descrizione = x.GetString("descrizione"),
                    IdComuneBase = x.GetString("idcomunebase"),
                    IdEnte = x.GetString("idente"),
                    UrlServizi = x.GetString("ws_servizi"),
                    AliasServizi = x.GetString("alias_ws_servizi")
                }).FirstOrDefault();

            return parametri;
        }

        public UrlServiziConsole GetUrlServizi()
        {
            var parametri = GetParametriSdeProxy();

            return new UrlServiziConsole(parametri.UrlServizi, parametri.AliasServizi);
        }

        public AuthenticationInfo LoginSuAliasLocale()
        {
            return AuthenticationManager.LoginApplicativo(this._parametriSde.Value.AliasEnte);
        }
    }
}
