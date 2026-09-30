using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using VBG.Shared.Infrastructure.ServiceModel;
using Init.Sigepro.FrontEnd.Infrastructure.Web;
using log4net;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public class LivornoSITServiceProxy : ServiceCreatorBase<PresentazioneAreeUsoPubblicoSoapClient>
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(LivornoSITServiceProxy));
        private readonly Tls12Utils _tls12Utils;
        private readonly IConfigurazione<ParametriIntegrazioneLDP> _cfg;
        public LivornoSITServiceProxy(IConfigurazione<ParametriIntegrazioneLDP> cfg, Tls12Utils tls12Utils, IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(config, tokenApplicazioneService, bindingFactory)
        {
            this._tls12Utils = tls12Utils;
            this._cfg = cfg;
        }

        public DatiOccupazioneLDP GetDatiOccupazione(string identificativoPratica)
        {
            var dati = this.Call(ws =>
            {
                using (var scope = new OperationContextScope(ws.Service.InnerChannel))
                {
                    this.AggiungiCredenzialiAContextScope(ws.Service);
                    return ws.Service.getDatiOccupazioneSuoloByIdentificativoTemporaneo(new ComplexTypeStringa { testo = identificativoPratica });
                }
            });
            this._log.DebugFormat("Dati della pratica {0}: {1}", identificativoPratica, dati.ToXmlString());
            var intervalli = dati.TointervalliOccupazione();
            var stringaRipetizioni = dati.GetStringaRipetizioni();
            return new DatiOccupazioneLDP(stringaRipetizioni, intervalli);
        }

        protected override PresentazioneAreeUsoPubblicoSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            binding.MaxBufferSize = 1024000;
            binding.MaxReceivedMessageSize = 1024000;
            return new PresentazioneAreeUsoPubblicoSoapClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return this._cfg.Parametri.UrlServizioDomanda;
        }

        private void AggiungiCredenzialiAContextScope(PresentazioneAreeUsoPubblicoSoapClient ws)
        {
            this._tls12Utils.ApplicaImpostazioniTls12(ws.Endpoint.Address.Uri.ToString());
            var credentials = new BasicSoapAuthenticationCredentials(this._cfg.Parametri.ServiceUsername, this._cfg.Parametri.ServicePassword);
            credentials.AggiungiCredenzialiAContextScope();
        }
    }
}