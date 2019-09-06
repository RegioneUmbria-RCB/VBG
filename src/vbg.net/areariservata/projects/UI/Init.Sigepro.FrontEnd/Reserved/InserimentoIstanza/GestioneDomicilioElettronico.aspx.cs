using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using System.Text.RegularExpressions;

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
		public DomicilioElettronicoService DomicilioElettronicoService { get; set; }

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
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
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
			var domicilioElettronico = ddlDomicilioElettronico.SelectedValue;

			if (domicilioElettronico == "-")
				domicilioElettronico = txtAltroIndirizzo.Text;

			DomicilioElettronicoService.ImpostaDomicilioElettronico(IdDomanda, domicilioElettronico);
		}

		public override bool CanExitStep()
		{
			if (String.IsNullOrEmpty(ReadFacade.Domanda.AltriDati.DomicilioElettronico))
			{
				Errori.Add(MessaggioErroreDomicilioElettronicoMancante);
				return false;
			}


			if (!DomicilioElettronicoValido(ReadFacade.Domanda.AltriDati.DomicilioElettronico))
			{
				var msg = String.IsNullOrEmpty(ddlDomicilioElettronico.SelectedValue) ?
							Constants.MessaggioErroreEmailImmessaNonValida :
							Constants.MessaggioErroreEmailSelezionataNonValida;

				Errori.Add(msg);

				return false;
			}

			
			return true;
		}

		private bool DomicilioElettronicoValido(string indirizzo)
		{
			if (indirizzo.IndexOf("@") == -1)
				return false;

			if (indirizzo.IndexOf(".") == -1)
				return false;

			return true;
		}
        #endregion

        public override void DataBind()
        {
            var listaAnagrafiche = ReadFacade.Domanda.Anagrafiche
                                                     .Anagrafiche
                                                     .Where(x => !String.IsNullOrEmpty(x.Contatti.Pec))
                                                     .Select(x => new { Email = x.Contatti.Pec, Nominativo = x.ToString() + ": " + x.Contatti.Pec });

            if (this.UsaEmailSePecNonTrovata)
            {
                listaAnagrafiche = ReadFacade.Domanda.Anagrafiche
                                                     .Anagrafiche
                                                     .Select(x => new
                                                     {
                                                         Email = EstraiIndirizzoContatto(x),
                                                         Nominativo = x.ToString()
                                                     })
                                                     .Where(x => !string.IsNullOrEmpty(x.Email))
                                                     .Select(x => new
                                                     {
                                                         Email = x.Email,
                                                         Nominativo = x.Nominativo + ": " + x.Email
                                                     });
            }

            var domicilioPrecedentementeInserito = ReadFacade.Domanda.AltriDati.DomicilioElettronico;

            var domicilioDiUnSoggetto = listaAnagrafiche.Where(x => x.Email.ToUpper() == domicilioPrecedentementeInserito.ToUpper())
                                                 .Any();

            var dataSource = listaAnagrafiche.OrderBy(x => x.Nominativo).ToList();
            dataSource.Insert(0, new { Email = "", Nominativo = this.TestoSelezionareIndirizzo });
            dataSource.Add(new { Email = Constants.ValoreDefaultAltroIndirizzoPec, Nominativo = this.TestoAltroIndirizzoPEC });

            ddlDomicilioElettronico.DataSource = dataSource;
            ddlDomicilioElettronico.DataBind();

            // - Se ho già inserito il domicilio elettronico di un soggetto e l'indirizzo email è presente nella lista anagrafiche
            //   allora seleziono nella combo lianagrafica corrispondente
            // - Se ho inserito un domicilio elettronico ma non è l'indirizzo di uno dei soggetti allora seleziono l'elemento
            //   "Altro indirizzo pec/email"(il nome può variare) con il valore "Constants.ValoreDefaultAltroIndirizzoPec"
            // - Se non ho inserito nessun domicilio elettronico (ad es. quando entro per la prima volta nello step) seleziono
            //   l'elemento "Selezionare ..."
            ddlDomicilioElettronico.SelectedValue = String.Empty;

            if (!String.IsNullOrEmpty(domicilioPrecedentementeInserito))
            {
                if (domicilioDiUnSoggetto)
                {
                    ddlDomicilioElettronico.SelectedValue = domicilioPrecedentementeInserito;
                }
                else
                {
                    ddlDomicilioElettronico.SelectedValue = Constants.ValoreDefaultAltroIndirizzoPec;
                }
            }
            
			txtAltroIndirizzo.Text = domicilioDiUnSoggetto ? String.Empty : domicilioPrecedentementeInserito ;
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