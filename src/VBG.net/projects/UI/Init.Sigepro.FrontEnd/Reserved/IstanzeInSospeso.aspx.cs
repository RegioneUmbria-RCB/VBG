using System;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using System.Diagnostics;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class ListaIstanzePresentate : ReservedBasePage
	{
		[Inject]
		public IInterventiRepository _alberoProcRepository { get;set; }
		[Inject]
		public DomandeOnlineService _datiDomandaService { get; set; }


		public bool MostraDatiCatastaliEstesi
		{
			get
			{
				var obj = ConfigurationManager.AppSettings["MostraDatiCatastaliEstesi"];

				if (String.IsNullOrEmpty(obj))
					return false;

				try
				{
					return Convert.ToBoolean(obj);
				}
				catch (Exception)
				{
					return false;
				}
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				DataBind();
			}

		}

		protected override void DataBind(bool raiseOnDataBinding)
		{
			var codiceAnagrafe = UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value;

			lblTitoloIstanzeNonAcquisite.Visible = false;
			dgIstanzeNonAcquisite.Visible = false;

			var ds = _datiDomandaService.GetDomandeInSospeso(codiceAnagrafe);

			dgIstanzePresentate.DataSource = ds;
			dgIstanzePresentate.DataBind();
		}


		public void dgIstanzePresentate_SelectedIndexChanged(object sender, EventArgs e)
		{
			string key = dgIstanzePresentate.DataKeys[dgIstanzePresentate.SelectedIndex].Value.ToString();

			Redirect("~/Reserved/InserimentoIstanza/Benvenuto.aspx", qs =>
			{
				qs.Add("StepId", "0");
				qs.Add("IdPresentazione", key);
			});
		}


		public void dgIstanzePresentate_ItemDataBound(object sender, GridViewRowEventArgs e)
		{
			if ( e.Row.RowType == DataControlRowType.DataRow )
			{
				Label lblRichiedente = (Label)e.Row.FindControl("lblRichiedente");
				Label lblTipoIntervento = (Label)e.Row.FindControl("lblTipoIntervento");
				//Label lblIndirizzo = (Label)e.Row.FindControl("lblIndirizzo");
				//Label lblCivico = (Label)e.Row.FindControl("lblCivico");
				//Label lblEsponente = (Label)e.Row.FindControl("lblEsponente");
				//Label lblColore = (Label)e.Row.FindControl("lblColore");
				//Label lblScala = (Label)e.Row.FindControl("lblScala");
				//Label lblInterno = (Label)e.Row.FindControl("lblInterno");
				//Label lblEsponenteInterno = (Label)e.Row.FindControl("lblEsponenteInterno");
				Label lblIdDomanda = (Label)e.Row.FindControl("lblIdDomanda");

				var datiDomanda = (FoDomande)e.Row.DataItem;

				try
				{
					var domanda = _datiDomandaService.GetById(datiDomanda.Id.Value);

					// Nominativo
					var richiedente = domanda.ReadInterface.Anagrafiche.GetRichiedente();


					if (richiedente != null)
						lblRichiedente.Text = richiedente.ToString();

					//lblIdDomanda.Text = domanda.GetPresentazioneIstanzaDataKey().ToString();

					// Intervento
					try
					{

						string strIntervento = domanda.ReadInterface.AltriDati.Intervento == null ? "Non definito" : domanda.ReadInterface.AltriDati.Intervento.Descrizione;
						
						if(String.IsNullOrEmpty(strIntervento))
							strIntervento = StringaIntervento(domanda);

						lblTipoIntervento.Text = strIntervento.Replace(Environment.NewLine, "<br />");
						//var txt = dataSet.ISTANZE[0].OGGETTO;
						//lblTipoIntervento.Text = "<pre>" + (String.IsNullOrEmpty( txt ) ? "Non specificato" : txt) + "</pre>";
					}
					catch (Exception)
					{
						lblTipoIntervento.Text = "<div style='color:red'>Intervento non valido. La domanda non potrà essere ripresa</div>";
						LinkButton lb = (LinkButton)e.Row.FindControl("Linkbutton1");

						lb.Visible = false;
					}
				}
				catch (Exception ex)
				{
					lblTipoIntervento.Text = "Errore nella lettura dei dati della domanda: " + ex.Message;
				}
			}
		}

		public string StringaIntervento(DomandaOnline domanda)
		{
			var strIntervento = "Non specificato";

			if (domanda.ReadInterface.AltriDati.Intervento == null)
				return strIntervento;

			var idcomune = domanda.DataKey.IdComune;
			var codIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;

			var albero = _alberoProcRepository.GetAlberaturaNodoDaId(idcomune, codIntervento);

			return AttraversaAlbero(albero);

		}

		private string AttraversaAlbero(ClassTreeOfInterventoDto albero)
		{
			var str = albero.Elemento.Descrizione;

			str += Environment.NewLine;

			if (albero.NodiFiglio.Length > 0)
				str += AttraversaAlbero(albero.NodiFiglio[0]);

			return str;

		}



		protected void cmdDeleteRows_Click(object sender, EventArgs e)
		{
			foreach (GridViewRow it in dgIstanzePresentate.Rows)
			{
				var chkChecked = (CheckBox)it.FindControl("chkChecked");

				if (chkChecked.Checked)
				{
					int key = (int)dgIstanzePresentate.DataKeys[it.RowIndex].Value;
					_datiDomandaService.Elimina( key , UserAuthenticationResult.DatiUtente.Codicefiscale);
				}
			}

			DataBind();
		}
	}
}
