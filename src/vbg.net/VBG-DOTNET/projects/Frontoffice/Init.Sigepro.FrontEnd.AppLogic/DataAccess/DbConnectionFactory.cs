using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using PersonalLib2.Data;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.DataAccess
{
    public class DbConnectionFactory
    {
        Lazy<IDbConnectionInfo> _dbConnectionInfo;
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        private readonly ITokenResolver _tokenResolver;

        public string IdComune => this._dbConnectionInfo.Value.IdComune;
        public string Alias => this._dbConnectionInfo.Value.Alias;

        public DbConnectionFactory(IAliasResolver aliasResolver, SigeproSecurityProxy sigeproSecurityProxy, ITokenResolver tokenResolver)
        {
            this._sigeproSecurityProxy = sigeproSecurityProxy;
            this._tokenResolver = tokenResolver;
            this._dbConnectionInfo = new Lazy<IDbConnectionInfo>(() => this._sigeproSecurityProxy.GetDbConnection(aliasResolver.AliasComune));
        }

        public IDatabase CreateDatabase()
        {
            return this._dbConnectionInfo.Value.CreateDatabase(this._tokenResolver.Token);
        }
    }
}
