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

		}

		[Inject]
		public DomicilioElettronicoService DomicilioElettronicoService { get; set; }

		public string MessaggioErroreDomicilioElettronicoMancante
		{
			get { object o = this.ViewState[Constants.ViewstateKeyMessaggioErroreEmailMancante]; return o == null ? Constants.MessaggioErroreEmailMancante : (string)o; }
			set { this.ViewState[Constants.ViewstateKeyMessaggioErroreEmailMancante] = value; }
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

			if (String.IsNullOrEmpty(domicilioElettronico))
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

			var domicilioPrecedentementeInserito = ReadFacade.Domanda.AltriDati.DomicilioElettronico;

			var domicilioDiUnSoggetto = listaAnagrafiche.Where(x => x.Email.ToUpperInvariant() == domicilioPrecedentementeInserito.ToUpperInvariant())
												 .Count() > 0;

			var dataSource = listaAnagrafiche.OrderBy(x => x.Nominativo).ToList();
			dataSource.Add(new { Email = String.Empty, Nominativo = "Altro indirizzo PEC" });

			ddlDomicilioElettronico.DataSource = dataSource;
			ddlDomicilioElettronico.DataBind();

			ddlDomicilioElettronico.SelectedValue = domicilioDiUnSoggetto ? domicilioPrecedentementeInserito : String.Empty;
			txtAltroIndirizzo.Text = domicilioDiUnSoggetto ? String.Empty : domicilioPrecedentementeInserito ;


		}
	}
}