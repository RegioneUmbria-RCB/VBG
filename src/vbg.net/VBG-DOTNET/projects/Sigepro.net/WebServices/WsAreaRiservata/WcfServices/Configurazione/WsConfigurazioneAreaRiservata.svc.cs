using Init.SIGePro.Manager.DTO.Configurazione;
using Init.SIGePro.Manager.Logic.GestioneConfigurazione;
using Ninject;
using Sigepro.net.WebServices.WsSIGePro;
using SIGePro.Manager.VerticalizzazioniBase;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Configurazione
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsCommissioni" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsCommissioni.svc or WsCommissioni.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsConfigurazioneAreaRiservata : WcfServiceBase, IWsConfigurazioneAreaRiservata
    {
        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }

        public WsConfigurazioneAreaRiservata()
        {
        }
        public ConfigurazioneAreaRiservataDto LeggiConfigurazioneFrontoffice(string token, string software)
        {
            if (this._verticalizzazioniFactory == null)
                throw new System.Exception("VerticalizzazioniFactory non iniettata");

            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var svc = new ConfigurazioneAreaRiservataService(db, authInfo.IdComune, this._verticalizzazioniFactory);
                return svc.GetConfigurazioneAreaRiservata(authInfo.Alias, software);
            }
        }

        public Init.SIGePro.Data.Configurazione LeggiConfigurazioneComune(string token, string software)
        {
            return new ConfigurazioneComune().LeggiConfigurazioneComune(token, software);
        }
    }
}
