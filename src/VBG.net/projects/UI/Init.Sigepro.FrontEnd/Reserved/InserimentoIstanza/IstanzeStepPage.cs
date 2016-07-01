namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	using System;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
	using log4net;
	using Ninject;

	public class IstanzeStepPage : ReservedBasePage, IStepPage
	{
		private ILog _log = LogManager.GetLogger(typeof(IstanzeStepPage));

		protected DomandeOnlineService DomandeOnlineService { get; private set; }

		protected IReadFacade ReadFacade { get; private set; }

		protected IIdDomandaResolver IdDomandaResolver { get; private set; }

		public int IdDomanda 
		{ 
			get 
			{ 
				return IdDomandaResolver.IdDomanda; 
			} 
		}

		[Inject]
		public void SetIdDomandaResolver(IIdDomandaResolver idDomandaResolver)
		{
			this.IdDomandaResolver = idDomandaResolver;
		}

		[Inject]
		public void SetReadFacade(IReadFacade readFacade)
		{
			this.ReadFacade = readFacade;
		}

		[Inject]
		public void SetDomandeService(DomandeOnlineService domandeService)
		{
			this.DomandeOnlineService = domandeService;
		}

		protected override void OnPreRender(EventArgs e)
		{
			if (!String.IsNullOrEmpty(Request.QueryString["dumpDomanda"]))
			{
				DomandeOnlineService.DumpDomanda(IdDomanda);
			}

			base.OnPreRender(e);
		}

		protected override void OnLoad(EventArgs e)
		{
			var domanda = ReadFacade.Domanda;

			if (!domanda.UtentePuoAccedere(CodiceUtente))
			{
				_log.ErrorFormat("L'utente {0} ha cercato di accedere alla presentazione {1} del comune {2} senza possederne i permessi", CodiceUtente, IdDomanda, IdComune);

				ErroreAccessoPagina.Mostra(IdComune, UserAuthenticationResult.Token, Software, ErroreAccessoPagina.TipoErroreEnum.PermessiNonDisponibili);
				return;
			}

			if (!IsPostBack && domanda.IsPresentata())
			{
				_log.ErrorFormat("L'utente {0} ha cercato di accedere alla presentazione {1} del comune {2} ma la domanda era già stata presentata", CodiceUtente, IdDomanda, IdComune);

				ErroreAccessoPagina.Mostra(IdComune, UserAuthenticationResult.Token, Software, ErroreAccessoPagina.TipoErroreEnum.IstanzaGiaPresentata);
				return;
			}

			base.OnLoad(e);
		}

		public bool CheckIfCanEnterPage()
		{
			RequestActivation();

			OnInitializeStep();

			return CanEnterStep();
		}

		public virtual void OnInitializeStep()
		{
		}

		public virtual void OnBeforeExitStep()
		{
		}

		public virtual bool CanEnterStep()
		{
			return true;
		}

		public virtual bool CanExitStep()
		{
			return true;
		}

		protected T ViewstateGet<T>(string viewstateKey, T ifNull)
		{
			var o = this.ViewState[viewstateKey];

			return o == null ? ifNull : (T)o;
		}

		protected void ViewStateSet(string viewstateKey, object value)
		{
			this.ViewState[viewstateKey] = value;
		}
	}
}
