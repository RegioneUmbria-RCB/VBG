using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP;
using Ninject;
using System;
using System.Configuration;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class PagamentoMIP : IstanzeStepPage
    {
        public enum CallingReason
        {
            InizioPagamento,
            HOME,
            BACK,
            ERRORE,
            OK
        }

        private static class Constants
        {
            public const int ViewNuovoPagamento = 0;
            public const int ViewPagamentoFallito = 1;
            public const int ViewPagamentoRiuscito = 2;
            public const int ViewPagamentoAnnullato = 3;
            public const int ViewPagamentoInCorso = 4;
        }


        [Inject]
        protected PagamentiMIPService PagamentiService { get; set; }

        [Inject]
        protected OneriDomandaService OneriDomandaService { get; set; }

        public bool ErrorePagamento = false;
        protected CallingReason Reason
        {
            get
            {
                var reason = this.Request.QueryString["reason"];

                if (String.IsNullOrEmpty(reason))
                {
                    return CallingReason.InizioPagamento;
                }

                return (CallingReason)Enum.Parse(typeof(CallingReason), reason);
            }
        }

        protected string BufferMIP
        {
            get { return this.Request.QueryString["buffer"]; }
        }

        protected string UrlPagamenti
        {
            get;
            set;
        }

        public string TestoPagamentoFallito
        {
            get => this.VsGet("TestoPagamentoFallito",
                "Il pagamento è fallito oppure l'operazione di pagamento è stata annullata dall'utente." +
                "<br />" +
                "L'esito dell'operazione è: <b>{errore}</b>" +
                "<br /><br />" +
                "Fai click su <b>\"Torna Indietro\"</b> per tornare allo step precedente.");
            set => this.VsSet("TestoPagamentoFallito", value);
        }

        public string TestoPagamentoRiuscito
        {
            get => this.VsGet("TestoPagamentoRiuscito",
                "A breve riceverai una mail contenente i dettagli del pagamento.<br />" +
                "Fai click su<b>\"Vai Avanti\"</b> per proseguire con la presentazione della domanda.");
            set => this.VsSet("TestoPagamentoRiuscito", value);
        }

        public string TestoPagamentoAnnullato
        {
            get => this.VsGet("TestoPagamentoAnnullato",
                "Il pagamento è stato annullato dall'utente e nessuna somma verrà prelevata.<br />" +
                "Fai click su <b>\"Torna Indietro\"</b> per tornare allo step precedente.");
            set => this.VsSet("TestoPagamentoAnnullato", value);
        }

        protected string UrlReload { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override bool CanEnterStep()
        {
            if (!this.PagamentiService.VerticalizzazioneAttiva())
            {
                return false;
            }

            var tipoPagamentoDefault = this.PagamentiService.GetTipoPagamentoDefault();

            if (tipoPagamentoDefault == null)
            {
                throw new ConfigurationErrorsException("Non è stato configurato un tipo pagamento. Verificare la configurazione del modulo PAGAMENTI_MIP_RPCSUAP e riprovare");
            }

            if (this.Reason == CallingReason.InizioPagamento && this.ReadFacade.Domanda.Oneri.GetOneriOnlineProntiPerPagamento().Count() == 0)
            {
                return false;
            }

            return true;
        }

        public override void DataBind()
        {
            if (this.Reason == CallingReason.InizioPagamento)
            {
                this.IniziaPagamento();
                return;
            }

            if (this.Reason == CallingReason.HOME || this.Reason == CallingReason.BACK)
            {
                this.PagamentoAnnullato();
                return;
            }

            var esitoPagamento = this.PagamentiService.VerificaStatoPagamento(this.IdDomanda);

            switch (esitoPagamento.Stato)
            {
                case EsitoPagamentoMip.StatoPagamentoMipEnum.OK:
                    this.PagamentiService.PagamentoRiuscito(this.IdDomanda, esitoPagamento.EsitoWs);

                    this.multiView.ActiveViewIndex = Constants.ViewPagamentoRiuscito;
                    break;

                case EsitoPagamentoMip.StatoPagamentoMipEnum.KO:
                case EsitoPagamentoMip.StatoPagamentoMipEnum.UK:
                case EsitoPagamentoMip.StatoPagamentoMipEnum.ER:
                    var descrizioneErrore = this.PagamentiService.PagamentoFallito(this.IdDomanda, esitoPagamento.EsitoWs);

                    this.ltrTestoPagamentoFallito.Text = this.TestoPagamentoFallito.Replace("{errore}", descrizioneErrore);

                    this.multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
                    this.Master.MostraBottoneAvanti = false;
                    break;

                case EsitoPagamentoMip.StatoPagamentoMipEnum.OP:
                    this.UrlReload = this.Request.Url.AbsoluteUri;
                    this.multiView.ActiveViewIndex = Constants.ViewPagamentoInCorso;
                    this.Master.MostraBottoneAvanti = false;
                    break;
            }

        }

        private void PagamentoAnnullato()
        {
            this.PagamentiService.AnnullaPagamento(this.IdDomanda, this.BufferMIP);

            this.multiView.ActiveViewIndex = Constants.ViewPagamentoAnnullato;
            this.Master.MostraBottoneAvanti = false;
        }


        private void IniziaPagamento()
        {
            this.Master.MostraBottoneAvanti = false;

            var email = this.UserAuthenticationResult.DatiUtente.Email;

            if (String.IsNullOrEmpty(email))
            {
                email = this.ReadFacade.Domanda.AltriDati.DomicilioElettronico;
            }

            if (String.IsNullOrEmpty(email))
            {
                this.Errori.Add("Impossibile inizializzare il pagamento perchè l'indirizzo email dell'utente è mancante. Tornare allo step di inserimento anagrafiche e inserire un indirizzo email valido.");
                this.ErrorePagamento = true;
                return;
            }

            var estremiDomanda = new EstremiDomanda(this.IdDomanda, this.Master.LastStep, email, this.UserAuthenticationResult.DatiUtente.Codicefiscale);
            var datiAvvioPagamento = this.PagamentiService.InizializzaPagamento(estremiDomanda);

            this.PagamentiService.AvviaPagamento(this.IdDomanda, datiAvvioPagamento.NumeroOperazione, datiAvvioPagamento.Oneri);

            this.UrlPagamenti = datiAvvioPagamento.UrlAvvioPagamento;

            this.multiView.ActiveViewIndex = Constants.ViewNuovoPagamento;
        }

        public override void OnInitializeStep()
        {

        }

        private string VsGet(string vsKey, string defaultVal)
        {
            var str = this.ViewState[vsKey];

            if (str != null)
            {
                return str.ToString();
            }

            return defaultVal;
        }

        private void VsSet(string key, string val)
        {
            this.ViewState[key] = val;
        }
    }
}