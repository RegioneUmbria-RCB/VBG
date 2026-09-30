using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.Authentication.Const;
using Init.SIGePro.Manager.Authentication.ServiceReferences;
using Init.SIGePro.Manager.Authentication.SoftwareAttivi;
using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;

namespace Init.SIGePro.Manager.Authentication
{
    internal class AuthenticationInfoRepositoryFactory
    {
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        private readonly ITimedCache _timedCache;

        public AuthenticationInfoRepositoryFactory(SigeproSecurityProxy sigeproSecurityProxy, ITimedCache timedCache)
        {
            this._sigeproSecurityProxy = sigeproSecurityProxy;
            this._timedCache = timedCache;
        }

        protected static class MappatureEnumerazioni
        {
            // Mappature per risolvere il tipo contesto e l'ambiente nel servizio Java
            public static ReadOnlyDictionary<ContextType, ContestoType> Contesti { get; } = new ReadOnlyDictionary<ContextType, ContestoType>(new Dictionary<ContextType, ContestoType>
                {
                    {ContextType.Anagrafe, ContestoType.UTE },
                    { ContextType.Operatore, ContestoType.OPE },
                    { ContextType.ExternalUsers, ContestoType.APP }
                });

            public static ReadOnlyDictionary<TipiAmbiente, AmbienteType> Ambienti { get; } = new ReadOnlyDictionary<TipiAmbiente, AmbienteType>(new Dictionary<TipiAmbiente, AmbienteType>
                {
                    { TipiAmbiente.DOTNET, AmbienteType.DOTNET },
                    { TipiAmbiente.JAVA, AmbienteType.JAVA },
                    { TipiAmbiente.DEFAULT, AmbienteType.ASP }
                });
        }

        protected class AuthenticationInfoRepository : IAuthenticationInfoRepository
        {
            private readonly SigeproSecurityProxy _sigeproSecurityProxy;
            public AuthenticationInfoRepository(SigeproSecurityProxy sigeproSecurityProxy)
            {
                this._sigeproSecurityProxy = sigeproSecurityProxy;
            }

            #region IAuthenticationInfoRepository Members

            public AuthenticationInfo GetByToken(string token, TipiAmbiente tipiAmbiente)
            {
                var chkReq = new CheckTokenRequest
                {
                    token = token,
                    tokenInfo = true
                };

                var checkResult = this._sigeproSecurityProxy.CheckToken(chkReq);

                if (!checkResult.valid)
                    return null;

                var dbInfoReq = new GetDbConnectionInfoRequest
                {
                    alias = checkResult.tokenInfo.alias,
                    ambiente = MappatureEnumerazioni.Ambienti[tipiAmbiente]
                };

                var dbInfo = this._sigeproSecurityProxy.GetDbConnectionInfo(dbInfoReq);

                dbInfoReq = new GetDbConnectionInfoRequest
                {
                    alias = checkResult.tokenInfo.alias,
                    ambiente = AmbienteType.DOTNET
                };

                var dbInfoLocal = tipiAmbiente == TipiAmbiente.DOTNET ? dbInfo : this._sigeproSecurityProxy.GetDbConnectionInfo(dbInfoReq);

                using (var db = dbInfoLocal.CreateDatabase())
                {
                    var swService = new SoftwareAttiviService(db, checkResult.tokenInfo.idcomune);
                    var softwareAttivi = swService.GetSoftwareAttivi();

                    return new AuthenticationInfo
                    {
                        Alias = checkResult.tokenInfo.alias,
                        Ambiente = tipiAmbiente.ToString(),
                        CodiceResponsabile = this.EstraiCodiceResponsabile(db, checkResult.tokenInfo),
                        ConnectionString = dbInfo.connectionString,
                        DBMSName = string.IsNullOrEmpty(dbInfo.dbMsName) ? db.DBMSName.ToString() : dbInfo.dbMsName,
                        DBOwner = dbInfo.dbOwner,
                        DBPassword = dbInfo.dbPassword,
                        DBUser = dbInfo.dbUser,
                        IdComune = checkResult.tokenInfo.idcomune,
                        Provider = dbInfo.provider,
                        SoftwareAttivi = softwareAttivi.StringaSoftwareAttiviBackoffice,
                        SoftwareAttiviFO = softwareAttivi.StringaSoftwareAttiviFrontoffice,
                        Token = token,
                        Contesto = this.AdattaContesto(checkResult.tokenInfo.contesto)
                    };
                }
            }

            private ContestoTokenEnum AdattaContesto(ContestoType contestoType)
            {
                switch (contestoType)
                {
                    case ContestoType.AMM:
                        return ContestoTokenEnum.Amministratore;

                    case ContestoType.APP:
                        return ContestoTokenEnum.Applicazione;

                    case ContestoType.UTE:
                    case ContestoType.UTEG:
                        return ContestoTokenEnum.Utente;

                    case ContestoType.OPE:
                        return ContestoTokenEnum.Operatore;
                }

                throw new Exception("Tipo contesto " + contestoType + " non supporato");
            }


            public AuthenticationInfo GetByLoginInfo(string alias, string userId, string password, ContextType tipoContesto)
            {
                var req = new LoginRequest
                {
                    alias = alias,
                    username = userId,
                    password = password,
                    contesto = MappatureEnumerazioni.Contesti[tipoContesto],
                    ipAddress = "127.0.0.1"
                };

                var response = this._sigeproSecurityProxy.Login(req);

                if (string.IsNullOrEmpty(response.token))
                    return null;

                return this.GetByToken(response.token, TipiAmbiente.DOTNET);
            }

            #endregion

            private int? EstraiCodiceResponsabile(DataBase db, TokenInfoType tokenInfo)
            {
                var idComune = tokenInfo.idcomune;
                var codUtente = tokenInfo.userid;

                var codiceUtenteResolver = new CodiceUtenteResolver(db, idComune);

                switch (tokenInfo.contesto)
                {
                    case ContestoType.OPE:
                        var responsabile = codiceUtenteResolver.GetCodiceResponsabileByCodiceUtente(codUtente);

                        if (!responsabile.HasValue)
                            throw new ArgumentException("Impossibile trovare un responsabile con user id " + tokenInfo.userid);

                        return responsabile.Value;

                    case ContestoType.UTE:
                        var anagrafe = codiceUtenteResolver.GetCodiceAnagrafePersonaFisica(codUtente);

                        if (!anagrafe.HasValue)
                            throw new ArgumentException("Impossibile trovare un'anagrafica con user codice fiscale o partita iva " + tokenInfo.userid);

                        return anagrafe.Value;

                    case ContestoType.UTEG:
                        var anagrafeg = codiceUtenteResolver.GetCodiceAnagrafePersonaGiuridica(codUtente);

                        if (!anagrafeg.HasValue)
                            throw new ArgumentException("Impossibile trovare un'anagrafica con user codice fiscale o partita iva " + tokenInfo.userid);

                        return anagrafeg.Value;

                    default:
                        return null;
                }
            }



        }

        protected class CachedAuthenticationInfoRepository : IAuthenticationInfoRepository
        {
            private readonly AuthenticationInfoRepository _baseRepository;
            private readonly SigeproSecurityProxy _sigeproSecurityProxy;
            private readonly ITimedCache _applicationCache;

            public CachedAuthenticationInfoRepository(SigeproSecurityProxy sigeproSecurityProxy, ITimedCache applicationCache)
            {
                this._baseRepository = new AuthenticationInfoRepository(sigeproSecurityProxy);
                this._sigeproSecurityProxy = sigeproSecurityProxy;
                this._applicationCache = applicationCache;
            }

            #region IAuthenticationInfoRepository Members

            public AuthenticationInfo GetByToken(string token, TipiAmbiente tipiAmbiente)
            {
                var cacheKey = String.Format("{0}_{1}", token, tipiAmbiente.ToString());
                var tokenTimeout = this._sigeproSecurityProxy.GetValoreParametro(AuthParamNames.CHECK_TOKEN_TIMEOUT);
                var timeout = String.IsNullOrEmpty(tokenTimeout) ? 0 : Convert.ToInt32(tokenTimeout);

                var authInfo = this._applicationCache.GetOrAdd(cacheKey, timeout, () =>
                    this._baseRepository.GetByToken(token, tipiAmbiente)
                );

                return authInfo;
            }

            public AuthenticationInfo GetByLoginInfo(string alias, string userId, string password, ContextType tipoContesto)
            {
                return this._baseRepository.GetByLoginInfo(alias, userId, password, tipoContesto);
            }

            #endregion
        }

        internal IAuthenticationInfoRepository Create()
        {
            return new CachedAuthenticationInfoRepository(this._sigeproSecurityProxy, this._timedCache);
        }
    }
}
