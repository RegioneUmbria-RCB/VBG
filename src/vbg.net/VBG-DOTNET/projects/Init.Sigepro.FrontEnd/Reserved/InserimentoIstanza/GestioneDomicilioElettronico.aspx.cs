using Init.Sigepro.FrontEnd.AppLogic.GestioneDomicilioElettronico;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Ninject;
using System;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneDomicilioElettronico : IstanzeStepPage
    {
        private static class Constants
        {
            public const string ViewstateKeyMessaggioErroreEmailMancante = "MessaggioErroreDomicilioElettronicoMancante";
            public const string MessaggioErroreEmailMancante = "Specificare il domicilio elettronico da utilizzare per l'istanza";
            public const string MessaggioErroreEmailImmessaNonValida = "L'indirizzo immesso non è un indirizzo email valido";
            public const string MessaggioErroreEmailSelezionataNonValida = "L'indirizzo selezionato non è un indirizzo email valido";
            public const string PatternValidazioneEmail = @"^[a-zA-Z]+(([\'\,\.\- ][a-zA-Z ])?[a-zA-Z]*)*\s+&lt;(\w[-._\w]*\w@\w[-._\w]*\w\.\w{2,3})&gt;$|^(\w[-._\w]*\w@\w[-._\w]*\w\.\w{2,3})$";
            public const string ValoreDefaultAltroIndirizzoPec = "-";
        }

        [Inject]
        public IDomicilioElettronicoService DomicilioElettronicoService { get; set; }

        public bool UsaEmailSePecNonTrovata
        {
            get { object o = this.ViewState["UsaEmailSePecNonTrovata"]; return o == null ? false : (bool)o; }
            set { this.ViewState["UsaEmailSePecNonTrovata"] = value; }
        }

        public bool DaiPrioritaAEmail
        {
            get { object o = this.ViewState["DaiPrioritaAEmail"]; return o == null ? false : (bool)o; }
            set { this.ViewState["DaiPrioritaAEmail"] = value; }
        }


        public string MessaggioErroreDomicilioElettronicoMancante
        {
            get { object o = this.ViewState[Constants.ViewstateKeyMessaggioErroreEmailMancante]; return o == null ? Constants.MessaggioErroreEmailMancante : (string)o; }
            set { this.ViewState[Constants.ViewstateKeyMessaggioErroreEmailMancante] = value; }
        }

        public string TestoAltroIndirizzoPEC
        {
            get { object o = this.ViewState["TestoAltroIndirizzoPEC"]; return o == null ? "Altro indirizzo PEC" : (string)o; }
            set { this.ViewState["TestoAltroIndirizzoPEC"] = value; }
        }

        public string TestoSelezionareIndirizzo
        {
            get { object o = this.ViewState["TestoSelezionareIndirizzo"]; return o == null ? "Seleziona..." : (string)o; }
            set { this.ViewState["TestoSelezionareIndirizzo"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // Il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
                this.DataBind();
        }

        #region gestione del ciclo di vita dello step
        public override void OnInitializeStep()
        {
            base.OnInitializeStep();
        }

        public override bool CanEnterStep()
        {
            return base.CanEnterStep();
        }

        public override void OnBeforeExitStep()
        {
            var domicilioElettronico = this.ddlDomicilioElettronico.SelectedValue;

            if (domicilioElettronico == "-")
                domicilioElettronico = this.txtAltroIndirizzo.Text;

            this.DomicilioElettronicoService.ImpostaDomicilioElettronico(this.IdDomanda, domicilioElettronico);
        }

        public override bool CanExitStep()
        {
            if (String.IsNullOrEmpty(this.ReadFacade.Domanda.AltriDati.DomicilioElettronico))
            {
                this.Errori.Add(this.MessaggioErroreDomicilioElettronicoMancante);
                return false;
            }


            if (!this.DomicilioElettronicoValido(this.ReadFacade.Domanda.AltriDati.DomicilioElettronico))
            {
                var msg = this.ddlDomicilioElettronico.SelectedValue == "-" ?   // è stata selezionata la voce "Altro indirizzo email"
                            Constants.MessaggioErroreEmailImmessaNonValida :
                            Constants.MessaggioErroreEmailSelezionataNonValida;

                this.Errori.Add(msg);

                return false;
            }


            return true;
        }

        private bool DomicilioElettronicoValido(string indirizzo)
        {
            return EmailValidator.ValidaEmail(indirizzo, fallisciSeVuota: true);
        }
        #endregion

        public override void DataBind()
        {
            var listaAnagrafiche = this.ReadFacade.Domanda.Anagrafiche
                                                     .Anagrafiche
                                                     .Where(x => !String.IsNullOrEmpty(x.Contatti.Pec))
                                                     .Select(x => new { Email = x.Contatti.Pec, Nominativo = x.ToString() + ": " + x.Contatti.Pec });

            if (this.UsaEmailSePecNonTrovata)
            {
                listaAnagrafiche = this.ReadFacade.Domanda.Anagrafiche
                                                     .Anagrafiche
                                                     .Select(x => new
                                                     {
                                                         Email = this.EstraiIndirizzoContatto(x),
                                                         Nominativo = x.ToString()
                                                     })
                                                     .Where(x => !string.IsNullOrEmpty(x.Email))
                                                     .Select(x => new
                                                     {
                                                         Email = x.Email,
                                                         Nominativo = x.Nominativo + ": " + x.Email
                                                     });
            }

            var domicilioPrecedentementeInserito = this.ReadFacade.Domanda.AltriDati.DomicilioElettronico;

            var domicilioDiUnSoggetto = listaAnagrafiche.Where(x => x.Email.ToUpper() == domicilioPrecedentementeInserito.ToUpper())
                                                 .Any();

            var dataSource = listaAnagrafiche.OrderBy(x => x.Nominativo).ToList();
            dataSource.Insert(0, new { Email = "", Nominativo = this.TestoSelezionareIndirizzo });
            dataSource.Add(new { Email = Constants.ValoreDefaultAltroIndirizzoPec, Nominativo = this.TestoAltroIndirizzoPEC });

            this.ddlDomicilioElettronico.DataSource = dataSource;
            this.ddlDomicilioElettronico.DataBind();

            // - Se ho già inserito il domicilio elettronico di un soggetto e l'indirizzo email è presente nella lista anagrafiche
            //   allora seleziono nella combo lianagrafica corrispondente
            // - Se ho inserito un domicilio elettronico ma non è l'indirizzo di uno dei soggetti allora seleziono l'elemento
            //   "Altro indirizzo pec/email"(il nome può variare) con il valore "Constants.ValoreDefaultAltroIndirizzoPec"
            // - Se non ho inserito nessun domicilio elettronico (ad es. quando entro per la prima volta nello step) seleziono
            //   l'elemento "Selezionare ..."
            this.ddlDomicilioElettronico.SelectedValue = String.Empty;

            if (!String.IsNullOrEmpty(domicilioPrecedentementeInserito))
            {
                if (domicilioDiUnSoggetto)
                {
                    this.ddlDomicilioElettronico.SelectedValue = domicilioPrecedentementeInserito;
                }
                else
                {
                    this.ddlDomicilioElettronico.SelectedValue = Constants.ValoreDefaultAltroIndirizzoPec;
                }
            }

            this.txtAltroIndirizzo.Text = domicilioDiUnSoggetto ? String.Empty : domicilioPrecedentementeInserito;
        }

        private string EstraiIndirizzoContatto(AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.AnagraficaDomanda x)
        {
            if (this.DaiPrioritaAEmail)
            {
                return String.IsNullOrEmpty(x.Contatti.Email) ? x.Contatti.Pec : x.Contatti.Email;
            }
            return String.IsNullOrEmpty(x.Contatti.Pec) ? x.Contatti.Email : x.Contatti.Pec;
        }
    }
}