using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class IstanzePresentateV2 : ReservedBasePage
	{
		private static class Constants
		{
			public const int VistaRicerca = 0;
			public const int VistaLista = 1;
			public const string SessionKey = "IstanzePresentateV2:SessionKey";
		}

		[Inject]
		public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

		private bool BindFromLastResult
		{
			get{
				var val = Request.QueryString["fromLastResult"];
				
				if(String.IsNullOrEmpty(val))
					return false;
				
				return val.ToUpper()=="TRUE";
			}
		}

		private DettaglioPraticaBreveType[] UltimoRisultato
		{
			get { return (DettaglioPraticaBreveType[])this.Session[Constants.SessionKey]; }
			set { this.Session[Constants.SessionKey] = value; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				if (BindFromLastResult)
				{
					DataBindFromLastResult();
				}
				else
				{
					DataBind();
				}
			}
		}

		private void DataBindFromLastResult()
		{
			BindResultsGrid(UltimoRisultato);
		}

		public override void DataBind()
		{
			filtriVisuraControl.IdComune = this.IdComune;
			filtriVisuraControl.Software = this.Software;
			filtriVisuraControl.DataBind();
		}



		protected void cmdCerca_Click(object sender, EventArgs e)
		{
			try
			{
				var filtri = filtriVisuraControl.GetRichiestaLista(UserAuthenticationResult.DatiUtente);

				var risultato = _istanzePresentateRepository.GetListaPratiche(IdComune, Software, filtri);

				if (risultato.dettaglioErrore != null && risultato.dettaglioErrore.Length > 0)
				{
					foreach (var errore in risultato.dettaglioErrore)
					{
						Errori.Add(errore.numeroErrore + " - " + errore.descrizione);
					}
				}

				BindResultsGrid(risultato.dettaglioPratica);
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		private void BindResultsGrid(DettaglioPraticaBreveType[] risultato)
		{
			multiView.ActiveViewIndex = Constants.VistaLista;

			UltimoRisultato = risultato;

			listaPraticheVisuraV2.PageIndex = 0;
			listaPraticheVisuraV2.DataSource = risultato;
			listaPraticheVisuraV2.DataBind();
		}


		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = Constants.VistaRicerca;
		}

		protected void listaPraticheVisuraV2_IstanzaSelezionata(string codiceIstanza)
		{
			Response.Redirect("~/Reserved/DettaglioIstanzaV2.aspx?IdComune=" + IdComune + "&Id=" + codiceIstanza + "&Software=" + this.Software + "&Token=" + UserAuthenticationResult.Token);
		}
	}
}