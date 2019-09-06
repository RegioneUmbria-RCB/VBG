using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
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
    public partial class PagamentoEntraNext : IstanzeStepPage
    {
        public enum CallingReason
        {
            INIZIO_PAGAMENTO,
            OK,
            DIFFERITO,
            ERROR,
            FALLITO,
            TIMEOUT
        }

        private static class Constants
        {
            public const int ViewNuovoPagamento = 0;          
            public const int ViewPagamentoFallito = 1;
            public const int ViewPagamentoRiuscito = 2;
            public const int ViewPagamentoAnnullato = 3;
            public const int ViewPagamentoInAttesa = 4;
        }

        [Inject]
        protected PagamentiEntraNextService PagamentiService { get; set; }

        [Inject]
        protected OneriDomandaService OneriDomandaService { get; set; }

        public bool ErrorePagamento = false;
        protected CallingReason Reason
        {
            get
            {
                var reason = Request["esito"];

                if (String.IsNullOrEmpty(reason))
                {
                    return CallingReason.INIZIO_PAGAMENTO;
                }

                return (CallingReason)Enum.Parse(typeof(CallingReason), reason);
            }
        }

        protected string CodicePagamento
        {
            get
            {
                return Request.QueryString["codicePagamento"];
            }
        }

        protected string IdTransaction
        {
            get
            {
                return Request["idTransaction"];
            }
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
            if (this.Reason == CallingReason.INIZIO_PAGAMENTO && ReadFacade.Domanda.Oneri.GetOneriProntiPerPagamentoOnline().Count() == 0)
            {
                
                return false;
            }

            return true;
        }

        public override void DataBind()
        {
            try
            {
                if (this.Reason == CallingReason.INIZIO_PAGAMENTO)
                {
                    IniziaPagamento();
                }
                else if (this.Reason == CallingReason.OK)
                {
                    PagamentoRiuscito();
                }
                else if (this.Reason == CallingReason.DIFFERITO)
                {
                    multiView.ActiveViewIndex = Constants.ViewPagamentoInAttesa;
                }
                else if (this.Reason == CallingReason.TIMEOUT)
                {
                    this.Errori.Add($"Il pagamento risulta essere ancora in corso, riprovare più tardi per verificare se andato a buon fine oppure contattare il comune comunicando il codice del pagamento visibile nello step precedente");
                    PagamentoFallito();
                }
                else
                {
                    PagamentoFallito();
                }
            }
            catch (Exception ex)
            {
                multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
                this.Errori.Add($"Pagamento fallito: {ex.Message}");
                Master.MostraBottoneAvanti = false;
            }
        }

        private void PagamentoFallito()
        {
            multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
            Master.MostraBottoneAvanti = false;
        }

        private void PagamentoRiuscito()
        {
            try
            {
                var verifica = PagamentiService.VerificaPagamento(this.IdDomanda, this.IdTransaction);

                if (!verifica)
                {
                    throw new Exception("Verifica del pagamento fallita");
                }
                multiView.ActiveViewIndex = Constants.ViewPagamentoRiuscito;
            }
            catch (Exception ex)
            {
                multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
                this.Errori.Add($"{ex.Message}");
                Master.MostraBottoneAvanti = false;
            }
        }

        private void IniziaPagamento()
        {
            try
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

                var anagraficaDomanda = ReadFacade.Domanda.Anagrafiche
                                  .Anagrafiche
                                  .Where(x => x.Codicefiscale == UserAuthenticationResult.DatiUtente.Codicefiscale && x.TipoPersona == AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.TipoPersonaEnum.Fisica)
                                  .FirstOrDefault();

                var estremiDomanda = new EstremiDomandaEntraNext(IdDomanda, Master.LastStep, anagraficaDomanda, this.ReadFacade.Comuni);
                var datiAvvioPagamento = PagamentiService.InizializzaPagamento(estremiDomanda);

                PagamentiService.AvviaPagamento(IdDomanda, datiAvvioPagamento.NumeroOperazione, datiAvvioPagamento.Oneri);
                this.UrlPagamenti = datiAvvioPagamento.UrlAvvioPagamento;

                multiView.ActiveViewIndex = Constants.ViewNuovoPagamento;
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
                this.ErrorePagamento = true;
            }
        }

        public override void OnInitializeStep()
        {
            //var tipoPagamentoDefault = this.PagamentiService.GetTipoPagamentoDefault();

            //if (tipoPagamentoDefault == null)
            //{
            //    throw new ConfigurationErrorsException("Non è stato configurato un tipo pagamento. Verificare la configurazione del modulo PAGAMENTI_ENTRANEXT e riprovare");
            //}
        }
    }
}