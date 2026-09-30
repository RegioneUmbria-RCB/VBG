using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneCalcoli;
using log4net;
using System;
using System.Web.Http;

namespace Sigepro.net.Api.ConfiguratoreCalcoli
{
    [RoutePrefix("web-api/configuratorecalcoli/{token}")]
    public class ConfiguratoreCalcoliController : ApiController
    {
        private readonly ILog _logger = LogManager.GetLogger("ConfiguratoreCalcoliController");
        private readonly IAuthenticationManager _authenticationManager;

        public ConfiguratoreCalcoliController(IAuthenticationManager authenticationManager)
        {
            this._authenticationManager = authenticationManager;
        }

        [HttpGet]
        [Route("{idConfigurazione}")]
        public string Get(string token, int idConfigurazione)
        {

            return "value";
        }


        // PUT api/<controller>/5
        [HttpPut]
        [Route("{idConfigurazione}")]
        public ConfiguratoreCalcoliPutResponse Put(string token, int idConfigurazione, ConfiguratoreCalcoliPutRequest request)
        {
            try
            {
                if (string.IsNullOrEmpty(token))
                {
                    throw new ArgumentException($"'{nameof(token)}' non può essere null o vuoto.", nameof(token));
                }

                if (request is null)
                {
                    throw new ArgumentNullException(nameof(request));
                }

                if (string.IsNullOrEmpty(request.Descrizione))
                {
                    throw new ArgumentException($"'{nameof(request.Descrizione)}' non può essere null o vuoto.", nameof(request.Descrizione));
                }

                this._logger.Debug($"Inizio aggiornamento descrizione della configurazione {idConfigurazione}");

                var authInfo = this._authenticationManager.CheckToken(token);
                using (var db = authInfo.CreateDatabase())
                {
                    new ConfigurazioneCalcoliMgr(db).UpdateDescrizione(authInfo.IdComune, idConfigurazione, request.Descrizione);
                }


                this._logger.Debug($"Fine aggiornamento descrizione della configurazione {idConfigurazione}");

                return ConfiguratoreCalcoliPutResponse.OK();
            }
            catch (Exception ex)
            {
                return ConfiguratoreCalcoliPutResponse.KO(ex.Message);
            }
        }

    }
}