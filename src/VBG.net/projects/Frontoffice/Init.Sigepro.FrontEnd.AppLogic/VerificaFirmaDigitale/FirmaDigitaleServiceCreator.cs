using System;
using System.ServiceModel;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.WsVerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
	public class FirmaDigitaleServiceCreator
	{
		IConfigurazione<ParametriSigeproSecurity> _configurazione;

		public FirmaDigitaleServiceCreator(IConfigurazione<ParametriSigeproSecurity> configurazione)
		{
			Condition.Requires(configurazione, "configurazione")
					 .IsNotNull();

			_configurazione = configurazione;
		}


		public ServiceInstance<ValidationServiceClient> CreateClient(/*string aliasComune*/)
		{
			var endPoint = new EndpointAddress(_configurazione.Parametri.UrlVerificaFirmaService);

			var binding = new BasicHttpBinding("firmaDigitaleServiceBinding");


			var ws = new ValidationServiceClient(binding, endPoint);
			var token = String.Empty; // WsProxy.GetToken(aliasComune);

			return new ServiceInstance<ValidationServiceClient>(ws, token);
		}
	}
}
