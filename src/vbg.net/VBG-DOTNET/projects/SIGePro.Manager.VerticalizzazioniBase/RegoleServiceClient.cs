using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using RegoleService;
using System;
using System.ServiceModel;

namespace SIGePro.Manager.VerticalizzazioniBase
{
    public class RegoleServiceClient
    {
        private readonly IAuthenticationManager _authenticationManager;
        private readonly IBindingFactory _bindingFactory;

        public RegoleServiceClient(IAuthenticationManager authenticationManager, IBindingFactory bindingFactory)
        {
            this._authenticationManager = authenticationManager;
            this._bindingFactory = bindingFactory;
        }

        public RegolaResponse GetRegola(string alias, RegolaRequest request)
        {

            var authInfo = this._authenticationManager.GetTokenApplicativo(alias);

            if (authInfo == null)
                throw new Exception($"AUTHENTICATION INFO NON VALORIZZATO, IdComuneAlias: {alias}");

            var urlBackend = ParametriConfigurazione.Get.WsHostUrlJava;
            var wsUrl = urlBackend + "/services/regole?wsdl";

            request.token = authInfo.Token;

            using (var ws = this.CreaWebService(wsUrl))
            {
                var response = ws.GetRegola(request);

                if (response == null)
                    throw new Exception($"LA RISPOSTA DELLA RICERCA DELLA VERTICALIZZAZIONE {request.nomeRegola} RISULTA ESSERE NULL");

                return response;
            }
        }

        private RegoleClient CreaWebService(string wsUrl)
        {
            try
            {
                //var bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();
                var endPointAddress = new EndpointAddress(wsUrl);
                var binding = this._bindingFactory.CreateAndConfigure("defaultHttpBinding");

                var ws = new RegoleClient(binding, endPointAddress);

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DELLE VERTICALIZZAZIONI, {0}", ex.Message));
            }
        }
    }
}
