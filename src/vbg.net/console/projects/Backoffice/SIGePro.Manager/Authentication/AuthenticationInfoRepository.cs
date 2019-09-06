using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.WsSigeproSecurity;
using PersonalLib2.Data;
using System.Web;
using Init.SIGePro.Manager.Configuration;
using System.Web.Caching;
using log4net;

namespace Init.SIGePro.Manager.Authentication
{
	internal interface IAuthenticationInfoRepository
	{
		AuthenticationInfo GetByToken(string token, TipiAmbiente tipiAmbiente);
		AuthenticationInfo GetByLoginInfo(string alias, string userId, string password, ContextType tipoContesto);
	}

	internal class AuthenticationInfoRepositoryFactory
	{

		protected class AuthenticationInfoRepository : IAuthenticationInfoRepository
		{
			protected static class MappatureEnumerazioni
			{

				// Mappature per risolvere il tipo contesto e l'ambiente nel servizio Java
				static Dictionary<ContextType, ContestoType> m_mappaturaContesto;

				public static Dictionary<ContextType, ContestoType> Contesti
				{
					get
					{
						if (m_mappaturaContesto == null)
						{
							m_mappaturaContesto = new Dictionary<ContextType, ContestoType>();
							m_mappaturaContesto[ContextType.Anagrafe] = ContestoType.UTE;
							m_mappaturaContesto[ContextType.Operatore] = ContestoType.OPE;
							m_mappaturaContesto[ContextType.ExternalUsers] = ContestoType.APP;
						}

						return m_mappaturaContesto;
					}
				}

				static Dictionary<TipiAmbiente, AmbienteType> m_mappaturaAmbiente;

				public static Dictionary<TipiAmbiente, AmbienteType> Ambienti
				{
					get
					{
						if (m_mappaturaAmbiente == null)
						{
							m_mappaturaAmbiente = new Dictionary<TipiAmbiente, AmbienteType>();
							m_mappaturaAmbiente[TipiAmbiente.DOTNET] = AmbienteType.DOTNET;
							m_mappaturaAmbiente[TipiAmbiente.JAVA] = AmbienteType.JAVA;
							m_mappaturaAmbiente[TipiAmbiente.DEFAULT] = AmbienteType.ASP;
						}

						return m_mappaturaAmbiente;
					}
				}
			}





			#region IAuthenticationInfoRepository Members

			public AuthenticationInfo GetByToken(string token, TipiAmbiente tipiAmbiente)
			{
				var chkReq = new CheckTokenRequest
				{
					token = token,
					tokenInfo = true
				};

				var checkResult = SigeproSecurityProxy.CheckToken(chkReq);

				if (!checkResult.valid)
					return null;

				var dbInfoReq = new GetDbConnectionInfoRequest
				{
					alias = checkResult.tokenInfo.alias,
					ambiente = MappatureEnumerazioni.Ambienti[tipiAmbiente]
				};

				var dbInfo = SigeproSecurityProxy.GetDbConnectionInfo(dbInfoReq);

				dbInfoReq = new GetDbConnectionInfoRequest
				{
					alias = checkResult.tokenInfo.alias,
					ambiente = AmbienteType.DOTNET
				};

				var dbInfoLocal = tipiAmbiente == TipiAmbiente.DOTNET ? dbInfo : SigeproSecurityProxy.GetDbConnectionInfo(dbInfoReq);

				using (var db = dbInfoLocal.CreateDatabase())
				{
					var softwareAttivi = new SoftwareAttiviMgr(db).GetSoftwareAttivi(checkResult.tokenInfo.idcomune);

					return new AuthenticationInfo
					{
						Alias = checkResult.tokenInfo.alias,
						Ambiente = tipiAmbiente.ToString(),
						CodiceResponsabile = EstraiCodiceResponsabile(db, checkResult.tokenInfo),
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
						Contesto = AdattaContesto(checkResult.tokenInfo.contesto)
					};
				}
			}

			private SIGePro.Authentication.ContestoTokenEnum AdattaContesto(WsSigeproSecurity.ContestoType contestoType)
			{
				switch(contestoType)
				{
					case WsSigeproSecurity.ContestoType.AMM:
						return SIGePro.Authentication.ContestoTokenEnum.Amministratore;

					case WsSigeproSecurity.ContestoType.APP:
						return SIGePro.Authentication.ContestoTokenEnum.Applicazione;

					case WsSigeproSecurity.ContestoType.UTE:
                    case WsSigeproSecurity.ContestoType.UTEG:
						return SIGePro.Authentication.ContestoTokenEnum.Utente;

					case WsSigeproSecurity.ContestoType.OPE:
						return SIGePro.Authentication.ContestoTokenEnum.Operatore;
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
					ipAddress = HttpContext.Current == null ? String.Empty : HttpContext.Current.Request.ServerVariables["REMOTE_ADDR"]
				};

				var response = SigeproSecurityProxy.Login(req);

				if (string.IsNullOrEmpty(response.token))
					return null;

				return GetByToken(response.token, TipiAmbiente.DOTNET);
			}

			#endregion


			private int? EstraiCodiceResponsabile(DataBase db, TokenInfoType tokenInfo)
			{
				string idComune  = tokenInfo.idcomune;
				string codUtente = tokenInfo.userid;

				switch (tokenInfo.contesto)
				{
					case ContestoType.OPE:
						var responsabile = new ResponsabiliMgr(db).GetByUserId(idComune, codUtente);

						if (responsabile == null)
							throw new ArgumentException("Impossibile trovare un responsabile con user id " + tokenInfo.userid);

						return Convert.ToInt32(responsabile.CODICERESPONSABILE);

					case ContestoType.UTE:
						var anagrafe = new AnagrafeMgr(db).GetByCodiceFiscalePartitaIvaETipoPersona(idComune, codUtente, AnagrafeMgr.TipoPersona.Fisica);

						if (anagrafe == null)
							throw new ArgumentException("Impossibile trovare un'anagrafica con user codice fiscale o partita iva " + tokenInfo.userid);

						return Convert.ToInt32(anagrafe.CODICEANAGRAFE);

                    case ContestoType.UTEG:
                        var anagrafeg = new AnagrafeMgr(db).GetByCodiceFiscalePartitaIvaETipoPersona(idComune, codUtente, AnagrafeMgr.TipoPersona.Giuridica);

                        if (anagrafeg == null)
                            throw new ArgumentException("Impossibile trovare un'anagrafica con user codice fiscale o partita iva " + tokenInfo.userid);

                        return Convert.ToInt32(anagrafeg.CODICEANAGRAFE);
					default:
						return null;
				}
			}
		}


		protected class CachedAuthenticationInfoRepository : IAuthenticationInfoRepository
		{
			ILog _log = LogManager.GetLogger(typeof(CachedAuthenticationInfoRepository));
			AuthenticationInfoRepository _baseRepository;

			public CachedAuthenticationInfoRepository()
			{
				this._baseRepository = new AuthenticationInfoRepository();
			}

			#region IAuthenticationInfoRepository Members

			public AuthenticationInfo GetByToken(string token, TipiAmbiente tipiAmbiente)
			{
				var cacheKey = String.Format("{0}_{1}", token, tipiAmbiente.ToString());

				if (HttpContext.Current == null)
				{
					_log.Debug("Httpcontext non disponibile, i dati del token verranno letti da sigeprosecurity");
					return _baseRepository.GetByToken(token, tipiAmbiente);
				}

				var authInfo = (AuthenticationInfo)HttpContext.Current.Cache[cacheKey];

				if (authInfo == null)
				{
					_log.DebugFormat("Token {0} non presente nella cache, verrà letto da sigeprosecurity", cacheKey);

					authInfo = _baseRepository.GetByToken(token, tipiAmbiente);

					if (authInfo == null)
						return null;

					var tokenExpiration = DateTime.Now.Add(new TimeSpan(0, ParametriConfigurazione.Get.TokenTimeout, 0));

					_log.DebugFormat("Token expiration impostato a {0} minuti, il token scadrà il {1}", ParametriConfigurazione.Get.TokenTimeout, tokenExpiration.ToLongDateString());

					HttpContext.Current.Cache.Add(cacheKey,
													authInfo,
													null,
													tokenExpiration, Cache.NoSlidingExpiration, CacheItemPriority.Normal, null);

					_log.DebugFormat("Token {0} inserito nella cache", cacheKey);
				}

				return authInfo;
			}

			public AuthenticationInfo GetByLoginInfo(string alias, string userId, string password, ContextType tipoContesto)
			{
				return _baseRepository.GetByLoginInfo(alias, userId, password, tipoContesto);
			}

			#endregion
		}



		internal IAuthenticationInfoRepository Create()
		{
			return new CachedAuthenticationInfoRepository();
		}
	}
}
