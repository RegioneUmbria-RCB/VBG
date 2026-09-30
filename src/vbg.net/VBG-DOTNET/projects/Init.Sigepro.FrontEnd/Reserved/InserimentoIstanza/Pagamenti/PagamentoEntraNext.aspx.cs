using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Ninject;
using System;
using System.Linq;

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
            TIMEOUT,
            ANNULLATO
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
        [Inject]
        public IComuniService _comuniService { get; set; }

        public bool ErrorePagamento = false;
        protected CallingReason Reason
        {
            get
            {
                var reason = Request["esito"];

                if (String.IsNullOrEmpty(reason))
                {
                    if (String.IsNullOrEmpty(Request.QueryString["codicePagamento"]))
                    {
                        return CallingReason.INIZIO_PAGAMENTO;
                    }
                    else
                    {
                        return CallingReason.DIFFERITO;
                    }

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
            if (Reason == CallingReason.INIZIO_PAGAMENTO && ReadFacade.Domanda.Oneri.GetOneriOnlineProntiPerPagamento().Count() == 0 && !ReadFacade.Domanda.Oneri.GetOneriOnlineConPagamentoAvviato().Any())
            {

                return false;
            }

            return true;
        }

        public override void DataBind()
        {
            try
            {
                switch (Reason)
                {
                    case CallingReason.INIZIO_PAGAMENTO:
                        {
                            IniziaPagamento();
                            break;
                        }

                    case CallingReason.OK:
                        {
                            PagamentoRiuscito();
                            break;
                        }

                    case CallingReason.DIFFERITO:
                        {
                            PagamentoDifferito();
                            break;
                        }

                    case CallingReason.TIMEOUT:
                        {
                            TimeoutPagamento();
                            break;
                        }

                    default:
                        {
                            AnnullaPagamento();
                            break;
                        }
                }
            }
            catch (Exception ex)
            {
                Errori.Add($"Pagamento fallito: {ex.Message}");
                MostraVistaPagamentoFallito();
            }
        }

        private void PagamentoDifferito()
        {
            multiView.ActiveViewIndex = Constants.ViewPagamentoInAttesa;
        }

        private void TimeoutPagamento()
        {
            Errori.Add($"Il pagamento risulta essere ancora in corso, riprovare più tardi per verificare se andato a buon fine oppure contattare il comune comunicando il codice del pagamento visibile nello step precedente");
            MostraVistaPagamentoFallito();
        }

        private void AnnullaPagamento()
        {
            PagamentiService.AggiornaStatoPagamentiInSospeso(IdDomanda);
            MostraVistaPagamentoFallito();
        }

        private void MostraVistaPagamentoFallito()
        {
            multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
            Master.MostraBottoneAvanti = false;
        }

        private void PagamentoRiuscito()
        {
            try
            {
                var verifica = PagamentiService.VerificaPagamento(IdDomanda, IdTransaction);

                // Il pagamento è riuscito ma non risultano oneri pagati. Effettuo un redirect sulla stessa pagina
                if (!verifica)
                {
                    Response.Redirect("~/reserved/inserimentoistanza/pagamenti/pagamentoentranext.aspx?" + this.Request.QueryString);

                    return;
                }

                multiView.ActiveViewIndex = Constants.ViewPagamentoRiuscito;
            }
            catch (Exception ex)
            {
                Errori.Add($"{ex.Message}");
                MostraVistaPagamentoFallito();
            }
        }

        private void IniziaPagamento()
        {
            try
            {
                Master.MostraBottoneAvanti = false;

                //PagamentiService.AggiornaStatoPagamentiInSospeso(IdDomanda);

                var email = String.IsNullOrEmpty(UserAuthenticationResult.DatiUtente.Email) ? ReadFacade.Domanda.AltriDati.DomicilioElettronico : UserAuthenticationResult.DatiUtente.Email;

                if (String.IsNullOrEmpty(email))
                {
                    Errori.Add("Impossibile inizializzare il pagamento perchè l'indirizzo email dell'utente è mancante. Tornare allo step di inserimento anagrafiche e inserire un indirizzo email valido.");
                    ErrorePagamento = true;
                    return;
                }

                var anagraficaDomanda = ReadFacade.Domanda.Anagrafiche
                                  .Anagrafiche
                                  .Where(x => x.Codicefiscale == UserAuthenticationResult.DatiUtente.Codicefiscale && x.TipoPersona == AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.TipoPersonaEnum.Fisica)
                                  .FirstOrDefault();

                var estremiDomanda = new EstremiDomandaEntraNext(IdDomanda, Master.LastStep, anagraficaDomanda, this._comuniService);
                var datiAvvioPagamento = PagamentiService.InizializzaPagamento(estremiDomanda);

                PagamentiService.AvviaPagamento(IdDomanda, datiAvvioPagamento.NumeroOperazione, datiAvvioPagamento.Oneri);
                UrlPagamenti = datiAvvioPagamento.UrlAvvioPagamento;

                multiView.ActiveViewIndex = Constants.ViewNuovoPagamento;
            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
                ErrorePagamento = true;
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