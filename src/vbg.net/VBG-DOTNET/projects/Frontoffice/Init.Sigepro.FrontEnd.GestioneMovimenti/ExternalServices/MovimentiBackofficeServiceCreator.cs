// -----------------------------------------------------------------------
// <copyright file="MovimentiBackofficeServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.GestioneMovimenti.WsScadenzario;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
    /// <summary>
    /// CLasse utilizzata per istanziare un client per l'accesso ai dati di un movimento effettuato nel backoffice
    /// </summary>
    public class MovimentiBackofficeServiceCreator : ServiceCreatorBase<WsScadenzarioServiceClient>
    {
        public MovimentiBackofficeServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "areaRiservataServiceBinding";

        protected override WsScadenzarioServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsScadenzarioServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWebServiceMovimenti;
        }
    }
}
