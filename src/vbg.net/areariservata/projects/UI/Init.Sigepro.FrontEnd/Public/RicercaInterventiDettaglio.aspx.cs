using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Contenuti;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class RicercaInterventiDettaglio : ContenutiBasePage
	{
        [Inject]
        protected AllegatiInterventoService _allegatiInterventoService { get; set; }

		public new string IdComune
		{
			get { return Request.QueryString["IdComune"]; }
		}


		public string Id
		{
			get { return Request.QueryString["Id"]; }
		}

        protected bool ModelloDomandaPresente
        {
            get
            {
                var codiceOggettoRiepilogo = _allegatiInterventoService.GetCodiceOggettoDelModelloDiRiepilogo(Convert.ToInt32(Id));

                return codiceOggettoRiepilogo.HasValue;
            }
        }


		protected void Page_Load(object sender, EventArgs e)
		{

		}

		public string GetUrlStampaPagina()
		{
			return GetBaseUrlAssoluto() + "Public/MostraDettagliIntervento.aspx?idComune=" + IdComune + "&Id=" + Id + "&Print=true";
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
		}

		protected void cmdClose_Click(object sender, EventArgs e)
		{
			Response.Redirect("~/Public/RicercaInterventi.aspx?IdComune=" + IdComune + "&Software=" + Software );
		}
	}
}