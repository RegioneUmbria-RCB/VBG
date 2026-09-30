using Init.SIGePro.Manager.Authentication;
using Init.Utils;
using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Manager;

namespace VBG.Backend.SIT.WebServices
{
    public class WsSit : IWsSit
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsSit));
        private readonly IAuthenticationManager _authenticationManager;
        private readonly ITransientAuthenticationInfoResolver _transientAuthResolver;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public WsSit(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver _transientAuthResolver, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._authenticationManager = authenticationManager;
            this._transientAuthResolver = _transientAuthResolver;
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        protected AuthenticationInfo CheckToken(string token)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            this._transientAuthResolver.SetTransientAuthInfo(authInfo);

            return authInfo;
        }

        public ListSit GetListField(string token, string field, Sit dataSit, string software)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    var sitMgr = new SitIntegrationService(authInfo.IdComune, authInfo.Alias, software, this._verticalizzazioniFactory);

                    return sitMgr.GetList(field, dataSit);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetListField : {0}", ex.ToString());


                    throw;
                }
            }
        }

        public bool EffettuaValidazioneFormale(string token, string software, Sit sitClass)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var idComune = authInfo.IdComune;
                var idComuneAlias = authInfo.Alias;

                using (var db = authInfo.CreateDatabase())
                {
                    var mgr = new SitIntegrationService(idComune, idComuneAlias, software, this._verticalizzazioniFactory);

                    return mgr.EffettuaValidazioneFormale(sitClass);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la validazione formale della classe sit, token={0}, software={1}, struttura sit={2} \r\n\r\nEccezione: {3}", token, software, StreamUtils.SerializeClass(sitClass), ex.ToString());

                throw;
            }
        }

        public string[] GetCampiGestiti(string token, string software)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var idComune = authInfo.IdComune;
                var idComuneAlias = authInfo.Alias;

                using (var db = authInfo.CreateDatabase())
                {
                    var mgr = new SitIntegrationService(idComune, idComuneAlias, software, this._verticalizzazioniFactory);

                    return mgr.GetListaCampiGestiti().ToArray();
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetCampiGestiti con token={0} e software={1}: {2}", token, software, ex.ToString());

                throw;
            }
        }

        public DetailSit GetDetailField(string token, string field, Sit dataSit, string software)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {

                    var sitMgr = new SitIntegrationService(authInfo.IdComune, authInfo.Alias, software, this._verticalizzazioniFactory);

                    return sitMgr.GetDetail(field, dataSit);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetDetailField : {0}", ex.ToString());


                    throw;
                }
            }
        }

        public SitFeatures GetFeatures(string token, string software)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var idComune = authInfo.IdComune;
                var idComuneAlias = authInfo.Alias;

                using (var db = authInfo.CreateDatabase())
                {
                    var mgr = new SitIntegrationService(idComune, idComuneAlias, software, this._verticalizzazioniFactory);

                    return mgr.GetFeatures();
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata a GetCampiGestiti con token={0} e software={1}: {2}", token, software, ex.ToString());

                throw;
            }
        }

        public DettagliVia[] GetListaVie(string token, string software, FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    var sitMgr = new SitIntegrationService(authInfo.IdComune, authInfo.Alias, software, this._verticalizzazioniFactory);

                    return sitMgr.GetListaVie(filtro, codiciComuni?.ToArray() ?? new string[0]);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in ValidateField : {0}", ex.ToString());


                    throw;
                }
            }
        }

        public ValidateSit ValidateField(string token, string field, Sit dataSit, string software)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    var sitMgr = new SitIntegrationService(authInfo.IdComune, authInfo.Alias, software, this._verticalizzazioniFactory);

                    return sitMgr.Validate(field, dataSit);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in ValidateField : {0}", ex.ToString());


                    throw;
                }
            }
        }


    }
}
