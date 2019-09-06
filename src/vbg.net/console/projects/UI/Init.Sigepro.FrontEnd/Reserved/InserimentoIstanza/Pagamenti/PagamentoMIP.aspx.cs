using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP;
using Ninject;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

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
                var reason = Request.QueryString["reason"];

                if (String.IsNullOrEmpty(reason))
                {
                    return CallingReason.InizioPagamento;
                }

                return (CallingReason)Enum.Parse(typeof(CallingReason), reason);
            }
        }

        protected string BufferMIP
        {
            get { return Request.QueryString["buffer"]; }
        }

        protected string UrlPagamenti
        {
            get;
            set;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override bool CanEnterStep()
        {
            if (this.Reason == CallingReason.InizioPagamento && ReadFacade.Domanda.Oneri.GetOneriProntiPerPagamentoOnline().Count() == 0)
            {
                return false;
            }

            return true;
        }
        
        public override void DataBind()
        {
            switch(Reason)
            {
                case CallingReason.InizioPagamento:
                    IniziaPagamento();
                    break;
                case CallingReason.OK:
                    PagamentoRiuscito();
                    break;
                case CallingReason.ERRORE:
                    PagamentoFallito();
                    break;
                case CallingReason.HOME:
                case CallingReason.BACK:
                    PagamentoAnnullato();
                    break;
            }
        }

        private void PagamentoAnnullato()
        {
            PagamentiService.AnnullaPagamento(IdDomanda, this.BufferMIP);

            multiView.ActiveViewIndex = Constants.ViewPagamentoAnnullato;
            Master.MostraBottoneAvanti = false;
        }

        private void PagamentoFallito()
        {
            PagamentiService.PagamentoFallito(IdDomanda, this.BufferMIP);

            multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
            Master.MostraBottoneAvanti = false;
        }

        private void PagamentoRiuscito()
        {
            PagamentiService.PagamentoRiuscito(IdDomanda, this.BufferMIP);

            multiView.ActiveViewIndex = Constants.ViewPagamentoRiuscito;
        }

        private void IniziaPagamento()
        {
            Master.MostraBottoneAvanti = false;

            var email = UserAuthenticationResult.DatiUtente.Email;

            if (String.IsNullOrEmpty(email))
            {
                email = ReadFacade.Domanda.AltriDati.DomicilioElettronico;
            }

            if (String.IsNullOrEmpty(email))
            {
                this.Errori.Add("Impossibile inizializzare il pagamento perchè l'indirizzo email dell'utente è mancante. Tornare allo step di inserimento anagrafiche e inserire un indirizzo email valido.");
                this.ErrorePagamento = true;
                return;
            }

            var estremiDomanda = new EstremiDomanda(IdDomanda, Master.LastStep, email, UserAuthenticationResult.DatiUtente.Codicefiscale);
            var datiAvvioPagamento = PagamentiService.InizializzaPagamento(estremiDomanda);

            PagamentiService.AvviaPagamento(IdDomanda, datiAvvioPagamento.NumeroOperazione, datiAvvioPagamento.Oneri);

            this.UrlPagamenti = datiAvvioPagamento.UrlAvvioPagamento;

            multiView.ActiveViewIndex = Constants.ViewNuovoPagamento;
        }

        public override void OnInitializeStep()
        {
            var tipoPagamentoDefault = this.PagamentiService.GetTipoPagamentoDefault();

            if (tipoPagamentoDefault == null)
            {
                throw new ConfigurationException("Non è stato configurato un tipo pagamento. Verificare la configurazione del modulo PAGAMENTI_MIP_RPCSUAP e riprovare");
            }
        }
    }
}