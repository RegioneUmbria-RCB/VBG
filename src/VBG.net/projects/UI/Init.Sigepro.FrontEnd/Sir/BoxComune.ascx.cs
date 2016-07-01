using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.IoC;

namespace Init.Sigepro.FrontEnd.Sir
{
	public partial class BoxComune : System.Web.UI.UserControl
	{
		[Inject]
		protected ConfigurazioneContenuti _configurazione { get; set; }

		public BoxDatiComune DataSource { get; set; }
		public string UrlLogo { get; set; }

		public string AliasComune
		{
			get
			{
				return Request.QueryString["alias"];
			}
		}

		public string Software
		{
			get
			{
				var sw = Request.QueryString["software"];

				if (String.IsNullOrEmpty(sw))
					return "SS";
				return sw;
			}
		}

		public BoxComune()
		{
			FoKernelContainer.Inject(this); 
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			this.UrlLogo = String.Format("{0}?alias={1}&Software={2}", ResolveClientUrl("~/Contenuti/logoComune.ashx"), AliasComune, Software);

			this.DataSource = this._configurazione.DatiComune;
			base.DataBind();
		}
	}
}