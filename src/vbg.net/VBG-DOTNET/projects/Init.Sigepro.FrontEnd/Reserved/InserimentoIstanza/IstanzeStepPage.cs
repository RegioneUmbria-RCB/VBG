namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
    using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
    using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
    using log4net;
    using Ninject;
    using System;

    public class IstanzeStepPage : ReservedBasePage, IStepPage
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(IstanzeStepPage));

        protected DomandeOnlineService DomandeOnlineService { get; private set; }

        protected IReadFacade ReadFacade { get; private set; }

        protected IIdDomandaResolver IdDomandaResolver { get; private set; }

        public int IdDomanda
        {
            get
            {
                return this.IdDomandaResolver.IdDomanda;
            }
        }

        [Inject]
        public IEventiDomandeInBozzaService _eventiDomandeInBozzaService { get; set; }

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

        public bool IgnoraVerificaAccessoIstanza
        {
            get { object o = this.ViewState["IgnoraVerificaAccesso"]; return o == null ? false : (bool)o; }
            set { this.ViewState["IgnoraVerificaAccesso"] = value; }
        }


        protected override void OnPreRender(EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.Request.QueryString["dumpDomanda"]))
            {
                this.DomandeOnlineService.DumpDomanda(this.IdDomanda);
            }

            base.OnPreRender(e);
        }

        protected override void OnLoad(EventArgs e)
        {
            if (this.IgnoraVerificaAccessoIstanza)
            {
                return;
            }

            var domanda = this.ReadFacade.Domanda;

            if (!domanda.UtentePuoAccedere(this.CodiceUtente))
            {
                this._log.Error($"L'utente {this.CodiceUtente} ha cercato di accedere alla presentazione {this.IdDomanda} del comune {this.IdComune} senza possederne i permessi");

                ErroreAccessoPagina.Mostra(this.IdComune, this.Software, ErroreAccessoPagina.TipoErroreEnum.PermessiNonDisponibili);
                return;
            }

            if (!this.IsPostBack && domanda.IsPresentata())
            {
                this._eventiDomandeInBozzaService.DomandaInBozzaPresentata(this.ReadFacade.Domanda);

                this._log.Error($"L'utente {this.CodiceUtente} ha cercato di accedere alla presentazione {this.IdDomanda} del comune {this.IdComune} ma la domanda era già stata presentata");

                ErroreAccessoPagina.Mostra(this.IdComune, this.Software, ErroreAccessoPagina.TipoErroreEnum.IstanzaGiaPresentata);
                return;
            }

            base.OnLoad(e);
        }

        public bool CheckIfCanEnterPage()
        {
            this.RequestActivation();

            this.OnInitializeStep();

            return this.CanEnterStep();
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
