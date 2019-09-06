using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria
{
	public partial class AllegatiBandoB1 : IstanzeStepPage
	{
		public int CodiceOggettoAllegato3
		{
			get { object o = this.ViewState["CodiceOggettoAllegato3"]; return o == null ? 49 : (int)o; }
			set { this.ViewState["CodiceOggettoAllegato3"] = value; }
		}

		public int CodiceOggettoAllegato4
		{
			get { object o = this.ViewState["CodiceOggettoAllegato4"]; return o == null ? 50 : (int)o; }
			set { this.ViewState["CodiceOggettoAllegato4"] = value; }
		}

		public int CodiceOggettoAllegato10
		{
			get { object o = this.ViewState["CodiceOggettoAllegato10"]; return o == null ? 53 : (int)o; }
			set { this.ViewState["CodiceOggettoAllegato10"] = value; }
		}

		public int CodiceOggettoAllegatoAltreSedi
		{
			get { object o = this.ViewState["CodiceOggettoAllegatoAltreSedi"]; return o == null ? 894 : (int)o; }
			set { this.ViewState["CodiceOggettoAllegatoAltreSedi"] = value; }
		}
		

		[Inject]
		protected IBandiUmbriaService _service { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				DataBind();
			}
		}

		public override void DataBind()
		{
			var datiBando = this._service.GetDatiDomandaB1(IdDomanda, CodiceOggettoAllegato3, CodiceOggettoAllegato4, CodiceOggettoAllegato10, CodiceOggettoAllegatoAltreSedi);

			var dati = new List<AziendaBando>(datiBando.Aziende);

			var aziendaBando = new AziendaBando()
			{
				RagioneSociale = "Allegati della domanda",
				Allegati = new List<AllegatoDomandaBandi>{
					datiBando.Allegato3,
					datiBando.Allegato4,
					datiBando.AllegatoAltreSediOperative
				}
			};

			dati.Insert(0, aziendaBando);


			this.rptAziende.DataSource = dati;
			this.rptAziende.DataBind();
		}

		protected void AziendaDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				var dataItem = (AziendaBando)e.Item.DataItem;
				var crtlDatiAzienda = (AllegatiBandoA1BindingGrid)e.Item.FindControl("crtlDatiAzienda");

				crtlDatiAzienda.DataSource = dataItem;
				crtlDatiAzienda.DataBind();
			}
		}

		protected void OnErrore(object sender, Exception e)
		{
			this.Errori.Add(e.Message);

			DataBind();
		}

		protected void OnFileUploaded(object sender, AllegatiBandoA1BindingGrid.FileUploadedEventArgs e)
		{
			try
			{
				this._service.AllegaADomanda(IdDomanda, e.IdAllegato, e.File, e.CercaTag);
			}
			catch (Exception ex)
			{
				this.Errori.Add(ex.Message);
			}
			finally
			{
				DataBind();
			}
		}

		protected void OnFileDeleted(object sender, AllegatiBandoA1BindingGrid.FileEventArgs e)
		{
			this._service.RimuoviAllegatoDaDomanda(IdDomanda, e.IdAllegato);

			DataBind();
		}

		public override bool CanExitStep()
		{
			var errori = this._service.ValidaPresenzaAllegati(IdDomanda);

			this.Errori.AddRange(errori);

			return errori.Count() == 0;
		}
	}
}