using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.AidaSmart;
using Init.SIGePro.Manager.Logic.AidaSmart.GestioneDatiPrivacy;
using log4net;
using System;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
    public partial class AreaRiservataServiceBase
    {
        public class ConfigurazioneEx
        {
            public Init.SIGePro.Data.Configurazione Configurazione { get; set; }
            public ResponsabiliPrivacyConsoleDto ResponsabiliPrivacy { get; set; }
        }

        ILog _logger = LogManager.GetLogger(typeof(AreaRiservataServiceBase));

        [WebMethod]
        public ConfigurazioneEx LeggiConfigurazioneComune(string token, string software)
        {
            var ai = CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);

            try
            {
                using (var db = ai.CreateDatabase())
                {
                    var console = new ConsoleService(db, ai.Alias);
                    var privacyService = new PrivacyConsoleService(console);

                    return new ConfigurazioneEx
                    {
                        Configurazione = new ConfigurazioneMgr(ai.CreateDatabase()).GetByIdComuneESoftwareSovrascrivendoTT(ai.IdComune, software),
                        ResponsabiliPrivacy = privacyService.GetDatiPrivacy()
                    };
                }
            }
            catch (Exception ex)
            {
                _logger.ErrorFormat("ConfigurazioneComune.LeggiConfigurazioneComune: {0}", ex.ToString());
                throw;
            }
        }

    }
}
