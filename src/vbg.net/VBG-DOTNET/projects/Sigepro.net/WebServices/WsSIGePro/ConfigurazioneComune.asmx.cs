using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using log4net;
using Ninject;
using Ninject.Web;
using System;
using System.ComponentModel;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsSIGePro
{
    /// <summary>
    /// Summary description for ConfigurazioneComune
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    public class ConfigurazioneComune : WebServiceBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ConfigurazioneComune));

        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        [WebMethod]
        public Init.SIGePro.Data.Configurazione LeggiConfigurazioneComune(string token, string software)
        {
            AuthenticationInfo ai = this._authenticationManager.CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);

            try
            {
                return new ConfigurazioneMgr(ai.CreateDatabase()).GetByIdComuneESoftwareSovrascrivendoTT(ai.IdComune, software);
            }
            catch (Exception ex)
            {
                this._log.Error($"ConfigurazioneComune.LeggiConfigurazioneComune: {ex}");
                throw ex;
            }
        }

    }
}
