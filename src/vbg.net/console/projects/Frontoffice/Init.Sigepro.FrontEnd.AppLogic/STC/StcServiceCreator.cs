// -----------------------------------------------------------------------
// <copyright file="StcServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.STC
{
	using System.ServiceModel;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Stc;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.AppLogic.StcService;

	public interface IStcServiceCreator
	{
        ParametriStcAidaSmart ConfigurazioneStc { get; }
		ServiceInstance<StcClient> CreateClient();
	}

	internal class StcServiceCreator : IStcServiceCreator
	{
		IConfigurazione<ParametriStcAidaSmart> _configurazione;
		StcToken _stcToken;

        public StcServiceCreator(IConfigurazione<ParametriStcAidaSmart> configurazione)
		{
			_configurazione = configurazione;
			_stcToken = new StcToken(_configurazione);
		}

		public ParametriStcAidaSmart ConfigurazioneStc { get { return this._configurazione.Parametri; } }

		public ServiceInstance<StcClient> CreateClient()
		{
			var endPoint = new EndpointAddress(this._configurazione.Parametri.UrlStc);

			var binding = new BasicHttpBinding("stcServiceBinding");

			var ws = new StcClient(binding, endPoint);

			var token = _stcToken.GetToken();

			return new ServiceInstance<StcClient>(ws, token);
		}
	}
}
