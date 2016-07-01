using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Contenuti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.Sir
{
	public partial class Step3 : ContenutiBasePage
	{
		[Inject]
		protected IInterventiAllegatiRepository _interventiAllegatiRepository { get; set; }

		//		[RegExValidate("^[0-9]{1,10}$")]
		public int Id
		{
			get { return Convert.ToInt32(Request.QueryString["Id"]); }
		}

		public Step3()
		{
			//
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public string GetUrlStampaPagina()
		{
			return GetUrlBase() + "Public/MostraDettagliIntervento.aspx?idComune=" + IdComune + "&Id=" + Id + "&Print=true";
		}

		public string GetUrlDownloadPagina()
		{
			var downloadUrl = GetUrlStampaPagina();

			return ResolveClientUrl("~/Public/DownloadPage.ashx") + "?IdComune=" + IdComune + "&url=" + Server.UrlEncode(downloadUrl);
		}

		public string GetUrlEndoAttivabili()
		{
			return ResolveClientUrl("~/Public/ListaEndoAttivabili.aspx") + "?IdComune=" + IdComune + "&intervento=" + Id + "&fromAreaRiservata=false";
		}

		public override void DataBind()
		{
			hlVisualizzaModello.Visible = false;
			//imgVisualizzaModello.Visible = false;

			var documentiIntervento = _interventiAllegatiRepository.GetAllegatiDaIdintervento(Id, AmbitoRicerca.AreaRiservata);

			var riepilogoDomanda = documentiIntervento.Where(x => x.RiepilogoDomanda).FirstOrDefault();

			if (riepilogoDomanda != null)
			{
				hlVisualizzaModello.Visible = true;
				//imgVisualizzaModello.Visible = true;
				hlVisualizzaModello.NavigateUrl = ResolveClientUrl("~/Public/ModelloDomanda/Visualizza.aspx") + "?idComune=" + IdComune + "&Intervento=" + Id + "&Software=" + this.Software;
				//imgVisualizzaModello.ImageUrl = ResolveClientUrl("~/images/contenuti/visualizza-modello.jpg");
			}
		}
	}
}