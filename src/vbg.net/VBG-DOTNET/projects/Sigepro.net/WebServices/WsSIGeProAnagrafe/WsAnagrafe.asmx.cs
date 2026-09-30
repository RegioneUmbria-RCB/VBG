using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca;
using log4net;
using Ninject;
using PersonalLib2.Data;
using Sigepro.net.WebServices.WsSIGePro;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGeProAnagrafe
{
    /// <summary>
    /// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class WsAnagrafe : SigeproWebService
    {
        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }
        [Inject]
        public IBindingFactory _bindingFactory { get; set; }

        private readonly ILog _logger = LogManager.GetLogger(typeof(WsAnagrafe));
        //private readonly ComponentiRicercaFactory _componentiRicercaFactory = new ComponentiRicercaFactory();

        private const string DEFAULT_ANAGRAFE_SEARCHER_NAME = "DEFAULTANAGRAFESEARCHER";


        /// <summary>
        /// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per codice fiscale
        /// </summary>
        /// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
        /// <param name="codiceFiscale">Codice fiscale da ricercare</param>
        /// <returns>Dati dell'anagrafica trovata o null se non è stato trovato niente</returns>
        [WebMethod]
        public Anagrafe ByCodiceFiscale(string authenticationToken, string codiceFiscale)
        {
            var authInfo = this.CheckToken(authenticationToken);

            using (var db = authInfo.CreateDatabase())
            {
                var searcher = this.GetSearcher(db, authInfo);

                try
                {
                    searcher.Init();

                    return searcher.ByCodiceFiscaleImp(codiceFiscale);
                }
                catch (Exception ex)
                {
                    this._logger.DebugFormat("Errore in ByCodiceFiscale:" + ex.ToString());
                    throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
                }
                finally
                {
                    if (searcher != null)
                        searcher.CleanUp();
                }
            }
        }

        /// <summary>
        /// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per codice fiscale
        /// </summary>
        /// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
        /// <param name="tipoPersona">Tipo persona da ricercare</param> 
        /// <param name="codiceFiscale">Codice fiscale da ricercare</param>
        /// <returns>Dati dell'anagrafica trovata o null se non è stato trovato niente</returns>
        [WebMethod(MessageName = "ByCodiceFiscaleETipoPersona")]
        public Anagrafe ByCodiceFiscale(string authenticationToken, TipoPersona tipoPersona, string codiceFiscale)
        {
            var authInfo = this.CheckToken(authenticationToken);

            using (var db = authInfo.CreateDatabase())
            {
                var searcher = this.GetSearcher(db, authInfo);

                try
                {
                    searcher.Init();

                    return searcher.ByCodiceFiscaleImp(tipoPersona, codiceFiscale);
                }
                catch (Exception ex)
                {
                    throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
                }
                finally
                {
                    if (searcher != null)
                        searcher.CleanUp();
                }
            }
        }

        public Anagrafe GetByUserId(string authenticationToken, string userId, TipoPersona tipoPersona)
        {
            var authInfo = this.CheckToken(authenticationToken);

            if (authInfo == null)
                throw new InvalidTokenException(authenticationToken);

            using (var db = authInfo.CreateDatabase())
            {
                return new AnagrafeMgr(db).GetByUserId(authInfo.IdComune, userId, tipoPersona == TipoPersona.PersonaFisica ? AnagrafeMgr.TipoPersona.Fisica : AnagrafeMgr.TipoPersona.Giuridica);
            }
        }

        /// <summary>
        /// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per partita iva
        /// </summary>
        /// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
        /// <param name="partitaIva">Partita IVA da ricercare</param>
        /// <returns>Dati dell'anagrafica trovata o null se non è stato trovato niente</returns>
        [WebMethod]
        public Anagrafe ByPartitaIva(string authenticationToken, string partitaIva)
        {
            var authInfo = this.CheckToken(authenticationToken);

            using (var db = authInfo.CreateDatabase())
            {
                var searcher = this.GetSearcher(db, authInfo);

                try
                {
                    searcher.Init();

                    return searcher.ByPartitaIvaImp(partitaIva);
                }
                catch (Exception ex)
                {
                    throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
                }
                finally
                {
                    if (searcher != null)
                        searcher.CleanUp();
                }
            }
        }

        /// <summary>
        /// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per nome e/o cognome
        /// </summary>
        /// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
        /// <param name="nome">Nome da ricercare</param>
        /// <param name="cognome">Cognome da ricercare</param>
        /// <returns>Lista delle anagrafiche trovate o lista vuota se non è stato trovato niente</returns>
        [WebMethod]
        public List<Anagrafe> ByNomeCognome(string authenticationToken, string nome, string cognome)
        {
            var authInfo = this.CheckToken(authenticationToken);

            using (var db = authInfo.CreateDatabase())
            {
                var searcher = this.GetSearcher(db, authInfo);

                try
                {
                    searcher.Init();

                    return searcher.ByNomeCognomeImp(string.IsNullOrEmpty(nome) ? null : nome, string.IsNullOrEmpty(cognome) ? null : cognome);
                }
                catch (Exception ex)
                {
                    throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
                }
                finally
                {
                    if (searcher != null)
                        searcher.CleanUp();
                }
            }
        }


        [WebMethod]
        public Anagrafe GetDaCodiceAnagrafe(string token, int codiceanagrafe)
        {
            var authInfo = this.CheckToken(token);

            return new AnagrafeMgr(authInfo.CreateDatabase()).GetById(authInfo.IdComune, codiceanagrafe);
        }

        /// <summary>
        /// Crea un istanza dell'oggetto che effettuerà la ricerca
        /// TODO: si potrebbero velocizzare le operazioni di caricamento utilizzando la cache del server
        /// </summary>
        /// <param name="authInfo">Credenziali di accesso</param>
        /// <returns>Implementatore di <see cref="AnagrafeSearcherBase"/> che effettua la ricerca nella fonte dati</returns>
        private IAnagrafeSearcher GetSearcher(DataBase db, AuthenticationInfo authInfo)
        {
            var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneWsanagrafe>(authInfo.Alias, "TT");

            var assemblyName = vert.SearchComponent;

            this._logger.DebugFormat("Caricamento del componente di ricerca dall'assembly {0}", assemblyName);


            var defaultSigeproSearcher = new AnagrafeSearcher(this._verticalizzazioniFactory, DEFAULT_ANAGRAFE_SEARCHER_NAME);
            defaultSigeproSearcher.InitParams(authInfo.IdComune, authInfo.Alias, db);

            if (!vert.Attiva || assemblyName == "" || assemblyName.ToUpper() == DEFAULT_ANAGRAFE_SEARCHER_NAME)
                return defaultSigeproSearcher;

            try
            {
                var searcher = new SigeproWrappedAnagrafeSearcher(defaultSigeproSearcher, this.CreaIstanzaSearcher(vert));

                searcher.InitParams(authInfo.IdComune, authInfo.Alias, db);

                return searcher;
            }
            catch (Exception ex)
            {
                this._logger.Error($"WsSIGeProAnagrafe.WsAnagrafe: {ex}");

                return null;
            }

        }

        private IAnagrafeSearcher CreaIstanzaSearcher(VerticalizzazioneWsanagrafe vert)
        {
            try
            {
                var componentiFactory = new ComponentiRicercaFactory(this._verticalizzazioniFactory, this._bindingFactory);
                return componentiFactory.CreaIstanzaSearcher(vert);

            }
            catch (Exception ex)
            {
                this._logger.ErrorFormat("CreaIstanzaSearcher: {0}", ex.ToString());

                throw;
            }
        }

        //private string GetMd5(string text)
        //{
        //    var pass = Encoding.UTF8.GetBytes(text);
        //    MD5 md5 = new MD5CryptoServiceProvider();
        //    var bytes = md5.ComputeHash(pass);

        //    var sb = new StringBuilder();

        //    for (var i = 0; i < bytes.Length; i++)
        //        sb.Append(bytes[i].ToString("X2"));

        //    return sb.ToString().ToLower();
        //}
    }
}